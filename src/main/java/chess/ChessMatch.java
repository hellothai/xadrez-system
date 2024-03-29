/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

/**
 *
 * @author thais
 */
public class ChessMatch {

    private Board board;
    private int turn;
    private Color currentPlayer;
    private List<Piece> pieceBoard = new ArrayList<>();
    private List<Piece> capturedPiece = new ArrayList<>();
    private boolean check;
    private boolean checkMate;

    public ChessMatch() {
        this.board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
    }

    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color) {
        List<Piece> king = pieceBoard.stream().filter(k -> ((ChessPiece) k).getColor() == color).collect(Collectors.toList());
        for (Piece p : king) {
            if (p instanceof King) {
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("There is no " + color + "king.");
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }

    public boolean[][] possibleMoves(ChessPosition sourceP) {
        Position pos = sourceP.toPosition();
        validateSourcePosition(pos);
        return board.piece(pos).possibleMoves();
    }

    /*
    Method convert chess position to matrix position and validate 
     */
    public ChessPiece performChessMove(ChessPosition source, ChessPosition target) {
        Position sourceP = source.toPosition();
        Position targetP = target.toPosition();
        validateSourcePosition(sourceP);
        validateTargetPosition(sourceP, targetP);
        Piece captured = makeMove(sourceP, targetP);
        if (testCheck(currentPlayer)) {
            undoMove(sourceP, targetP, captured);
            throw new ChessException("You cant put yourself in check.");
        }
        check = (testCheck(opponent(currentPlayer))) ? true : false;
        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }
        return (ChessPiece) captured;

    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on position.");
        }

        if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
            throw new ChessException("The piece is not yours.");
        }

        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible moves for the piece.");
        }

    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("The piece can't move to target.");
        }
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        pieceBoard.add(piece);

    }

    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPiece = pieceBoard.stream().filter(k -> ((ChessPiece) k).getColor() == opponent(color)).collect(Collectors.toList());
        for (Piece p : opponentPiece) {
            boolean[][] mat = p.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) {
            return false;
        }
        List<Piece> p = pieceBoard.stream().filter(k -> ((ChessPiece) k).getColor() == color).collect(Collectors.toList());
        for (Piece piece : p) {
            boolean[][] mat = piece.possibleMoves();
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece) piece).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece captured = makeMove(source, target);
                        boolean test = testCheck(color);
                        undoMove(source, target, captured);
                        if (!check) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void initialSetup() {
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));        
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
    }

    /*
    Method removes piece on source position and removes possible piece in target position and insert piece on target
     */
    private Piece makeMove(Position sourceP, Position targetP) {
        ChessPiece p = (ChessPiece) board.removePiece(sourceP);
        p.increaseMove();
        Piece captured = board.removePiece(targetP);
        if (captured != null) {
            pieceBoard.remove(captured);
            capturedPiece.add(captured);
        }
        board.placePiece(p, targetP);
        return captured;
    }

    private void undoMove(Position source, Position target, Piece captured) {
        ChessPiece p = (ChessPiece) board.removePiece(target);
        p.descreaseMove();
        board.placePiece(p, source);

        if (captured != null) {
            board.placePiece(captured, target);
            pieceBoard.add(captured);
            capturedPiece.remove(captured);

        }

    }
}
