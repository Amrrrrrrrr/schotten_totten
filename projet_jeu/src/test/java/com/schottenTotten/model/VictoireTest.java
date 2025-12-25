package com.schottenTotten.test;

import com.schottenTotten.controller.Jeu;
import com.schottenTotten.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class VictoireTest {

    @Test
    public void testVictoireParTroisBornesAdjacentes() {
        Joueur j1 = new Joueur("Alice");
        Joueur j2 = new Joueur("Bob");

        Jeu jeu = new Jeu(j1, j2, false); // Pas de cartes tactiques
        jeu.demarrer();

        List<Borne> bornes = jeu.getBornes();

        for (int i = 0; i < 3; i++) {
            Borne b = bornes.get(i);
            Couleur couleur = new Couleur("Vert", 0, 255, 0);

            b.addCarte(1, new ClanCarte(5, couleur));
            b.addCarte(1, new ClanCarte(6, couleur));
            b.addCarte(1, new ClanCarte(7, couleur));

            b.addCarte(2, new ClanCarte(2, couleur));
            b.addCarte(2, new ClanCarte(3, couleur));
            b.addCarte(2, new ClanCarte(4, couleur));

            jeu.reclamer(1, b);
        }

        assertEquals(3, j1.getNombreBorneAcquises(), "J1 doit avoir 3 bornes acquises");

        assertEquals(j1, jeu.estVictoire(), "J1 doit être déclaré vainqueur");
    }

    @Test
public void testVictoireParMajoriteBornes() {
    Joueur j1 = new Joueur("Alice");
    Joueur j2 = new Joueur("Bob");

    Jeu jeu = new Jeu(j1, j2, false);
    jeu.demarrer();

    List<Borne> bornes = jeu.getBornes();
    Couleur couleur = new Couleur("Jaune", 255, 255, 0);

    int[] indices = {0, 2, 4, 6, 8};
    for (int idx : indices) {
        Borne b = bornes.get(idx);
        b.addCarte(1, new ClanCarte(5, couleur));
        b.addCarte(1, new ClanCarte(6, couleur));
        b.addCarte(1, new ClanCarte(7, couleur));

        b.addCarte(2, new ClanCarte(2, couleur));
        b.addCarte(2, new ClanCarte(3, couleur));
        b.addCarte(2, new ClanCarte(4, couleur));

        jeu.reclamer(1, b);
    }

    assertEquals(5, j1.getNombreBorneAcquises(), "J1 doit avoir 5 bornes acquises");

    assertEquals(j1, jeu.estVictoire(), "J1 doit être déclaré vainqueur par majorité de bornes");
}

}
