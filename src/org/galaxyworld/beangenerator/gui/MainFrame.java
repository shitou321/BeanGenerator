package org.galaxyworld.beangenerator.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

import org.galaxyworld.beangenerator.core.Config;
import org.galaxyworld.beangenerator.core.DatabaseProcessor;
import org.galaxyworld.beangenerator.core.JavaBeanGenerator;
import org.galaxyworld.beangenerator.data.CommonData;
import org.galaxyworld.beangenerator.data.JavaBeanData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	private static final Logger logger = LoggerFactory.getLogger(MainFrame.class);
	
	private final JTextArea outputArea = new JTextArea(20, 10);

	public MainFrame() {
		setBounds(25, 25, 800, 600);
		setMinimumSize(new Dimension(800, 600));
		setTitle("Bean Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pane = new JPanel();
		MigLayout layout = new MigLayout("fillx, wrap", "[align right]rel[grow, fill]rel[pref]", "[]rel[]rel[]rel[]rel[]rel[grow, fill]rel[nogrid]");
		pane.setLayout(layout);
		
		pane.add(new JLabel("Output Directory"), "gapright 6");
		final JTextField outputField = new JTextField(Config.getInstance().getAppPath());
		outputField.setToolTipText("output path for generated bean source files");
		pane.add(outputField);
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
		pane.add(outputFolderButton);
		
		pane.add(new JLabel("Package Name"), "gapright 6");
		final JTextField packageField = new JTextField();
		packageField.setToolTipText("package for generated beans");
		pane.add(packageField, "span 2");

		pane.add(new JLabel("Default Comment"), "gapright 6");
		final JTextField commentField = new JTextField();
		commentField.setToolTipText("default comment for uncommented beans");
		pane.add(commentField, "span 2");
		
		pane.add(new JLabel("Author"), "gapright 6");
		final JTextField authorField = new JTextField();
		authorField.setToolTipText("author for beans");
		pane.add(authorField, "span 2");
		
		pane.add(new JLabel("Version"), "gapright 6");
		final JTextField versionField = new JTextField();
		versionField.setToolTipText("version number for beans");
		pane.add(versionField, "span 2");
		
		pane.add(new JLabel("Output"), "gapright 6");
		final JScrollPane outputScrollPane = new JScrollPane(outputArea);
		outputArea.setEditable(false);
		pane.add(outputScrollPane, "span 2");
		
		JButton genButton = new JButton("Generate");
		genButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
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
		});
		pane.add(genButton, "align center");
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				packageField.setText("");
				commentField.setText("");
				authorField.setText("");
				versionField.setText("");
			}
			
		});
		pane.add(resetButton, "align center");
		
		pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		
		getContentPane().add(pane, BorderLayout.CENTER);
		
		JMenuBar mb = new JMenuBar();
		JMenu mFile = new JMenu("File");
		JMenuItem miExit = new JMenuItem("Exit");
		miExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		mFile.add(miExit);
		mb.add(mFile);
		getContentPane().add(mb, BorderLayout.NORTH);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
	        @Override
	        public void run() {
	            try {
	            	UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	            } catch (Exception ex) {
	                logger.warn("Could not load NimbusLookAndFeel.");
	            }
	            MainFrame frame = new MainFrame();
	    		frame.setVisible(true);
	        }
	        
	    });
	}
	
	class DatabaseWorker extends SwingWorker<Void, String> {

		@Override
		protected Void doInBackground() throws Exception {
			JavaBeanGenerator gen = new JavaBeanGenerator();
			DatabaseProcessor dp = new DatabaseProcessor();
			List<JavaBeanData> beans = dp.getJavaBeanData();
			for(JavaBeanData bean : beans) {
				gen.generate(bean);
				StringBuilder sb = new StringBuilder();
				sb.append("Finish generate table ");
				sb.append(bean.getComment());
				sb.append(".\n");
				publish(sb.toString());
			}
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
