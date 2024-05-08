public class Note {
    protected float note;
    protected String id_etudiant;
    protected String id_matiere;
    private JBDConnector DB = new JBDConnector();
    private String tableName = "note";

    public Note(String id_etudiant, String id_matiere, float note) {

        this.note = note;
        this.id_etudiant = id_etudiant;
        this.id_matiere = id_matiere;

    }

    // Ajouter a notre base de donnees
    public void Ajouter() {
        DB.add(this.tableName,
                new String[] {
                        this.id_etudiant,
                        this.id_matiere,
                        Float.toString(this.note)
                });

    }

    // Modifier dans la base de donnees
    public void Modifier(String[] newRow) {
        try {
            DB.OneDArrayWithScript("0",
                    String.format(
                            "UPDATE `note` SET `note_finale` = '%s' WHERE `note`.`id_etudiant` = %s AND `note`.`id_matiere` = '%s'",
                            newRow[6], newRow[0], newRow[1]));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }

        this.note = Float.parseFloat(newRow[6]);
        this.id_etudiant = newRow[0];
        this.id_matiere = newRow[1];

    }

    public void Supprimer() {

    }
}
