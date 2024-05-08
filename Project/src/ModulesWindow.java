import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.border.MatteBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.*;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Window.Type;

public class ModulesWindow implements ActionListener, TableModelListener {
    private String tableName = "module";

    JFrame f;
    JPanel choix;
    JLabel titre;
    JButton afficher;
    JButton ajouter;
    
//    pour connecter a notre base de donnees
    private JBDConnector DB = new JBDConnector();

    JPanel contenu;
    private final JPanel afficherContent;
    private final JPanel ajouterContent;

    private JTable table;
    private JScrollPane tableScrollPane;
    private final JLabel Nom;
    private final JTextField nomField;
    private final JLabel Prenom;
    private JTextField prenomField;
    private JTextPane DescriptionPan;
    private final JLabel Description;
    private final JComboBox choixNiveaux;
    private final JButton supprimer_1;
    private JComboBox choixFiliere;

//    Liste des modules
    private ArrayList<Module> Modules;

    public ModulesWindow() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); // Windows Look and Feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        f = new JFrame();
        choix = new JPanel();
        titre = new JLabel("Modules");
        titre.setVerticalAlignment(SwingConstants.TOP);
        afficher = new JButton("Liste");
        afficher.setVerticalAlignment(SwingConstants.TOP);
        ajouter = new JButton("+");
        ajouter.setVerticalAlignment(SwingConstants.TOP);

        contenu = new JPanel();
        afficherContent = new JPanel();
        ajouterContent = new JPanel();

        table = new JTable();

        Nom = new JLabel("ID");
        Nom.setVerticalAlignment(SwingConstants.TOP);
        nomField = new JTextField();
        Prenom = new JLabel("Nom");
        Prenom.setVerticalAlignment(SwingConstants.TOP);

        Description = new JLabel("Description");
        Description.setVerticalAlignment(SwingConstants.TOP);
        Description.setHorizontalAlignment(SwingConstants.LEFT);
        Description.setFont(new Font("Gabriola", Font.PLAIN, 20));
        Description.setBounds(116, 260, 86, 27);

        ajouterContent.add(Description);
        
        DescriptionPan = new JTextPane();
        DescriptionPan.setBounds(116, 280, 240, 75);
        ajouterContent.add(DescriptionPan);


        choixNiveaux = new JComboBox();
        supprimer_1 = new JButton("Supprimer");
        supprimer_1.setVerticalAlignment(SwingConstants.TOP);
        choixFiliere = new JComboBox();

        nomField.setBounds(116, 48, 240, 27);
        nomField.setHorizontalAlignment(SwingConstants.LEFT);
        nomField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        nomField.setColumns(35);
        f.setTitle("Étudiants");
        f.setBounds(300,150, 600, 500);
        f.getContentPane().setLayout(null);

        choix.setBounds(0, 0, 128, 500);
        choix.setBackground(new Color(220, 20, 60));
        f.getContentPane().add(choix);
        choix.setLayout(null);
        titre.setBounds(0, 43, 128, 58);

        titre.setHorizontalAlignment(SwingConstants.CENTER);
        titre.setForeground(new Color(255, 255, 255));
        titre.setFont(new Font("Gabriola", Font.PLAIN, 40));
        choix.add(titre);
        afficher.setForeground(new Color(0, 0, 0));
        afficher.setBounds(0, 129, 128, 33);

        afficher.setFont(new Font("Gabriola", Font.PLAIN, 20));
        afficher.setBackground(new Color(211, 211, 211));
        choix.add(afficher);
        ajouter.setForeground(new Color(0, 0, 0));
        ajouter.setBounds(0, 173, 128, 33);

        ajouter.setFont(new Font("Yu Gothic", Font.PLAIN, 20));
        ajouter.setBackground(new Color(211, 211, 211));
        choix.add(ajouter);
        f.getContentPane().add(choix);

        contenu.setBounds(129, 0, 457, 363);
        contenu.setBackground(new Color(0, 206, 209));
        afficherContent.setBackground(new Color(0, 206, 209));
        afficherContent.setBounds(129, 0, 457, 363);
        afficherContent.setLayout(new BorderLayout(0, 0));
        
//      Créez la table avec les données des étudiants
        afficherTable();
        
        table.setShowHorizontalLines(false);
        table.setFillsViewportHeight(true);

        tableScrollPane = new JScrollPane(table);

        afficherContent.add(tableScrollPane);

        supprimer_1.setForeground(new Color(0, 0, 0));
        supprimer_1.setFont(new Font("Gabriola", Font.PLAIN, 20));
        supprimer_1.setBackground(new Color(240, 255, 240));
        supprimer_1.addActionListener(this);
        supprimer_1.addActionListener(this::supprimerModule);

        afficherContent.add(supprimer_1, BorderLayout.SOUTH);

