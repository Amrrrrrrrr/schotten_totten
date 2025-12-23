package com.schottenTotten.ai;

import com.schottenTotten.controller.Jeu;

public interface StrategieIA {
    Coup choisirCoup(Jeu jeu, int numJoueur);
}
