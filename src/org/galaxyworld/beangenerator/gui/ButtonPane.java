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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.galaxyworld.beangenerator.util.ResourceUtils;

/**
 * Tool bar panel at the top of main frame.
 * 
 * @author debean
 * @version 0.0.1
 */
@SuppressWarnings("serial")
public class ButtonPane extends JPanel {

	private JFrame mainWindow;
	
	public ButtonPane(JFrame frame) {
		mainWindow = frame;
		
		setLayout(new FlowLayout(FlowLayout.LEADING));
		
		// configuration
		ToolBarButton configButton = new ToolBarButton(ResourceUtils.tr("mainwindow.buttonpane.configbutton"), new ImageIcon("res/config.png"));
		add(configButton);
		
		// about
		ToolBarButton aboutButton = new ToolBarButton(ResourceUtils.tr("mainwindow.buttonpane.aboutbutton"), new ImageIcon("res/about.png"));
		aboutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(mainWindow, ResourceUtils.tr("mainwindow.buttonpane.about.message"),
						ResourceUtils.tr("mainwindow.buttonpane.about.title"), JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		add(aboutButton);
		
		// exit
		ToolBarButton exitButton = new ToolBarButton(ResourceUtils.tr("mainwindow.buttonpane.exitbutton"), new ImageIcon("res/exit.png"));
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		add(exitButton);
	}
	
}
