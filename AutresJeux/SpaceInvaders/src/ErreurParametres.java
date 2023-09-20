import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.Slider;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**Pop-up erreur dans les paramètres de la partie  */
public class ErreurParametres extends Application{

        /**Créer la borderPane contenant les textes de la fenetre
         * @return la borderPane
         */
        private BorderPane borderPane(){
            BorderPane pane = new BorderPane();
            Text titre = new Text("Erreur");
            titre.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
            pane.setTop(titre);
            Text texte = new Text("\nLes paramètres que vous avez rentrés ne sont pas valides.\n\n- Les paramètres rentrés doivent être composés uniquement de chiffres, sans virgules\n- Le nombre d'aliens doit être compris entre 6 et 24\n- Le nombre de vagues doit être d'au moins 1.");
            pane.setAlignment(titre, Pos.CENTER);
            pane.setCenter(texte);
            pane.setAlignment(texte, Pos.CENTER);
            return pane;
        }
    
        /**Créer une HBox contenant des boutons
         * @return la HBox
         */
        private HBox boutons(){
            HBox pane = new HBox();
            Button ok = new Button("J'ai compris");
            pane.getChildren().addAll(ok);
            pane.setAlignment(Pos.CENTER);
            ok.setOnAction(new ControleurOk(this));
            return pane;
        }
    
        /**Créer la HBox principale
         * Contient la borderPane() et boutons()
         * @return la HBox
         */
        private HBox root(){
            HBox pane = new HBox(10);
            VBox vbox = new VBox(50);
            vbox.getChildren().addAll(borderPane(), boutons());
            vbox.setPrefWidth(400);
            HBox.setMargin(vbox, new Insets(30));
            pane.getChildren().add(vbox);
            return pane;
        }

    /**Méthode pour lancer et configurer la fenetre */
    @Override
    public void start(Stage stage){              
        Scene scene =new Scene(root());
        stage.setTitle("Menu Principal - Erreur dans les paramètres");
        stage.setScene(scene);
        stage.show();
    }
}