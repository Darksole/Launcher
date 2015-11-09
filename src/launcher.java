import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class launcher {
	static String string1;
	static JFileChooser chooser;
	static String proppath;
	static File progfile;
	static String progpath;
	File propfile;
	public static void main(String[] args){
		makewindow();
	}

public static void makewindow(){
	
	progpath = open();
	
	JFrame frame = new JFrame("Launcher");
	JPanel panel = new JPanel();
	frame.setResizable(false);
	frame.add(panel);
	
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	final JLabel label = new JLabel();
	
    
    frame.setSize(400, 100);

    
    JButton button1 = new JButton("File...");
    final JButton button2 = new JButton("Launch");
    final JLabel label2 = new JLabel();
	
	label2.setText("Current File:");
	final JLabel label3 = new JLabel();
	
	label3.setText("Number of Copys to Launch:");
	JButton button3 = new JButton("Re-Enable Launch");
    panel.add(label2);
    panel.add(label);
    panel.add(button1);
    final JTextField field = new JTextField(null, 3);
    panel.add(button2);
    panel.add(label3);
    panel.add(field);
    panel.add(button3);
    
    frame.setVisible(true);
    
    if(progpath == "" || progpath == null){
    	label.setText("No File Selected");
    	button2.setEnabled(false);
    }
    else{
    	progfile = new File(progpath);
    	String progname = progfile.getName();
    	label.setText(progname);
    }
    button1.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent ev){
    		JFrame chooserframe = new JFrame();
    		chooser = new JFileChooser();
    		int returnVal = chooser.showOpenDialog(chooserframe);
    		if(returnVal == JFileChooser.APPROVE_OPTION) {
    			 string1 = chooser.getSelectedFile().getName();
    			 label.setText(string1);
    			 button2.setEnabled(true);
    			
    			 
    			 
    			 JOptionPane.showMessageDialog(chooserframe,
    					    "File Selected! \n File: " + string1);
    			    }
    	}
});	
 
    button3.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent ev){
    		button2.setEnabled(true);
    		
    	}
});	
    
    button2.addActionListener(new ActionListener(){
    	public void actionPerformed(ActionEvent ev){
    		File file = chooser.getSelectedFile();
    		String fieldtext = field.getText();
    		if(file.exists()){
    		int dialogResult = JOptionPane.showConfirmDialog (null, "Do you want to Launch " + fieldtext + " Copy(s) of " + string1);
        	if(dialogResult == JOptionPane.YES_OPTION){	
        		
        		button2.setEnabled(false);
    		int x = Integer.parseInt(fieldtext);

    		save(file.getAbsolutePath());
    		try {
    			for(int i = 0; i < x; i++){
				Process p = Runtime.getRuntime().exec("cmd /c start "+file.getAbsolutePath());
    			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
        	}	
    	}
    }
});	
    
}
private static void save(String thepath){
    Properties prop = new Properties();
    prop.setProperty("progpath", thepath);
    try {
		prop.store(new FileOutputStream("file.properties"), null);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
private static String open(){
    Properties prop = new Properties();
    try {
		prop.load(new FileInputStream("file.properties"));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    return prop.getProperty("progpath");
}
}