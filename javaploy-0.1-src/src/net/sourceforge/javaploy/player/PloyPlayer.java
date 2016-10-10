/*
 PloyPlayer.java
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

/**
 * Class representing a game player.
 * 
 * @author Jeff Conrad
 */
public class PloyPlayer {

	private String pieceColor;

	private String name;

	private String type;

	private PloyPlayerTeam team;

	private int score;

	public PloyPlayer(String name, String type, PloyPlayerTeam team, String pieceColor) {
		this.name = name;
		this.type = type;
		this.team = team;
		this.pieceColor = pieceColor;
		this.score = 0;
	}

	public String getPieceColor() {
		return pieceColor;
	}

	public void setPieceColor(String pieceColor) {
		this.pieceColor = pieceColor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PloyPlayerTeam getTeam() {
		return team;
	}

	public void setTeam(PloyPlayerTeam team) {
		this.team = team;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}