/*
 PloyController.java
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

package net.sourceforge.javaploy.rules;

import java.awt.Label;
import java.awt.TextArea;

import net.sourceforge.javaploy.math.GamePoint;
import net.sourceforge.javaploy.panel.PloyBoardPanel;
import net.sourceforge.javaploy.panel.PloyGamePanel;
import net.sourceforge.javaploy.panel.PloyPieceBoardPanel;
import net.sourceforge.javaploy.piece.PloyPiece;
import net.sourceforge.javaploy.piece.PloyPieceState;
import net.sourceforge.javaploy.piece.PloyPieceType;
import net.sourceforge.javaploy.player.PloyPlayer;
import net.sourceforge.javaploy.player.PloyPlayerState;
import net.sourceforge.javaploy.player.PloyPlayerTeam;

/**
 * This class assists with text description of the events during the game. It
 * also functions as a rules manager.
 * 
 * @author Jeff Conrad
 */
public class PloyController {
	
	private int currentPlayer = -1;

	private int numPlayers;

	private int numTeams;

	private Label playerNameLabels[];

	private Label teamLabels[];

	private PloyBoardPanel ployBoard;

	private PloyGamePanel ployGameSpace;

	private PloyPieceBoardPanel playerPiecePanels[];

	private TextArea statusTextArea;

	private PloyPieceState pieceState;
	
	private PloyPlayerState playerState;

	private boolean victory = false;

	public PloyController() {
	}

	/**
	 * Locally repaint the board
	 */
	public void localRepaintBoard() {
		ployBoard.repaint();
	}

	/**
	 * Local player has selected a piece.
	 * 
	 * @param thePiece
	 * @return PloyPiece - returns the piece if a valid selection, null
	 *         otherwise.
	 */
	public PloyPiece localSelectPiece(PloyPiece thePiece) {
		if (thePiece.getPlayer() == currentPlayer) {
			String s = getPlayerName(currentPlayer) + " selected the "
					+ PloyPieceType.getName(thePiece.getPieceType()) + " at ("
					+ Integer.toString(thePiece.col) + ","
					+ Integer.toString(thePiece.row) + ").";
			localStatusAppend(s);

			thePiece.select();
			localRepaintBoard();
			return thePiece;
		} else {
			String s = "ILLEGAL:  The "
					+ PloyPieceType.getName(thePiece.getPieceType()) + " at ("
					+ Integer.toString(thePiece.col) + ","
					+ Integer.toString(thePiece.row) + ") is not owned by "
					+ getPlayerName(currentPlayer) + ".";
			localStatusAppend(s);
			return null;
		}
	}

	/**
	 * Local message only, not sent to other players.
	 * 
	 * @param s
	 */
	public void localStatusAppend(String s) {
		// statusAppend("[local] " + s);
	}

	/**
	 * Another player, on the network has caused a piece to move.
	 * 
	 * @param thePiece
	 * @param rcPoint
	 */
	public void networkMovePiece(PloyPiece thePiece, GamePoint rcPoint) {
		String s = getPlayerName(currentPlayer) + " moved the "
				+ PloyPieceType.getName(thePiece.getPieceType()) + " at ("
				+ Integer.toString(thePiece.col) + ","
				+ Integer.toString(thePiece.row) + ") to ("
				+ Integer.toString(rcPoint.getCol()) + ","
				+ Integer.toString(rcPoint.getRow()) + ").";
		networkStatusAppend(s);

		thePiece.moveToRowColumn(rcPoint);
		thePiece.deselect();
	}

	/**
	 * Network request to go to next player.
	 */
	public void networkNextPlayer() {
		if (!victory) {
			//
			// Go to the next player
			//
			currentPlayer++;

			//
			// Rotate around the end to the top
			//
			if (currentPlayer >= numPlayers) {
				currentPlayer = 0;
			}

			//
			// Skip over dead players
			//
			while (playerState.isDeadPlayer(currentPlayer))
				currentPlayer++;

			//
			// Send new player message to network
			//
			networkSetPlayer(currentPlayer);
		}
	}

