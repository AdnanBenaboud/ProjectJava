import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.mysql.cj.x.protobuf.MysqlxNotice.Frame;

import javax.swing.border.MatteBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLIntegrityConstraintViolationException;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Window.Type;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.print.*;
import java.io.File;
import java.awt.image.BufferedImage;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

import javax.swing.*;

import javax.swing.border.TitledBorder;

import javax.imageio.ImageIO;

import java.io.File;

public class EtudiantInfo {
	JFrame g;
	private String id_filiere;
	private String id_semestre;
	private String id_etudiant;
	private JBDConnector DB = new JBDConnector();
	private JScrollPane tableScrollPane;

	private JLabel nom;
	private JLabel prenom;
	private JLabel id;
	private JTable table;
	private JLabel noteMoyenneLabel;
	private JLabel noteMoyenne;

	private JButton imprimer;

	public EtudiantInfo(String id_filiere, String id_semestre, String id_etudiant) {

		this.id_filiere = id_filiere;
		this.id_semestre = id_semestre;
		this.id_etudiant = id_etudiant;

		System.out.println(id_etudiant);
		System.out.println(id_filiere);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); // Windows Look and Feel
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// notre frame
		g = new JFrame();
		g.getContentPane().setBackground(new Color(176, 196, 222));
		g.getContentPane().setLayout(null);

		g.setTitle("Informations de l'Étudiant");
		g.setBounds(350, 200, 700, 500);
		g.getContentPane().setLayout(null);

		// ou on va mettre le nom de l'etudiant
		nom = new JLabel("Nom: ");
		nom.setFont(new Font("Yu Gothic Light", Font.PLAIN, 11));
		nom.setBounds(10, 50, 158, 14);
		g.getContentPane().add(nom);

		// ou on va mettre le prenom de l'etudiant
		prenom = new JLabel("Prenom: ");
		prenom.setFont(new Font("Yu Gothic Light", Font.PLAIN, 11));
		prenom.setBounds(10, 75, 195, 14);
		g.getContentPane().add(prenom);

		id = new JLabel("ID: ");
		id.setFont(new Font("Yu Gothic Light", Font.PLAIN, 11));
		id.setBounds(10, 25, 158, 14);
		g.getContentPane().add(id);

		JLabel titre = new JLabel("Informations de l'Étudiant");
		titre.setFont(new Font("Yu Gothic Light", Font.PLAIN, 18));
		titre.setBounds(262, 0, 200, 34);
		g.getContentPane().add(titre);

		noteMoyenneLabel = new JLabel("Note moyenne: ");
		noteMoyenneLabel.setFont(new Font("Yu Gothic Light", Font.PLAIN, 14));
		noteMoyenneLabel.setBounds(583, 395, 97, 34);
		g.getContentPane().add(noteMoyenneLabel);

		// la table des notes
		table = new JTable();
		table.setForeground(new Color(0, 0, 0));
		table.setBackground(new Color(176, 196, 222));
		tableScrollPane = new JScrollPane(table);
		tableScrollPane.setLocation(10, 96);
		tableScrollPane.setSize(563, 367);
		g.getContentPane().add(tableScrollPane);
		addImprimerBtn();
		noteMoyenne = new JLabel("20");
		noteMoyenne.setBackground(new Color(102, 205, 170));
		noteMoyenne.setForeground(new Color(0, 0, 139));
		noteMoyenne.setFont(new Font("Yu Gothic Light", Font.PLAIN, 11));
		noteMoyenne.setBounds(612, 429, 57, 34);
		g.getContentPane().add(noteMoyenne);

		afficherInformations();
		g.setVisible(true);
		g.setResizable(false);

	}

	public void showError(String message) {
		JOptionPane.showMessageDialog(g, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void addImprimerBtn() {
		imprimer = new JButton("imprimer");
		imprimer.setBounds(580, 350, 100, 34);
		imprimer.setFont(new Font("Gabriola", Font.PLAIN, 20));
		imprimer.setVerticalAlignment(SwingConstants.TOP);
		imprimer.addActionListener(this::takeScreenshot);
		g.getContentPane().add(imprimer);
	}

	public void takeScreenshot(ActionEvent e) {

		g.getContentPane().remove(imprimer);
		BufferedImage img = getScreenShot(
				g.getContentPane());

		JOptionPane.showMessageDialog(
				null,
				new JLabel(
						new ImageIcon(
								img.getScaledInstance(
										img.getWidth(null) / 2,
										img.getHeight(null) / 2,
										Image.SCALE_SMOOTH))));
		try {
			// write the image as a PNG
			ImageIO.write(
					img,
					"png",
					new File(String.format("notes/%s-%s-%s-%s.png",
							DB.getOneColumnWithConditions("etudiant", "nom_etudiant", new String[] { "id_etudiant" },
									new String[] { id_etudiant })[0],
							DB.getOneColumnWithConditions("etudiant", "prenom_etudiant", new String[] { "id_etudiant" },
									new String[] { id_etudiant })[0],
							id_filiere, id_semestre)));
			addImprimerBtn();
		} catch (Exception error) {
			error.printStackTrace();
			addImprimerBtn();
		}
	}

	public static BufferedImage getScreenShot(
			Component component) {

		BufferedImage image = new BufferedImage(
				component.getWidth(),
				component.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		// call the Component's paint method, using
		// the Graphics object of the image.
		component.paint(image.getGraphics()); // alternately use .printAll(..)
		return image;
	}

	public void afficherInformations() {

		// le script qui va etre execute
		String script = String.format(
				"SELECT nom_matiere, coefficient_matiere, note_finale from matiere, note, module, semestre, filiere where note.id_matiere=matiere.id_matiere and module.id_module=matiere.id_module and module.id_semestre=semestre.id_semestre and filiere.id_filiere=semestre.id_filiere and id_etudiant=%s and filiere.id_filiere='%s'",
				id_etudiant, id_filiere);

		// les colonnes de resultat
		String[] columns = { "nom_matiere", "coefficient_matiere", "note_finale" };

		table.setFont(new Font("DialogInput", Font.PLAIN, 16));
		id.setText("ID: " + id_etudiant);
		nom.setText("Nom: " + DB.getOneColumnWithConditions("etudiant", "nom_etudiant", new String[] { "id_etudiant" },
				new String[] { id_etudiant })[0]);
		prenom.setText("Prenom: " + DB.getOneColumnWithConditions("etudiant", "prenom_etudiant",
				new String[] { "id_etudiant" }, new String[] { id_etudiant })[0]);
		String[][] results;

		try {
			results = DB.TwoDArrayWithScript(columns, script);

			// mise a jour de table
			table.setModel(new DefaultTableModel(
					results,
					new String[] { "Nom de Matiere", "Coefficient de Matiere", "Note" }));
			float noteM = 0;
			float sommeCoeff = 0;
			// calculer la moyenne
			for (int i = 0; i < results.length; i++) {
				float noteMatiere = Float.parseFloat(results[i][2]);
				float CeoffMatiere = Float.parseFloat(results[i][1]);
				noteM += CeoffMatiere * noteMatiere;
				sommeCoeff += CeoffMatiere;
			}
			// System.out.println(noteM);
			// System.out.println(sommeCoeff);

			noteM = noteM / sommeCoeff;
			// System.out.println(String.format("%.2f", noteM));

			noteMoyenne.setText(String.format("%.2f", noteM));
		} catch (Exception e) {
			// TODO: handle exception
			table.setModel(new DefaultTableModel(
					null,
					columns));
		}

	}

	public static void main(String[] args) throws Exception {
		new EtudiantInfo("GI", "S1", "1");
	}
}
