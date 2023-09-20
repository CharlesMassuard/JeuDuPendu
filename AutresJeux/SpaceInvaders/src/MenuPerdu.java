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


/**Menu affiché quand une partie est perdue*/
public class MenuPerdu extends Application{

    /**Création de la BorderPane affichant le texte
     * @return la BorderPane
     */
    private BorderPane borderPane(){
        BorderPane pane = new BorderPane();
        Text titre = new Text("VOUS AVEZ PERDU :'(");
        titre.setFont(Font.font("Dyuthi", FontWeight.BOLD, 80));
        titre.setFill(Color.WHITE); //couleur texte
        pane.setTop(titre);
        pane.setAlignment(titre, Pos.CENTER);
        return pane;
    }


    /**Création de la HBox regroupant les boutons
     * @return la HBox
     */
    private HBox boutons(){
        Executable menuP = new Executable();
        MenuGagne menuG = new MenuGagne(0);
        HBox pane = new HBox();
        Button start = new Button("Relancer une partie");
        Button reconfig = new Button("Retour au menu principal");
        Button quitter = new Button("Quitter le jeu");
        pane.setSpacing(100);
        pane.getChildren().addAll(start, reconfig, quitter);
        pane.setAlignment(Pos.CENTER);
        start.setOnAction(new ControleurRelancer(menuG));
        reconfig.setOnAction(new ControleurRetourMenu(menuG));
        quitter.setOnAction(new ControleurQuitter(menuP));
        return pane;
    }

    /**Création de la HBox root
     * @return la HBox
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

    /**Méthode servant à recommencer la partie*/
    public void recommencer(){
        LancementJeu.stopMusique();
        LancementJeu jeu = new LancementJeu(LancementJeu.getNbrAliens(), LancementJeu.getNbrVagues());
        Stage stage = new Stage();
        LancementJeu.fermerFenetre();
        jeu.start(stage);
    }

    /**Méthode servant à retourner au menu principal */
    public void reconfig(){
        LancementJeu.stopMusique();
        Executable menu = new Executable();
        Stage stage = new Stage();
        LancementJeu.fermerFenetre();
        menu.start(stage);
        menu.init(); //lancer la musique du menu principal
    }

    /**Lancement du menu */
    @Override
    public void start(Stage stage){              
        Scene scene =new Scene(root());
        stage.setTitle("Space Invaders - Perdu");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}