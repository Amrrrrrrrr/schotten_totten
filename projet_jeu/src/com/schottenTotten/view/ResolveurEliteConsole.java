package com.schottenTotten.view;

import java.util.List;
import java.util.Scanner;

import com.schottenTotten.controller.ResolveurElite;
import com.schottenTotten.model.*;

public class ResolveurEliteConsole implements ResolveurElite {

    private final Scanner sc;
    private final List<Couleur> couleurs;

    public ResolveurEliteConsole(Scanner sc, List<Couleur> couleurs) {
        this.sc = sc;
        this.couleurs = couleurs;
    }

    @Override
    public void resoudre(CarteTactiqueElite elite, int numJoueur, Borne borne) {
        if (elite.estResolue()) return;

        System.out.println("\n[Résolution Elite] Joueur " + numJoueur + " sur borne B" + borne.getIndex());
        System.out.println("Carte : " + elite);

        // choix couleur
        System.out.println("Choisir couleur :");
        for (int i = 0; i < couleurs.size(); i++) {
            System.out.println((i + 1) + ") " + couleurs.get(i).getName());
        }
        int choixC = lireChoix(1, couleurs.size());
        Couleur couleur = couleurs.get(choixC - 1);

        // choix valeur selon type
        int valeur;
        if (elite.getTypeElite().equals(TypeElite.ESPION)) {
            valeur = 7;
            System.out.println("ESPION => valeur imposée : 7");
        } else if (elite.getTypeElite().equals(TypeElite.PORTE_BOUCLIER)) {
            System.out.print("Valeur (1..3) : ");
            valeur = lireChoix(1, 3);
        } else { // JOKER
            System.out.print("Valeur (1..9) : ");
            valeur = lireChoix(1, 9);
        }

        elite.choisir(couleur, valeur);
        System.out.println("✅ Elite résolue : " + elite);
    }

    private int lireChoix(int min, int max) {
        while (true) {
            System.out.print("> ");
            String s = sc.nextLine().trim();
            try {
                int x = Integer.parseInt(s);
                if (x < min || x > max) {
                    System.out.println("Choix invalide (" + min + ".." + max + ")");
                    continue;
                }
                return x;
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide.");
            }
        }
    }
}
