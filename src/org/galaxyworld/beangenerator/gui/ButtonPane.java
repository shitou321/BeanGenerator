package org.galaxyworld.beangenerator.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.galaxyworld.beangenerator.util.ResourceUtils;

@SuppressWarnings("serial")
public class ButtonPane extends JPanel {

	private JFrame mainWindow;
	
	public ButtonPane(JFrame frame) {
		mainWindow = frame;
		
		setLayout(new FlowLayout(FlowLayout.LEADING));
		
		ToolBarButton configButton = new ToolBarButton(ResourceUtils.tr("mainwindow.buttonpane.configbutton"), new ImageIcon("res/config.png"));
		add(configButton);
		
		ToolBarButton aboutButton = new ToolBarButton(ResourceUtils.tr("mainwindow.buttonpane.aboutbutton"), new ImageIcon("res/about.png"));
		aboutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(mainWindow, ResourceUtils.tr("mainwindow.buttonpane.about.message"),
						ResourceUtils.tr("mainwindow.buttonpane.about.title"), JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		add(aboutButton);
		
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
