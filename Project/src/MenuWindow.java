import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

public class MenuWindow extends JFrame {
	public MenuWindow() {
		super("My window");
		 this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	     this.setSize(700,500);
	     this.setLocationRelativeTo(null);
	     
	     JButton btnNewButton_1 = new JButton("Etudiants");
	     btnNewButton_1.setBounds(104, 60, 200, 115);
	     btnNewButton_1.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent e) {
				new EtudiantGUI();
	     	}
	     });
	     getContentPane().setLayout(null);
	     
	     btnNewButton_1.setPreferredSize(new Dimension(200,50));
	     getContentPane().add(btnNewButton_1);
	     
	     JButton btnNewButton_2 = new JButton("Filière");
	     btnNewButton_2.setBounds(372, 61, 200, 112);
		 btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FiliereGUI();
			}
		});
	     
	     btnNewButton_2.setPreferredSize(new Dimension(200,50));
	     getContentPane().add(btnNewButton_2);
	     
	     JButton btnNewButton = new JButton("Modules");
	     btnNewButton.setBounds(104, 239, 200, 110);
	     
	     btnNewButton.setPreferredSize(new Dimension(200,50));
	     getContentPane().add(btnNewButton);

		 btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ModulesWindow();
			}
		});
	     
	     JButton btnNewButton_3 = new JButton("Matières");
	     btnNewButton_3.setBounds(372, 234, 200, 115);
	     
	     btnNewButton_3.setPreferredSize(new Dimension(200,50));
	     getContentPane().add(btnNewButton_3);
	}
	
	public static void main(String args[]) throws Exception {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		MenuWindow loginpage=new MenuWindow();
		loginpage.setVisible(true);
	}
}

