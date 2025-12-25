package com.schottenTotten.test;

import com.schottenTotten.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarteTactiqueEliteTest {

    @Test
    public void testCreationEtType() {
        CarteTactiqueElite joker = new CarteTactiqueElite(TypeElite.JOKER);
        assertEquals(TypeElite.JOKER, joker.getTypeElite());
        assertTrue(joker.estTactique());
        assertFalse(joker.estResolue());
        assertEquals("Elite(Joker: ?)", joker.afficher());
    }

    @Test
    public void testChoisirValide() {
        CarteTactiqueElite carte = new CarteTactiqueElite(TypeElite.JOKER);
        Couleur bleu = new Couleur("Bleu", 0, 0, 255);
        carte.choisir(bleu, 5);

        assertTrue(carte.estResolue());
        assertEquals("Elite(Joker:5-Bleu)", carte.afficher());

        ClanCarte clan = carte.enClanCarte();
        assertNotNull(clan);
        assertEquals(5, clan.getValue());
        assertEquals(bleu, clan.getCouleur());
    }

    @Test
    public void testChoisirValeursInvalides() {
        CarteTactiqueElite joker = new CarteTactiqueElite(TypeElite.JOKER);
        Couleur rouge = new Couleur("Rouge", 255, 0, 0);

        // Joker hors plage 1..9
        assertThrows(IllegalArgumentException.class, () -> joker.choisir(rouge, 0));
        assertThrows(IllegalArgumentException.class, () -> joker.choisir(rouge, 10));

        CarteTactiqueElite espion = new CarteTactiqueElite(TypeElite.ESPION);
        // Espion valeur différente de 7
        assertThrows(IllegalArgumentException.class, () -> espion.choisir(rouge, 5));

        CarteTactiqueElite bouclier = new CarteTactiqueElite(TypeElite.PORTE_BOUCLIER);
        // Porte-bouclier hors plage 1..3
        assertThrows(IllegalArgumentException.class, () -> bouclier.choisir(rouge, 0));
        assertThrows(IllegalArgumentException.class, () -> bouclier.choisir(rouge, 4));
    }

    @Test
    public void testEnClanCarteNonResolue() {
        CarteTactiqueElite carte = new CarteTactiqueElite(TypeElite.JOKER);
        assertNull(carte.enClanCarte(), "Carte non résolue doit retourner null");
    }

    @Test
    public void testFabriqueTactique() {
        List<CarteTactiqueElite> pioche = FabriqueTactique.creerPiocheElites();
        assertEquals(4, pioche.size(), "La pioche contient 4 cartes");
        assertEquals(TypeElite.JOKER, pioche.get(0).getTypeElite());
        assertEquals(TypeElite.JOKER, pioche.get(1).getTypeElite());
        assertEquals(TypeElite.ESPION, pioche.get(2).getTypeElite());
        assertEquals(TypeElite.PORTE_BOUCLIER, pioche.get(3).getTypeElite());
    }
}
