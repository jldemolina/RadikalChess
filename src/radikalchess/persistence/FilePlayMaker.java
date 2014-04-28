package radikalchess.persistence;

import radikalchess.model.Play;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


public class FilePlayMaker implements PlayMaker {

    private final String file;

    public FilePlayMaker(String file) {
        this.file = file;
    }

    @Override
    public void save(Play play) {
        FileWriter writer;
        try {
            writer = new FileWriter(file, true);
            writer.write("\n" + play.getPlayer().getName() + "," + play.getNumberOfMovements());
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
