import javafx.event.EventHandler;
import javafx.event.ActionEvent;


/**Controleur permettant de quitter l'application */
public class ControleurQuitter implements EventHandler<ActionEvent>{ 

    private Executable appli;
    
    /**Création du controleur
    * @param appli la classe Executable, qui appel ce Controleur
    */
    public ControleurQuitter(Executable appli){
        this.appli = appli;
    }

    /**Permet de lancer l'action
     * @param event l'event lancé
     * Lance la méthode quitter() de l'Executable
    */
    @Override
    public void handle(ActionEvent event) {
        this.appli.quitter();
    }
}