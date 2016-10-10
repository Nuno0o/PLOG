/*
PloyAnglePanel.java
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
import java.awt.Polygon;
import java.awt.RenderingHints;

import net.sourceforge.javaploy.math.GamePoint;

/**
 * Represents a control to change the heading of a piece.
 * 
 * @author Jeff Conrad
 */
public class PloyAnglePanel extends OffscreenPanel {

	private static final int centerOffset = 3;

	int lastx = 0;

	int lasty = 0;

	int initialx = 0;

	int initialy = 0;

	int x = 0;

	int y = 0;

	/* the current angle */
	double currentDegrees = 0.0d;

	/* the original angle */
	double initialDegrees = 0.0d;

	/* the amount the angle can change */
	double quantumDegrees = 45.0d;

	/* whether the current angle is being changed */
	boolean angleActiveState = false;

	/* fill color for the circle */
	Color ovalFillColor;

	/* line color for the circle */
	Color ovalLineColor;

	/* heading color, not selected */
	Color headingColor;

	/* heading color, when selected */
	Color headingSelectColor;

	/* heading color, previous */
	Color headingInitialColor;
	
	GamePoint gamePoint;
	
	private static final int borderPixels = 5;

	public PloyAnglePanel(Color ovalFillColor, Color ovalLineColor, Color headingColor, Color headingSelectColor, Color headingInitialColor) {
		this.ovalFillColor = ovalFillColor;
		this.ovalLineColor = ovalLineColor;
		this.headingColor = headingColor;
		this.headingSelectColor = headingSelectColor;
		this.headingInitialColor = headingInitialColor;
	}

	/**
	 * @param g2d
	 */
	public void paintBackground(Graphics2D g2d) {
		//
		// Draw game board to a screen buffer and save buffered image in bi.
		//
		if (bi == null) {
			Dimension d = size();

			//
			// Create offscreen buffer image.
			//
			bi = (java.awt.image.BufferedImage) createImage(d.width, d.height);
			Graphics2D big = bi.createGraphics();
			big.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			
			big.setColor(ovalFillColor);
			big.fillOval(borderPixels, borderPixels, d.width - 2 * borderPixels, d.height - 2 * borderPixels);
			big.setColor(ovalLineColor);
			big.drawOval(borderPixels, borderPixels, d.width - 2 * borderPixels, d.height - 2 * borderPixels);
		}
		
		paintBufferedImage(g2d);
	}
	
	/**
	 * @param g2d
	 */
	public void paintForeground(Graphics2D g2d) {
		Dimension d = getSize();
		int xs, ys = 0;

		int xcenter = (int) (d.width / 2);
		int ycenter = (int) (d.height / 2);
		int radius = (int) (3 * d.width / 8);

		// paint the initial heading
		//if (angleActiveState) {
			g2d.setColor(headingInitialColor);

			Polygon polygon2 = new Polygon();
			polygon2.addPoint(initialx, initialy);
			polygon2.addPoint(xcenter - centerOffset, ycenter);
			polygon2.addPoint(xcenter + centerOffset, ycenter);
			polygon2.addPoint(initialx, initialy);
			g2d.fillPolygon(polygon2);

			Polygon polygon3 = new Polygon();
			polygon3.addPoint(initialx, initialy);
			polygon3.addPoint(xcenter, ycenter + centerOffset);
			polygon3.addPoint(xcenter, ycenter - centerOffset);
			polygon3.addPoint(initialx, initialy);
			g2d.fillPolygon(polygon3);
			
			g2d.fillOval(xcenter - centerOffset, ycenter - centerOffset, 2 * centerOffset, 2 * centerOffset);
		//}
		
		// currentFacing in degrees
		xs = (int) (Math.cos(Math.toRadians(currentDegrees)) * radius + xcenter);
		ys = (int) (-Math.sin(Math.toRadians(currentDegrees)) * radius + ycenter);

		if (angleActiveState)
			g2d.setColor(headingColor);
		else
			g2d.setColor(headingSelectColor);

		// paint the current heading
		Polygon polygon0 = new Polygon();
		polygon0.addPoint(xs, ys);
		polygon0.addPoint(xcenter - centerOffset, ycenter);
		polygon0.addPoint(xcenter + centerOffset, ycenter);
		polygon0.addPoint(xs, ys);
		g2d.fillPolygon(polygon0);

		Polygon polygon1 = new Polygon();
		polygon1.addPoint(xs, ys);
		polygon1.addPoint(xcenter, ycenter + centerOffset);
		polygon1.addPoint(xcenter, ycenter - centerOffset);
		polygon1.addPoint(xs, ys);
		g2d.fillPolygon(polygon1);
		
		g2d.fillOval(xcenter - centerOffset, ycenter - centerOffset, 2 * centerOffset, 2 * centerOffset);

		lastx = xs;
		lasty = ys;
	}

