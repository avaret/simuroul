package fr.iessa.app;

import javax.swing.UIManager;
import javax.swing.UIManager.*;

import aurelienribon.slidinglayout.SLAnimator;
import fr.iessa.dao.infra.PlateformeDAO;
import fr.iessa.metier.infra.Aeroport;
import fr.iessa.vue.FramePrincipale;

/**
 * Main de l'application.
 * @author hodiqual
 *
 */
public class Application {

	public static void main(String[] args) {
		// System.out.println("blablabla" + args[0]);
		// si exactement 2 args, récup dans 2 var "fichierPlateforme" et "fichierTraffic"
		// puis afficher les valeurs
		
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    //TODO Log4j -- Nimbus pas possible
			System.out.println("AEROPORT");
		}
		
	
		SLAnimator.start();
		
        //Creer et fait apparaitre l'application dans le thread EDT
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	
        		FramePrincipale frame = new FramePrincipale(args);
        		
        }
        });
	}

}
