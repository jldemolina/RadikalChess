package model;

import model.Pieces.Piece;

public class Move {

    private final Board board;
    private final Piece piece;
    private final Position origin;
    private final Position destination;

    public Move(Board board, Piece piece, Position origin, Position destination) {
        this.board = board;
        this.piece = piece;
        this.origin = origin;
        this.destination = destination;
    }

    public Position getOrigin() {
        return origin;
    }

    public Position getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Moving piece from " + origin.toString() + " to " + destination.toString();
    }


}
