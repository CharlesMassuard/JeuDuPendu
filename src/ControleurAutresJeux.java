import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.Cursor;

/**
 * Controleur pour lancer un autre jeu
 */
public class ControleurAutresJeux implements EventHandler<ActionEvent> {

    /**
     * vue du jeu
     */
    private Pendu vuePendu;

    /**
     * @param vuePendu vue du jeu
     */
    ControleurAutresJeux(Pendu vuePendu){
        this.vuePendu = vuePendu;
    }

    /**
     * Lancer un autre jeu
     * @param actionEvent l'événement
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getTarget();
        String nomDuRadiobouton = button.getText();
        if(nomDuRadiobouton.equals("Space Invaders")){
            vuePendu.lancerSpaceInvaders();
        }
        if(nomDuRadiobouton.equals("Démineur")){
            vuePendu.lancerDemineur();
        }
    }
}