	/**
	 * @param evt
	 * @param x
	 * @param y
	 * @return v
	 */
	public boolean mouseDown(Event evt, int x, int y) {
		angleActiveState = !angleActiveState;
		repaint();
		return true;
	}
	
	public GamePoint getGamePoint() {
		if (gamePoint == null) {
			gamePoint = new GamePoint(this.getSize());
		}
		return gamePoint;
	}

	/**
	 * @param evt
	 * @param x
	 * @param y
	 * @return boolean 
	 */
	public boolean mouseMove(Event evt, int x, int y) {
		if (angleActiveState) {
			double oldCurrentDegrees = currentDegrees;
			Point point = new Point(x, y);
			double currentRadians = getGamePoint().pointToRadians(point);
			setCurrentDegrees(Math.toDegrees(currentRadians));
			if (oldCurrentDegrees != currentDegrees) {
				repaint();
			}
			return true;
		} else
			return false;
	}

	// getters and setters
	
	public Color getOvalFillColor() {
		return ovalFillColor;
	}

	public void setOvalFillColor(Color ovalFillColor) {
		this.ovalFillColor = ovalFillColor;
	}

	public Color getOvalLineColor() {
		return ovalLineColor;
	}

	public void setOvalLineColor(Color ovalLineColor) {
		this.ovalLineColor = ovalLineColor;
	}

	public Color getHeadingColor() {
		return headingColor;
	}

	public void setHeadingColor(Color headingColor) {
		this.headingColor = headingColor;
	}

	public Color getHeadingSelectColor() {
		return headingSelectColor;
	}

	public void setHeadingSelectColor(Color headingSelectColor) {
		this.headingSelectColor = headingSelectColor;
	}

	public Color getHeadingInitialColor() {
		return headingInitialColor;
	}

	public void setHeadingInitialColor(Color headingInitialColor) {
		this.headingInitialColor = headingInitialColor;
	}

	/**
	 * Set the initial degrees, the current degress, and calculate the initial coordinates.
	 * @param degrees
	 */
	public void setInitialDegrees(double degrees) {
		angleActiveState = false;
		initialDegrees = quantizeDegrees(degrees);
		setCurrentDegrees(initialDegrees);
		Dimension d = getSize();

		int xcenter = (int) (d.width / 2);
		int ycenter = (int) (d.height / 2);
		int radius = (int) (3 * d.width / 8);
		
		initialx = (int) (Math.cos(Math.toRadians(initialDegrees)) * radius + xcenter);
		initialy = (int) (-Math.sin(Math.toRadians(initialDegrees)) * radius + ycenter);
	}

	/**
	 * @return double
	 */
	public double getCurrentDegrees() {
		return currentDegrees;
	}

	/**
	 * @param degrees
	 */
	public void setCurrentDegrees(double degrees) {
		this.currentDegrees = quantizeDegrees(degrees);
	}
	
	/**
	 * Quantize the provided sdegrees.
	 * 
	 * @param degrees
	 * @return the degrees, quantized to the quantization interval
	 */
	protected double quantizeDegrees(double degrees) {
		double ret = degrees;
		if (quantumDegrees > 0.0) {
			ret = (double) Math.round(degrees
					/ quantumDegrees)
					* quantumDegrees;
		}
		return ret;
	}
}
