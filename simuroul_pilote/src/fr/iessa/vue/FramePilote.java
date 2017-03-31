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
import fr.iessa.vue.trafic.ComponentVol;
import fr.iessa.vue.trafic.ShapeAvionFactory;


/**
 * Classe FramePilote
 * @author bernarti
 * */

// POUR L'INSTANT:
// - Zoom sur une zone contenant l'Avion (Pas centrée sur l'Avion en question)
// - ATTENTION, LE ZOOM PAR MOLETTE ET LE DRAG DE SOURIS DE FONCTIONNENT PLUS SUR LES FRAMES (PanelPlateforme)


public class FramePilote extends JFrame
{
	private static final long serialVersionUID = 1L;

	private PanelPrincipalMultiCouches jpanelPilote;

	private Controleur _controleurPilote;
	private Echelle _echellePilote;

	private int _zoomPilote = 30;

	private Point2D.Double positionPilotePrecedente = null;
	private Point2D.Double positionPiloteCourante = new Point2D.Double(0, 0);
	private Point2D.Double positionPiloteCouranteAbs = new Point2D.Double(0, 0);


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

		// Récupération des Coordonnées Courantes de l'Avion
		_echellePilote.getAffineTransform().deltaTransform(CompVol.getVol().getCoordCourante(), positionPiloteCourante);
		_echellePilote.getAffineTransform().transform(CompVol.getVol().getCoordCourante(), positionPiloteCouranteAbs);


		// Initialisation de positionPilotePrecedente
		if(positionPilotePrecedente == null)
			positionPilotePrecedente = (Double) positionPiloteCourante.clone();


		// Calcul des Ecarts en X et en Y
		double XEcart = positionPilotePrecedente.x - positionPiloteCourante.x;
		double YEcart = positionPilotePrecedente.y - positionPiloteCourante.y;
		Point2D.Double EcartRelatif = new Point2D.Double(XEcart, YEcart);


		// Zoom et Actualisation de la FramePilote
		_echellePilote.setZoomLevel(_zoomPilote, new Point((int)positionPiloteCouranteAbs.x, (int)positionPiloteCouranteAbs.y), getWidth(), getHeight());
		_echellePilote.setScroll(EcartRelatif, getWidth(), getHeight());


		// Rotation du contenu de la FramePilote pour garder le nez de l'Avion toujours dans la meme direction
		//_echellePilote.getAffineTransform().quadrantRotate((int)angle, positionPiloteCourante.x, positionPiloteCourante.y);


		// Stockage de positionPiloteCourante dans positionPilotePrecedente pour l'intstant t+1
		positionPilotePrecedente = (Double) positionPiloteCourante.clone();


		// Changement de représentation de l'Avion Selectionné (/!\ CHANGE SUR LA FRAMEPRINCIPALE /!\) 
		//CompVol.setImageFactory(ShapeAvionFactory.PILOTE);

		jpanelPilote.repaint();
		jpanelPilote.revalidate();

		this.revalidate();
		this.pack();
		this.setVisible(true);
	}
}