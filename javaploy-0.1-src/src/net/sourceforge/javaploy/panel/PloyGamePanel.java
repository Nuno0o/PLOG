/*
PloyGamePanel.java
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
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;

import net.sourceforge.javaploy.math.GamePoint;
import net.sourceforge.javaploy.piece.PloyPiece;
import net.sourceforge.javaploy.piece.PloyPieceState;
import net.sourceforge.javaploy.piece.PloyPieceType;
import net.sourceforge.javaploy.player.PloyPlayer;
import net.sourceforge.javaploy.player.PloyPlayerColor;
import net.sourceforge.javaploy.player.PloyPlayerState;
import net.sourceforge.javaploy.rules.PloyController;

/**
 * A panel which encapsulates the board, the angle panel, and labels.
 * 
 * @author Jeff Conrad
 */
public class PloyGamePanel extends Panel {

	private PloyBoardPanel ployBoard;

	private PloyPiece selectedPiece = null;

	private PloyController ployController;

	private Label headingLabel;

	private PloyAnglePanel headingPanel;
	
	private PloyPieceState pieceState;
	
	private PloyPlayerState ployPlayerState;

	/**
	 * @param theLeft
	 * @param theTop
	 * @param theWidth
	 * @param theHeight
	 * @param thePloyBoard
	 * @param theNRows
	 * @param theNCols
	 */
	public PloyGamePanel(int theLeft, int theTop, int theWidth, int theHeight,
			PloyBoardPanel thePloyBoard, int theNRows, int theNCols) {
		ployBoard = thePloyBoard;

		setLocation(new Point(theLeft, theTop));
		setVisible(true);
		setSize(new java.awt.Dimension(theWidth, theHeight));
		setBackground(null);
	}
	
	/**
	 * @param g
	 */
	public void paint(Graphics g) {
		Iterator pieceIterator = pieceState.pieceIterator();
		while (pieceIterator.hasNext()){
			PloyPiece piece = (PloyPiece)pieceIterator.next();
			int row = piece.getRow();
			int col = piece.getCol();
			Rectangle r = ployBoard.RowColumnToRectangle(row, col);
			piece.paintPiece(g, r);
		}
	}

