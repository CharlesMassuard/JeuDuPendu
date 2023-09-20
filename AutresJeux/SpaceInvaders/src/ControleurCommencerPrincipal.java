import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

/**Controleur servant à lancer le jeu avec un appui sur le bouton*/
public class ControleurCommencerPrincipal implements EventHandler<ActionEvent>{ 

    private Executable appli;
    
    /**Créer le Controleur
     * @param appli l'Executable, qui appel ce Controleur
     */
    public ControleurCommencerPrincipal(Executable appli){
        this.appli = appli;
    }

    /**Permet de lancer l'action
     * @param event l'event lancé
     * Lancement de la méthode commencer() de la classe Executable
     * Si cette méthode renvoie true, la fenêtre de l'Executable est fermée
    */
    @Override
    public void handle(ActionEvent event) {
        if(this.appli.commencer()){
            Node n = (Node) event.getSource();
            Stage stage = (Stage) n.getScene().getWindow();
            stage.close();
        }
    }
}