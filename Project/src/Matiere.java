import java.util.ArrayList;


public class Matiere {
    protected int IDMatiere;
    protected String NomMatiere;
    protected float VolumeHorraire;
    protected int Coefficient;
    protected String description;
    protected Note note;
    private JBDConnector DB = new JBDConnector();
    private String tableName = "matiere";
    
    public Matiere(int IDMatiere, String NomMatiere, float VolumeHorraire, int Coefficient, String description, Note note){
        this.IDMatiere = IDMatiere;
        this.NomMatiere = NomMatiere;
        this.VolumeHorraire = VolumeHorraire;
        this.Coefficient = Coefficient;
        this.description = description;
        this.note = note;
    }
    
    // Ajouter à notre base de données
    public void Ajouter() {
        DB.add(tableName, new String[] {
            Integer.toString(this.IDMatiere),
            this.NomMatiere,
            Float.toString(this.VolumeHorraire),
            Integer.toString(this.Coefficient),
            this.description
        });
    }
    
    // Modifier une matière dans la base de données
    public void Modifier() {
        DB.update(tableName, new String[] {
            Integer.toString(this.IDMatiere),
            this.NomMatiere,
            Float.toString(this.VolumeHorraire),
            Integer.toString(this.Coefficient),
            this.description
        }, new String[] {
            "id_matiere" // Nom de la colonne pour la condition WHERE
        });
    }
    
    // Supprimer une matière de la base de données
    public void Supprimer() {
        DB.delete(tableName, new String[] { "id_matiere" }, new String[] { Integer.toString(this.IDMatiere) });
    }
}
