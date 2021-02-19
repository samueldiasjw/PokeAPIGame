import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Player {
    private String[] deck = new String[4];
    private int[] pokemonsPoints = new int[4];
    private int upgrade = 2;
    private int askNewDeck = 3;
    private int count = 0;

    public void setUpgrade(int upgrade) {
        this.upgrade = upgrade;
    }

    public void setAskNewDeck(int askNewDeck) {
        this.askNewDeck = askNewDeck;
    }

    public void setDeck(String[] deck) {
        this.deck = deck;
    }

    public void showDeck() {
        for(int i = 0; i < this.deck.length; i++){
            System.out.print("🃏 - " + deck[i] + "(points: " + pokemonsPoints[i] + ") ");
        }
        System.out.println();
        System.out.println();
    }


    //---------------------------------------


    public void getNewCard(URL url){
        if(askNewDeck > 0){
            System.out.println("*--------UPGRADE--------*");
            for(int i = 0; i < deck.length; i++){
                System.out.println((i + 1) + ": " + deck[i] + "(" + pokemonsPoints[i] + ")");
            }

            Scanner myObj = new Scanner(System.in);
            String playerChoose = myObj.nextLine();

            this.count = (Integer.parseInt(playerChoose) - 1);
            BufferedReader reader;
            HttpURLConnection con = null;
            String line;


            try {
                //Request setup
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);

                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                while ((line = reader.readLine()) != null) {
                    int start = line.indexOf(",\"pokemon\"");
                    line = line.substring(start + 1);


                    String findStr = "\"name\":";
                    int lastIndex = 0;


                    while(lastIndex != -1){
                        int random = (int) (Math.random() * 8);
                        boolean flag = true;

                        lastIndex = line.indexOf(findStr,lastIndex);

                        String pokemonName = line.substring(line.indexOf(findStr,lastIndex) + 8,line.indexOf(",\"url\":",lastIndex) - 1);

                        for(int i = 0; i < deck.length; i++){
                            if(deck[i].compareTo(pokemonName) == 0){
                                flag = false;
                            }
                        }


                        if(random == 1 && flag){
                            deck[this.count] = pokemonName;
                            getPokemonPoints(line.substring(line.indexOf("\"url\":\"",lastIndex) + 7,line.indexOf("},\"slot\":",lastIndex) - 1));

                            resetCount();
                            break;
                        }

                        if(lastIndex != -1){
                            lastIndex += findStr.length();
                        }
                    }
                }

                setDeck(deck);
                reader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                con.disconnect();
            }


            this.askNewDeck--;
            System.out.println("New Card granted ✅");
            System.out.println();
        }else{
            System.out.println("You can't use this more ❌");
            System.out.println();
        }
    }




    //---------------------------------------



    public void getNewDeck(URL url){
        if(askNewDeck > 0){
            count = 0;
            getPokemons(url);
            this.askNewDeck--;

            System.out.println("New deck granted ✅");
            System.out.println();
        }else{
            System.out.println("You can't use this more ❌");
            System.out.println();
        }
    }

    public int getSumPokemonsPoints() {
        int sum = 0;

        for(int i = 0; i < pokemonsPoints.length; i++){
            sum = sum + pokemonsPoints[i];
        }

        return sum;
    }

    public void resetCount() {
        this.count = 0;
    }

    public void resetUpgrade(){
        this.upgrade = 2;
    }

    public void resetAskNewDeck(){
        this.askNewDeck = 3;
    }

    public String savePlayerInfo(URL url, int stage) {
        String str = url.toString() + "\n" + stage + "\n" + this.upgrade + "\n" + this.askNewDeck + "\n";

        for(int i = 0; i < deck.length; i++){
            str = str + deck[i] + "\n";
            str = str + pokemonsPoints[i] + "\n";
        }

        return str;
    }

    public void loadPlayerArrays(String line, int numberLine){
        if(numberLine % 2 != 0){
            this.deck[count] = line;
        }else{
            this.pokemonsPoints[count] = Integer.parseInt(line);
            count++;
        }
    }

    public void upgradePokemon(){
        if(this.upgrade > 0){
            System.out.println("*--------UPGRADE--------*");
            for(int i = 0; i < deck.length; i++){
                System.out.println((i + 1) + ": " + deck[i] + "(" + pokemonsPoints[i] + ")");
            }

            Scanner myObj = new Scanner(System.in);
            String playerChoose = myObj.nextLine();

            double random = Math.random();
            int upOrDownGrade = (int) (Math.random() * 15);

            if(random < 0.5){
                pokemonsPoints[(Integer.parseInt(playerChoose) - 1)] = pokemonsPoints[(Integer.parseInt(playerChoose) - 1)] + upOrDownGrade;
                System.out.println("UPGRADE ⬆ - " + upOrDownGrade);
            }else{
                pokemonsPoints[(Integer.parseInt(playerChoose) - 1)] = pokemonsPoints[(Integer.parseInt(playerChoose) - 1)] - upOrDownGrade;
                System.out.println("UNLUCKY DOWNGRADE ⬇ - " + upOrDownGrade);
            }

            this.upgrade--;
        }else{
            System.out.println("You can't use this more ❌");
            System.out.println();
        }


    }

    public void getPokemonPoints(String link){
        URL url = null;
        HttpURLConnection con = null;

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            //Request setup
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            //Verificar o codigo
            int status = con.getResponseCode();

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));

                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }

                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                line = reader.readLine();
                int points = Integer.parseInt(line.substring(line.indexOf("base_experience") + 17,line.indexOf("\"forms") - 1));
                pokemonsPoints[count] = points;

                this.count++;
                reader.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }
    }

    public void getPokemons(URL url) {
        String[] deck = new String[4];
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        HttpURLConnection con = null;
        try {
            //Request setup
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            //Verificar o codigo
            int status = con.getResponseCode();

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));

                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }

                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                while ((line = reader.readLine()) != null) {
                    int start = line.indexOf(",\"pokemon\"");
                    line = line.substring(start + 1);


                    String findStr = "\"name\":";
                    int lastIndex = 0, count = 0;


                    while(lastIndex != -1){
                        int random = (int) (Math.random() * 4);

                        lastIndex = line.indexOf(findStr,lastIndex);

                        String pokemonName = line.substring(line.indexOf(findStr,lastIndex) + 8,line.indexOf(",\"url\":",lastIndex) - 1);

                        if(count < 4 && random == 1){
                            getPokemonPoints(line.substring(line.indexOf("\"url\":\"",lastIndex) + 7,line.indexOf("},\"slot\":",lastIndex) - 1));
                            deck[count] = pokemonName;
                            count++;
                        }

                        if(lastIndex != -1){
                            lastIndex += findStr.length();
                        }
                    }
                }

                setDeck(deck);
                reader.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }
    }
}
