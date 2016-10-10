/*
OffscreenPanel.java
Copyright (c) 2000-2006 Jeff D. Conrad.  All rights reserved.

Javaploy License
Based on MIT License, http://www.opensource.org/licenses/mit-license.php

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

package net.sourceforge.javaploy.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.RenderingHints;

/**
 * A panel that uses an offscreen graphics buffer.
 * 
 * @author Jeff Conrad
 */
abstract public class OffscreenPanel extends Panel {

	protected java.awt.image.BufferedImage bi; // offscreen buffer  

	/**
	 * Paint the ploy game board. Uses an off-screen image to be more efficient.
	 * 
	 * @param g
	 */
	public void paint(Graphics g) {
		//
		// Create off-screen buffer
		//
		Graphics2D g2d = (Graphics2D) g;

		//
		// Use anti-aliasing to avoid jagged edges.
		//
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		paintBackground(g2d);
		paintForeground(g2d);
	}
	
	/**
	 * Paint the background (usually try to cache this in an image).
	 *
	 * @param g2d
	 */
	abstract public void paintBackground(Graphics2D g2d);
	
	/**
	 * Paint the foreground.
	 * 
	 * @param g2d
	 */
	abstract public void paintForeground(Graphics2D g2d);
	
	public void paintBufferedImage(Graphics2D g2d) {
		//
		// Draws the buffered image to the screen.
		//
		g2d.drawImage(bi, 0, 0, this);
	}
}
