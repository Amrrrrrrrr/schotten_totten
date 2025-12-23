package com.schottenTotten.model;

public interface CarteMain {

    // Par défaut : une carte de main n'est pas tactique
    default boolean estTactique() {
        return false;
    }

    // Par défaut : ce que tu affiches en console
    default String afficher() {
        return toString();
    }
}
