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
	private int nbCercle =3;
	private int rayonCercle =10;
	private boolean allumer=true; // choix à faire d'allumer ou etendre la BA
	private boolean permanent =true; // choix à faire pour BA permanente ou commandable
	private final int nb =nbCercle; //Nombre de cercles à definir selon le zoom
	private int rayon =rayonCercle;
	private int x0;  
	private int y0;

	private JMenuItem etat_barre_Arret ; 



	/** constructeur */
	public StopBar (int x, int y){
		setX0(x);
		setY0(y);
		}

	/** méthode */
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
		double deltax = this.x0-p_abs.getX();
		double deltay = this.y0-p_abs.getY();
		return Math.sqrt(deltax*deltax+deltay*deltay);
	}
}