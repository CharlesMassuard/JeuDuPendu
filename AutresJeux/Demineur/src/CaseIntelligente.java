import java.util.List;
import java.util.ArrayList;

public class CaseIntelligente extends Case{
    private List<Case> lesVoisines;

    /**
     * Constructeur de la classe CaseIntelligente
     */
    public CaseIntelligente(){
        super();
        this.lesVoisines = new ArrayList<>();
    }


    /**
     * Méthode qui ajoute une case à la liste des voisines
     * @param c la case à ajouter
     */
    public void ajouterVoisine(Case c){
        this.lesVoisines.add(c);
    }

    public int nombreBombesVoisines(){
        int nbBombes = 0;
        for(Case c : this.lesVoisines){
            if(c.contientUneBombe()){
                nbBombes++;
            }
        }
        return nbBombes;
    }

    /**
     * Méthode qui retourne la liste des voisines
     * @return la liste des voisines
     */
    public List<Case> getVoisines(){
        return this.lesVoisines;
    }

    /**
     * Méthode qui reset la case
     */
    public void reset(){
        super.reset();
    }

    /**
     * Méthode qui retourne la case sous forme de String
     * @return la case sous forme de String
     */
    @Override
    public String toString(){
        return " "+this.nombreBombesVoisines();
    }
}