	/**
	 * @param evt
	 * @param x
	 * @param y
	 * @return boolean
	 */
	public boolean mouseDown(Event evt, int x, int y) {
		if (ployController.isVictory())
			return false;
		if (selectedPiece != null) {
			PloyPiece atPiece = pieceState.getPieceAtXY(x, y);
			if ((atPiece != null) && (atPiece == selectedPiece)) {
				if (selectedPiece.isRotating()) {
					//
					// if rotating, set angle
					//
					float currentAngle = (float) headingPanel
							.getCurrentDegrees();
					if (currentAngle != selectedPiece.getFacing()) {
						float previousAngle = selectedPiece.getFacing();
						selectedPiece.setFacing(currentAngle);
						ployController.networkRotatePiece(selectedPiece,
								previousAngle, currentAngle);
						ployController.networkNextPlayer();
					}
					hideHeadingPanel();
					ployBoard.repaint();
				} else {
					//
					// if not rotating, start rotating
					//
					int selectedPlayerIndex = selectedPiece.getPlayer();
					PloyPlayer selectedPlayer = ployPlayerState.getPloyPlayer(selectedPlayerIndex);
					String pieceColorString = selectedPlayer.getPieceColor();
					Color headingSelectColor = PloyPlayerColor.getColor1(pieceColorString);
					Color headingColor = PloyPlayerColor.getColor2(pieceColorString);

					selectedPiece.startRotating();
					headingPanel.setInitialDegrees(selectedPiece.getFacing());
					headingPanel.setHeadingColor(headingColor);
					headingPanel.setHeadingSelectColor(headingSelectColor);
					showHeadingPanel();
				}
				return true;
			} else if (selectedPiece.isRotating()) {
				//
				// if rotating, cancel rotating
				//
				hideHeadingPanel();
				return true;
			} else if ((atPiece != null)
					&& (atPiece.getTeam() == selectedPiece.getTeam())) {
				//
				// User tried to move a piece onto one of their teammates (or their own).
				//
				String s = "ILLEGAL:  Cannot move the " + PloyPieceType.getName(selectedPiece.getPieceType())
						+ " at (" + Integer.toString(selectedPiece.col) + ","
						+ Integer.toString(selectedPiece.row) + ") to ("
						+ Integer.toString(atPiece.col) + ","
						+ Integer.toString(atPiece.row)
						+ ") because it would remove the player-friendly "
						+ PloyPieceType.getName(atPiece.getPieceType()) + ".";
				ployController.localStatusAppend(s);
			} else {
				GamePoint endGamePoint = ployBoard.XYtoRowColumn(x, y);
				if (endGamePoint != null) {
					boolean b1 = selectedPiece.permissableMoveRC(endGamePoint);
					if (b1) {
						GamePoint startGamePoint = selectedPiece.getRowColumn();
						if (startGamePoint != null) {
							int i1 = pieceState.piecesBetweenRowColumns(startGamePoint, endGamePoint);
							if (i1 > 0) {
								String s = "ILLEGAL:  Cannot move the "
										+ PloyPieceType.getName(selectedPiece.getPieceType())
										+ " at ("
										+ Integer.toString(selectedPiece.col)
										+ ","
										+ Integer.toString(selectedPiece.row)
										+ ") to ("
										+ Integer.toString(endGamePoint.getCol())
										+ ","
										+ Integer.toString(endGamePoint.getRow())
										+ ") because there are interfering pieces along that path.";
								ployController.localStatusAppend(s);
							} else {
								ployController.networkMovePiece(selectedPiece,
										endGamePoint);
								selectedPiece = null;
								if (atPiece != null) {
									ployController.networkRemovePiece(atPiece);
								}
								ployController.networkRepaintBoard();
								ployController.networkNextPlayer();
								return true;
							}
						}
					} else {
						String s = "ILLEGAL:  Cannot move the "
								+ PloyPieceType.getName(selectedPiece.getPieceType()) + " at ("
								+ Integer.toString(selectedPiece.col) + ","
								+ Integer.toString(selectedPiece.row) + ") to ("
								+ Integer.toString(endGamePoint.getCol()) + ","
								+ Integer.toString(endGamePoint.getRow()) + ").";
						ployController.localStatusAppend(s);
					}
				}
			}
		} else {
			selectedPiece = pieceState.getPieceAtXY(x, y);
			if (selectedPiece != null) {
				selectedPiece = ployController.localSelectPiece(selectedPiece);
				return true;
			}
		}

		ployBoard.repaint();
		return false;
	}

	/**
	 * @param thePloyCommunicator
	 */
	public void setPloyController(PloyController thePloyCommunicator) {
		ployController = thePloyCommunicator;
	}

	public PloyPieceState getPieceState() {
		return pieceState;
	}

	public void setPieceState(PloyPieceState pieceState) {
		this.pieceState = pieceState;
	}
	
	private void hideHeadingPanel() {
		headingLabel.hide();
		headingPanel.hide();
		selectedPiece.deselect();
		selectedPiece.stopRotating();
		selectedPiece = null;
		ployBoard.repaint();
	}
	
	private void showHeadingPanel() {
		headingLabel.show();
		headingPanel.show();
	}

	public Label getHeadingLabel() {
		return headingLabel;
	}

	public void setHeadingLabel(Label headingLabel) {
		this.headingLabel = headingLabel;
	}

	public PloyAnglePanel getHeadingPanel() {
		return headingPanel;
	}

	public void setHeadingPanel(PloyAnglePanel headingPanel) {
		this.headingPanel = headingPanel;
	}

	public PloyPlayerState getPloyPlayerState() {
		return ployPlayerState;
	}

	public void setPlayerState(PloyPlayerState ployPlayerState) {
		this.ployPlayerState = ployPlayerState;
	}
}
