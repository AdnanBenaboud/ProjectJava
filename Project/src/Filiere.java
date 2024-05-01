import java.util.ArrayList;

public class Filiere{
    protected int IDFiliere;
    protected String NomFiliere;
    protected String ObjectifFiliere;
    protected ArrayList<Module> Module;
    
    public Filiere(int IDFiliere,String NomFiliere,String ObjectifFiliere){
        this.IDFiliere=IDFiliere;
        this.NomFiliere=NomFiliere;
        this.ObjectifFiliere=ObjectifFiliere;
        this.Module= new ArrayList<>();
    };


    protected void AjoutModule(){
        
    };
    protected void SupprimerModule(){

    };
    protected void ModifierFiliere(){

    };
    protected void ListeFilieres(){

    };

}