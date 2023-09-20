import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

/**Controleur permettant de retourner au menu principal*/
public class ControleurRetourMenu implements EventHandler<ActionEvent>{ 

    private MenuGagne appli;
    
    /**Création du controleur
    * @param appli la classe MenuGagne, qui appel ce Controleur
    */
    public ControleurRetourMenu(MenuGagne appli){
        this.appli = appli;
    }

    /**Permet de lancer l'action
     * @param event l'event lancé
     * Lance la méthode reconfig() de la classe MenuGagne (retour au menu principal)
    */
    @Override
    public void handle(ActionEvent event) {
        this.appli.reconfig();
        Node n = (Node) event.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}