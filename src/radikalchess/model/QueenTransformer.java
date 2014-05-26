package radikalchess.model;

import radikalchess.model.pieces.Piece;
import radikalchess.model.pieces.Queen;

public class QueenTransformer implements Transformer {
    private Queen playerAQueen;
    private Queen playerBQueen;

    public QueenTransformer(Queen playerAQueen, Queen PlayerBQueen) {
        this.playerAQueen = playerAQueen;
        this.playerBQueen = PlayerBQueen;
    }

    @Override
    public void transform(Piece piece, Board board) {
        board.setPieceAt(piece.getPosition(), new Queen(playerAQueen.getPlayer(), new Image(new Bitmap("images/pieces/lilacqueen.png"))));
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
