package com.schottenTotten.model;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
	private String nom;
	private List<ClanCarte> main;
	private List<Borne> borneAcquises;
	
	public Joueur(String nom) {
		this.nom = nom;
		this.main = new ArrayList<>();
		this.borneAcquises = new ArrayList<>();
	}
	public String getName() {
		return nom;
	}
	public List<ClanCarte> getHand(){
		return main;
	}
	public void addCardToHand(ClanCarte c) {
		if(c != null) {
			main.add(c);
		}
	}
	public boolean removeCardFromHand(ClanCarte c) {
		return main.remove(c);
	}
	public void addBorneAcquise(Borne b) {
		if( b != null) {
			borneAcquises.add(b);
		}
	}
	public int getNombreBorneAcquises() {
		return borneAcquises.size();
	}
	
}
