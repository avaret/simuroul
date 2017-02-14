package fr.iessa.vue;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.iessa.controleur.Controleur;
import fr.iessa.vue.trafic.ComponentVol;


/**
 * Classe FramePilote
 * @author bernarti
 * */

public class FramePilote extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private JPanel jpanelPilote;
	private Echelle _echellePilote;
	private int _zoomPilote = 50;
	
	public FramePilote(Controleur controleur)
	{
		super("Vue Pilote");
		
	   	this.setPreferredSize((new Dimension(800, 600)));
	   	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	   	
	   	_echellePilote = new Echelle();
	   	//_echellePilote = FramePrincipale._echelle;
	   	jpanelPilote = new PanelPrincipalMultiCouches(controleur, false, _echellePilote);
	   	this.getContentPane().add(jpanelPilote);
	   	
	    //Create and set up the content pane.
	    this.validate();
		this.pack();
		this.setVisible(false);
	}
	
	public void VuePilote(int X, int Y)
	{	
		System.out.println("Position X: " + X + " / Y: " + Y);			
		Point positionPilote = new Point(1+X, 9+Y);	// Pourquoi ces valeurs fonctionnent-elles ???
		_echellePilote.setZoomLevel(_zoomPilote, positionPilote, getWidth(), getHeight());
		this.setVisible(true);
	}
}
