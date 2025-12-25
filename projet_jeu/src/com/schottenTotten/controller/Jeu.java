package com.schottenTotten.controller;

import com.schottenTotten.ai.Coup;
import com.schottenTotten.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jeu {

    private final Joueur joueur1;
    private final Joueur joueur2;
    private final List<Borne> bornes;

    private final Pioche<ClanCarte> piocheClan;
    private Pioche<CarteTactiqueElite> piocheTactique; 

    private int joueurActuel;
    private final EvaluateurCombinaison evaluateur;

    private final boolean varianteTactique;
    private final Random rng = new Random();

    private final ResolveurElite resolveurJ1;
    private final ResolveurElite resolveurJ2;

    private int tactiquesPoseesJ1 = 0;
    private int tactiquesPoseesJ2 = 0;

    private static final int NB_BORNES = 9;

    public Jeu(Joueur j1, Joueur j2) {
        this(j1, j2, false, null, null);
    }

    public Jeu(Joueur j1, Joueur j2, boolean varianteTactique) {
        this(j1, j2, varianteTactique, null, null);
    }

    public Jeu(Joueur j1, Joueur j2, boolean varianteTactique,
               ResolveurElite resolveurJ1, ResolveurElite resolveurJ2) {

        this.joueur1 = j1;
        this.joueur2 = j2;
        this.varianteTactique = varianteTactique;

        this.resolveurJ1 = resolveurJ1;
        this.resolveurJ2 = resolveurJ2;

        this.bornes = new ArrayList<>();
        this.evaluateur = new EvaluateurCombinaison();
        this.joueurActuel = 1;

        for (int i = 0; i < NB_BORNES; i++) {
            bornes.add(new Borne(i));
        }

        this.piocheClan = new Pioche<>(creerToutesLesCartes());

        if (varianteTactique) {
            List<CarteTactiqueElite> elites = FabriqueTactique.creerPiocheElites();
            this.piocheTactique = new Pioche<>(elites);
        }
    }

    public boolean isVarianteTactique() {
        return varianteTactique;
    }

    public void demarrer() {
        piocheClan.melanger();
        if (varianteTactique) piocheTactique.melanger();

        int tailleMain = varianteTactique ? 7 : 6;

        for (int i = 0; i < tailleMain; i++) {
            distribuerUneCarteClan(joueur1);
            distribuerUneCarteClan(joueur2);
        }
    }

    private void distribuerUneCarteClan(Joueur j) {
        ClanCarte c = piocheClan.piocher();
        if (c != null) j.addCardToHand(c);
    }

    private void distribuerUneCarteTactique(Joueur j) {
        if (!varianteTactique) return;
        CarteTactiqueElite c = piocheTactique.piocher();
        if (c != null) j.addCardToHand(c);
    }

    private boolean peutPiocherTactique() {
        return varianteTactique && piocheTactique != null && !piocheTactique.estVide();
    }

    private boolean peutPiocherClan() {
        return !piocheClan.estVide();
    }

    private void piocherFinDeCoup(Joueur jActif, int numJoueur, int choixPioche) {
        if (!varianteTactique) {
            if (peutPiocherClan()) distribuerUneCarteClan(jActif);
            return;
        }

        int choix = choixPioche;
        if (choix == 0) {
            if (peutPiocherTactique() && peutPiocherClan()) {
                choix = rng.nextBoolean() ? 1 : 2;
            } else if (peutPiocherTactique()) {
                choix = 2;
            } else {
                choix = 1;
            }
        }

        if (choix == 2 && peutPiocherTactique()) {
            distribuerUneCarteTactique(jActif);
        } else if (choix == 1 && peutPiocherClan()) {
            distribuerUneCarteClan(jActif);
        }
    }

    public List<Coup> coupsValides(int numJoueur) {
        List<Coup> res = new ArrayList<>();
        Joueur j = (numJoueur == 1) ? joueur1 : joueur2;

        for (int iCarte = 0; iCarte < j.getHand().size(); iCarte++) {
            CarteMain cm = j.getHand().get(iCarte);

            if (!(cm instanceof CarteCombat)) continue;

            if (varianteTactique && (cm instanceof CarteTactiqueElite)) {
                if (!tactiqueAutorisee(numJoueur)) continue;
            }

            for (int iBorne = 0; iBorne < bornes.size(); iBorne++) {
                Borne b = bornes.get(iBorne);
                if (!b.estRevendiquee() && !b.isFull(numJoueur)) {
                    res.add(new Coup(iBorne, iCarte));
                }
            }
        }
        return res;
    }

    private boolean tactiqueAutorisee(int numJoueur) {
        int m = (numJoueur == 1) ? tactiquesPoseesJ1 : tactiquesPoseesJ2;
        int a = (numJoueur == 1) ? tactiquesPoseesJ2 : tactiquesPoseesJ1;
        return m <= a;
    }

    public boolean jouerCoup(int indexBorne, int indexCarteMain, int choixPioche) {
        Joueur jActif = getJoueurActuel();
        int numJoueur = joueurActuel;

        if (indexBorne < 0 || indexBorne >= bornes.size()) return false;
        if (indexCarteMain < 0 || indexCarteMain >= jActif.getHand().size()) return false;

        Borne b = bornes.get(indexBorne);
        if (b.estRevendiquee()) return false;
        if (b.isFull(numJoueur)) return false;

        CarteMain carteMain = jActif.getHand().get(indexCarteMain);
        if (!(carteMain instanceof CarteCombat)) return false;

        if (varianteTactique && (carteMain instanceof CarteTactiqueElite)) {
            if (!tactiqueAutorisee(numJoueur)) return false;
        }

        boolean ok = jActif.removeCardFromHand(carteMain);
        if (!ok) return false;

        CarteCombat carteCombat = (CarteCombat) carteMain;
        b.addCarte(numJoueur, carteCombat);

        if (varianteTactique && (carteMain instanceof CarteTactiqueElite)) {
            if (numJoueur == 1) tactiquesPoseesJ1++;
            else tactiquesPoseesJ2++;
        }

        for (Borne borne : bornes) {
            if (peutReclamer(numJoueur, borne)) {
                reclamer(numJoueur, borne);
            }
        }

        piocherFinDeCoup(jActif, numJoueur, choixPioche);

        changerJoueur();
        return true;
    }

    public boolean jouerCoup(int indexBorne, int indexCarteMain) {
        return jouerCoup(indexBorne, indexCarteMain, 0);
    }

    public boolean peutReclamer(int numJoueur, Borne b) {
        if (b == null) return false;
        if (b.estRevendiquee()) return false;

        int autre = (numJoueur == 1) ? 2 : 1;

        if (!b.isFull(numJoueur)) return false;
        if (!b.isFull(autre)) return false;

        if (varianteTactique) {
            resoudreElitesSiBesoin(b);
        }

        List<ClanCarte> cartesJoueur = toClanCartes(b.getCartes(numJoueur));
        List<ClanCarte> cartesAutre = toClanCartes(b.getCartes(autre));

        if (cartesJoueur.size() != 3) return false;
        if (cartesAutre.size() != 3) return false;

        ResultatCombinaison rJoueur = evaluateur.evaluer(cartesJoueur);
        ResultatCombinaison rAutre = evaluateur.evaluer(cartesAutre);

        return evaluateur.comparer(rJoueur, rAutre) > 0;
    }

    private void resoudreElitesSiBesoin(Borne b) {
        for (CarteCombat c : b.getCartes(1)) {
            if (c instanceof CarteTactiqueElite elite && !elite.estResolue()) {
                if (resolveurJ1 != null) resolveurJ1.resoudre(elite, 1, b);
            }
        }
        for (CarteCombat c : b.getCartes(2)) {
            if (c instanceof CarteTactiqueElite elite && !elite.estResolue()) {
                if (resolveurJ2 != null) resolveurJ2.resoudre(elite, 2, b);
            }
        }
    }

    private List<ClanCarte> toClanCartes(List<CarteCombat> cartes) {
        List<ClanCarte> res = new ArrayList<>();
        for (CarteCombat c : cartes) {
            if (c == null) continue;
            ClanCarte cc = c.enClanCarte();
            if (cc != null) res.add(cc);
        }
        return res;
    }

    public void reclamer(int numJoueur, Borne b) {
        if (!peutReclamer(numJoueur, b)) return;
        Joueur j = (numJoueur == 1) ? joueur1 : joueur2;
        b.setProprietaire(j);
        j.addBorneAcquise(b);
    }

    public Joueur estVictoire() {
        if (joueur1.getNombreBorneAcquises() >= 5) return joueur1;
        if (joueur2.getNombreBorneAcquises() >= 5) return joueur2;

        if (aTroisAdjacentes(joueur1)) return joueur1;
        if (aTroisAdjacentes(joueur2)) return joueur2;

        return null;
    }

    private boolean aTroisAdjacentes(Joueur j) {
        int streak = 0;
        for (Borne b : bornes) {
            if (b.getProprietaire() == j) streak++;
            else streak = 0;
            if (streak >= 3) return true;
        }
        return false;
    }

    private Joueur getJoueurActuel() {
        return (joueurActuel == 1) ? joueur1 : joueur2;
    }

    private void changerJoueur() {
        joueurActuel = (joueurActuel == 1) ? 2 : 1;
    }

    public int getJoueurActuelNumero() {
        return joueurActuel;
    }

    public Joueur getJoueur1() { return joueur1; }
    public Joueur getJoueur2() { return joueur2; }
    public List<Borne> getBornes() { return bornes; }

    private List<ClanCarte> creerToutesLesCartes() {
        List<ClanCarte> cartes = new ArrayList<>();

        List<Couleur> couleurs = new ArrayList<>();
        couleurs.add(new Couleur("Bleu", 0, 0, 255));
        couleurs.add(new Couleur("Rouge", 255, 0, 0));
        couleurs.add(new Couleur("Vert", 0, 255, 0));
        couleurs.add(new Couleur("Jaune", 255, 255, 0));
        couleurs.add(new Couleur("Violet", 128, 0, 128));
        couleurs.add(new Couleur("Orange", 255, 165, 0));

        for (Couleur c : couleurs) {
            for (int valeur = 1; valeur <= 9; valeur++) {
                cartes.add(new ClanCarte(valeur, c));
            }
        }
        return cartes;
    }
}
