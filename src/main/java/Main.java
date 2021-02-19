public class Main {
    public static void main(String[] args) {
        Account account = new Account();
        GameMenus gameMenus = new GameMenus();

        gameMenus.loadAccount(account);
        gameMenus.welcome(account);
    }
}
