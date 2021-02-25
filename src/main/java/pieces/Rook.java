/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

/**
 *
 * @author thais
 */
public class Rook extends ChessPiece{
    
    public Rook(Board board, Color color) {
        super(board, color);
    }
    
    @Override
    public String toString(){
        return "R";
    }
}