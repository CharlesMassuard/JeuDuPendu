import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.application.Platform;
import javafx.scene.paint.Color;

/**
 * Permet de gérer un Text associé à une Timeline pour afficher un minuteur
 */
public class ChronoContreLaMontre extends Text{
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
    private ControleurContreMontre actionTemps;
    private Long tempsEnChiffres;
    private Pendu vuePendu;


    /**
     * Constructeur permettant de créer le minuteur
     * Ce constructeur créer la Timeline, la KeyFrame et le contrôleur
     */
    public ChronoContreLaMontre(Pendu vuePendu){
        super.setText("1min0s");
        super.setFont(Font.font(32));
        this.vuePendu = vuePendu;
        this.actionTemps = new ControleurContreMontre(this);
        KeyFrame fen = new KeyFrame(Duration.millis(100), actionTemps);
        this.timeline = new Timeline(fen);
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Permet de récupérer le temps sous la forme d'un String
     * @return le temps sous la forme d'un String
     */
    public String getTime(){
        return getTimeChiffres()+"";
    }

    /**
     * Permet de récupérer le temps sous la forme d'un Long
     * @return le temps sous la forme d'un Long
     */
    public Long getTimeChiffres(){
        return this.tempsEnChiffres;
    }

    /**
     * Permet au controleur de mettre à jour le text
     * la durée est affichée sous la forme m:s
     * @param tempsMillisec la durée depuis à afficher
     */
    public void setTime(Long tempsMillisec){
        Long temps = vuePendu.getTempsContreLaMontre()-tempsMillisec;
        long tpsEnMinutes = temps/60;
        this.tempsEnChiffres = tempsMillisec;
        if(temps == 5){
            setFill(Color.RED);
        }
        if(temps == 60 && vuePendu.getTempsContreLaMontre() != 60){
            setFill(Color.RED);
        }
        if(temps == 10 && vuePendu.getTempsContreLaMontre() != 10){
            setFill(Color.RED);
        }
        if(temps != 60 && temps !=10 && temps > 5){
            setFill(Color.BLACK);
        }
        if(temps <= -1){
            timeline.stop();
            if(vuePendu.getFenetreActuelle().equals("jeu")){
                Platform.runLater(() -> {
                    vuePendu.partiePerdue();
                });
            } else {
                vuePendu.resetPartie();
                vuePendu.setPartieEnCours(false);
                System.out.println("Temps limite du contre la montre dépassé, l'utilisateur n'est pas sur la fenetre de jeu, partie réinitialisée");
            }
        } else if(tpsEnMinutes <= 0){
            setText(""+temps%60+"s");
        } else {
            setText(tpsEnMinutes+"min"+temps%60+"s");
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
        this.setTime(0L);
        actionTemps.reset();
    }
}
