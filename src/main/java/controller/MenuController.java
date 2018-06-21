package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.json.JSONObject;

import entity.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import main.ControlledScreen;
import main.ScreensController;
import threads.SearchThread;
import utility.Connectivity;
import utility.Constants;

public class MenuController implements Initializable, ControlledScreen {
	public static ScreensController myController;

	@FXML
	private Pane pnlLoading;

	/**
	 * FXML attributes initialization
	 */

	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		pnlLoading.setVisible(false);
	}

	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		myController = screenPage;
	}

	/**
	 * FXML Actions
	 * 
	 * @throws IOException
	 */
	@FXML
	private void startTwoPlayer() throws IOException {
		System.out.println("player 2");
		makeGameSession(2);
	}

	@FXML
	private void startThreePlayer() throws IOException {
		System.out.println("player 3");
		//makeGameSession(3);
	}

	@FXML
	private void startFourPlayer() throws IOException {
		System.out.println("player 4");
		//makeGameSession(4);
	}

	@FXML
	private void ExitGame() throws IOException {
		System.out.println("Open Login Screen");
		Action action = Dialogs.create().title("Confirm Exit").message("Do you want to exit ?")
				.actions(Dialog.Actions.YES, Dialog.Actions.NO).showConfirm();
		if (action == Dialog.Actions.YES) {
			myController.setScreen(Constants.MAIN_SCREEN);
			offlineUser();
		}
	}

	/**
	 * Make User Offline when exit from game
	 * 
	 * @throws IOException
	 */
	private void offlineUser() throws IOException {
		pnlLoading.setVisible(false);
		JSONObject data = new JSONObject();
		data.put("userID", Player.getPlayerID());
		Connectivity connectivity = new Connectivity();
		connectivity.sendPOST("logout=" + data.toString());
	}

	/**
	 * Search for Game Session if no session found in server, it starts new game
	 * session or join game session with matching requirements
	 * 
	 * @param type
	 * @throws IOException
	 */
	private void makeGameSession(int type) throws IOException {
		pnlLoading.setVisible(true);
		JSONObject data = new JSONObject();
		data.put("gameType", type);
		data.put("userID", Player.getPlayerID());
		Connectivity connectivity = new Connectivity();
		JSONObject response = connectivity.sendPOST("search_game=" + data.toString());
		// begin waiting
		checkGameStatus(response.getInt("sessionID"), type);
	}

	private void checkGameStatus(int sessionID, int type) throws IOException {
		System.out.println("SESSION ID = " + sessionID);
		JSONObject data = new JSONObject();
		data.put("gameType", type);
		data.put("sessionID", sessionID);
		// start searching thread for other players to join Game Session
		SearchThread searchThread = new SearchThread();
		searchThread.setData(data);
		Thread thread = new Thread(searchThread);
		thread.start();
	}
}
