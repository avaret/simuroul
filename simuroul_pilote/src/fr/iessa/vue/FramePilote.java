package fr.iessa.vue;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.iessa.controleur.Controleur;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.vue.trafic.ComponentVol;


/**
 * Classe FramePilote
 * @author bernarti
 * */

// POUR L'INSTANT:
// - Zoom sur une zone contenant l'Avion (Pas centrée sur l'Avion en question)
// - Actualisation de la position de l'Avion (Sur plusieures Frames + Frames blanches)
// - Création de plusieurs Frames (Pas d'actualisation d'une seule Frame)


public class FramePilote extends JFrame
{
	private static final long serialVersionUID = 1L;

	private PanelPrincipalMultiCouches jpanelPilote;
	private Controleur _controleurPilote;
	private Echelle _echellePilote;
	private int _zoomPilote = 30;
	private Point2D.Double positionPiloteCourante = new Point2D.Double(0, 0);
	private Point2D.Double positionPiloteSuivante = new Point2D.Double(0, 0);

	public FramePilote(Controleur controleur)
	{
		super("Vue Pilote");

		this.setPreferredSize((new Dimension(800, 600)));
		this.setLocation(400, 300);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		_echellePilote = new Echelle();
		_controleurPilote = controleur;

		jpanelPilote = new PanelPrincipalMultiCouches(_controleurPilote, false, _echellePilote);	
		this.setContentPane(jpanelPilote);

		//Create and set up the content pane.
		this.validate();
		this.pack();
		this.setVisible(false);

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("GetX(): " + getX() + " / GetY(): " + getY());
				System.out.println("Mouse getX: " + e.getY()+ " / Mouse getY: " + e.getY() + "\n");
			}
		});
	}


	public void VuePilote(Vol vol, int H, int W)
	{	
		_echellePilote.getAffineTransform().transform(vol.getCoordCourante(), positionPiloteCourante);
		double XCourant = positionPiloteCourante.x;
		double YCourant = positionPiloteCourante.y;
		
		_echellePilote.getAffineTransform().transform(vol.getCoordSuivante(), positionPiloteSuivante);
		double XSuivant = positionPiloteSuivante.x;
		double YSuivant = positionPiloteSuivante.y;

		// Le Calcul des Coordonnées du Centre change si les Coordonnées sont > ou < à 0
		CalculCoord(XCourant, YCourant, H, W);
		CalculCoord(XSuivant, YSuivant, H, W);


		System.out.println("Position Centre Avion Courante: X: " + XCourant + " / Y: " + YCourant);
		System.out.println("Position Centre Avion Suivante: X: " + XSuivant + " / Y: " + YSuivant);	

		positionPiloteCourante.x = XCourant;
		positionPiloteCourante.y = YCourant;

		Point2D.Double ecartCourantSuivant = new Point2D.Double(-XSuivant+XCourant, -YSuivant+YCourant);
		Point p = new Point((int)XCourant, (int)YCourant);
		
		_echellePilote.setZoomLevel(_zoomPilote, p, getWidth(), getHeight());
		_echellePilote.setScroll(ecartCourantSuivant, getWidth(), getHeight());
		
		//_echellePilote.getAffineTransform().setToRotation(0);		<=== C'est ce truc qui fait de la merde !

		jpanelPilote.repaint();
		jpanelPilote.revalidate();

		this.validate();
		this.pack();
		this.setVisible(true);
	}

	public void ActualiserVuePilote(Vol vol, int H, int W)
	{

		// Création de Fenetre une par une pour actualisation
		/*
		Frame[] F = FramePrincipale.getFrames();

		System.out.println("Nombres Frames: " + F.length);

		int i;
		for(i = 1; i < (F.length); i++)
		{
			setTitle("Vue Pilote n°" + (F.length -1));

			System.out.println("Fenetre: " + F[i].getTitle() + " visible: " + F[i].isShowing());
			F[i].setEnabled(false);
			F[i].dispose();
		}

		FramePrincipale.FPilote = new FramePilote(FramePrincipale._controleur);
		//*/
		// Repaint de la Fenetre pour actualisation

		VuePilote(vol, H, W);
	}

	public void CalculCoord(double x, double y, int H, int W)
	{
		// Calcul de x
		if(x >= 0)
			x += H/2;
		else
			x -= H/2;

		// Calcul de y
		if(y >= 0)
			y += W/2;
		else
			y -= W/2;
	}
}