package radikalchess.controller;

import radikalchess.ai.RadikalChessGame;
import radikalchess.ai.RadikalChessStatus;
import radikalchess.ai.heuristics.MediumHeuristic;
import radikalchess.ai.search.AlphaBetaSearch;
import radikalchess.model.*;
import radikalchess.model.pieces.*;
import radikalchess.persistence.FileSaveGameListLoader;
import radikalchess.persistence.FileSaveGameMaker;
import radikalchess.view.swing.ApplicationFrame;

/**
 * This class is responsible for the total control of the application. It connects the model and the view
 * (and, of course, the IA)
 *
 * @author Jose Luis Molina
 * @author Eduardo Mendoza García
 */
public class SwingApplicationController {
    private static final String saveGameFilePath = "game/savegames/savegames.txt";

    /**
     * Method that is responsible for implementing the entire application
     */
    public void execute() {
        Board board = new Board(6, 4);
        Player playerA = new Player("Player A");
        Player playerB = new Player("Player B");

        addPiecesToLittleBoard(board, playerA, playerB);
        // addPiecesToHugeBoard(board, playerA, playerB);

        RadikalChessStatus status = new RadikalChessStatus(board, playerA, playerB);
        status.getTransformers().add(new QueenTransformer(new Queen(playerA, new Image(new Bitmap("images/pieces/bluequeen.png"))), new Queen(playerB, new Image(new Bitmap("images/pieces/lilacqueen.png")))));
        System.out.println(status.getTransformers().size());

        RadikalChessGame game = new RadikalChessGame(status);
        game.setBlackPlayerHeuristic(new MediumHeuristic());
        game.setWhitePlayerHeuristic(new MediumHeuristic());
        game.setBlackPlayerSearch(new AlphaBetaSearch(game, 4));
        game.setWhitePlayerSearch(new AlphaBetaSearch(game, 4));

        new ApplicationFrame(game, new FileSaveGameMaker(saveGameFilePath),
                new FileSaveGameListLoader(saveGameFilePath));
    }

    /**
     * Add absolutely all the pieces to the board
     */
    private void addPiecesToLittleBoard(Board board, Player playerA, Player playerB) {
        board.setPieceAt(new Position(0, 0), new King(playerA, new Image(new Bitmap("images/pieces/blueking.png"))));
        board.setPieceAt(new Position(0, 1), new Queen(playerA, new Image(new Bitmap("images/pieces/bluequeen.png"))));
        board.setPieceAt(new Position(0, 2), new Bishop(playerA, new Image(new Bitmap("images/pieces/bluebishop.png"))));
        board.setPieceAt(new Position(0, 3), new Rook(playerA, new Image(new Bitmap("images/pieces/bluerook.png"))));

        board.setPieceAt(new Position(1, 0), new Pawn(playerA, new Image(new Bitmap("images/pieces/bluepawn.png")), AllowedPawnMove.DOWN));
        board.setPieceAt(new Position(1, 1), new Pawn(playerA, new Image(new Bitmap("images/pieces/bluepawn.png")), AllowedPawnMove.DOWN));
        board.setPieceAt(new Position(1, 2), new Pawn(playerA, new Image(new Bitmap("images/pieces/bluepawn.png")), AllowedPawnMove.DOWN));
        board.setPieceAt(new Position(1, 3), new Pawn(playerA, new Image(new Bitmap("images/pieces/bluepawn.png")), AllowedPawnMove.DOWN));

        board.setPieceAt(new Position(4, 0), new Pawn(playerB, new Image(new Bitmap("images/pieces/lilacpawn.png")), AllowedPawnMove.UP));
        board.setPieceAt(new Position(4, 1), new Pawn(playerB, new Image(new Bitmap("images/pieces/lilacpawn.png")), AllowedPawnMove.UP));
        board.setPieceAt(new Position(4, 2), new Pawn(playerB, new Image(new Bitmap("images/pieces/lilacpawn.png")), AllowedPawnMove.UP));
        board.setPieceAt(new Position(4, 3), new Pawn(playerB, new Image(new Bitmap("images/pieces/lilacpawn.png")), AllowedPawnMove.UP));

        board.setPieceAt(new Position(5, 0), new Rook(playerB, new Image(new Bitmap("images/pieces/lilacrook.png"))));
        board.setPieceAt(new Position(5, 1), new Bishop(playerB, new Image(new Bitmap("images/pieces/lilacbishop.png"))));
        board.setPieceAt(new Position(5, 2), new Queen(playerB, new Image(new Bitmap("images/pieces/lilacqueen.png"))));
        board.setPieceAt(new Position(5, 3), new King(playerB, new Image(new Bitmap("images/pieces/lilacking.png"))));
    }

    /**
     * Add absolutely all the pieces to the board
     */
    private void addPiecesToHugeBoard(Board board, Player playerA, Player playerB) {
        board.setPieceAt(new Position(0, 0), new King(playerA, new Image(new Bitmap("images/pieces/blueking.png"))));
        board.setPieceAt(new Position(0, 1), new Queen(playerA, new Image(new Bitmap("images/pieces/bluequeen.png"))));
        board.setPieceAt(new Position(0, 2), new Bishop(playerA, new Image(new Bitmap("images/pieces/bluebishop.png"))));
        board.setPieceAt(new Position(0, 3), new Rook(playerA, new Image(new Bitmap("images/pieces/bluerook.png"))));

        board.setPieceAt(new Position(1, 0), new Pawn(playerA, new Image(new Bitmap("images/pieces/bluepawn.png")), AllowedPawnMove.DOWN));
        board.setPieceAt(new Position(1, 1), new Pawn(playerA, new Image(new Bitmap("images/pieces/bluepawn.png")), AllowedPawnMove.DOWN));
        board.setPieceAt(new Position(1, 2), new Pawn(playerA, new Image(new Bitmap("images/pieces/bluepawn.png")), AllowedPawnMove.DOWN));
        board.setPieceAt(new Position(1, 3), new Pawn(playerA, new Image(new Bitmap("images/pieces/bluepawn.png")), AllowedPawnMove.DOWN));

        board.setPieceAt(new Position(5, 0), new Pawn(playerB, new Image(new Bitmap("images/pieces/lilacpawn.png")), AllowedPawnMove.UP));
        board.setPieceAt(new Position(5, 1), new Pawn(playerB, new Image(new Bitmap("images/pieces/lilacpawn.png")), AllowedPawnMove.UP));
        board.setPieceAt(new Position(5, 2), new Pawn(playerB, new Image(new Bitmap("images/pieces/lilacpawn.png")), AllowedPawnMove.UP));
        board.setPieceAt(new Position(5, 3), new Pawn(playerB, new Image(new Bitmap("images/pieces/lilacpawn.png")), AllowedPawnMove.UP));

        board.setPieceAt(new Position(6, 0), new Rook(playerB, new Image(new Bitmap("images/pieces/lilacrook.png"))));
        board.setPieceAt(new Position(6, 1), new Bishop(playerB, new Image(new Bitmap("images/pieces/lilacbishop.png"))));
        board.setPieceAt(new Position(6, 2), new Queen(playerB, new Image(new Bitmap("images/pieces/lilacqueen.png"))));
        board.setPieceAt(new Position(6, 3), new King(playerB, new Image(new Bitmap("images/pieces/lilacking.png"))));
    }

}
