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

import javax.imageio.ImageIO;
import javax.swing.JButton;

import fr.iessa.controleur.Controleur;
import fr.iessa.metier.Instant;
import fr.iessa.metier.Instant.InstantFabrique;
import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.TypeVol;
import fr.iessa.controleur.Controleur;

public class VolAvionPilote extends Vol  {



	private boolean recording;
	private boolean replay;
	private Point _PointDepart;
	private int _Vitesse;
	private int nombreAppel;
	private Instant _premierInstant;	
	private double angle;
	private Map<Instant, Point> _recordCoord = new HashMap<Instant, Point>(10);
	public VolAvionPilote(Instant instant, Point depart)
	{
		_premierInstant = instant;
		_PointDepart = depart;
	}

	public VolAvionPilote()
	{
		_premierInstant = Instant.InstantFabrique.getMinimumInstant();
		_Vitesse=5;
		_PointDepart = new Point(0,0);
		nombreAppel=0;
		_coordCourante=_PointDepart;
		angle=0;
	}

	//update des coordonnées à l'aide de l'angle, incrémenter par la suite par les touches du claviers
	@Override
	public void updateCoordCourantes(Instant instant) {
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

				_coordCourante = new Point(_coordCourante.x + (int)(_Vitesse*Math.cos(angle*Math.PI/180)), _coordCourante.y + (int)(_Vitesse *Math.sin(angle*Math.PI/180)));
				_coordSuivante = new Point(_coordCourante.x + (int)(100*Math.cos(angle*Math.PI/180)), _coordCourante.y + (int)(100*Math.sin(angle*Math.PI/180)));
				_recordCoord.put(instant, _coordCourante);
			}
		}
	}

	public void sauverReplay(String nomfichier) {
		ObjectOutputStream os;
		try {
			os = new ObjectOutputStream(new FileOutputStream(nomfichier));
			os.writeObject(_recordCoord);
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public void chargerReplay(String nomfichier) {
		// TODO Auto-generated method stub
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
			Controleur.avionPilote.set_recordCoord(tempHashMap2);
			oe.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		// TODO Auto-generated method stub
		return _PointDepart;
	}

	@Override
	public TypeVol getTypeVol() {
		// TODO Auto-generated method stub
		return TypeVol.DEP;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "ABR6GZ5";
	}

	@Override
	public Categorie getCategorie() {
		// TODO Auto-generated method stub
		return Categorie.MEDIUM;
	}

	@Override
	public void setADesCollisions(boolean aDesCollisions) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<Instant, Point> getInstantVersCoord() {
		// TODO Auto-generated method stub
		Map<Instant, Point> r = new HashMap<Instant, Point>(2);
		r.put(_premierInstant, _PointDepart);
		r.put(Instant.InstantFabrique.getMaximumInstant(), _PointDepart);
		return r;
	}


	public void RotationGauche(){
		double angle = Controleur.avionPilote.getAngle();
		angle += 1;
		Controleur.avionPilote.setAngle(angle);
	}

	public void RotationDroite(){
		double angle = Controleur.avionPilote.getAngle();
		angle -= 1;
		Controleur.avionPilote.setAngle(angle);
	}

	public void Accelerer(){
		int vitesse = Controleur.avionPilote.getVitesse();
		vitesse += 2;
		if (vitesse > 25){
			vitesse=24;
		}
		Controleur.avionPilote.setVitesse(vitesse);
	}

	public void Ralentir(){
		int vitesse = Controleur.avionPilote.getVitesse();
		vitesse -= 2;
		if (vitesse < 0){
			vitesse=0;
		}
		Controleur.avionPilote.setVitesse(vitesse);
	}

	public boolean isRecording() {
		return recording;
	}

	public void setRecording(boolean recording) {
		recording = recording;
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
}