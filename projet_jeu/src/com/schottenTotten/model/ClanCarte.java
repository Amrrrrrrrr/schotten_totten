package com.schottenTotten.model;

import java.util.Objects;

public class ClanCarte implements Comparable<ClanCarte> {

    private final int value;

    private final Couleur couleur;

    public ClanCarte(int value, Couleur couleur) {
        this.value = value;
        this.couleur = couleur;
    }

    public int getValue() {
        return value;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                        
        if (o == null || getClass() != o.getClass()) return false; 

        ClanCarte other = (ClanCarte) o;

        return value == other.value && Objects.equals(couleur, other.couleur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, couleur);
    }

    @Override
    public int compareTo(ClanCarte other) {
        if (this.value < other.value) {
            return -1;         
        }
        if (this.value > other.value) {
            return 1;          
        }

        String thisName = (this.couleur != null) ? this.couleur.getNom() : "";
        String otherName = (other.couleur != null) ? other.couleur.getNom() : "";

        return thisName.compareTo(otherName);
    }

}
