import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Permet de gérer un Text associé à une Timeline pour afficher un temps écoulé
 */
public class Chronometre extends Text{
    /**
     * timeline qui va gérer le temps
     */
    private Timeline timeline;
    /**
     * la fenêtre de temps
     */
    private KeyFrame keyFrame;
    /**
     * le contrôleur associé au chronomètre
     */
    private ControleurChronometre actionTemps;
    private long tempsEnChiffres;


    /**
     * Constructeur permettant de créer le chronomètre
     * avec un label initialisé à "0:0:0"
     * Ce constructeur créer la Timeline, la KeyFrame et le contrôleur
     */
    public Chronometre(){
        super.setText("0");
        super.setFont(Font.font(32));
        this.actionTemps = new ControleurChronometre(this);
        KeyFrame fen = new KeyFrame(Duration.millis(100), actionTemps);
        this.timeline = new Timeline(fen);
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public String getTime(){
        return getText();
    }

    public long getTimeChiffres(){
        return this.tempsEnChiffres;
    }

    /**
     * Permet au controleur de mettre à jour le text
     * la durée est affichée sous la forme m:s
     * @param tempsMillisec la durée depuis à afficher
     */
    public void setTime(long tempsMillisec){
        long tpsEnMinutes = tempsMillisec/60;
        this.tempsEnChiffres = tempsMillisec;
        if(tpsEnMinutes <= 0){
            setText(""+tempsMillisec%60+"s");
        } else {
            setText(tpsEnMinutes+"min"+tempsMillisec%60+"s");
        }
    }

    /**
     * Permet de démarrer le chronomètre
     */
    public void start(){
        timeline.play();
    }

    /**
     * Permet d'arrêter le chronomètre
     */
    public void stop(){
        timeline.stop();
    }

    /**
     * Permet de remettre le chronomètre à 0
     */
    public void resetTime(){
        timeline.stop();
        this.setTime(0);
        actionTemps.reset();
    }
}
