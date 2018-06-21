package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import entity.CountDownClock;
import entity.GameSession;
import entity.LetterEngine;
import entity.Player;
import entity.SessionPlayer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.ControlledScreen;
import main.ScreensController;
import rita.wordnet.RiWordnet;
import threads.GamePlayThread;
import utility.Constants;
import utility.PropertyHandler;
import utility.SortHandler;

public class PlayGroundController implements Initializable, ControlledScreen {
	/**
	 * FXML attributes initialization
	 */
	@FXML
	private Text txtPlayerOneName, txtPlayerTwoName, txtPlayerOnePoints, txtPlayerOneHints, txtPlayerTwoPoints,
			txtPlayerTwoHints, txtTimer, txtRoundNo, txtPlayerTowRound, txtSL_1_Player_1, txtSL_2_Player_1,
			txtSL_1_Player_2, txtSL_2_Player_2, txtSL_1_P_Player_1, txtSL_2_P_Player_1, txtSL_1_P_Player_2,
			txtSL_2_P_Player_2, txtL_1, txtL_2, txtL_3, txtL_4, txtL_5, txtL_6, txtL_7, txtL_8, txtL_9, txtL_10,
			txtL_P_1, txtL_P_2, txtL_P_3, txtL_P_4, txtL_P_5, txtL_P_6, txtL_P_7, txtL_P_8, txtL_P_9, txtL_P_10,
			txtPlayerTwoWord,
			txtLeaderBoard_1, txtLeaderBoard_2;
	@FXML
	private ChoiceBox<String> listConsonants, listVowels;
	@FXML
	private Button btnStartGamePlay, btnGenerate, btnSubmit;
	@FXML
	private TextField txtInputText;

	// -------------------------------------------------------------

	/**
	 * Local attributes
	 */
	ScreensController myController;
	private int round = 1;
	private String submit_status = "PENDING";
	private String player_word = "";
	private List<String> randomLetters = new ArrayList<String>();
	private LetterEngine letterEngine = new LetterEngine();
	/** Get CountDown Time from propFile **/
	private static int countDownTime = Integer.parseInt(new PropertyHandler().getProperty("countDownTime"));
	private boolean status = false;
	private boolean intializing = true;
	private int points = 0, hints = Integer.parseInt(new PropertyHandler().getProperty("gameMaxhints"));
	private int consCount = 0, vowelCount = 0;
	private List<String> consonantsList = new ArrayList<String>();
	private List<String> vowelsList = new ArrayList<String>();
	private int pos = 1;
	
	// -------------------------------------------------------------

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		txtRoundNo.setText("" + round);
		/** Clear generated Letters **/
		clearRandomLetters();
		/** Visible Generate Button **/
		btnGenerate.setVisible(true);
		/** Disable Buttons **/
		btnGenerate.setDisable(true);
		btnSubmit.setDisable(true);

