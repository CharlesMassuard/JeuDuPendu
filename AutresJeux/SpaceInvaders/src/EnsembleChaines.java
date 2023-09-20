import java.util.ArrayList;

/**L'ensemble des ChainePositionne présentes dans le jeu */
public class EnsembleChaines {
    ArrayList<ChainePositionnee> chaines;

    /**Créer l'ensemble de ChainesPositionnees
     * Instancie une ArrayList<ChainePositionnee>
     */
    public EnsembleChaines(){chaines= new ArrayList<ChainePositionnee>(); }

    /**Permet d'ajouter une ChainePositionnee à l'ensemble de chaines
     * @param x la position en X de la ChainePositionnee
     * @param y la position en Y de la ChainePositionnee
     * @param c le contenu de la ChainePositionnee
     * @param couleur la couleur de la ChainePositionnee
     */
    public void ajouteChaine(double x, double y, String c, String couleur){
        chaines.add(new ChainePositionnee(x,y,c,couleur));}

    /**Permet d'ajouter les ChainesPositionnes d'un ensemble de chaines à l'ensemble de chaines actuel
     * @param e un ensemble de chaines
     */
    public void union(EnsembleChaines e){
        for(ChainePositionnee c : e.chaines)
            chaines.add(c);
    }

    /**Permet de savoir si la ChainePositionnee est aux posiitons X et Y données
     * @param x la position en X où l'on souhaite vérifier si la ChainePositionnee y est ou non
     * @param y la position en Y où l'on souhaite vérifier si la ChainePositionnee y est ou non
     * @return true si la ChainePositionne est aux coordonnées (x,y), false sinon
     */
    public boolean contient(int x, int y ){
        for(ChainePositionnee c : chaines)
            if(c.x <= x && c.x < x + c.c.length() && x<c.x+c.c.length() && c.y == y){
                return true;
            }
        return false;
    }

}
