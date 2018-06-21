package main;

import java.util.HashMap;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class ScreensController extends StackPane {
	private HashMap<String, Node> screens = new HashMap<String, Node>();

	public void addScreen(String name, Node screen) {
		screens.put(name, screen);
	}

	public boolean loadScreen(String name, String resource) {
		try {
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
			Parent loadScreen = (Parent) myLoader.load();
			ControlledScreen myScreenController = ((ControlledScreen) myLoader.getController());
			myScreenController.setScreenParent(this);
			addScreen(name, loadScreen);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean setScreen(final String name) {
		if (screens.get(name) != null) {
			final DoubleProperty opacity = opacityProperty();

			javafx.event.EventHandler<ActionEvent> eventHandler = new javafx.event.EventHandler<ActionEvent>() {

				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					// remove displayed screen
					getChildren().remove(0);
					// add new screen
					getChildren().add(0, screens.get(name));
					Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
							new KeyFrame(new Duration(400), new KeyValue(opacity, 1.0)));
					fadeIn.play();
				}
			};

			if (!getChildren().isEmpty()) {
				Timeline fade = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
						new KeyFrame(new Duration(500), eventHandler, new KeyValue(opacity, 0.0)));
				fade.play();

			} else {
				// no one else been displayed, then just show
				setOpacity(0.0);
				getChildren().add(screens.get(name));
				Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
						new KeyFrame(new Duration(1000), new KeyValue(opacity, 1.0)));
				fadeIn.play();
			}
			return true;
		} else {
			System.out.println("screen hasn't been loaded!\n");
			return false;
		}
	}

	public boolean unloadScreen(String name) {
		if (screens.remove(name) == null) {
			System.out.println("Screen didn`t exist");
			return false;
		} else {
			return true;
		}
	}
}
