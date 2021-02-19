import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class FileManager {
    public void saveGame(Player player, URL url, int numberOfStages){
        String str = "";
        BufferedWriter writer = null;

        str = player.savePlayerInfo(url, numberOfStages);

        try {
            writer = new BufferedWriter(new FileWriter("game.txt"));
            writer.write(str);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
