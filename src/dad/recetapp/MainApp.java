package dad.recetapp;

import java.io.IOException;

import dad.recetapp.db.BaseDatos;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MainApp extends Application {

	public static Stage primaryStage;
	private BorderPane rootLayout;
	private Stage initStage;
	public static Timeline timeline;
	
	
	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("RecetApp");
		this.primaryStage.getIcons().add(new Image("dad/recetapp/ui/images/logo.png"));


		initLogoView();


		timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(4),
				new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				initStage.close(); 
				if(BaseDatos.test()){
					initStageRoot();
					showMainFrame();
				}else{
					Alert alertError = new Alert(AlertType.ERROR);
					alertError.setTitle("Error");
					alertError.setHeaderText("Error ");
					alertError.setContentText("Se ha producido un error al conectarse a la base de datos");

					alertError.showAndWait();
				}
				
			}
		}));
		timeline.play();
	}

	private void initLogoView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/dad/recetapp/view/IniciandoView.fxml"));
			BorderPane initView = (BorderPane) loader.load();
			initStage = new Stage();
			initStage.initStyle(StageStyle.UNDECORATED);
			initStage.setScene(new Scene(initView));
			initStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the root layout.
	 */
	public void initStageRoot() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/dad/recetapp/view/MainStage.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the person overview inside the root layout.
	 */
	public void showMainFrame() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/dad/recetapp/view/RecetappFrameMain.fxml"));
			BorderPane personOverview = (BorderPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(personOverview);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void showNuevaReceta() {

	}
	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
