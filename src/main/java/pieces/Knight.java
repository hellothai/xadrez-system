/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

/**
 *
 * @author thais
 */
public class Knight extends ChessPiece {

    public Knight(Board board, Color color) {
        super(board, color);
    }

    /*
    method verify color of piece and returns if it can be moved
     */
    public boolean canMove(Position pos) {
        ChessPiece p = (ChessPiece) getBoard().piece(pos);
        return p == null || p.getColor() != getColor();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position aux = new Position(0, 0);

        aux.setValues(position.getRow() - 1, position.getColumn() -2);
        if (getBoard().positionExists(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getColumn()] = true;
        }

        aux.setValues(position.getRow() -2, position.getColumn() -1);
        if (getBoard().positionExists(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getColumn()] = true;
        }

        aux.setValues(position.getRow() -2, position.getColumn() +1);
        if (getBoard().positionExists(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getColumn()] = true;
        }

        aux.setValues(position.getRow() -1, position.getColumn() + 2);
        if (getBoard().positionExists(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getColumn()] = true;
        }

        aux.setValues(position.getRow() +1, position.getColumn() +2);
        if (getBoard().positionExists(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getColumn()] = true;
        }

        aux.setValues(position.getRow() + 2, position.getColumn() + 1);
        if (getBoard().positionExists(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getColumn()] = true;
        }
        
        aux.setValues(position.getRow() + 2, position.getColumn() - 1);
        if (getBoard().positionExists(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getColumn()] = true;
        }

        aux.setValues(position.getRow() + 1, position.getColumn() -2);
        if (getBoard().positionExists(aux) && canMove(aux)) {
            mat[aux.getRow()][aux.getColumn()] = true;
        }
        return mat;
    }

    @Override
    public String toString() {
        return "N";
    }

}
