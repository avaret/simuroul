package fr.iessa.metier.trafic;
/**
@author bouletcy
 */
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.CookieHandler;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import fr.iessa.controleur.Controleur;
import fr.iessa.metier.Instant;
import fr.iessa.metier.Instant.InstantFabrique;
import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.TypeVol;
import fr.iessa.vue.FramePilote;
import fr.iessa.vue.FramePrincipale;
import fr.iessa.controleur.Controleur;
import fr.iessa.metier.trafic.Trafic;

public class VolAvionPilote extends Vol {



	private boolean recording;
	private boolean replay;
	private Point _PointDepart;
	private int _Vitesse;
	private Instant _premierInstant;	
	private double angle;
	private Map<Instant, Point> _recordCoord = new HashMap<Instant, Point>(10);
	private static int nombreVolAvionPilote=0;
	private int IDVolAvionPilote;
	
	
	/**
	 * Constructeur avec paramètre qui fait appel à initialiserVolAvionPilote() afin d'implémenter les paramètres par défauts de l'avionPilote
	 * @param instant définit l'instant de création de l'avionPilote
	 * @param depart définit la position de départ de l'avionPilote
	 */
	public VolAvionPilote(Instant instant, Point depart)
	{
		_premierInstant = instant;
		_PointDepart = depart;
		initialiserVolAvionPilote();
	}

	/**
	 * constructeur de base qui permet de créer un avionPilote en début de simulation au point initial (0,0)
	 */
	public VolAvionPilote()
	{
		_premierInstant = Instant.InstantFabrique.getMinimumInstant();
		_PointDepart = new Point(0,0);
		initialiserVolAvionPilote();
	}

	/**
	 * initialise un avionPilote avec des paramètres par defaut. Incrémente également nombreVolAvionPilote
	 */
	public void initialiserVolAvionPilote() {
		_Vitesse=5;
		_coordCourante=_PointDepart;
		angle=0;
		IDVolAvionPilote=nombreVolAvionPilote;
		nombreVolAvionPilote++;		
	}

	/**
	 * Permet l'update des coordonnées courante de l'avionPilote, en prenant en compte si la fonction replay 
	 * est lancé ou pas, si elle l'est, on charge les positions à partir d'une hashmap, sinon on update les coordCourante 
	 * et coordSuivante à partir des variables vitesse et angle.
	 * 
	 * @param instant correspond à l'instant courant de l'application
	 * @param visible détermine l'état de visibilité de l'avion.
	 */
	@Override
	public void updateCoordCourantes(Instant instant, boolean visible) {
		if(replay)
		{
			if( instant == null )
				_coordCourante = null;
			else if (_recordCoord.containsKey(instant))
			{

				_coordCourante = _recordCoord.get(instant);
				Instant instantSuivant = InstantFabrique.get(instant.getSeconds()+InstantFabrique._pasEntreInstant);
				_coordSuivante = _recordCoord.get(instantSuivant);	
			}
		} 
		else
		{
			if( instant == null )
				_coordCourante = null;
			else
			{

				_coordCourante = new Point(_coordCourante.x + (int)(_Vitesse*Math.cos(angle*Math.PI/180)),
										   _coordCourante.y + (int)(_Vitesse *Math.sin(angle*Math.PI/180)));
				
				_coordSuivante = new Point(_coordCourante.x + (int)(100*Math.cos(angle*Math.PI/180)),
						 				   _coordCourante.y + (int)(100*Math.sin(angle*Math.PI/180)));
				_recordCoord.put(instant, _coordCourante);
			}
		}
	}

