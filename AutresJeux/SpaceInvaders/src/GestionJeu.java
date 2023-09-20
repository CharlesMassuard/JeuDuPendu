import java.util.List;
import java.util.ArrayList;
import javafx.application.Platform;
import java.io.File;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javafx.stage.Stage;
import java.lang.Thread;

/**Classe servant à gérer le jeu */
public class GestionJeu {
    
    private EnsembleChaines chaines;
    private int positionX;
    private Vaisseau vaisseau;
    private Score score;
    private Projectile projectile;
    private List<Alien> lesAliens;
    private NbrAliensEnVie nbrAliensEnVie;
    private int nbrAliens;
    private int nbrVagues;
    private int nbrVaguesPasses;

    /**Initialisation de tous les paramètres du jeu */
    public GestionJeu(){
        this.chaines = new EnsembleChaines();
        this.positionX = 0;
        this.vaisseau = new Vaisseau(positionX);
        this.score = new Score(0);
        this.lesAliens = new ArrayList<>();
        this.projectile = null;
        this.nbrAliens = LancementJeu.getNbrAliens(); //nombre d'aliens rentré par l'utilisateur sur le menu principal (Executable)
        this.nbrVagues = LancementJeu.getNbrVagues(); //nombre de vagues rentré par l'utilisateur sur le menu principal (Executable)
        this.nbrVaguesPasses = 1;
        this.nbrAliensEnVie = new NbrAliensEnVie(nbrAliens);
        ajouterAliens(nbrAliens);
    }

    /**Cette méthode ajoute au jeu le nombre d'Aliens choisis
     * @param nbrAliens le nombre d'Aliens à créer
     */
    public void ajouterAliens(int nbrAliens){
        int posX = 2;
        int posY = getHauteur()-10;
        for(int i=0; i<nbrAliens; ++i){
            lesAliens.add(new Alien(posX, posY));
            posX+=15;
            if(lesAliens.size()%6==0){ //si plus de 6 aliens sur une ligne, passage à la ligne suivante
                posX = 2;
                posY -= 7;
            }
        }
    }

    /**Récupérer la hauteur de la fenetre du jeu
     * @return la hauteur de la fenetre du jeu
     */
    public int getHauteur(){
        return 60;
    }

    /**Récupérer la largeur de la fenetre du jeu
     * @return la largeur de la fenetre du jeu
     */
    public int getLargeur(){
        return 100;
    }

    /**Emmene le vaisseau vers la gauche quand la flèche gauche est pressée.
    */
    public void toucheGauche(){
        if(vaisseau.getPosX() > 0){ //ne va pas vers la gauche si le vaisseau touche le bord
            vaisseau.setPosX(-1);
        }
    }

    /** Emmene le vaisseau vers la droite quand la flèche droite est pressée.
    */
    public void toucheDroite(){
        if(vaisseau.getPosX() < this.getLargeur()-13){ //ne va pas vers la droite si le vaisseau touche le bord (largeur du vaisseau = 13)
            vaisseau.setPosX(1);
        }
    }

