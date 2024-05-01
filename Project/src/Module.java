import java.util.ArrayList;

public class Module {
    
    protected int IDModule;
    protected String NomModule;
    protected String NiveauModule;
    protected String DescriptionModule;
    protected ArrayList<Matiere> matiere;
    
    public Module(int IDModule,String NomModule,String NiveauModule,String DescriptionModule){
        this.IDModule=IDModule;
        this.NomModule=NomModule;
        this.NiveauModule=NiveauModule;
        this.DescriptionModule=DescriptionModule;
        this.matiere=new ArrayList<>();
    };


    protected void AjoutModule(){
        
    };
    protected void SupprimerModule(){

    };
    protected void ModifierModule(){

    };
    protected void ListeModules(){

    };

}
