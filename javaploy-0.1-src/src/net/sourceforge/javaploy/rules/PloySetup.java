/*
 PloySetup.java
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

import net.sourceforge.javaploy.piece.PloyPiece;
import net.sourceforge.javaploy.piece.PloyPieceType;
import net.sourceforge.javaploy.player.PloyPlayer;
import net.sourceforge.javaploy.player.PloyPlayerTeam;

/**
 * Class which helps set up the Ploy game.
 * 
 * @author Jeff Conrad
 */
public class PloySetup {
	int numPlayers;

	int numTeams;

	PloyPiece[] pieces = null;

	PloyPlayerTeam[] ployTeams = null;

	PloyPlayer[] players = null;

	String[] playerNames = null;

	String[] playerColors = null;

	String[] playerTypes = null;

	public PloySetup(int numPlayers, String[] playerNames,
			String[] playerColors, String[] playerTypes, int numTeams,
			String[] teamNames) {
		this.numPlayers = numPlayers;
		this.numTeams = numTeams;
		this.players = new PloyPlayer[numPlayers];
		this.ployTeams = new PloyPlayerTeam[numTeams];
		this.playerNames = playerNames;
		this.playerTypes = playerTypes;
		this.playerColors = playerColors;

		setupTeams();

		setupPlayers();

		setupPieces();
	}

	private int teamForPlayer(int i, int numTeams) {
		int team = 0;
		switch (i) {
		case 0:
			team = 0;
			break;
		case 1:
			team = 1;
			break;
		case 2:
			if (numTeams > 2) {
				team = 2;
			} else {
				team = 0;
			}
			break;
		case 3:
			if (numTeams > 2) {
				team = 3;
			} else {
				team = 1;
			}
			break;
		}
		return team;
	}

	private void setupPlayers() {
		for (int i = 0; i < numPlayers; i++) {
			int team = teamForPlayer(i, numTeams);
			players[i] = new PloyPlayer(playerNames[i], playerTypes[i],
					ployTeams[team], playerColors[i]);
		}
	}

	private void setupTeams() {
		for (int i = 0; i < numTeams; i++) {
			PloyPlayerTeam team = new PloyPlayerTeam(i);
			ployTeams[i] = team;
		}
	}

	/**
	 * Prepare the array of pieces for the current number of players and teams.
	 */
	public PloyPiece[] setupPieces() {
		if (numPlayers == 2) {
			if (numTeams == 2) {
				setupPieces22();
			} else {
				// throw exception
			}
		} else if (numPlayers == 4) {
			if (numTeams == 2) {
				setupPieces24();
			} else if (numTeams == 4) {
				setupPieces44();
			} else {
				// throw exception
			}
		} else {
			// throw exception
		}
		return pieces;
	}

