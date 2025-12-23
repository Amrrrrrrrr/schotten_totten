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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Couleur other = (Couleur) o;
        return r == other.r && g == other.g && b == other.b
                && java.util.Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, r, g, b);
    }

    @Override
    public String toString() {
        return name + "(" + r + "," + g + "," + b + ")";
    }
}
