package org.galaxyworld.beangenerator.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class ControlPane extends JPanel {
	
	private final JTabbedPane tabPane;

	public ControlPane(JTabbedPane pane) {
		this.tabPane = pane;
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JButton genButton = new JButton("Generate");
		genButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GenerateAction act = (GenerateAction)tabPane.getSelectedComponent();
				act.generate();
			}
		});
		add(genButton);
		
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GenerateAction act = (GenerateAction)tabPane.getSelectedComponent();
				act.reset();
			}
			
		});
		add(resetButton);
	}
	
}
