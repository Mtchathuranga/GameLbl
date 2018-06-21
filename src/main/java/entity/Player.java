package entity;

public class Player {
	private static String playerID;
	private static String playerName;
	private static String playerUsername;

	public Player() {

	}

	public Player(String id, String name, String username) {
		setPlayerID(id);
		setPlayerName(name);
		setPlayerUsername(username);
	}

	public static String getPlayerID() {
		return playerID;
	}

	public static void setPlayerID(String playerID) {
		Player.playerID = playerID;
	}

	public static String getPlayerName() {
		return playerName;
	}

	public static void setPlayerName(String playerName) {
		Player.playerName = playerName;
	}

	public static String getPlayerUsername() {
		return playerUsername;
	}

	public static void setPlayerUsername(String playerUsername) {
		Player.playerUsername = playerUsername;
	}
}
