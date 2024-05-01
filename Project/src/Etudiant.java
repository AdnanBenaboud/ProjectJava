
public class Etudiant {

    protected int IDEtudiant;
    protected String NomEtudiant;
    protected String PrenomEtudiant;
    protected String NiveauEtudiant;
    protected Filiere filiereEtu;


    public Etudiant(int IDEtudiant,String NomEtudiant,String PrenomEtudiant,String NiveauEtudiant,Filiere filiereEtu){
        this.IDEtudiant=IDEtudiant;
        this.NomEtudiant=NomEtudiant;
        this.PrenomEtudiant=PrenomEtudiant;
        this.NiveauEtudiant=NiveauEtudiant;
        this.filiereEtu=filiereEtu;
    };


    protected void AjoutEtudiant(){
        
    };
    protected void SupprimerEtudiant(){

    };
    protected void ModifierEtudiant(){

    };
    protected void ListeEtudiants(){
        
    };

    @Override
    public String toString(){
        return "Id: "+this.IDEtudiant +" Nom: "+this.NomEtudiant+" Prenom: "+this.PrenomEtudiant+" Niveau: "+this.NiveauEtudiant;
    }

    public static void main(String[] args) throws Exception {
    }
}
