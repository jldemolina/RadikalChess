package radikalchess.view.swing;

import radikalchess.ai.RadikalChessStatus;
import radikalchess.model.Move;
import radikalchess.model.Player;
import radikalchess.model.Position;
import radikalchess.model.checkers.MoveChecker;
import radikalchess.model.checkers.PieceAttackRangeChecker;
import radikalchess.model.pieces.King;
import radikalchess.model.pieces.Piece;

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
    private RadikalChessStatus radikalChessStatus;

    public BoardPanel(RadikalChessStatus radikalChessStatus) {
        this.radikalChessStatus = radikalChessStatus;
        this.cellPanels = new CellPanel[radikalChessStatus.getBoard().getNumberOfRows()][radikalChessStatus.getBoard().getNumberOfCols()];
        this.setLayout(new GridLayout(radikalChessStatus.getBoard().getNumberOfRows(), radikalChessStatus.getBoard().getNumberOfCols()));
        this.setVisible(true);
        initializeBoard();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
    }

    public void update() {
        for (int i = 0; i < radikalChessStatus.getBoard().getNumberOfRows(); i++) {
            for (int j = 0; j < radikalChessStatus.getBoard().getNumberOfCols(); j++) {
                cellPanels[i][j].update();
            }
        }
        revalidate();
    }

    public void reset() {
        placePieces();
    }

    private void initializeBoard() {
        fillCellPanels();
        placePieces();
    }

    private void fillCellPanels() {
        for (int i = 0; i < radikalChessStatus.getBoard().getNumberOfRows(); i++) {
            for (int j = 0; j < radikalChessStatus.getBoard().getNumberOfCols(); j++) {
                if ((j + i) % 2 == 0) {
                    cellPanels[i][j] = new CellPanel(radikalChessStatus.getBoard().getCells()[i][j], Color.BLACK);
                } else {
                    cellPanels[i][j] = new CellPanel(radikalChessStatus.getBoard().getCells()[i][j], Color.WHITE);
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
                if (!radikalChessStatus.isTerminal()) {
                    if (!cellPanel.hasAnyPiece()) {
                        movePressedPieceTo(cellPanel);
                    } else {
                        killPiece(cellPanel);
                        checkCellPanel(cellPanel);
                    }
                } else {
                    System.out.println("THE WINNER IS" + radikalChessStatus.getWinner().getName().toUpperCase());
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
            showPermittedPieceToMove();
            showPermittedMovements();

            System.out.println("\n\n" + radikalChessStatus.getCurrentPlayer().getName().toUpperCase() + "'s TURN");

        }
    }

    private void unCheckAllCellPanels() {
        for (int i = 0; i < radikalChessStatus.getBoard().getNumberOfRows(); i++) {
            for (int j = 0; j < radikalChessStatus.getBoard().getNumberOfCols(); j++) {
                cellPanels[i][j].setPressed(false);
            }
        }
    }

    private void movePressedPieceTo(CellPanel cellPanel) {
        for (int i = 0; i < radikalChessStatus.getBoard().getNumberOfRows(); i++) {
            for (int j = 0; j < radikalChessStatus.getBoard().getNumberOfCols(); j++) {
                if (cellPanels[i][j].hasAnyPiece()) {
                    if (cellPanels[i][j].isPressed() && cellPanels[i][j].getPiece().getPlayer().equals(radikalChessStatus.getCurrentPlayer())) {
                        Player player = (this.radikalChessStatus.getPlayerA() == cellPanels[i][j].getPiece().getPlayer()) ? radikalChessStatus.getPlayerB() : radikalChessStatus.getPlayerA();
                        if (MoveChecker.getInstance().isAValidMove(new Move
                                        (new Position(i, j), new Position(cellPanel.getPosition().getRow(), cellPanel.getPosition().getCol())),
                                cellPanels[i][j].getPiece(), radikalChessStatus.getBoard()
                        )
                                && isPermittedPieceToMove(cellPanels[i][j].getPiece())) {
                            if (!(cellPanels[i][j].getPiece() instanceof King)) {
                                if (isReducedEuclideanDistance(cellPanels[i][j].getPosition(), cellPanel.getPosition(), player)
                                        || PieceAttackRangeChecker.getInstance().mayThreatenTheKing(cellPanels[i][j].getPiece(), cellPanel.getPosition(), radikalChessStatus.getBoard())) {
                                    cellPanel.addPiece(cellPanels[i][j].getPiece());
                                    cellPanels[i][j].removePiece();
                                    cellPanels[i][j].setPressed(false);
                                    cellPanel.setPressed(false);
                                    radikalChessStatus.getBoard().getCells();
                                    radikalChessStatus.alternatePlayer();
                                }
                            } else {
                                cellPanel.addPiece(cellPanels[i][j].getPiece());
                                cellPanels[i][j].removePiece();
                                cellPanels[i][j].setPressed(false);
                                cellPanel.setPressed(false);
                                radikalChessStatus.getBoard().getCells();
                                radikalChessStatus.alternatePlayer();
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    private void killPiece(CellPanel cellPanel) {
        for (int i = 0; i < radikalChessStatus.getBoard().getNumberOfRows(); i++) {
            for (int j = 0; j < radikalChessStatus.getBoard().getNumberOfCols(); j++) {
                if (cellPanels[i][j].hasAnyPiece()) {
                    if (cellPanels[i][j].isPressed() && cellPanels[i][j].getPiece().getPlayer().equals(radikalChessStatus.getCurrentPlayer())) {
                        if (MoveChecker.getInstance().isAValidKillerMove(new Move
                                        (new Position(i, j), new Position(cellPanel.getPosition().getRow(), cellPanel.getPosition().getCol())),
                                cellPanels[i][j].getPiece(), radikalChessStatus.getBoard()
                        )) {
                            cellPanel.addPiece(cellPanels[i][j].getPiece());
                            cellPanels[i][j].removePiece();
                            cellPanels[i][j].setPressed(false);
                            cellPanel.setPressed(false);
                            radikalChessStatus.alternatePlayer();
                        }
                        return;
                    }
                }
            }
        }
    }

    private boolean isReducedEuclideanDistance(Position origin, Position destination, Player player) {
        return new Position(destination.getRow(), destination.getCol()).getEuclideanDistanceTo(radikalChessStatus.getBoard().searchKingPosition(player)) <
                new Position(origin.getRow(), origin.getCol()).getEuclideanDistanceTo(radikalChessStatus.getBoard().searchKingPosition(player));
    }

    private void placePieces() {
        for (int i = 0; i < radikalChessStatus.getBoard().getNumberOfRows(); i++) {
            for (int j = 0; j < radikalChessStatus.getBoard().getNumberOfCols(); j++) {
                if (radikalChessStatus.getBoard().getCells()[i][j].getPiece() != null)
                    cellPanels[i][j].addPiece(radikalChessStatus.getBoard().getCells()[i][j].getPiece());
            }
        }
    }

    private boolean isPermittedPieceToMove(Piece piece) {
        for (Piece permittedPiece : radikalChessStatus.getPiecesForPermittedMoves()) {
            if (permittedPiece.equals(piece)) return true;
        }
        return false;
    }

    private void showKillablePiecesFor(Piece piece) {
        Piece[] killablePieces = PieceAttackRangeChecker.getInstance().getKillablePiecesFor(piece, radikalChessStatus.getBoard());
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
        if (PieceAttackRangeChecker.getInstance().isKillable(piece, radikalChessStatus.getBoard()))
            System.out.println("* DEFENSE STATUS: THREATENED");
        else System.out.println("* DEFENSE STATUS: NOT THREATENED");

    }

    private void showAttackRange(Piece piece) {
        System.out.println("* ATTACK RANGE:");
        for (Position position : PieceAttackRangeChecker.getInstance().getAttackRangeFor(piece, radikalChessStatus.getBoard())) {
            if (radikalChessStatus.getBoard().getPieceAt(position) instanceof King)
                System.out.println("\t > Position: " + position + " > ADVERSARIAL KING");
            else System.out.println("\t > Position: " + position);
        }
    }

    private void showPermittedPieceToMove() {
        System.out.println("PERMITTED MOVEMENTS:");
        for (Piece permittedPiece : radikalChessStatus.getPiecesForPermittedMoves()) {
            System.out.println(permittedPiece);
        }
    }

    private void showPermittedMovements() {
        System.out.println("PERMITTED MOVEMENTS:");
        for (Move move : radikalChessStatus.getPossibleMovements()) {
            System.out.println(move);
        }
    }

}
