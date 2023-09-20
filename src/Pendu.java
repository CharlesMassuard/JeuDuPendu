import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData ;
import javafx.scene.control.ButtonType ;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.image.ImageView;
import java.util.List;
import java.util.Arrays;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.FlowPane;
import java.util.Arrays;
import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import javafx.scene.text.FontWeight;
import javafx.scene.control.ToggleGroup;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import javafx.application.Platform;
import javafx.scene.control.ToggleButton;
import java.io.IOException;
import javafx.stage.FileChooser;
import javafx.scene.Cursor;

/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {
    /**
     * modèle du jeu
     **/
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les noms des niveaux
     */    
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Text motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private Text leNiveau;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    private ChronoContreLaMontre chronoMontre;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private BorderPane panelCentral;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button boutonParametres;
    /**
     * le bouton Accueil / Maison
     */    
    private Button boutonMaison;
    /**
     * le bouton qui permet de (lancer ou relancer une partie
     */ 
    private Button bJouer;
    private Button boutonInfos;
    private String fenetreActuelle;
    private boolean partieEnCours;
    private ButtonType recommencer;
    private ControleurLancerPartie controleurLancer;
    private int mauvaisesLettres;
    private boolean darkMode;
    private Text bestTemps;
    private Long bestTempsChiffres;
    private boolean contreLaMontre;
    private int tempsContreMontre;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        Image dessinInital = new Image("pendu0.png");
        this.dessin = new ImageView(dessinInital);
        this.motCrypte = new Text(modelePendu.getMotCrypte());
        this.pg = new ProgressBar(0);
        this.clavier = new Clavier("ABCDEFGHIJKLMNOPQRSTUVWXYZ-", new ControleurLettres(modelePendu, this));
        this.leNiveau = new Text("facile");
        this.chrono = new Chronometre();
        this.chronoMontre = new ChronoContreLaMontre(this);
        this.panelCentral = new BorderPane();
        this.boutonParametres = new Button();
        boutonParametres.setOnAction(new ControleurParametres(this));
        this.boutonMaison = new Button();
        this.boutonInfos = new Button();
        boutonMaison.setOnAction(new RetourAccueil(modelePendu, this));
        this.bJouer = new Button("Lancer une partie");
        this.controleurLancer = new ControleurLancerPartie(modelePendu, this);
        this.bJouer.setOnAction(controleurLancer);
        this.boutonInfos.setOnAction(new ControleurInfos(this));
        this.partieEnCours = false;
        this.recommencer = new ButtonType("Recommencer");
        this.mauvaisesLettres = 0;
        this.darkMode = false;
        this.bestTemps = new Text("Aucun temps enregistré");
        this.bestTempsChiffres = null;
        this.contreLaMontre = false;
        this.tempsContreMontre = 60;
        this.fenetreActuelle = "accueil";
    }

    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene(){
        BorderPane fenetre = new BorderPane();
        fenetre.setTop(this.titre());
        fenetre.setCenter(this.panelCentral);
        return new Scene(fenetre, 800, 1000);
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private BorderPane titre(){       
        BorderPane banniere = new BorderPane();
        Text titre = new Text("Le jeu du pendu");
        titre.setFont(Font.font("Arial",FontWeight.BOLD,32));
        banniere.setBackground((new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(0), Insets.EMPTY))));
        banniere.setLeft(titre);
        banniere.setRight(boutonsBanniere());
        return banniere;
    }

    /**
     * @return le panel contenant les boutons de la bannière
     */
    private HBox boutonsBanniere(){
        HBox pane = new HBox();

        Image home = new Image("home.png", 50, 40, true, false);
        ImageView imgViewHome = new ImageView(home);
        boutonMaison.setPadding(new Insets(5, 5, 10, 5));
        boutonMaison.setGraphic(imgViewHome); //ajoute l'image au bouton
        boutonMaison.setTooltip(new Tooltip("Revenir à l'accueil"));

        Image param = new Image("parametres.png", 50, 40, true, false);
        ImageView imgViewParam = new ImageView(param);
        boutonParametres.setPadding(new Insets(5, 5, 10, 5));
        boutonParametres.setGraphic(imgViewParam);
        boutonParametres.setTooltip(new Tooltip("Accéder aux paramètres"));

        Image info = new Image("info.png", 50, 40, true, false);
        ImageView imgViewInfo = new ImageView(info);
        boutonInfos.setPadding(new Insets(5, 5, 10, 5));
        boutonInfos.setGraphic(imgViewInfo);
        pane.getChildren().addAll(boutonMaison, boutonParametres, boutonInfos);
        boutonInfos.setTooltip(new Tooltip("Voir les règles du jeu"));
        return pane;
    }

    /**
     * @return le panel du chronomètre
     */
    private TitledPane leChrono(){
        if(contreLaMontre){
            TitledPane chronometre = new TitledPane("Temps restant", chronoMontre);
            chronometre.setCollapsible(false);
            return chronometre;
        } else {
            TitledPane chronometre = new TitledPane("Temps écoulé", chrono);
            chronometre.setCollapsible(false);
            return chronometre;
        }
    }

    /**
     * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     *         de progression et le clavier
     */
    private BorderPane fenetreJeu(){
        BorderPane res = new BorderPane();
        res.setPadding(new Insets(30, 30, 30, 30));
        
        VBox jeu = new VBox();
        jeu.setSpacing(20);
        jeu.setAlignment(Pos.CENTER);
        motCrypte.setFont(Font.font("Dyuthi", FontWeight.BOLD, 40));
        if(darkMode){
            motCrypte.setFill(Color.WHITE);
        } else {
            motCrypte.setFill(Color.BLACK); //couleur texte
        }
        jeu.getChildren().addAll(motCrypte, dessin, pg, clavier);

        VBox infos = new VBox();
        infos.setSpacing(30);
        Text niveau;
        if(contreLaMontre){
            niveau = new Text(leNiveau.getText());
        } else {
            niveau = new Text("Niveau "+leNiveau.getText());
        }
        niveau.setFont(Font.font("Dyuthi", FontWeight.NORMAL, 30));
        if(darkMode){
            niveau.setFill(Color.WHITE); //couleur texte
        } else {
            niveau.setFill(Color.BLACK);
        }

        Button newMot = new Button("Nouveau mot");
        newMot.setOnAction(controleurLancer);

        infos.getChildren().addAll(niveau, leChrono(), newMot);


        res.setLeft(jeu);
        res.setRight(infos);
        return res;
    }

    /**
     * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
     */
    private VBox fenetreAccueil(){
        VBox res = new VBox();
        res.setPadding(new Insets(10, 10, 10, 10));
        res.setSpacing(15);

        HBox temps = new HBox();
        temps.setPadding(new Insets(10, 10, 10, 10));
        temps.setSpacing(15);
        Text text = new Text("Meilleur temps : ");
        text.setFont(Font.font("Dyuthi", FontWeight.BOLD, 25));
        bestTemps.setFont(Font.font("Dyuthi", FontWeight.BOLD, 25));
        if(darkMode){
            bestTemps.setFill(Color.WHITE);
            text.setFill(Color.WHITE);
        } else {
            bestTemps.setFill(Color.BLACK);
        }
        temps.getChildren().addAll(text, bestTemps);


        VBox choixNiveau = new VBox();
        choixNiveau.setPadding(new Insets(10, 10, 10, 10));
        choixNiveau.setSpacing(10);
        RadioButton niv1 = new RadioButton("Facile");
        RadioButton niv2 = new RadioButton("Moyen");
        RadioButton niv3 = new RadioButton("Difficile");
        RadioButton niv4 = new RadioButton("Expert");
        RadioButton contreMontre = new RadioButton("Contre la montre");

        //Séléction du bouton
        if(leNiveau.getText().equals("facile")){
            niv1.setSelected(true);
        } else if (leNiveau.getText().equals("moyen")){
            niv2.setSelected(true);
        } else if (leNiveau.getText().equals("difficile")){
            niv3.setSelected(true);
        } else if (leNiveau.getText().equals("expert")){
            niv4.setSelected(true);
        } else {
            contreMontre.setSelected(true);
        }

        niv1.setSelected(true);
        ControleurNiveau controlNiveau = new ControleurNiveau(modelePendu, this);
        niv1.setOnAction(controlNiveau);
        niv2.setOnAction(controlNiveau);
        niv3.setOnAction(controlNiveau);
        niv4.setOnAction(controlNiveau);
        contreMontre.setOnAction(controlNiveau);
        ToggleGroup groupeNiveaux = new ToggleGroup();
        niv1.setToggleGroup(groupeNiveaux);
        niv2.setToggleGroup(groupeNiveaux);
        niv3.setToggleGroup(groupeNiveaux);
        niv4.setToggleGroup(groupeNiveaux);
        contreMontre.setToggleGroup(groupeNiveaux);
        choixNiveau.getChildren().addAll(niv1, niv2, niv3, niv4, contreMontre);


        HBox boutons = new HBox();
        boutons.setPadding(new Insets(10, 10, 10, 10));
        boutons.setSpacing(15);
        Button quitterJeu = new Button("Quitter le jeu");
        quitterJeu.setOnAction(new ControleurQuitter(this));
        boutons.getChildren().addAll(bJouer, quitterJeu);

        Text espace = new Text("");
        Text autresJeux = new Text("========== DÉCOUVRE D'AUTRES JEUX ==========");
        autresJeux.setFont(Font.font("Dyuthi", FontWeight.BOLD, 33));
        if(darkMode){
            autresJeux.setFill(Color.WHITE);
        }
        autresJeux.setTextAlignment(TextAlignment.CENTER);

        GridPane jeux = new GridPane();
        jeux.setAlignment(Pos.CENTER);

        Image spaceI = new Image("SpaceILogo.png", 100, 100, true, false);
        ImageView spaceIView = new ImageView(spaceI);
        jeux.add(spaceIView, 0, 0);
        Image demin = new Image("demineur.png", 100, 100, true, false);
        ImageView deminView = new ImageView(demin);
        jeux.add(deminView, 2, 0);

        Button spaceInvaders = new Button("Space Invaders");
        spaceInvaders.setOnAction(new ControleurAutresJeux(this));
        jeux.add(spaceInvaders, 0, 1);
        Button demineur = new Button("Démineur");
        demineur.setOnAction(new ControleurAutresJeux(this));
        jeux.add(demineur, 2, 1);

        TitledPane selectionNiveau = new TitledPane("Niveau de difficulté", choixNiveau);
        selectionNiveau.setCollapsible(false);

        res.getChildren().addAll(temps, selectionNiveau, boutons, espace, autresJeux, jeux);
        return res;
    }

    /**
     * @return la fenêtre des paramètres de jeu
     */
    private VBox fenetreParametres(){
        VBox res = new VBox();
        res.setPadding(new Insets(10, 10, 10, 10));
        res.setSpacing(15);

        HBox dark = new HBox();
        dark.setPadding(new Insets(10, 10, 10, 10));
        dark.setSpacing(15);
        Text configDarkText = new Text("Mode sombre : ");
        configDarkText.setFont(Font.font("Dyuthi", FontWeight.BOLD, 25));
        if(darkMode){
            configDarkText.setFill(Color.WHITE);
        }
        ToggleButton darkOn = new ToggleButton("Activé");
        ToggleButton darkOff = new ToggleButton("Désactivé");
        ControleurDarkMode controleurDarkMode = new ControleurDarkMode(this);
        darkOn.setOnAction(controleurDarkMode);
        darkOff.setOnAction(controleurDarkMode);
        if(!darkMode){
            darkOff.setSelected(true);
        } else {
            darkOn.setSelected(true);
        }
        ToggleGroup group = new ToggleGroup();
        darkOn.setToggleGroup(group);
        darkOff.setToggleGroup(group);
        dark.getChildren().addAll(configDarkText, darkOn, darkOff);

        HBox sltTemps = new HBox();
        sltTemps.setPadding(new Insets(10, 10, 10, 10));
        sltTemps.setSpacing(15);
        Text configTemps = new Text("Temps Contre la Montre : ");
        configTemps.setFont(Font.font("Dyuthi", FontWeight.BOLD, 25));
        if(darkMode){
            configTemps.setFill(Color.WHITE);
        }
        ToggleButton dixSecondes = new ToggleButton("10 secondes");
        ToggleButton trentesSecondes = new ToggleButton("30 secondes");
        ToggleButton soixanteSecondes = new ToggleButton("1 minute");
        ToggleButton minuteEtDemie = new ToggleButton("1 minute et demie");
        ControleurTempsContreLaMontre controleurTemps = new ControleurTempsContreLaMontre(this);
        dixSecondes.setOnAction(controleurTemps);
        trentesSecondes.setOnAction(controleurTemps);
        soixanteSecondes.setOnAction(controleurTemps);
        minuteEtDemie.setOnAction(controleurTemps);
        ToggleGroup tempsGroupe = new ToggleGroup();
        dixSecondes.setToggleGroup(tempsGroupe);
        trentesSecondes.setToggleGroup(tempsGroupe);
        soixanteSecondes.setToggleGroup(tempsGroupe);
        minuteEtDemie.setToggleGroup(tempsGroupe);

        //Séléction boutons
        if(tempsContreMontre == 10){
            dixSecondes.setSelected(true);
        } else if(tempsContreMontre == 30){
            trentesSecondes.setSelected(true);
        } else if(tempsContreMontre == 60){
            soixanteSecondes.setSelected(true);
        } else {
            minuteEtDemie.setSelected(true);
        }

        sltTemps.getChildren().addAll(configTemps, dixSecondes, trentesSecondes, soixanteSecondes, minuteEtDemie);

        res.getChildren().add(dark);
        res.getChildren().add(sltTemps);

        return res;
    }

    /**
     * charge les images à afficher en fonction des erreurs
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire){
        for (int i=0; i<this.modelePendu.getNbErreursMax()+1; i++){
            File file = new File(repertoire+"/pendu"+i+".png");
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    /**
     * Défini le mode Accueil
     */
    public void modeAccueil(){
        this.fenetreActuelle = "accueil";
        boutonParametres.setDisable(false);
        boutonMaison.setDisable(true);
        this.panelCentral.setCenter(fenetreAccueil());
    }
    
    /**
     * Défini le mode Jeu
     */
    public void modeJeu(){
        this.fenetreActuelle = "jeu";
        boutonParametres.setDisable(true);
        boutonMaison.setDisable(false);
        this.panelCentral.setCenter(fenetreJeu());
    }
    
    /**
     * Défini le mode Paramètres
     */
    public void modeParametres(){
        this.fenetreActuelle = "parametres";
        this.boutonMaison.setDisable(false);
        this.boutonParametres.setDisable(true);
        this.panelCentral.setCenter(fenetreParametres());
    }

    /** lance une partie */
    public void lancePartie(){
        this.modeJeu();
        this.partieEnCours = true;
        //rendre disponible les lettres déjà découvertes (première et dernière) + rendre indisponible les boutons de ces lettres
        char lettre1 = modelePendu.getMotCrypte().charAt(0);
        char lastLettre = modelePendu.getMotCrypte().charAt(modelePendu.getMotCrypte().length()-1);
        modelePendu.essaiLettre(lastLettre);
        modelePendu.essaiLettre(lettre1);
        this.motCrypte.setText(modelePendu.getMotCrypte());
        Set<String> lettresOff = new HashSet<>();
        lettresOff.add(String.valueOf(lettre1));
        lettresOff.add(String.valueOf(lastLettre));
        this.clavier.desactiveTouches(lettresOff);
        pg.setProgress(((double)(modelePendu.getMotATrouve().length()-modelePendu.getNbLettresRestantes())/modelePendu.getMotATrouve().length()));
        if(contreLaMontre){
            this.chronoMontre.start();
        } else {
            this.chrono.resetTime();
            this.chrono.start();
        }
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
        // System.out.println(modelePendu); //activer pour voir le mot à trouver
        String motAvant = this.motCrypte.getText();
        this.motCrypte.setText(modelePendu.getMotCrypte());
        pg.setProgress(((double)(modelePendu.getMotATrouve().length()-modelePendu.getNbLettresRestantes())/modelePendu.getMotATrouve().length()));
        if(this.motCrypte.getText().equals(motAvant)){
            this.mauvaisesLettres ++;
            Image img = new Image("pendu"+mauvaisesLettres+".png");
            this.dessin.setImage(img);
            if(mauvaisesLettres >= modelePendu.getNbErreursMax()){
                partiePerdue();
            }
        }
        if(this.motCrypte.getText().equals(modelePendu.getMotATrouve())){
            partieGagne();
        }
    }

    /**
     * affiche une fenêtre si une partie est en cours lorsque l'on souhaite lancer une nouvelle partie
     */
    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours !\nEtes-vous sûr de l'interrompre ?", new ButtonType("Continuer la partie en cours"), ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }
        
    /**
     * affiche une fenêtre contenant les règles du jeu
     */
    public Alert popUpReglesDuJeu(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Le but du jeu est simple : deviner toute les lettres qui doivent composer un mot.\n\nA chaque fois que le joueur devine une lettre, celle-ci est affichée. Dans le cas contraire, le dessin d'un pendu se met à apparaître…\n\nSi le pendu est complet (10 essais infructueux), la partie est perdue.");
        alert.setHeaderText("Règles du jeu");
        alert.setTitle("Informations");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        return alert;
    }
    
    /**
     * affiche une fenêtre lorsque le jeu est gagné
     */
    public Alert popUpMessageGagne(){
        Alert alert;
        if(contreLaMontre){
            alert = new Alert(Alert.AlertType.INFORMATION, "Vous avez gagné en "+chronoMontre.getTimeChiffres()+" secondes !", this.recommencer, new ButtonType("Retour à l'accueil"));
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION, "Vous avez gagné en "+chrono.getTime()+" !", this.recommencer, new ButtonType("Retour à l'accueil"));
        }
        alert.setTitle("Gagné !");
        return alert;
    }
    
    /**
     * affiche une fenêtre lorsque le jeu est perdu
     */
    public Alert popUpMessagePerdu(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vous avez perdu la partie, dommage !\nLe mot a trouver était "+modelePendu.getMotATrouve(), this.recommencer, new ButtonType("Retour à l'accueil"));
        alert.setTitle("Perdu :/");
        return alert;
    }

    /**
     * Affiche une fenêtre lorsque l'on souhaite quitter le jeu (confirmation)
     * @return la fenêtre de confirmation
     */
    public Alert popUpQuitterJeu(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Toute progression sera perdue", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Quitter le jeu");
        alert.setHeaderText("Etes-vous sûr de vouloir quitter le jeu ?");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        return alert;
    }

    /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        this.modeAccueil();
        stage.setScene(this.laScene());
        stage.setResizable(false); //on ne peut pas changer la taille de la fenetre, évite les problèmes d'adaptabilité
        stage.show();
    }

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }    

    /**fenetreActuelle
     * retourne la fenêtre actuelle
     * @return la fenêtre actuelle
     */
    public String getFenetreActuelle(){
        return this.fenetreActuelle;
    }

    /**
     * Modifier la fenêtre actuelle
     * @param fen la fenêtre actuelle
     */
    public void setFenetreActuelle(String fen){
        this.fenetreActuelle = fen;
    }

    /**
     * Savoir si une partie est en cours60
     * @return true si une partie est en cours, false sinon
     */
    public boolean getPartieEnCours(){
        return this.partieEnCours;
    }

    /**
     * Modifier l'état du jeu (partie en cours ou non)
     * @param enCours true si une partie est en cours, false sinon
     */
    public void setPartieEnCours(boolean enCours){
        this.partieEnCours = enCours;
    }

    /**
     * Permet de réinitialiser une partie
     */
    public void resetPartie(){
        Image dessinInital = new Image("pendu0.png");
        this.mauvaisesLettres = 0;
        this.pg.setProgress(0);
        this.dessin.setImage(dessinInital);
        this.clavier = new Clavier("ABCDEFGHIJKLMNOPQRSTUVWXYZ-", new ControleurLettres(modelePendu, this));
        this.modelePendu.setMotATrouver();
        this.motCrypte = new Text(modelePendu.getMotCrypte());
        this.chronoMontre.resetTime();
        this.chrono.resetTime();
    }

    /**
     * Appelée lorsque la partie est perdue
     */
    public void partiePerdue(){
        chrono.stop();
        chronoMontre.stop();
        Optional<ButtonType> reponse = popUpMessagePerdu().showAndWait();
        if (reponse.isPresent() && reponse.get().equals(this.recommencer)){
            this.resetPartie();
            lancePartie();
        } else {
            this.resetPartie();
            this.partieEnCours = false;
            this.modeAccueil();
        }
    }

    /**
     * Appelée lorsque la partie est gagnée
     */
    public void partieGagne(){
        chrono.stop();
        chronoMontre.stop();
        if(chrono.getTime().equals("0s")){
            if(bestTempsChiffres == null || bestTempsChiffres > chronoMontre.getTimeChiffres()){
                bestTemps.setText(chronoMontre.getTime()+"s");
                bestTempsChiffres = chronoMontre.getTimeChiffres();
            }
        } else if(bestTempsChiffres == null || bestTempsChiffres > chrono.getTimeChiffres()){
            bestTemps.setText(chrono.getTime());
            bestTempsChiffres = chrono.getTimeChiffres();
        }
        Optional<ButtonType> reponse = popUpMessageGagne().showAndWait();
        if (reponse.isPresent() && reponse.get().equals(this.recommencer)){
            this.resetPartie();
            lancePartie();
        } else {
            this.resetPartie();
            this.partieEnCours = false;
            this.modeAccueil();
        }
    }

    /**
     * Modifier le niveau séléctionné
     * @param niveau le niveau séléctionné
     */
    public void setNiveau(String niveau){
        if(niveau.equals("Contre la montre")){
            this.leNiveau.setText("Contre la montre");
            this.contreLaMontre = true;
        } else {
            this.contreLaMontre = false;
            this.leNiveau.setText(niveau);
        }
    }

    /**
     * Quitter le jeu
     */
    public void quitterJeu(){
        Optional<ButtonType> reponse = popUpQuitterJeu().showAndWait();
        if (reponse.isPresent() && reponse.get().equals(ButtonType.YES)){
            Platform.exit();
        } else {
        }
    }

    /**
     * Activer ou désactiver le mode sombre
     * @param darkMode true pour activer le mode sombre, false pour le désactiver
     */
    public void setDarkMode(boolean darkMode){
        if(darkMode){
            this.darkMode = true;
            panelCentral.setBackground(new Background(new BackgroundFill(Color.rgb(30, 30, 30), CornerRadii.EMPTY, Insets.EMPTY)));
        } else {
            this.darkMode = false;
            panelCentral.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        this.modeParametres();
    }

    /**
     * obtenir le temps choisi pour le contre la montre
     * @return le temps choisi pour le contre la montre
     */
    public int getTempsContreLaMontre(){
        return tempsContreMontre;
    }

    /**
     * Modifier le temps choisi pour le contre la montre
     * @param temps le temps choisi pour le contre la montre
     */
    public void setTempsContreLaMontre(int temps){
        this.tempsContreMontre = temps;
    }

    //AUTRES JEUX

    /**
     * Lancer le jeu SpaceInvaders
     */
    public void lancerSpaceInvaders(){
        try{
            System.out.println("Ouverture du jeu SpaceInvaders en cours...");
            Process process = Runtime.getRuntime().exec("sh spaceI.sh");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    /**
     * Lancer le jeu Démineur
     */
    public void lancerDemineur(){
        boolean lance;
        try{
            System.out.println("Ouverture du jeu Demineur en cours...");
            Process process = Runtime.getRuntime().exec("sh demineur.sh");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
