package org.galaxyworld.beangenerator.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	private static final Logger logger = LoggerFactory.getLogger(MainFrame.class);

	public MainFrame() {
		setBounds(25, 25, 800, 600);
		setMinimumSize(new Dimension(800, 600));
		setTitle("BeanGenerator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container content = getContentPane();
		
		content.add(new ButtonPane(this), BorderLayout.NORTH);
		
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.add("D2J", new D2JGeneratePane());
		content.add(tabPane, BorderLayout.CENTER);
		
		content.add(new ControlPane(tabPane), BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
	        @Override
	        public void run() {
	            try {
	            	// UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	            	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	            } catch (Exception ex) {
	                logger.warn("Could not load system look and feel.");
	            }
	            MainFrame frame = new MainFrame();
	            SwingUtilities.updateComponentTreeUI(frame);
	            frame.setIconImage(new ImageIcon("res/bean.png").getImage());
	            initGobalFont(new Font("Dialog",Font.PLAIN,12));
	    		frame.setVisible(true);
	        }
	        
	    });
	}
	
    private static void initGobalFont(Font font) {
        FontUIResource fontResource = new FontUIResource(font);
        for(Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if(value instanceof FontUIResource) {
                UIManager.put(key, fontResource);
            }
        }
    }
	
}
