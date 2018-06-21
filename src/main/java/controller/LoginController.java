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
import javafx.scene.layout.AnchorPane;
import main.ControlledScreen;
import main.ScreensController;
import utility.Connectivity;
import utility.Constants;

public class LoginController implements Initializable, ControlledScreen {
	ScreensController myController;

	@FXML
	private AnchorPane contentLogin;
	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Button btnLogin;

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		myController = screenPage;
	}

	@FXML
	private void loginUser() throws IOException {
		if (txtUsername.getText().length() == 0) {
			Dialogs.create().title("Warning").message("Please Enter Username").showWarning();
		} else {
			if (txtPassword.getText().length() == 0) {
				Dialogs.create().title("Warning").message("Please Enter Password").showWarning();
			} else {
				JSONObject data = new JSONObject();
				data.put("username", txtUsername.getText());
				data.put("password", txtPassword.getText());
				Connectivity connectivity = new Connectivity();

				String res = connectivity.sendPOST("login=" + data.toString()).toString();

				if (!res.equals(HttpURLConnection.HTTP_BAD_REQUEST)) {
					JSONObject response = new JSONObject(res);
					if (response.getBoolean("errorStatus")) {
						System.out.println("USER Error");
						Dialogs.create().title("Warning")
								.message(
										"Invalid Username or Password\nIf you do not have any account, Please Register")
								.showWarning();
					} else {
						System.out.println(response);
						new Player(response.getString("id"), response.getString("name"),
								response.getString("username"));
						myController.setScreen(Constants.MENU_SCREEN);
						txtPassword.setText("");
					}
				}
			}
		}
	}

	@FXML
	private void openRegister() {
		myController.setScreen(Constants.REGISTER_SCREEN);
	}
}
