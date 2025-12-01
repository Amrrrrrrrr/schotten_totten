package com.schottenTotten.model;

import java.util.ArrayList;
import java.util.List;

public class Borne {
	private int index;
	private List<ClanCarte> cartesJoueur1;
	private List<ClanCarte> cartesJoueur2;
    private Joueur proprietaire;
    private int maxCartesParCote = 3; 
    
    public Borne(int index) {
        this.index = index;
        this.cartesJoueur1 = new ArrayList<>();
        this.cartesJoueur2 = new ArrayList<>();
        this.proprietaire = null;
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
    
    public void addCarte(int numJoueur, ClanCarte c) {
        if (numJoueur == 1) {
            if (cartesJoueur1.size() < maxCartesParCote) {
                cartesJoueur1.add(c);
            }
        } else if (numJoueur == 2) {
            if (cartesJoueur2.size() < maxCartesParCote) {
                cartesJoueur2.add(c);
            }
        }
    }
    
    public List<ClanCarte> getCartes(int numJoueur) {
        if (numJoueur == 1) {
            return cartesJoueur1;
        } else {
            return cartesJoueur2;
        }
    }
    
    public boolean isFull(int numJoueur) {
        if (numJoueur == 1) {
            return cartesJoueur1.size() == maxCartesParCote;
        } else {
            return cartesJoueur2.size() == maxCartesParCote;
        }
    }

    



}
