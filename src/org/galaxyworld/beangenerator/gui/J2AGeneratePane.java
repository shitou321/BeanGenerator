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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.galaxyworld.beangenerator.core.AS3BeanGenerator;
import org.galaxyworld.beangenerator.core.AppContext;
import org.galaxyworld.beangenerator.core.GenerateTaskWorker;
import org.galaxyworld.beangenerator.util.ResourceUtils;

import net.miginfocom.swing.MigLayout;

/**
 * JavaBean to ActionScript3 code generator panel.
 * 
 * @author devbean
 * @version 0.0.1
 */
@SuppressWarnings("serial")
public class J2AGeneratePane extends JPanel implements GenerateAction {
	
	// input directory path
	private final JTextField inputField = new JTextField(AppContext.getInstance().getAppPath());
	
	// output directory path
	private final JTextField outputField = new JTextField(AppContext.getInstance().getAppPath());
	
	// whether add [Bindable] meta data
	private final JCheckBox bindableBox = new JCheckBox("Bindable");
	
	// application log output
	private final JTextArea outputArea = new JTextArea(20, 10);
	
	public J2AGeneratePane() {
		MigLayout layout = new MigLayout("fillx, wrap", "[align right]rel[grow, fill]rel[pref]", "[]rel[]rel[]rel[grow, fill]");
		setLayout(layout);
		
		add(new JLabel(ResourceUtils.tr("j2a.inputdir.label")), "gapright 6");
		inputField.setToolTipText(ResourceUtils.tr("j2a.inputdir.tip"));
		add(inputField);
		JButton inputFolderButton = new JButton(ResourceUtils.tr("j2a.inputdir.browser"));
		inputFolderButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(inputField.getText());
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int flag = -1;
				try{
					flag = fc.showOpenDialog(null);
				} catch(HeadlessException ex) {
					ex.printStackTrace();
				}
				if(flag == JFileChooser.APPROVE_OPTION) {
					inputField.setText(fc.getSelectedFile().getAbsolutePath());
				}
			}
		});
		add(inputFolderButton);
		
		add(new JLabel(ResourceUtils.tr("j2a.outputdir.label")), "gapright 6");
		outputField.setToolTipText(ResourceUtils.tr("j2a.outputdir.tip"));
		add(outputField);
		JButton outputFolderButton = new JButton(ResourceUtils.tr("j2a.outputdir.browser"));
		outputFolderButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(outputField.getText());
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int flag = -1;
				try{
					flag = fc.showOpenDialog(null);
				} catch(HeadlessException ex) {
					ex.printStackTrace();
				}
				if(flag == JFileChooser.APPROVE_OPTION) {
					outputField.setText(fc.getSelectedFile().getAbsolutePath());
				}
			}
		});
		add(outputFolderButton);
		
		add(new JLabel(""), "gapright 6");
		bindableBox.setSelected(true);
		add(bindableBox, "wrap");
		
		add(new JLabel(ResourceUtils.tr("j2a.logs.label")), "gapright 6");
		final JScrollPane outputScrollPane = new JScrollPane(outputArea);
		outputArea.setEditable(false);
		add(outputScrollPane, "span 2");
		
		setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
	}

	@Override
	public void generate() {
		AppContext appCtx = AppContext.getInstance();
		String inputPath = inputField.getText();
		if(inputPath.lastIndexOf(File.separator) != inputPath.length()) {
			inputPath += File.separator;
		}
		appCtx.setInputPath(inputPath);
		String outputPath = outputField.getText();
		if(outputPath.lastIndexOf(File.separator) != outputPath.length()) {
			outputPath += File.separator;
		}
		appCtx.setOutputPath(outputPath);
		
		GenerateTaskWorker worker = new GenerateTaskWorker(new AS3BeanGenerator(), outputArea);
		worker.execute();
	}

	@Override
	public void reset() {
		outputArea.setText("");
	}

}
