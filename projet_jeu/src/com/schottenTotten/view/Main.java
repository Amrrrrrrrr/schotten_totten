package com.schottenTotten.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.schottenTotten.ai.Coup;
import com.schottenTotten.ai.IAAleatoire;
import com.schottenTotten.ai.StrategieIA;
import com.schottenTotten.controller.Jeu;
import com.schottenTotten.controller.ResolveurElite;
import com.schottenTotten.model.Borne;
import com.schottenTotten.model.CarteMain;
import com.schottenTotten.model.Couleur;
import com.schottenTotten.model.Joueur;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== SCHOTTEN TOTTEN (console) ===");

        System.out.println("\nChoisir l'edition/variante :");
        System.out.println("1) Classique");
        System.out.println("2) Tactique (Élites seulement)");
        int variante = lireChoix(sc, 1, 2);
        boolean tactique = (variante == 2);

        System.out.println("Variante choisie : " + (tactique ? "Tactique (Élites)" : "Classique"));

        System.out.println("\nParamètres des joueurs :");
        Joueur j1 = creerJoueur(sc, 1);
        Joueur j2 = creerJoueur(sc, 2);

        StrategieIA iaJ1 = choisirIA(sc, "Joueur 1", j1);
        StrategieIA iaJ2 = choisirIA(sc, "Joueur 2", j2);


        List<Couleur> couleurs = creerCouleurs();


        ResolveurElite resolveurJ1 = (iaJ1 == null) ? new ResolveurEliteConsole(sc, couleurs) : null;
        ResolveurElite resolveurJ2 = (iaJ2 == null) ? new ResolveurEliteConsole(sc, couleurs) : null;


        Jeu jeu = new Jeu(j1, j2, tactique, resolveurJ1, resolveurJ2);
        jeu.demarrer();

        System.out.println("\n Début de la partie !");
        while (jeu.estVictoire() == null) {
            afficherEtat(jeu);

            int numActuel = jeu.getJoueurActuelNumero();
            Joueur actif = (numActuel == 1) ? jeu.getJoueur1() : jeu.getJoueur2();
            StrategieIA iaActif = (numActuel == 1) ? iaJ1 : iaJ2;

            System.out.println("\n--- Tour de " + actif.getName() + " (J" + numActuel + ") ---");

            if (iaActif == null) {
                jouerTourHumain(sc, jeu, numActuel);
            } else {
                jouerTourIA(jeu, numActuel, iaActif);
            }
        }

        Joueur gagnant = jeu.estVictoire();
        System.out.println("\n Victoire de " + gagnant.getName() + " !");
        sc.close();
    }



    private static List<Couleur> creerCouleurs() {
        List<Couleur> couleurs = new ArrayList<>();
        couleurs.add(new Couleur("Bleu", 0, 0, 255));
        couleurs.add(new Couleur("Rouge", 255, 0, 0));
        couleurs.add(new Couleur("Vert", 0, 255, 0));
        couleurs.add(new Couleur("Jaune", 255, 255, 0));
        couleurs.add(new Couleur("Violet", 128, 0, 128));
        couleurs.add(new Couleur("Orange", 255, 165, 0));
        return couleurs;
    }



    private static Joueur creerJoueur(Scanner sc, int num) {
        System.out.print("Nom joueur " + num + " : ");
        String nom = sc.nextLine().trim();
        if (nom.isEmpty()) nom = (num == 1) ? "Joueur1" : "Joueur2";
        return new Joueur(nom);
    }

    private static StrategieIA choisirIA(Scanner sc, String label, Joueur j) {
        System.out.println("\nMode pour " + label + " (" + j.getName() + ") :");
        System.out.println("1) Humain");
        System.out.println("2) IA aléatoire");
        int choix = lireChoix(sc, 1, 2);

        if (choix == 1) return null;
        return new IAAleatoire();
    }



    private static void jouerTourHumain(Scanner sc, Jeu jeu, int numJoueur) {
        while (true) {
            Joueur j = (numJoueur == 1) ? jeu.getJoueur1() : jeu.getJoueur2();

            afficherMainAvecIndices(j.getHand());

            System.out.print("Index carte (0.." + (j.getHand().size() - 1) + ") ou 'q' : ");
            String s = sc.nextLine().trim();
            if (s.equalsIgnoreCase("q")) {
                System.out.println("Arrêt demandé. Bye.");
                System.exit(0);
            }

            Integer idxCarte = parseIntOrNull(s);
            if (idxCarte == null) {
                System.out.println("Entrée invalide.");
                continue;
            }

            System.out.print("Index borne (0..8) : ");
            Integer idxBorne = parseIntOrNull(sc.nextLine().trim());
            if (idxBorne == null) {
                System.out.println("Entrée invalide.");
                continue;
            }

            int choixPioche;
            if (jeu.isVarianteTactique()) {
                System.out.println("Choisir la pioche après le coup :");
                System.out.println("1) Pioche Clan");
                System.out.println("2) Pioche Tactique (Élites)");
                choixPioche = lireChoix(sc, 1, 2);
            } else {
                choixPioche = 1;
            }

            boolean ok = jeu.jouerCoup(idxBorne, idxCarte, choixPioche);
            if (!ok) {
                System.out.println(" Coup invalide (borne pleine/revendiquée, carte pas jouable, règle tactique...). Réessaie.");
                continue;
            }
            break;
        }
    }

    private static void jouerTourIA(Jeu jeu, int numJoueur, StrategieIA ia) {
        Coup coup = ia.choisirCoup(jeu, numJoueur);
        if (coup == null) {
            System.out.println("IA: aucun coup possible.");
            return;
        }
        System.out.println("IA joue carte #" + coup.indexCarte + " sur borne #" + coup.indexBorne);

        jeu.jouerCoup(coup.indexBorne, coup.indexCarte, 0);
    }


    private static void afficherEtat(Jeu jeu) {
        Joueur j1 = jeu.getJoueur1();
        Joueur j2 = jeu.getJoueur2();

        System.out.println("\n======================");
        System.out.println("Main " + j1.getName() + " : " + j1.getHand());
        System.out.println("Main " + j2.getName() + " : " + j2.getHand());

        System.out.println("\nBornes :");
        List<Borne> bornes = jeu.getBornes();
        for (Borne b : bornes) {
            String prop = (b.getProprietaire() == null) ? "-" : b.getProprietaire().getName();
            System.out.println("B" + b.getIndex()
                    + " | J1=" + b.getCartes(1)
                    + " | J2=" + b.getCartes(2)
                    + " | prop=" + prop);
        }
        System.out.println("======================");
    }

    private static void afficherMainAvecIndices(List<CarteMain> main) {
        System.out.println("Ta main :");
        for (int i = 0; i < main.size(); i++) {
            System.out.println("  [" + i + "] " + main.get(i));
        }
    }


    private static int lireChoix(Scanner sc, int min, int max) {
        while (true) {
            System.out.print("> ");
            Integer x = parseIntOrNull(sc.nextLine().trim());
            if (x == null || x < min || x > max) {
                System.out.println("Choix invalide. Entrez un nombre entre " + min + " et " + max + ".");
                continue;
            }
            return x;
        }
    }

    private static Integer parseIntOrNull(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
