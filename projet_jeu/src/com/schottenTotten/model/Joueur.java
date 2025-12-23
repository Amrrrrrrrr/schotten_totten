package com.schottenTotten.model;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
    private String nom;
    private List<CarteMain> main;           // ✅ main mixte (Clan + Tactique)
    private List<Borne> borneAcquises;

    // ✅ tactique
    private int nbTactiquesJouees;
    private boolean jokerPose;

    public Joueur(String nom) {
        this.nom = nom;
        this.main = new ArrayList<>();
        this.borneAcquises = new ArrayList<>();
        this.nbTactiquesJouees = 0;
        this.jokerPose = false;
    }

    public String getName() {
        return nom;
    }

    public List<CarteMain> getHand() {
        return main;
    }

    public void addCardToHand(CarteMain c) {
        if (c != null) {
            main.add(c);
        }
    }

    public boolean removeCardFromHand(CarteMain c) {
        return main.remove(c);
    }

    public void addBorneAcquise(Borne b) {
        if (b != null) {
            borneAcquises.add(b);
        }
    }

    public int getNombreBorneAcquises() {
        return borneAcquises.size();
    }

    // ---------- Tactique : getters/setters ----------

    public int getNbTactiquesJouees() {
        return nbTactiquesJouees;
    }

    public void incrementTactiquesJouees() {
        nbTactiquesJouees++;
    }

    public boolean isJokerPose() {
        return jokerPose;
    }

    public void setJokerPose(boolean jokerPose) {
        this.jokerPose = jokerPose;
    }
}