		// start GamePlay Main Thread
		GamePlayThread();
	}

	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		myController = screenPage;
	}

	private void clearRandomLetters() {
		txtSL_1_Player_1.setText("");
		txtSL_1_P_Player_1.setText("");
		txtSL_2_Player_1.setText("");
		txtSL_2_P_Player_1.setText("");
		txtSL_1_Player_2.setText("");
		txtSL_1_P_Player_2.setText("");
		txtSL_2_Player_2.setText("");
		txtSL_2_P_Player_2.setText("");
		txtL_1.setText("");
		txtL_P_1.setText("");
		txtL_2.setText("");
		txtL_P_2.setText("");
		txtL_3.setText("");
		txtL_P_3.setText("");
		txtL_4.setText("");
		txtL_P_4.setText("");
		txtL_5.setText("");
		txtL_P_5.setText("");
		txtL_6.setText("");
		txtL_P_6.setText("");
		txtL_7.setText("");
		txtL_P_7.setText("");
		txtL_8.setText("");
		txtL_P_8.setText("");
		txtL_9.setText("");
		txtL_P_9.setText("");
		txtL_10.setText("");
		txtL_P_10.setText("");
	}

	// -------------------------------------------------------------

	/**
	 * FXML Actions
	 */
	@FXML
	private void startGamePlay() {
		status = true;

		/** reset actions **/
		resetActions();

		/** Start CountDown Clock **/
		clockStart();

		/** Hide Start Button **/
		btnStartGamePlay.setVisible(false);

		/** Set Consonants Picker **/
		setConsonantCount(10);
		/** Set Vowels Picker **/
		setVowelsCount(5);

		listConsonants.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
				consCount = Integer.parseInt(listConsonants.getItems().get((Integer) number2));
			}
		});
		listVowels.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
				vowelCount = Integer.parseInt(listVowels.getItems().get((Integer) number2));
			}
		});
	}

	/**
	 * restore actions
	 */
	private void resetActions() {
		/** Clear generated Letters **/
		clearRandomLetters();

		txtRoundNo.setText("" + round);

		randomLetters.clear();

		/** Get Random Letters and Display **/
		randomLetters = letterEngine.getRandomLetterPairs(Constants.LETTER, 2);
		txtSL_1_Player_1.setText(randomLetters.get(0));
		txtSL_2_Player_1.setText(randomLetters.get(1));
		/** Set Points **/
		txtSL_1_P_Player_1.setText("" + letterEngine.getLetterPoints(randomLetters.get(0)));
		txtSL_2_P_Player_1.setText("" + letterEngine.getLetterPoints(randomLetters.get(1)));

		/** Visible Generate Button **/
		btnGenerate.setVisible(true);

		/** Enable Buttons **/
		btnGenerate.setDisable(false);
		
		txtInputText.setText("");
	}

	@FXML
	private void generateLetters() {
		if ((consCount + vowelCount) != 10) {
			Dialogs.create().title("Warning").message("Consonants and Vowels sum should be equal to 10").showWarning();
		} else {
			consonantsList.clear();
			vowelsList.clear();

			consonantsList = letterEngine.getRandomLetterPairs(Constants.CONSONANT, consCount);
			vowelsList = letterEngine.getRandomLetterPairs(Constants.VOWEL, vowelCount);
			
			pos = 1;			
			// Set Randomly Generated Consonants
			setGeneratedLetters(consonantsList);
			// Set Randomly Generated Vowels
			setGeneratedLetters(vowelsList);

			/** Hide Generate Button **/
			btnGenerate.setVisible(false);

			/** Enable Button **/
			btnSubmit.setDisable(false);
		}
	}
	
	private void setGeneratedLetters(List<String> letters) {
		for (String letter : letters) {
			switch (pos) {
			case 1:
				txtL_1.setText(letter);
				txtL_P_1.setText("" + letterEngine.getLetterPoints(letter));
				break;
			case 2:
				txtL_2.setText(letter);
				txtL_P_2.setText("" + letterEngine.getLetterPoints(letter));
				break;
			case 3:
				txtL_3.setText(letter);
				txtL_P_3.setText("" + letterEngine.getLetterPoints(letter));
				break;
			case 4:
				txtL_4.setText(letter);
				txtL_P_4.setText("" + letterEngine.getLetterPoints(letter));
				break;
			case 5:
				txtL_5.setText(letter);
				txtL_P_5.setText("" + letterEngine.getLetterPoints(letter));
				break;
			case 6:
				txtL_6.setText(letter);
				txtL_P_6.setText("" + letterEngine.getLetterPoints(letter));
				break;
			case 7:
				txtL_7.setText(letter);
				txtL_P_7.setText("" + letterEngine.getLetterPoints(letter));
				break;
			case 8:
				txtL_8.setText(letter);
				txtL_P_8.setText("" + letterEngine.getLetterPoints(letter));
				break;
			case 9:
				txtL_9.setText(letter);
				txtL_P_9.setText("" + letterEngine.getLetterPoints(letter));
				break;
			case 10:
				txtL_10.setText(letter);
				txtL_P_10.setText("" + letterEngine.getLetterPoints(letter));
				break;
			}
			pos++;
		}
	}

	@FXML
	private void submit() {
		if (!txtInputText.getText().isEmpty() && txtInputText.getText().matches("^[a-zA-Z]+$")) {
			String word = txtInputText.getText();
			if (checkExist(word, randomLetters) || checkExist(word, consonantsList) || checkExist(word, vowelsList)) {
				if (!letterEngine.checkWordMistakes(word, randomLetters, consonantsList, vowelsList)) {
					RiWordnet riWordnet = new RiWordnet();
					if (riWordnet.exists(word)) {
						Action action = Dialogs.create().title("Confirm Exit").message("Do you want to submit ?")
								.actions(Dialog.Actions.YES, Dialog.Actions.NO).showConfirm();
						if (action == Dialog.Actions.YES) {
							points += letterEngine.getWordPoints(word);
							player_word = word;
							txtPlayerOnePoints.setText("" + points);

							countDownTime = Integer.parseInt(new PropertyHandler().getProperty("countDownTime"));

							/** reset actions **/
							resetActions();
							if(round < 3){
								round++;
							}
							txtRoundNo.setText("" + round);
						}
					} else {
						Dialogs.create().title("Warning").message("Not a word").showWarning();
					}
				} else {
					Dialogs.create().title("Warning")
							.message("Entered Word contains some letters, more than given amount").showWarning();
				}
			} else {
				Dialogs.create().title("Warning").message("Entered Word is missing some letters").showWarning();
			}
		} else {
			Dialogs.create().title("Warning")
					.message("Please Enter Word !..\nCheck for space,numbers and special characters").showWarning();
		}
	}

	/**
	 * checks the word contains letters for given letter List
	 * 
	 * @param word
	 * @param letters
	 * @return
	 */
	private boolean checkExist(String word, List<String> letters) {
		boolean check = false;
		for (String letter : letters) {
			if (word.contains(letter) || word.contains(letter.toLowerCase())) {
				check = true;
			}
		}
		return check;
	}

	@FXML
	private void backAction() {
		myController.setScreen(Constants.MENU_SCREEN);

		status = false;
		countDownTime = Integer.parseInt(new PropertyHandler().getProperty("countDownTime"));

		/** reset random letter **/
		randomLetters.clear();

		/** Hide Start Button **/
		btnStartGamePlay.setVisible(true);

		/** Clear generated Letters **/
		clearRandomLetters();

		/** Visible Generate Button **/
		btnGenerate.setVisible(true);

		/** Disable Buttons **/
		btnGenerate.setDisable(true);
		btnSubmit.setDisable(true);
	}

	// -------------------------------------------------------------

	/**
	 * Custom Functions
	 */
	public void setPlayers(GameSession gameSession) {
		Map<String, Integer> tempMap = new HashMap<String, Integer>();
		for (SessionPlayer player : GameSession.getPlayersList()) {
			if (player.getPlayerLocal()) {
				txtPlayerOneName.setText(Player.getPlayerName());
				txtPlayerOneHints.setText(""+hints);
				txtPlayerOnePoints.setText(""+points);
			} else {
				txtSL_1_Player_2.setText(player.getPlayerLetter_1());
				txtSL_2_Player_2.setText(player.getPlayerLetter_2());
				/** Set Points **/
				txtSL_1_P_Player_2.setText("" + letterEngine.getLetterPoints(player.getPlayerLetter_1()));
				txtSL_2_P_Player_2.setText("" + letterEngine.getLetterPoints(player.getPlayerLetter_2()));

				txtPlayerTwoName.setText(player.getPlayerName());
				txtPlayerTwoPoints.setText("" + player.getPoints());
				txtPlayerTwoHints.setText("" + player.getHints());
				txtPlayerTwoWord.setText(player.getPlayerWord());

				txtPlayerTowRound.setText("" + player.getPlayerRound());
			}
			tempMap.put(player.getPlayerName(), player.getPoints());
		}
		/**
		 * Set Players to leader board
		 */
		setLeaderBoard(tempMap);
	}

	private boolean startGatheringDataStatus = false;

	private void GamePlayThread() {
		final PropertyHandler propertyHandler = new PropertyHandler();
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws Exception {
				while (intializing) {
					Platform.runLater(new Runnable() {
						public void run() {
							GameSession gameSession = new GameSession();
							if (GameSession.getGameType() != 0) {
								setPlayers(gameSession);
								// update local player Details
								String letter_1 = "", letter_2 = "";
								if (!randomLetters.isEmpty()) {
									letter_1 = randomLetters.get(0).toString();
									letter_2 = randomLetters.get(1).toString();
								}
								gameSession.updatePlayer(Player.getPlayerID(), round, letter_1, letter_2, player_word,
										points, hints, submit_status);

								/** Start Main Thread **/
								if (!startGatheringDataStatus) {
									startGatheringDataStatus = true;
									startGatheringData();
								}
							}
						}
					});
					Thread.sleep(Integer.parseInt(propertyHandler.getProperty("gamePlayRefresh")));
				}
				return null;
			}
		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

	private void setLeaderBoard(Map<String, Integer> players){
		@SuppressWarnings("static-access")
		Map<String, Integer> sorted = new SortHandler().SortMap(players);
		
		List<String> keys = new ArrayList<String>();
		for(String key : sorted.keySet()){
			keys.add(key);
		}
		// reverse the order
		Collections.reverse(keys);
		int num=1;
		for(String name : keys){
			switch (num) {
			case 1:
				txtLeaderBoard_1.setText(num+". "+name+" - "+sorted.get(name));
				break;
			case 2:
				txtLeaderBoard_2.setText(num+". "+name+" - "+sorted.get(name));
				break;
			default:
				break;
			}
			num++;
		}
	}
	
	private void startGatheringData() {
		// start searching thread for other players to join Game Session
		GamePlayThread gamePlayThread = new GamePlayThread();
		Thread thread = new Thread(gamePlayThread);
		thread.start();
	}

	private void clockStart() {
		final CountDownClock clock = new CountDownClock();
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws Exception {
				while (status && countDownTime > 0) {
					Platform.runLater(new Runnable() {
						public void run() {
							txtTimer.setText(clock.getTimeLeft(countDownTime).toString());
						}
					});
					countDownTime--;

					/**
					 * Check auto submit details
					 */
					autoSubmit();

					Thread.sleep(1000);
				}
				return null;
			}
		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

	/**
	 * Automatically submit user data when time is up
	 */
	private void autoSubmit() {
		if (countDownTime == 0) {
			RiWordnet riWordnet = new RiWordnet();
			String word = txtInputText.getText();
			if (!txtInputText.getText().isEmpty() && txtInputText.getText().matches("^[a-zA-Z]+$")
					&& (checkExist(word, randomLetters) || checkExist(word, consonantsList)
							|| checkExist(word, vowelsList))
					&& !letterEngine.checkWordMistakes(word, randomLetters, consonantsList, vowelsList)
					&& riWordnet.exists(word)) {

				points += letterEngine.getWordPoints(word);
				player_word = word;				
			} else {
				player_word = "";
			}
			txtPlayerOnePoints.setText("" + points);

			countDownTime = Integer.parseInt(new PropertyHandler().getProperty("countDownTime"));

			/** reset actions **/
			resetActions();

			if(round < 3){
				round++;
			}
			txtRoundNo.setText("" + round);

			clockStart();
		}
	}

	private void setConsonantCount(int count) {
		if (listConsonants.getItems().size() == 0) {
			for (int i = 1; i <= count; i++) {
				listConsonants.getItems().add("" + i);
			}
		}
	}

	private void setVowelsCount(int count) {
		if (listVowels.getItems().size() == 0) {
			for (int i = 1; i <= count; i++) {
				listVowels.getItems().add("" + i);
			}
		}
	}
}
