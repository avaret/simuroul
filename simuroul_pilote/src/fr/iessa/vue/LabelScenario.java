
package fr.iessa.vue;

import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JLabel;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.ModeleEvent;
import fr.iessa.metier.Horloge;
import fr.iessa.metier.Scenario;

/** Class LabelScenario : Affichage des paroles du controleur et du pilote dans la fenetre principale et la fenetre pilote.
 * @author DONG Justine 
 * @version 1.0
 * 
 */

public class LabelScenario extends JLabel implements PropertyChangeListener {

	String nomScenario;
	int i;
	int debut;
	private Controleur _controleur;
	private Horloge _horloge;

	public LabelScenario(Controleur controleur)
	{
		super(" ");

		this._controleur=controleur;

		Font fontParDefaut = getFont();
		setFont(new Font(fontParDefaut.getName(), Font.BOLD, fontParDefaut.getSize()));

		final ModeleEvent[] evts = {ModeleEvent.UPDATE_INSTANT};
		controleur.ajoutVue(this,  evts) ;
		
		this.setPreferredSize(new Dimension(440,80));
		
		
		
	}


	/** 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String s = " ";

		final String html1 = "<html><body style='width: ";

		final String html2 = "px'>";

		new JLabel(html1 + "200" + html2 + s); /* 200 est la largeur du JLabel */

		
		System.out.println("temps: "+((int)_controleur.getInstantCourant()));
		String texte_prin=FramePrincipale._controleur.scenario.afficherParole((int)_controleur.getInstantCourant());
		setText(html1 + "200" + html2 + texte_prin);

	}
}