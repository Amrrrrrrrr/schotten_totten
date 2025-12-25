package com.schottenTotten.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BorneTest {


    private static class CarteCombatStub implements CarteCombat {
    private final String nom;

    CarteCombatStub(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean estTactique() {
        return false;
    }

    @Override
    public String afficher() {
        return nom;
    }

    @Override
    public ClanCarte enClanCarte() {
        return null; 
    }
}


    private static class JoueurStub extends Joueur {
        public JoueurStub(String nom) {
            super(nom);
        }
    }
    @Test
    void testConstructeur() {
        Borne b = new Borne(5);

        assertEquals(5, b.getIndex());
        assertFalse(b.estRevendiquee());
        assertEquals(0, b.getCartes(1).size());
        assertEquals(0, b.getCartes(2).size());
    }

    @Test
    void testSetEtGetProprietaire() {
        Borne b = new Borne(1);
        Joueur j = new JoueurStub("Alice");

        b.setProprietaire(j);

        assertTrue(b.estRevendiquee());
        assertEquals(j, b.getProprietaire());
    }

    @Test
    void testAddCarteLimite() {
        Borne b = new Borne(1);

        b.addCarte(1, new CarteCombatStub("C1"));
        b.addCarte(1, new CarteCombatStub("C2"));
        b.addCarte(1, new CarteCombatStub("C3"));
        b.addCarte(1, new CarteCombatStub("C4")); 

        assertEquals(3, b.getCartes(1).size());
        assertTrue(b.isFull(1));
    }

    @Test
    void testAddCarteBorneRevendiquee() {
        Borne b = new Borne(1);
        b.setProprietaire(new JoueurStub("Bob"));

        b.addCarte(1, new CarteCombatStub("C1"));

        assertEquals(0, b.getCartes(1).size());
    }

    @Test
    void testSetMaxCartesParCote() {
        Borne b = new Borne(1);

        b.setMaxCartesParCote(5);

        assertEquals(5, b.getMaxCartesParCote());
    }

    @Test
    void testSetMaxCartesParCoteInvalide() {
        Borne b = new Borne(1);

        assertThrows(IllegalArgumentException.class,
                () -> b.setMaxCartesParCote(0));
    }

    @Test
    void testAfficherCartes() {
        Borne b = new Borne(1);

        b.addCarte(2, new CarteCombatStub("A"));
        b.addCarte(2, new CarteCombatStub("B"));

        assertEquals("[A, B]", b.afficherCartes(2));
    }
}
