package com.schottenTotten.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CarteMainTest {

    // Stub minimal pour tester les méthodes default
    private static class CarteMainStub implements CarteMain {
        @Override
        public String toString() {
            return "CarteTest";
        }
    }

    @Test
    void testEstTactiqueParDefaut() {
        CarteMain carte = new CarteMainStub();
        assertFalse(carte.estTactique(), "estTactique() doit retourner false par défaut");
    }

    @Test
    void testAfficherParDefaut() {
        CarteMain carte = new CarteMainStub();
        assertEquals("CarteTest", carte.afficher(),
                "afficher() doit retourner le toString() par défaut");
    }
}