//         f.getContentPane().add(afficherContent);

        ajouterContent.setBackground(new Color(0, 206, 209));
        ajouterContent.setBounds(128, 0, 458, 500);

        f.getContentPane().add(ajouterContent);

        ajouterContent.setLayout(null);
        Nom.setBounds(116, 26, 35, 27);
        Nom.setHorizontalAlignment(SwingConstants.LEFT);
        Nom.setFont(new Font("Gabriola", Font.PLAIN, 20));

        ajouterContent.add(Nom);

        ajouterContent.add(nomField);
        Prenom.setHorizontalAlignment(SwingConstants.LEFT);
        Prenom.setFont(new Font("Gabriola", Font.PLAIN, 20));
        Prenom.setBounds(116, 86, 86, 27);

        ajouterContent.add(Prenom);

        prenomField = new JTextField();
        prenomField.setHorizontalAlignment(SwingConstants.LEFT);
        prenomField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        prenomField.setColumns(35);
        prenomField.setBounds(116, 108, 240, 27);
        ajouterContent.add(prenomField);

        JLabel Filiere = new JLabel("Filière");
        Filiere.setVerticalAlignment(SwingConstants.TOP);
        Filiere.setHorizontalAlignment(SwingConstants.LEFT);
        Filiere.setFont(new Font("Gabriola", Font.PLAIN, 20));
        Filiere.setBounds(116, 146, 86, 27);
        ajouterContent.add(Filiere);

        choixFiliere = new JComboBox();

        choixFiliere.setModel(new DefaultComboBoxModel(
                DB.getOneColumn("filiere", "id_filiere")));
        choixFiliere.setBounds(116, 170, 240, 27);
        choixFiliere.addActionListener(this);
        choixFiliere.addActionListener(this::getSemestreCorrespondants);
        ajouterContent.add(choixFiliere);
        

        JButton ajouterBtn = new JButton("Ajouter");
        ajouterBtn.setVerticalAlignment(SwingConstants.TOP);
        ajouterBtn.setFont(new Font("Gabriola", Font.PLAIN, 20));
        ajouterBtn.setBackground(new Color(255, 255, 255));
        ajouterBtn.setBounds(187, 400, 92, 35);
        ajouterContent.add(ajouterBtn);
        choixNiveaux.setModel(new DefaultComboBoxModel(
        DB.getOneColumnWithConditions(
            			"semestre",
            			"id_semestre",
            			new String[] {"id_filiere"}, 
            			new String[] {choixFiliere.getSelectedItem().toString()})
                ));
        choixNiveaux.setBounds(116, 228, 240, 27);

        ajouterContent.add(choixNiveaux);

        JLabel niveau = new JLabel("Semestre");
        niveau.setVerticalAlignment(SwingConstants.TOP);
        niveau.setHorizontalAlignment(SwingConstants.LEFT);
        niveau.setFont(new Font("Gabriola", Font.PLAIN, 20));
        niveau.setBounds(116, 208, 86, 27);
        ajouterContent.add(niveau);

        // Action listeners pour gerer l'appui des buttons
        afficher.addActionListener(this);
        
//      Ici, "afficherPanel" est la fonction qui sera execute si la button
//       "afficher" est pressé
        afficher.addActionListener(this::afficherPanel);

        ajouter.addActionListener(this);
        ajouter.addActionListener(this::ajouterPanel);

        ajouterBtn.addActionListener(this);
        ajouterBtn.addActionListener(this::ajouterModule);

//      Ajouter les etudiants a la liste des etudiants "Etudiants"
        getListModules();

        f.setVisible(true);
        f.setResizable(false);
    }

    public static void main(String[] args) {
        new ModulesWindow();
    }

