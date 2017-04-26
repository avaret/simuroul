
package fr.iessa.vue;

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
	}


	/** 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String texte=FramePrincipale._controleur.scenario.afficherParole((int)_controleur.getInstantCourant());
		setText(texte);
	}
}