	/**
	 * Network request to update the board
	 */
	public void networkRepaintBoard() {
		ployBoard.repaint();
	}

	public String getPlayerName(int playerIndex) {
		return playerNameLabels[playerIndex].getText();
	}

	/**
	 * Network request to remove a piece from the board.
	 * 
	 * @param thePiece
	 */
	public void networkRemovePiece(PloyPiece thePiece) {
		String s = playerNameLabels[currentPlayer].getText() + " removed the "
				+ PloyPieceType.getName(thePiece.getPieceType()) + ", of "
				+ getPlayerName(thePiece.getPlayer()) + ", at ("
				+ Integer.toString(thePiece.col) + ","
				+ Integer.toString(thePiece.row) + ").";
		networkStatusAppend(s);

		//
		// Hiding the piece removes it from play.
		//
		thePiece.hide();

		// In the 4 player free for all, the loss of a commander will mean
		// captures
		// of the remaining pieces.
		networkAssessCapture(thePiece);

		// Different game setups correspond to different victory conditions.
		networkAssessVictory();

		networkRepaintBoard();
	}

	/**
	 * Network assess capture
	 * 
	 * @param thePiece
	 */
	public void networkAssessCapture(PloyPiece thePiece) {
		int capturedPlayer = thePiece.getPlayer();
		PloyPiece[] activePieces = ployGameSpace.getPieceState()
				.getActivePlayerPieces(capturedPlayer);
		if (activePieces.length == 0) {
			String s = playerNameLabels[capturedPlayer].getText()
					+ " has no more pieces and is out of the game.";
			networkStatusAppend(s);
		}
		if ((numPlayers == 4) && (numTeams == 4)
				&& (PloyPieceType.isCommander(thePiece.getPieceType()))) {
			int team = playerState.getPloyPlayer(currentPlayer).getTeam()
					.getTeamIndex();
			ployGameSpace.getPieceState().reassignPieces(capturedPlayer,
					currentPlayer, team);
			String s = playerNameLabels[currentPlayer].getText() + " captured "
					+ playerNameLabels[capturedPlayer].getText() + ".";
			networkStatusAppend(s);
		} else if ((numPlayers == 4) && (numTeams == 2)
				&& (PloyPieceType.isCommander(thePiece.getPieceType()))) {
			if (playerState.getPlayerScore(capturedPlayer) > 0) {
				String s = playerNameLabels[capturedPlayer].getText()
						+ " may continue to play as long as he has pieces on the"
						+ " board if his partner has a Commander and at least one"
						+ " other piece on the board.";
				networkStatusAppend(s);
			}
		}
	}

	public void networkAssessVictory() {
		//
		// Calculate player scores
		//
		PloyPlayer[] players = playerState.getPloyPlayers();
		int playerLength = players.length;
		int[] playerScore = new int[playerLength];
		for (int player = 0; player < playerLength; player++) {
			playerScore[player] = playerState.getPlayerScore(player);
			// String s = playerNameLabels[player].getText() + " score: "
			// + playerScore[player] + ".";
			// networkStatusAppend(s);
		}

		//
		// Calculate team scores
		//
		PloyPlayerTeam[] teams = playerState.getPloyTeams();
		int teamLength = teams.length;
		int[] teamScore = new int[teamLength];
		int teamsWithPositiveScore = 0;
		int teamWinner = -1;
		for (int team = 0; team < teamLength; team++) {
			teamScore[team] = 0;
			for (int player = 0; player < playerLength; player++) {
				if (players[player].getTeam() == teams[team]) {
					teamScore[team] += playerScore[player];
				}
			}
			// String s = teams[team].getName() + " score: "
			// + teamScore[team] + ".";
			// networkStatusAppesnd(s);
			if (teamScore[team] > 0) {
				teamsWithPositiveScore++;
				if (teamWinner == -1)
					teamWinner = team;
			}
		}

		//
		// if only one team has a positive score, they are the winner
		//
		if (teamsWithPositiveScore == 1) {
			String s = teams[teamWinner].getName()
					+ " has won the game, congratulations!";
			networkStatusAppend(s);
			setVictory(true);
		}
	}

