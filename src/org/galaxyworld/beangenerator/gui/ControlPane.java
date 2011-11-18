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

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.galaxyworld.beangenerator.util.ResourceUtils;

/**
 * Control button pane at the bottom of main frame.
 * 
 * @author devbean
 * @version 0.0.1
 */
@SuppressWarnings("serial")
public class ControlPane extends JPanel {
	
	private final JTabbedPane tabPane;

	public ControlPane(JTabbedPane pane) {
		this.tabPane = pane;
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		// generate
		JButton genButton = new JButton(ResourceUtils.tr("mainwindow.controlpane.generate"));
		genButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GenerateAction act = (GenerateAction)tabPane.getSelectedComponent();
				act.generate();
			}
		});
		add(genButton);
		
		// reset
		JButton resetButton = new JButton(ResourceUtils.tr("mainwindow.controlpane.reset"));
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
