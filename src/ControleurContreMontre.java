import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Contrôleur du chronomètre
 */
public class ControleurContreMontre implements EventHandler<ActionEvent> {
    /**
     * temps enregistré lors du dernier événement
     */
    private long tempsPrec;
    /**
     * temps écoulé depuis le début de la mesure
     */
    private long tempsEcoule;
    /**
     * Vue du chronomètre
     */
    private ChronoContreLaMontre chrono;

    /**
     * Constructeur du contrôleur du chronomètre
     * noter que le modèle du chronomètre est tellement simple
     * qu'il est inclus dans le contrôleur
     * @param chrono Vue du chronomètre
     */

    public ControleurContreMontre(ChronoContreLaMontre chrono){
        this.chrono = chrono;
        this.tempsEcoule = 0;
        this.tempsPrec = -1; // le Chrono n'est pas encore lancé                                                                                                                                                                          
    }

    @Override
    public void handle(ActionEvent t) {
        // on récupère l'heure en millisecondes                                                                                                                                                                                              
        long heureDuSysteme = System.currentTimeMillis();
        if (this.tempsPrec != -1){
            // calcul du tps écoulé depuis la dernière frame                                                                                                                                                                                 
            tempsEcoule += heureDuSysteme - this.tempsPrec;
            this.chrono.setTime(tempsEcoule/1000);
        }
        this.tempsPrec = heureDuSysteme;
        }


    /**
     * Remet la durée à 0
     */        // this.timeline.getKeyFrames().add(fen);
    public void reset(){
        this.tempsEcoule = 0;
        this.tempsPrec = -1;
    } 
}
