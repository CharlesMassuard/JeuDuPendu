/**Classe servant pour paramétrer et obtenir le nombre d'Aliens toujours en vie */
public class NbrAliensEnVie {
    private int aliens;

    /**Initialisation du nombre d'Aliens en vie
     * @param aliens le nombre d'aliens en vie
     */
    public NbrAliensEnVie(int aliens){
        this.aliens = aliens;
    }

    /**Enlever 1 au nombre d'Aliens toujours en vie
     * @return le nombre d'Aliens en vie actuellement
    */
    public int enleve(){
        this.aliens -= 1;
        return aliens;
    }

    /**Obtenir le nombre d'Aliens toujours en vie
     * @return le nombre d'Aliens en vie actuellement
     */
    public int getEnVie(){
        return this.aliens;
    }

    /**Créer l'ensemble de chaines affichant le nombre d'Aliens en vie
     * @return l'EnsembleChaines des Aliens toujours en vie
     */
    public EnsembleChaines getEnsembleChaines(){
        EnsembleChaines aliens = new EnsembleChaines();
        aliens.ajouteChaine(72, 59, "Nombre d'aliens en vie : "+this.aliens, "0xFFFFFF"); //59 car hauteur fenetre = 60
        return aliens;
    }
}
