import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

/**Controleur permettant de relancer une partie de zéro en gardant les mêmes paramètres */
public class ControleurRelancer implements EventHandler<ActionEvent>{ 

    private MenuGagne appli;
    
    /**Création du controleur
    * @param appli la classe MenuGame, qui appel ce Controleur
    */
    public ControleurRelancer(MenuGagne appli){
        this.appli = appli;
    }

    /**Permet de lancer l'action
     * @param event l'event lancé
     * Lance la méthode recommencer() de la classe MenuGagne (commence une nouvelle partie)
    */
    @Override
    public void handle(ActionEvent event) {
        this.appli.recommencer();
        Node n = (Node) event.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}