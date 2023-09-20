import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * Contrôleur à activer lorsque l'on clique sur le bouton paramètres
 */
public class ControleurParametres implements EventHandler<ActionEvent> {
    /**
     * vue du jeu
     **/
    private Pendu vuePendu;

    /**
     * @param vuePendu vue du jeu
     */
    public ControleurParametres(Pendu vuePendu) {
        this.vuePendu = vuePendu;
    }

    /**
     * L'action consiste à ouvrir le menu paramètres.
     * @param actionEvent l'événement action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        vuePendu.modeParametres();
    }
}
