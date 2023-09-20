import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

/**Controleur servant à fermer le pop-up d'erreur du menu principal (Executable) */
public class ControleurOk implements EventHandler<ActionEvent>{ 

    private ErreurParametres appli;
    
    /**Création du controleur
     * @param appli la classe ErreurParametres, qui appel ce Controleur
     */
    public ControleurOk(ErreurParametres appli){
        this.appli = appli;
    }

    /**Permet de lancer l'action
     * @param event l'event lancé
     * Fermeture de la fenetre appartenant à la classe ErreurParametres (le pop-up d'erreur)
    */
    @Override
    public void handle(ActionEvent event) {
        Node n = (Node) event.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}