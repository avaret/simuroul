package fr.iessa.metier.infra;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/** Class BarreArret qui permet de creer une Barre d'Arret, d'afficher celle-ci, de la positionner et son etat.
 * c'est fonction seront accessible par un clic droit sous forme de liste.
 * @author GINEYS Christophe 
 * @version 1.0 
 */

public class StopBar{

	/** Attributs */
	private boolean allumer=true; // choix à faire d'allumer ou etendre la BA
	private boolean permanent =true;
	private final int nb =3; //Nombre de cercles à definir selon le zoom
	private int rayon = 10;
	private int x0;  
	private int y0;

	private JMenuItem etat_barre_Arret ; 

	

	/** constructeur */
	public StopBar (int x, int y){
		setX0(x);
		setY0(y);
	//	System.out.println("Ref StopBar: x=" + x + " / y= " + y);
	/*	addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPopupMenu sous_menu_souris = new JPopupMenu();
				//selection la barre d'arret

				etat_barre_Arret = new JMenuItem("allumer /eteindre");
				sous_menu_souris.add(etat_barre_Arret);
			}
		});*/
	}


	public boolean isAllumer() {
		return allumer;
	}

	public void setAllumer(boolean allumer) {
		this.allumer = allumer;
	}

	public boolean isPermanent() {
		return permanent;
	}

	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}

	public int getRayon() {
		return rayon;
	}

	public void setRayon(int rayon) {
		this.rayon = rayon;
	}

	public int getNb() {
		return nb;
	}

	public int getX0() {
		return x0;
	}

	public void setX0(int x0) {
		this.x0 = x0;
	}

	public int getY0() {
		return y0;
	}

	public void setY0(int y0) {
		this.y0 = y0;
	}


	public double distance(Point2D.Double p_abs) {
		
		 double x1=0;
		 double y1=0;
		 
		 double distance;
		return  distance = Math.sqrt((x1-p_abs.getX())+(y1-p_abs.getY()));
		
		 
	}
}
