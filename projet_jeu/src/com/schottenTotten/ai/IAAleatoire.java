package com.schottenTotten.ai;

import java.util.List;
import java.util.Random;

import com.schottenTotten.controller.Jeu;

public class IAAleatoire implements StrategieIA {

    private final Random rnd = new Random();

    @Override
    public Coup choisirCoup(Jeu jeu, int numJoueur) {
        List<Coup> coups = jeu.coupsValides(numJoueur);
        if (coups.isEmpty()) return null;
        return coups.get(rnd.nextInt(coups.size()));
    }
}
