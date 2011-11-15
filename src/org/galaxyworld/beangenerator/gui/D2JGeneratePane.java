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

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import org.galaxyworld.beangenerator.core.AbstractGenerator;
import org.galaxyworld.beangenerator.core.Config;
import org.galaxyworld.beangenerator.core.GeneratorProcessListener;
import org.galaxyworld.beangenerator.core.JavaBeanGenerator;
import org.galaxyworld.beangenerator.data.CommonData;
import org.galaxyworld.beangenerator.event.GeneratorProcessEvent;

import net.miginfocom.swing.MigLayout;

/**
 * Database tables to JavaBean generator panel.
 * 
 * @author devbean
 * @version 0.0.1
 */
@SuppressWarnings("serial")
public class D2JGeneratePane extends JPanel implements GenerateAction {
	
	// output path
	private final JTextField outputField = new JTextField(Config.getInstance().getAppPath());
	
	// package
	private final JTextField packageField = new JTextField();
	
	// comment
	private final JTextField commentField = new JTextField();
	
	// author
	private final JTextField authorField = new JTextField();
	
	// version
	private final JTextField versionField = new JTextField();
	
	// application log output
	private final JTextArea outputArea = new JTextArea(20, 10);
	
	public D2JGeneratePane() {
		MigLayout layout = new MigLayout("fillx, wrap", "[align right]rel[grow, fill]rel[pref]", "[]rel[]rel[]rel[]rel[]rel[grow, fill]");
		setLayout(layout);
		
		add(new JLabel("Output Directory"), "gapright 6");
		outputField.setToolTipText("output path for generated bean source files");
		add(outputField);
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
		add(outputFolderButton);
		
		add(new JLabel("Package Name"), "gapright 6");
		packageField.setToolTipText("package for generated beans");
		add(packageField, "span 2");

		add(new JLabel("Default Comment"), "gapright 6");
		commentField.setToolTipText("default comment for uncommented beans");
		add(commentField, "span 2");
		
		add(new JLabel("Author"), "gapright 6");
		authorField.setToolTipText("author for beans");
		add(authorField, "span 2");
		
		add(new JLabel("Version"), "gapright 6");
		versionField.setToolTipText("version number for beans");
		add(versionField, "span 2");
		
		add(new JLabel("Output"), "gapright 6");
		final JScrollPane outputScrollPane = new JScrollPane(outputArea);
		outputArea.setEditable(false);
		add(outputScrollPane, "span 2");
		
		setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
	}
	
	@Override
	public void generate() {
		Config cfg = Config.getInstance();
		String outputPath = outputField.getText();
		if(outputPath.lastIndexOf(File.separator) != outputPath.length()) {
			outputPath += File.separator;
		}
		cfg.setOutputPath(outputPath);
		CommonData cd = new CommonData();
		cd.setDefaultComment(commentField.getText());
		cd.setAuthor(authorField.getText());
		cd.setVersion(versionField.getText());
		cd.setPackageName(packageField.getText());
		cfg.setCommonData(cd);
		DatabaseWorker dw = new DatabaseWorker();
		dw.execute();
	}

	@Override
	public void reset() {
		packageField.setText("");
		commentField.setText("");
		authorField.setText("");
		versionField.setText("");
		outputArea.setText("");
	}
	
	private class DatabaseWorker extends SwingWorker<Void, String> implements GeneratorProcessListener {

		@Override
		protected Void doInBackground() throws Exception {
			AbstractGenerator gen = new JavaBeanGenerator();
			gen.addGeneratorProcessListener(this);
			gen.generate();
			return null;
		}
		
		@Override
		protected void process(List<String> outputList) {
			for(String output: outputList) {
				if (isCancelled()) {
					break;
				}
				String oldText = outputArea.getText();
				outputArea.setText(oldText + output);
			}
		}

		@Override
		public void generatorProcess(GeneratorProcessEvent e) {
			publish(e.getMessage());
		}

	}

}
