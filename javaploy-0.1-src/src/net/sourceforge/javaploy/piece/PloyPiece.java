/*
PloyPiece.java
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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;

import net.sourceforge.javaploy.math.GamePoint;

/**
 * Class representing a ploy game piece.
 * 
 * @author Jeff Conrad
 */
public class PloyPiece extends Piece {
	
	private boolean rotating = false;

	private Color color1 = Color.black;

	private Color color2 = Color.white;

	private float facing = 90;

	private int pieceType = PloyPieceType.PIECE_1_00;
	
	private PloyPieceState pieceState = null;

	public PloyPiece() {
		super();
	}

	/**
	 * @return float the current facing
	 */
	public float getFacing() {
		return facing;
	}

	/**
	 * @param g
	 * @param r
	 */
	public void paintPiece(Graphics g, Rectangle r) {
		Graphics2D g2d = (Graphics2D) g;

		// This time, we want to use anti-aliasing if possible
		// to avoid the jagged edges that were so prominent in
		// our last example. With ask the Java 2D rendering
		// engine (Graphics2D) to do this using a "rendering hint".
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		rectangle = r;
		if (shown && r.intersects(g2d.getClipRect())) {
			//
			// shadow
			//
			if (selected) {
				g2d.setColor(Color.white);
				g2d.fillOval(r.x, r.y, r.width, r.height);
			}
			
			//
			// fill
			//
			if (drawFill) {
				//
				// every piece is shaped like a circle
				//
				g2d.setColor(color1);
				g2d.fillOval(r.x + shadowOffsetPixels, r.y + shadowOffsetPixels, r.width
						- 2 * shadowOffsetPixels, r.height - 2 * shadowOffsetPixels);

				g2d.setColor(color2);
				g2d.drawOval(r.x + shadowOffsetPixels, r.y + shadowOffsetPixels, r.width
						- 2 * shadowOffsetPixels, r.height - 2 * shadowOffsetPixels);
				
				int arcAngle = 20;
				int angle = (int) (facing - arcAngle / 2.0);

				// always render the starting arc
				g2d.fillArc(r.x + shadowOffsetPixels, r.y + shadowOffsetPixels, r.width
						- 2 * shadowOffsetPixels, r.height - 2 * shadowOffsetPixels,
						angle, arcAngle);

				// render additional arcs
				if (pieceType == PloyPieceType.PIECE_1_00) {
					// no change
				} else if (pieceType == PloyPieceType.PIECE_2_45) {
					angle = (int) (facing + 45 - arcAngle / 2.0);
					g2d.fillArc(r.x + shadowOffsetPixels, r.y + shadowOffsetPixels,
							r.width - 2 * shadowOffsetPixels, r.height - 2
									* shadowOffsetPixels, angle, arcAngle);
				} else if (pieceType == PloyPieceType.PIECE_2_90) {
					angle = (int) (facing + 90 - arcAngle / 2.0);
					g2d.fillArc(r.x + shadowOffsetPixels, r.y + shadowOffsetPixels,
							r.width - 2 * shadowOffsetPixels, r.height - 2
									* shadowOffsetPixels, angle, arcAngle);
				} else if (pieceType == PloyPieceType.PIECE_2_180) {
					angle = (int) (facing + 180 - arcAngle / 2.0);
					g2d.fillArc(r.x + shadowOffsetPixels, r.y + shadowOffsetPixels,
							r.width - 2 * shadowOffsetPixels, r.height - 2
									* shadowOffsetPixels, angle, arcAngle);
				} else if (pieceType == PloyPieceType.PIECE_3_45) {
					angle = (int) (facing + 45 - arcAngle / 2.0);
					g2d.fillArc(r.x + shadowOffsetPixels, r.y + shadowOffsetPixels,
							r.width - 2 * shadowOffsetPixels, r.height - 2
									* shadowOffsetPixels, angle, arcAngle);

					angle = (int) (facing + 90 - arcAngle / 2.0);
					g2d.fillArc(r.x + shadowOffsetPixels, r.y + shadowOffsetPixels,
							r.width - 2 * shadowOffsetPixels, r.height - 2
									* shadowOffsetPixels, angle, arcAngle);
				} else if (pieceType == PloyPieceType.PIECE_3_90) {
					angle = (int) (facing + 90 - arcAngle / 2.0);
					g2d.fillArc(r.x + shadowOffsetPixels, r.y + shadowOffsetPixels,
							r.width - 2 * shadowOffsetPixels, r.height - 2
									* shadowOffsetPixels, angle, arcAngle);

					angle = (int) (facing + 180 - arcAngle / 2.0);
					g2d.fillArc(r.x + shadowOffsetPixels, r.y + shadowOffsetPixels,
							r.width - 2 * shadowOffsetPixels, r.height - 2
									* shadowOffsetPixels, angle, arcAngle);
				} else if (pieceType == PloyPieceType.PIECE_3_120) {
					angle = (int) (facing + 120 - arcAngle / 2.0);
					g2d.fillArc(r.x + shadowOffsetPixels, r.y + shadowOffsetPixels,
							r.width - 2 * shadowOffsetPixels, r.height - 2
									* shadowOffsetPixels, angle, arcAngle);

					angle = (int) (facing + 240 - arcAngle / 2.0);
					g.fillArc(r.x + shadowOffsetPixels, r.y + shadowOffsetPixels, r.width
							- 2 * shadowOffsetPixels, r.height - 2 * shadowOffsetPixels,
							angle, arcAngle);
				} else if (pieceType == PloyPieceType.PIECE_4_90) {
					angle = (int) (facing + 90 - arcAngle / 2.0);
					g2d.fillArc(r.x + shadowOffsetPixels, r.y + shadowOffsetPixels,
							r.width - 2 * shadowOffsetPixels, r.height - 2
									* shadowOffsetPixels, angle, arcAngle);

					angle = (int) (facing + 180 - arcAngle / 2.0);
					g2d.fillArc(r.x + shadowOffsetPixels, r.y + shadowOffsetPixels,
							r.width - 2 * shadowOffsetPixels, r.height - 2
									* shadowOffsetPixels, angle, arcAngle);

					angle = (int) (facing + 270 - arcAngle / 2.0);
					g2d.fillArc(r.x + shadowOffsetPixels, r.y + shadowOffsetPixels,
							r.width - 2 * shadowOffsetPixels, r.height - 2
									* shadowOffsetPixels, angle, arcAngle);
				}
			}
		}
	}

