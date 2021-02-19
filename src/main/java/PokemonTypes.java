import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class PokemonTypes {
    public URL chooseType() throws MalformedURLException {
        System.out.println("1 - Flying 🍃");
        System.out.println("2 - Bug 🐛");
        System.out.println("3 - Ghost 👻");
        System.out.println("4 - Fire 🔥");
        System.out.println("5 - Water 🌊");
        System.out.println("6 - Grass 🍃");
        System.out.println("Choose pokemon type:");

        Scanner myObj = new Scanner(System.in);
        String pokemonsType = myObj.nextLine();

        switch (pokemonsType){
            case "1":
                return new URL("https://pokeapi.co/api/v2/type/3");
            case "2":
                return new URL("https://pokeapi.co/api/v2/type/7");
            case "3":
                return new URL("https://pokeapi.co/api/v2/type/8");
            case "4":
                return new URL("https://pokeapi.co/api/v2/type/10");
            case "5":
                return new URL("https://pokeapi.co/api/v2/type/11");
            case "6":
                return new URL("https://pokeapi.co/api/v2/type/12");
            default:
                System.out.println("Don't exist in Menu!");
        }
        return new URL("https://pokeapi.co/api/v2/type/3");
    }
}
