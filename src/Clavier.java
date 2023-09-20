import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle ;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javafx.geometry.Insets;

/**
 * Génère la vue d'un clavier et associe le contrôleur aux touches
 * le choix ici est d'un faire un héritié d'un TilePane
 */
public class Clavier extends TilePane{
    /**
     * il est conseillé de stocker les touches dans un ArrayList
     */
    private List<Button> clavier;

    /**
     * constructeur du clavier
     * @param touches une chaine de caractères qui contient les lettres à mettre sur les touches
     * @param actionTouches le contrôleur des touches
     * @param tailleLigne nombre de touches par ligne
     */
    public Clavier(String touches, EventHandler<ActionEvent> actionTouches) {
        this.clavier = new ArrayList<>();
        for(int i=0; i<touches.length(); ++i){
            Button bouton = new Button(touches.substring(i, i+1));
            bouton.setOnAction(actionTouches);
            bouton.setShape(new Circle(1.5));
            bouton.setStyle("-fx-background-insets: 0 0 -1 0, 0, 1, 2;");
            this.getChildren().add(bouton);
            clavier.add(bouton);
        }
    }

    /**
     * retourne la liste des touches
     * @return la liste des touches
     */
    public List<Button> getBoutons(){
        return clavier;
    }

    /**
     * désactive les touches passées en paramètre
     * @param touchesDesactives l'ensemble des touches à désactiver
     */
    public void desactiveTouches(Set<String> touchesDesactives){
        for(Button bouton : clavier){
            String lettre = bouton.getText();
            if(touchesDesactives.contains(lettre)){
                bouton.setDisable(true);
            }
        }
    }
}
