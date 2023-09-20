import javax.management.ValueExp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;

/**
 * Controleur des radio boutons gérant le niveau
 */
public class ControleurDarkMode implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu
     */
    private Pendu vuePendu;


    /**
     * @param modelePendu modèle du jeu
     */
    public ControleurDarkMode(Pendu vuePendu) {
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
        if(nomDuRadiobouton.equals("Activé")){
            vuePendu.setDarkMode(true);
        } else {
            vuePendu.setDarkMode(false);
        }
    }
}
