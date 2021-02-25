/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import boardgame.Board;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import java.util.Scanner;

/**
 *
 * @author thais
 */
public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ChessMatch chess = new ChessMatch();

        while (true) {
            UI.printBoard(chess.getPieces());
            System.out.println("");
            System.out.println("Source: ");
            ChessPosition source = UI.readChessPosition(sc);

            System.out.println("");
            System.out.println("Target: ");
            ChessPosition target = UI.readChessPosition(sc);
            
            ChessPiece captured= chess.performChessMove(source, target);

        }
    }

}
