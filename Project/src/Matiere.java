import java.util.ArrayList;


public class Matiere {
    protected String IDMatiere;
    protected String NomMatiere;
    protected String VolumeHorraire;
    protected String Coefficient;
    protected String description;
    protected String IDModule;

    protected Note note;
    private JBDConnector DB = new JBDConnector();
    private String tableName = "matiere";
    
    public Matiere(String IDMatiere, String NomMatiere, String VolumeHorraire, String Coefficient, String description, String IDModule){
        this.IDMatiere = IDMatiere;
        this.NomMatiere = NomMatiere;
        this.VolumeHorraire = VolumeHorraire;
        this.Coefficient = Coefficient;
        this.description = description;
        this.IDModule = IDModule;
    }
    
    // Ajouter à notre base de données
    public void Ajouter() {
        DB.add(tableName, new String[] {
            this.IDMatiere,
            this.NomMatiere,
            this.VolumeHorraire,
            this.Coefficient,
            this.description,
            this.IDModule
        });
    }
    
    // Modifier une matière dans la base de données
    public void Modifier(String[] newRow) {
        DB.update(this.tableName, newRow, new String[] {
            this.IDMatiere,
            this.NomMatiere,
            this.VolumeHorraire,
            this.Coefficient,
            this.description,
            this.IDModule
        }); 
        this.IDMatiere= newRow[0];
        this.NomMatiere=newRow[1];
        this.VolumeHorraire=newRow[2];
        this.Coefficient=newRow[3];
        this.description=newRow[4];
        this.IDModule= newRow[5];
       
    }
    
    // Supprimer une matière de la base de données
    public void Supprimer() {
        DB.delete(this.tableName, new String[] { "id_matiere" }, new String[] { this.IDMatiere });
    }
}
