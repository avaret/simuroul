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

	private final static int nb =10; //Nombre de cercles fix
	private final static int rayon =3; // diam des cercles
	private final static int phy= (int) (2*(((float)rayon)*1.2));

	/** méthode */
	public void dessineStopBar(StopBar sb, Graphics g)
	{
		int i;
		int y;
		int theta;

		for (i=-5;i<5;i++)
		{
			int x=sb.getX0()+i*phy;
			if (i>=-3 && i<3)
			{
				y= sb.getY0()+phy;
			}
			else
			{
				y= sb.getY0();
			}

			theta = sb.getAngle();
			double x0=sb.getX0();
			double y0=sb.getY0();
			double x1b=x-x0;
			double y1b=y-y0;
			double theta_rad= ((double)theta)*Math.PI/180.0;
			double r=Math.sqrt(x1b*x1b+y1b*y1b);
			double phi;
			if(x1b==0)
			{
				if(y1b>=0)
					phi=0;
				else
					phi=Math.PI;
			}
			else
			{
				phi=Math.atan(y1b/x1b);
			}
			double x2=r*Math.cos(theta_rad+phi)+x0-x1b;
			double y2=r*Math.sin(theta_rad+phi)+y0-y1b;
			
			double test1=Math.cos(theta_rad+phi);
			double test2=Math.cos(theta_rad+phi)*r;
			double test3=r*Math.cos(theta_rad+phi)+x0-x1b;
			System.out.println("Math.sin(theta_rad+phi)     " + test1 +"   r*Math.sin(theta_rad+phi)    " + test2 +" r*Math.sin(theta_rad+phi)+y0-y1b      "+ test3);
			System.out.println(" P0(" + x0 + "," + y0 + ")" + " P1(" + x1b + "," + y1b + ")" + " P2(" + x2 + "," + y2 + ")" );
			System.out.println("r" +r +"phi"+phi+"theta_rad"+theta_rad);
			
			
			dessinUnFeu(g, (int)x2, (int)y2, sb.isAllumer(),sb.isPermanent());
		}
	}


	private void dessinUnFeu(Graphics g, int xi, int yi, boolean allumer, boolean permanent) {
		g.setColor(Color.BLACK);
		CirclePanel.drawCircle(g, xi, yi, rayon);
		g.setColor(Color.GREEN);
		CirclePanel.fillCircle(g, xi, yi, rayon);

		if (allumer)
		{
			if (permanent)
			{
				g.setColor(Color.RED);
				CirclePanel.fillCircle(g, xi, yi, rayon);
				g.setColor(Color.BLACK);
				CirclePanel.drawCircle(g, xi, yi, rayon-2);
			}
			else 
			{
				g.setColor(Color.RED);
				CirclePanel.fillCircle(g, xi, yi, rayon);
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
