package fr.iessa.vue.infra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import fr.iessa.vue.BarreArret;
import fr.iessa.vue.PanelPrincipalMultiCouches;

public class PlateformePopupMenu extends JPopupMenu implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x_clic_souris;
	private int y_clic_souris;
	private JPanel panel;

	//La déclaration pour le menu contextuel
	private JPopupMenu menu_souris;

	private JMenuItem barre_Arret ;      

	//constructeurs
	public PlateformePopupMenu (JPanel pan){
		panel = pan;
		menu_souris = new JPopupMenu();
		barre_Arret = new JMenuItem("creation de la barre d'arret");
		menu_souris.add(barre_Arret);

		//listeners globaux
		barre_Arret.addActionListener(this);


		//On crée et on passe l'écouteur pour afficher le menu contextuel
		//Création d'une implémentation de MouseAdapter
		//avec redéfinition de la méthode adéquate
		pan.addMouseListener(new MouseAdapter(){

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
		});

		this.setVisible(true);


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		BarreArret ba = new BarreArret (x_clic_souris,y_clic_souris);
		ba.paintComponent(panel.getGraphics());
		// TODO Auto-generated method stub

	}
}