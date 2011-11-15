/*
 * BeanGenerator
 * 
 * Copyright (C) 2011 galaxyworld.org
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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

/**
 * Main frame for BeanGenerate.
 * 
 * @author devbean
 * @version 0.0.1
 */
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
	
	/**
	 * Initializes Swing font.
	 * 
	 * @param font new font should be used
	 */
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
