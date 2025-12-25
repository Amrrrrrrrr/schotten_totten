package com.schottenTotten.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CouleurTest {

    @Test
    void testGetters() {
        Couleur vert = new Couleur("Vert", 0, 255, 0);
        assertEquals("Vert", vert.getName());
        assertEquals(0, vert.getR());
        assertEquals(255, vert.getG());
        assertEquals(0, vert.getB());
    }

    @Test
    void testEquals() {
        Couleur rouge1 = new Couleur("Rouge", 255, 0, 0);
        Couleur rouge2 = new Couleur("Rouge", 255, 0, 0);
        Couleur bleu = new Couleur("Bleu", 0, 0, 255);

        assertTrue(rouge1.equals(rouge2), "Deux couleurs identiques doivent être égales");
        assertFalse(rouge1.equals(bleu), "Deux couleurs différentes ne doivent pas être égales");
    }

    @Test
    void testToString() {
        Couleur orange = new Couleur("Orange", 255, 165, 0);
        String expected = "Orange(255,165,0)";
        assertEquals(expected, orange.toString());
    }
}
