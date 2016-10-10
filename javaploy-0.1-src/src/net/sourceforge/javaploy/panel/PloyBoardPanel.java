/*
PloyBoardPanel.java
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
import java.awt.Event;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import net.sourceforge.javaploy.math.GamePoint;

/**
 * The panel containing the game pieces
 * 
 * @author Jeff Conrad
 */
public class PloyBoardPanel extends OffscreenPanel {
	
	public int width, height; // size of display

	public int nRows, nCols; // number of rows/columns

	public int pieceWidth, pieceHeight;

	public int intOffsetX, intOffsetY; // initial offset

	public int intCenterWidth, intCenterHeight; // total line width/height

	public int pieceRadius; // piece Radius

	public Color bdColor, lnColor, selColor; // colors

	public PloyGamePanel ployGamePanel = null; 

	/**
	 * @param theLeft
	 * @param theTop
	 * @param theRows
	 * @param theCols
	 * @param theWidth
	 * @param theHeight
	 */
	public PloyBoardPanel(Color bd, Color ln, Color sel, int theLeft, int theTop,
			int theRows, int theCols, int theWidth, int theHeight) {
		setLocation(new Point(theLeft, theTop));
		setVisible(true);
		setBackground(bd);
		setSize(new java.awt.Dimension(theWidth, theHeight));

		width = theWidth;
		height = theHeight;

		nRows = theRows;
		nCols = theCols;

		pieceWidth = (int) (theWidth / nCols);
		pieceHeight = (int) (theHeight / nRows);

		intOffsetX = (int) (pieceWidth / 2.0);
		intOffsetY = (int) (pieceHeight / 2.0);

		intCenterWidth = theWidth - pieceWidth;
		intCenterHeight = theHeight - pieceHeight;

		pieceRadius = (int) ((pieceHeight / 4.0) * 1.45);

		//
		// Set the game board colors
		// bdColor = board background
		// lnColor = line color
		// selColor = selected piece color
		//
		bdColor = bd;
		lnColor = ln;
		selColor = sel;
	}

	/**
	 * User pressed mouse down at x, y.
	 * 
	 * @param evt
	 * @param x
	 * @param y
	 * @return boolean
	 */
	public boolean mouseDown(Event evt, int x, int y) {
		return ployGamePanel.mouseDown(evt, x, y);
	}

	/**
	 * Paint the ploy game board. Uses an off-screen image to be more efficient.
	 * 
	 * @param g2d
	 */
	public void paintBackground(Graphics2D g2d) {
		//
		// Draw game board to a screen buffer and save buffered image in bi.
		//
		if (bi == null) {
			Dimension d = size();
			Rectangle r = new Rectangle(0, 0, d.width, d.height);

			//
			// Create offscreen buffer image.
			//
			bi = (java.awt.image.BufferedImage) createImage(d.width, d.height);
			Graphics2D big = bi.createGraphics();
			big.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			//
			// Fill in board color/background.
			//
			big.setColor(bdColor);
			big.fillRect(r.x, r.y, r.width, r.height);
			big.setColor(lnColor);
			big.drawRect(r.x, r.y, r.width-1, r.height-1);

			//
			// Draw lines. In ploy, the spaces centers are interconnected by
			// lines.
			//
			big.setColor(lnColor);
			big.drawRect(r.x, r.y, r.width-1, r.height-1);
			for (int j = 0; j < nRows; j++) {
				for (int i = 0; i < nCols; i++) {
					if (j < (nRows - 1)) {
						//
						// Draw the line to the left from this space.
						//
						big.drawLine(intOffsetX + i * pieceWidth, intOffsetY
								+ j * pieceHeight, intOffsetX + i * pieceWidth,
								intOffsetY + (j + 1) * pieceHeight);

						//
						// Draw the line to the left and downward from this
						// space.
						//
						if (i > 0) {
							big.drawLine(intOffsetX + i * pieceWidth,
									intOffsetY + j * pieceHeight, intOffsetX
											+ (i - 1) * pieceWidth, intOffsetY
											+ (j + 1) * pieceHeight);
						}
					}
					if (i < (nCols - 1)) {
						//
						// Draw the line to the right from this space.
						//
						big.drawLine(intOffsetX + i * pieceWidth, intOffsetY
								+ j * pieceHeight, intOffsetX + (i + 1)
								* pieceWidth, intOffsetY + j * pieceHeight);

						//
						// Draw the line to the right and downward from this
						// space.
						//
						if (j < (nRows - 1)) {
							big.drawLine(intOffsetX + i * pieceWidth,
									intOffsetY + j * pieceHeight, intOffsetX
											+ (i + 1) * pieceWidth, intOffsetY
											+ (j + 1) * pieceHeight);
						}
					}
				}
			}

			//
			// Draw the "hollow" circles for each space.
			//
			for (int j = 0; j < nRows; j++) {
				for (int i = 0; i < nCols; i++) {
					//
					// Draw the center of the circle to cover up the previously
					// drawn lines.
					// Creates the "hollow" effect.
					//
					big.setColor(bdColor);
					big.fillOval(intOffsetX + i * pieceWidth - pieceRadius,
							intOffsetY - pieceRadius + j * pieceHeight,
							pieceRadius * 2, pieceRadius * 2);

					//
					// Draw the circle borders for each space.
					//
					big.setColor(lnColor);
					big.drawOval(intOffsetX + i * pieceWidth - pieceRadius,
							intOffsetY - pieceRadius + j * pieceHeight,
							pieceRadius * 2, pieceRadius * 2);
				}
			}
		}
		
		paintBufferedImage(g2d);
	}
	
	public void paintForeground(Graphics2D g2d) {
		//
		// Draw playing pieces
		//
		if (ployGamePanel != null) {
			ployGamePanel.paint(g2d);
		}
	}

	/**
	 * Convert the given row and column, return the appropriate rectangle.
	 * 
	 * @param theRow
	 * @param theCol
	 * @return <code>Rectangle</code> bounding the current piece's row,
	 *         column.
	 */
	public Rectangle RowColumnToRectangle(int theRow, int theCol) {
		Rectangle ret = null;
		if ((theRow >= 1 && theRow <= nRows)
				&& (theCol >= 1 && theCol <= nCols)) {
			ret = new Rectangle(intOffsetX + (theCol - 1) * pieceWidth
					- pieceRadius, intOffsetY - pieceRadius + (theRow - 1)
					* pieceHeight, pieceRadius * 2, pieceRadius * 2);
		}
		return ret;
	}
	
	/**
	 * Convert a position in coordinates to the row column. Used for
	 * determining, for instance, which game coordinate a mouse click
	 * corresponds.
	 * 
	 * @param x
	 * @param y
	 * @return GamePoint
	 */
	public GamePoint XYtoRowColumn(int x, int y) {
		for (int r = 1; r <= nRows; r++) {
			for (int c = 1; c <= nCols; c++) {
				if (RowColumnToRectangle(r, c).contains(x, y)) {
					return new GamePoint(c, r);
				}
			}
		}
		return null;
	}
}
