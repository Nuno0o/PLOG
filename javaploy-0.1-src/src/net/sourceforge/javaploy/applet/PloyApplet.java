/*
PloyApplet.java
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

package net.sourceforge.javaploy.applet;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Label;
import java.awt.TextArea;

import net.sourceforge.javaploy.panel.PloyAnglePanel;
import net.sourceforge.javaploy.panel.PloyBoardPanel;
import net.sourceforge.javaploy.panel.PloyGamePanel;
import net.sourceforge.javaploy.panel.PloyPieceBoardPanel;
import net.sourceforge.javaploy.piece.PloyPieceState;
import net.sourceforge.javaploy.player.PloyPlayer;
import net.sourceforge.javaploy.player.PloyPlayerState;
import net.sourceforge.javaploy.player.PloyPlayerTeam;
import net.sourceforge.javaploy.rules.PloyController;
import net.sourceforge.javaploy.rules.PloySetup;

/**
 * The Ploy Applet creates the framework for the two-dimensional strategy game,
 * javaploy.
 * 
 * @author Jeff Conrad
 */
public class PloyApplet extends Applet {
	
	TextArea statusTextArea;

	boolean isStandalone = false;

	// Applet parameters
	int boardHeight;

	int boardWidth;

	int numPlayers;

	int numTeams;

	int nRows, nCols;

	int currentPlayer;

	int player1Score, player2Score, player3Score, p4ScoreInt = 0;

	Color bgColor, txColor;

	Color bdColor, lnColor, selColor;
	
	String playerColors[];

	String playerTypes[];

	String playerNames[];

	String teamNames[];

	Label nameLabels[];

	Label teamLabels[];

	PloyBoardPanel ployBoard;

	PloyGamePanel ployGamePanel;

	PloyPieceBoardPanel pieceBoards[];
	
	PloyPlayer ployPlayers[];

	PloyPlayerTeam ployTeams[];
	
	PloyPieceState ployPieceState;
	
	PloyPlayerState ployPlayerState;

	PloyController ployController;

	PloyAnglePanel headingPanel;
	
	PloySetup ploySetup;

	Label headingLabel;

	public PloyApplet() {
	}

	/**
	 * @return String the applet info string.  To see this, run in AppletView,
	 * select menu option "Applet:Info...".
	 */
	public String getAppletInfo() {
		return "Plot Applet (c) 2006 Jeff Conrad";
	}

	/**
	 * Return a parameter specified to the Applet from the HTML.
	 * 
	 * @param key
	 * @param def
	 * @return String the value of the specified applet parameter
	 */
	public String getParameter(String key, String def) {
		return isStandalone ? System.getProperty(key, def)
				: (getParameter(key) != null ? getParameter(key) : def);
	}

	/**
	 * The Applet function to expose the available parameters and their type.
	 * 
	 * @return <code>Array</code> of <code>Strings</code>
	 */
	public String[][] getParameterInfo() {
		String info[][] = { { "BoardHeight", "int", "" },
				{ "BoardWidth", "int", "" }, { "NCols", "int", "" },
				{ "NRows", "int", "" }, { "NumberPlayers", "int", "" },
				{ "NumberTeams", "int", "" }, { "PlayerNumber", "int", "" },

				{ "BgR", "String", "" }, { "BgG", "String", "" },
				{ "BgB", "String", "" },

				{ "TxR", "String", "" }, { "TxG", "String", "" },
				{ "TxB", "String", "" },

				{ "BdR", "String", "" }, { "BdG", "String", "" },
				{ "BdB", "String", "" },

				{ "LnR", "String", "" }, { "LnG", "String", "" },
				{ "LnB", "String", "" },

				{ "SelR", "String", "" }, { "SelG", "String", "" },
				{ "SelB", "String", "" },

				{ "P1Color", "String", "" }, { "P2Color", "String", "" },
				{ "P3Color", "String", "" }, { "P4Color", "String", "" },

				{ "P1Name", "String", "" }, { "P2Name", "String", "" },
				{ "P3Name", "String", "" }, { "P4Name", "String", "" },

				{ "P1Team", "String", "" }, { "P2Team", "String", "" },
				{ "P3Team", "String", "" }, { "P4Team", "String", "" },

				{ "P1Type", "String", "" }, { "P2Type", "String", "" },
				{ "P3Type", "String", "" }, { "P4Type", "String", "" }, };

		return info;
	}

