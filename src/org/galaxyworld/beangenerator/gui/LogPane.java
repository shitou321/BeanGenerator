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

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Log panel.
 * 
 * @author devbean
 * @version 0.0.1
 */
@SuppressWarnings("serial")
public class LogPane extends JLabel {
	
	public LogPane() {
		setVerticalAlignment(SwingConstants.TOP);
		setOpaque(true);
		setBackground(Color.WHITE);
		setFont(new Font("Dialog", Font.PLAIN, 13));
		setText("<html>");
	}
	
	/**
	 * Append message to log pane.
	 * 
	 * @param message append to log pane
	 */
	public void appendMessage(String message) {
		StringBuilder sb = new StringBuilder(getText());
		sb.append(message);
		sb.append("<br />");
		setText(sb.toString());
	}
	
	/**
	 * Append error message to log pane. Error messages usually display with red color.
	 * 
	 * @param error error message
	 */
	public void appendError(String error) {
		StringBuilder sb = new StringBuilder(getText());
		sb.append("<font color='red'>");
		sb.append(error);
		sb.append("</font>");
		sb.append("<br />");
		setText(sb.toString());
	}

}
