/*
 PloyTeam.java
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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Simple class storing attributes about a team.
 * 
 * @author Jeff Conrad
 */
public class PloyPlayerTeam {
	ArrayList players;

	int teamIndex;

	public PloyPlayerTeam(int teamIndex) {
		players = new ArrayList();
		this.teamIndex = teamIndex;
	}

	public void addPlayer(PloyPlayer player) {
		players.add(player);
	}

	public Iterator playerIterator() {
		return players.iterator();
	}

	public String getName() {
		return "Team " + getTeamIndex();
	}

	public int getTeamIndex() {
		return teamIndex;
	}

	public void setTeamIndex(int team) {
		this.teamIndex = team;
	}
}