import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Game {
    private int numberOfStages = 1;
    private boolean playerWin = true;
    private String pokemonsType = "";
    private URL url = null;
    private int addDamageEveryRound = 5;
    private Account account;

    Player player = new Player();
    Player maquina = new Player();
    PlayersRanking ranking = new PlayersRanking();
    FileManager fileManager = new FileManager();
    PokemonTypes pokemonTypes = new PokemonTypes();

    public void loadAccount(Account account){
        this.account = account;
    }

    public void startGame(){
        try {
            this.url = pokemonTypes.chooseType();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        player.getPokemons(url);

        round();
    }

    public void loadGame(){
        BufferedReader reader;
        int countLines = 0;

        try {
            reader = new BufferedReader(new FileReader("game.txt"));
            String line = reader.readLine();

            while (line != null) {
                countLines++;

                switch (countLines){
                    case 1:
                        this.url = new URL (line);
                        break;
                    case 2:
                        this.numberOfStages = Integer.parseInt(line);
                        break;
                    case 3:
                        player.setUpgrade(Integer.parseInt(line));
                        break;
                    case 4:
                        player.setAskNewDeck(Integer.parseInt(line));
                        break;

                    default:
                        if(countLines <=12){
                            player.loadPlayerArrays(line,countLines);
                        }
                }

                line = reader.readLine();
            }

            player.resetCount();
            reader.close();
            round();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void round(){
        while(this.playerWin){
            System.out.println("|STAGE " + this.numberOfStages + "|");

            System.out.print("*---------------------*");
            System.out.print("\n1: Upgrade Card 🔝");
            System.out.print("\n2: Ask New Card 🃏");
            System.out.print("\n3: Go Battle ⚔");
            System.out.print("\n4: See My Cards ♣");
            System.out.print("\n5: Quit Game 🚪");
            System.out.print("\n6: Save Game 📂");
            System.out.print("\n7: Ranking 🥇");
            System.out.print("\n*---------------------*");

            System.out.println();

            Scanner myObj = new Scanner(System.in);
            String userSelect = myObj.nextLine();

            switch(userSelect) {
                case "1":
                    player.upgradePokemon();
                    break;
                case "2":
                    player.getNewCard(this.url);
                    break;
                case "3":
                    battle();
                    break;
                case "4":
                    player.showDeck();
                    break;
                case "5":
                    System.exit(0);
                    break;
                case "6":
                    fileManager.saveGame(player,this.url,this.numberOfStages);
                    break;
                case "7":
                    ranking.playersRanking(this.account.getId());
                    break;
                default:
                    System.out.println("Don't exist in Menu!");
            }

            if(!this.playerWin){
                account.addPlayerLose();

                System.out.println("Game Over! 🤭");
                System.exit(0);
            }
        }
    }


    public void battle(){
        maquina.getPokemons(this.url);

        int sumPlayer = player.getSumPokemonsPoints();
        int sumMaquina = (maquina.getSumPokemonsPoints() + (this.addDamageEveryRound * (this.numberOfStages - 1)));

        System.out.println("Player 👨");
        player.showDeck();
        System.out.println("Maquina 🤖");
        maquina.showDeck();

        System.out.println("Player - " + sumPlayer + " 🆚" + " Robot - " + sumMaquina);
        System.out.println();

        if(sumPlayer < sumMaquina){
            this.playerWin = false;
        }else{
            this.numberOfStages++;

            player.resetCount();
            player.getPokemons(this.url);
            player.resetUpgrade();
            player.resetAskNewDeck();

            maquina.resetCount();

            System.out.println("********** NEW ROUND **********");
            System.out.println("YOU WIN AND GOT NEW DECK 🥇");
            System.out.println("🤖 - Next time you will loose. I got extra ⬆5 points");
            player.showDeck();
            account.addPlayerWin();
        }
    }
}
