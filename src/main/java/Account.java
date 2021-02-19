import java.sql.*;
import java.util.Scanner;

public class Account {
    private int id;
    private String username;
    private String password;
    private int wins;
    private int loses;

    public boolean login(String playerUsername, String playerPassword){
        String db_url = "jdbc:mysql://localhost:8889/game";
        String user = "root";
        String password = "root";
        int canLoggin = 0;

        try (Connection connection = DriverManager.getConnection(db_url, user, password)) {
            try (Statement statement = connection.createStatement()) {
                String query = "SELECT * FROM players where username='" + playerUsername + "' and user_password='" + playerPassword + "'";
                ResultSet resultSet = statement.executeQuery(query);

                while(resultSet.next()) {
                    this.id = resultSet.getInt("id");
                    this.username = playerUsername;
                    this.password = playerPassword;
                    this.wins = resultSet.getInt("wins");
                    this.loses = resultSet.getInt("loses");

                    canLoggin = 1;
                }

                if(canLoggin == 1){
                    System.out.println("\nAccess Granted ✅");
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean getAccount(){
        Scanner myObj = new Scanner(System.in);
        String secretPassword = "";

        for(int i = 0; i < this.password.length(); i++){
            secretPassword = secretPassword + "*";
        }

        System.out.println("Username - " + this.username + "\nPassword - " + secretPassword + "\nWins - " + this.wins + "\nLoses - " + this.loses);
        System.out.print("\n*---------------------*");
        System.out.print("\n1: Change Password");
        System.out.print("\n2: Back");
        System.out.print("\n*---------------------*");
        System.out.println();

        String userSelect = myObj.nextLine();

        switch (userSelect){
            case "1":
                changePassword(myObj);
                return true;
            case "2":
                return true;
            default:
                System.out.println("Don't exist in Menu!");
                return true;
        }
    }

    public void changePassword(Scanner myObj){
        System.out.println("Old Password - ");
        String oldPassword = myObj.nextLine();

        System.out.println("New Password - ");
        String newPassword = myObj.nextLine();

        System.out.println("Repeat New Password - ");
        String newPasswordRepeat = myObj.nextLine();

        if(oldPassword.compareTo(this.password) == 0){
            if(newPassword.compareTo(newPasswordRepeat) == 0){
                String db_url = "jdbc:mysql://localhost:8889/game";
                String user = "root";
                String password = "root";

                try (Connection connection = DriverManager.getConnection(db_url, user, password)) {
                    try (Statement statement = connection.createStatement()) {
                        String query = "UPDATE players SET user_password='" + newPassword + "' where id=" + this.id;
                        statement.executeUpdate(query);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }else{
                System.out.println();
                System.out.println("New Password don't Match! 🛑");
                System.out.println();

                getAccount();
            }
        } else{
            System.out.println();
            System.out.println("Old Password Error! 🛑");
            System.out.println();

            getAccount();
        }
    }

    public void register(String playerUsername, String playerPassword){
        String db_url = "jdbc:mysql://localhost:8889/game";
        String user = "root";
        String password = "root";
        int userExist = 0;

        try (Connection connection = DriverManager.getConnection(db_url, user, password)) {
            try (Statement statement = connection.createStatement()) {
                String query = "SELECT COUNT(*) AS usernameExist FROM players where username='" + playerUsername + "'";
                ResultSet resultSet = statement.executeQuery(query);

                while(resultSet.next()) {
                    userExist = resultSet.getInt("usernameExist");
                }

                if(userExist == 0){
                    query = "INSERT INTO players (username,user_password,wins,loses) VALUES('" + playerUsername + "','" + playerPassword + "',0,0)";
                    statement.executeUpdate(query);

                    System.out.println("Account Created! ✅");
                }else{
                    System.out.println("Username already exits. Account not created! 🛑");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addPlayerWin(){
        String db_url = "jdbc:mysql://localhost:8889/game";
        String user = "root";
        String password = "root";

        this.wins = this.wins + 1;

        try (Connection connection = DriverManager.getConnection(db_url, user, password)) {
            try (Statement statement = connection.createStatement()) {
                String query = "UPDATE players SET wins = " + this.wins + " WHERE id=" + this.id + ";";
                statement.executeUpdate(query);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addPlayerLose(){
        String db_url = "jdbc:mysql://localhost:8889/game";
        String user = "root";
        String password = "root";

        this.loses = this.loses + 1;

        try (Connection connection = DriverManager.getConnection(db_url, user, password)) {
            try (Statement statement = connection.createStatement()) {
                String query = "UPDATE players SET loses = " + this.loses + " WHERE id=" + this.id + ";";
                statement.executeUpdate(query);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }
}
