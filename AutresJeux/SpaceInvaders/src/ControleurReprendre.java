import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

/**Controlleur permettant de reprendre la partie */
public class ControleurReprendre implements EventHandler<ActionEvent>{ 

    private MenuPause appli;
    
    /**Création du controleur
    * @param appli la classe MenuPause, qui appel ce Controleur
    */
    public ControleurReprendre(MenuPause appli){
        this.appli = appli;
    }

    /**Permet de lancer l'action
     * @param event l'event lancé
     * Lance la méthode reprendre() de la classe MenuPause (reprend la partie en cours)
    */
    @Override
    public void handle(ActionEvent event) {
        this.appli.reprendre();
        Node n = (Node) event.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}