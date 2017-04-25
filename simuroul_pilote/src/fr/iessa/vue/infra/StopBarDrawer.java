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
	 * 	dessineStopBar sert à BLABLABLA TODO
	 * 
	 * @param sb 	TODO A quoi ca sert
	 * @param g    	TODO A quoi ca sert 
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
	 * 	dessinUnFeu sert à BLABLABLA TODO
	 * 
	 * @param xi 	TODO A quoi ca sert
	 * @param g    	TODO A quoi ca sert 
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

	public void dessine(Aeroport aeroport, Graphics2D g2) {

		for(StopBar sb : aeroport.get_StopBar())
		{
			dessineStopBar(sb, g2);
		}
	}
}
