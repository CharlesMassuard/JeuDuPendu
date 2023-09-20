import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import java.io.File;
import javax.sound.sampled.Clip;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;

/**Menu affiché quand une partie est gagnée*/
public class MenuGagne extends Application{

    private int score;

    /**Initialisation
     * @param score le score atteint par le joueur
     */
    public MenuGagne(int score){
        this.score = score;
    }

    /**La BorderPane regroupant tous les textes affichés sur le menu
     * @return la BorderPane
    */
    private BorderPane borderPane(){
        BorderPane pane = new BorderPane();
        Text titre = new Text("VOUS AVEZ GAGNÉ !");
        titre.setFont(Font.font("Dyuthi", FontWeight.BOLD, 80));
        titre.setFill(Color.WHITE); //couleur texte
        pane.setTop(titre);
        titre.setStrokeWidth(5); //Taille bordure
        titre.setStroke(Color.BLACK); //couleur bordure
        pane.setAlignment(titre, Pos.CENTER);
        Text scoreAffiche;
        if(score == 1 || score == -1){
            scoreAffiche = new Text("Vous avez obtenu un score de "+this.score+" point !");
        } else {
            scoreAffiche = new Text("Vous avez obtenu un score de "+this.score+" points !");
        }
        scoreAffiche.setFont(Font.font("Dyuthi", FontWeight.NORMAL, 30));
        scoreAffiche.setFill(Color.WHITE); //couleur texte
        scoreAffiche.setStrokeWidth(1); //Taille bordure
        scoreAffiche.setStroke(Color.BLACK); //couleur bordure
        pane.setCenter(scoreAffiche);
        return pane;
    }

    /**HBox regroupant les boutons affichés sur le menu
     * @return la HBox
     */
    private HBox boutons(){
        Executable menuP = new Executable();
        HBox pane = new HBox();
        Button start = new Button("Relancer une partie");
        Button reconfig = new Button("Retour au menu principal");
        Button quitter = new Button("Quitter le jeu");
        pane.setSpacing(100);
        pane.getChildren().addAll(start, reconfig, quitter);
        pane.setAlignment(Pos.CENTER);
        start.setOnAction(new ControleurRelancer(this));
        reconfig.setOnAction(new ControleurRetourMenu(this));
        quitter.setOnAction(new ControleurQuitter(menuP));
        return pane;
    }

    /**Création de la HBox root
     * @return la HBox root
     */
    private HBox root(){
        HBox pane = new HBox(10);
        VBox vbox = new VBox(50);
        //Mise en place de l'arrière plan
        Image image = new Image("file:./fichiers_menus/space.gif");
        BackgroundImage backImage = new BackgroundImage(
            image,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, true, true)
        );
        Background background = new Background(backImage);
        pane.setBackground(background);
        //Vue
        vbox.setStyle("-fx-background-color:transparent");
        vbox.getChildren().addAll(borderPane(), boutons());
        vbox.setPrefWidth(400);
        HBox.setMargin(vbox, new Insets(30));
        pane.getChildren().add(vbox);
        return pane;
    }

    /**Méthode lancée quand le bouton "Recommencer" est appuyé
     * Relance la partie avec les mêmes paramètres que celle précédente
     */
    public void recommencer(){
        LancementJeu.stopMusique(); //stop la musique, évite d'avoir une musique en double
        LancementJeu jeu = new LancementJeu(LancementJeu.getNbrAliens(), LancementJeu.getNbrVagues()); //création du jeu avec les mêmes paramètres
        Stage stage = new Stage();
        LancementJeu.fermerFenetre(); //ferme la fenetre de l'ancien jeu
        jeu.start(stage); //lancement du jeu
    }

    /**Méthode servant à reconfigurer une nouvelle partie
     * Fait un retour au menu principal
     */
    public void reconfig(){
        LancementJeu.stopMusique(); //stop la musique
        Executable menu = new Executable();
        Stage stage = new Stage();
        LancementJeu.fermerFenetre(); //ferme la fenetre du jeu précédent
        menu.start(stage); //lance le menu principal
    }

    /**Lance le menu */
    @Override
    public void start(Stage stage){              
        Scene scene =new Scene(root());
        stage.setTitle("Space Invaders - Gagné");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show(); //Afiche la fenetre
    }
}