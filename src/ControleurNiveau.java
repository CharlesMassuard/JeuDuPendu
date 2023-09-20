import javax.management.ValueExp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;

/**
 * Controleur des radio boutons gérant le niveau
 */
public class ControleurNiveau implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    private Pendu vuePendu;


    /**
     * @param modelePendu modèle du jeu
     */
    public ControleurNiveau(MotMystere modelePendu, Pendu vuePendu) {
        this.modelePendu = modelePendu;
        this.vuePendu = vuePendu;
    }

    /**
     * gère le changement de niveau
     * @param actionEvent
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        RadioButton radiobouton = (RadioButton) actionEvent.getTarget();
        String nomDuRadiobouton = radiobouton.getText();
        if(nomDuRadiobouton.equals("Facile")){
            modelePendu.setNiveau(0);
            vuePendu.setNiveau("facile");
        } else if(nomDuRadiobouton.equals("Moyen")){
            modelePendu.setNiveau(1);
            vuePendu.setNiveau("moyen");
        } else if(nomDuRadiobouton.equals("Difficile")){
            modelePendu.setNiveau(2);
            vuePendu.setNiveau("difficile");
        } else if(nomDuRadiobouton.equals("Expert")){
            modelePendu.setNiveau(3);
            vuePendu.setNiveau("expert");
        } else {
            modelePendu.setNiveau(1);
            vuePendu.setNiveau("Contre la montre");
        }
        System.out.println(nomDuRadiobouton);
    }
}
