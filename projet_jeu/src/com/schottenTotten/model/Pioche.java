package com.schottenTotten.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pioche {

    private List<ClanCarte> cartes;

    public Pioche(List<ClanCarte> cartes) {
        this.cartes = new ArrayList<>(cartes);
    }

    public void melanger() {
        Collections.shuffle(cartes);
    }

    public boolean estVide() {
        return cartes.isEmpty();
    }

    public int taille() {
        return cartes.size();
    }

    public ClanCarte piocher() {
        if (estVide()) {
            return null; 
        }
        return cartes.remove(0); 
    }
}
