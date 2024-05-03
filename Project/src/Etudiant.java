
import javax.swing.*;
import java.awt.Color;

public class Etudiant {
	protected String id;
	protected String nom;
	protected String prenom;
	protected Filiere filiere;
	protected String niveau;
	private JBDConnector DB = new JBDConnector();
	private String tableName = "etudiant";

	/**
	 * @wbp.parser.entryPoint
	 */
	public Etudiant(String id, String nom, String prenom, String niveau, Filiere filiere) {

		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.filiere = filiere;
		this.niveau = niveau;
	}

	// Ajouter a notre base de donnees
	public void Ajouter() {
		DB.add(this.tableName,
				new String[] {
						this.nom,
						this.prenom,
						this.niveau,
						this.filiere.id
				});
	}

	// Modifier dans la base de donnees
	public void Modifier(String[] newRow) {
		DB.update(this.tableName,
				newRow,
				new String[] {
						this.id,
						this.nom,
						this.prenom,
						this.niveau,
						this.filiere.id
				});
		this.nom = newRow[1];
		this.prenom = newRow[2];
		this.niveau = newRow[3];
		this.filiere.id = newRow[4];

	}

	public void Supprimer() {
		DB.delete(this.tableName, new String[] { "id_etudiant" },
				new String[] { this.id });
	}

	public static void main(String[] args) {

	}
}
