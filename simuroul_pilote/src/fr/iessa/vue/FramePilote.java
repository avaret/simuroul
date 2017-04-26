package fr.iessa.vue;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.ModeleEvent;
import fr.iessa.metier.trafic.VolAvionPilote;
import fr.iessa.vue.trafic.PanelTrafic;

/** 
 * La classe FramePilote permet de créer une Frame qui suit un avion particulier
 * (l'Avion Piloté) tout en reproduisant le contenu de la FramePrincipale
 * <p>
 * <strong>Modification:</strong> N/A
 * <p>
 * 
 * @author Timothée Bernard (ISESA16)
 */
public class FramePilote extends JFrame implements Observer, PropertyChangeListener
{
	/**
	 * Déclaration des Attributs de la Classe
	 */
	private static final long serialVersionUID = 1L;
	private static int nombreFrame = 0;
	private int iDFrame;

	private PanelPrincipalMultiCouches jpanelPilote;

	private int _zoomPilote = 30;
	private Echelle _echPilote = new Echelle();

	private Controleur _ctrlPilote;
	private VolAvionPilote _avionPilote;

	private Point2D.Double precedentPilote = null;
	private Point2D.Double courantPilote = new Point2D.Double(0, 0);
	private Point2D.Double courantPiloteAbs = new Point2D.Double(0, 0);


	/**
	 * Constructeur de la Classe
	 *  
	 * @param controleur
	 * @param avion
	 */
	public FramePilote(Controleur controleur, VolAvionPilote avion)
	{
		// Appel du Constructeur de la Classe-Mère JFrame
		super("Vue Pilote #" + nombreFrame);
		iDFrame = nombreFrame;
		nombreFrame++;


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

		final ModeleEvent[] evts = { ModeleEvent.UPDATE_INSTANT };
		_ctrlPilote.ajoutVue(this,  evts) ;
		//_echPilote.addObserver(this);
		
		// Create and set up the content pane.
		this.validate();
		this.pack();
		this.setVisible(true);
	}


	/**
	 * Accès à la valeur de l'iD de la FramePilote
	 * 
	 * @return iDFrame
	 */
	public int getIDFrame()
	{
		return iDFrame;
	}


	/**
	 * Actualisation du contenu de la FramePilote
	 * afin de suivre l'Avion Piloté
	 * 
	 */
	public void ActualiserVuePilote()
	{
		System.out.println("CoordAvion: " + _avionPilote.getCoordCourante());
		
		// Récupération des Coordonnées Courantes de l'Avion Piloté
		_echPilote.getAffineTransform().deltaTransform(_avionPilote.getCoordCourante(), courantPilote);
		_echPilote.getAffineTransform().transform(_avionPilote.getCoordCourante(), courantPiloteAbs);
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
		//double angle = (Math.PI/2.0 - _avionPilote.getAngle());
		//_avionPilote.setAngle(Math.PI/2.0);
		//_echPilote.setRotationAngle(angle);


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


	@Override
	public void update(Observable o, Object arg)
	{
		System.out.println("update");
		// Nothing to do
		
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		System.out.println(" propertyy changed ! ");
		ActualiserVuePilote();
	}
}