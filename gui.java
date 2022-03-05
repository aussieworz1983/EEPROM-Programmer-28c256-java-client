/**
*Text genereted by Simple GUI Extension for BlueJ
*/
import com.fazecast.jSerialComm.*;
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
import java.awt.event.*;
import javax.swing.border.Border;
import javax.swing.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.io.InputStream;
import java.io.File;
import java.io.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.*;
import java.util.ArrayList;
import java.util.List;

public class gui extends JFrame {
    public SerialPort deviceToUse;
    private JMenuBar menuBar;
    private JButton button1;
    private JButton button2;
    private JComboBox combobox1;
    
    private JLabel label1;
    private JLabel label2;
    private JTextArea textarea1;
    private JTextField textfield1;
    int lineNum = 16;
    int baudRate = 9600;
    byte[] buffer = new byte[32768];
    //Constructor 
    public gui(){
        SerialPort[] ports = SerialPort.getCommPorts();
        this.setTitle("gui");
        this.setSize(800,600);
        //menu generate method
        generateMenu();
        this.setJMenuBar(menuBar);

        //pane with null layout
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(800,600));
        contentPane.setBackground(new Color(192,192,192));


        button1 = new JButton();
        button1.setBounds(9,497,130,35);
        button1.setBackground(new Color(214,217,223));
        button1.setForeground(new Color(0,0,0));
        button1.setEnabled(true);
        button1.setFont(new Font("sansserif",0,12));
        button1.setText("Write EEprom");
        button1.setVisible(true);
        //Set methods for mouse events
        //Call defined methods
        button1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                WriteEEprom(evt);
            }
        });


        button2 = new JButton();
        button2.setBounds(143,498,130,35);
        button2.setBackground(new Color(214,217,223));
        button2.setForeground(new Color(0,0,0));
        button2.setEnabled(true);
        button2.setFont(new Font("sansserif",0,12));
        button2.setText("Read EEprom");
        button2.setVisible(true);
        //Set methods for mouse events
        //Call defined methods
        button2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                ReadEEprom(evt);
            }
        });
             

        combobox1 = new JComboBox(ports);
        combobox1.setBounds(5,108,90,35);
        combobox1.setBackground(new Color(214,217,223));
        combobox1.setForeground(new Color(0,0,0));
        combobox1.setEnabled(true);
        combobox1.setFont(new Font("sansserif",0,12));
        combobox1.setVisible(true);
        
        label1 = new JLabel();
        label1.setBounds(6,76,90,35);
        label1.setBackground(new Color(214,217,223));
        label1.setForeground(new Color(0,0,0));
        label1.setEnabled(true);
        label1.setFont(new Font("sansserif",0,12));
        label1.setText("Choose Port");
        label1.setVisible(true);

        label2 = new JLabel();
        label2.setBounds(103,75,90,35);
        label2.setBackground(new Color(214,217,223));
        label2.setForeground(new Color(0,0,0));
        label2.setEnabled(true);
        label2.setFont(new Font("sansserif",0,12));
        label2.setText("Choose File");
        label2.setVisible(true);

        textarea1 = new JTextArea(16,32768);
        textarea1.setBounds(9,161,780,300);
        textarea1.setBackground(new Color(255,255,255));
        textarea1.setForeground(new Color(255,100,100));
        textarea1.setEnabled(true);
        textarea1.setEditable(false);
        textarea1.setFont(new Font("Verdana",0,16));
        textarea1.setText("This is where read data and written data will be displayed");
        textarea1.setBorder(BorderFactory.createBevelBorder(1));
        textarea1.setVisible(true);

        textfield1 = new JTextField();
        textfield1.setBounds(271,19,230,35);
        textfield1.setBackground(new Color(255,255,255));
        textfield1.setForeground(new Color(0,0,0));
        textfield1.setEnabled(true);
        textfield1.setFont(new Font("sansserif",0,12));
        textfield1.setText("Enter Com Port Name");
        
        textfield1.setVisible(true);
        
        JScrollPane scroll = new JScrollPane(textarea1);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        scroll.setBounds(9, 161, 780, 300);
                
        //adding components to contentPane panel
        contentPane.add(button1);
        contentPane.add(button2);
        contentPane.add(combobox1);
        
        contentPane.add(label1);
        contentPane.add(label2);
        contentPane.add(scroll);
        contentPane.add(textfield1);

        //adding panel to JFrame and seting of window position and close operation
        this.add(contentPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
        combobox1.addItemListener(new ItemListener() {
            
            
            public void itemStateChanged(ItemEvent event) {
                     
                        Object item = event.getItem();
                     if(event.SELECTED == 1){
                        
                      String name = item.toString();
                     
                      deviceToUse = SerialPort.getCommPort(name);
                      String portName = deviceToUse.getSystemPortName();
                      //textfield1.setText("device chosen "+portName);
                        
                        }
                     
                       }
               });
    }

    //Method mouseClicked for button1
    private void WriteEEprom (MouseEvent evt) {
            //TODO
            ConnSettings conS = ConnSettings.getInstance();
            conS.setDeviceName(textfield1.getText());
            conS.setBaudRate(baudRate);                                   
            (new WriteWorker(textarea1)).execute();
        
    }

    //Method mouseClicked for button2
    private void ReadEEprom (MouseEvent evt) {
            //TODO
            
            ConnSettings conS = ConnSettings.getInstance();
            conS.setDeviceName(textfield1.getText());
            conS.setBaudRate(baudRate);                                   
            (new ReadWorker(textarea1)).execute();
            System.out.println("raedding from "+conS.getDeviceName());
    }

    //Method mouseClicked for combobox1
    private void SelectPort (MouseEvent evt) {
            //TODO
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
        open.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
          JFileChooser fc = new JFileChooser();
          File file = new File("/Users/aussie/Documents/GitHub/serial_comms/");
          fc.setCurrentDirectory(file);
          FileFilter filter = new FileFilter()
          {
            @Override
            public String getDescription (){
            
                
                return "";
            }
            @Override
            public boolean accept(File file){
                
                if(file.getName().endsWith(".bin")||file.getName().endsWith(".out")){
                return true;
                }
            
              return false;
            }
          };
         
          
          fc.showOpenDialog(null);
          fc.setFileFilter(filter);
          File selectedFile  = fc.getSelectedFile();
          System.out.println(selectedFile);
          try{
              ConnSettings conS = ConnSettings.getInstance();
              FileReader reader = new FileReader(selectedFile);
              FileInputStream fs = new FileInputStream(selectedFile);
              BufferedReader bf = new BufferedReader(reader);
              
              int n = fs.read(buffer);
              
              List<String> strD = new ArrayList<String>();
              for(int i = 0;i<buffer.length;i++){
                byte data = buffer[i];
                String hex = Integer.toHexString(data & 0xFF);
                if(i == lineNum){
                strD.add("\n");
                lineNum += 16;
                }
                strD.add(hex);
                
                }
              textarea1.setText(strD.toString());
              conS.setTheBytes(buffer);
            }catch(IOException err){
            System.out.println(err);
            }
          //put the file contents in text area
          }
          
          });
                                   
    }
   
    public static void main(String[] args){
        System.setProperty("swing.defaultlaf", "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new gui();
            }
        });
    }
       
}