	/**
	 * The Applet initialization function.
	 */
	public void init() {
		String bgR = "0";
		String bgG = "0";
		String bgB = "51";

		String txR = "144";
		String txG = "144";
		String txB = "204";

		String bdR = "75";
		String bdG = "57";
		String bdB = "98";

		String lnR = "0";
		String lnG = "0";
		String lnB = "0";

		String selR = "0";
		String selG = "0";
		String selB = "0";

		// game setup

		try {
			boardHeight = Integer.parseInt(this.getParameter("BoardHeight",
					"400"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			boardWidth = Integer.parseInt(this
					.getParameter("BoardWidth", "400"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			nRows = Integer.parseInt(this.getParameter("NRows", "9"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			nCols = Integer.parseInt(this.getParameter("NCols", "9"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			numPlayers = Integer.parseInt(this.getParameter("NumberPlayers",
//					"2"));
					"4"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
//			numTeams = Integer.parseInt(this.getParameter("NumberTeams", "2"));
			numTeams = Integer.parseInt(this.getParameter("NumberTeams", "4"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			currentPlayer = Integer.parseInt(this.getParameter("CurrentPlayer",
					"1"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// color - bg
		try {
			bgR = this.getParameter("BgR", "0");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			bgG = this.getParameter("BgG", "0");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			bgB = this.getParameter("BgB", "51");
		} catch (Exception e) {
			e.printStackTrace();
		}

		bgColor = new Color(Integer.parseInt(bgR), Integer.parseInt(bgG),
				Integer.parseInt(bgB));
		setBackground(bgColor);

		// color - text

		try {
			txR = this.getParameter("TxR", "144");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			txG = this.getParameter("TxG", "144");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			txB = this.getParameter("TxB", "204");
		} catch (Exception e) {
			e.printStackTrace();
		}

		txColor = new Color(Integer.parseInt(txR), Integer.parseInt(txG),
				Integer.parseInt(txB));
		setForeground(txColor);

		// bd

		try {
			bdR = this.getParameter("BdR", "75");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			bdG = this.getParameter("BdG", "57");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			bdB = this.getParameter("BdB", "98");
		} catch (Exception e) {
			e.printStackTrace();
		}

		bdColor = new Color(Integer.parseInt(bdR), Integer.parseInt(bdG),
				Integer.parseInt(bdB));

		// ln
		try {
			lnR = this.getParameter("LnR", "144");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			lnG = this.getParameter("LnG", "144");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			lnB = this.getParameter("LnB", "204");
		} catch (Exception e) {
			e.printStackTrace();
		}

		lnColor = new Color(Integer.parseInt(lnR), Integer.parseInt(lnG),
				Integer.parseInt(lnB));

		// bd

		try {
			selR = this.getParameter("SelR", "100");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			selG = this.getParameter("SelG", "100");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			selB = this.getParameter("SelB", "100");
		} catch (Exception e) {
			e.printStackTrace();
		}

		selColor = new Color(Integer.parseInt(selR), Integer.parseInt(selG),
				Integer.parseInt(selB));

		// ployPlayers

		playerColors = new String[4];
		try {
			playerColors[0] = this.getParameter("P1Color", "yellow");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			playerColors[1] = this.getParameter("P2Color", "blue");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			playerColors[2] = this.getParameter("P3Color", "orange");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			playerColors[3] = this.getParameter("P4Color", "green");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// playerNames
		playerNames = new String[4];
		try {
			playerNames[0] = this.getParameter("P1Name", "Player One");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			playerNames[1] = this.getParameter("P2Name", "Player Two");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			playerNames[2] = this.getParameter("P3Name", "Player Three");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			playerNames[3] = this.getParameter("P4Name", "Player Four");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// pTeamString

		teamNames = new String[4];
		try {
			teamNames[0] = this.getParameter("P1Team", "Team 1");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			teamNames[1] = this.getParameter("P2Team", "Team 2");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			teamNames[2] = this.getParameter("P3Team", "Team 3");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			teamNames[3] = this.getParameter("P4Team", "Team 4");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// pTypeString
		playerTypes = new String[4];
		try {
			playerTypes[0] = this.getParameter("P1Type", "human");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			playerTypes[1] = this.getParameter("P2Type", "human");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			playerTypes[2] = this.getParameter("P3Type", "human");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			playerTypes[3] = this.getParameter("P4Type", "human");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			initComponents();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws Exception
	 */
	public void initComponents() throws Exception {
		//
		// init view components
		//
		headingLabel = new Label();
		headingLabel.setText("Heading");
		headingLabel.setLocation(new java.awt.Point(10, 10));
		headingLabel.setVisible(false);
		headingLabel.setFont(new java.awt.Font("Dialog", 3, 12));
		headingLabel.setSize(new java.awt.Dimension(80, 20));
		
		headingPanel = new PloyAnglePanel(bdColor, lnColor, Color.white, Color.yellow, lnColor);
		headingPanel.setLocation(new java.awt.Point(10, 50));
		headingPanel.setVisible(false);
		headingPanel.setSize(new java.awt.Dimension(80, 80));
		
		statusTextArea = new TextArea();
		statusTextArea.setLocation(new java.awt.Point(100, 420));
		statusTextArea.setVisible(true);
		statusTextArea.setSize(new java.awt.Dimension(400, 80));
		statusTextArea.setForeground(bgColor);

		setLocation(new java.awt.Point(0, 0));
		setLayout(null);
		setSize(new java.awt.Dimension(650, 550));
		add(statusTextArea);
		add(headingPanel);
		add(headingLabel);

		try {
			ployBoard = new PloyBoardPanel(bdColor, lnColor, selColor, 100, 10,
					nRows, nCols, boardWidth, boardHeight);
			add(ployBoard);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// ploy players/teams

		try {
			nameLabels = new Label[4];
			teamLabels = new Label[4];
			pieceBoards = new PloyPieceBoardPanel[4];
			
			int initialOffsetY = 10;
			int textHeight = 10;
			int spacingY = 10;

			for (int i = 0; i < numPlayers; i++) {
				//
				// pName
				//
				nameLabels[i] = new Label();
				nameLabels[i].setLocation(new java.awt.Point(510, initialOffsetY + i * 130));
				nameLabels[i].setFont(new java.awt.Font("Dialog", 3, 12));
				nameLabels[i].setSize(new java.awt.Dimension(130, 20));
				add(nameLabels[i]);

				//
				// pTeam
				//
				teamLabels[i] = new Label();
				teamLabels[i].setLocation(new java.awt.Point(520,
						initialOffsetY + textHeight + spacingY + i * 130));
				teamLabels[i].setSize(new java.awt.Dimension(50, 20));
				add(teamLabels[i]);

				//
				// pPieceBoards
				//
				try {
					pieceBoards[i] = new PloyPieceBoardPanel(bgColor, selColor, 520,
							initialOffsetY + 2 * textHeight + 2 * spacingY + i * 130, 40, 40);
					pieceBoards[i].setPieceColor(playerColors[i]);
					add(pieceBoards[i]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//
		// init game components
		//
		ploySetup = new PloySetup(numPlayers, playerNames, playerColors, playerTypes, numTeams, teamNames);
		ployPlayers = ploySetup.getPloyPlayers();
		ployTeams = ploySetup.getPloyTeams();
		ployPieceState = new PloyPieceState(ploySetup, nRows, nCols);
		ployPlayerState = new PloyPlayerState(ployPlayers, ployTeams);
		ployPlayerState.setPieceState(ployPieceState);

		//
		// init panels
		//
		try {
			ployGamePanel = new PloyGamePanel(100, 10, boardWidth, boardHeight,
					ployBoard, nRows, nCols);
			if (ployBoard != null) {
				ployBoard.ployGamePanel = ployGamePanel;
			}
			ployGamePanel.setHeadingLabel(headingLabel);
			ployGamePanel.setHeadingPanel(headingPanel);
	
			ployController = new PloyController();
			ployController.setPloyBoard(ployBoard);
			ployController.setPloyGameSpace(ployGamePanel);
			ployController.setStatusTextArea(statusTextArea);
			ployController.setPlayerNameLabels(nameLabels);
			ployController.setTeamLabels(teamLabels);
			ployController.setCurrentPlayer(currentPlayer);
			ployController.setNumPlayers(numPlayers);
			ployController.setNumTeams(numTeams);
			ployController.setPlayerPiecePanels(pieceBoards);
			ployController.setPlayerState(ployPlayerState);

			ployGamePanel.setPloyController(ployController);
			ployGamePanel.setPieceState(ployPieceState);
			ployGamePanel.setPlayerState(ployPlayerState);
			
			ployController.networkStartGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 
		// set labels
		// 
		for (int player=0; player<numPlayers; player++) {
			nameLabels[player].setText(playerNames[player]);
			teamLabels[player].setText(teamNames[ployPlayerState.playerToTeam(player)]);
		}
	}

	/**
	 * The Applet start function.
	 */
	public void start() {
	}

	/**
	 * The Applet stop function.
	 */
	public void stop() {
	}

	/**
	 * The Applet destroy function.
	 */
	public void destroy() {
	}
}
