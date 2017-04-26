package fr.iessa.vue;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.ModeleEvent;
import fr.iessa.metier.trafic.VolAvionPilote;

/** 
 * La classe FramePilote permet de créer une Frame qui suit un avion particulier
 * (l'Avion Piloté) tout en reproduisant le contenu de la FramePrincipale
 * <p>
 * <strong>Modification:</strong> Implémentation des commandes de l'avion piloté au clavier (zqsd)
 * <p>
 * 
 * @author Timothée Bernard (ISESA16) / bouletcy
 */
public class FramePilote extends JFrame implements PropertyChangeListener, KeyListener
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

		// Initialisation du Controleur déclaré
		_ctrlPilote = controleur;
		_avionPilote = avion;


		// Initialisation des Caractéristiques (Taille, Position sur l'écran, etc...) de la FramePilote
		this.setPreferredSize((new Dimension(800, 600)));
		this.setLocation(400, 300);
		this.setDefaultCloseOperation(FermerVuePilote());


		// Création du Contenu de la FramePilote
		jpanelPilote = new PanelPrincipalMultiCouches(_ctrlPilote, false, _echPilote);	
		this.setContentPane(jpanelPilote);

		final ModeleEvent[] evts = { ModeleEvent.UPDATE_INSTANT };
		_ctrlPilote.ajoutVue(this,  evts) ;

		//Ajout du listener commande clavier.
		this.addKeyListener(this);

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


		// Confirmation de l'Actualisation par le redessinage du Contenu (JPanel)
		jpanelPilote.repaint();
		jpanelPilote.revalidate();

		this.revalidate();
		this.pack();
	}

	
	/**
	 * Méthode de fermeture de fenetre afin de
	 * supprimer en plus l'avion piloté associé
	 * à la FramePilote en question
	 * 
	 * @return
	 */
	public int FermerVuePilote()
	{	
		//_avionPilote.updateCoordCourantes(null, false);
		return JFrame.DISPOSE_ON_CLOSE;
	}

	
	/**
	 * Méthode qui permet appel ActualiserVuePilote()
	 */
	public void propertyChange(PropertyChangeEvent evt)
	{
		ActualiserVuePilote();
	}


	/**
	 * @author bouletcy
	 * implémentation des commandes de l'avion piloté au clavier (zqsd)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_Q) {
			_avionPilote.RotationGauche();
		}else if (e.getKeyCode() == KeyEvent.VK_D) {
			_avionPilote.RotationDroite();
		}else if (e.getKeyCode() == KeyEvent.VK_Z) {
			_avionPilote.Accelerer();
		}else if (e.getKeyCode() == KeyEvent.VK_S) { 
			_avionPilote.Ralentir();
		}
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// Nothing to do

	}


	@Override
	public void keyReleased(KeyEvent e) {
		// Nothing to do

	}
}