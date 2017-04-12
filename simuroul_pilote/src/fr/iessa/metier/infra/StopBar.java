package fr.iessa.metier.infra;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/** Class BarreArret qui permet de creer une Barre d'Arret, d'afficher celle-ci, de la positionner et son etat.
 * c'est fonction seront accessible par un clic droit sous forme de liste.
 * @author GINEYS Christophe 
 * @version 1.0 
 */

public class StopBar implements Serializable {

	/** Attributs */

	private boolean allumer=true; // choix à faire d'allumer ou etendre la BA
	private boolean permanent =true; // choix à faire pour BA permanente ou commandable

	private int x0;  // coordonnée du point d'insertion de la BA
	private int y0;  // coordonnée du point d'insertion de la BA
	private int theta0;  // angle de la BA
	
	private JMenuItem etat_barre_Arret ; 

	/** constructeur */
	public StopBar (int x, int y, int theta){
		setX0(x);
		setY0(y);
		getAngle0();
		
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
	
	public int getAngle0() {
		return theta0;
	}

	public double distance(Point2D.Double p_abs) {
		double deltax = this.x0-p_abs.getX();
		double deltay = this.y0-p_abs.getY();
		return Math.sqrt(deltax*deltax+deltay*deltay);
	}
}