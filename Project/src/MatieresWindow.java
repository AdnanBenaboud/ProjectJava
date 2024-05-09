import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MatieresWindow implements ActionListener, TableModelListener {
    private String tableName = "matiere";

    JFrame frame;
    JPanel choix;
    JLabel titre;
    JButton afficher;
    JButton ajouter;

    // To connect to our database
    private JBDConnector DB = new JBDConnector();

    JPanel contenu;
    private final JPanel afficherContent;
    private final JPanel ajouterContent;

    private JTable table;
    private JScrollPane tableScrollPane;
    private final JLabel id;
    private final JTextField idField;
    private final JLabel nom;
    private final JTextField nomField;
    private final JLabel module;
    private final JComboBox moduleLabel;
    private final JLabel volumeHoraireLabel;
    private final JTextField volumeHoraireField;
    private final JLabel coefficientLabel;
    private final JTextField coefficientField;
    private final JLabel descriptionLabel;
    private final JTextPane descriptionPane;
    private final JButton supprimer;

    // List of matieres
    private ArrayList<Matiere> Matieres;

    public MatieresWindow() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); // Windows Look and Feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        getListMatieres();
        frame = new JFrame();
        choix = new JPanel();
        titre = new JLabel("Matières");
        titre.setVerticalAlignment(SwingConstants.TOP);

        afficher = new JButton("Liste");
        afficher.setVerticalAlignment(SwingConstants.TOP);

        ajouter = new JButton("+");
        ajouter.setVerticalAlignment(SwingConstants.TOP);

        contenu = new JPanel();
        afficherContent = new JPanel();
        ajouterContent = new JPanel();

        table = new JTable();

        id = new JLabel("ID");
        id.setVerticalAlignment(SwingConstants.TOP);

        idField = new JTextField();
        nom = new JLabel("Nom");
        nom.setVerticalAlignment(SwingConstants.TOP);

        nomField = new JTextField();
        module = new JLabel("Module");
        module.setVerticalAlignment(SwingConstants.TOP);
        module.setBounds(50, 130, 120, 25);
        ajouterContent.add(module);

        moduleLabel = new JComboBox();
        moduleLabel.setModel(new DefaultComboBoxModel(
                DB.getOneColumn("module", "id_module")));
        moduleLabel.setBounds(116, 170, 240, 27);
        moduleLabel.addActionListener(this);
        ajouterContent.add(moduleLabel);

        volumeHoraireLabel = new JLabel("Volume Horaire");

        volumeHoraireField = new JTextField();
        coefficientLabel = new JLabel("Coefficient");
        coefficientField = new JTextField();
        descriptionLabel = new JLabel("Description");
        descriptionPane = new JTextPane();
        supprimer = new JButton("Supprimer");

        // Initialize components
        initializeComponents();

        frame.setVisible(true);
    }

    private void initializeComponents() {
        // Set frame properties
        frame.setTitle("Gestion des Matières");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Set choice panel properties
        choix.setBounds(0, 0, 128, 500);
        choix.setBackground(new Color(220, 20, 60));
        frame.getContentPane().add(choix);
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
        frame.getContentPane().add(choix);

        // Set content panel properties
        contenu.setBounds(129, 0, 457, 363);
        contenu.setBackground(new Color(0, 206, 209));
        afficherContent.setBackground(new Color(0, 206, 209));
        afficherContent.setBounds(129, 0, 457, 363);
        afficherContent.setLayout(new BorderLayout(0, 0));

        // Initialize table
        afficherTable();
        table.setShowHorizontalLines(false);
        table.setFillsViewportHeight(true);

        tableScrollPane = new JScrollPane(table);
        afficherContent.add(tableScrollPane);

        supprimer.setForeground(new Color(0, 0, 0));
        supprimer.setFont(new Font("Gabriola", Font.PLAIN, 20));
        supprimer.setBackground(new Color(240, 255, 240));
        afficherContent.add(supprimer, BorderLayout.SOUTH);

        // Set ajouter content panel properties
        ajouterContent.setBackground(new Color(0, 206, 209));
        ajouterContent.setBounds(128, 0, 458, 500);
        ajouterContent.setLayout(null);

        // Set component properties
        setComponentProperties();

        // Add action listeners
        afficher.addActionListener(e -> afficherPanel());
        ajouter.addActionListener(e -> ajouterPanel());
        supprimer.addActionListener(e -> supprimerMatiere());

        frame.getContentPane().add(ajouterContent);
    }

    private void setComponentProperties() {
        // Set properties for ID label and field
        id.setBounds(50, 50, 120, 25);
        id.setHorizontalAlignment(SwingConstants.LEFT);
        id.setFont(new Font("Gabriola", Font.PLAIN, 20));
        ajouterContent.add(id);
        idField.setBounds(180, 50, 200, 25);
        ajouterContent.add(idField);

        // Set properties for Nom label and field
        nom.setBounds(50, 90, 120, 25);
        ajouterContent.add(nom);
        nomField.setBounds(180, 90, 200, 25);
        ajouterContent.add(nomField);

        // Set properties for module label and field
        moduleLabel.setBounds(180, 130, 200, 25);
        ajouterContent.add(moduleLabel);

        // Set properties for volume horaire label and field
        volumeHoraireLabel.setBounds(50, 170, 120, 25);
        ajouterContent.add(volumeHoraireLabel);
        volumeHoraireField.setBounds(180, 170, 200, 25);
        ajouterContent.add(volumeHoraireField);

        // Set properties for coefficient label and field
        coefficientLabel.setBounds(50, 210, 120, 25);
        ajouterContent.add(coefficientLabel);
        coefficientField.setBounds(180, 210, 200, 25);
        ajouterContent.add(coefficientField);

        // Set properties for description label and pane
        descriptionLabel.setBounds(50, 250, 120, 25);
        ajouterContent.add(descriptionLabel);
        descriptionPane.setBounds(180, 250, 200, 100);
        ajouterContent.add(descriptionPane);

        // Set properties for ajouter button
        JButton ajouterBtn = new JButton("Ajouter");
        ajouterBtn.setBounds(180, 370, 100, 30);
        ajouterBtn.addActionListener(this::ajouterMatiere);
        ajouterContent.add(ajouterBtn);
    }

    public void afficherTable() {
        table.setModel(new DefaultTableModel(
                DB.read(tableName),
                DB.getColumnsOfTable(tableName)));

        table.getModel().addTableModelListener(this::tableChanged);
    }

    public void updateTable() {
        afficherContent.remove(tableScrollPane);

        DefaultTableModel model = new DefaultTableModel(
                DB.read(tableName),
                DB.getColumnsOfTable(tableName));

        table = new JTable(model);
        table.setShowHorizontalLines(false);
        table.setFillsViewportHeight(true);
        table.getModel().addTableModelListener(this::tableChanged);

        tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(10, 10, 437, 300);
        afficherContent.add(tableScrollPane);

        frame.repaint();
        frame.revalidate();
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        System.out.println("hello");
        int rowIndex = e.getFirstRow();
        String[] newRow = getRow(rowIndex);
        Matieres.get(rowIndex).Modifier(newRow);
    }

    public String[] getRow(int rowIndex) {
        String[] row = new String[table.getColumnCount()];
        for (int k = 0; k < table.getColumnCount(); k++) {
            row[k] = (String) table.getModel().getValueAt(rowIndex, k);
        }
        return row;
    }

    public void afficherPanel() {
        afficher.setBackground(new Color(60, 179, 113));
        ajouter.setBackground(new Color(211, 211, 211));
        frame.getContentPane().remove(ajouterContent);
        updateTable();
        frame.getContentPane().add(afficherContent);
        frame.repaint();
        frame.revalidate();
    }

    public void ajouterPanel() {
        ajouter.setBackground(new Color(60, 179, 113));
        afficher.setBackground(new Color(211, 211, 211));
        frame.getContentPane().remove(afficherContent);
        frame.getContentPane().add(ajouterContent);
        frame.repaint();
        frame.revalidate();
    }

    public void supprimerMatiere() {
        if (table.getSelectedRowCount() == 0) {
            showError("Veuillez choisir une matiere d'abord.");
            return;
        }

        int res = JOptionPane.showConfirmDialog(null, new JLabel("Voulez-vous supprimer cet filière?"),
                "Supprimer une matiere",
                JOptionPane.CANCEL_OPTION);
        if (res == 0) {
            int selectedRowIndex = table.getSelectedRow();
            Matieres.get(selectedRowIndex).Supprimer();
            getListMatieres();
            updateTable();
        } else {
            updateTable();
            return;
        }
    }

    public void ajouterMatiere(ActionEvent e) {

        // Gérer les erreures
        if (nomField.getText().trim().isEmpty()) {
            showError("Nom vide ! ");
            return;
        } else if (idField.getText().trim().isEmpty()) {
            showError("Prenom vide ! ");
            return;

        }
        // Si il n'y a pas une des semestres/Niveaux disponible pour cette filiere
        else if (moduleLabel.getSelectedItem() == null) {
            showError("Niveau ou Semestre invalide ! ");
            return;

        }

        Matiere matiere = new Matiere(
                idField.getText(),
                nomField.getText(),
                descriptionPane.getText(),
                volumeHoraireField.getText(),
                coefficientField.getText(),
                new Module(moduleLabel.getSelectedItem().toString(), null, null, null, null));

        // Ajouter le module crée à la BDD
        matiere.Ajouter();
        Matieres.add(matiere);
        getListMatieres();
        updateTable();
        afficherPanel();
        nomField.setText("");
        idField.setText("");
        moduleLabel.setSelectedItem(0);
        descriptionLabel.setText("");
        volumeHoraireLabel.setText("");
        coefficientLabel.setText("");
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void getListMatieres() {
        Matieres = new ArrayList<Matiere>();
        String[][] listDesMatieres = DB.read(tableName);
        for (int i = 0; i < listDesMatieres.length; i++) {
            String[] matiere = listDesMatieres[i];
            Matieres.add(new Matiere(
                    matiere[0],
                    matiere[1],
                    matiere[2],
                    matiere[3],
                    matiere[4],
                    new Module(matiere[5], null, null, null, null)));
        }
    }

    public static void main(String[] args) {
        new MatieresWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Add your implementation here
    }

}
