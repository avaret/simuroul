package fr.iessa.vue;
/**@author bouletcy
 * Frame de commande basique de l'avion piloté
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;

import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JPanel;

import fr.iessa.controleur.Controleur;
import fr.iessa.metier.trafic.VolAvionPilote;

public class FrameCommandeAvionPilote extends JFrame{


  public FrameCommandeAvionPilote(){

    this.setTitle("Box Layout");

    this.setSize(200, 100);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);


    JPanel b1 = new JPanel();

    //On définit le layout en lui indiquant qu'il travaillera en ligne

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

    //On positionne maintenant ces trois lignes en colonne

    b4.setLayout(new BoxLayout(b4, BoxLayout.PAGE_AXIS));
    b4.add(b1);
    b4.add(b3);

        

    this.getContentPane().add(b4);

    this.setVisible(true);

  
  
  
	 gauche.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent arg0){
			  double angle = Controleur.avionPilote.getAngle();
			  angle += 15;
			  Controleur.avionPilote.setAngle(angle);
		  }
	 });
	 
	 droite.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent arg0){
			  double angle = Controleur.avionPilote.getAngle();
			  angle -= 15;
			  Controleur.avionPilote.setAngle(angle);
		  }
	 });
	  
	 haut.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent arg0){
			  int vitesse = Controleur.avionPilote.getVitesse();
			  vitesse += 5;
			  if (vitesse > 20){
				  vitesse=20;
			  }
			  Controleur.avionPilote.setVitesse(vitesse);
			  
			  
		  }
	 });
	  
	 bas.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent arg0){
			  int vitesse = Controleur.avionPilote.getVitesse();
			  vitesse -= 5;
			  if (vitesse < 0){
				  vitesse=0;
			  }
			  Controleur.avionPilote.setVitesse(vitesse);
			  
			  
		  }
	 });
  }
}

