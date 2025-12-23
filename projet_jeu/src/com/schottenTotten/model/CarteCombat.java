package com.schottenTotten.model;

public interface CarteCombat {
    ClanCarte enClanCarte();
    boolean estTactique();

    default String afficher() {
        return String.valueOf(this);
    }
}

