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
package net.sourceforge.javaploy.player;

import java.awt.Color;
import java.util.HashMap;

/**
 * Class representing the color mapping for ploy game pieces.
 * 
 * @author Jeff Conrad
 */
public final class PloyPlayerColor {
	
	private HashMap colorMap1;

	private HashMap colorMap2;

	private static PloyPlayerColor _singleton;

	private static final String BLUE = "blue";

	private static final String GREEN = "green";

	private static final String YELLOW = "yellow";

	private static final String ORANGE = "orange";

	private static final String PURPLE = "purple";

	private static final String RED = "red";

	public PloyPlayerColor() {
		super();
		colorMap1 = new HashMap();
		colorMap2 = new HashMap();

		colorMap1.put(BLUE, new Color(10, 10, 140));
		colorMap2.put(BLUE, new Color(63, 63, 255));

		colorMap1.put(GREEN, new Color(24, 46, 2));
		colorMap2.put(GREEN, new Color(120, 232, 12));

		colorMap1.put(ORANGE, new Color(145, 65, 0));
		colorMap2.put(ORANGE, new Color(255, 147, 12));

		colorMap1.put(PURPLE, new Color(25, 5, 25));
		colorMap2.put(PURPLE, new Color(250, 50, 250));

		colorMap1.put(RED, new Color(25, 0, 0));
		colorMap2.put(RED, new Color(250, 0, 0));

		colorMap1.put(YELLOW, new Color(200, 200, 0));
		colorMap2.put(YELLOW, new Color(255, 255, 0));
	}

	public static final PloyPlayerColor createInstance() {
		if (_singleton == null)
			_singleton = new PloyPlayerColor();
		return _singleton;
	}

	public Color getColorMap1(String colorName) {
		return (Color) (colorMap1.get(colorName));
	}

	public Color getColorMap2(String colorName) {
		return (Color) (colorMap2.get(colorName));
	}

	public static Color getColor1(String colorName) {
		return createInstance().getColorMap1(colorName);
	}

	public static Color getColor2(String colorName) {
		return createInstance().getColorMap2(colorName);
	}
}