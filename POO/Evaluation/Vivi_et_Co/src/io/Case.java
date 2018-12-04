package io;
import gui.*;
import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;

/**
 * Contient les donnees des cases 
 */
public class Case {
  private int ligne;
  private int colonne;
  private NatureTerrain nature;

  /**
  Constructeur de la case
  */
  public Case(int l, int c, NatureTerrain n){
    this.ligne = l;
    this.colonne = c;
    this.nature = n;
  }

  /**
  * retourne la ligne de la case 
  */
  public int getLigne(){
    return this.ligne;
  }

  /**
   * retourne la colonne de la case 
   */
  public int getColonne(){
    return this.colonne;
  }

  /**
  * Retourne la nature de la case
  */
  public NatureTerrain getNature(){
       return this.nature;
  }

  @Override
  public String toString(){
    int lig = this.getLigne();
    int col = this.getColonne();
    return "(" + lig + "," + col + ")";
  }

  /**
  * Dessine la case
  */
  public void draw_case(GUISimulator gui, int taille_case){
      int x = taille_case/10 + (this.getLigne())* taille_case;
      int y =  taille_case/10 + (this.getColonne())* taille_case;
      int taille = taille_case;
      switch (this.getNature()){
          case EAU:
            gui.addGraphicalElement(new ImageElement(y, x, "img/EAU.jpg", taille, taille, new Canvas()));
            break;

          case FORET:
            gui.addGraphicalElement(new ImageElement(y, x, "img/FORET.png", taille, taille, new Canvas()));
            break;

          case ROCHE:
            gui.addGraphicalElement(new ImageElement(y, x, "img/ROCHE.jpg", taille, taille, new Canvas()));
            break;

          case HABITAT:
            gui.addGraphicalElement(new ImageElement(y, x, "img/HABITAT.jpg", taille, taille, new Canvas()));
            break;

          case TERRAIN_LIBRE:
            gui.addGraphicalElement(new ImageElement(y, x, "img/TERRAIN_LIBRE.jpg", taille, taille, new Canvas()));
            break;
      }

  }
}
