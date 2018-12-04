package io;
import gui.*;
import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;
import gui.Text;

/**
Un incendie est defini par sa position et par son intensite (nb de litres d'eau
necessaires pour l'eteindre)
*/
public class Incendie{
  private int ligne;
  private int colonne;
  private double nb_litres_extinction;

/**
 * Constructeur de la classe
 */
  public Incendie(int lig, int col, double nbl){
    this.ligne = lig;
    this.colonne = col;
    this.nb_litres_extinction = nbl;
  }

/**
 * retourne la ligne de l'incendie
 */
  public int getLigne(){
      return this.ligne;
  }

/**
 * retourne la colonne de l'incendie
 */
  public int getColonne(){
      return this.colonne;
  }

/**
 * retourne l'intensite de l'intensite
 */
  public double getIntensite(){
      return this.nb_litres_extinction;
  }

/**
 * setter de l'intensite
 */
  public void setIntensite(double intensite){
      this.nb_litres_extinction = intensite;
  }

/**
 * dessine l'incendie dans l'interface graphique
 */
  public void draw_incendie(GUISimulator gui, int taille_case){
      if (this.getIntensite() == 0){
        return;
      }
      int x = taille_case/5 + (this.getLigne())* taille_case;
      int y =  taille_case/5 + (this.getColonne())* taille_case;
      int taille = 4 * taille_case/5;
      gui.addGraphicalElement(new ImageElement(y, x, "img/flammes.png", taille, taille, new Canvas()));

  }

  public String toString(){
      return "(" + this.ligne + ", " + this.colonne + ", " + this.nb_litres_extinction + ")";
  }

}
