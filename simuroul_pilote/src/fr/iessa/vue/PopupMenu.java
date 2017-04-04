package fr.iessa.vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import fr.iessa.controleur.Controleur;
import fr.iessa.metier.infra.Point;
import fr.iessa.metier.infra.StopBar;
import fr.iessa.vue.infra.PanelPlateforme;
import fr.iessa.vue.infra.StopBarDrawer;

public class PopupMenu extends JPopupMenu implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x_clic_souris;
	private int y_clic_souris;
	private Echelle echelle1;
	
	private JPanel panel;
	
	PanelPlateforme plateforme;

	//La déclaration pour le menu contextuel
	public JPopupMenu menu_souris = new JPopupMenu();

	private JMenuItem barre_Arret ; 
	
	private Controleur controleur;
	
	//private ChargeEnCoursLayerUI layerUI;

	//constructeurs
	public PopupMenu (PanelPlateforme plateforme, Echelle ech, Controleur controleur, int x, int y){

		echelle1= ech;
		x_clic_souris = x;
		y_clic_souris = y;
		this.controleur = controleur;
		this.plateforme = plateforme;
		
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


		//On crée et on passe l'écouteur pour afficher le menu contextuel
		//Création d'une implémentation de MouseAdapter
		//avec redéfinition de la méthode adéquate
		/*pan.addMouseListener(new MouseAdapter(){

			public void mouseReleased(MouseEvent event){


				//Seulement s'il s'agit d'un clic droit
				//if(event.getButton() == MouseEvent.BUTTON3)
				x_clic_souris = event.getX();
				y_clic_souris = event.getY();
				
				

				if(event.isPopupTrigger()){       
					menu_souris.getAccessibleContext();



					//La méthode qui va afficher le menu
					menu_souris.show(pan, event.getX(), event.getY());

				}
			}
		});*/

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		
		//Boîte du message d'information
		JOptionPane jop = new JOptionPane();
		
		switch (e.getActionCommand().trim()) {
		case ("AJOUTER de la barre d'arret permanente"):
			StopBar sb = new StopBar(x_clic_souris, y_clic_souris);
			controleur.getAeroport().add(sb );
			System.out.println(" SB créée ! x=" + x_clic_souris);
			plateforme.update(null, null);
			break;
		
		case ("AJOUTER de la barre d'arret commandable eteinte"):
			jop.showMessageDialog(null, "ajout BA CE", "Information", JOptionPane.INFORMATION_MESSAGE);
			break;
			
		case ("ajouter de la barre d'arret commandable allumee"):
			jop.showMessageDialog(null, "ajout BA CA", "Information", JOptionPane.INFORMATION_MESSAGE);
			break;
			
		case ("SUPPRIMER de la barre d'arret"):	
		jop.showMessageDialog(null, "supprimer BA", "Information", JOptionPane.INFORMATION_MESSAGE);
			break;
		
		default:
			break;
		}
		// TODO Auto-generated method stub

	}
}	
	
	