package com.schottenTotten.controller;

import com.schottenTotten.model.Borne;
import com.schottenTotten.model.CarteTactiqueElite;

public interface ResolveurElite {
    void resoudre(CarteTactiqueElite elite, int numJoueur, Borne borne);
}
