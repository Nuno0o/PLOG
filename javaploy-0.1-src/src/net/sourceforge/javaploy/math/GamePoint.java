/*
GamePoint.java
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

package net.sourceforge.javaploy.math;

import java.awt.Dimension;
import java.awt.Point;

/**
 * Game point has extended utilities above and beyond a standard Point.
 * s
 * @author Jeff Conrad
 */
public class GamePoint {

	private Point point;
	
	/**
	 * A class for storing a coordinate in the game space.
	 */
	public GamePoint(int col, int row) {
		super();
		point = new Point(col, row);
	}
	
	/**
	 * A class for storing a coordinate in the game space.
	 */
	public GamePoint(Dimension d) {
		super();
		int width = (int)Math.round(d.getWidth());
		int height = (int)Math.round(d.getHeight());
		point = new Point(width, height);
	}
	
	public int getCol()
	{
		return point.x;
	}

	public int getRow()
	{
		return point.y;
	}
	
	public void setCol(int col) {
		point.x = col;
	}
	
	public void setRow(int row) {
		point.y = row;
	}
	
	public boolean equals(Object o)
	{
		if (o == null)
			return false;
		if (!(o instanceof GamePoint))
			return false;
		GamePoint ogp = (GamePoint)o;
		if (this.getCol() != ogp.getCol())
			return false;
		if (this.getRow() != ogp.getRow())
			return false;
		return true;
	}
	
	public double pointToRadians(Point p) {
		double radians = 0.0d;
		
		double xc = (double) (this.getCol() / 2);
		double yc = (double) (this.getRow() / 2);
		double xs = (double) (p.getX() - xc);
		double ys = (double) (-p.getY() + yc);

		// currentFacing in radians
		if (xs > 0.0 && ys > 0.0) {
			radians = Math.atan(ys / xs);
		} else if (xs < 0.0 && ys > 0.0) {
			radians = Math.PI + Math.atan(ys / xs);
		} else if (xs < 0.0 && ys < 0.0) {
			radians = Math.PI + Math.atan(ys / xs);
		} else if (xs > 0.0 && ys < 0.0) {
			radians = 2 * Math.PI + Math.atan(ys / xs);
		} else {
			if (xs == 0.0 && ys >= 0.0)
				radians = Math.PI / 2;
			else if (xs == 0.0 && ys < 0.0)
				radians = 3 * Math.PI / 2;
			else if (xs >= 0.0 && ys == 0.0)
				radians = 0.0;
			else if (xs < 0.0 && ys == 0.0)
				radians = Math.PI;
		}
		return radians;
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getClass().getName());
		sb.append(";col=" + getCol());
		sb.append(";row=" + getRow());
		return sb.toString();
	}
}
