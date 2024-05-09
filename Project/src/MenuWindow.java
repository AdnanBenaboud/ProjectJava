import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class MenuWindow extends JFrame {
	public MenuWindow() {
		super("My window");
		 this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	     this.setSize(700,500);
	     this.setLocationRelativeTo(null);
	     
	     JButton btnNewButton_1 = new JButton("Etudiants");
	     btnNewButton_1.setBounds(129, 160, 171, 95);
	     btnNewButton_1.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent e) {
				new EtudiantGUI();
	     	}
	     });
	     getContentPane().setLayout(null);
	     getContentPane().setBackground(new Color(108, 147, 142));
	     
	     btnNewButton_1.setPreferredSize(new Dimension(200,50));
	     getContentPane().add(btnNewButton_1);
	     
	     JButton btnNewButton_2 = new JButton("Filière");
	     btnNewButton_2.setBounds(262, 303, 165, 95);
		 btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FiliereGUI();
			}
		});
	     
	     btnNewButton_2.setPreferredSize(new Dimension(200,50));
	     getContentPane().add(btnNewButton_2);
	     
	     JButton btnNewButton = new JButton("Modules");
	     btnNewButton.setBounds(42, 303, 171, 95);
	     
	     btnNewButton.setPreferredSize(new Dimension(200,50));
	     getContentPane().add(btnNewButton);

		 btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ModulesWindow();
			}
		});
	     
	     JButton btnNewButton_3 = new JButton("Matières");
	     btnNewButton_3.setBounds(484, 303, 165, 95);
		 btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MatieresWindow();
			}
		});
	     
	     btnNewButton_3.setPreferredSize(new Dimension(200,50));
	     getContentPane().add(btnNewButton_3);
	     
	     JButton btnNewButton_1_1 = new JButton("Notes");
	     btnNewButton_1_1.setPreferredSize(new Dimension(200, 50));
	     btnNewButton_1_1.setBounds(402, 160, 171, 95);
	     getContentPane().add(btnNewButton_1_1);
		 btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			   new NoteGUI();
			}
		});
	     
	     JLabel lblNewLabel = new JLabel("Système de gestion universitaire");
	     lblNewLabel.setFont(new Font("Nirmala UI", Font.BOLD, 23));
	     lblNewLabel.setBounds(159, 47, 370, 40);
	     getContentPane().add(lblNewLabel);
	}
	
	public static void main(String args[]) throws Exception {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		MenuWindow loginpage=new MenuWindow();
		loginpage.setVisible(true);
	}
}

