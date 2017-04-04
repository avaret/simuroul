package fr.iessa.vue.infra;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import fr.iessa.metier.infra.Aeroport;
import fr.iessa.metier.infra.StopBar;
import fr.iessa.vue.CirclePanel;


public class StopBarDrawer {

	public StopBarDrawer() {
		// TODO Auto-generated constructor stub
	}
	
	

	public void dessineStopBar(StopBar sb, Graphics g)
	{
		int decalage_pixels_x_par_cercle=(int) (2*(((float)sb.getRayon())*1.2)); // decalage des ronds
		char[] lettre_BA = new char[1]; // variable pour afficher la lettre C ou P

		// barre d'arret permanente ou controlable

		if (sb.isPermanent()==true)
		{
			// affiche le "P"
			sb.setAllumer(true);
			lettre_BA[0] = 'P';
		}
		else
		{
			// affiche le "C"
			sb.setAllumer(false); // BA non allumé afin de voir la difference avec la barre permanente
			lettre_BA[0] = 'C';
		}

		int yi = sb.getY0()+50;

		// allumer ou pas la barre d'arret

		g.drawChars(lettre_BA, 0, 1, sb.getX0()+decalage_pixels_x_par_cercle*sb.getNb()+sb.getRayon(), yi);

		int no=0; // variable pour indiquer le n° de cercle
		while(no<sb.getNb())
		{
			g.setColor(Color.BLACK);
			int xi = sb.getX0()+decalage_pixels_x_par_cercle*no;


			CirclePanel.drawCircle(g, xi, yi, sb.getRayon());
			if (sb.isAllumer() == true)
			{
				g.setColor(Color.RED);
				CirclePanel.fillCircle(g, xi, yi, sb.getRayon());
			}


			//decalage_pixels_x_par_cercle+=10;
			no++;

		}

	}

	public void dessine(Aeroport aeroport, Graphics2D g2) {
		System.out.println(" dessine les StopBar ");
		for(StopBar sb : aeroport.get_StopBar())
		{
			System.out.println(" Affichage du StopBar x=" + sb.getX0() + " y=" + sb.getY0() );
			//affiche une stopbar
			dessineStopBar(sb, g2);
		}
	}
}
