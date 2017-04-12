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


/** Classe FramePilote:
 * Cette classe permet de créer une Frame qui suit un avion particulier
 * (l'Avion Piloté) tout en reproduisant le contenu de la FramePrincipale
 * @author Timothée Bernard (ISESA16)
 * @version 1.0
 * 
 * Modifiée par: N/A
 * Modification: N/A
 * */


public class FramePilote extends JFrame
{
	// Déclaration des Attributs de la Classe
	private static final long serialVersionUID = 1L;

	private PanelPrincipalMultiCouches jpanelPilote;

	private int _zoomPilote = 30;
	private Echelle _echPilote = new Echelle();

	private Controleur _ctrlPilote;
	private VolAvionPilote _avionPilote;

	private Point2D.Double precedentPilote = null;
	private Point2D.Double courantPilote = new Point2D.Double(0, 0);
	private Point2D.Double courantPiloteAbs = new Point2D.Double(0, 0);


	// Constructeur de la Classe
	public FramePilote(Controleur controleur, VolAvionPilote avion)
	{
		// Appel du Constructeur de la Classe-Mère JFrame
		super("Vue Pilote");


		// Initialisation des Caractéristiques (Taille, Position sur l'écran, etc...) de la FramePilote
		this.setPreferredSize((new Dimension(800, 600)));
		this.setLocation(400, 300);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


		// Initialisation du Controleur déclaré
		_ctrlPilote = controleur;
		_avionPilote = avion;

		// Création du Contenu de la FramePilote
		jpanelPilote = new PanelPrincipalMultiCouches(_ctrlPilote, false, _echPilote);	
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
		_echPilote.getAffineTransform().deltaTransform(coordXY, courantPilote);
		_echPilote.getAffineTransform().transform(coordXY, courantPiloteAbs);
		Point pointAbs =  new Point((int)courantPiloteAbs.x, (int)courantPiloteAbs.y);


		// Initialisation de precedentPilote
		if(precedentPilote == null)
			precedentPilote = (Double)courantPilote.clone();


		// Calcul des Ecarts en X et en Y
		double xEcart = precedentPilote.x - courantPilote.x;
		double yEcart = precedentPilote.y - courantPilote.y;
		Point2D.Double ecartRelatif = new Point2D.Double(xEcart, yEcart);


		// Zoom et Actualisation de la FramePilote
		_echPilote.setZoomLevel(_zoomPilote, pointAbs, getWidth(), getHeight());
		_echPilote.setScroll(ecartRelatif, getWidth(), getHeight());


		// Fixation de l'orientation de l'Avion Piloté puis Rotation du contenu de la FramePilote
		// FIXME ATTENTION: IL EST POSSIBLE QUE L'ORIENTATION 
		// DE L'AVION SE FASSE AUSSI SUR LA FRAMEPRINCIPALE !!
		double angle = (Math.PI/2.0 - _avionPilote.getAngle());
		_avionPilote.setAngle(Math.PI/2.0);
		_echPilote.setRotationAngle(angle);


		// Stockage de courantPilote dans precedentPilote pour l'instant suivant (T+1)
		precedentPilote = (Double)courantPilote.clone();


		// TODO Changement de représentation de l'Avion Selectionné
		// FIXME ATTENTION: CHANGE L'IMAGE DE L'AVION SUR LA 
		// FRAMEPRINCIPALE MAIS PAS SUR LA FRAMEPILOTE !!
		//CompVol.setImageFactory(ShapeAvionFactory.PILOTE);


		// Confirmation de l'Actualisation par le redessinage du Contenu (JPanel)
		jpanelPilote.repaint();
		jpanelPilote.revalidate();

		this.revalidate();
		this.pack();
	}
}