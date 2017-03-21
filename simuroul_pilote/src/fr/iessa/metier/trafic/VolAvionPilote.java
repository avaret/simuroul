package fr.iessa.metier.trafic;


import java.awt.Point;
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
import java.io.IOException; 
import javax.imageio.ImageIO;
import javax.swing.JButton;
import fr.iessa.metier.Instant;
import fr.iessa.metier.Instant.InstantFabrique;
import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.TypeVol;


public class VolAvionPilote extends Vol {
	
	
	private Point _PointDepart;
	private int _Vitesse;
	private int nombreAppel;
	private Instant _premierInstant;	
	private double angle;
	
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
		nombreAppel++;
		int a=nombreAppel;
		
		
		if( instant == null )
			_coordCourante = null;
		else
		{
			
			_coordCourante = new Point(_coordCourante.x + (int)(_Vitesse*Math.cos(angle*Math.PI/180)), _coordCourante.y + (int)(_Vitesse *Math.sin(angle*Math.PI/180)));
			_coordSuivante = new Point(_coordCourante.x + (int)(_Vitesse*Math.cos(angle*Math.PI/180)), _coordCourante.y + (int)(_Vitesse *Math.sin(angle*Math.PI/180)));
			
			
			
			

			
			
			
			
			
			
			
			// Translation de l'avion en carré
			/*if (nombreAppel<10){
				_coordCourante = new Point(_coordCourante.x, _coordCourante.y + _Vitesse * a);
			}
			
			if( nombreAppel<20 && nombreAppel>=10){
				a+=-10;
				_coordCourante = new Point(_coordCourante.x + _Vitesse * a, _coordCourante.y);
			}
			
			if( nombreAppel<30 && nombreAppel>=20){
				a+=-20;
				_coordCourante = new Point(_coordCourante.x, _coordCourante.y - _Vitesse * a);
			}
			
			if( nombreAppel<40 && nombreAppel>=30){
				a+=-30;
				_coordCourante = new Point(_coordCourante.x - _Vitesse * a, _coordCourante.y);
			}
			
			if(nombreAppel==40){
				nombreAppel=0;
			}*/
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
}