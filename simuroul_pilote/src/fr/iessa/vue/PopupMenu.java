package fr.iessa.vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import fr.iessa.controleur.Controleur;
import fr.iessa.metier.infra.Point;
import fr.iessa.metier.infra.StopBar;
import fr.iessa.vue.infra.PanelPlateforme;
import fr.iessa.vue.infra.StopBarDrawer;

import java.awt.geom.NoninvertibleTransformException;

/** Class PopupMenu qui permet de creer un popup menu pour mettre en place des barres d'arret et de les modifier
 * @author GINEYS Christophe 
 * @version 1.0 
 */

public class PopupMenu extends JPopupMenu implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Echelle echelle1;
	Point2D.Double coordSouris = new Point2D.Double(0, 0);
	Point2D.Double p = new Point2D.Double(0, 0);
	Point2D.Double p_abs = new Point2D.Double(0, 0);

	private JPanel panel;

	PanelPlateforme plateforme;

	//La déclaration pour le menu contextuel
	public JPopupMenu menu_souris = new JPopupMenu();

	private JMenuItem barre_Arret ; 

	private Controleur controleur;

	//private ChargeEnCoursLayerUI layerUI;

	//constructeurs
	public PopupMenu (PanelPlateforme plateforme, Echelle ech, Controleur controleur, int x_clic_souris, int y_clic_souris){

		echelle1= ech;
		coordSouris.x = x_clic_souris;
		coordSouris.y = y_clic_souris;

		this.controleur = controleur;
		this.plateforme = plateforme;

		barre_Arret = new JMenuItem("AJOUTER un Avion Piloté");
		menu_souris.add(barre_Arret);
		barre_Arret.setActionCommand("AJOUTER un Avion Piloté");
		barre_Arret.addActionListener(this);
		
		barre_Arret = new JMenuItem("AJOUTER de la barre d'arret permanente");
		menu_souris.add(barre_Arret);
		barre_Arret.setActionCommand("AJOUTER de la barre d'arret permanente");
		barre_Arret.addActionListener(this);

		barre_Arret = new JMenuItem("AJOUTER de la barre d'arret commandable eteinte");
		menu_souris.add(barre_Arret);
		barre_Arret.setActionCommand("AJOUTER de la barre d'arret commandable eteinte");
		barre_Arret.addActionListener(this);

		barre_Arret = new JMenuItem("ajouter de la barre d'arret commandable allumee");
		menu_souris.add(barre_Arret);
		barre_Arret.setActionCommand("ajouter de la barre d'arret commandable allumee");
		barre_Arret.addActionListener(this);

		barre_Arret = new JMenuItem("SUPPRIMER de la barre d'arret");
		menu_souris.add(barre_Arret);
		barre_Arret.setActionCommand("SUPPRIMER de la barre d'arret");
		barre_Arret.addActionListener(this);


	}

	@Override
	public void actionPerformed(ActionEvent e) {

		this.menu_souris.setVisible(true);

		//Boîte du message d'information
		JOptionPane jop = new JOptionPane();

		try {
			echelle1.getAffineTransform().inverseTransform(coordSouris, p_abs);
		} catch (NoninvertibleTransformException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		switch (e.getActionCommand().trim()) {
		case("AJOUTER un Avion Piloté"):
			FramePilote VuePilote = new FramePilote(this.controleur);
			VuePilote.ActualiserVuePilote(coordSouris, 48, 48);
			break;
		
		case ("AJOUTER de la barre d'arret permanente"):
		{
			StopBar sb = new StopBar((int)p_abs.x, (int)p_abs.y);

			controleur.getAeroport().add(sb);
			//System.out.println("SB créée ! x=" + x_clic_souris + "/ y=" + y_clic_souris);
			plateforme.update(null, null);
			break;
		}
		case ("AJOUTER de la barre d'arret commandable eteinte"):
		{
			//jop.showMessageDialog(null, "ajout BA CE", "Information", JOptionPane.INFORMATION_MESSAGE);


			StopBar sb = new StopBar((int)p_abs.x, (int)p_abs.y);
			controleur.getAeroport().add(sb);
			sb.setAllumer(false);
			sb.setPermanent(false);
			plateforme.update(null, null);
			break;
		}
		case ("ajouter de la barre d'arret commandable allumee"):
		{
			//jop.showMessageDialog(null, "ajout BA CA", "Information", JOptionPane.INFORMATION_MESSAGE);
			StopBar sb = new StopBar((int)p_abs.x, (int)p_abs.y);
			controleur.getAeroport().add(sb);
			sb.setAllumer(true);
			sb.setPermanent(false);
			plateforme.update(null, null);
			break;
		}
		case ("SUPPRIMER de la barre d'arret"):	
		{
			//jop.showMessageDialog(null, "supprimer BA", "Information", JOptionPane.INFORMATION_MESSAGE);
			
			//chercher la stopbar la plus proche
			double dist_plus_courte = 9999999;
			StopBar sb_min = null;

			for (StopBar sb1 :controleur.getAeroport().get_StopBar()) {

				double distance_list = sb1.distance(p_abs);
				if (distance_list < dist_plus_courte)
				{
					dist_plus_courte = distance_list;
					sb_min =sb1;
				}
				
			} 
			
			//supprimer la stopbar 
			controleur.getAeroport().get_StopBar().remove(sb_min);
			plateforme.update(null, null);
			break;
		}
		default:
			break;
		}
		//sert à supprimer la fenetre du popup
		this.menu_souris.setVisible(false);
		
		this.menu_souris.setVisible(false);
	}
}	


