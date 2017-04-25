package fr.iessa.vue.infra;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.List;

import fr.iessa.metier.infra.Aeroport;
import fr.iessa.metier.infra.StopBar;
import fr.iessa.vue.CirclePanel;

/** Class StopBarDrawer qui permet de dessiner la barre d'arret.
 * @author GINEYS Christophe 
 * @version 1.0 
 */

public class StopBarDrawer {

	private final static int nb_feux =10; //Nombre de cercles fix
	private final static int rayon_par_feu =3; // diam des cercles
	private final static double espacement_entre_feux=(double)(2*(((float)rayon_par_feu)*1.2));

	/** méthode */
	public void dessineStopBar(StopBar sb, Graphics g)
	{
		int i;
		double y;
		double theta;

		for (i=-5;i<5;i++)
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
			double phi;


			/////////////////////////////////////////////
			System.out.println(" x="+x+" y="+y);
			if(y==0)
			{
				if(x>=0)
					phi=0;
				else
					phi=Math.PI;
			}
			else
			{
				double a=y/x;
				phi=Math.atan(a);
			}
			/////////////////////////////////////////////
			

			System.out.println(" r="+r+" theta="+theta_rad + " phi="+phi + " a = "+ (theta_rad+phi));
			System.out.println(" r cos a = "+r*Math.cos(theta_rad+phi)+ "  r sin a="+r*Math.sin(theta_rad+phi));

			double x2=r*Math.cos(theta_rad+phi)+x0;
			double y2=r*Math.sin(theta_rad+phi)+y0;

			dessinUnFeu(g, x2, y2, sb.isAllumer(),sb.isPermanent());
		}
	}


	private void dessinUnFeu(Graphics g, double xi, double yi, boolean allumer, boolean permanent) {
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
