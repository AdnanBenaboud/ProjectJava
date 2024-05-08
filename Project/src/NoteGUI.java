
import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
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

public class NoteGUI implements ActionListener, TableModelListener {
    private String tableName = "notes";

    JFrame f;
    JPanel choix;
    JLabel titre;

    // pour connecter a notre base de donnees
    private JBDConnector DB = new JBDConnector();

    JPanel contenu;
    private final JPanel afficherContent;

    private JTable table;
    private JScrollPane tableScrollPane;
    private final JButton supprimer_1;
    private JComboBox choixFiliere;
    private JComboBox choixSemestre;
    private JComboBox choixMatiere;

    // Liste des filières
    private ArrayList<Note> Notes;
    private JButton add;
    private JLabel lblNewLabel_2;

    public NoteGUI() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); // Windows Look and Feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        f = new JFrame();
        f.getContentPane().setBackground(new Color(95, 158, 160));
        choix = new JPanel();
        titre = new JLabel("Notes");
        titre.setVerticalAlignment(SwingConstants.TOP);

        contenu = new JPanel();
        afficherContent = new JPanel();

        table = new JTable();
        supprimer_1 = new JButton("Supprimer");
        supprimer_1.setVerticalAlignment(SwingConstants.TOP);
        choixFiliere = new JComboBox();
        f.setTitle("Notes");
        f.setBounds(300, 150, 600, 400);
        f.getContentPane().setLayout(null);

        choix.setBounds(0, 0, 128, 363);
        choix.setBackground(new Color(95, 158, 160));
        f.getContentPane().add(choix);
        choix.setLayout(null);
        titre.setBounds(0, 26, 128, 58);

        titre.setHorizontalAlignment(SwingConstants.CENTER);
        titre.setForeground(new Color(255, 255, 255));
        titre.setFont(new Font("Gabriola", Font.PLAIN, 40));
        choix.add(titre);
        f.getContentPane().add(choix);

        choixFiliere = new JComboBox();
        choixFiliere.setModel(new DefaultComboBoxModel(
                DB.getOneColumn("filiere", "id_filiere")));

        choixFiliere.setBounds(20, 123, 98, 22);
        choix.add(choixFiliere);
        choixFiliere.addActionListener(this);
        choixFiliere.addActionListener(this::getSemestreCorrespondants);

        choixSemestre = new JComboBox();
        choixSemestre.setModel(new DefaultComboBoxModel(new String[] {}));
        choixSemestre.setBounds(20, 173, 98, 22);
        choix.add(choixSemestre);

        choixSemestre.addActionListener(this);
        choixSemestre.addActionListener(this::getMatieresCorrespondants);

        choixMatiere = new JComboBox();
        choixMatiere.setModel(new DefaultComboBoxModel(new String[] {}));
        choixMatiere.setBounds(20, 227, 98, 22);
        choix.add(choixMatiere);

        JLabel lblNewLabel = new JLabel("Filiere");
        lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
        lblNewLabel.setFont(new Font("Yu Gothic Light", Font.PLAIN, 11));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBounds(20, 105, 49, 14);
        choix.add(lblNewLabel);

        JLabel lblSemestre = new JLabel("Semestre");
        lblSemestre.setVerticalAlignment(SwingConstants.TOP);
        lblSemestre.setForeground(Color.WHITE);
        lblSemestre.setFont(new Font("Yu Gothic Light", Font.PLAIN, 11));
        lblSemestre.setBounds(20, 156, 49, 14);
        choix.add(lblSemestre);

        JLabel lblNewLabel_1_1 = new JLabel("Matiere");
        lblNewLabel_1_1.setVerticalAlignment(SwingConstants.TOP);
        lblNewLabel_1_1.setForeground(Color.WHITE);
        lblNewLabel_1_1.setFont(new Font("Yu Gothic Light", Font.PLAIN, 11));
        lblNewLabel_1_1.setBounds(20, 212, 49, 14);
        choix.add(lblNewLabel_1_1);

        JButton Search = new JButton("Search");
        Search.setBackground(new Color(0, 191, 255));
        Search.setVerticalAlignment(SwingConstants.TOP);
        Search.setFont(new Font("Yu Gothic Light", Font.PLAIN, 11));
        Search.setBounds(30, 260, 72, 23);
        Search.addActionListener(this);
        Search.addActionListener(this::search);
        choix.add(Search);

        add = new JButton("+");
        add.addActionListener(this::addNewNote);

        add.setFont(new Font("Times New Roman", Font.PLAIN, 16));

        add.setBackground(new Color(0, 191, 255));
        add.setBounds(30, 313, 72, 23);
        choix.add(add);

        JLabel lblNewLabel_1 = new JLabel("_____________________\r\n");
        lblNewLabel_1.setForeground(new Color(255, 255, 255));
        lblNewLabel_1.setBounds(0, 287, 128, 14);
        choix.add(lblNewLabel_1);

        lblNewLabel_2 = new JLabel("_____________________\r\n");
        lblNewLabel_2.setForeground(Color.WHITE);
        lblNewLabel_2.setBounds(0, 70, 128, 14);
        choix.add(lblNewLabel_2);

        contenu.setBounds(129, 0, 457, 363);
        contenu.setBackground(new Color(0, 206, 209));
        afficherContent.setBackground(new Color(0, 206, 209));
        afficherContent.setBounds(129, 0, 457, 363);
        afficherContent.setLayout(new BorderLayout(0, 0));

        // Créer la table avec les donees des etudiants
        afficherTable();

        table.setShowHorizontalLines(false);
        table.setFillsViewportHeight(true);

        tableScrollPane = new JScrollPane(table);

        afficherContent.add(tableScrollPane);

        supprimer_1.setForeground(new Color(0, 0, 0));
        supprimer_1.setFont(new Font("Gabriola", Font.PLAIN, 20));
        supprimer_1.setBackground(new Color(240, 255, 240));
        supprimer_1.addActionListener(this);
        supprimer_1.addActionListener(this::supprimerNote);

        afficherContent.add(supprimer_1, BorderLayout.SOUTH);

        f.getContentPane().add(afficherContent);
        // Ajouter les etudiants a la liste des "Filières"

        getSemestreCorrespondants(null);
        getListNotes();
        f.setVisible(true);
        f.setResizable(false);
    }

    public static void main(String[] args) {
        new NoteGUI();
    }

    // Si il y a une changement dans la table
    @Override
    public void tableChanged(TableModelEvent e) {
        // TODO Auto-generated method stub

        // l'indice de la table qui à changé
        int rowIndex = e.getFirstRow();
        // la ligne qui à changé
        String[] newRow = getRow(rowIndex);
        // on modifie l'objet de l'étudiant
        Notes.get(rowIndex).Modifier(newRow);
        // mise à jour de la table
        updateTable();

    }

    public void afficherTable() {

        table.setFont(new Font("DialogInput", Font.PLAIN, 12));

        String[] columns = { "id_etudiant", "id_matiere", "nom_etudiant", "prenom_etudiant", "nom_matiere",
                "coefficient_matiere", "note_finale" };

        try {
            String script = String.format(
                    "SELECT etudiant.id_etudiant, matiere.id_matiere, nom_etudiant, prenom_etudiant, nom_matiere,coefficient_matiere, note_finale FROM etudiant, note, matiere WHERE etudiant.id_etudiant=note.id_etudiant and matiere.id_matiere=note.id_matiere and nom_matiere='%s'",
                    choixMatiere.getSelectedItem().toString());
            table.setModel(new DefaultTableModel(
                    DB.TwoDArrayWithScript(columns, script),
                    DB.getColumnsOfTable(tableName)));
        } catch (Exception e) {
            // TODO: handle exception
            table.setModel(new DefaultTableModel(
                    new String[][] { {
                            "", "", "", "", "", "", "", ""
                    } },
                    DB.getColumnsOfTable(tableName)));
        }

        table.getModel().addTableModelListener(this::tableChanged);
    }

    public void updateTable() {

        try {
            // D'abord on supprime la table; On crée une nouveau table mise à jour
            // et on l'ajoute à notre frame

            f.remove(afficherContent);
            afficherContent.remove(tableScrollPane);
            tableScrollPane.remove(table);

            String script = String.format(
                    "SELECT etudiant.id_etudiant, matiere.id_matiere, nom_etudiant, prenom_etudiant, nom_matiere,coefficient_matiere, note_finale FROM etudiant, note, matiere WHERE etudiant.id_etudiant=note.id_etudiant and matiere.id_matiere=note.id_matiere and nom_matiere='%s'",
                    choixMatiere.getSelectedItem().toString());
            String[] columns = { "id_etudiant", "id_matiere", "nom_etudiant", "prenom_etudiant", "nom_matiere",
                    "coefficient_matiere", "note_finale" };

            table.setModel(new DefaultTableModel(
                    DB.TwoDArrayWithScript(columns, script),
                    DB.getColumnsOfTable(tableName)));

            // table = new JTable(model);
            table.setFont(new Font("DialogInput", Font.PLAIN, 12));
            table.setFillsViewportHeight(true);
            table.setShowHorizontalLines(false);
            table.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
            table.getModel().addTableModelListener(this::tableChanged);

            tableScrollPane = new JScrollPane(table);
            afficherContent.add(tableScrollPane);
            f.getContentPane().add(afficherContent);

            f.repaint();
            f.revalidate();

        } catch (Exception error) {

            table.setModel(new DefaultTableModel(
                    new String[][] { {
                            "", "", "", "", "", "", "", ""
                    } },
                    DB.getColumnsOfTable(tableName)));

            // table = new JTable(model);
            table.setFont(new Font("DialogInput", Font.PLAIN, 12));
            table.setFillsViewportHeight(true);
            table.setShowHorizontalLines(false);
            table.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
            table.getModel().addTableModelListener(this::tableChanged);

            tableScrollPane = new JScrollPane(table);
            afficherContent.add(tableScrollPane);
            f.getContentPane().add(afficherContent);

            f.repaint();
            f.revalidate();

        }

    }

    public String[] getRow(int rowIndex) {
        // Une fonction qui nous permet d'obtenir la ligne dans une table
        // à partir des l'indice de cette ligne.

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

    public void getListNotes() {
        try {
            String script = String.format(
                    "SELECT etudiant.id_etudiant, matiere.id_matiere, nom_etudiant, prenom_etudiant, nom_matiere,coefficient_matiere, note_finale FROM etudiant, note, matiere WHERE etudiant.id_etudiant=note.id_etudiant and matiere.id_matiere=note.id_matiere and nom_matiere='%s'",
                    choixMatiere.getSelectedItem().toString());
            String[] columns = { "id_etudiant", "id_matiere", "nom_etudiant", "prenom_etudiant", "nom_matiere",
                    "coefficient_matiere", "note_finale" };
            // get La liste des filières à partir de la BDD
            Notes = new ArrayList<Note>();
            String[][] listDesNotes = DB.TwoDArrayWithScript(columns, script);
            for (int i = 0; i < listDesNotes.length; i++) {
                String[] note = listDesNotes[i];
                Notes.add(
                        new Note(note[0], note[1], Float.parseFloat(note[6])));

            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(f, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void supprimerNote(ActionEvent e) {
        // Gérer les erreures
        if (table.getSelectedRowCount() == 0) {
            showError("Veuillez choisir un note d'abord.");
            return;
        }

        int res = JOptionPane.showConfirmDialog(null, new JLabel("Voulez-vous supprimer cet note?"),
                "Supprimer un note",
                JOptionPane.CANCEL_OPTION);
        if (res == 0) {
            int selectedRowIndex = table.getSelectedRow();
            // Supprimer l'étudiant crée de la BDD
            Notes.get(selectedRowIndex).Supprimer();
            getListNotes();
            updateTable();
        } else {
            updateTable();
            return;
        }

    }

    public void getSemestreCorrespondants(ActionEvent e) {

        String[] listeDesNiveaux = DB.getOneColumnWithConditions(
                "semestre",
                "id_semestre",
                new String[] { "id_filiere" },
                new String[] { choixFiliere.getSelectedItem().toString() });

        // Mise a jour des choix des semestres/Niveaux
        choixSemestre.setModel(new DefaultComboBoxModel(
                listeDesNiveaux));

        getMatieresCorrespondants(e);
    }

    public void getMatieresCorrespondants(ActionEvent e) {

        try {
            String[] listeDesNiveaux = DB.OneDArrayWithScript(
                    "nom_matiere",
                    String.format(
                            "SELECT nom_matiere FROM matiere, module WHERE module.id_module=matiere.id_module and `id_semestre` = '%s';",
                            choixSemestre.getSelectedItem().toString()));
            choixMatiere.setModel(new DefaultComboBoxModel(
                    listeDesNiveaux));
        } catch (Exception error) {
            // TODO: handle exception
            System.out.println("Aucune semestre est choisie");
            return;
        }

        // Mise a jour des choix des semestres/Niveaux

    }

    public void addNewNote(ActionEvent e) {
        try {
            new addNote(choixFiliere.getSelectedItem().toString(),
                    choixSemestre.getSelectedItem().toString(),
                    choixMatiere.getSelectedItem().toString());
        } catch (Exception error) {
            showError("Veuillez choisir Filiere, Semestre et Matiere");
            return;
        }

    }

    public void search(ActionEvent e) {

        if (choixFiliere.getSelectedItem() == null) {
            showError("Choisie une Filiere ! ");
            return;

        } else if (choixMatiere.getSelectedItem() == null) {
            showError("Choisie une Matiere ! ");
            return;

        } else if (choixSemestre.getSelectedItem() == null) {
            showError("Choisie une Semestre ! ");
            return;

        }

        try {
            f.remove(afficherContent);
            afficherContent.remove(tableScrollPane);
            tableScrollPane.remove(table);

            String script = String.format(
                    "SELECT etudiant.id_etudiant, matiere.id_matiere, nom_etudiant, prenom_etudiant, nom_matiere,coefficient_matiere, note_finale FROM etudiant, note, matiere WHERE etudiant.id_etudiant=note.id_etudiant and matiere.id_matiere=note.id_matiere and nom_matiere='%s'",
                    choixMatiere.getSelectedItem().toString());
            String[] columns = { "id_etudiant", "id_matiere", "nom_etudiant", "prenom_etudiant", "nom_matiere",
                    "coefficient_matiere", "note_finale" };

            table.setModel(new DefaultTableModel(
                    DB.TwoDArrayWithScript(columns, script),
                    DB.getColumnsOfTable(tableName)));

            // table = new JTable(model);
            table.setFont(new Font("DialogInput", Font.PLAIN, 12));
            table.setFillsViewportHeight(true);
            table.setShowHorizontalLines(false);
            table.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
            table.getModel().addTableModelListener(this::tableChanged);

            tableScrollPane = new JScrollPane(table);
            afficherContent.add(tableScrollPane);
            f.getContentPane().add(afficherContent);
            getListNotes();

            f.repaint();
            f.revalidate();
        } catch (Exception error) {
            table.setModel(new DefaultTableModel(
                    new String[][] { {
                            "", "", "", "", "", "", "", ""
                    } },
                    DB.getColumnsOfTable(tableName)));

            // table = new JTable(model);
            table.setFont(new Font("DialogInput", Font.PLAIN, 12));
            table.setFillsViewportHeight(true);
            table.setShowHorizontalLines(false);
            table.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
            table.getModel().addTableModelListener(this::tableChanged);

            tableScrollPane = new JScrollPane(table);
            afficherContent.add(tableScrollPane);
            f.getContentPane().add(afficherContent);

            f.repaint();
            f.revalidate();
            showError("Pas des resultats");
        }
    }
}