package com.schottenTotten.model;

import java.util.ArrayList;
import java.util.List;

public final class FabriqueTactique {

    private FabriqueTactique() {

    }

    /**
     * Pioche Tactique :
     * - 2 Jokers
     * - 1 Espion
     * - 1 Porte-Bouclier
     */
    public static List<CarteTactiqueElite> creerPiocheElites() {
        List<CarteTactiqueElite> elites = new ArrayList<>();

        elites.add(new CarteTactiqueElite(TypeElite.JOKER));
        elites.add(new CarteTactiqueElite(TypeElite.JOKER));
        elites.add(new CarteTactiqueElite(TypeElite.ESPION));
        elites.add(new CarteTactiqueElite(TypeElite.PORTE_BOUCLIER));

        return elites;
    }
}
