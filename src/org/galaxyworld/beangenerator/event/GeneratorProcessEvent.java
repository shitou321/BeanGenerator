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

package org.galaxyworld.beangenerator.event;

import java.util.EventObject;

/**
 * Event for generator processing.
 * 
 * @author debean
 * @version 0.0.1
 */
public class GeneratorProcessEvent extends EventObject {
	
	private static final long serialVersionUID = 255711490386531831L;

	/**
	 * Generator process phase.
	 * 
	 * @author devbean
	 * @version 0.0.1
	 */
	public static enum Phase {
		/**
		 * Process starting.
		 */
		Starting,
		
		/**
		 * Process started.
		 */
		Started,
		
		/**
		 * Process one item starting.
		 */
		ItemStarting,
		
		/**
		 * Process one item started.
		 */
		ItemStarted,
		
		/**
		 * Process one item finished.
		 */
		ItemFinished,
		
		/**
		 * Process finished.
		 */
		Finished
	};
	
	private String message;
	
	private Phase phase;
	
	/**
	 * Constructs a process event.
	 * 
	 * @param phase process phase
	 * @param message message to show
	 * @param source The object on which the Event initially occurred.
	 */
	public GeneratorProcessEvent(Phase phase, String message, Object source) {
		super(source);
		this.phase = phase;
		this.message = message;
	}
	
	/**
	 * Message to show.
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Process phase.
	 * 
	 * @return process phase
	 */
	public Phase getPhase() {
		return phase;
	}

}
