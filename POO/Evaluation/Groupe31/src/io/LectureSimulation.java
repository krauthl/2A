package io;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.zip.DataFormatException;

import travail.Case;
import travail.ComparateurDate;
import travail.DonneesSimulation;
import travail.Robot;
import travail.Evenement;
import travail.EvenementDeplacer;
import travail.EvenementRemplir;
import travail.EvenementVerserEau;
import travail.CalculeChemin;
import travail.Carte;

public class LectureSimulation {
	
	private static Scanner scanner;
	
	private static  String string ="";
	 
	public static  SortedSet<Evenement> lire(String fichierDonnees, DonneesSimulation donnees) 
			 throws FileNotFoundException, DataFormatException {
		 
		 new LectureSimulation(fichierDonnees);
		 SortedSet<Evenement> evenements = new TreeSet<Evenement>(new ComparateurDate());
		 long date;
		 Evenement evenement ; 
		 Case casePred = null; // pour prendre en compte la case precedente pour trouver si une case est voisine de maCase
		 while(scanner.hasNext()) {
			 try {
				 Robot robot = donnees.getRobot()[scanner.nextInt()-1];
				 
				 date = scanner.nextLong();
				 
				 
				 //lecture d'un String d'evenement
				 Case maCase = parseDonnees(scanner.nextLine());
				 System.out.println("LectureSumulation maCase: " + maCase);
				 
				 System.out.println("LectureSimulation string: "+string);
				 switch(string) {
				 		         	
				 	case "D":
				 		// une case voisine est voisine de la case precedente lue dans le fichier txt des evenements
				 		if((casePred == null && donnees.getCarte().sonVoisin(robot.getPosition(),maCase)) || donnees.getCarte().sonVoisin(casePred,maCase)) {
				 			double tempsDeplacement = robot.tempsDeplacementVoisin(donnees.getCarte().getTailleCases());
				 			evenement = new EvenementDeplacer(date,robot, maCase, tempsDeplacement);
				 			evenements.add(evenement);
				 		}else {
				 			CalculeChemin chemin = new CalculeChemin(robot);
				 		// calcul de chemin optimal et rajout des evenements elementaires dans la liste d'evt
				 			chemin.CalculeRoute(robot.getPosition(), maCase, donnees.getCarte());
				 			chemin.calculDeplacements(date, evenements, donnees.getCarte().getTailleCases());
				 		}
				 		

				 		break;
				 	case "R":
				 		evenement = new EvenementRemplir(date, robot);
				 		evenements.add(evenement);
				 		
				 		break;
				 	case "V":
				 		// FIXME j'ai mis un numero 1000 au pif!
				 		evenement = new EvenementVerserEau(date, robot, 1000);
				 		evenements.add(evenement);
				 		break;
				 	default:
				 		 throw new DataFormatException("Format invalide. "
				                    + "evenement Non Reconu");
				 		
				 }
			     casePred = maCase;
			 }catch (NoSuchElementException e) {
		            throw new DataFormatException("Format invalide. "
		                    + "Attendu: Date Evenement (Saut de ligne)");
		     }
		 }
		 System.out.println("LectureSimulation: nombre d'evenements "+ evenements.size());
		 return evenements;
	 }
	 private static Case parseDonnees(String nextLine)  {
		int position = 1 ;
		char car = nextLine.charAt(0);
		String lig ="";
		String col ="";
		Case maCase = null;
		nextLine+='\n';
		string = "";
		while(car == ' ' && car !='\n') {
			car = nextLine.charAt(position);
			
			position++;
		}
		//lecture du type d'evenement
		while(car != '\n' && car != ' ' ) {
			string += car;
			car = nextLine.charAt(position);
			position++;
		}
		
		//lecture colonne
		while(car == ' ' && car !='\n') {
			car = nextLine.charAt(position);
			
			position++;
		}
		while(car != ' ' && car !='\n') {
			lig += car;
			car = nextLine.charAt(position);
			position++;
		}
		
		
		while(car == ' ' && car !='\n') {
			car = nextLine.charAt(position);
			
			position++;
		}
		while(car != ' ' && car !='\n') {
			col += car;
			car = nextLine.charAt(position);
			position++;
		}
		if(lig != "" && col != "") {
			maCase = new Case(Integer.parseInt(lig),Integer.parseInt(col));
		}
		
		return maCase;
		
	}
	private LectureSimulation(String fichierDonnees)
		        throws FileNotFoundException {
		        scanner = new Scanner(new File(fichierDonnees));
		        scanner.useLocale(Locale.US);
		    }
}
