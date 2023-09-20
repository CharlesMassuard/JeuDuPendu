public class Case{
    private int bombesVoisines;
    private boolean contientUneBombe;
    private boolean estMarquee;
    private boolean estDecouverte;

    /** Constructeur de la classe Case
     *  Initialise une case sans bombe, non marquée, non découverte
     */
    public Case(){
        this.contientUneBombe = false;
        this.estMarquee = false;
        this.estDecouverte = false;
    }

    /** Réinitialise une case sans bombe, non marquée, non découverte */
    public void reset(){
        this.estMarquee = false;
        this.estDecouverte = false;
        this.contientUneBombe=false;
    }


    /** Pose une bombe sur la case */
    public void poseBombe(){
        this.contientUneBombe = true;
    }

    /** Renvoie si cette case comporte une bombe
     * @return true si elle contient une bombe, false sinon */
    public boolean contientUneBombe(){
        return this.contientUneBombe;
    }

    /** Renvoie si cette case est marquée
     * @return true si elle est marquée, false sinon */
    public boolean estMarquee(){
        return this.estMarquee;
    }

    /** Renvoie si cette case est découverte
     * @return true si elle est découverte, false sinon */
    public boolean estDecouverte(){
        return this.estDecouverte;
    }

    /** Marque la case si elle ne l'est pas et la démarque si elle l'est*/
    public void marquer(){
        if(this.estMarquee()){
            this.estMarquee = false;
        } else {
            this.estMarquee = true;
        }
    }


    /** Révèle la case */
    public void reveler(){
        this.estDecouverte = true ;
    }

}