package com.schottenTotten.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClanCarteTest {

    @Test
    public void testConstructeurEtGetters() {
        Couleur rouge = new Couleur("Rouge", 255, 0, 0);
        ClanCarte clan = new ClanCarte(5, rouge);

        assertEquals(5, clan.getValue(), "La valeur doit être 5");
        assertEquals(rouge, clan.getCouleur(), "La couleur doit être Rouge");
    }

    @Test
    public void testEqualsEtHashCode() {
        Couleur bleu = new Couleur("Bleu", 0, 0, 255);
        ClanCarte c1 = new ClanCarte(3, bleu);
        ClanCarte c2 = new ClanCarte(3, bleu);
        ClanCarte c3 = new ClanCarte(4, bleu);

        assertEquals(c1, c2, "Deux cartes avec même valeur et couleur doivent être égales");
        assertNotEquals(c1, c3, "Deux cartes avec valeur différente ne doivent pas être égales");

        assertEquals(c1.hashCode(), c2.hashCode(), "HashCode doit être identique pour des cartes égales");
    }

    @Test
    public void testCompareTo() {
        Couleur rouge = new Couleur("Rouge", 255, 0, 0);
        Couleur bleu = new Couleur("Bleu", 0, 0, 255);

        ClanCarte c1 = new ClanCarte(5, rouge);
        ClanCarte c2 = new ClanCarte(5, bleu);
        ClanCarte c3 = new ClanCarte(6, rouge);

        assertTrue(c1.compareTo(c2) > 0, "Rouge > Bleu si même valeur");
        assertTrue(c1.compareTo(c3) < 0, "5 < 6, donc compareTo < 0");
    }

    @Test
    public void testToStringEtAfficher() {
        Couleur vert = new Couleur("Vert", 0, 255, 0);
        ClanCarte clan = new ClanCarte(7, vert);

        String expected = "7-Vert";
        assertEquals(expected, clan.toString(), "toString doit retourner '7-Vert'");
        assertEquals(expected, clan.afficher(), "afficher doit retourner '7-Vert'");
    }

    @Test
    public void testEstTactiqueEtEnClanCarte() {
        Couleur jaune = new Couleur("Jaune", 255, 255, 0);
        ClanCarte clan = new ClanCarte(2, jaune);

        assertFalse(clan.estTactique(), "ClanCarte n'est jamais tactique");
        assertEquals(clan, clan.enClanCarte(), "enClanCarte retourne l'objet lui-même");
    }
}
