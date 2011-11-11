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

import org.galaxyworld.beangenerator.core.Config;
import org.galaxyworld.beangenerator.core.DatabaseProcessor;
import org.galaxyworld.beangenerator.core.JavaBeanGenerator;
import org.galaxyworld.beangenerator.data.CommonData;
import org.galaxyworld.beangenerator.data.JavaBeanData;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class D2JGeneratePane extends JPanel implements GenerateAction {
	
	private final JTextField outputField = new JTextField(Config.getInstance().getAppPath());
	
	private final JTextField packageField = new JTextField();
	
	private final JTextField commentField = new JTextField();
	
	private final JTextField authorField = new JTextField();
	
	private final JTextField versionField = new JTextField();
	
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
	
	class DatabaseWorker extends SwingWorker<Void, String> {

		@Override
		protected Void doInBackground() throws Exception {
			publish("Start parsing database tables...\n");
			DatabaseProcessor dp = new DatabaseProcessor();
			List<String> tables = dp.getTables();
			publish("Finish parsed database.\n");
			JavaBeanGenerator gen = new JavaBeanGenerator();
			for(String tableName : tables) {
				JavaBeanData bean = dp.getTableMetaData(tableName);
				gen.generate(bean);
				StringBuilder sb = new StringBuilder();
				sb.append("Finish generate table ");
				sb.append(bean.getComment());
				sb.append(".\n");
				publish(sb.toString());
			}
			dp.closeConnection();
			publish("Done.");
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

	}

}
