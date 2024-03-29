/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import boardgame.Board;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author thais
 */
public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ChessMatch chess = new ChessMatch();
        List<ChessPiece> capt = new ArrayList<>();

        while (!chess.getCheckMate()) {
            try {
                UI.clearScreen();
                UI.printMatch(chess, capt);
                System.out.println("");
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(sc);
                
                boolean[][] possibleMoves = chess.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(chess.getPieces(), possibleMoves);

                System.out.println();
                System.out.print("Target: "); 
                ChessPosition target = UI.readChessPosition(sc);

                ChessPiece captured = chess.performChessMove(source, target);
                
                if(captured!= null){
                    capt.add(captured); 
                }

            } catch (ChessException ex) {
                System.out.println(ex.getMessage());
                sc.nextLine();
            } catch (InputMismatchException ex) {
                System.out.println(ex.getMessage());
                sc.nextLine();
            }

        }
        UI.clearScreen();
        UI.printMatch(chess, capt);
    }

}
