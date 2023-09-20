public class ChainePositionnee{
    double x,y;
    String c, couleur;
    /**Créer la chainePositionnee
     * @param a la position en X de la ChainePositionnee
     * @param b la position en Y de la ChainePositionnee
     * @param d le contenu de la ChainePositionnee
     * @param couleur la couleur de la ChainePositionnee
     */
    public ChainePositionnee(double a,double b, String d, String couleur){x=a; y=b; c=d; this.couleur=couleur;}

    /**Récupérer la position en X de la ChainePositionne
     * @return la position en X de la ChainePositionne
     */
    public double getX(){
        return this.x;
    }

    /**Récupérer la position en Y de la ChainePositionne
     * @return la position en Y de la ChainePositionne
     */
    public double getY(){
        return this.y;
    }

    /**Récupérer la couleur de la ChainePositionne
     * @return la couleur en Y de la ChainePositionne
     */
    public String getCouleur(){
        return this.couleur;
    }

    /**Renvoie la ChainePositionnee sous la forme d'un String
     * positionX,positionY)
     * @return le String de la ChainePositionnee
     */
    @Override
    public String toString(){
        return "("+getX()+","+getY()+")";
    }
}
