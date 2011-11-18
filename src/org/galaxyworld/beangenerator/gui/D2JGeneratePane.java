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
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.galaxyworld.beangenerator.core.AppContext;
import org.galaxyworld.beangenerator.core.GenerateTaskWorker;
import org.galaxyworld.beangenerator.core.JavaBeanGenerator;
import org.galaxyworld.beangenerator.data.CommonData;
import org.galaxyworld.beangenerator.util.ResourceUtils;

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
	private final JTextField outputField = new JTextField(AppContext.getInstance().getAppPath());
	
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
		
		add(new JLabel(ResourceUtils.tr("d2j.outputdir.label")), "gapright 6");
		outputField.setToolTipText(ResourceUtils.tr("d2j.outputdir.tip"));
		add(outputField);
		JButton outputFolderButton = new JButton(ResourceUtils.tr("d2j.outputdir.browser"));
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
		
		add(new JLabel(ResourceUtils.tr("d2j.package.label")), "gapright 6");
		packageField.setToolTipText(ResourceUtils.tr("d2j.package.tip"));
		add(packageField, "span 2");

		add(new JLabel(ResourceUtils.tr("d2j.defaultcomment.label")), "gapright 6");
		commentField.setToolTipText(ResourceUtils.tr("d2j.defaultcomment.tip"));
		add(commentField, "span 2");
		
		add(new JLabel(ResourceUtils.tr("d2j.author.label")), "gapright 6");
		authorField.setToolTipText(ResourceUtils.tr("d2j.author.tip"));
		add(authorField, "span 2");
		
		add(new JLabel(ResourceUtils.tr("d2j.version.label")), "gapright 6");
		versionField.setToolTipText(ResourceUtils.tr("d2j.version.tip"));
		add(versionField, "span 2");
		
		add(new JLabel(ResourceUtils.tr("d2j.logs.label")), "gapright 6");
		final JScrollPane outputScrollPane = new JScrollPane(outputArea);
		outputArea.setEditable(false);
		add(outputScrollPane, "span 2");
		
		setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
	}
	
	@Override
	public void generate() {
		AppContext appCtx = AppContext.getInstance();
		String outputPath = outputField.getText();
		if(outputPath.lastIndexOf(File.separator) != outputPath.length()) {
			outputPath += File.separator;
		}
		appCtx.setOutputPath(outputPath);
		CommonData cd = new CommonData();
		cd.setDefaultComment(commentField.getText());
		cd.setAuthor(authorField.getText());
		cd.setVersion(versionField.getText());
		cd.setPackageName(packageField.getText());
		appCtx.setCommonData(cd);
		
		GenerateTaskWorker worker = new GenerateTaskWorker(new JavaBeanGenerator(), outputArea);
		worker.execute();
	}

	@Override
	public void reset() {
		packageField.setText("");
		commentField.setText("");
		authorField.setText("");
		versionField.setText("");
		outputArea.setText("");
	}

}
