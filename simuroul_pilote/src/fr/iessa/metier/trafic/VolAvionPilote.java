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
	private Instant _premierInstant;
	
	
	public VolAvionPilote(Instant instant, Point depart)
	{
		_premierInstant = instant;
		_PointDepart = depart;
	}
	
	public VolAvionPilote()
	{
		_premierInstant = Instant.InstantFabrique.getMinimumInstant();
		_Vitesse=10;
		_PointDepart = new Point(1000,500);
	
	}
	
	@Override
	public void updateCoordCourantes(Instant instant) {
		if( instant == null )
			_coordCourante = null;
		else
		{
			_coordCourante = new Point(1200,400);
			_coordSuivante = new Point(150,500);
		}
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