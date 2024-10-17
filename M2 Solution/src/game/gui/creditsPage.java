package game.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class creditsPage{
	public static void showCreditPage(Stage primaryStage, Main mainApp) {
        StackPane stackpane = new StackPane();
        BorderPane borderpane = new BorderPane();
        
        // Background Image
        Image backgroundImage = new Image("Attack on Titan Game Start Menu.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundImageView.fitHeightProperty().bind(primaryStage.heightProperty());
        
        // Button for returning back to start page
        Button returnStart = new Button("Return Back");
        HBox hbox = new HBox(returnStart);
        hbox.setAlignment(Pos.TOP_LEFT);
        hbox.setPadding(new Insets(10));
        borderpane.setTop(hbox);
        
        
        // Action of return Button
        returnStart.setOnAction(e -> {
        	try {
				mainApp.start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        });
        
        Scene gameScene = new Scene(stackpane);
        stackpane.getChildren().addAll(backgroundImageView,borderpane);
        primaryStage.setScene(gameScene);
    }
}
