import java.sql.*;

public class PlayersRanking {
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_RESET = "\u001B[0m";

    public void playersRanking(int id){
        String db_url = "jdbc:mysql://localhost:8889/game";
        String user = "root";
        String password = "root";
        int idPlayer = 0, counter = 1;
        double wlPlayer = 0;
        String usernamePlayer = "";

        try (Connection connection = DriverManager.getConnection(db_url, user, password)) {
            try (Statement statement = connection.createStatement()) {
                String query = "SELECT *,(wins/loses) AS WL FROM players order by WL desc;";
                ResultSet resultSet = statement.executeQuery(query);

                while(resultSet.next()) {
                    idPlayer = resultSet.getInt("id");
                    usernamePlayer = resultSet.getString("username");
                    wlPlayer = resultSet.getDouble("WL");

                    if(idPlayer == id){
                        System.out.printf(ANSI_GREEN_BACKGROUND + counter + ": Username - %s | WL - %.2f\n" + ANSI_RESET,usernamePlayer, wlPlayer);
                    } else {
                        System.out.printf(counter + ": Username - %s | WL - %.2f\n",usernamePlayer, wlPlayer);
                    }

                    counter++;
                }
                System.out.println();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
