package fr.iessa.vue;

import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.LibereMemoire;
import fr.iessa.controleur.ModeleEvent;
import fr.iessa.metier.Instant.InstantFabrique;

/** Classe FramePrincipale
 * @author THOMAS Raimana
 * @version 1.0 
 *  
 * Modifiée par: @author GINEYS Christophe (IESSA 16)
 * Modification: Ajout d'une option pour ajouter un menu sauvegarder les BA et charger les BA
 *
 */

public class FramePrincipale extends JFrame implements PropertyChangeListener {
	/** La barre de menu */
	private JMenuBar _barreMenu;
	private JMenu _menu;
	private JMenuItem _menuChargerPlateForme;
	private JMenuItem _menuChargerTrafic;
	private JMenuItem _menuQuitter;
	private JMenu _menuOption;


	private JMenuItem menuChargerScenario;


	public static Controleur _controleur;
	private Echelle _echelle;

	//Charge la plateforme et le traffic à partir des arguments    
	private void chargerPlateformeTrafficAvecArguments(String fichierPlateforme, String fichierTraffic) {

		_controleur.chargerCarte(fichierPlateforme);

		_controleur.chargerTrafic(fichierTraffic);

	}

	/** Constructeur */
	public FramePrincipale(String[] args) {
		// Construction de la fenêtre principale



		super("Ground Trafic Control");
		this.setPreferredSize((new Dimension(800, 600)));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		// Construction de la barre de menu
		_barreMenu = new JMenuBar();
		_menu = new JMenu("Fichier");
		_menuChargerPlateForme = new JMenuItem("Charger plateforme");
		_menuChargerTrafic = new JMenuItem("Charger trafic");
		_menuChargerTrafic.setEnabled(false);;
		_menuQuitter = new JMenuItem("Quitter");

		_menuChargerPlateForme.addActionListener(new ActionChargerPlateForme());
		_menuChargerTrafic.addActionListener(new ActionChargerTrafic());
		_menuQuitter.addActionListener(new ActionQuitter());


		_menu.add(_menuChargerPlateForme);
		_menu.add(_menuChargerTrafic);
		_menu.add(_menuQuitter);
		_barreMenu.add(_menu);

		_menuOption = new JMenu("Options");
		_menuOption.setEnabled(false);

		//hodiqual
		JMenuItem menuAjoutVue = new JMenuItem("Ajout vue");
		menuAjoutVue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new FrameSecondaire(_controleur, _echelle);
			}
		});

		//hodiqual
		JCheckBoxMenuItem menuCollision = new JCheckBoxMenuItem("Collisions");
		menuCollision.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(menuCollision.isSelected())
					_controleur.showCollision(true);
				else
					_controleur.showCollision(false);
			}
		});

		//hodiqual
		JMenuItem menuSauvCollision = new JMenuItem("Sauvegarde collisions");
		menuSauvCollision.addActionListener(new ActionSauverCollisions());

		JMenuItem menuAviomSimu = new JMenuItem("Simulation pilote");
		menuAviomSimu.addActionListener(new ActionSimulationPilote());

		JMenuItem menuSauvegarderReplay = new JMenuItem("Sauvegarder le replay");
		menuSauvegarderReplay.addActionListener(new ActionSauvegarderReplay());

		JMenuItem menuChargerReplay = new JMenuItem("Charger un replay");
		menuChargerReplay.addActionListener(new ActionChargerReplay());

		JMenuItem menuStartReplay = new JMenuItem("Lancer le replay");
		menuStartReplay.addActionListener(new ActionStartReplay());

		JMenuItem menuCutReplay = new JMenuItem("Arreter le replay");
		menuCutReplay.addActionListener(new ActionCutReplay());

		/** 
		 * menu sauver les BA
		 */
		JMenuItem menuSauvStopBar = new JMenuItem("Sauvegarde Barre Arret");
		menuSauvStopBar.addActionListener(new ActionSauverBarreArret());


		/** 
		 * menu charger les BA
		 */
		JMenuItem menuChargerStopBar = new JMenuItem("Charger Barre Arret");
		menuChargerStopBar.addActionListener(new ActionChargerBarreArret());

		JMenuItem menuChargerScenario = new JMenuItem("Charger Scenario");
		menuChargerScenario.addActionListener(new ActionChargerScenario());


		_menuOption.add(menuAjoutVue);
		_menuOption.add(menuAviomSimu);
		_menuOption.add(menuCollision);
		_menuOption.add(menuSauvCollision);
		_menuOption.add(menuSauvegarderReplay);
		_menuOption.add(menuChargerReplay);
		_menuOption.add(menuStartReplay);
		_menuOption.add(menuCutReplay);
		_menuOption.add(menuSauvStopBar);
		_menuOption.add(menuChargerStopBar);
		_menuOption.add(menuChargerScenario);
		_barreMenu.add(_menuOption);

		// Création et configuration du controleur MVC
		_controleur = new Controleur();
		_echelle = new Echelle();
		this.getContentPane().add(new PanelPrincipalMultiCouches(_controleur,true, _echelle));
		final ModeleEvent[] evts = {ModeleEvent.CHARGEMENT_CARTE_FICHIER_DONE, 
				ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_DONE,
				ModeleEvent.CHARGEMENT_CARTE_FICHIER_ERREUR,
				ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_ERREUR,
				ModeleEvent.SAUVEGARDE_COLLISION_DONE,
				ModeleEvent.SAUVEGARDE_COLLISION_ERREUR};

		_controleur.ajoutVue(this, evts) ;

		//On ne fait plus apparaitre la FrameCommandeAvionPilote() ! Les commandes se font au clavier !!
		//Fait apparaitre les boutons de commande de l'avion
		//FrameCommande = new FrameCommandeAvionPilote();

		//Create and set up the content pane.
		this.validate();
		this.setJMenuBar(_barreMenu);
		this.pack();
		this.setVisible(true);

		// Test de la présence d'arguments et charge les fichiers
		if (args.length==2)
		{
			String fichierPlateforme=args[0];

			String fichierTraffic=args[1];

			chargerPlateformeTrafficAvecArguments(fichierPlateforme, fichierTraffic);
		}
	}



	/** Listeners */

	class ActionSauvegarderReplay implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {

			File fichierCollision = null;
			String nomFichierCollision = "";
			JFileChooser dialogue = new JFileChooser(new File("."));

			if (dialogue.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				fichierCollision = dialogue.getSelectedFile();
				nomFichierCollision = fichierCollision.getName();
				_controleur.getTrafic().get_premierVolAvionPilote().sauverReplay(dialogue.getCurrentDirectory()+"/"+nomFichierCollision + ".bin");
			}	
		}
	}
	class ActionChargerReplay implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {

			File fichierTrafic = null;
			JFileChooser dialogue = new JFileChooser(new File("."));

			if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				fichierTrafic = dialogue.getSelectedFile();
				_controleur.getTrafic().get_premierVolAvionPilote().chargerReplay(fichierTrafic.getPath());
			}

		}
	}
	class ActionStartReplay implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			_controleur.getTrafic().get_premierVolAvionPilote().setReplay(true);
		}
	}
	class ActionCutReplay implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			_controleur.getTrafic().get_premierVolAvionPilote().setReplay(false);
		}
	}
	class ActionChargerPlateForme implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			File fichierPlateForme = null;
			JFileChooser dialogue = new JFileChooser(new File("."));

			if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				fichierPlateForme = dialogue.getSelectedFile();
				_controleur.chargerCarte(fichierPlateForme.getPath());
			}
		}
	}

	/** 
	 * actionlistener pour charger les BA
	 */
	class ActionChargerBarreArret implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			File fichierBarreArret = null;
			JFileChooser dialogue = new JFileChooser(new File("."));

			if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				fichierBarreArret = dialogue.getSelectedFile();
				_controleur.getAeroport().chargerBarreArret(fichierBarreArret.getPath());
			}
		}
	}

	class ActionChargerScenario implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			File numeroScenario = null;
			JFileChooser dialogue = new JFileChooser(new File("."));

			if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				numeroScenario = dialogue.getSelectedFile();
				_controleur.chargerScenario(numeroScenario.getPath());
			}
		}
	}


	class ActionChargerTrafic implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			File fichierTrafic = null;
			JFileChooser dialogue = new JFileChooser(new File("."));

			if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				fichierTrafic = dialogue.getSelectedFile();
				_controleur.chargerTrafic(fichierTrafic.getPath());
			}

		}
	}

	/** 
	 * actionlistener pour sauver les BA
	 */
	class ActionSauverBarreArret implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			File fichierBarreArret = null;
			String nomFichierBarreArret = "";
			JFileChooser dialogue = new JFileChooser(new File("."));

			if (dialogue.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				fichierBarreArret = dialogue.getSelectedFile();
				nomFichierBarreArret = fichierBarreArret.getName();
				_controleur.getAeroport().sauverBarreArret(dialogue.getCurrentDirectory()+"/"+nomFichierBarreArret + ".txt");
			}	
		}
	}

	//hodiqual
	class ActionSauverCollisions implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			File fichierCollision = null;
			String nomFichierCollision = "";
			JFileChooser dialogue = new JFileChooser(new File("."));

			if (dialogue.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				fichierCollision = dialogue.getSelectedFile();
				nomFichierCollision = fichierCollision.getName();
				_controleur.sauvegarderCollision(dialogue.getCurrentDirectory()+"/"+nomFichierCollision + ".txt");
			}	
		}
	}

	class ActionQuitter implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}

	/* Va permettre de pouvoir choisir le scénario de la simulation */
	class ActionSimulationPilote implements ActionListener {
		public void actionPerformed(ActionEvent arg0){

		}
	}

	/** Actions à realiser lors d'un evenement du contrôleur */
	public void propertyChange(PropertyChangeEvent evt) {

		switch (ModeleEvent.valueOf(evt.getPropertyName())) {
		case CHARGEMENT_CARTE_FICHIER_DONE:
			_menuChargerTrafic.setEnabled(true);
			break;

		case CHARGEMENT_TRAFIC_FICHIER_DONE:
			_menuOption.setEnabled(true);
			//hodiqual
			KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
			manager.addKeyEventDispatcher(new KeyDispatcher());
			break;

		case CHARGEMENT_CARTE_FICHIER_ERREUR:
			JOptionPane.showMessageDialog(null, "Chargement echoue : " + evt.getNewValue(), "" , JOptionPane.ERROR_MESSAGE);
			break;

		case CHARGEMENT_TRAFIC_FICHIER_ERREUR:
			JOptionPane.showMessageDialog(null, "Chargement echoue : " + evt.getNewValue(), "" , JOptionPane.ERROR_MESSAGE);
			break;

			//hodiqual	
		case SAUVEGARDE_COLLISION_DONE:
			JOptionPane.showMessageDialog(null, "Sauvegarde des collisions effectuées dans " + evt.getNewValue(), "" , JOptionPane.INFORMATION_MESSAGE);
			break;

			//hodiqual
		case SAUVEGARDE_COLLISION_ERREUR:
			JOptionPane.showMessageDialog(null, "Savegarde des collisions echouée : " + evt.getNewValue(), "" , JOptionPane.ERROR_MESSAGE);
			break;

		default:
			break;
		}

	}

	//hodiqual
	private class KeyDispatcher implements KeyEventDispatcher {
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getID() == KeyEvent.KEY_PRESSED ) {
				if(e.getKeyCode() == KeyEvent.VK_RIGHT) {	
					_controleur.setInstant(_controleur.getInstantCourant()+InstantFabrique._pasEntreInstant);
					return true;
				}else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					_controleur.setInstant(_controleur.getInstantCourant()-InstantFabrique._pasEntreInstant);
					return true;
				}else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if(_controleur.isTraficRunning())
						_controleur.stopTrafic();
					else
						_controleur.runTrafic();

					return true;
				}else if (e.getKeyCode() == KeyEvent.VK_M) { 
					JOptionPane.showMessageDialog(null, "Tips memoire :" + LibereMemoire.controleMemoire() + " Mo", "" , JOptionPane.INFORMATION_MESSAGE);
					return true;
				}

				return false;
			}
			else
				return false;
		}
	}

}
