package fr.iessa.vue;
import java.awt.KeyEventDispatcher;
/**@author bouletcy
 * Frame de commande basique de l'avion piloté
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.LibereMemoire;
import fr.iessa.metier.Instant.InstantFabrique;
import fr.iessa.metier.trafic.VolAvionPilote;

/**
 * 
 * @author bouletcy
 * 
 * Premier essaie des commandes avionPilote, destiné a ne plus etre utilisé par la suite.
 * Ne permet de commander que le premier avionPilote
 */
public class FrameCommandeAvionPilote extends JFrame{

	public FrameCommandeAvionPilote(){

		this.setTitle("Box Layout");

		this.setSize(200, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);


		JPanel b1 = new JPanel();

		b1.setLayout(new BoxLayout(b1, BoxLayout.LINE_AXIS));

		JButton haut = new JButton("Haut");
		b1.add(haut);


		JPanel b3 = new JPanel();

		b3.setLayout(new BoxLayout(b3, BoxLayout.LINE_AXIS));
		JButton gauche = new JButton("Gauche");
		b3.add(gauche);
		JButton bas = new JButton("Bas");
		b3.add(bas);
		JButton droite = new JButton("Droite");
		b3.add(droite);


		JPanel b4 = new JPanel();

		b4.setLayout(new BoxLayout(b4, BoxLayout.PAGE_AXIS));
		b4.add(b1);
		b4.add(b3);



		this.getContentPane().add(b4);

		this.setVisible(true);




		gauche.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				FramePrincipale._controleur.getTrafic().get_premierVolAvionPilote().RotationGauche();
			}
		});

		
		droite.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				FramePrincipale._controleur.getTrafic().get_premierVolAvionPilote().RotationDroite();
			}
		});
		
		haut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				FramePrincipale._controleur.getTrafic().get_premierVolAvionPilote().Accelerer();
			}
		});
		
		bas.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				FramePrincipale._controleur.getTrafic().get_premierVolAvionPilote().Ralentir();
			}
		});
	}
	
}

