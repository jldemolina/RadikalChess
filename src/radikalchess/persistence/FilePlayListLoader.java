package radikalchess.persistence;

import radikalchess.model.Play;
import radikalchess.model.PlayList;
import radikalchess.model.Player;

import java.io.*;

public class FilePlayListLoader implements PlayListLoader {

    private final String file;

    public FilePlayListLoader(String file) {
        this.file = file;
    }

    @Override
    public void load() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
            while (true) {
                String line = reader.readLine();
                if (line == null)
                    break;
                if (!line.isEmpty()) {
                    String[] play = line.trim().split(",");
                    PlayList.getInstance().add(new Play(new Player(play[0]), Integer.valueOf(play[1])));
                }
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
    }

}
