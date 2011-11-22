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

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import org.galaxyworld.beangenerator.event.GeneratorProcessEvent;
import org.galaxyworld.beangenerator.generator.AbstractGenerator;

/**
 * Worker for generating task in a separate thread.
 * 
 * @author devbean
 * @version 0.0.1
 */
public class GenerateTaskWorker extends SwingWorker<Void, String> implements GeneratorProcessListener {
	
	/**
	 * Generator for task.
	 */
	private AbstractGenerator generator;
	
	/**
	 * Output area on main window.
	 */
	private LogPane logPane;
	
	public GenerateTaskWorker(AbstractGenerator generator, LogPane logPane) {
		this.generator = generator;
		this.logPane = logPane;
	}
	
	@Override
	protected void process(List<String> outputList) {
		for(String output: outputList) {
			if (isCancelled()) {
				break;
			}
			logPane.appendMessage(output);
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
	
	@Override
    protected void done() {
        try {
            get();
        } catch (final InterruptedException ex) {
        	logPane.appendError(ex.getMessage());
            throw new RuntimeException(ex);
        } catch (final ExecutionException ex) {
        	logPane.appendError(ex.getMessage());
            throw new RuntimeException(ex.getCause());
        }
    }

}
