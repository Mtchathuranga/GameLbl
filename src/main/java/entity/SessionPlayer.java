package entity;

public class SessionPlayer {
	private boolean playerLocal;
	private String playerID;
	private String playerName;
	private String playerUsername;
	private String playerLetter_1 = "", playerLetter_2 = "";
	private int points;
	private int hints;
	private String playerSubmitStatus;
	private int playerRound;
	private String playerWord;

	public String getPlayerSubmitStatus() {
		return playerSubmitStatus;
	}

	public void setPlayerSubmitStatus(String playerSubmitStatus) {
		this.playerSubmitStatus = playerSubmitStatus;
	}

	public SessionPlayer() {

	}

	public SessionPlayer(String id, String name, String username) {
		setPlayerID(id);
		setPlayerName(name);
		setPlayerUsername(username);
		// default assignments
		setPlayerRound(1);
		setPlayerWord("");
	}

	public String getPlayerID() {
		return playerID;
	}

	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerUsername() {
		return playerUsername;
	}

	public void setPlayerUsername(String playerUsername) {
		this.playerUsername = playerUsername;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getHints() {
		return hints;
	}

	public void setHints(int hints) {
		this.hints = hints;
	}

	public boolean getPlayerLocal() {
		return playerLocal;
	}

	public void setPlayerLocal(boolean playerLocal) {
		this.playerLocal = playerLocal;
	}

	public String getPlayerLetter_1() {
		return playerLetter_1;
	}

	public void setPlayerLetter_1(String playerLetter_1) {
		this.playerLetter_1 = playerLetter_1;
	}

	public String getPlayerLetter_2() {
		return playerLetter_2;
	}

	public void setPlayerLetter_2(String playerLetter_2) {
		this.playerLetter_2 = playerLetter_2;
	}

	public int getPlayerRound() {
		return playerRound;
	}

	public void setPlayerRound(int playerRound) {
		this.playerRound = playerRound;
	}

	public String getPlayerWord() {
		return playerWord;
	}

	public void setPlayerWord(String playerWord) {
		this.playerWord = playerWord;
	}
}
