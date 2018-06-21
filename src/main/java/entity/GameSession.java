package entity;

import java.util.ArrayList;
import java.util.List;

public class GameSession {
	private static List<SessionPlayer> playersList = new ArrayList<SessionPlayer>();
	private static int gameType = 0;
	private static int sessionID = -1;

	public GameSession() {

	}

	public static List<SessionPlayer> getPlayersList() {
		return playersList;
	}

	public static int getGameType() {
		return gameType;
	}

	public static void setGameType(int gameType) {
		GameSession.gameType = gameType;
	}

	public void addPlayer(SessionPlayer player) {
		playersList.add(player);
	}

	// /**
	// * Update Only local player
	// * @param letters
	// * @param points
	// * @param hints
	// */
	// public void updateLocalPlayer(List<String> letters,int points,int hints){
	// int index=-1;
	// for(int i=0;i<playersList.size();i++){
	// if(playersList.get(i).getPlayerLocal()){
	// index = i;
	// break;
	// }
	// }
	// if(index != -1){
	// playersList.get(index).setPlayerLetters(letters);
	// playersList.get(index).setPoints(points);
	// playersList.get(index).setHints(hints);
	// }
	// }

	/**
	 * Update single player
	 * 
	 * @param playerID
	 * @param letters
	 * @param points
	 * @param hints
	 */
	public void updatePlayer(String playerID, int round, String letter_1, String letter_2, String word, int points,
			int hints, String submit_status) {
		int index = -1;
		for (int i = 0; i < playersList.size(); i++) {
			if (playersList.get(i).getPlayerID().equals(playerID)) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			playersList.get(index).setPlayerRound(round);
			playersList.get(index).setPlayerLetter_1(letter_1);
			playersList.get(index).setPlayerLetter_2(letter_2);
			playersList.get(index).setPlayerWord(word);
			playersList.get(index).setPoints(points);
			playersList.get(index).setHints(hints);
			playersList.get(index).setPlayerSubmitStatus(submit_status);
		}
	}

	public void clearGameSession() {
		playersList.clear();
		gameType = 0;
	}

	public static int getSessionID() {
		return sessionID;
	}

	public static void setSessionID(int sessionID) {
		GameSession.sessionID = sessionID;
	}
}