package controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.dialog.Dialogs;
import org.json.JSONObject;

import entity.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.ControlledScreen;
import main.ScreensController;
import utility.Connectivity;
import utility.Constants;

public class RegisterController implements Initializable, ControlledScreen {

	@FXML
	private TextField txtName, txtEmail, txtUsername;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Button btnRegister;

	ScreensController myController;

	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		myController = screenPage;
	}

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	private void registerUser() throws IOException {
		if (txtName.getText().length() == 0) {
			Dialogs.create().title("Warning").message("Please Enter Name").showWarning();
		} else {
			if (txtEmail.getText().length() == 0) {
				Dialogs.create().title("Warning").message("Please Enter Email").showWarning();
			} else {
				if (txtUsername.getText().length() == 0) {
					Dialogs.create().title("Warning").message("Please Enter Username").showWarning();
				} else {
					if (txtPassword.getText().length() == 0) {
						Dialogs.create().title("Warning").message("Please Enter Password").showWarning();
					} else {
						JSONObject data = new JSONObject();
						data.put("name", txtName.getText());
						data.put("email", txtEmail.getText());
						data.put("username", txtUsername.getText());
						data.put("password", txtPassword.getText());
						System.out.println(data.toString());
						Connectivity connectivity = new Connectivity();

						String res = connectivity.sendPOST("register=" + data.toString()).toString();
						if (!res.equals(HttpURLConnection.HTTP_BAD_REQUEST)) {
							JSONObject response = new JSONObject(res);
							if (response.getBoolean("errorStatus")) {
								System.out.println("USER Error");
								Dialogs.create().title("Warning").message("Server Error... \nPlease try again later")
										.showWarning();

								txtPassword.setText("");
							} else {
								System.out.println(response);
								new Player(response.getString("id"), response.getString("name"),
										response.getString("username"));
								myController.setScreen(Constants.MENU_SCREEN);

								txtName.setText("");
								txtEmail.setText("");
								txtUsername.setText("");
								txtPassword.setText("");
							}
						}
					}
				}
			}
		}
	}

	@FXML
	private void backAction() {
		myController.setScreen(Constants.MAIN_SCREEN);
		txtName.setText("");
		txtEmail.setText("");
		txtUsername.setText("");
		txtPassword.setText("");
	}

}
