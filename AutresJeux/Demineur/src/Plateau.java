import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Plateau{
    private int nbLignes;
    private int nbColonnes;
    private int nbBombes;
    private int pourcentageDeBombes;
    private List<CaseIntelligente> lePlateau;

    /**
     * Constructeur de la classe Plateau
     * @param nbLignes le nombre de lignes du plateau
     * @param nbColonnes le nombre de colonnes du plateau
     * @param pourcentageDeBombes le pourcentage de bombes sur le plateau
     */
    public Plateau(int nbLignes, int nbColonnes, int pourcentageDeBombes) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.pourcentageDeBombes = pourcentageDeBombes;
        this.nbBombes = 0;
        this.lePlateau = new ArrayList<>();
        creerLesCasesVides();
        rendLesCasesIntelligentes();
        poseDesBombesAleatoirement();
    }

    /**
     * Remplit le plateau de cases vides
     */
    private void creerLesCasesVides(){
        for (int i=0; i<this.getNbLignes(); i++){
            for (int j=0; j<this.getNbColonnes(); j++){
                this.lePlateau.add(new CaseIntelligente());
            }
        }
    }
    

    /**
     * Méthode qui rend les cases intelligentes et donc créer ses voisines
     */
    private void rendLesCasesIntelligentes(){
        for (int i=0; i<this.getNbLignes(); i++){
            for (int j=0; j<this.getNbColonnes(); j++){
                if (i>0){
                    this.getCase(i, j).ajouterVoisine(this.getCase(i-1, j));
                }
                if (i<this.getNbLignes()-1){
                    this.getCase(i, j).ajouterVoisine(this.getCase(i+1, j));
                }
                if (j>0){
                    this.getCase(i, j).ajouterVoisine(this.getCase(i, j-1));
                }
                if (j<this.getNbColonnes()-1){
                    this.getCase(i, j).ajouterVoisine(this.getCase(i, j+1));
                }
                if (i>0 && j>0){
                    this.getCase(i, j).ajouterVoisine(this.getCase(i-1, j-1));
                }
                if (i>0 && j<this.getNbColonnes()-1){
                    this.getCase(i, j).ajouterVoisine(this.getCase(i-1, j+1));
                }
                if (i<this.getNbLignes()-1 && j>0){
                    this.getCase(i, j).ajouterVoisine(this.getCase(i+1, j-1));
                }
                if (i<this.getNbLignes()-1 && j<this.getNbColonnes()-1){
                    this.getCase(i, j).ajouterVoisine(this.getCase(i+1, j+1));
                }
            }
        }
    }
    /**
     * Renvoie le nombre de lignes du plateau
     * @return le nombre de lignes du plateau
     */
    public int getNbLignes(){
        return this.nbLignes;
    }

    /**
     * Renvoie le nombre de colonnes du plateau
     * @return le nombre de colonnes du plateau
     */
    public int getNbColonnes(){
        return this.nbColonnes;
    }

    /**
     * Renvoie le nombre de bombes du plateau
     * @return le nombre de bombes du plateau
     */
    public int getNbTotalBombes(){
        return this.nbBombes;
    }

    /**
     * Renvoie le nombre de cases marquées du plateau
     * @return le nombre de cases marquées du plateau
     */
    public int getNbCasesMarquees(){
        int nbCasesMarquees = 0;
        for (CaseIntelligente c : this.lePlateau){
            if (c.estMarquee()){
                nbCasesMarquees++;
            }
        }
        return nbCasesMarquees;
    }

    /**
     * Renvoie le nombre de cases découvertes du plateau
     * @return le nombre de cases découvertes du plateau
     */
    public CaseIntelligente getCase(int numLigne, int numColonne){
        if (lePlateau.size()==0){
            return null;
        }
        return this.lePlateau.get(numLigne*this.getNbColonnes()+numColonne);
    }

    /**
     * Pose une bombe sur la case (x,y)
     * @param x la ligne de la case
     * @param y la colonne de la case
     */
    public void poseBombe(int x, int y){
        this.getCase(x, y).poseBombe();
    }

    /**
     * Pose des bombes aléatoirement sur le plateau
     */
    protected void poseDesBombesAleatoirement(){
        Random generateur = new Random();
        for (int x = 0; x < this.getNbLignes(); x++){
            for (int y = 0; y < this.getNbColonnes(); y++){
                if (generateur.nextInt(100)+1 < this.pourcentageDeBombes){
                    this.poseBombe(x, y);
                    this.nbBombes = this.nbBombes + 1;
                }
            }
        }
    }

    /**
     * Reinitialise le plateau
     */
    public void reset(){
        for (CaseIntelligente c : this.lePlateau){
            c.reset();
        }
        this.nbBombes = 0;
    }

}
