import java.util.Scanner;

public class GameMenus {
    private Account account;

    public void loadAccount(Account account){
        this.account = account;
    }
    public void welcome(Account account){
        Scanner myObj = new Scanner(System.in);

        System.out.print("*---------Welcome-------*");
        System.out.print("\n1: Login 🚪");
        System.out.print("\n2: Register 🆕");
        System.out.print("\n*---------------------*");
        System.out.println();

        String userSelect = myObj.nextLine();

        switch (userSelect){
            case "1":
                login(account);
                break;
            case "2":
                createAccount(account);
                break;
            default:
                System.out.println("Don't exist in Menu!");
        }
    }

    public void login(Account account){
        Scanner myObj = new Scanner(System.in);

        System.out.print("*--------LOGIN--------*\n");
        System.out.print("Username - ");
        String playerUsername = myObj.nextLine();
        System.out.print("Password - ");
        String playerPassowrd = myObj.nextLine();

        if(account.login(playerUsername,playerPassowrd)){
            loggedIn(account);
        }else{
            System.out.println("Error in Username or Password! Try Again 🛑");
            welcome(account);
        }
    }

    public void loggedIn(Account account){
        Game newGame = new Game();
        Scanner myObj = new Scanner(System.in);
        boolean repeat = true;

        while(repeat){
            System.out.print("\n*---------------------*");
            System.out.print("\n1: New Game 🆕");
            System.out.print("\n2: Load Game 📂");
            System.out.print("\n3: My Account 🤺");
            System.out.print("\n*---------------------*");
            System.out.println();

            String userSelect = myObj.nextLine();
            newGame.loadAccount(account);

            switch (userSelect){
                case "1":
                    repeat = false;
                    newGame.startGame();
                    break;
                case "2":
                    repeat = false;
                    newGame.loadGame();
                    break;
                case "3":
                    repeat = account.getAccount();
                    break;
                default:
                    System.out.println("Don't exist in Menu!");
            }
        }
    }

    public void createAccount(Account account){
        Scanner myObj = new Scanner(System.in);

        System.out.print("*-----CREATEACCOUNT-----*\n");
        System.out.print("Username - ");
        String playerUsername = myObj.nextLine();
        System.out.print("Password - ");
        String playerPassowrd = myObj.nextLine();

        account.register(playerUsername,playerPassowrd);

        welcome(account);
    }
}
