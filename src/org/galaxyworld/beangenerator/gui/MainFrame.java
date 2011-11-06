package org.galaxyworld.beangenerator.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

import org.galaxyworld.beangenerator.core.Config;
import org.galaxyworld.beangenerator.core.JavaBeanGenerator;
import org.galaxyworld.beangenerator.data.RootData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	private static final Logger logger = LoggerFactory.getLogger(MainFrame.class);

	public MainFrame() {
		setBounds(25, 25, 450, 300);
		setMinimumSize(new Dimension(450, 300));
		setTitle("Bean Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel pane = new JPanel();
		MigLayout layout = new MigLayout("fillx", "[right]rel[grow,fill]rel[min]", "[]rel[]");
		pane.setLayout(layout);
		
		pane.add(new JLabel("Output Directory"), "gapright 6");
		final JTextField outputField = new JTextField("D:\\");
		pane.add(outputField);
		JButton outputFolderButton = new JButton("Browser...");
		outputFolderButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int flag = -1;
				try{
					flag=fc.showOpenDialog(null);
				} catch(HeadlessException ex) {
					ex.printStackTrace();
				}
				if(flag == JFileChooser.APPROVE_OPTION) {
					outputField.setText(fc.getSelectedFile().getAbsolutePath());
				}
			}
		});
		pane.add(outputFolderButton, "wrap");
		
		pane.add(new JLabel("Package Name"), "gapright 6");
		final JTextField packageField = new JTextField("org.galaxyworld.test");
		pane.add(packageField, "span 2, wrap");
		
		pane.add(new JLabel("Class Name"), "gapright 6");
		final JTextField classField = new JTextField();
		pane.add(classField, "span 2, wrap");
		
		pane.add(new JLabel("Comment"), "gapright 6");
		final JTextField commentField = new JTextField();
		pane.add(commentField, "span 2, wrap");
		
		pane.add(new JLabel("Author"), "gapright 6");
		final JTextField authorField = new JTextField();
		pane.add(authorField, "span 2, wrap");
		
		pane.add(new JLabel("Version"), "gapright 6");
		final JTextField versionField = new JTextField();
		pane.add(versionField, "span 2, wrap");
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton genButton = new JButton("Generate");
		genButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Config cfg = Config.getInstance();
				String outputPath = outputField.getText();
				if(outputPath.lastIndexOf(File.separator) != outputPath.length()) {
					outputPath += File.separator;
				}
				cfg.setOutputPath(outputPath);
				RootData root = new RootData();
				root.setPackageName(packageField.getText());
				root.setClassName(classField.getText());
				root.setComment(commentField.getText());
				root.setAuthor(authorField.getText());
				root.setVersion(versionField.getText());
				JavaBeanGenerator gen = new JavaBeanGenerator(root);
				// gen.generate();
				gen.createPackageFolders();
			}
		});
		buttonPane.add(genButton);
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				packageField.setText("");
				classField.setText("");
				commentField.setText("");
				authorField.setText("");
				versionField.setText("");
			}
			
		});
		buttonPane.add(resetButton);
		pane.add(buttonPane, "span 3, align center");
		pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		getContentPane().add(pane, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
	        @Override
	        public void run() {
	            try {
	            	UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	            } catch (Exception ex) {
	                logger.warn("Could not load NimbusLookAndFeel.");
	            }
	            MainFrame frame = new MainFrame();
	    		frame.setVisible(true);
	        }
	        
	    });
	}
	
}
