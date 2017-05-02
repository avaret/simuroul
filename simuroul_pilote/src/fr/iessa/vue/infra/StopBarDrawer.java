package fr.iessa.vue.infra;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import fr.iessa.metier.infra.Aeroport;
import fr.iessa.metier.infra.StopBar;
import fr.iessa.vue.CirclePanel;

/** Class StopBarDrawer qui permet de dessiner la barre d'arret.
 * @author GINEYS Christophe 
 * @version 1.0 
 */

public class StopBarDrawer {

	private final static int nb_feux = 10; //Nombre de cercles fix
	private final static int rayon_par_feu = 3; // diam des cercles
	private final static double espacement_entre_feux = (double)(2*(((float)rayon_par_feu)*1.2));

	/** 
	 * 	Permet de dessiner les StopBar passées en paramètre
	 * 
	 * @param sb 	StopBar destinée à etre dessinée sur la plateforme.
	 * @param g    	Objet graphique sur lequel doit etre dessiné la StopBar
	 * */
	public void dessineStopBar(StopBar sb, Graphics g)
	{
		int i;
		double y;
		double theta;

		for (i=-nb_feux/2;i<nb_feux/2;i++)
		{
			double x=i*espacement_entre_feux;
			if (i<-3 || i>=3)
			{
				y= espacement_entre_feux;
			}
			else
			{
				y= 0;
			}

			theta = sb.getAngle();
			double x0=sb.getX0();
			double y0=sb.getY0();

			double theta_rad= ((theta)*Math.PI/180.0);

			double r=Math.sqrt((x*x)+(y*y));
			double phi = Math.atan2(y, x);

			double x2=r*Math.cos(theta_rad+phi)+x0;
			double y2=r*Math.sin(theta_rad+phi)+y0;

			dessinUnFeu(g, (int)x2, (int)y2, sb.isAllumer(),sb.isPermanent());
		}
	}

	/** 
	 * 	dessinUnFeu permet de dessiner un feu, composant des StopBar que l'on dessine par la suite.
	 * 
	 * @param xi 	correspond à l'abscisse d'insertion du feu sur la stop bar	
	 * @param yi 	correspond à l'ordonnée d'insertion du feu sur la stop bar	
	 * @param allumer	correspond à l'état allumé ou non du feu.
	 * @param permanent	correpond à l'état permanent ou non du feu
	 * @param g    	Objet graphique sur lequel doit etre dessiné la StopBar
	 * */
	private void dessinUnFeu(Graphics g, int xi, int yi, boolean allumer, boolean permanent) {
		g.setColor(Color.BLACK);
		CirclePanel.drawCircle(g, (int)xi, (int)yi, rayon_par_feu);
		g.setColor(Color.GREEN);
		CirclePanel.fillCircle(g, (int)xi, (int)yi, rayon_par_feu);

		if (allumer)
		{
			if (permanent)
			{
				g.setColor(Color.RED);
				CirclePanel.fillCircle(g, (int)xi, (int)yi, rayon_par_feu);
				g.setColor(Color.BLACK);
				CirclePanel.drawCircle(g, (int)xi, (int)yi, rayon_par_feu-2);
			}
			else 
			{
				g.setColor(Color.RED);
				CirclePanel.fillCircle(g, (int)xi, (int)yi, rayon_par_feu);
			}
		}
	}

	/** 
	 * Dessine les StopBar sur la frame de l'aeroport.
	 *
	 *@param aeroport correspond à la plateforme (aeroport) sur laquelle on dessine les stopBar
	 *@param g2	Objet graphique sur lequel doit etre dessiné la StopBar
	 * */
	public void dessine(Aeroport aeroport, Graphics2D g2) {

		for(StopBar sb : aeroport.get_StopBar())
		{
			dessineStopBar(sb, g2);
		}
	}
}
