import javax.management.ValueExp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;

/**
 * Controleur des radio boutons gérant le niveau
 */
public class ControleurTempsContreLaMontre implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu
     */
    private Pendu vuePendu;


    /**
     * @param modelePendu modèle du jeu
     */
    public ControleurTempsContreLaMontre(Pendu vuePendu) {
        this.vuePendu = vuePendu;
    }

    /**
     * gère le dark mode
     * @param actionEvent
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        ToggleButton toggleButton = (ToggleButton) actionEvent.getTarget();
        String nomDuRadiobouton = toggleButton.getText();
        if(nomDuRadiobouton.equals("10 secondes")){
            vuePendu.setTempsContreLaMontre(10);
        } else if(nomDuRadiobouton.equals("30 secondes")){
            vuePendu.setTempsContreLaMontre(30);
        } else if(nomDuRadiobouton.equals("1 minute")){
            vuePendu.setTempsContreLaMontre(60);
        } else {
            vuePendu.setTempsContreLaMontre(90);
        }
    }
}
