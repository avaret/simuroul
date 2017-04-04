package fr.iessa.metier.infra;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/** Class BarreArret qui permet de creer une Barre d'Arret, d'afficher celle-ci, de la positionner et son etat.
 * c'est fonction seront accessible par un clic droit sous forme de liste.
 * @author GINEYS Christophe 
 * @version 1.0 
 */

public class StopBar extends JPanel{

	/** Attributs */
	private boolean allumer=true; // choix à faire d'allumer ou etendre la BA
	private boolean permanent =true;
	private final int nb =3; //Nombre de cercles à definir selon le zoom
	private int rayon = 10;
	private int x0;  
	private int y0;

	private JPopupMenu sous_menu_souris;
	private JMenuItem etat_barre_Arret ; 

	public Point position_BA;

	/** constructeur */
	public StopBar (int x, int y){
		setX0(x);
		setY0(y);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPopupMenu sous_menu_souris = new JPopupMenu();
				//selection la barre d'arret

				etat_barre_Arret = new JMenuItem("allumer /eteindre");
				sous_menu_souris.add(etat_barre_Arret);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		setAllumer(!isAllumer());
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
}
