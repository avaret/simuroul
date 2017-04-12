/**
 * 
 */
package fr.iessa.dao.trafic;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.iessa.metier.Instant;
import fr.iessa.metier.Instant.InstantFabrique;
import fr.iessa.metier.trafic.PointFabrique;
import fr.iessa.metier.trafic.Trafic;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.metier.trafic.VolAvionPilote;
import fr.iessa.metier.trafic.VolAvionPredefini;
import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.TypeVol;

/**
 * Instancie le trafic decrit dans un fichier texte.
 * @author hodiqual
 * 
 * @author bouletcy 
 * Interpolation de points pour fluidifier la simulation
 */
public class TraficDao {
	
	
	/**
	 * Charge le trafic en parallele.
	 * @param ficname contient la description de l'ensemble des vols du trafic sur 24h.
	 * @return instance de la classe Trafic contenant les vols decrits dans le fichier.
	 */
	public Trafic charger(String ficname) {
		
		Trafic _trafic = new Trafic();
		
		Set<Vol> vols = null;
		try (Stream<String> lignes = Files.lines(Paths.get(ficname));) {
			vols = lignes.parallel()
					.map(this::chargerVol)
					.collect(Collectors.toSet());
		} catch (IOException e) {
			e.printStackTrace();
		}
		_trafic.setVols(vols);
		return _trafic;
	}
	
	/**
	 * Charge un vol a partir d une description texte.
	 * @param ligneFormatVol String decrivant un vol, le format attendu 
	 * "DEP BCS1748 M M17 27L 1440 _ -955,-1104 -946,-1116 -938,-1128 -930,-1141".
	 * @return instance de la classe Vol decrit par la String ligneFormatVol.
	 * 
	 * @author bouletcy modification de la méthode pour interpoler les points des vols predefinis
	 */
	public VolAvionPredefini chargerVol(String ligneFormatVol)
	{
		Scanner scan = new Scanner(ligneFormatVol);
		scan.useDelimiter(" |\n");
		
		TypeVol typeVol = TypeVol.valueOf(scan.next());
		String id = scan.next();
		Categorie categorie = Categorie.from(scan.next());
		
		scan.next();scan.next(); // skip
		
		int secondes = scan.nextInt();
		
		VolAvionPredefini vol = new VolAvionPredefini(typeVol, id, categorie, InstantFabrique.get(secondes));
		
		scan.next(); // skip
		
		Point maCoordCourante=PointFabrique.get( scan.next());
		
		
		while(scan.hasNext()) //boucle sur les coordonnees + interpolation sur 4 points pour atteindre 1 point par seconde
		{		
			
			Point maCoordSuivante = PointFabrique.get( scan.next());
			
			vol.ajout( InstantFabrique.get(secondes)
					 , maCoordCourante ) ;
			
			int i;
			for(i=1;i<5;i++){
				
				secondes+=1;
				Point pointInterpol = new Point(
						(int)(maCoordCourante.x + (maCoordSuivante.x - maCoordCourante.x)*i/5) , 
						(int)(maCoordCourante.y + (maCoordSuivante.y - maCoordCourante.y)*i/5));
				vol.ajout(InstantFabrique.get(secondes), pointInterpol);
				
				
			}
			maCoordCourante=maCoordSuivante;
			
		}
		vol.ajout( InstantFabrique.get(secondes)
				 , maCoordCourante ) ;
		return 	vol;
	}
}
