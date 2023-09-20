/**Classe représentant un Projectile
 * Un Projectile est défini par sa positionX et sa positionY
 */
public class Projectile {

    private double positionX, positionY;

    /**Initialisation du Projectile
     * @param positionX la position en X du projectile
     * @param positionY le position en Y du projectile
     */
    public Projectile(double positionX, double positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }

    /**Cette méthode permet de récupérer les chaines qui dessinent le projectile
     * Les chaines sont ajoutés via la fonction *ajouteChaine* de la Classe EnsembleChaines
     * @return un Ensemble de Chaines
     */
    public EnsembleChaines getEnsembleChaines(){
        EnsembleChaines projectile = new EnsembleChaines();
        projectile.ajouteChaine(positionX,positionY, "█", "0x9C9C9C");
        projectile.ajouteChaine(positionX,positionY-1, "-", "0xFF6600");
        return projectile;
    }

    /**Obtenir la position en X du projectile
     * @return la position en X du projectile
     */
    public double getPositionX(){
        return this.positionX;
    }

    /**Obtenir la position en Y du projectile
     * @return la position en Y du projectile
     */
    public double getPositionY(){
        return this.positionY;
    }

    /**Faire évoluer le projectile, le fait monter de 0.5 */
    public void evolue(){
        this.positionY += 0.5;
    }
    
}
