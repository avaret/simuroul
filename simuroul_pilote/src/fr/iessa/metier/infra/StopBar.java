package fr.iessa.metier.infra;

import java.awt.geom.Point2D;
import java.io.Serializable;


/** Class BarreArret qui permet de creer une Barre d'Arret, d'afficher celle-ci, de la positionner et son etat.
 * ces fonctions seront accessible par un clic droit sous forme de liste.
 * @author GINEYS Christophe 
 * @version 1.0 
 */

public class StopBar implements Serializable {


	private boolean allumer=true; // choix à faire d'allumer ou etendre la BA
	private boolean permanent =true; // choix à faire pour BA permanente ou commandable
	private int x0;  // coordonnée du point d'insertion de la BA en x
	private int y0;  // coordonnée du point d'insertion de la BA en y
	private int angle;  // angle de la BA



	/** constructeur de la classe StopBar
	 * @param x 	Abscisse de la StopBar à créer 
	 * @param y 	Ordonnée de la StopBar à créer
	 * @param theta	Angle (par rapport à l'Est) de la StopBar à créer
	 * */
	public StopBar (int x, int y, int theta){
		setX0(x);
		setY0(y);
		setAngle(theta);

	}

	/** retourne le booléen correspondant à l'état allumer ou éteint de la barre d'arret */
	public boolean isAllumer() {
		return allumer;
	}

	/** permet de fixer l'état de l'allumage de la barre d'arret */
	public void setAllumer(boolean allumer) {
		this.allumer = allumer;
	}

	/** retourne le booléen correspondant à l'état permanent ou non de la barre d'arret */
	public boolean isPermanent() {
		return permanent;
	}

	/** permet de fixer l'état permanent ou non de la BA*/
	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}

	/** retourne l'abscisse de la souris */
	public int getX0() {
		return x0;
	}

	public void setX0(int x0) {
		this.x0 = x0;
	}

	
	/** retourne l'ordonnée de la souris */
	public int getY0() {
		return y0;
	}

	public void setY0(int y0) {
		this.y0 = y0;
	}

	/** retourne l'angle de la BA*/
	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	/** permet de calculer la distance entre deux points */
	public double distance(Point2D.Double p_abs) {
		double deltax = this.x0-p_abs.getX();
		double deltay = this.y0-p_abs.getY();
		return Math.sqrt(deltax*deltax+deltay*deltay);
	}
}