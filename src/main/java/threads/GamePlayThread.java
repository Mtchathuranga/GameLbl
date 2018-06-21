package threads;

import java.io.IOException;

import org.json.JSONObject;

import entity.GameSession;
import entity.Player;
import entity.SessionPlayer;
import utility.Connectivity;
import utility.PropertyHandler;

public class GamePlayThread implements Runnable {

	private Boolean stop = false;
	private Connectivity connectivity = new Connectivity();
	private PropertyHandler propertyHandler = new PropertyHandler();

	public void run() {
		// TODO Auto-generated method stub
		while (!stop) {
			JSONObject res;
			try {
				JSONObject data = new JSONObject();
				data.put("userID", Player.getPlayerID());
				data.put("sessionID", GameSession.getSessionID());
				GameSession gameSession = new GameSession();
				for (SessionPlayer player : GameSession.getPlayersList()) {
					if (player.getPlayerLocal()) {
						data.put("round", player.getPlayerRound());
						data.put("letter1", player.getPlayerLetter_1());
						data.put("letter2", player.getPlayerLetter_2());
						data.put("word", player.getPlayerWord());
						data.put("points", player.getPoints());
						data.put("hints", player.getHints());
						data.put("submit_status", player.getPlayerSubmitStatus());
					} else {
						data.append("users", player.getPlayerID());
					}
				}
				res = connectivity.sendPOST("game_data=" + data.toString());
				System.out.println("GamePlay Thread = " + res.toString());
				if (res.has("users_data")) {
					for (int i = 0; i < res.getJSONArray("users_data").length(); i++) {
						JSONObject tempPlayer = res.getJSONArray("users_data").getJSONObject(i);
						String letter_1 = "", letter_2 = "", word = "", submit_status = "";
						int points = 0, hints = 3, crr_round = 1;
						if (tempPlayer.has("current_round") && !tempPlayer.isNull("current_round")) {
							crr_round = tempPlayer.getInt("current_round");
						}
						if (tempPlayer.has("r_" + crr_round + "_letter_1")
								&& !tempPlayer.isNull("r_" + crr_round + "_letter_1")) {
							letter_1 = tempPlayer.getString("r_" + crr_round + "_letter_1");
						}
						if (tempPlayer.has("r_" + crr_round + "_letter_2")
								&& !tempPlayer.isNull("r_" + crr_round + "_letter_2")) {
							letter_2 = tempPlayer.getString("r_" + crr_round + "_letter_2");
						}
						if (tempPlayer.has("word") && !tempPlayer.isNull("word")) {
							word = tempPlayer.getString("word");
						}
						if (tempPlayer.has("points") && !tempPlayer.isNull("points")) {
							points = tempPlayer.getInt("points");
						}
						if (tempPlayer.has("hints") && !tempPlayer.isNull("hints")) {
							hints = tempPlayer.getInt("hints");
						}
						if (tempPlayer.has("submit_status") && !tempPlayer.isNull("submit_status")) {
							submit_status = tempPlayer.getString("submit_status");
						}

						gameSession.updatePlayer(tempPlayer.getString("user_id"), crr_round, letter_1, letter_2, word.toUpperCase(),
								points, hints, submit_status);
					}
				}

				Thread.sleep(Integer.parseInt(propertyHandler.getProperty("gamePlayRefresh")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Boolean getStop() {
		return stop;
	}

	public void setStop(Boolean stop) {
		this.stop = stop;
	}
}
