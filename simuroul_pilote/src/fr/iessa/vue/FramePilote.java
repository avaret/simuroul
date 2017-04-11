package fr.iessa.vue;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
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
import fr.iessa.metier.trafic.VolAvionPilote;
import fr.iessa.vue.trafic.ComponentVol;
import fr.iessa.vue.trafic.ShapeAvionFactory;


/**
 * Classe FramePilote
 * @author Timothée Bernard (ISESA16)
 * */


public class FramePilote extends JFrame
{
	// Déclaration des Attributs de la Classe
	private static final long serialVersionUID = 1L;

	private PanelPrincipalMultiCouches jpanelPilote;

	private int _zoomPilote = 30;
	private Controleur _controleurPilote;
	private Echelle _echellePilote = new Echelle();
	private VolAvionPilote _avionPilote;

	private Point2D.Double positionPilotePrecedente = null;
	private Point2D.Double positionPiloteCourante = new Point2D.Double(0, 0);
	private Point2D.Double positionPiloteCouranteAbs = new Point2D.Double(0, 0);


	// Constructeur de la Classe
	public FramePilote(Controleur controleur, VolAvionPilote avion)
	{
		super("Vue Pilote");


		// Initialisation des Caractéristiques (Taille, Position sur l'écran, etc...) de la FramePilote
		this.setPreferredSize((new Dimension(800, 600)));
		this.setLocation(400, 300);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


		// Initialisation du Controleur déclaré
		_controleurPilote = controleur;
		_avionPilote = avion;
		

		// Création du Contenu de la FramePilote
		jpanelPilote = new PanelPrincipalMultiCouches(_controleurPilote, false, _echellePilote);	
		this.setContentPane(jpanelPilote);


		// Create and set up the content pane.
		this.validate();
		this.pack();
		this.setVisible(true);
	}


	// Méthode d'Actualisation et de Suivi de l'Avion Piloté
	public void ActualiserVuePilote(Point2D.Double coordXY)
	{	
		// Récupération des Coordonnées Courantes de l'Avion Piloté
		_echellePilote.getAffineTransform().deltaTransform(coordXY, positionPiloteCourante);
		_echellePilote.getAffineTransform().transform(coordXY, positionPiloteCouranteAbs);
		Point pointAbs =  new Point((int)positionPiloteCouranteAbs.x, (int)positionPiloteCouranteAbs.y);


		// Initialisation de positionPilotePrecedente
		if(positionPilotePrecedente == null)
			positionPilotePrecedente = (Double)positionPiloteCourante.clone();


		// Calcul des Ecarts en X et en Y
		double xEcart = positionPilotePrecedente.x - positionPiloteCourante.x;
		double yEcart = positionPilotePrecedente.y - positionPiloteCourante.y;
		Point2D.Double ecartRelatif = new Point2D.Double(xEcart, yEcart);


		// Zoom et Actualisation de la FramePilote
		_echellePilote.setZoomLevel(_zoomPilote, pointAbs, getWidth(), getHeight());
		_echellePilote.setScroll(ecartRelatif, getWidth(), getHeight());


		// TODO Rotation du contenu de la FramePilote et fixation de l'orientation de l'Avion Piloté
 		_avionPilote.setAngle(90);
		//_echellePilote.getAffineTransform().quadrantRotate((int)angle, positionPiloteCourante.x, positionPiloteCourante.y);


		// Stockage de positionPiloteCourante dans positionPilotePrecedente pour l'intstant t+1
		positionPilotePrecedente = (Double)positionPiloteCourante.clone();


		// TODO Changement de représentation de l'Avion Selectionné (/!\ CHANGE SUR LA FRAMEPRINCIPALE /!\) 
		//CompVol.setImageFactory(ShapeAvionFactory.PILOTE);


		// Confirmation de l'Actualisation par le redessinage du Contenu (JPanel)
		jpanelPilote.repaint();
		jpanelPilote.revalidate();

		this.revalidate();
		this.pack();
	}
}