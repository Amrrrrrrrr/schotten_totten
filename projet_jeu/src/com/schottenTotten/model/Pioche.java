package com.schottenTotten.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pioche<T> {

    private List<T> cartes;

    public Pioche(List<T> cartes) {
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

    public T piocher() {
        if (estVide()) {
            return null;
        }
        return cartes.remove(0);
    }
}
