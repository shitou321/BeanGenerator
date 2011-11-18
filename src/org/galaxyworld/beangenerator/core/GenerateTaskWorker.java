package org.galaxyworld.beangenerator.core;

import java.util.List;

import javax.swing.SwingWorker;
import javax.swing.text.JTextComponent;

import org.galaxyworld.beangenerator.event.GeneratorProcessEvent;

public class GenerateTaskWorker extends SwingWorker<Void, String> implements GeneratorProcessListener {
	
	private AbstractGenerator generator;
	
	private JTextComponent outputArea;
	
	public GenerateTaskWorker(AbstractGenerator generator, JTextComponent outputArea) {
		this.generator = generator;
		this.outputArea = outputArea;
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

	@Override
	protected Void doInBackground() throws Exception {
		generator.addGeneratorProcessListener(this);
		generator.generate();
		return null;
	}

}
