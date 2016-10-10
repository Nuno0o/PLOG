/*
PloyPieceState.java
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

package net.sourceforge.javaploy.player;

import net.sourceforge.javaploy.piece.PloyPiece;
import net.sourceforge.javaploy.piece.PloyPieceState;

/**
 * Player state
 * 
 * @author Jeff Conrad
 */
public class PloyPlayerState {
	
	PloyPlayer ployPlayers[];

	PloyPlayerTeam ployTeams[];
	
	private PloyPieceState pieceState;
	
	public PloyPlayerState(PloyPlayer[] ployPlayers, PloyPlayerTeam[] ployTeams) {
		this.ployPlayers = ployPlayers;
		this.ployTeams = ployTeams;
	}
	
	/**
	 * If the players score is zero.
	 * 
	 * @param player
	 * @return true, if the score is zero
	 */
	public boolean isDeadPlayer(int player) {
		return getPlayerScore(player) == 0;
	}
	
	/**
	 * Return whether the given player is out of the game.
	 * 
	 * @param player
	 * @return boolean, whether the given player is out of the game.
	 */
	private boolean isPlayerOut(int player) {
		//
		// if the player has no pieces, they are out of the game
		//
		boolean hasCommander = getPieceState().playerHasCommander(player);
		boolean hasOtherThanCommander = getPieceState().playerHasPiecesOtherThanCommander(player);
		if (!(hasCommander || hasOtherThanCommander)) {
			return true;
		}

		//
		// otherwise, it is a function of the game setup.
		//
		int playersLength = ployPlayers.length;
		int teamsLength = ployTeams.length;
		if (((playersLength == 2) && (teamsLength == 2))
			|| ((playersLength == 4) && (teamsLength == 4))
		) {
			if (!(hasCommander && hasOtherThanCommander)) {
				return true;
			}
		}
		else if ((playersLength == 4) && (teamsLength == 2)) {
			if (!hasCommander) {
				int otherPlayer = otherPlayer(player);
				boolean otherHasCommander = getPieceState().playerHasCommander(otherPlayer);
				boolean otherHasOtherThanCommander = getPieceState().playerHasPiecesOtherThanCommander(otherPlayer);
				if (!(otherHasCommander && otherHasOtherThanCommander)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Computes the total current score for the provided player
	 * 
	 * @param player
	 * @return total current score for the provided player
	 */
	public int getPlayerScore(int player) {
		int ret = 0;
		if (!isPlayerOut(player)) {
			PloyPiece[] pieces = getPieceState().getPlayerPieces(player);
			int piecesLength = pieces.length;
			for (int i=0; i < piecesLength; i++) {
				if (pieces[i].isShown()) {
					ret += pieces[i].getValue();
				}
			}
		}
		return ret;
	}
	
	/**
	 * Computes the total current score for the provided team
	 * 
	 * @param team
	 * @return total current score for the provided team
	 */
	public int getTeamScore(PloyPlayerTeam team) {
		int ret = 0;
		int playerLength = getPloyPlayers().length;
		for (int player = 0; player < playerLength; player++) {
			if (getPloyPlayer(player).getTeam() == team) {
				ret += getPlayerScore(player);
			}
		}
		return ret;
	}

	/**
	 * For a two-player game, return the value of the other team index
	 * 
	 * @param player index
	 * @return the other player index
	 */
	public int otherPlayer(int player) {
		int ret = -1;
		switch (player) {
			case 0:
				ret = 2;
				break;
			case 1:
				ret = 3;
				break;
			case 2:
				ret = 0;
				break;
			case 3:
				ret = 1;
				break;
		}
		return ret;
	}
	
	/**
	 * For a two-player game, return the value of the other team index
	 * 
	 * @param team index
	 * @return the other team index
	 */
	public int otherTeam(int team) {
		if (team == 0)
			return 1;
		else
			return 0;
	}
	
	/**
	 * For a given player index, return the corresponding team index
	 * 
	 * @param player index
	 * @return team index
	 */
	public int playerToTeam(int player) {
		return ployPlayers[player].getTeam().getTeamIndex();
	}
	
	public PloyPlayerTeam[] getPloyTeams() {
		return ployTeams;
	}

	public void setPloyTeams(PloyPlayerTeam[] ployTeams) {
		this.ployTeams = ployTeams;
	}
	
	public PloyPlayer getPloyPlayer(int i) {
		return ployPlayers[i];
	}
	
	public PloyPlayer[] getPloyPlayers() {
		return ployPlayers;
	}

	public void setPloyPlayers(PloyPlayer[] ployPlayers) {
		this.ployPlayers = ployPlayers;
	}
	
	public PloyPieceState getPieceState() {
		return pieceState;
	}

	public void setPieceState(PloyPieceState pieceState) {
		this.pieceState = pieceState;
	}
}