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


/**Menu affiché quand une partie est mise en pause*/
public class MenuPause extends Application{


    /**Création de la VBox principale
     * Compend tout ce qui est affiché
     * @return la VBpx
     */
    private VBox vbox(){
        Executable menuP = new Executable();
        MenuGagne menuG = new MenuGagne(0);
        VBox pane = new VBox();
        Text texte = new Text("JEU EN PAUSE");
        texte.setFont(Font.font("Dyuthi", 30));
        texte.setFill(Color.WHITE);
        texte.setStrokeWidth(0.4); //Taille bordure
        texte.setStroke(Color.BLACK); //couleur bordure
        Button reprendre = new Button("Reprendre la partie");
        Button reconfig = new Button("Retour au menu principal");
        Button quitter = new Button("Quitter le jeu");
        pane.setSpacing(30);
        pane.getChildren().addAll(texte, reprendre, reconfig, quitter);
        pane.setAlignment(Pos.CENTER);
        reprendre.setOnAction(new ControleurReprendre(this));
        reconfig.setOnAction(new ControleurRetourMenu(menuG)); //Controler du MenuGagne réutilisé
        quitter.setOnAction(new ControleurQuitter(menuP)); //Controleur du menu principal réutilisé
        return pane;
    }

    /**Création de la HBox root
     * @return la HBox root
     */
    private HBox root(){
        HBox pane = new HBox(10);
        VBox vbox = new VBox(50);
        vbox.getChildren().addAll(vbox());
        vbox.setPrefWidth(400);
        HBox.setMargin(vbox, new Insets(30));
        pane.getChildren().add(vbox);
        //Mise en place de l'arrière plan
        Image image = new Image("file:./fichiers_menus/espace.gif");
        BackgroundImage backImage = new BackgroundImage(
            image,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, true, true)
        );
        Background background = new Background(backImage);
        pane.setBackground(background);
        return pane;
    }

    /**Fonction servant à reprendre le jeu lorsque le bouton "reprendre" est appuyé
     * Appel la fonction reprendre de la classe GestionJeu
     */
    public void reprendre(){
        GestionJeu.reprendre();
    }

    /**Lancement du menu */
    @Override
    public void start(Stage stage){              
        Scene scene =new Scene(root());
        stage.setTitle("Space Invaders - Pause");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}