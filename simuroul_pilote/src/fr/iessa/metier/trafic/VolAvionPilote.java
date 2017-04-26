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


	public void setAngle(double angle) {
		this.angle = angle;
	}

	public Instant getPremierInstant() {
		return _premierInstant;
	}


	public Point getPointDepart() {
		return _PointDepart;
	}

	public int getVitesse() {
		return _Vitesse;
	}
	public void setVitesse(int _Vitesse) {
		this._Vitesse = _Vitesse;
	}
	public  boolean aDesCollisions()
	{
		return false;
	}

	public double getAngle(){
		return angle;
	}

	@Override
	public boolean estSurLaPlateforme(Instant instant) {
		return true;
	}

	@Override
	public Point getCoord(Instant i) {
		return _PointDepart;
	}

	@Override
	public TypeVol getTypeVol() {
		return TypeVol.DEP;
	}

	@Override
	public String getId() {
		return "VolAvionPilote_"+IDVolAvionPilote;
	}

	@Override
	public Categorie getCategorie() {
		return Categorie.MEDIUM;
	}

	@Override
	public void setADesCollisions(boolean aDesCollisions) {
		//Nothing to do
	}

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
	 * 
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

	public boolean isRecording() {
		return recording;
	}

	public void setRecording(boolean Recording) {
		recording = Recording;
	}

	public boolean isReplay() {
		return replay;
	}

	public void setReplay(boolean Replay) {
		replay = Replay;
	}

	public Point get_PointDepart() {
		return _PointDepart;
	}

	public void set_PointDepart(Point _PointDepart) {
		this._PointDepart = _PointDepart;
	}

	public Instant get_premierInstant() {
		return _premierInstant;
	}

	public void set_premierInstant(Instant _premierInstant) {
		this._premierInstant = _premierInstant;
	}

	public Map<Instant, Point> get_recordCoord() {
		return _recordCoord;
	}

	public void set_recordCoord(Map<Instant, Point> _recordCoord) {
		this._recordCoord = _recordCoord;
	}

	public int getIDVolAvionPilote() {
		return IDVolAvionPilote;
	}

}