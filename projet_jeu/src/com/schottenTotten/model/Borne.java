package com.schottenTotten.model;

import java.util.ArrayList;
import java.util.List;

public class Borne {
    private final int index;

    private final List<CarteCombat> cartesJoueur1;
    private final List<CarteCombat> cartesJoueur2;

    private Joueur proprietaire;

    private int maxCartesParCote = 3;

    public Borne(int index) {
        this.index = index;
        this.cartesJoueur1 = new ArrayList<>();
        this.cartesJoueur2 = new ArrayList<>();
        this.proprietaire = null;
    }

    public int getIndex() {
        return index;
    }

    public Joueur getProprietaire() {
        return proprietaire;
    }

    public boolean estRevendiquee() {
        return proprietaire != null;
    }

    public void setProprietaire(Joueur j) {
        this.proprietaire = j;
    }


    public int getMaxCartesParCote() {
        return maxCartesParCote;
    }

    public void setMaxCartesParCote(int max) {
        if (max < 1) throw new IllegalArgumentException("maxCartesParCote < 1");
        this.maxCartesParCote = max;
    }

    public void addCarte(int numJoueur, CarteCombat c) {
        if (c == null) return;
        if (estRevendiquee()) return; 

        if (numJoueur == 1) {
            if (cartesJoueur1.size() < maxCartesParCote) cartesJoueur1.add(c);
        } else if (numJoueur == 2) {
            if (cartesJoueur2.size() < maxCartesParCote) cartesJoueur2.add(c);
        }
    }

    public List<CarteCombat> getCartes(int numJoueur) {
        return (numJoueur == 1) ? cartesJoueur1 : cartesJoueur2;
    }

    public boolean isFull(int numJoueur) {
        return getCartes(numJoueur).size() >= maxCartesParCote;
    }


    public String afficherCartes(int numJoueur) {
        StringBuilder sb = new StringBuilder("[");
        List<CarteCombat> list = getCartes(numJoueur);
        for (int i = 0; i < list.size(); i++) {
            CarteCombat c = list.get(i);
            sb.append(c == null ? "null" : c.afficher());
            if (i < list.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
