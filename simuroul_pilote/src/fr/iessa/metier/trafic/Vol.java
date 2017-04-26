/**
 * 
 */
package fr.iessa.metier.trafic;

import java.awt.Point;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

import fr.iessa.metier.Instant;
import fr.iessa.metier.Instant.InstantFabrique;
import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.TypeVol;

/**
 * Decrit un vol
 * @author hodiqual
 * 
 * @author bouletcy
 * Modification de la classe pour devenir classe mère de volAvionPilote et volAvionPredefini
 */
public abstract class Vol  {
	
	protected Point _coordCourante;
	protected Point _coordSuivante;
	
	
	/**
	 * @return the _coordCourante
	 */
	public Point getCoordCourante() {
		return _coordCourante;
	}

	/**
	 * @return the _coordSuivante
	 */
	public Point getCoordSuivante() {
		return _coordSuivante;
	}
	
	public abstract void updateCoordCourantes(Instant instant, boolean visible);

	public abstract boolean aDesCollisions();

	public abstract boolean estSurLaPlateforme(Instant instant);
	
	public abstract Point getCoord(Instant i );
	
	public abstract TypeVol getTypeVol() ;
	public abstract String getId() ;
	public abstract Instant getPremierInstant() ;
	
	public abstract Categorie getCategorie();
	public abstract void setADesCollisions(boolean aDesCollisions);
	public abstract Map<Instant, Point> getInstantVersCoord();
}
