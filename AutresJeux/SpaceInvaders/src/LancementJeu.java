import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import javax.sound.sampled.Clip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;

//Anciennement l'executable
/**Classe servant à lancer le jeu Space Invaders*/
public class LancementJeu extends Application {

    private static Pane root;
    private static Group caracteres;
    private static GestionJeu gestionnaire;
    private Executable menu;
    private static int hauteurTexte;
    private static int largeurCaractere;
    private static Clip clip;
    private static Timeline timeline;
    private static Stage stage;
    private static Integer nbrAliens;
    private static Integer nbrVagues;

    /**Initialisation du lancement du jeu
     * @param nbrAliens le nombre d'Aliens à créer
     * @param nbrVagues le nombre de Vagues à réaliser
    */
    public LancementJeu(Integer nbrAliens, Integer nbrVagues){
        this.nbrAliens = nbrAliens;
        this.nbrVagues = nbrVagues;
    }

    /**Récupérer le nombre d'Aliens à créer
     * @return le nombre d'Aliens à créer
     */
    public static int getNbrAliens(){
        return (int) nbrAliens;
    }

    /**Récupérer le nombre de vagues à réaliser
     * @return le nombre de vagues à réaliser
     */
    public static int getNbrVagues(){
        return (int) nbrVagues;
    }

    /**Permet d'afficher les caractères sur la fenetre du jeu */
    private static void afficherCaracteres(){
        caracteres.getChildren().clear();
        int hauteur = (int) root.getHeight();
        for( ChainePositionnee c : gestionnaire.getChaines().chaines)
        {
            Text t = new Text (c.x*largeurCaractere,hauteur - c.y*hauteurTexte, c.c);
            t.setFont(Font.font ("Monospaced", 10));
            t.setFill(Color.web(c.getCouleur()));
            caracteres.getChildren().add(t);
        }
    }

    /**Lancer l'animation */
    public static void lancerAnimation() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                    new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent actionEvent) {
                            gestionnaire.jouerUnTour();
                            afficherCaracteres();
                        }
                    }),
                new KeyFrame(Duration.seconds(0.025))
                );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**Stoper l'animation */
    public static void stopAnimation(){
        timeline.stop();
    }

    /**Fermer la fenetre du jeu */
    public static void fermerFenetre(){
        stage.close();
    }

    /**Lancer la musique du jeu */
    public static void lancerMusique(){
        try{
            File musique = new File("./fichiers_menus/musique_volumebas.wav");
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musique);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.loop(999);
            clip.start();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    /**Stoper la musique du jeu */
    public static void stopMusique(){
        clip.stop();
    }

    /**Méthode pour lancer le jeu */
    @Override
        public void start(Stage primaryStage){
            stage = primaryStage;
            primaryStage.setTitle("IUTO Space Invader");
            caracteres = new Group();
            root= new AnchorPane(caracteres);
            gestionnaire = new GestionJeu();
            menu = new Executable();
            Text t=new Text("█");
            t.setFont(Font.font("Monospaced",10));
            hauteurTexte =(int) t.getLayoutBounds().getHeight();
            largeurCaractere = (int) t.getLayoutBounds().getWidth();
            //Actions quand touches pressées
            Scene scene = new Scene(root,gestionnaire.getLargeur()*largeurCaractere,gestionnaire.getHauteur()*hauteurTexte);
            scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
                if(key.getCode()==KeyCode.LEFT)
                    gestionnaire.toucheGauche();
                if(key.getCode()==KeyCode.RIGHT)
                    gestionnaire.toucheDroite();
                if(key.getCode()==KeyCode.SPACE)
                    gestionnaire.toucheEspace();
                if(key.getCode()==KeyCode.ESCAPE)
                    gestionnaire.pauseMenu();
            });
            //mise en place de l'image d'arrière plan
            Image image = new Image("file:./fichiers_menus/espace.jpg");
            BackgroundImage backImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true)
            );
            Background background = new Background(backImage);
            root.setBackground(background);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            lancerMusique(); //lancer la musique
            lancerAnimation(); //lancer l'animation

        }
}
