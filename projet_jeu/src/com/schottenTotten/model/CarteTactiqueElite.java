package com.schottenTotten.model;

public class CarteTactiqueElite implements CarteMain, CarteCombat {

    private final TypeElite type;

    private Integer valeurChoisie;
    private Couleur couleurChoisie;

    public CarteTactiqueElite(TypeElite type) {
        if (type == null) throw new IllegalArgumentException("type elite null");
        this.type = type;
    }

    public TypeElite getTypeElite() {
        return type;
    }

    public boolean estResolue() {
        return valeurChoisie != null && couleurChoisie != null;
    }


    public void choisir(Couleur couleur, int valeur) {
        if (couleur == null) throw new IllegalArgumentException("couleur null");

        if (type == TypeElite.JOKER) {
            if (valeur < 1 || valeur > 9) throw new IllegalArgumentException("JOKER: valeur 1..9");
        } else if (type == TypeElite.ESPION) {
            if (valeur != 7) throw new IllegalArgumentException("ESPION: valeur obligatoirement 7");
        } else if (type == TypeElite.PORTE_BOUCLIER) {
            if (valeur < 1 || valeur > 3) throw new IllegalArgumentException("PORTE_BOUCLIER: valeur 1..3");
        } else {
            throw new IllegalStateException("TypeElite inconnu: " + type);
        }

        this.couleurChoisie = couleur;
        this.valeurChoisie = valeur;
    }

    @Override
    public ClanCarte enClanCarte() {
        if (!estResolue()) return null;
        return new ClanCarte(valeurChoisie, couleurChoisie);
    }

    @Override
    public boolean estTactique() {
        return true;
    }

    @Override
    public String afficher() {
        if (!estResolue()) {
            return "Elite(" + type.getNom() + ": ?)";
        }
        return "Elite(" + type.getNom() + ":" + valeurChoisie + "-" + couleurChoisie.getName() + ")";
    }

    @Override
    public String toString() {
        return afficher();
    }
}
