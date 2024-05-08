import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.border.MatteBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.util.ArrayList;


public class MatieresWindow implements ActionListener, TableModelListener {
    private String tableName = "matiere";

    JFrame frame;
    JPanel choix;
    JLabel titre;
    JButton afficher;
    JButton ajouter;

    // Pour connecter à notre base de données
    private JBDConnector DB = new JBDConnector();

    JPanel contenu;
    private final JPanel afficherContent;
    private final JPanel ajouterContent;

    private JTable table;
    private JScrollPane tableScrollPane;
    private final JLabel idLabel;
    private final JTextField idField;
    private final JLabel nomLabel;
    private final JTextField nomField;
    private final JLabel moduleLabel;
    private final JTextField moduleField;
    private final JLabel volumeHoraireLabel;
    private final JTextField volumeHoraireField;
    private final JLabel coefficientLabel;
    private final JTextField coefficientField;
    private final JLabel descriptionLabel;
    private final JTextPane descriptionPane;
    private final JButton supprimer_1;


    // Liste des matières
    private ArrayList<Matiere> Matieres;

    public MatieresWindow() {
        frame = new JFrame();
        choix = new JPanel();
        titre = new JLabel("Matières");
        afficher = new JButton("Liste");
        ajouter = new JButton("+");

        contenu = new JPanel();
        afficherContent = new JPanel();
        ajouterContent = new JPanel();

        table = new JTable();
        
        
        
        idLabel = new JLabel("ID");
        idField = new JTextField();
        nomLabel = new JLabel("Nom");
        nomField = new JTextField();
        moduleLabel = new JLabel("module");
        moduleField = new JTextField();
        volumeHoraireLabel = new JLabel("Volume Horaire");
        volumeHoraireField = new JTextField();
        coefficientLabel = new JLabel("Coefficient");
        coefficientField = new JTextField();
        descriptionLabel = new JLabel("Description");
        descriptionPane = new JTextPane();

        supprimer_1 = new JButton("Supprimer");
        supprimer_1.setVerticalAlignment(SwingConstants.TOP);
        frame.setTitle("Gestion des Matières");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

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

        contenu.setBounds(129, 0, 457, 363);
        contenu.setBackground(new Color(0, 206, 209));
        afficherContent.setBackground(new Color(0, 206, 209));
        afficherContent.setBounds(129, 0, 457, 363);
        afficherContent.setLayout(null);
        
       

        // Créer la table avec les données des matières
        afficherTable();

        table.setShowHorizontalLines(false);
        table.setFillsViewportHeight(true);

        tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(10, 10, 437, 300);
        afficherContent.add(tableScrollPane);
        
        supprimer_1.setForeground(new Color(0, 0, 0));
        supprimer_1.setFont(new Font("Gabriola", Font.PLAIN, 20));
        supprimer_1.setBackground(new Color(240, 255, 240));

        afficherContent.add(supprimer_1, BorderLayout.SOUTH);

        frame.getContentPane().add(afficherContent);

        ajouterContent.setBackground(new Color(0, 206, 209));
        ajouterContent.setBounds(128, 0, 458, 500);
        ajouterContent.setLayout(null);
        
        idLabel.setBounds(50, 50, 120, 25);
        ajouterContent.add(idLabel);
        idField.setBounds(180, 50, 200, 25);
        ajouterContent.add(idField);

        nomLabel.setBounds(50, 90, 120, 25);
        ajouterContent.add(nomLabel);
        nomField.setBounds(180, 90, 200, 25); // Adjusted Y-coordinate
        ajouterContent.add(nomField);

        // Positionnement du label "module"
        moduleLabel.setBounds(50, 130, 120, 25);
        ajouterContent.add(moduleLabel);
        moduleField.setBounds(180, 130, 200, 25); // Adjusted Y-coordinate
        ajouterContent.add(moduleField);

        volumeHoraireLabel.setBounds(50, 170, 120, 25);
        ajouterContent.add(volumeHoraireLabel);
        volumeHoraireField.setBounds(180, 170, 200, 25);
        ajouterContent.add(volumeHoraireField);

        coefficientLabel.setBounds(50, 210, 120, 25);
        ajouterContent.add(coefficientLabel);
        coefficientField.setBounds(180, 210, 200, 25); // Adjusted Y-coordinate
        ajouterContent.add(coefficientField);

        descriptionLabel.setBounds(50, 250, 120, 25);
        ajouterContent.add(descriptionLabel);
        descriptionPane.setBounds(180, 250, 200, 100); // Adjusted Y-coordinate
        ajouterContent.add(descriptionPane);

        JButton ajouterBtn = new JButton("Ajouter");
        ajouterBtn.setBounds(180, 370, 100, 30); // Adjusted Y-coordinate
        ajouterContent.add(ajouterBtn);



        afficher.addActionListener(this);
        afficher.addActionListener(e -> afficherPanel());
        ajouter.addActionListener(this);
        ajouter.addActionListener(e -> ajouterPanel());

        ajouterBtn.addActionListener(this::ajouterMatiere);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MatieresWindow();
    }


    @Override
    public void actionPerformed(ActionEvent e) {}

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

        tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(10, 10, 437, 300);
        afficherContent.add(tableScrollPane);

        frame.repaint();
        frame.revalidate();
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        // Code pour gérer le changement dans la table
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
    
    
    


    public void ajouterMatiere(ActionEvent e) {
        // Code pour ajouter une matière
    }
}
