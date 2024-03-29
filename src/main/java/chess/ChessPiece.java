/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

/**
 *
 * @author thais
 */
public abstract class ChessPiece extends Piece {

    private Color color;
    private int moveCount;
    
    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    protected boolean isThereOpponentPiece(Position pos) {
        ChessPiece p = (ChessPiece) getBoard().piece(pos);
        
        return p !=null && p.getColor()!= color;
    }
    
    public ChessPosition getChessPosition(){
        return ChessPosition.fromPosition(position);
    }
    
    public int getMoveCount(){
        return moveCount;
    }

    public void increaseMove(){
        moveCount++;
    }
    
    public void descreaseMove(){
        moveCount--;
    }
}
