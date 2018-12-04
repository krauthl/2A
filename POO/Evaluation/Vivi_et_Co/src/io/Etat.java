package io;
import io.*;

/**
 * Classe qui contient les etats possibles des robots
 */
public enum Etat {
  OCCUPE ("OCCUPE"),
  LIBRE ("LIBRE"),
  INUTILE ("INUTILE");

  private String name = "";

  /**
  Constructeur
  */
  Etat(String name){
    this.name = name;
  }

  @Override
  public String toString(){
    return name;
  }
}
