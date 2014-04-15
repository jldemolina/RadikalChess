package radikalchess.view.swing;

import radikalchess.model.*;
import radikalchess.model.Image;
import radikalchess.model.checkers.MoveChecker;
import radikalchess.model.checkers.PieceAttackRangeChecker;
import radikalchess.model.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the board panel of the game. Contains a board, and represents it visually.
 * Allows direct user interaction with the same
 *
 * @author Jose Luis Molina
 * @see radikalchess.view.swing.CellPanel
 */
public class BoardPanel extends JPanel {

    private CellPanel[][] cellPanels;
    private final Board board;
    private final Player playerA;
    private final Player playerB;

    public BoardPanel(Board board, Player playerA, Player playerB) {
        this.board = board;
        this.playerA = playerA;
        this.playerB = playerB;
        cellPanels = new CellPanel[board.getNumberOfRows()][board.getNumberOfCols()];
        this.setLayout(new GridLayout(board.getNumberOfRows(), board.getNumberOfCols()));
        this.setVisible(true);
        initializeBoard();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
    }

    private void initializeBoard() {
        fillCellPanels();
        placePieces();
    }

    private void fillCellPanels() {
        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfCols(); j++) {
                if ((j + i) % 2 == 0) {
                    cellPanels[i][j] = new CellPanel(board.getCells()[i][j], Color.BLACK);
                } else {
                    cellPanels[i][j] = new CellPanel(board.getCells()[i][j], Color.WHITE);
                }
                addCellPanelActionListener(cellPanels[i][j]);
                this.add(cellPanels[i][j]);
            }
        }
    }

    private void addCellPanelActionListener(final CellPanel cellPanel) {
        cellPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!cellPanel.hasAnyPiece()) {
                    movePressedPieceTo(cellPanel);
                } else {
                    killPiece(cellPanel);
                    checkCellPanel(cellPanel);
                }
            }
        });
    }

    private void checkCellPanel(CellPanel cellPanel) {
        if (cellPanel.isPressed()) {
            cellPanel.setPressed(false);
        } else {
            unCheckAllCellPanels();
            cellPanel.setPressed(true);
            System.out.println("\n>> RADIKALCHESS INFORMATION: NEW PIECE SELECTED <<");
            showKillablePiecesFor(cellPanel.getPiece());
            showThreats(cellPanel.getPiece());
            showAttackRange(cellPanel.getPiece());
        }
    }

    private void unCheckAllCellPanels() {
        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfCols(); j++) {
                cellPanels[i][j].setPressed(false);
            }
        }
    }

    private void movePressedPieceTo(CellPanel cellPanel) {
        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfCols(); j++) {
                if (cellPanels[i][j].hasAnyPiece()) {
                    if (cellPanels[i][j].isPressed()) {
                        Player player = (this.playerA == cellPanels[i][j].getPiece().getPlayer()) ? playerB : playerA;
                        if (MoveChecker.getInstance().isAValidMove(new Move
                                (new Position(i, j), new Position(cellPanel.getPosition().getRow(), cellPanel.getPosition().getCol())),
                                cellPanels[i][j].getPiece(), board)) {
                            if (!(cellPanels[i][j].getPiece() instanceof King)) {
                                if (isReducedEuclideanDistance(cellPanels[i][j].getPosition(), cellPanel.getPosition(), player)
                                        || PieceAttackRangeChecker.getInstance().mayThrearenTheKing(cellPanels[i][j].getPiece(), cellPanel.getPosition(), board)) {
                                    cellPanel.addPiece(cellPanels[i][j].getPiece());
                                    cellPanels[i][j].removePiece();
                                    cellPanels[i][j].setPressed(false);
                                    cellPanel.setPressed(false);
                                    board.getCells();
                                }
                            } else {
                                cellPanel.addPiece(cellPanels[i][j].getPiece());
                                cellPanels[i][j].removePiece();
                                cellPanels[i][j].setPressed(false);
                                cellPanel.setPressed(false);
                                board.getCells();
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    private void killPiece(CellPanel cellPanel) {
        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfCols(); j++) {
                if (cellPanels[i][j].hasAnyPiece()) {
                    if (cellPanels[i][j].isPressed()) {
                        if (MoveChecker.getInstance().isAValidKillerMove(new Move
                                (new Position(i, j), new Position(cellPanel.getPosition().getRow(), cellPanel.getPosition().getCol())),
                                cellPanels[i][j].getPiece(), board)) {
                            cellPanel.addPiece(cellPanels[i][j].getPiece());
                            cellPanels[i][j].removePiece();
                            cellPanels[i][j].setPressed(false);
                            cellPanel.setPressed(false);
                        }
                        return;
                    }
                }
            }
        }
    }

    private boolean isReducedEuclideanDistance(Position origin, Position destination, Player player) {
        return new Position(destination.getRow(), destination.getCol()).getEuclideanDistanceTo(board.searchKingPosition(player)) <
                new Position(origin.getRow(), origin.getCol()).getEuclideanDistanceTo(board.searchKingPosition(player));
    }

    private void placePieces() {
        cellPanels[0][0].addPiece(new King(playerA, new Image(new Bitmap("images/pieces/blueking.png"))));
        cellPanels[0][1].addPiece(new Queen(playerA, new Image(new Bitmap("images/pieces/bluequeen.png"))));
        cellPanels[0][2].addPiece(new Bishop(playerA, new Image(new Bitmap("images/pieces/bluebishop.png"))));
        cellPanels[0][3].addPiece(new Rook(playerA, new Image(new Bitmap("images/pieces/bluerook.png"))));

        cellPanels[1][0].addPiece(new Pawn(playerA, new Image(new Bitmap("images/pieces/bluepawn.png")), AllowedPawnMove.DOWN));
        cellPanels[1][1].addPiece(new Pawn(playerA, new Image(new Bitmap("images/pieces/bluepawn.png")), AllowedPawnMove.DOWN));
        cellPanels[1][2].addPiece(new Pawn(playerA, new Image(new Bitmap("images/pieces/bluepawn.png")), AllowedPawnMove.DOWN));
        cellPanels[1][3].addPiece(new Pawn(playerA, new Image(new Bitmap("images/pieces/bluepawn.png")), AllowedPawnMove.DOWN));

        cellPanels[4][0].addPiece(new Pawn(playerB, new Image(new Bitmap("images/pieces/lilacpawn.png")), AllowedPawnMove.UP));
        cellPanels[4][1].addPiece(new Pawn(playerB, new Image(new Bitmap("images/pieces/lilacpawn.png")), AllowedPawnMove.UP));
        cellPanels[4][2].addPiece(new Pawn(playerB, new Image(new Bitmap("images/pieces/lilacpawn.png")), AllowedPawnMove.UP));
        cellPanels[4][3].addPiece(new Pawn(playerB, new Image(new Bitmap("images/pieces/lilacpawn.png")), AllowedPawnMove.UP));

        cellPanels[5][0].addPiece(new Rook(playerB, new Image(new Bitmap("images/pieces/lilacrook.png"))));
        cellPanels[5][1].addPiece(new Bishop(playerB, new Image(new Bitmap("images/pieces/lilacbishop.png"))));
        cellPanels[5][2].addPiece(new Queen(playerB, new Image(new Bitmap("images/pieces/lilacqueen.png"))));
        cellPanels[5][3].addPiece(new King(playerB, new Image(new Bitmap("images/pieces/lilacking.png"))));
    }

    private void showKillablePiecesFor(Piece piece) {
        Piece[] killablePieces = PieceAttackRangeChecker.getInstance().getKillablePiecesFor(piece, board);
        if (killablePieces.length == 0) {
            System.out.println("* ATTACK STATUS: NOT POSSIBLE ATTACKS");
            return;
        }
        System.out.println("* ATTACK STATUS: THREATENING TO...");
        for (Piece killablePiece : killablePieces) {
            System.out.println("\t > Piece at position " + killablePiece.getPosition());
        }
    }

    private void showThreats(Piece piece) {
        if (PieceAttackRangeChecker.getInstance().isKillable(piece, board))
            System.out.println("* DEFENSE STATUS: THREATENED");
        else System.out.println("* DEFENSE STATUS: NOT THREATENED");

    }

    private void showAttackRange(Piece piece) {
        System.out.println("* ATTACK RANGE:");
        for (Position position : PieceAttackRangeChecker.getInstance().getAttackRangeFor(piece, board)) {
            if (board.getPieceAt(position) instanceof King)
                System.out.println("\t > Position: " + position + " > ADVERSARIAL KING");
            else System.out.println("\t > Position: " + position);
        }
    }

}
