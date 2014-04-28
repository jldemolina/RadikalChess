package radikalchess.persistence;

import radikalchess.ai.RadikalChessGame;
import radikalchess.ai.RadikalChessStatus;
import radikalchess.model.*;
import radikalchess.model.pieces.*;

import java.io.*;
import java.util.Date;

public class FileSaveGameListLoader implements SaveGameListLoader {

    private final String file;

    public FileSaveGameListLoader(String file) {
        this.file = file;
    }

    @Override
    public void load() {
        RadikalChessStatus radikalChessStatus = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
            while (true) {
                String line = reader.readLine();
                if (line == null)
                    break;
                String[] game = line.trim().split(",");
                if (game[0].equals("status")) {
                    radikalChessStatus = new RadikalChessStatus(new Board(Integer.valueOf(game[1]), Integer.valueOf(game[2])), new Player(game[3]), new Player(game[4]));
                    radikalChessStatus.setCurrentPlayer(new Player(game[5]));
                } else if (game[0].equals("piece")) {
                    radikalChessStatus.getBoard().setPieceAt(new Position(Integer.valueOf(game[1]), Integer.valueOf(game[2])), getPieceAnalyzed(game));
                } else if (game[0].equals("endOfSaveGame"))
                    SaveGameList.getInstance().add(new SaveGame(new RadikalChessGame(radikalChessStatus),
                            new Date(Integer.valueOf(game[1]), Integer.valueOf(game[2]), Integer.valueOf(game[3]),
                                    Integer.valueOf(game[4]), Integer.valueOf(game[5]))
                    ));
            }
            reader.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
    }

    private Piece getPieceAnalyzed(String[] game) {
        if (game[3].equals("king"))
            return new King(new Player(game[4]), new Image(new Bitmap(game[5])));
        else if (game[3].equals("queen"))
            return new Queen(new Player(game[4]), new Image(new Bitmap(game[5])));
        else if (game[3].equals("rook"))
            return new Rook(new Player(game[4]), new Image(new Bitmap(game[5])));
        else if (game[3].equals("bishop"))
            return new Bishop(new Player(game[4]), new Image(new Bitmap(game[5])));

        return new Pawn(new Player(game[4]), new Image(new Bitmap(game[5])), getAllowedPawnMoveAnalyzed(game));
    }

    private AllowedPawnMove getAllowedPawnMoveAnalyzed(String[] game) {
        if (game[6].equals("UP"))
            return AllowedPawnMove.UP;
        return AllowedPawnMove.DOWN;
    }
}
