/*
PloyPieceBoardPanel.java
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;

import net.sourceforge.javaploy.piece.PloyPiece;
import net.sourceforge.javaploy.piece.PloyPieceType;

/**
 * A board for a specific PloyPiece
 * 
 * @author Jeff Conrad
 */
public class PloyPieceBoardPanel extends Panel {
	
	public Color bdColor, selColor;

	protected PloyPiece ployPiece;

	protected String pieceColor;

	/**
	 * Used to create a small PloyBoard which displays a single playing piece
	 * 
	 * @param theLeft
	 * @param theTop
	 * @param theWidth
	 * @param theHeight
	 */
	public PloyPieceBoardPanel(Color bd, Color sel, int theLeft, int theTop,
			int theWidth, int theHeight) {
		//
		// Prepare size and location
		//
		setLocation(new Point(theLeft, theTop));
		setSize(new java.awt.Dimension(theWidth, theHeight));

		//
		// Create my ploy piece
		//
		ployPiece = new PloyPiece();
		ployPiece.deselect();
		ployPiece.setPieceType(PloyPieceType.PIECE_4_90);
		ployPiece.setFacing(45);

		//
		// Set the initial color scheme
		// 
		bdColor = bd;
		selColor = sel;
	}

	/*
	 * Used to deselect the current piece
	 */
	public void deselect() {
		ployPiece.deselect();
		repaint();
	}

	/**
	 * Returns the current piece color
	 * 
	 * @return String the current piece color
	 */
	public String getPieceColor() {
		return pieceColor;
	}

	/**
	 * Paint the current display
	 * 
	 * @param g
	 */
	public void paint(Graphics g) {
		Dimension d = size();
		Rectangle r = new Rectangle(0, 0, d.width, d.height);

		//
		// Board area
		//
		g.setColor(bdColor);
		g.fillRect(r.x, r.y, r.width, r.height);

		if (ployPiece != null) {
			ployPiece.paintPiece(g, r);
		}
	}

	/**
	 * Select the piece
	 */
	public void select() {
		ployPiece.select();
		repaint();
	}

	/**
	 * Set the current piece color
	 * 
	 * @param theColor
	 */
	public void setPieceColor(String theColor) {
		pieceColor = theColor;
		if (ployPiece != null) {
			ployPiece.setPieceColor(theColor);
		}
		repaint();
	}
}
