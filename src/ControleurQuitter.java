import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * Controleur pour quitter le jeu
 */
public class ControleurQuitter implements EventHandler<ActionEvent> {

    /**
     * vue du jeu
     */
    private Pendu vuePendu;

    /**
     * @param vuePendu vue du jeu
     */
    ControleurQuitter(Pendu vuePendu){
        this.vuePendu = vuePendu;
    }

    /**
     * Quitter le jeu
     * @param actionEvent l'événement
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        vuePendu.quitterJeu();
    }
}
