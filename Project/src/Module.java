import java.util.ArrayList;

public class Module {
    
    protected String IDModule;
    protected String NomModule;
    protected String NiveauModule;
    protected String DescriptionModule;
    protected ArrayList<Matiere> matiere;
    protected JBDConnector DB = new JBDConnector();
    protected String tableName = "module";
    
    public Module(String IDModule,String NomModule,String NiveauModule,String DescriptionModule){
        this.IDModule=IDModule;
        this.NomModule=NomModule;
        this.NiveauModule=NiveauModule;
        this.DescriptionModule=DescriptionModule;
        this.matiere=new ArrayList<>();
    };

    // Ajouter a notre base de donnees
	public void Ajouter() {
		DB.add(this.tableName,
				new String[] {
						this.IDModule,
						this.NomModule,
						this.DescriptionModule,
				});
	}

    public void modifier(String[] newRow){
        DB.update(this.tableName, newRow,
        new String[] {
                this.IDModule, 
                this.NomModule,
                this.NiveauModule,
                this.DescriptionModule
        });
            this.IDModule = newRow[0] ;
            this.NomModule= newRow[1];
            this.NiveauModule = newRow[2];
            this.DescriptionModule = newRow[3];
    }

    public void supprimer(){
        DB.delete(this.tableName, new String[] { "id_module" },
        new String[] { this.IDModule });
    }
    

}
