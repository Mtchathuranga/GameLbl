package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utility.Constants;
import utility.PropertyHandler;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		PropertyHandler propertyHandler = new PropertyHandler();

		// load screens into mainScreen Controller
		ScreensController mainContainer = new ScreensController();
		mainContainer.loadScreen(Constants.MAIN_SCREEN, Constants.MAIN_SCREEN_FXML);
		mainContainer.loadScreen(Constants.REGISTER_SCREEN, Constants.REGISTER_SCREEN_FXML);
		mainContainer.loadScreen(Constants.MENU_SCREEN, Constants.MENU_SCREEN_FXML);
		mainContainer.loadScreen(Constants.GAMEPLAY_SCREEN, Constants.GAMEPLAY_SCREEN_FXML);

		mainContainer.setScreen(Constants.MAIN_SCREEN);

		Group root = new Group();
		root.getChildren().addAll(mainContainer);
		Scene scene = new Scene(root);
		stage.setWidth(Integer.parseInt(propertyHandler.getProperty("preWindowWidth")));
		stage.setHeight(Integer.parseInt(propertyHandler.getProperty("preWindowHeight")));
		stage.setTitle("Letter-By-Letter");
		stage.getIcons().add(new Image("images/logo.jpg"));
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
}
