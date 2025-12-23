package com.schottenTotten.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.schottenTotten.model.ClanCarte;
import com.schottenTotten.model.ResultatCombinaison;
import com.schottenTotten.model.TypeCombinaison;

public class EvaluateurCombinaison {


    public ResultatCombinaison evaluer(List<ClanCarte> cartes) {
        if (cartes == null || cartes.size() != 3) {
            throw new IllegalArgumentException("evaluer() attend exactement 3 cartes.");
        }

   
        List<ClanCarte> cards = new ArrayList<>(cartes);

        boolean flush = estCouleur(cards);
        boolean straight = estSuite(cards);
        boolean brelan = estBrelan(cards);

        int sum = somme(cards);

        TypeCombinaison type;
        if (straight && flush) {
            type = TypeCombinaison.SUITE_COULEUR;
        } else if (brelan) {
            type = TypeCombinaison.BRELAN;
        } else if (straight) {
            type = TypeCombinaison.SUITE;
        } else if (flush) {
            type = TypeCombinaison.COULEUR;
        } else {
            type = TypeCombinaison.SOMME;
        }

        return new ResultatCombinaison(type, sum, cards);
    }

    public int comparer(ResultatCombinaison r1, ResultatCombinaison r2) {
        if (r1 == null || r2 == null) {
            throw new IllegalArgumentException("comparer() ne peut pas comparer null.");
        }
        return r1.compareTo(r2);
    }

  

    private int somme(List<ClanCarte> cards) {
        int s = 0;
        for (ClanCarte c : cards) {
            s += c.getValue(); 
        }
        return s;
    }

    private boolean estCouleur(List<ClanCarte> cards) {
        return cards.get(0).getCouleur().equals(cards.get(1).getCouleur())
            && cards.get(0).getCouleur().equals(cards.get(2).getCouleur());
    }

    private boolean estBrelan(List<ClanCarte> cards) {
        int v0 = cards.get(0).getValue();
        int v1 = cards.get(1).getValue();
        int v2 = cards.get(2).getValue();
        return v0 == v1 && v1 == v2;
    }

    private boolean estSuite(List<ClanCarte> cards) {
        List<Integer> vals = new ArrayList<>();
        for (ClanCarte c : cards) {
            vals.add(c.getValue());
        }
        Collections.sort(vals);

        return vals.get(0) + 1 == vals.get(1) && vals.get(1) + 1 == vals.get(2);
    }
}
