package fr.iessa.vue;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.undo.AbstractUndoableEdit;

import fr.iessa.controleur.Controleur;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.vue.trafic.ComponentVol;
import fr.iessa.vue.trafic.ShapeAvionFactory;


/**
 * Classe FramePilote
 * @author bernarti
 * */

// POUR L'INSTANT:
// - Zoom sur une zone contenant l'Avion (Pas centrée sur l'Avion en question)


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

		// Initialisation des Caractéristiques (Taille, Position, etc...) de la FramePilote
		this.setPreferredSize((new Dimension(800, 600)));
		this.setLocation(400, 300);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		_echellePilote = new Echelle();
		_controleurPilote = controleur;

		// Création du Contenu de la FramePilote
		jpanelPilote = new PanelPrincipalMultiCouches(_controleurPilote, false, _echellePilote);	
		this.setContentPane(jpanelPilote);

		// Create and set up the content pane.
		this.validate();
		this.pack();
		this.setVisible(false);
	}


	public void ActualiserVuePilote(ComponentVol CompVol, int H, int W, double angle)
	{	
		// Récupération des Coordonnées Courantes de l'Avion (coin supérieur gauche)
		_echellePilote.getAffineTransform().transform(CompVol.getVol().getCoordCourante(), positionPiloteCourante);
		double XCourant = positionPiloteCourante.x;
		double YCourant = positionPiloteCourante.y;
		
		// Récupération des Coordonnées Suivantes de l'Avion (coin supérieur gauche)
		_echellePilote.getAffineTransform().transform(CompVol.getVol().getCoordSuivante(), positionPiloteSuivante);
		double XSuivant = positionPiloteSuivante.x;
		double YSuivant = positionPiloteSuivante.y;

		
		// Calcul des Coordonnées Courantes et Suivantes du Centre de l'Avion Selectionné
		CalculCoordCentre(XCourant, YCourant, H, W);
		CalculCoordCentre(XSuivant, YSuivant, H, W);

		
		// Affichage des Coordonnées pour controle
		System.out.println("Position Centre Avion Courante: X: " + XCourant + " / Y: " + YCourant);
		System.out.println("Position Centre Avion Suivante: X: " + XSuivant + " / Y: " + YSuivant);	
		System.out.println("Angle: " + angle);
		
		positionPiloteCourante.x = XCourant;
		positionPiloteCourante.y = YCourant;

		
		Point2D.Double ecartCourantSuivant = new Point2D.Double(XCourant-XSuivant, YCourant-YSuivant);
		Point p = new Point((int)XCourant, (int)YCourant);
		
		
		// Zoom et Actualisation de la FramePilote
		_echellePilote.setZoomLevel(_zoomPilote, p, getWidth(), getHeight());
		_echellePilote.setScroll(ecartCourantSuivant, getWidth(), getHeight());
		
		//CompVol.setImageFactory(ShapeAvionFactory.PILOTE);
		
		// Rotation du contenu de la Frame pour garder le nez de l'Avion toujours vers le haut
		//_echellePilote.setAffineTransform(AffineTransform.getRotateInstance(-Math.toRadians(angle), XCourant, YCourant));
		//_echellePilote.getAffineTransform().quadrantRotate((int)angle, XCourant, YCourant);
		//_echellePilote.getAffineTransform().rotate(angle, XCourant, YCourant);
		
		jpanelPilote.repaint();
		jpanelPilote.revalidate();

		this.validate();
		this.pack();
		this.setVisible(true);
	}

	public void CalculCoordCentre(double x, double y, int H, int W)
	{
		// Calcul pour des Coordonnées > ou < 0
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