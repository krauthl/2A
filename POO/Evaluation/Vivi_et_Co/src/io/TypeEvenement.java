package io;

/** 
 * Classe contenants tous les types d'evenements possibles 
 */
public enum TypeEvenement {
  DeverserEau ("DeverserEau"),
  deplacement ("deplacement"),
  remplirReservoir ("remplirReservoir");

  private String name = "";

  /**
  Constructeur
  */
  TypeEvenement(String name){
    this.name = name;
  }

  public String toString(){
    return name;
  }
}
