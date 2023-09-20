/**Classe représentant un Vaisseau
 * Un Vaisseau est défini par sa position en X
 */
public class Vaisseau{

    private int posX;

    /**Création du Vaisseau
     * @param posX la position en X du Vaisseau
     */
    public Vaisseau(int posX){
        this.posX = posX;
    }

    /**Permet de déplacer le Vaisseau
     * @param vitesse la vitesse du vaisseau, la valeur que PosX gagne ou perd
     */
    public void deplace(double vitesse){
        this.posX += vitesse;
    }

    /**Cette méthode permet de récupérer les chaines qui dessinent le Vaisseau
     * Les chaines sont ajoutés via la fonction *ajouteChaine* de la Classe EnsembleChaines
     * @return un Ensemble de Chaines
     */
    public EnsembleChaines getEnsembleChaine(){
        EnsembleChaines vaisseau = new EnsembleChaines();
        vaisseau.ajouteChaine(posX, 0, "███████████████", "0x9C9C9C");
        vaisseau.ajouteChaine(posX, 1, " █████████████", "0x9C9C9C");
        vaisseau.ajouteChaine(posX, 2, " █████████████", "0x9C9C9C");
        vaisseau.ajouteChaine(posX, 3, " ▄███████████▄", "0x9C9C9C");
        vaisseau.ajouteChaine(posX, 4, "      ███     ", "0x9C9C9C");
        vaisseau.ajouteChaine(posX, 5, "       ▄      ", "0x9C9C9C");
        return vaisseau;
    }

    /**Récupérer la position du canon
     * @return la position du canon
     */
    public int positionCanon(){
        return this.posX+6; //6 car le vaisseau a une hauteur de 6
    }

    /**Obtenir la position en X du Vaisseau
     * @return la position en X du Vaisseau
     */
    public double getPosX(){
        return this.posX;
    }

    /**Définir la position en X du Vaisseau */
    public void setPosX(int vitesse){
        this.posX += vitesse;
    }
}