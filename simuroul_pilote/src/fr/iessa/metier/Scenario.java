package fr.iessa.metier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;
import java.util.Map.Entry;

import javax.swing.JFrame;

/** Class Scenario : Traitement des fichiers textes décrivant les démonstrations/scénarios pour pouvoir 
 * les afficher dans le LabelScenario
 * @author DONG Justine 
 * @version 1.0
 * 
 */



public class Scenario  {

	private String nomScenario;
	private HashMap <Integer, String> dialogue = new HashMap<Integer,String>();
	private boolean indelay;
	private int compteur;
	private int dernierDialogue;
	private final int delay = 3;
	private int debut;
	private int x_initial;
	private int y_initial;

	public Scenario(){
	}

	public int getX_initial() {
		return x_initial;
	}

	public void setX_initial(int x_initial) {
		this.x_initial = x_initial;
	}

	public int getY_initial() {
		return y_initial;
	}

	public void setY_initial(int y_initial) {
		this.y_initial = y_initial;
	}
	
	public int getDebut(){
		return debut;
	}

	public void setDebut(int debut) {
		this.debut = debut;
	}

	private void convertirScenario(String nomScenario) throws IOException{

		String ligne;
		String[] element;

		File fichier = new File(nomScenario);
		BufferedReader entree = new BufferedReader(new FileReader (fichier));

		debut = Integer.parseInt(entree.readLine())-1;
		x_initial=Integer.parseInt(entree.readLine());
		y_initial=Integer.parseInt(entree.readLine());

		dialogue.clear();
		do {
			// on lit une ligne 
			ligne=entree.readLine();


			if (ligne!=null){
				// traitement de la ligne

				element = ligne.split(":");

				int cle = debut+Integer.parseInt(element[0]); 
				String valeur = element[1];
				dialogue.put(cle,valeur);			
			}

		} while (ligne!=null);

		entree.close();	
	}


	public void preparationScenario(String numeroScenario){

		try {
			convertirScenario(numeroScenario);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String afficherParole(int i){

		String parole = " ";

		if( compteur != 0 ){
			compteur--;
			indelay = true;
		}else{
			indelay = false;
		}

		if(dialogue.containsKey(i)){
			dernierDialogue = i;
			compteur = delay;
			parole = dialogue.get(i);

		}else if( indelay ){
			parole = dialogue.get(dernierDialogue);
		}
		
		return parole;
		}	
}






