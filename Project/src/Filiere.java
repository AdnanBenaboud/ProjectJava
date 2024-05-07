import java.util.*;

public class Filiere {
	protected String id;
	protected String nom;
	protected String objectif;
	protected Filiere filiere;

	private String tableName = "filiere";
	private JBDConnector DB = new JBDConnector();
	
	public Filiere(String id, String nom, String objectif) {
		this.id =id;
		this.nom = nom;
		this.objectif = objectif;
	}
	public void Ajouter() {
		DB.add(this.tableName,
				new String[] {
						this.id, 
						this.nom,
						this.objectif
				});
	}
	public void Modifier(String[] newRow) {
		DB.update(this.tableName,
				newRow,
				new String[] {
						this.id, 
						this.nom,
						this.objectif
				});
		this.id = newRow[0] ;
		this.nom = newRow[1];
		this.objectif = newRow[2];

	}

	public void Supprimer() {
		DB.delete(this.tableName, new String[] { "id_filiere" },
				new String[] { this.id });
	}
	
}
