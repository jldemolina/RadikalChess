package radikalchess.view.swing;

import radikalchess.ai.RadikalChessStatus;
import radikalchess.model.Move;
import radikalchess.model.Position;
import radikalchess.model.checkers.MovementRangeChecker;
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
 * @author Eduardo Mendoza García
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

    /**
     * Update the current view (repaint and revalidate)
     */
    public void update() {
        for (int i = 0; i < radikalChessStatus.getBoard().getNumberOfRows(); i++) {
            for (int j = 0; j < radikalChessStatus.getBoard().getNumberOfCols(); j++) {
                cellPanels[i][j].update();
            }
        }
        revalidate();
    }

    /**
     * Update the current view (repaint and revalidate) with an specific status
     *
     * @param radikalChessStatus
     */
    public void update(RadikalChessStatus radikalChessStatus) {
        this.radikalChessStatus = radikalChessStatus;
        for (int i = 0; i < this.radikalChessStatus.getBoard().getNumberOfRows(); i++) {
            for (int j = 0; j < this.radikalChessStatus.getBoard().getNumberOfCols(); j++) {
                cellPanels[i][j].removePiece();
            }
        }
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
                    System.out.println("\n\n" + radikalChessStatus.getCurrentPlayer().getName().toUpperCase() + "'s TURN");
                    movePressedPieceTo(cellPanel);
                    if (cellPanel.hasAnyPiece()) {
                        checkCellPanel(cellPanel);
                    }
                } else {
                    System.out.println("THE WINNER IS " + radikalChessStatus.getWinner().getName().toUpperCase());
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
                    if (cellPanels[i][j].isPressed()) {
                        if (isPermittedMove(new Move
                                (new Position(i, j), new Position(cellPanel.getPosition().getRow(), cellPanel.getPosition().getCol())))) {
                            radikalChessStatus.move(new Move(cellPanels[i][j].getPiece().getPosition(), cellPanel.getPosition()));
                            cellPanels[i][j].setPressed(false);
                            cellPanel.setPressed(false);
                            radikalChessStatus.getBoard().getCells();
                            this.update();
                        }
                        break;
                    }
                }
            }
        }
    }

    private void placePieces() {
        for (int i = 0; i < radikalChessStatus.getBoard().getNumberOfRows(); i++) {
            for (int j = 0; j < radikalChessStatus.getBoard().getNumberOfCols(); j++) {
                if (radikalChessStatus.getBoard().getCells()[i][j].getPiece() != null)
                    cellPanels[i][j].addPiece(radikalChessStatus.getBoard().getCells()[i][j].getPiece());
            }
        }
    }

    private boolean isPermittedMove(Move move) {
        for (Move permittedMove : radikalChessStatus.getPossibleMovements()) {
            if (permittedMove.equals(move)) return true;
        }
        return false;
    }

    private void showKillablePiecesFor(Piece piece) {
        Piece[] killablePieces = MovementRangeChecker.getInstance().getKillablePiecesFor(piece, radikalChessStatus.getBoard());
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
        if (MovementRangeChecker.getInstance().isKillable(piece, radikalChessStatus.getBoard()))
            System.out.println("* DEFENSE STATUS: THREATENED");
        else System.out.println("* DEFENSE STATUS: NOT THREATENED");

    }

    private void showAttackRange(Piece piece) {
        System.out.println("* ATTACK RANGE:");
        for (Position position : MovementRangeChecker.getInstance().getAttackRangeFor(piece, radikalChessStatus.getBoard())) {
            if (radikalChessStatus.getBoard().getPieceAt(position) instanceof King)
                System.out.println("\t > Position: " + position + " > ADVERSARIAL KING");
            else System.out.println("\t > Position: " + position);
        }
    }

    private void showPermittedPieceToMove() {
        System.out.println("PERMITTED MOVEMENTS:");
        for (Move permittedMove : radikalChessStatus.getPossibleMovements()) {
            System.out.println(permittedMove);
        }
    }

}
