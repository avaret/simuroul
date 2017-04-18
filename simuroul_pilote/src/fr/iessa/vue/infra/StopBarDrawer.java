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
			
			//TODO donner l'angle 
			theta = sb.getAngle();
			double x2=(double)x+((double)x-(double)sb.getX0())*Math.cos(((double)theta)*Math.PI/180.0);
			double y2=(double)y+((double)y-(double)sb.getY0())*Math.sin(((double)theta)*Math.PI/180.0);
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
