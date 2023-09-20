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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import java.io.File;
import javax.sound.sampled.Clip;

//MENU PRINCIPAL RECREE, MAINTENANT L'EXECUTABLE
//**Menu Princial du jeu*/
public class Executable extends Application{

        private static Clip clip;
        private static TextField nbrAliens;
        private static TextField nbrVagues;

        /**Permet de renvoyer la musique utilisée
         * @return la musique utilisée
         */
        public static Clip getClip(){
            return clip;
        } 

        /**Initialisation de la classe
         * Initialise les 2 TextFields présents sur la fenetre
         * Le TextField 'nbrAliens' à comme valeur par défaut '12'
         * Le TextFiel 'nbrVagues' à comme valeur par défaut '5'
        */
        @Override
        public void init(){
        // cette méthode est utilisée pour initialiser les éléments 
        // non graphiques et événetuellement graphiques autres que la Scène et le Stage
            this.nbrAliens = new TextField("12");
            this.nbrVagues = new TextField("5");
        }

        /**Création du module BorderPane
         * Contient le titre "SPACE INVADER"
         * @return la BorderPane
         */
        private BorderPane borderPane(){
            BorderPane pane = new BorderPane();
            Text titre = new Text("SPACE INVADERS");
            titre.setFont(Font.font("Dyuthi", FontWeight.BOLD, 80));
            titre.setFill(Color.WHITE); //couleur texte
            titre.setStrokeWidth(3); //Taille bordure
            titre.setStroke(Color.BLACK); //couleur bordure
            pane.setTop(titre);
            pane.setAlignment(titre, Pos.CENTER);
            return pane;
        }
    
    
        /**Création de la HBox contenant les boutons "commencer" et "quitter"
         * @return la HBox
        */
        private HBox boutons(){
            HBox pane = new HBox();
            Button start = new Button("Commencer une partie");
            Button quitter = new Button("Quitter le jeu");
            pane.setSpacing(200);
            pane.getChildren().addAll(start, quitter);
            pane.setAlignment(Pos.CENTER);
            quitter.setOnAction(new ControleurQuitter(this));
            start.setOnAction(new ControleurCommencerPrincipal(this));
            return pane;
        }

        /**Création de la GridPane contenant les paramètres
         * Conteint les textes indicateurs ainsi que les TextFiels
         * @return la GridPane
         */
        private GridPane parametres(){
            GridPane pane = new GridPane();
            Text texte = new Text("Choisissez entre 6 à 24 aliens à combatttre : ");
            texte.setFont(Font.font("Dyuthi", FontWeight.NORMAL, 20));
            texte.setFill(Color.WHITE); //couleur texte
            texte.setStrokeWidth(0.4); //Taille bordure
            texte.setStroke(Color.BLACK); //couleur bordure
            Text texteVagues = new Text("Choisissez le nombre de vagues d'aliens à affronter : ");
            texteVagues.setFont(Font.font("Dyuthi", FontWeight.NORMAL, 20));
            texteVagues.setFill(Color.WHITE); //couleur texte
            texteVagues.setStrokeWidth(0.4); //Taille bordure
            texteVagues.setStroke(Color.BLACK); //couleur bordure
            nbrAliens.setOnKeyReleased(new ControleurDebutEntree(this));
            nbrVagues.setOnKeyReleased(new ControleurDebutEntree(this));
            pane.add(texte, 0, 0, 100, 1);
            pane.add(nbrAliens, 50, 0, 1, 10);
            pane.add(texteVagues, 0, 10, 5, 1);
            pane.add(nbrVagues, 50, 10, 2, 1);
            return pane;
        }
    
        /**Création de la HBox root, mise en forme
         * @return la HBox root
         */
        private HBox root(){
            HBox pane = new HBox(10);
            VBox vbox = new VBox(50);
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
            //Vue
            vbox.setStyle("-fx-background-color:transparent");
            vbox.getChildren().addAll(borderPane(), parametres(), boutons());
            vbox.setPrefWidth(400);
            HBox.setMargin(vbox, new Insets(30));
            pane.getChildren().add(vbox);
            return pane;
        }

    /**Quitter l'application, appelée quand le bouton "Quitter" est appuyé
     * Cette méthode est appelé lorsque le bouton "Quitter" est quitté, peut importe la fenetre sur laquelle il se trouve
    */
    public void quitter(){
        Platform.exit();
    }

    /**Lancer le jeu, appelée lorsque le bouton "Commencer" est appuyé
     * Le jeu est lancé uniquement si les conditions de lancement sont respectées
     * @boolean true si les conditions de lancement sont respectés, false sinon
    */
    public boolean commencer(){
        try{
        String nbrAliensRentre = nbrAliens.getText(); //récupère la valeur du TextField "nbrAliens"
        String nbrVaguesRentre = nbrVagues.getText(); //récupère la valeur du TextField 'nbrVagues'
        Integer nbrAliensRentreInteger = Integer.parseInt(nbrAliensRentre); //transformation du texte rentré en Integer
        Integer nbrVaguesInteger = Integer.parseInt(nbrVaguesRentre); //transformation du texte rentré en Integer
            if(nbrAliensRentreInteger>=6 && nbrAliensRentreInteger<=24 && nbrVaguesInteger>=1){
                clip.stop(); //éteindre la musoqie
                LancementJeu menu = new LancementJeu(nbrAliensRentreInteger, nbrVaguesInteger);
                Stage stage = new Stage();
                menu.start(stage); //lancer le jeu
                return true;
            } else {
                ErreurParametres menu = new ErreurParametres(); 
                Stage stage = new Stage();
                menu.start(stage); //lancer le pop-up d'erreur (ErreurParametres)
            }
        } catch (Exception e){
            ErreurParametres menu = new ErreurParametres();
            Stage stage = new Stage();
            menu.start(stage); //lancer le pop-up d'erreur (ErreurParametres)
        }
        return false;
    }

    /**Méthode servant à lancer l'application */
    @Override
    public void start(Stage stage){              
        Scene scene =new Scene(root());
        stage.setTitle("Space Invaders - Menu Principal");
        stage.setScene(scene);
        stage.setResizable(false);
        //Mise en place de la musique
        try{
            File musique = new File("./fichiers_menus/musique_volumebas.wav");
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musique);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.loop(200);
            clip.start();
        } catch (Exception e){
            System.out.println(e);
        }
        stage.show(); //apparition de la fenetre
    }
}