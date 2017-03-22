
package fr.iessa.vue;


import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.iessa.controleur.Controleur;
import fr.iessa.metier.infra.Point;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.metier.type.TypeVol;
import fr.iessa.vue.trafic.ComponentVol;

/** Class BarreArret qui permet de creer une Barre d'Arret, d'afficher celle-ci, de la positionner et son etat.
 * c'est fonction seront accessible par un clic droit sous forme de liste.
 * @author GINEYS Christophe 
 * @version 1.0 
 */

public class BarreArret extends JPanel{

	/** Attributs */
	

	 private boolean allumer=true; // choix à faire d'allumer ou etendre la BA
	 private final int nb =3; //Nombre de cercles à definir selon le zoom
	 int rayon = 10;
	 int x0;  
	 int y0;
	 
	public Point position_BA;
	
	//constructueur

	public BarreArret (int x, int y){
		x0 =x;
		y0=y;
		
		
	}
	

public void paintComponent(Graphics g)
 {
		
		int decalage_pixels_x_par_cercle=(int) (2*(((float)rayon)*1.2)); // decalage des ronds
		char[] lettre_BA = new char[1]; // variable pour afficher la lettre C ou P
		
		// barre d'arret permanente ou controlable
		boolean permanent =true;
		if (permanent==true)
		{
			// affiche le "P"
			allumer=true;
			lettre_BA[0] = 'P';
		}
		
		else
		{
			// affiche le "C"
			allumer=false; // BA non allumé afin de voir la difference avec la barre permanente
			lettre_BA[0] = 'C';
		}
		
		
		
		int yi = y0+50;
		
		// allumer ou pas la barre d'arret
		
		g.drawChars(lettre_BA, 0, 1, x0+decalage_pixels_x_par_cercle*nb+rayon, yi);
		
		int no=0; // variable pour indiquer le n° de cercle
		while(no<nb)
		{
			g.setColor(Color.BLACK);
			int xi = x0+decalage_pixels_x_par_cercle*no;
			
	
			CirclePanel.drawCircle(g, xi, yi, rayon);
			if (allumer == true)
			{
				g.setColor(Color.RED);
				CirclePanel.fillCircle(g, xi, yi, rayon);
			}
			
			
			//decalage_pixels_x_par_cercle+=10;
			no++;
					
		}
		
		
	
 }
 
 


	/*

public positionner(){
	//this.

	// TODO
}

public afficher(JPanel) implements ActionListener {
	// TODO

}
	 */

 
 
}