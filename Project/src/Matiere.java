public class Matiere {
    protected int IDMatiere;
    protected String NomMatiere;
    protected float VolumeHorraire;
    protected int Coefficient;
    protected String description;
    Note note;
    private JBDConnector DB = new JBDConnector();
	private String tableName = "matiere";
    
    public Matiere(int IDMatiere,String NomMatiere,float VolumeHorraire,int Coefficient,String description,Note note){
        this.IDMatiere=IDMatiere;
        this.NomMatiere=NomMatiere;
        this.VolumeHorraire=VolumeHorraire;
        this.Coefficient=Coefficient;
        this.description=description;
        this.note=note;
    };


    

}