	/**
	 * Permet de sauvegarder la hashmap de l'avionPilote.
	 * @param nomfichier Fichier dans lequel on va sauvegarder la hashmap de l'avionPilote
	 */
	public void sauverReplay(String nomfichier) {
		ObjectOutputStream os;
		try {
			os = new ObjectOutputStream(new FileOutputStream(nomfichier));
			os.writeObject(_recordCoord);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * Permet de charger un replay d'une simulation à partir d'un fichier
	 * @param nomfichier Nom du ficher regroupant la hashmap de l'avionpilote sauvegardé
	 */
	public void chargerReplay(String nomfichier) {
		ObjectInputStream oe;
		try {
			oe = new ObjectInputStream(new FileInputStream(nomfichier));
			Map<Instant, Point> tempHashMap;
			tempHashMap=(HashMap<Instant, Point>)oe.readObject();
			Map<Instant, Point> tempHashMap2 = new HashMap<Instant,Point>(10);
			for (Instant a : tempHashMap.keySet()) //forEach
			{
				Instant b = InstantFabrique.get(a.getSeconds());
				tempHashMap2.put(b, tempHashMap.get(a));
			}
			System.out.println("nombre de point chargé =" + tempHashMap.size());
			this.set_recordCoord(tempHashMap2);
			oe.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}


	/**
	 * permet de définir l'angle de l'avionPilote
	 * @param angle
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}

	/**
	 * @return _premierInstant
	 */
	public Instant getPremierInstant() {
		return _premierInstant;
	}

	/**
	 * 
	 * @return _PointDepart
	 */
	public Point getPointDepart() {
		return _PointDepart;
	}

	/**
	 * 
	 * @return _Vitesse
	 */
	public int getVitesse() {
		return _Vitesse;
	}
	
	/**
	 * Permet de fixer la vitesse de de l'avion
	 * @param _Vitesse
	 */
	public void setVitesse(int _Vitesse) {
		this._Vitesse = _Vitesse;
	}
	
	/**
	 * @return false
	 */
	public  boolean aDesCollisions(){
		return false;
	}

	/**
	 * 
	 * @return angle
	 */
	public double getAngle(){
		return angle;
	}

	/**
	 * @return true
	 */
	@Override
	public boolean estSurLaPlateforme(Instant instant) {
		return true;
	}

	/**
	 * @return _PointDepart
	 */
	@Override
	public Point getCoord(Instant i) {
		return _PointDepart;
	}

	/**
	 * retourne le type de l'avion
	 * @return TypeVol.DEP
	 */
	@Override
	public TypeVol getTypeVol() {
		return TypeVol.DEP;
	}

	/**
	 * retourne l'identifiant de l'avionPilote, incrémenter de 1 à chaque nouvel avionPilote.
	 * @return "VolAvionPilote_"+IDVolAvionPilote
	 */
	@Override
	public String getId() {
		return "VolAvionPilote_"+IDVolAvionPilote;
	}

	// Nouvelle Catégorie de vol créée spécialement pour les avions pilotés
	// Timothée BERNARD (ISESA16)
	@Override
	public Categorie getCategorie() {
		return Categorie.PILOTE;
	}

	@Override
	public void setADesCollisions(boolean aDesCollisions) {
		//Nothing to do
	}

	/**
	 * 
	 */
	@Override
	public Map<Instant, Point> getInstantVersCoord() {
		Map<Instant, Point> r = new HashMap<Instant, Point>(2);
		r.put(_premierInstant, _PointDepart);
		r.put(Instant.InstantFabrique.getMaximumInstant(), _PointDepart);
		return r;
	}

/**
 * Méthodes de rotation vers la gauche de l'avionPilote
 */
	public void RotationGauche(){
		double angle = getAngle();
		angle += 1;
		this.setAngle(angle);
	}
	
	/**
	 * Méthode de rotation vers la droite de l'avionPilote
	 */
	public void RotationDroite(){
		double angle = getAngle();
		angle -= 1;
		this.setAngle(angle);
	}
	
	/**
	 * Méthode de d'accelération de l'avionPilote
	 * Incrément de 2 par appel (toujours <25)
	 */
	public void Accelerer(){
		int vitesse = getVitesse();
		vitesse += 2;
		if (vitesse > 25){
			vitesse=24;
		}
		this.setVitesse(vitesse);
	}
	
	/**
	 * Méthode du ralentissement de l'avionPilote. 
	 * Réduit le paramètre vitesse de -2 par appel (toujours >0)
	 */
	public void Ralentir(){
		int vitesse = getVitesse();
		vitesse -= 2;
		if (vitesse < 0){
			vitesse=0;
		}
		this.setVitesse(vitesse);
	}

	/**
	 * Retourne le booléen correspondant à l'état actif de la fonction de replay ou non.
	 * @return recording
	 */
	public boolean isRecording() {
		return recording;
	}

	/**
	 * permet de définir l'état actif ou non de la fonction replay.
	 * @param Recording
	 */
	public void setRecording(boolean Recording) {
		recording = Recording;
	}

	public boolean isReplay() {
		return replay;
	}

	public void setReplay(boolean Replay) {
		replay = Replay;
	}

	/**
	 * retourn le point de départ de l'avionPilote
	 * @return _PointDepart
	 */
	public Point get_PointDepart() {
		return _PointDepart;
	}

	/**
	 * permet de définir le point de départ de l'avionPilote.
	 * @param _PointDepart
	 */
	public void set_PointDepart(Point _PointDepart) {
		this._PointDepart = _PointDepart;
	}

	/**
	 * retourne le première instant de l'avionPilote
	 * @return _premierInstant
	 */
	public Instant get_premierInstant() {
		return _premierInstant;
	}

	/**
	 * Permet de définir le première instant de l'avionPilote.
	 * @param _premierInstant
	 */
	public void set_premierInstant(Instant _premierInstant) {
		this._premierInstant = _premierInstant;
	}

	/**
	 * retourne la HashMap permettant de sauvegarder le trajet de l'avionPilote, utile pour la méthode de replay
	 * @return
	 */
	public Map<Instant, Point> get_recordCoord() {
		return _recordCoord;
	}

	/**
	 * permet de définir la HashMap charger si le replay est actif. Utile au chargement d'un replay.
	 * @param _recordCoord
	 */
	public void set_recordCoord(Map<Instant, Point> _recordCoord) {
		this._recordCoord = _recordCoord;
	}

	public int getIDVolAvionPilote() {
		return IDVolAvionPilote;
	}

}