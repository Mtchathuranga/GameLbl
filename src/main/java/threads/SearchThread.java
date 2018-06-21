package threads;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import controller.MenuController;
import entity.GameSession;
import entity.Player;
import entity.SessionPlayer;
import utility.Connectivity;
import utility.Constants;
import utility.PropertyHandler;

public class SearchThread implements Runnable {
	private static Boolean stop = false;
	private JSONObject data = new JSONObject();
	private Connectivity connectivity = new Connectivity();
	private PropertyHandler propertyHandler = new PropertyHandler();

	public void run() {
		// TODO Auto-generated method stub
		while (!stop) {
			JSONObject res;
			try {
				res = connectivity.sendPOST("check_game_status=" + data.toString());
				System.out.println("SearchThread = " + res.toString());
				if (res.getString("gameCheck").equals("STARTED")) {
					stop = true;
					// create new GameSession
					GameSession newGameSession = new GameSession();

					JSONArray players = res.getJSONObject("gameData").getJSONArray("users");
					for (int i = 0; i < players.length(); i++) {
						SessionPlayer newPlayer = new SessionPlayer();
						if (Player.getPlayerID().equals(players.getJSONObject(i).getString("id"))) {
							newPlayer.setPlayerLocal(true);
						} else {
							newPlayer.setPlayerLocal(false);
						}
						newPlayer.setPlayerID(players.getJSONObject(i).getString("id"));
						newPlayer.setPlayerName(players.getJSONObject(i).getString("name"));
						newPlayer.setPlayerUsername(players.getJSONObject(i).getString("username"));
						newPlayer.setPoints(0);
						newPlayer.setHints(Integer.parseInt(propertyHandler.getProperty("gameMaxhints")));

						// add new Player into Game Session Player List
						newGameSession.addPlayer(newPlayer);
					}
					// set game type in Game Session
					GameSession.setGameType(data.getInt("gameType"));
					// set game session id
					GameSession.setSessionID(data.getInt("sessionID"));

					// change screen
					MenuController.myController.setScreen(Constants.GAMEPLAY_SCREEN);
					Thread.interrupted();
				}
				Thread.sleep(Integer.parseInt(propertyHandler.getProperty("searchPlayers")));
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
		SearchThread.stop = stop;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	public JSONObject getData() {
		return this.data;
	}
}
