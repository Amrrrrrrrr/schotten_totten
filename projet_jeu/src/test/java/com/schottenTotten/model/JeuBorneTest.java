package com.schottenTotten.test;

import com.schottenTotten.controller.Jeu;
import com.schottenTotten.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class JeuBorneTest {

    @Test
    public void testRevendicationSimple() {
        Joueur j1 = new Joueur("Alice");
        Joueur j2 = new Joueur("Bob");

        // Partie classique (pas de cartes tactiques)
        Jeu jeu = new Jeu(j1, j2, false);
        jeu.demarrer();

        // On récupère la première borne
        Borne borne = jeu.getBornes().get(0);

        // Remplissons la borne pour les deux joueurs avec des cartes simples
        Couleur couleur = new Couleur("Bleu", 0, 0, 255);

        // Ajouter cartes J1
        borne.addCarte(1, new ClanCarte(5, couleur));
        borne.addCarte(1, new ClanCarte(6, couleur));
        borne.addCarte(1, new ClanCarte(7, couleur));

        // Ajouter cartes J2
        borne.addCarte(2, new ClanCarte(2, couleur));
        borne.addCarte(2, new ClanCarte(3, couleur));
        borne.addCarte(2, new ClanCarte(4, couleur));

        // Vérifions que J1 peut revendiquer
        assertTrue(jeu.peutReclamer(1, borne), "J1 doit pouvoir revendiquer la borne");
        assertFalse(jeu.peutReclamer(2, borne), "J2 ne peut pas encore revendiquer");

        // On revendique
        jeu.reclamer(1, borne);

        assertEquals(j1, borne.getProprietaire(), "Propriétaire doit être J1");
        assertEquals(1, j1.getNombreBorneAcquises(), "J1 doit avoir 1 borne acquise");

                // Après revendication, la borne est bloquée pour tous
        assertTrue(borne.isFull(1), "La borne est bloquée pour J1 après revendication");
        assertTrue(borne.isFull(2), "La borne est bloquée pour J2 après revendication");

    }

    @Test
    public void testRevendicationNonComplète() {
        Joueur j1 = new Joueur("Alice");
        Joueur j2 = new Joueur("Bob");

        Jeu jeu = new Jeu(j1, j2, false);
        jeu.demarrer();

        Borne borne = jeu.getBornes().get(1);

        Couleur couleur = new Couleur("Rouge", 255, 0, 0);

        // Ajouter cartes J1 partiellement
        borne.addCarte(1, new ClanCarte(5, couleur));
        borne.addCarte(1, new ClanCarte(6, couleur));

        // Ajouter cartes J2 partiellement
        borne.addCarte(2, new ClanCarte(2, couleur));

        // Aucun joueur ne peut revendiquer car la borne n'est pas pleine
        assertFalse(jeu.peutReclamer(1, borne), "J1 ne peut pas revendiquer borne incomplète");
        assertFalse(jeu.peutReclamer(2, borne), "J2 ne peut pas revendiquer borne incomplète");
    }

    @Test
    public void testRevendicationSuiteBorne() {
        Joueur j1 = new Joueur("Alice");
        Joueur j2 = new Joueur("Bob");

        Jeu jeu = new Jeu(j1, j2, false);
        jeu.demarrer();

        List<Borne> bornes = jeu.getBornes();

        // Remplissons 3 bornes adjacentes pour J1 pour déclencher la victoire
        for (int i = 0; i < 3; i++) {
            Borne b = bornes.get(i);
            Couleur couleur = new Couleur("Vert", 0, 255, 0);

            // Cartes complètes
            b.addCarte(1, new ClanCarte(5, couleur));
            b.addCarte(1, new ClanCarte(6, couleur));
            b.addCarte(1, new ClanCarte(7, couleur));

            b.addCarte(2, new ClanCarte(2, couleur));
            b.addCarte(2, new ClanCarte(3, couleur));
            b.addCarte(2, new ClanCarte(4, couleur));

            // Revendiquer chaque borne
            jeu.reclamer(1, b);
        }

        // Vérifions que J1 a bien trois bornes acquises
        assertEquals(3, j1.getNombreBorneAcquises(), "J1 doit avoir 3 bornes acquises");

        // Vérifions que la victoire par 3 bornes adjacentes fonctionne
        assertEquals(j1, jeu.estVictoire(), "J1 doit être déclaré vainqueur");
    }
}
