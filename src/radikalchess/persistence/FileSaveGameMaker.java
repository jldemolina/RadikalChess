package radikalchess.persistence;

import radikalchess.ai.RadikalChessGame;
import radikalchess.ai.RadikalChessStatus;
import radikalchess.model.pieces.*;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class FileSaveGameMaker implements SaveGameMaker {

    private final String file;

    public FileSaveGameMaker(String file) {
        this.file = file;
    }

    @Override
    public void save(RadikalChessGame game) {
        Calendar calendar = new GregorianCalendar();
        FileWriter writer;
        RadikalChessStatus status = game.getActualStatus();
        Date date = new Date();
        try {
            writer = new FileWriter(file, true);
            writer.write("\nstatus," + status.getBoard().getNumberOfRows() + "," +
                    status.getBoard().getNumberOfCols() + "," + status.getPlayerA().getName() + "," + status.getPlayerB().getName()
                    + "," + status.getCurrentPlayer().getName());
            writePieces(status, writer);
            writer.write("\nendOfSaveGame," + new SimpleDateFormat("yyyy,MM,dd,HH,mm").format(new Date()));
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writePieces(RadikalChessStatus status, FileWriter writer) throws IOException {
        for (int i = 0; i < status.getBoard().getNumberOfRows(); i++) {
            for (int j = 0; j < status.getBoard().getNumberOfCols(); j++) {
                if (status.getBoard().getCells()[i][j].getPiece() != null) {
                    writer.write("\npiece," + i + "," + j + "," + getPieceAnalyzed(status.getBoard().getCells()[i][j].getPiece()));
                }
            }
        }
    }

    private String getPieceAnalyzed(Piece piece) {
        String pieceType = "";
        if (piece instanceof King) {
            pieceType += "king," + piece.getPlayer().getName() + "," + piece.getImage().getBitmap().getFile();
        } else if (piece instanceof Queen) {
            pieceType += "queen," + piece.getPlayer().getName() + "," + piece.getImage().getBitmap().getFile();
        } else if (piece instanceof Rook) {
            pieceType += "rook," + piece.getPlayer().getName() + "," + piece.getImage().getBitmap().getFile();
        } else if (piece instanceof Bishop) {
            pieceType += "bishop," + piece.getPlayer().getName() + "," + piece.getImage().getBitmap().getFile();
        } else {
            pieceType += "pawn," + piece.getPlayer().getName() + "," + piece.getImage().getBitmap().getFile() + ","
                    + ((Pawn) piece).getAllowedPawnMove();
        }
        return pieceType;
    }
}