	/**
	 * @param rcPoint
	 * @return boolean - whether the given point is a permissable move
	 */
	public boolean permissableMoveRC(GamePoint rcPoint) {
		ArrayList pm = permissableMoves();
		for (int i = 0; i < pm.size(); i++) {
			if (rcPoint.equals(pm.get(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return ArrayList - an array of permissable GamePoints
	 */
	public ArrayList permissableMoves() {
		// maximum for ploy - 3 + 3 + 3 alternatives
		ArrayList ret = new ArrayList();
		double radius = (float) Math.sqrt((double) 2.0);
		double angle;
		GamePoint anRC;

		if (pieceType == PloyPieceType.PIECE_1_00) {
			angle = (double) facing;
			anRC = XYARItoXY(col, row, angle, radius, 1);
			if (pieceState.permissableRowColumn(anRC)) {
				ret.add(anRC);
			}
		} else if (pieceType == PloyPieceType.PIECE_2_45) {
			angle = (double) facing;
			for (int i = 1; i <= 2; i++) {
				anRC = XYARItoXY(col, row, angle, radius, i);
				if (pieceState.permissableRowColumn(anRC)) {
					ret.add(anRC);
				}
			}
			angle = (double) (facing + 45.0d);
			for (int i = 1; i <= 2; i++) {
				anRC = XYARItoXY(col, row, angle, radius, i);
				if (pieceState.permissableRowColumn(anRC)) {
					ret.add(anRC);
				}
			}
		} else if (pieceType == PloyPieceType.PIECE_2_90) {
			angle = (double) facing;
			for (int i = 1; i <= 2; i++) {
				anRC = XYARItoXY(col, row, angle, radius, i);
				if (pieceState.permissableRowColumn(anRC)) {
					ret.add(anRC);
				}
			}
			angle = (double) (facing + 90.0d);
			for (int i = 1; i <= 2; i++) {
				anRC = XYARItoXY(col, row, angle, radius, i);
				if (pieceState.permissableRowColumn(anRC)) {
					ret.add(anRC);
				}
			}
		} else if (pieceType == PloyPieceType.PIECE_2_180) {
			angle = (double) facing;
			for (int i = 1; i <= 2; i++) {
				anRC = XYARItoXY(col, row, angle, radius, i);
				if (pieceState.permissableRowColumn(anRC)) {
					ret.add(anRC);
				}
			}
			angle = (double) (facing + 180.0d);
			for (int i = 1; i <= 2; i++) {
				anRC = XYARItoXY(col, row, angle, radius, i);
				if (pieceState.permissableRowColumn(anRC)) {
					ret.add(anRC);
				}
			}
		} else if (pieceType == PloyPieceType.PIECE_3_45) {
			angle = (double) facing;
			for (int i = 1; i <= 3; i++) {
				anRC = XYARItoXY(col, row, angle, radius, i);
				if (pieceState.permissableRowColumn(anRC)) {
					ret.add(anRC);
				}
			}
			angle = (double) (facing + 45);
			for (int i = 1; i <= 3; i++) {
				anRC = XYARItoXY(col, row, angle, radius, i);
				if (pieceState.permissableRowColumn(anRC)) {
					ret.add(anRC);
				}
			}
			angle = (double) (facing + 90);
			for (int i = 1; i <= 3; i++) {
				anRC = XYARItoXY(col, row, angle, radius, i);
				if (pieceState.permissableRowColumn(anRC)) {
					ret.add(anRC);
				}
			}
		} else if (pieceType == PloyPieceType.PIECE_3_90) {
			angle = (double) facing;
			for (int i = 1; i <= 3; i++) {
				anRC = XYARItoXY(col, row, angle, radius, i);
				if (pieceState.permissableRowColumn(anRC)) {
					ret.add(anRC);
				}
			}
			angle = (double) (facing + 90.0d);
			for (int i = 1; i <= 3; i++) {
				anRC = XYARItoXY(col, row, angle, radius, i);
				if (pieceState.permissableRowColumn(anRC)) {
					ret.add(anRC);
				}
			}
			angle = (double) (facing + 180.0d);
			for (int i = 1; i <= 3; i++) {
				anRC = XYARItoXY(col, row, angle, radius, i);
				if (pieceState.permissableRowColumn(anRC)) {
					ret.add(anRC);
				}
			}
		} else if (pieceType == PloyPieceType.PIECE_3_120) {
			angle = (double) facing;
			for (int i = 1; i <= 3; i++) {
				anRC = XYARItoXY(col, row, angle, radius, i);
				if (pieceState.permissableRowColumn(anRC)) {
					ret.add(anRC);
				}
			}
			angle = (double) (facing + 120.0d);
			for (int i = 1; i <= 3; i++) {
				anRC = XYARItoXY(col, row, angle, radius, i);
				if (pieceState.permissableRowColumn(anRC)) {
					ret.add(anRC);
				}
			}
			angle = (double) (facing + 240.0d);
			for (int i = 1; i <= 3; i++) {
				anRC = XYARItoXY(col, row, angle, radius, i);
				if (pieceState.permissableRowColumn(anRC)) {
					ret.add(anRC);
				}
			}
		} else if (pieceType == PloyPieceType.PIECE_4_90) {
			angle = (double) facing;
			anRC = XYARItoXY(col, row, angle, radius, 1);
			if (pieceState.permissableRowColumn(anRC)) {
				ret.add(anRC);
			}
			angle = (double) (facing + 90.0d);
			anRC = XYARItoXY(col, row, angle, radius, 1);
			if (pieceState.permissableRowColumn(anRC)) {
				ret.add(anRC);
			}
			angle = (double) (facing + 180.0d);
			anRC = XYARItoXY(col, row, angle, radius, 1);
			if (pieceState.permissableRowColumn(anRC)) {
				ret.add(anRC);
			}
			angle = (double) (facing + 270.0d);
			anRC = XYARItoXY(col, row, angle, radius, 1);
			if (pieceState.permissableRowColumn(anRC)) {
				ret.add(anRC);
			}
		}
		return ret;
	}

	private GamePoint XYARItoXY(int x, int y, double angle, double radius, int i) {
		double radians = (double) (angle / 360.0d * 2.0d * Math.PI);
		int col = x + i * (int) (Math.round(radius * Math.cos(radians)));
		int row = y - i * (int) (Math.round(radius * Math.sin(radians)));
		GamePoint ret = new GamePoint(col, row);
		return ret;
	}

	/**
	 * @param theFacing
	 */
	public void setFacing(float theFacing) {
		// normalize to 0, 360 degrees
		while (theFacing >= 360.0f)
			theFacing -= 360.0f;
		while (theFacing < 0.0f)
			theFacing += 360.0f;
		facing = theFacing;
	}

	/**
	 * @param c
	 */
	public void setPieceColor(String c) {
		if (c.equals("blue")) {
			setPieceColor1(new Color(10, 10, 140));
			setPieceColor2(new Color(63, 63, 255));
		} else if (c.equals("green")) {
			setPieceColor1(new Color(24, 46, 2));
			setPieceColor2(new Color(120, 232, 12));
		} else if (c.equals("orange")) {
			setPieceColor1(new Color(145, 65, 0));
			setPieceColor2(new Color(255, 147, 12));
		} else if (c.equals("purple")) {
			setPieceColor1(new Color(25, 5, 25));
			setPieceColor2(new Color(250, 50, 250));
		} else if (c.equals("red")) {
			setPieceColor1(new Color(25, 0, 0));
			setPieceColor2(new Color(250, 0, 0));
		} else if (c.equals("yellow")) {
			setPieceColor1(new Color(200, 200, 0));
			setPieceColor2(new Color(255, 255, 0));
		} else {
			// throw exception
		}
	}

	/**
	 * @param c
	 */
	public void setPieceColor1(Color c) {
		color1 = c;
	}

	/**
	 * @param c
	 */
	public void setPieceColor2(Color c) {
		color2 = c;
	}

	/**
	 * @param theType
	 */
	public void setPieceType(int theType) {
		pieceType = theType;
	}
	
	/**
	 * @return int
	 */
	public int getPieceType() {
		return pieceType;
	}

	public PloyPieceState getPieceState() {
		return pieceState;
	}

	public void setPieceState(PloyPieceState pieceState) {
		this.pieceState = pieceState;
	}
	
	public boolean isRotating() {
		return rotating;
	}
	
	public void startRotating() {
		rotating = true;
	}
	
	public void stopRotating() {
		rotating = false;
	}
	
	/**
	 * Return a game value for this piece type
	 * 
	 * @return int
	 */
	public int getValue() {
		int ret = getValue(pieceType);
		return ret;
	}
	
	/**
	 * Return a game value for this piece type
	 * 
	 * @param pieceType int
	 * @return int
	 */
	public static int getValue(int pieceType) {
		int ret = 0;
		if (pieceType == PloyPieceType.PIECE_1_00) {
			ret = 1;
		} else if (pieceType == PloyPieceType.PIECE_2_45) {
			ret = 4;
		} else if (pieceType == PloyPieceType.PIECE_2_90) {
			ret = 4;
		} else if (pieceType == PloyPieceType.PIECE_2_180) {
			ret = 4;
		} else if (pieceType == PloyPieceType.PIECE_3_45) {
			ret = 9;
		} else if (pieceType == PloyPieceType.PIECE_3_90) {
			ret = 9;
		} else if (pieceType == PloyPieceType.PIECE_3_120) {
			ret = 9;
		} else if (pieceType == PloyPieceType.PIECE_4_90) {
			ret = 1000;
		}
		return ret;
	}
}