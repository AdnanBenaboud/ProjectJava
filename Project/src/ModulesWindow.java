import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTable;

public class ModulesWindow extends JFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;
	private JTable table_1;
	private JTabbedPane tabbedPane;
	public ModulesWindow() {
		 super("My window");
		 this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	     this.setSize(700,500);
	     this.setLocationRelativeTo(null);
	     getContentPane().setLayout(null);
	     
	     JButton btnNewButton = new JButton("Ajouter Module");
	     btnNewButton.setBounds(59, 53, 140, 52);
	     getContentPane().add(btnNewButton);
	     
	     btnNewButton.addActionListener(new ActionListener() {
		     	public void actionPerformed(ActionEvent e) {
		     		tabbedPane.setSelectedIndex(0);
                     tabbedPane.setVisible(true);

		     	}
		     });
	     
	     JButton btnNewButton_1 = new JButton("Supprimer Module");
	     btnNewButton_1.setBounds(272, 53, 140, 52);
	     getContentPane().add(btnNewButton_1);

         btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(1);
                tabbedPane.setVisible(true);

            }
        });
	     
	     JButton btnNewButton_1_1 = new JButton("Modifier Module");
	     btnNewButton_1_1.setBounds(499, 53, 140, 52);
	     getContentPane().add(btnNewButton_1_1);
         btnNewButton_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(2);
                tabbedPane.setVisible(true);

            }
        });
	     
	     tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	     tabbedPane.setBounds(6, 153, 660, 284);
	     getContentPane().add(tabbedPane);
         tabbedPane.setVisible(false);
	     
	     JPanel panel = new JPanel();
	     tabbedPane.addTab("New tab", null, panel, null);
	     panel.setLayout(null);
	     
	     JLabel lblNewLabel = new JLabel("Id");
	     lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
	     lblNewLabel.setBounds(45, 35, 93, 29);
	     panel.add(lblNewLabel);
	     
	     textField = new JTextField();
	     textField.setBounds(97, 36, 102, 28);
	     panel.add(textField);
	     textField.setColumns(10);
	     
	     JLabel lblNom = new JLabel("Nom");
	     lblNom.setFont(new Font("SansSerif", Font.BOLD, 14));
	     lblNom.setBounds(255, 35, 93, 29);
	     panel.add(lblNom);
	     
	     textField_1 = new JTextField();
	     textField_1.setColumns(10);
	     textField_1.setBounds(307, 36, 102, 28);
	     panel.add(textField_1);
	     
	     JLabel lblNewLabel_1_1 = new JLabel("Niveau");
	     lblNewLabel_1_1.setFont(new Font("SansSerif", Font.BOLD, 14));
	     lblNewLabel_1_1.setBounds(470, 35, 93, 29);
	     panel.add(lblNewLabel_1_1);
	     
	     JComboBox comboBox = new JComboBox();
	     comboBox.setBounds(564, 37, 90, 26);
	     panel.add(comboBox);
	     
	     JLabel lblNewLabel_1 = new JLabel("Discreption");
	     lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 14));
	     lblNewLabel_1.setBounds(45, 155, 93, 29);
	     panel.add(lblNewLabel_1);
	     
	     JTextArea textArea = new JTextArea();
	     textArea.setBounds(137, 118, 373, 106);
	     panel.add(textArea);
	     
	     JPanel panel_1 = new JPanel();
	     tabbedPane.addTab("New tab", null, panel_1, null);
	     panel_1.setLayout(null);
	     
	     table = new JTable();
	     table.setBounds(162, 22, 492, 242);
	     panel_1.add(table);
	     
	     JButton btnNewButton_2 = new JButton("Supprimer");
	     btnNewButton_2.setBounds(24, 36, 110, 47);
	     panel_1.add(btnNewButton_2);
	     
	     JPanel panel_2 = new JPanel();
	     tabbedPane.addTab("New tab", null, panel_2, null);
	     panel_2.setLayout(null);
	     
	     table_1 = new JTable();
	     table_1.setBounds(85, 29, 491, 219);
	     panel_2.add(table_1);
	}
	
	
	public static void main(String args[]) throws Exception {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		ModulesWindow loginpage=new ModulesWindow();
		loginpage.setVisible(true);
	}
}
