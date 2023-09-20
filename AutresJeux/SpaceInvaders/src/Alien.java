/**Classe représentant un Alien
 * Un Alien est défini par sa position en X ainsi que par sa position en Y
 */
public class Alien{

    private double posX;
    private double posY;
    private int nbrTours = 0;
    private String direction = "E";

    /**Création de l'Alien
     * @param posX la position en X de l'Alien
     * @param posY la position en Y de l'Alien
     */
    public Alien(double posX, double posY){
        this.posX = posX;
        this.posY = posY;
    }

    /**Cette méthode permet de récupérer les chaines qui dessinent l'Alien avec les antennes vers l'extérieur
     * Les chaines sont ajoutés via la fonction *ajouteChaine* de la Classe EnsembleChaines
     * @return un Ensemble de Chaines
     */
    public EnsembleChaines getEnsembleChaine(){
        EnsembleChaines alien = new EnsembleChaines();
        alien.ajouteChaine(posX, posY,   " ▀▄     ▄▀ ", "0x008000");
        alien.ajouteChaine(posX, posY-1, " ▄█▀███▀█▄ ", "0x008000");
        alien.ajouteChaine(posX, posY-2, "█▀███████▀█", "0x008000");
        alien.ajouteChaine(posX, posY-3, "█ █▀▀▀▀▀█ █", "0x008000");
        alien.ajouteChaine(posX, posY-4, "   ▀▀ ▀▀   ", "0x008000");
        return alien;
    }

    /**Cette méthode permet de récupérer les chaines qui dessinent l'Alien avec les antennes vers l'intérieur
     * Les chaines sont ajoutés via la fonction *ajouteChaine* de la Classe EnsembleChaines
     * @return un Ensemble de Chaines
     */
    public EnsembleChaines getEnsembleChaine2(){
        EnsembleChaines alien = new EnsembleChaines();
        alien.ajouteChaine( posX, posY,   "  ▄▀    ▀▄ ", "0x008000");
        alien.ajouteChaine(posX, posY-1, " ▄█▀███▀█▄ ", "0x008000");
        alien.ajouteChaine(posX, posY-2, "█▀███████▀█", "0x008000");
        alien.ajouteChaine(posX, posY-3, "█ █▀▀▀▀▀█ █", "0x008000");
        alien.ajouteChaine(posX, posY-4, "   ▀▀ ▀▀   ", "0x008000");
        return alien;
    }

    /**Permet de récupérer la position en X de l'Alien
     * @return la position en X de l'Alien
     */
    public double getPosX(){
        return this.posX;
    }

    /**Permet de récupérer la position en Y de l'Alien
     * @return la position en Y de l'Alien
     */
    public double getPosY(){
        return this.posY;
    }

    /**Permet d'ajouter un tour au compteur de tours effectués par l'Alien */
    public void ajouterTour(){
        this.nbrTours += 1;
    }

    /**Permet de récupérer le nombre de tours effectués par l'Alien depuis la dernière réinitilisation
     * @return le nombre de tours effectués par l'Alien
     */
    public int getNbrTours(){
        return this.nbrTours;
    }

    /**Permet de changer la direction de l'Alien
     * Utilisé dans la méthode evolue()
     */
    public void changerDirection(){
        if(this.direction.equals("E")){
            this.direction = "O";
        } else {
            this.direction = "E";
        }
    }

    /**Permet de savoir si l'Alien est aux coordonnées données
     * @param x la position en X pour laquelle on vérifie si l'Alien y est
     * @param y la position en Y pour laquelle on vérifie si l'Alien y est
     * @return true si l'Alien est aux coordonnées (x,y), false sinon
     */
    public boolean contient(int x, int y){
        return getEnsembleChaine().contient(x, y);
    }

    /**Permet de faire évoluer l'Alien
     * Si le nombre de tours est égal à 100, on décrémente la position Y de l'Alien de 1, on remet le nombre de tour à zéro, on enlève 5 au score et on change la direction
     * Si la direction est "E", on ajoute 0.1 à la position en X de l'Alien
     * Sinon, on décrémente la position Y de 0.1
     */
    public void evolue(){
        if(getNbrTours() == 100){
            this.posY -= 1;
            this.nbrTours = 0;
            Score.enleve(5);
            changerDirection();
        } else if(this.direction.equals("E")){
            this.posX += 0.1;
        } else {
            this.posX -= 0.1;
        }
    }
}