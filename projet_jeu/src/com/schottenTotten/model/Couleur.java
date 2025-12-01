package com.schottenTotten.model;

public class Couleur {
	private int r;
	private int g;
	private int b;
	private String name;
	
    public Couleur(String name, int r, int g, int b) {
        this.name = name;
        this.r = r;
        this.g = g;
        this.b = b;
    }
    public String getName() {
    	return name;
    }
    public int getR() {
    	return r;
    }
    public int getG(){
    	return g;
    }
    public int getB() {
    	return b;
    }
    
    @Override
    public String toString() {
        return name + "(" + r + "," + g + "," + b + ")";
    }
}
