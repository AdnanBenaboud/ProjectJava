
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

public class FiliereGUI implements ActionListener, TableModelListener {
    private String tableName = "filiere";

    JFrame f;
    JPanel choix;
    JLabel titre;
    JButton afficher;
    JButton ajouter;

    // pour connecter a notre base de donnees
    private JBDConnector DB = new JBDConnector();

    JPanel contenu;
    private final JPanel afficherContent;
    private final JPanel ajouterContent;

    private JTable table;
    private JScrollPane tableScrollPane;
    private final JLabel id;
    private final JTextField idField;
    private final JLabel nom;
    private final JButton supprimer_1;
    private JComboBox choixFiliere;

    // Liste des filières
    private ArrayList<Filiere> Filieres;
    private JTextPane objectifPan;
    private JTextField nomField;

    public FiliereGUI() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); // Windows Look and Feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        f = new JFrame();
        choix = new JPanel();
        titre = new JLabel("Filières");
        titre.setVerticalAlignment(SwingConstants.TOP);
        afficher = new JButton("Liste");
        afficher.setVerticalAlignment(SwingConstants.TOP);
        ajouter = new JButton("+");
        ajouter.setVerticalAlignment(SwingConstants.TOP);

        contenu = new JPanel();
        afficherContent = new JPanel();
        ajouterContent = new JPanel();

        table = new JTable();

        id = new JLabel("Id");
        id.setVerticalAlignment(SwingConstants.TOP);
        idField = new JTextField();
        nom = new JLabel("Nom");
        nom.setVerticalAlignment(SwingConstants.TOP);
        supprimer_1 = new JButton("Supprimer");
        supprimer_1.setVerticalAlignment(SwingConstants.TOP);
        choixFiliere = new JComboBox();

        idField.setBounds(116, 48, 240, 27);
        idField.setHorizontalAlignment(SwingConstants.LEFT);
        idField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        idField.setColumns(35);
        f.setTitle("Filières");
        f.setBounds(300, 150, 600, 400);
        f.getContentPane().setLayout(null);

        choix.setBounds(0, 0, 128, 363);
        choix.setBackground(new Color(138, 43, 226));
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

        // Créer la table avec les donees des filiere
        afficherTable();

        table.setShowHorizontalLines(false);
        table.setFillsViewportHeight(true);

        tableScrollPane = new JScrollPane(table);

        afficherContent.add(tableScrollPane);

        supprimer_1.setForeground(new Color(0, 0, 0));
        supprimer_1.setFont(new Font("Gabriola", Font.PLAIN, 20));
        supprimer_1.setBackground(new Color(240, 255, 240));
        supprimer_1.addActionListener(this);
        supprimer_1.addActionListener(this::supprimerFiliere);

        afficherContent.add(supprimer_1, BorderLayout.SOUTH);

        // f.getContentPane().add(afficherContent);

        ajouterContent.setBackground(new Color(0, 206, 209));
        ajouterContent.setBounds(128, 0, 458, 363);

        f.getContentPane().add(ajouterContent);

        ajouterContent.setLayout(null);
        id.setBounds(116, 26, 35, 27);
        id.setHorizontalAlignment(SwingConstants.LEFT);
        id.setFont(new Font("Gabriola", Font.PLAIN, 20));

        ajouterContent.add(id);

        ajouterContent.add(idField);
        nom.setHorizontalAlignment(SwingConstants.LEFT);
        nom.setFont(new Font("Gabriola", Font.PLAIN, 20));
        nom.setBounds(116, 86, 86, 27);

        ajouterContent.add(nom);

        JLabel Objectif = new JLabel("Objectif");
        Objectif.setVerticalAlignment(SwingConstants.TOP);
        Objectif.setHorizontalAlignment(SwingConstants.LEFT);
        Objectif.setFont(new Font("Gabriola", Font.PLAIN, 20));
        Objectif.setBounds(116, 146, 86, 27);
        ajouterContent.add(Objectif);

        JButton ajouterBtn = new JButton("Ajouter");
        ajouterBtn.setVerticalAlignment(SwingConstants.TOP);
        ajouterBtn.setFont(new Font("Gabriola", Font.PLAIN, 20));
        ajouterBtn.setBackground(new Color(255, 255, 255));

        objectifPan = new JTextPane();
        objectifPan.setBounds(116, 172, 240, 75);
        ajouterContent.add(objectifPan);
        ajouterBtn.setBounds(187, 277, 92, 35);
        ajouterContent.add(ajouterBtn);

        nomField = new JTextField();
        nomField.setHorizontalAlignment(SwingConstants.LEFT);
        nomField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        nomField.setColumns(35);
        nomField.setBounds(116, 108, 240, 27);
        ajouterContent.add(nomField);

        // Action listeners pour gerer l'appui des buttons
        afficher.addActionListener(this);
        // Ici, "afficherPanel" est la fonction qui sera execute si la button
        // "afficher" est pressé
        afficher.addActionListener(this::afficherPanel);

        ajouter.addActionListener(this);
        ajouter.addActionListener(this::ajouterPanel);

        ajouterBtn.addActionListener(this);
        ajouterBtn.addActionListener(this::ajouterFiliere);

        // Ajouter les etudiants a la liste des "Filières"
        getListFilieres();

        f.setVisible(true);
        f.setResizable(false);
    }

    public static void main(String[] args) {
        new FiliereGUI();
    }

    // Si il y a une changement dans la table
    @Override
    public void tableChanged(TableModelEvent e) {
        // TODO Auto-generated method stub

        // l'indice de la table qui à changé
        int rowIndex = e.getFirstRow();
        // la ligne qui à changé
        String[] newRow = getRow(rowIndex);
        // on modifie l'objet de filiere
        Filieres.get(rowIndex).Modifier(newRow);
        // mise à jour de la table
        updateTable();

    }

    public void afficherTable() {

        table.setFont(new Font("DialogInput", Font.PLAIN, 12));

        table.setModel(new DefaultTableModel(
                DB.read(tableName),
                new String[] { "ID", "Nom", "Objectif" }));

        table.getModel().addTableModelListener(this::tableChanged);
    }

    public void updateTable() {
        // D'abord on supprime la table; On crée une nouveau table mise à jour
        // et on l'ajoute à notre frame

        f.remove(afficherContent);
        afficherContent.remove(tableScrollPane);
        tableScrollPane.remove(table);

        DefaultTableModel model = new DefaultTableModel(
                DB.read(tableName),
                new String[] { "ID", "Nom", "Objectif" });

        table = new JTable(model);
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

    public void getListFilieres() {
        // get La liste des filières à partir de la BDD
        Filieres = new ArrayList<Filiere>();
        String[][] listDesFilieres = DB.read(tableName);
        for (int i = 0; i < listDesFilieres.length; i++) {
            String[] filiere = listDesFilieres[i];
            Filieres.add(
                    new Filiere(
                            filiere[0],
                            filiere[1],
                            filiere[2]));
        }

    }

    // Afficher Panel pour Liste des filières
    public void afficherPanel(ActionEvent e) {
        // On change les panels ici;
        afficher.setBackground(new Color(60, 179, 113));
        ajouter.setBackground(new Color(211, 211, 211));

        f.getContentPane().remove(ajouterContent);
        table.setModel(new DefaultTableModel(
                DB.read(tableName),
                new String[] { "ID", "Nom", "Objectif" }));
        table.getModel().addTableModelListener(this::tableChanged);
        f.getContentPane().add(afficherContent);
        f.repaint();
        f.revalidate();

    }

    public void afficherPanel() {
        // Une autre version de la méme fonction qui peut étre utilisée indépendament
        // de l'ActionListener
        afficher.setBackground(new Color(60, 179, 113));
        ajouter.setBackground(new Color(211, 211, 211));

        f.getContentPane().remove(ajouterContent);
        table.setModel(new DefaultTableModel(
                DB.read(tableName),
                new String[] { "ID", "Nom", "Objectif" }));
        table.getModel().addTableModelListener(this::tableChanged);
        f.getContentPane().add(afficherContent);
        f.repaint();
        f.revalidate();

    }

    // Afficher Panel pour ajouter
    public void ajouterPanel(ActionEvent e) {
        // Changer les panels(afficher la panel qui permet d'ajouter une filiere)
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

    public void ajouterFiliere(ActionEvent e) {
        // Gérer les erreures
        if (idField.getText().trim().isEmpty()) {
            showError("Id vide ! ");
            return;
        } else if (nomField.getText().trim().isEmpty()) {
            showError("Nom vide ! ");
            return;

        } else if (objectifPan.getText().trim().isEmpty()) {
            showError("Objectif vide ! ");
            return;

        }
        Filiere filiere = new Filiere(
                idField.getText(),
                nomField.getText(),
                objectifPan.getText());
        filiere.Ajouter();
        // Ajouter la filiere crée à la BDD
        Filieres.add(filiere);
        getListFilieres();
        updateTable();
        afficherPanel();
        idField.setText("");
        nomField.setText("");
        objectifPan.setText("");
    }

    public void supprimerFiliere(ActionEvent e) {
        // Gérer les erreures
        if (table.getSelectedRowCount() == 0) {
            showError("Veuillez choisir un filière d'abord.");
            return;
        }

        int res = JOptionPane.showConfirmDialog(null, new JLabel("Voulez-vous supprimer cet filière?"),
                "Supprimer un filière",
                JOptionPane.CANCEL_OPTION);
        if (res == 0) {
            int selectedRowIndex = table.getSelectedRow();
            // Supprimer le filiere crée de la BDD
            Filieres.get(selectedRowIndex).Supprimer();
            getListFilieres();
            updateTable();
        } else {
            updateTable();
            return;
        }

    }
}