/*
PloyPieceType.java
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

/**
 * Class representing the types of standard game piece.
 * 
 * @author Jeff Conrad
 */
public class PloyPieceType {
	
	//
	// Scout
	//
	public static final int PIECE_1_00 = 1000;
	
	//
	// Probe
	//
	public static final int PIECE_2_45 = 2045;
	public static final int PIECE_2_90 = 2090;
	public static final int PIECE_2_180 = 2180;
	public static final int PIECE_2_270 = 2270;
	
	//
	// Lance
	//
	public static final int PIECE_3_45 = 3045;
	public static final int PIECE_3_90 = 3090;
	public static final int PIECE_3_120 = 3120;
	
	//
	// Commander
	//
	public static final int PIECE_4_90 = 4090;
	
	public static boolean isShield(int pieceType) {
		return (pieceType == PloyPieceType.PIECE_1_00);
	}
	
	public static boolean isShield(PloyPiece piece) {
		return isShield(piece.getPieceType());
	}
	
	public static boolean isProbe(int pieceType) {
		switch (pieceType) {
		case PloyPieceType.PIECE_2_90: // flow-through
		case PloyPieceType.PIECE_2_180: // flow-through
		case PloyPieceType.PIECE_2_270: // flow-through
			return true;
		default:
			return false;
		}
	}
	
	public static boolean isProbe(PloyPiece piece) {
		return isProbe(piece.getPieceType());
	}
	
	public static boolean isLance(int pieceType) {
		switch (pieceType) {
		case PloyPieceType.PIECE_3_45: // flow-through
		case PloyPieceType.PIECE_3_90: // flow-through
		case PloyPieceType.PIECE_3_120: // flow-through
			return true;
		default:
			return false;
		}
	}
	
	public static boolean isLance(PloyPiece piece) {
		return isLance(piece.getPieceType());
	}
	
	public static boolean isCommander(int pieceType) {
		return (pieceType == PloyPieceType.PIECE_4_90);
	}
	
	public static boolean isCommander(PloyPiece piece) {
		return isCommander(piece.getPieceType());
	}

	/**
	 * @return String the language translation for the piece name
	 *
	 */
	public static String getName(int pieceType) {
		// TODO: use property files
		String ret = "";
		if (pieceType == PloyPieceType.PIECE_1_00) {
			ret = "Shield";
		} else if (pieceType == PloyPieceType.PIECE_2_45) {
			ret = "Probe 45";
		} else if (pieceType == PloyPieceType.PIECE_2_90) {
			ret = "Probe 90";
		} else if (pieceType == PloyPieceType.PIECE_2_180) {
			ret = "Probe 180";
		} else if (pieceType == PloyPieceType.PIECE_3_45) {
			ret = "Lance 45";
		} else if (pieceType == PloyPieceType.PIECE_3_90) {
			ret = "Lance 90";
		} else if (pieceType == PloyPieceType.PIECE_3_120) {
			ret = "Lance 120";
		} else if (pieceType == PloyPieceType.PIECE_4_90) {
			ret = "Commander";
		}
		return ret;
	}
}