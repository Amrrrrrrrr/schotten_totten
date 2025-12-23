package com.schottenTotten.model;

public interface CarteMain {

    default boolean estTactique() {
        return false;
    }

    default String afficher() {
        return toString();
    }
}
