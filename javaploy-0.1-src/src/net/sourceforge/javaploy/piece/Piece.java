/*
Piece.java
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

package net.sourceforge.javaploy.piece;

import java.awt.Rectangle;

import net.sourceforge.javaploy.math.GamePoint;

/**
 * Class representing a standard game piece.
 * 
 * @author Jeff Conrad
 */
public class Piece {

	public boolean drawFill = true;

	public boolean selected = false;

	public int col = 0;

	public int row = 0;

	public Rectangle rectangle = null;

	public int player = 0;

	public int team = 0;

	public boolean shown = true;

	public int shadowOffsetPixels = 2;

	/**
	 * @param pt
	 * @return boolean
	 */
	public boolean atRowColumn(GamePoint pt) {
		return getRowColumn().equals(pt);
	}

	/**
	 * @param x
	 * @param y
	 * @return boolean
	 */
	public boolean containsXY(int x, int y) {
		if (isShown() && (rectangle != null)) {
			return rectangle.contains(x, y);
		}
		return false;
	}

	public void deselect() {
		selected = false;
	}

	/**
	 * @return int
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @return int
	 */
	public int getPlayer() {
		return player;
	}

	/**
	 * @return GamePoint
	 */
	public GamePoint getRowColumn() {
		return (new GamePoint(col, row));
	}

	/**
	 * @return int
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return int
	 */
	public int getTeam() {
		return team;
	}

	public void hide() {
		shown = false;
	}

	/**
	 * @param rcPoint
	 */
	public void moveToRowColumn(GamePoint rcPoint) {
		col = rcPoint.getCol();
		row = rcPoint.getRow();
	}

	public void select() {
		selected = true;
	}

	/**
	 * @param thePlayer
	 */
	public void setPlayer(int thePlayer) {
		player = thePlayer;
	}

	/**
	 * @param r
	 * @param c
	 */
	public void setRowColumn(int r, int c) {
		row = r;
		col = c;
	}

	/**
	 * @param theTeam
	 */
	public void setTeam(int theTeam) {
		team = theTeam;
	}

	/**
	 * @return boolean
	 */
	public boolean isShown() {
		return shown;
	}

	/**
	 * @param shown
	 */
	public void setShown(boolean shown) {
		this.shown = shown;
	}


	/**
	 * @return shadowOffsetPixels
	 */
	public int getShadowOffsetPixels() {
		return shadowOffsetPixels;
	}

	/**
	 * @param shadowOffsetPixels
	 */
	public void setShadowOffsetPixels(int shadowOffsetPixels) {
		this.shadowOffsetPixels = shadowOffsetPixels;
	}
}