package radikalchess.model;

import radikalchess.model.pieces.Piece;
import radikalchess.model.pieces.Queen;

/**
 * A Queen Transformer is responsible for transforming a piece in a Queen.
 * It has a single public method that allows the impending transformation.
 *
 * @author Jose Luis Molina
 * @author Eduardo Mendoza García
 */
public class QueenTransformer implements Transformer {
    private Queen playerAQueen;
    private Queen playerBQueen;

    public QueenTransformer(Queen playerAQueen, Queen PlayerBQueen) {
        this.playerAQueen = playerAQueen;
        this.playerBQueen = PlayerBQueen;
    }

    /**
     * Transform a piece in a Queen
     *
     * @param piece The piece to transform
     * @param board The current board
     */
    @Override
    public void transform(Piece piece, Board board) {
        Queen queen;
        Position position = piece.getPosition();
        try {
            queen = (board.getPieceAt(position).getPlayer().equals(playerAQueen.getPlayer())) ?
                    (Queen) this.playerAQueen.clone() : (Queen) this.playerBQueen.clone();
            queen.setPosition(position);
            board.setPieceAt(position, queen);
        } catch (CloneNotSupportedException e) {

        }
    }
}