    /**Quand la touche espace est pressée, lance un projectile et lance le bruitage associé*/
    public void toucheEspace(){
        this.projectile = new Projectile(vaisseau.positionCanon(), 4);
        try{
            File musique = new File("./fichiers_menus/bruitlaser.wav");
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musique);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    /**Quand la touche echap est pressée, ouvre le menu pause*, stop l'animation, stop la musique*/
    public void pauseMenu(){
        LancementJeu.stopAnimation(); //Animation stoppée
        LancementJeu.stopMusique(); //Musique stoppée
        MenuPause pauseMenu = new MenuPause();
        Stage stage = new Stage();
        pauseMenu.start(stage); //Lancement du menu Pause
    }

    /**Reprendre le jeu depuis le menu Pause */
    public static void reprendre(){
        LancementJeu.lancerMusique(); //relance la musique
        LancementJeu.lancerAnimation(); //relance l'animation
    }

    /**Créer le texte afin d'afficher le nombre de vagues effectuées
     * @return l'ensemble des chaines affichant le nombre de vagues effectuées
     */
    public EnsembleChaines nbrVagues(){
        EnsembleChaines vagues = new EnsembleChaines();
        vagues.ajouteChaine(45, 59, "Vague "+nbrVaguesPasses+"/"+nbrVagues, "0xFFFFFF");
        return vagues;
    }

    /**Créer l'ensemble des chaines composants le jeu: le vaisseau, le score, le nombre d'aliens en vie, le projectile, les aliens
     * @return l'ensemble des chaines composants le jeu
    */
    public EnsembleChaines getChaines(){
        this.chaines.union(this.vaisseau.getEnsembleChaine());
        this.chaines.union(this.score.getEnsembleChaines());
        this.chaines.union(this.nbrAliensEnVie.getEnsembleChaines());
        this.chaines.union(nbrVagues());
        if(this.projectile != null){
            this.chaines.union(this.projectile.getEnsembleChaines());
        }
        for(Alien alien : this.lesAliens){
            if(alien.getNbrTours()%2==0){
                this.chaines.union(alien.getEnsembleChaine());
            } else {
                this.chaines.union(alien.getEnsembleChaine2());
            }
        }
        return this.chaines;
    }

    /**Méthode appelée à chaque tour de jeu
     * Cette méthode effectue plusieurs vérifications et agit en conséquence
     * Permet notamment de faire évoluer les chaines composants le jeu, de gagner ou perdre la partie
     */
    public void jouerUnTour(){
        if(nbrAliensEnVie.getEnVie() != 0){ //vérification du nombre d'Aliens toujours en vie
            ArrayList<Projectile> ballesReussies = new ArrayList<>(); //création de la liste contenant les projectiles ayant touchés un Alien
            ArrayList<Alien> aliensTouches = new ArrayList<>(); //création de la liste contenant les Aliens touchés
            if(this.projectile != null){ //vérification si un projectile existe ou non
                if(this.projectile.getPositionY() > this.getHauteur()){ //vérification si le projectile est sortie de la fenetre du jeu ou non
                    this.projectile = null; //si le projectile est sortie, on le supprime
                } else {
                    this.projectile.evolue(); //sinon, il continue d'avancer (il évolue)
                }
            }
            for(Alien alien : this.lesAliens){
                if(this.projectile != null){ //vérification si le projectile existe ou non.
                    if(alien.contient((int) this.projectile.getPositionX(), (int) this.projectile.getPositionY())){ //vérification si le projectile touche l'Alien
                        //lancement du bruitage de l'Alien qui est touché
                        try{
                            File musique = new File("./fichiers_menus/alienmeurt.wav");
                            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musique);
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioInput);
                            clip.start();
                        } catch (Exception e){
                            System.out.println(e);
                        }
                        ballesReussies.add(this.projectile); //ajouter le projectile aux projectiles ayant touchés un Alien
                        aliensTouches.add(alien); //ajouter l'Alien aux Aliens touchés
                        score.ajoute(100); //incrémentation du score de 100 afin de féliciter le joueur si il touche un Alien
                        nbrAliensEnVie.enleve(); //décrémente le nombre d'Aliens toujours en vies
                    }
                }
                if(alien.getPosY()<10 || this.score.getScore()<-2000){ //vérification si le jeu est perdu ou non, il est perdu soit si le score est en dessous de 2000, soit si les Aliens sont arrivés au niveau du vaisseau
                    MenuPerdu partieGagne = new MenuPerdu(); 
                    Stage stage = new Stage();
                    partieGagne.start(stage); //lancement du menu Perdu
                    LancementJeu.stopAnimation(); //L'animation est stoppée afin de libérer les performances et ne pas engendrer plusieurs ouvertures du menu
                    break; //fin de la boucle for
                }
                alien.evolue(); //faire évoluer l'Alien (le faire bouger)
                alien.ajouterTour();  //ajouter un tour à l'Alien
            }
            if(aliensTouches.size() != 0){ //si le nombre d'Aliens touchés n'est pas égal à 0, 
                this.lesAliens.removeAll(aliensTouches); //on supprime tous les Aliens touchés
            }
            if(ballesReussies.size() != 0){ //si le projectile a touché un Alien,
                this.projectile = null; //on supprime ce projectile
            }
            this.chaines = new EnsembleChaines(); //réinitialisation des chaines
        } else {
            if(nbrVaguesPasses >= nbrVagues){ //vérification du nombre de vagues déjà effectuées
                //lancement de la musique de victoire
                try{
                    File musique = new File("./fichiers_menus/victoire.wav");
                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(musique);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInput);
                    clip.start();
                } catch (Exception e){
                    System.out.println(e);
                }
                MenuGagne partieGagne = new MenuGagne(this.score.getScore());
                Stage stage = new Stage();
                partieGagne.start(stage); //lancement du menu Gagné
                LancementJeu.stopAnimation(); //l'animation est arreté
            } else {
                //lancement du bruitage indiquant la fin d'une vague
                try{
                    File musique = new File("./fichiers_menus/ding_fin_vagues.wav");
                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(musique);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInput);
                    clip.start();
                } catch (Exception e){
                    System.out.println(e);
                }
                score.ajoute(300); //ajout de 300 au score pour récompenser le joueur ayant terminé une vague
                nbrVaguesPasses ++;
                if(nbrAliens+3 < 24){ //ne pas rajouter d'Aliens au delà de 24 Aliens déjà présents
                    nbrAliens += 3; //ajouter 3 Aliens pour la prochaine vague
                }
                this.nbrAliensEnVie = new NbrAliensEnVie(nbrAliens); //mise à jour du nombre d'Aliens en vie
                ajouterAliens(nbrAliens); //ajouter les Aliens pour la prochaine vague
            }
        }
    }
}
