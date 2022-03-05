/**
*Text genereted by Simple GUI Extension for BlueJ
*/
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.border.Border;
import javax.swing.*;


public class Worzels EEprom Programmer extends JFrame {

	private JMenuBar menuBar;
	private JButton button1;
	private JButton button2;
	private JComboBox combobox1;
	private JComboBox combobox2;
	private JLabel label1;
	private JLabel label2;
	private JTextArea textarea1;
	private JTextField textfield1;

	//Constructor 
	public Worzels EEprom Programmer(){

		this.setTitle("Worzels EEprom Programmer");
		this.setSize(800,600);
		//menu generate method
		generateMenu();
		this.setJMenuBar(menuBar);

		//pane with null layout
		JPanel contentPane = new JPanel(null);
		contentPane.setPreferredSize(new Dimension(800,600));
		contentPane.setBackground(new Color(192,192,192));


		button1 = new JButton();
		button1.setBounds(31,442,90,35);
		button1.setBackground(new Color(214,217,223));
		button1.setForeground(new Color(0,0,0));
		button1.setEnabled(true);
		button1.setFont(new Font("sansserif",0,12));
		button1.setText("Button1");
		button1.setVisible(true);

		button2 = new JButton();
		button2.setBounds(151,427,90,35);
		button2.setBackground(new Color(214,217,223));
		button2.setForeground(new Color(0,0,0));
		button2.setEnabled(true);
		button2.setFont(new Font("sansserif",0,12));
		button2.setText("Button2");
		button2.setVisible(true);

		combobox1 = new JComboBox();
		combobox1.setBounds(5,110,90,35);
		combobox1.setBackground(new Color(214,217,223));
		combobox1.setForeground(new Color(0,0,0));
		combobox1.setEnabled(true);
		combobox1.setFont(new Font("sansserif",0,12));
		combobox1.setVisible(true);

		combobox2 = new JComboBox();
		combobox2.setBounds(102,109,90,35);
		combobox2.setBackground(new Color(214,217,223));
		combobox2.setForeground(new Color(0,0,0));
		combobox2.setEnabled(true);
		combobox2.setFont(new Font("sansserif",0,12));
		combobox2.setVisible(true);

		label1 = new JLabel();
		label1.setBounds(6,76,90,35);
		label1.setBackground(new Color(214,217,223));
		label1.setForeground(new Color(0,0,0));
		label1.setEnabled(true);
		label1.setFont(new Font("sansserif",0,12));
		label1.setText("label");
		label1.setVisible(true);

		label2 = new JLabel();
		label2.setBounds(103,75,90,35);
		label2.setBackground(new Color(214,217,223));
		label2.setForeground(new Color(0,0,0));
		label2.setEnabled(true);
		label2.setFont(new Font("sansserif",0,12));
		label2.setText("label");
		label2.setVisible(true);

		textarea1 = new JTextArea();
		textarea1.setBounds(159,284,150,100);
		textarea1.setBackground(new Color(255,255,255));
		textarea1.setForeground(new Color(0,0,0));
		textarea1.setEnabled(true);
		textarea1.setFont(new Font("sansserif",0,12));
		textarea1.setText("JTextArea");
		textarea1.setBorder(BorderFactory.createBevelBorder(1));
		textarea1.setVisible(true);

		textfield1 = new JTextField();
		textfield1.setBounds(313,18,90,35);
		textfield1.setBackground(new Color(255,255,255));
		textfield1.setForeground(new Color(0,0,0));
		textfield1.setEnabled(true);
		textfield1.setFont(new Font("sansserif",0,12));
		textfield1.setText("JTextField");
		textfield1.setVisible(true);

		//adding components to contentPane panel
		contentPane.add(button1);
		contentPane.add(button2);
		contentPane.add(combobox1);
		contentPane.add(combobox2);
		contentPane.add(label1);
		contentPane.add(label2);
		contentPane.add(textarea1);
		contentPane.add(textfield1);

		//adding panel to JFrame and seting of window position and close operation
		this.add(contentPane);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}

	//method for generate menu
	public void generateMenu(){
		menuBar = new JMenuBar();

		JMenu file = new JMenu("File");
		JMenu tools = new JMenu("Tools");
		JMenu help = new JMenu("Help");

		JMenuItem open = new JMenuItem("Open   ");
		JMenuItem save = new JMenuItem("Save   ");
		JMenuItem exit = new JMenuItem("Exit   ");
		JMenuItem preferences = new JMenuItem("Preferences   ");
		JMenuItem about = new JMenuItem("About   ");


		file.add(open);
		file.add(save);
		file.addSeparator();
		file.add(exit);
		tools.add(preferences);
		help.add(about);

		menuBar.add(file);
		menuBar.add(tools);
		menuBar.add(help);
	}



	 public static void main(String[] args){
		System.setProperty("swing.defaultlaf", "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Worzels EEprom Programmer();
			}
		});
	}

}