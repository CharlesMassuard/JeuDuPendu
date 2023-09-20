import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

/**Controleur servant à lancer le jeu avec un appui sur la touche Entrée*/
public class ControleurDebutEntree implements EventHandler<KeyEvent>{ 

    private Executable appli;
    
    /**Créer le Controleur
     * @param appli l'Executable, qui appel ce Controleur
     */
    public ControleurDebutEntree(Executable appli){
        this.appli = appli;
    }
    
    /**Permet de lancer l'action
     * @param e l'event lancé
     * Si la touche appuyée est bien égale à la touche "ENTER",
     * Lancement de la méthode commencer() de la classe Executable
     * Si cette méthode renvoie true, la fenêtre de l'Executable est fermée
    */
    @Override
    public void handle(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)){
            if(this.appli.commencer()){
                Node n = (Node) e.getSource();
                Stage stage = (Stage) n.getScene().getWindow();
                stage.close();
            }
        }
    }
          
}