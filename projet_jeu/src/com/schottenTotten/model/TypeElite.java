package com.schottenTotten.model;

import java.util.Objects;

public final class TypeElite {

    public static final TypeElite JOKER = new TypeElite("Joker");
    public static final TypeElite ESPION = new TypeElite("Espion");
    public static final TypeElite PORTE_BOUCLIER = new TypeElite("Porte-Bouclier");

    private final String nom;

    private TypeElite(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeElite)) return false;
        TypeElite other = (TypeElite) o;
        return Objects.equals(nom, other.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}
