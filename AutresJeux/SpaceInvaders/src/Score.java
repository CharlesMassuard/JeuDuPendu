/**Classe représentant le score
 */
public class Score {
    private static int score;

    /**Initialisation du score
     * @param score le score
     */
    public Score(int score){
        this.score = score;
    }

    /**Ajouter la valeur choisie au score
     * @param valeur la valeur à ajouter au score
     * @return le score
     */
    public int ajoute(int valeur){
        score += valeur;
        return score;
    }

    /**Enlever la valeur choisie au score
     * @param valeur la valeur à enlever au score
     * @return le score
     */
    public static int enleve(int valeur){
        score -= valeur;
        return score;
    }

    /**Obtenir le score actuel
     * @return le score actuel
     */
    public int getScore(){
        return this.score;
    }

    /**Cette méthode permet de récupérer les chaines qui affiches le score
     * Les chaines sont ajoutés via la fonction *ajouteChaine* de la Classe EnsembleChaines
     * @return un Ensemble de Chaines
     */
    public EnsembleChaines getEnsembleChaines(){
        EnsembleChaines score = new EnsembleChaines();
        score.ajouteChaine(0, 59, "Score actuel : "+this.score, "0xFFFFFF"); //59 car hauteur fenetre = 60
        return score;
    }
}
