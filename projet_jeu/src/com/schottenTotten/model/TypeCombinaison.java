package com.schottenTotten.model;

public class TypeCombinaison implements Comparable<TypeCombinaison> {

    private final String nom;

    private TypeCombinaison(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return nom;
    }
    // SOMME < SUITE < COULEUR < BRELANT < SUITE_COULEUR

    public static final TypeCombinaison SOMME =
            new TypeCombinaison("SOMME");

    public static final TypeCombinaison SUITE =
            new TypeCombinaison("SUITE");

    public static final TypeCombinaison COULEUR =
            new TypeCombinaison("COULEUR");

    public static final TypeCombinaison BRELAN =
            new TypeCombinaison("BRELAN");

    public static final TypeCombinaison SUITE_COULEUR =
            new TypeCombinaison("SUITE_COULEUR");

    @Override
    public int compareTo(TypeCombinaison other) {
        if (this == other) {
            return 0;
        }

        if (this == SUITE_COULEUR) return 1;
        if (other == SUITE_COULEUR) return -1;

        if (this == BRELAN) return 1;
        if (other == BRELAN) return -1;

        if (this == COULEUR) return 1;
        if (other == COULEUR) return -1;

        if (this == SUITE) return 1;
        if (other == SUITE) return -1;

        throw new IllegalArgumentException("TypeCombinaison inconnu : " + this + " / " + other);
    }
}