	private void setupPieces22() {
		int piecesPerPlayer = 15;
		pieces = new PloyPiece[numPlayers * piecesPerPlayer];
		int pieceCounter = 0;

		//
		// 2 teams, 2 players
		// i is the team and the player
		// j is the piece number
		//
		for (int i = 0; i < 2; i++) {
			for (int j = 1; j < 16; j++) {
				pieces[pieceCounter] = new PloyPiece();
				pieces[pieceCounter].setPlayer(i);
				pieces[pieceCounter].setTeam(i);
				pieces[pieceCounter].setPieceColor(players[i].getPieceColor());

				//
				// player 1
				//
				if (i == 0) {
					//
					// row 1
					//
					if (j == 1) {
						pieces[pieceCounter].setRowColumn(1, 2);
						pieces[pieceCounter].setFacing(180);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_90);
					} else if (j == 2) {
						pieces[pieceCounter].setRowColumn(1, 3);
						pieces[pieceCounter].setFacing(150);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_120);
					} else if (j == 3) {
						pieces[pieceCounter].setRowColumn(1, 4);
						pieces[pieceCounter].setFacing(225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_45);
					} else if (j == 4) {
						pieces[pieceCounter].setRowColumn(1, 5);
						pieces[pieceCounter].setFacing(45);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_4_90);
					} else if (j == 5) {
						pieces[pieceCounter].setRowColumn(1, 6);
						pieces[pieceCounter].setFacing(225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_45);
					} else if (j == 6) {
						pieces[pieceCounter].setRowColumn(1, 7);
						pieces[pieceCounter].setFacing(150);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_120);
					} else if (j == 7) {
						pieces[pieceCounter].setRowColumn(1, 8);
						pieces[pieceCounter].setFacing(180);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_90);
					}

					//
					// row 2
					//
					else if (j == 8) {
						pieces[pieceCounter].setRowColumn(2, 3);
						pieces[pieceCounter].setFacing(270);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_45);
					} else if (j == 9) {
						pieces[pieceCounter].setRowColumn(2, 4);
						pieces[pieceCounter].setFacing(225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_90);
					} else if (j == 10) {
						pieces[pieceCounter].setRowColumn(2, 5);
						pieces[pieceCounter].setFacing(90);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_180);
					} else if (j == 11) {
						pieces[pieceCounter].setRowColumn(2, 6);
						pieces[pieceCounter].setFacing(225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_90);
					} else if (j == 12) {
						pieces[pieceCounter].setRowColumn(2, 7);
						pieces[pieceCounter].setFacing(225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_45);
					}

					//
					// row 3
					//
					else if (j == 13) {
						pieces[pieceCounter].setRowColumn(3, 4);
						pieces[pieceCounter].setFacing(270);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					} else if (j == 14) {
						pieces[pieceCounter].setRowColumn(3, 5);
						pieces[pieceCounter].setFacing(270);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					} else if (j == 15) {
						pieces[pieceCounter].setRowColumn(3, 6);
						pieces[pieceCounter].setFacing(270);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					}

				}
				//
				// Player 2
				//
				else if (i == 1) {
					int facing = 180;

					//
					// row 9
					//
					if (j == 1) {
						pieces[pieceCounter].setRowColumn(9, 2);
						pieces[pieceCounter].setFacing(facing + 180);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_90);
					} else if (j == 2) {
						pieces[pieceCounter].setRowColumn(9, 3);
						pieces[pieceCounter].setFacing(facing + 150);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_120);
					} else if (j == 3) {
						pieces[pieceCounter].setRowColumn(9, 4);
						pieces[pieceCounter].setFacing(facing + 225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_45);
					} else if (j == 4) {
						pieces[pieceCounter].setRowColumn(9, 5);
						pieces[pieceCounter].setFacing(facing + 45);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_4_90);
					} else if (j == 5) {
						pieces[pieceCounter].setRowColumn(9, 6);
						pieces[pieceCounter].setFacing(facing + 225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_45);
					} else if (j == 6) {
						pieces[pieceCounter].setRowColumn(9, 7);
						pieces[pieceCounter].setFacing(facing + 150);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_120);
					} else if (j == 7) {
						pieces[pieceCounter].setRowColumn(9, 8);
						pieces[pieceCounter].setFacing(facing + 180);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_90);
					}

					//
					// row 8
					//
					else if (j == 8) {
						pieces[pieceCounter].setRowColumn(8, 3);
						pieces[pieceCounter].setFacing(facing + 225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_45);
					} else if (j == 9) {
						pieces[pieceCounter].setRowColumn(8, 4);
						pieces[pieceCounter].setFacing(facing + 225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_90);
					} else if (j == 10) {
						pieces[pieceCounter].setRowColumn(8, 5);
						pieces[pieceCounter].setFacing(facing + 90);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_180);
					} else if (j == 11) {
						pieces[pieceCounter].setRowColumn(8, 6);
						pieces[pieceCounter].setFacing(facing + 225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_90);
					} else if (j == 12) {
						pieces[pieceCounter].setRowColumn(8, 7);
						pieces[pieceCounter].setFacing(facing + 270);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_45);
					}

					//
					// row 7
					//
					else if (j == 13) {
						pieces[pieceCounter].setRowColumn(7, 4);
						pieces[pieceCounter].setFacing(facing + 270);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					} else if (j == 14) {
						pieces[pieceCounter].setRowColumn(7, 5);
						pieces[pieceCounter].setFacing(facing + 270);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					} else if (j == 15) {
						pieces[pieceCounter].setRowColumn(7, 6);
						pieces[pieceCounter].setFacing(facing + 270);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					}
				}
				pieceCounter++;
			}
		}
	}

	private void setupPieces44() {
		int piecesPerPlayer = 9;
		pieces = new PloyPiece[numPlayers * piecesPerPlayer];
		int pieceCounter = 0;

		//
		// 4 teams, 4 players
		// i is the team
		// j is the piece number
		//
		for (int i = 0; i < 4; i++) {
			for (int j = 1; j < 10; j++) {
				pieces[pieceCounter] = new PloyPiece();
				pieces[pieceCounter].setPlayer(i);
				pieces[pieceCounter].setTeam(i);
				pieces[pieceCounter].setPieceColor(players[i].getPieceColor());
				if (i == 0) {
					// row 1
					if (j == 1) {
						pieces[pieceCounter].setRowColumn(1, 1);
						pieces[pieceCounter].setFacing(0);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_4_90);
					} else if (j == 2) {
						pieces[pieceCounter].setRowColumn(1, 2);
						pieces[pieceCounter].setFacing(270);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_45);
					} else if (j == 3) {
						pieces[pieceCounter].setRowColumn(1, 3);
						pieces[pieceCounter].setFacing(225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_45);
					}

					// row 2
					else if (j == 4) {
						pieces[pieceCounter].setRowColumn(2, 1);
						pieces[pieceCounter].setFacing(225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_90);
					} else if (j == 5) {
						pieces[pieceCounter].setRowColumn(2, 2);
						pieces[pieceCounter].setFacing(270);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_90);
					} else if (j == 6) {
						pieces[pieceCounter].setRowColumn(2, 3);
						pieces[pieceCounter].setFacing(315);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					}

					// row 3
					else if (j == 7) {
						pieces[pieceCounter].setRowColumn(3, 1);
						pieces[pieceCounter].setFacing(0);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_45);
					} else if (j == 8) {
						pieces[pieceCounter].setRowColumn(3, 2);
						pieces[pieceCounter].setFacing(315);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					} else if (j == 9) {
						pieces[pieceCounter].setRowColumn(3, 3);
						pieces[pieceCounter].setFacing(315);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					}
				} else if (i == 1) {
					// row 1
					if (j == 1) {
						pieces[pieceCounter].setRowColumn(1, 7);
						pieces[pieceCounter].setFacing(270);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_45);
					} else if (j == 2) {
						pieces[pieceCounter].setRowColumn(1, 8);
						pieces[pieceCounter].setFacing(135);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_90);
					} else if (j == 3) {
						pieces[pieceCounter].setRowColumn(1, 9);
						pieces[pieceCounter].setFacing(0);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_4_90);
					}

					// row 2
					else if (j == 4) {
						pieces[pieceCounter].setRowColumn(2, 7);
						pieces[pieceCounter].setFacing(225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					} else if (j == 5) {
						pieces[pieceCounter].setRowColumn(2, 8);
						pieces[pieceCounter].setFacing(180);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_90);
					} else if (j == 6) {
						pieces[pieceCounter].setRowColumn(2, 9);
						pieces[pieceCounter].setFacing(180);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_45);
					}

					// row 3
					else if (j == 7) {
						pieces[pieceCounter].setRowColumn(3, 7);
						pieces[pieceCounter].setFacing(225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					} else if (j == 8) {
						pieces[pieceCounter].setRowColumn(3, 8);
						pieces[pieceCounter].setFacing(225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					} else if (j == 9) {
						pieces[pieceCounter].setRowColumn(3, 9);
						pieces[pieceCounter].setFacing(135);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_45);
					}
				}

				else if (i == 2) {
					// row 7
					if (j == 1) {
						pieces[pieceCounter].setRowColumn(7, 1);
						pieces[pieceCounter].setFacing(315);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_45);
					} else if (j == 2) {
						pieces[pieceCounter].setRowColumn(7, 2);
						pieces[pieceCounter].setFacing(45);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					} else if (j == 3) {
						pieces[pieceCounter].setRowColumn(7, 3);
						pieces[pieceCounter].setFacing(45);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					}

					// row 8
					else if (j == 4) {
						pieces[pieceCounter].setRowColumn(8, 1);
						pieces[pieceCounter].setFacing(0);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_45);
					} else if (j == 5) {
						pieces[pieceCounter].setRowColumn(8, 2);
						pieces[pieceCounter].setFacing(0);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_90);
					} else if (j == 6) {
						pieces[pieceCounter].setRowColumn(8, 3);
						pieces[pieceCounter].setFacing(45);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					}

					// row 9
					else if (j == 7) {
						pieces[pieceCounter].setRowColumn(9, 1);
						pieces[pieceCounter].setFacing(0);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_4_90);
					} else if (j == 8) {
						pieces[pieceCounter].setRowColumn(9, 2);
						pieces[pieceCounter].setFacing(315);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_90);
					} else if (j == 9) {
						pieces[pieceCounter].setRowColumn(9, 3);
						pieces[pieceCounter].setFacing(90);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_45);
					}
				}

				else if (i == 3) {
					// row 7
					if (j == 1) {
						pieces[pieceCounter].setRowColumn(7, 7);
						pieces[pieceCounter].setFacing(135);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					} else if (j == 2) {
						pieces[pieceCounter].setRowColumn(7, 8);
						pieces[pieceCounter].setFacing(135);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					} else if (j == 3) {
						pieces[pieceCounter].setRowColumn(7, 9);
						pieces[pieceCounter].setFacing(180);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_45);
					}

					// row 8
					else if (j == 4) {
						pieces[pieceCounter].setRowColumn(8, 7);
						pieces[pieceCounter].setFacing(135);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					} else if (j == 5) {
						pieces[pieceCounter].setRowColumn(8, 8);
						pieces[pieceCounter].setFacing(90);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_90);
					} else if (j == 6) {
						pieces[pieceCounter].setRowColumn(8, 9);
						pieces[pieceCounter].setFacing(45);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_90);
					}

					// row 9
					else if (j == 7) {
						pieces[pieceCounter].setRowColumn(9, 7);
						pieces[pieceCounter].setFacing(45);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_45);
					} else if (j == 8) {
						pieces[pieceCounter].setRowColumn(9, 8);
						pieces[pieceCounter].setFacing(90);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_45);
					} else if (j == 9) {
						pieces[pieceCounter].setRowColumn(9, 9);
						pieces[pieceCounter].setFacing(0);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_4_90);
					}
				}

				pieceCounter++;
			}
		}
	}

	private void setupPieces24() {
		int piecesPerPlayer = 9;
		pieces = new PloyPiece[numPlayers * piecesPerPlayer];
		int pieceCounter = 0;

		//
		// 2 teams, 4 players
		// i is the player
		// j is the piece number
		//
		for (int i = 0; i < 4; i++) {
			for (int j = 1; j < 10; j++) {
				pieces[pieceCounter] = new PloyPiece();
				pieces[pieceCounter].setPlayer(i);
				pieces[pieceCounter].setPieceColor(players[i].getPieceColor());
				if (i == 0) {
					// equivalent to player A in ploy
					pieces[pieceCounter].setTeam(0);

					// row 1
					if (j == 1) {
						pieces[pieceCounter].setRowColumn(1, 2);
						pieces[pieceCounter].setFacing(180);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_90);
					} else if (j == 2) {
						pieces[pieceCounter].setRowColumn(1, 3);
						pieces[pieceCounter].setFacing(45);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_4_90);
					} else if (j == 3) {
						pieces[pieceCounter].setRowColumn(1, 4);
						pieces[pieceCounter].setFacing(225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_45);
					}

					// row 2
					else if (j == 4) {
						pieces[pieceCounter].setRowColumn(2, 2);
						pieces[pieceCounter].setFacing(270);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_45);
					} else if (j == 5) {
						pieces[pieceCounter].setRowColumn(2, 3);
						pieces[pieceCounter].setFacing(225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_90);
					} else if (j == 6) {
						pieces[pieceCounter].setRowColumn(2, 4);
						pieces[pieceCounter].setFacing(225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_45);
					}

					// row 3
					else if (j == 7) {
						pieces[pieceCounter].setRowColumn(3, 2);
						pieces[pieceCounter].setFacing(270);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					} else if (j == 8) {
						pieces[pieceCounter].setRowColumn(3, 3);
						pieces[pieceCounter].setFacing(270);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					} else if (j == 9) {
						pieces[pieceCounter].setRowColumn(3, 4);
						pieces[pieceCounter].setFacing(270);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					}

				} else if (i == 2) {
					pieces[pieceCounter].setTeam(0);

					// row 1
					if (j == 1) {
						pieces[pieceCounter].setRowColumn(1, 6);
						pieces[pieceCounter].setFacing(225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_45);
					} else if (j == 2) {
						pieces[pieceCounter].setRowColumn(1, 7);
						pieces[pieceCounter].setFacing(45);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_4_90);
					} else if (j == 3) {
						pieces[pieceCounter].setRowColumn(1, 8);
						pieces[pieceCounter].setFacing(180);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_90);
					}

					// row 2
					else if (j == 4) {
						pieces[pieceCounter].setRowColumn(2, 6);
						pieces[pieceCounter].setFacing(270);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_45);
					} else if (j == 5) {
						pieces[pieceCounter].setRowColumn(2, 7);
						pieces[pieceCounter].setFacing(225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_90);
					} else if (j == 6) {
						pieces[pieceCounter].setRowColumn(2, 8);
						pieces[pieceCounter].setFacing(225);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_45);
					}

					// row 3
					else if (j == 7) {
						pieces[pieceCounter].setRowColumn(3, 6);
						pieces[pieceCounter].setFacing(270);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					} else if (j == 8) {
						pieces[pieceCounter].setRowColumn(3, 7);
						pieces[pieceCounter].setFacing(270);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					} else if (j == 9) {
						pieces[pieceCounter].setRowColumn(3, 8);
						pieces[pieceCounter].setFacing(270);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					}
				}

				else if (i == 1) {
					pieces[pieceCounter].setTeam(1);

					// row 7
					if (j == 1) {
						pieces[pieceCounter].setRowColumn(7, 2);
						pieces[pieceCounter].setFacing(90);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					} else if (j == 2) {
						pieces[pieceCounter].setRowColumn(7, 3);
						pieces[pieceCounter].setFacing(90);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					} else if (j == 3) {
						pieces[pieceCounter].setRowColumn(7, 4);
						pieces[pieceCounter].setFacing(90);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					}

					// row 8
					else if (j == 4) {
						pieces[pieceCounter].setRowColumn(8, 2);
						pieces[pieceCounter].setFacing(45);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_45);
					} else if (j == 5) {
						pieces[pieceCounter].setRowColumn(8, 3);
						pieces[pieceCounter].setFacing(45);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_90);
					} else if (j == 6) {
						pieces[pieceCounter].setRowColumn(8, 4);
						pieces[pieceCounter].setFacing(90);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_45);
					}

					// row 9
					else if (j == 7) {
						pieces[pieceCounter].setRowColumn(9, 2);
						pieces[pieceCounter].setFacing(0);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_90);
					} else if (j == 8) {
						pieces[pieceCounter].setRowColumn(9, 3);
						pieces[pieceCounter].setFacing(45);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_4_90);
					} else if (j == 9) {
						pieces[pieceCounter].setRowColumn(9, 4);
						pieces[pieceCounter].setFacing(45);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_45);
					}
				}

				else if (i == 3) {
					pieces[pieceCounter].setTeam(1);

					// row 7
					if (j == 1) {
						pieces[pieceCounter].setRowColumn(7, 6);
						pieces[pieceCounter].setFacing(90);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					} else if (j == 2) {
						pieces[pieceCounter].setRowColumn(7, 7);
						pieces[pieceCounter].setFacing(90);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					} else if (j == 3) {
						pieces[pieceCounter].setRowColumn(7, 8);
						pieces[pieceCounter].setFacing(90);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_1_00);
					}

					// row 8
					else if (j == 4) {
						pieces[pieceCounter].setRowColumn(8, 6);
						pieces[pieceCounter].setFacing(45);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_45);
					} else if (j == 5) {
						pieces[pieceCounter].setRowColumn(8, 7);
						pieces[pieceCounter].setFacing(45);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_90);
					} else if (j == 6) {
						pieces[pieceCounter].setRowColumn(8, 8);
						pieces[pieceCounter].setFacing(90);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_2_45);
					}

					// row 9
					else if (j == 7) {
						pieces[pieceCounter].setRowColumn(9, 6);
						pieces[pieceCounter].setFacing(45);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_45);
					} else if (j == 8) {
						pieces[pieceCounter].setRowColumn(9, 7);
						pieces[pieceCounter].setFacing(45);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_4_90);
					} else if (j == 9) {
						pieces[pieceCounter].setRowColumn(9, 8);
						pieces[pieceCounter].setFacing(0);
						pieces[pieceCounter]
								.setPieceType(PloyPieceType.PIECE_3_90);
					}
				}

				pieceCounter++;
			}
		}
	}

	public PloyPlayer[] getPloyPlayers() {
		return players;
	}

	public PloyPiece[] getPieces() {
		return pieces;
	}

	public PloyPlayerTeam[] getPloyTeams() {
		return ployTeams;
	}

	public void setPloyTeams(PloyPlayerTeam[] ployTeams) {
		this.ployTeams = ployTeams;
	}
}