	/**
	 * Network request to rotate a piece on the board.
	 * 
	 * @param thePiece
	 * @param previousAngle
	 * @param currentAngle
	 */
	public void networkRotatePiece(PloyPiece thePiece, float previousAngle,
			float currentAngle) {
		String s = playerNameLabels[currentPlayer].getText() + " rotated the "
				+ PloyPieceType.getName(thePiece.getPieceType()) + " at ("
				+ Integer.toString(thePiece.col) + ","
				+ Integer.toString(thePiece.row) + ") from a heading of "
				+ Float.toString(previousAngle) + " degrees to a heading of "
				+ Float.toString(currentAngle) + " degrees.";
		networkStatusAppend(s);

		thePiece.deselect();
	}

	/**
	 * @param thePlayer
	 */
	public void networkSetPlayer(int thePlayer) {
		//
		// Deselect the currently selected piece
		//
		for (int i = 0; i < this.numPlayers; i++) {
			playerPiecePanels[i].deselect();
		}

		//
		// To defend against strange numbers
		//
		if (thePlayer >= this.numPlayers) {
			thePlayer = 0;
		}
		if (thePlayer < 0) {
			thePlayer = this.numPlayers - 1;
		}
		currentPlayer = thePlayer;
		playerPiecePanels[currentPlayer].select();
		String s = "Current player: "
				+ playerNameLabels[currentPlayer].getText() + ".";
		networkStatusAppend(s);
	}

	/**
	 * Tell the network that this game is ready to start
	 */
	public void networkStartGame() {
		//
		// Randomly select the starting player.
		//
		int thePlayer = (int) Math.round(Math.random() * numPlayers);
		networkSetPlayer(thePlayer);
	}

	/**
	 * @param s
	 */
	public void networkStatusAppend(String s) {
		statusAppend("[network] " + s);
	}

	/**
	 * @param s
	 */
	public void statusAppend(String s) {
		statusTextArea.appendText(s + "\n");
	}

	/**
	 * Specify my ploy board.
	 * 
	 * @param thePloyBoard
	 */
	public void setPloyBoard(PloyBoardPanel thePloyBoard) {
		ployBoard = thePloyBoard;
	}

	/**
	 * Specify my game space.
	 * 
	 * @param thePloyGameSpace
	 */
	public void setPloyGameSpace(PloyGamePanel thePloyGameSpace) {
		ployGameSpace = thePloyGameSpace;
	}

	/**
	 * @param theStatusTextArea
	 */
	public void setStatusTextArea(TextArea theStatusTextArea) {
		statusTextArea = theStatusTextArea;
	}

	/**
	 * @param panels
	 */
	public void setPlayerPiecePanels(PloyPieceBoardPanel[] panels) {
		playerPiecePanels = panels;
	}

	/**
	 * @param labels
	 */
	public void setPlayerNameLabels(Label[] labels) {
		playerNameLabels = labels;
	}

	/**
	 * @param theCurrentPlayer
	 */
	public void setCurrentPlayer(int theCurrentPlayer) {
		currentPlayer = theCurrentPlayer;
	}

	/**
	 * @param theNumPlayers
	 */
	public void setNumPlayers(int theNumPlayers) {
		numPlayers = theNumPlayers;
	}

	public PloyPieceState getPieceState() {
		return pieceState;
	}

	public void setPieceState(PloyPieceState pieceState) {
		this.pieceState = pieceState;
	}

	public int getNumTeams() {
		return numTeams;
	}

	public void setNumTeams(int numTeams) {
		this.numTeams = numTeams;
	}

	public Label[] getTeamLabels() {
		return teamLabels;
	}

	public void setTeamLabels(Label[] playerTeamLabels) {
		this.teamLabels = playerTeamLabels;
	}

	public boolean isVictory() {
		return victory;
	}

	public void setVictory(boolean victory) {
		this.victory = victory;
	}

	public PloyPlayerState getPloyPlayerState() {
		return playerState;
	}

	public void setPlayerState(PloyPlayerState ployPlayerState) {
		this.playerState = ployPlayerState;
	}
}