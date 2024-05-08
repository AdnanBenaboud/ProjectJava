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
import java.awt.event.WindowEvent;
import java.sql.SQLIntegrityConstraintViolationException;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.*;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Window.Type;

public class addNote {
	JFrame g;
	private String id_filiere;
	private String id_semestre;
	private String id_matiere;
	private JBDConnector DB = new JBDConnector();
	private JTextField textField;
	private JComboBox choixEtudiants;
	private JComboBox choixMatiere;
	private JTextField note_finale;

	public addNote(String id_filiere, String id_semestre, String id_matiere) {

		this.id_filiere = id_filiere;
		this.id_semestre = id_semestre;
		this.id_matiere = id_matiere;
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); // Windows Look and Feel
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g = new JFrame();
		g.getContentPane().setBackground(new Color(186, 85, 211));
		g.getContentPane().setLayout(null);

		g.setTitle("Ajouter une Note");
		g.setBounds(350, 200, 450, 300);
		g.getContentPane().setLayout(null);

		JLabel etudiantLabel = new JLabel("ID Etudiant");
		etudiantLabel.setBounds(145, 49, 104, 14);
		g.getContentPane().add(etudiantLabel);

		choixEtudiants = new JComboBox();
		choixEtudiants.setBounds(145, 66, 159, 22);
		g.getContentPane().add(choixEtudiants);

		JLabel noteLabel = new JLabel("Note");
		noteLabel.setBounds(145, 147, 49, 14);
		g.getContentPane().add(noteLabel);

		choixMatiere = new JComboBox();
		choixMatiere.setBounds(145, 114, 159, 22);
		g.getContentPane().add(choixMatiere);

		note_finale = new JTextField();
		note_finale.setBounds(145, 165, 159, 20);
		g.getContentPane().add(note_finale);
		note_finale.setColumns(10);

		JButton addbtn = new JButton("Ajouter");
		addbtn.setBounds(179, 206, 89, 23);
		g.getContentPane().add(addbtn);

		addbtn.addActionListener(arg0 -> {
			try {
				addALF(arg0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		});

		JLabel matiereLabel = new JLabel("ID Matiere");
		matiereLabel.setBounds(145, 99, 104, 14);
		g.getContentPane().add(matiereLabel);

		String[] listeDesEtudiants = DB.getOneColumnWithConditions(
				"etudiant",
				"id_etudiant",
				new String[] { "id_filiere" },
				new String[] { id_filiere });

		// Mise a jour des choix des Etudiants
		choixEtudiants.setModel(new DefaultComboBoxModel(
				listeDesEtudiants));

		try {
			String[] listeDesMatieres = DB.OneDArrayWithScript(
					"id_matiere",
					String.format(
							"SELECT matiere.id_matiere FROM matiere, module, semestre WHERE module.id_module=matiere.id_module and module.`id_semestre` = '%s' and semestre.id_semestre=module.id_semestre and semestre.id_filiere='%s';",
							id_semestre, id_filiere));
			choixMatiere.setModel(new DefaultComboBoxModel(
					listeDesMatieres));
		} catch (Exception error) {
			// TODO: handle exception
			System.out.println("Aucune semestre est choisie");
			return;
		}

		g.setVisible(true);
		g.setResizable(false);
	}

	public void addALF(ActionEvent e) throws Exception {
		if (note_finale.getText().trim().isEmpty()) {

			showError("Note vide ! ");
			return;
		} else if (choixEtudiants.getSelectedItem() == null) {
			showError("Etudiant invalide ! ");
			return;

		} else if (choixMatiere.getSelectedItem() == null) {
			showError("Matiere invalide ! ");
			return;

		} else {
			try {
				Float.parseFloat(note_finale.getText());
			} catch (Exception error) {
				showError("Type de note invalide ! ");
				return;
			}
		}

		try {
			Note note = new Note(choixEtudiants.getSelectedItem().toString(), choixMatiere.getSelectedItem().toString(),
					Float.parseFloat(note_finale.getText()));
			note.Ajouter();
			g.dispatchEvent(new WindowEvent(g, WindowEvent.WINDOW_CLOSING));

		} catch (Exception error) {
			// TODO: handle exception
			showError("C'est possible que la note de cette Etudiant/Matiere existe! ");
			return;
		}

	}

	public void showError(String message) {
		JOptionPane.showMessageDialog(g, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void main(String[] args) throws Exception {
		new addNote("GI", "S1", "G111");
	}
}
