package io;

/**
 * Classe contenant les directions cardinales 
 */
public enum Direction {
    NORD ("NORD"),
    EST ("EST"),
    SUD ("SUD"),
    OUEST ("OUEST");

    private String name = "";

/**
 * Constructeur 
 */
    Direction(String nom){
        this.name = nom;
    }

    public String ToString(){
        return name;
    }
}