//    Si il y a une changement dans la table
    @Override
    public void tableChanged(TableModelEvent e) {
        // TODO Auto-generated method stub
    	
//    	l'indice de la table qui à changé
        int rowIndex = e.getFirstRow();

//        la ligne qui à changé
        String[] newRow = getRow(rowIndex);
        
//      on modifie l'objet de l'étudiant 
        Modules.get(rowIndex).modifier(newRow);
//      mise à jour de la table
        updateTable();

    }

    public void afficherTable() {

        table.setFont(new Font("DialogInput", Font.PLAIN, 16));

        table.setModel(new DefaultTableModel(
                DB.read(tableName),
                DB.getColumnsOfTable(tableName)));

        table.getModel().addTableModelListener(this::tableChanged);
    }

    public void updateTable() {
//    	D'abord on supprime la table; On crée une nouveau table mise à jour
//    	et on l'ajoute à notre frame
    	
        f.remove(afficherContent);
        afficherContent.remove(tableScrollPane);
        tableScrollPane.remove(table);

        DefaultTableModel model = new DefaultTableModel(
                DB.read(tableName),
                DB.getColumnsOfTable(tableName));

        table = new JTable(model);
        table.setFont(new Font("DialogInput", Font.PLAIN, 16));
        table.setFillsViewportHeight(true);
        table.setShowHorizontalLines(false);
        table.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        table.getModel().addTableModelListener(this::tableChanged);

        tableScrollPane = new JScrollPane(table);
        afficherContent.add(tableScrollPane);
        f.getContentPane().add(afficherContent);
        table.setModel(model);

        f.repaint();
        f.revalidate();
    }

    public String[] getRow(int rowIndex) {
//    	Une fonction qui nous permet d'obtenir la ligne dans une table 
//    	à partir des l'indice de cette ligne.
    	
        String[] row = new String[table.getColumnCount()];
        for (int k = 0; k < table.getColumnCount(); k++) {
            row[k] = (String) table.getModel().getValueAt(rowIndex, k);

        }
        return row;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

    public void getListModules() {
    	// get La liste des modules à partir de la BDD
        Modules = new ArrayList<Module>();
        String[][] listDesModules = DB.read(tableName);
        for (int i = 0; i < listDesModules.length; i++) {
            String[] module = listDesModules[i];
            Modules.add(
                    new Module(module[0],
                            module[1],
                            module[2],
                            module[3],
                            new Filiere(module[4], null, null)
                            ));
        }

    }
    
    

    // Afficher Panel pour Liste des etudiants
    public void afficherPanel(ActionEvent e) {
//    	On change les panels ici; 
        afficher.setBackground(new Color(60, 179, 113));
        ajouter.setBackground(new Color(211, 211, 211));

        f.getContentPane().remove(ajouterContent);
        table.setModel(new DefaultTableModel(
                DB.read(tableName),
                DB.getColumnsOfTable(tableName)));
        table.getModel().addTableModelListener(this::tableChanged);
        f.getContentPane().add(afficherContent);
        f.repaint();
        f.revalidate();

    }

    public void afficherPanel() {
//    	Une autre version de la méme fonction qui peut étre utilisée indépendament
//    	de l'ActionListener
        afficher.setBackground(new Color(60, 179, 113));
        ajouter.setBackground(new Color(211, 211, 211));

        f.getContentPane().remove(ajouterContent);
        table.setModel(new DefaultTableModel(
                DB.read(tableName),
                DB.getColumnsOfTable(tableName)));
        table.getModel().addTableModelListener(this::tableChanged);
        f.getContentPane().add(afficherContent);
        f.repaint();
        f.revalidate();

    }

    // Afficher Panel pour ajouter
    public void ajouterPanel(ActionEvent e) {
//    	Changer les panels(afficher la panel qui permet d'ajouter une étudiant)
        ajouter.setBackground(new Color(60, 179, 113));
        afficher.setBackground(new Color(211, 211, 211));
        f.getContentPane().remove(afficherContent);
        f.getContentPane().add(ajouterContent);
        f.repaint();
        f.revalidate();

    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(f, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void ajouterModule(ActionEvent e) {
    	
//      Gérer les erreures 
        if (nomField.getText().trim().isEmpty()) {
            showError("Nom vide ! ");
            return;
        } else if (prenomField.getText().trim().isEmpty()) {
            showError("Prenom vide ! ");
            return;

        }
//       Si il n'y a pas une des semestres/Niveaux disponible pour cette filiere
        else if (choixNiveaux.getSelectedItem()==null) {
            showError("Niveau ou Semestre invalide ! ");
            return;

        }
        
        Module module = new Module(
                nomField.getText(),
                prenomField.getText(),
                DescriptionPan.getText(),
                choixNiveaux.getSelectedItem().toString(),
                new Filiere(choixFiliere.getSelectedItem().toString(), null, null)
                );
        
//      Ajouter le module crée à la BDD
        module.Ajouter();
        Modules.add(module);
        getListModules();
        updateTable();
        afficherPanel();
        nomField.setText("");
        prenomField.setText("");
        choixNiveaux.setSelectedItem(0);
        choixFiliere.setSelectedItem(0);
        DescriptionPan.setText("");

    }

    public void supprimerModule(ActionEvent e) {
//      Gérer les erreures 
        if (table.getSelectedRowCount() == 0) {
            showError("Veuillez choisir un module d'abord.");
            return;
        }
        
        int res = JOptionPane.showConfirmDialog(null, new JLabel("Voulez-vous supprimer ce module?"), "Supprimer un module",
        	      JOptionPane.CANCEL_OPTION);
      if(res == 0) {
    	  int selectedRowIndex = table.getSelectedRow();
//        Supprimer l'étudiant crée de la BDD
          Modules.get(selectedRowIndex).supprimer();
          getListModules();
          updateTable();
      }
      else {
    	  updateTable();
         return;
      }
      
        	      
        
    }
    public void getSemestreCorrespondants(ActionEvent e) {
    	
    	String[] listeDesNiveaux = DB.getOneColumnWithConditions(
    			"semestre",
    			"id_semestre",
    			new String[] {"id_filiere"}, 
    			new String[] {choixFiliere.getSelectedItem().toString()});
    	
//    	Mise a jour des choix des semestres/Niveaux
    	choixNiveaux.setModel(new DefaultComboBoxModel(
    			listeDesNiveaux));
    }
}