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

import java.awt.Insets;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * Button on button panel.
 * 
 * @author devbean
 * @version 0.0.1
 */
@SuppressWarnings("serial")
public class ToolBarButton extends JButton {
	
	public ToolBarButton(String text, Icon icon) {
		super(text, icon);
		initButton();
	}

	public ToolBarButton() {
		super();
		initButton();
	}

	public ToolBarButton(Action action) {
		super(action);
		initButton();
	}

	private void initButton() {
		setRolloverEnabled(true);
		setRequestFocusEnabled(false);
		setMargin(new Insets(2, 4, 2, 4));
		putClientProperty("JToolBar.isRollover", Boolean.TRUE);
	}

	public boolean isFocusTraversable() {
		return isRequestFocusEnabled();
	}

	/**
	 * @see javax.swing.JButton#updateUI()
	 */
	public void updateUI() {
		super.updateUI();

		setRolloverEnabled(true);
		putClientProperty("JToolBar.isRollover", Boolean.TRUE);
	}
}
