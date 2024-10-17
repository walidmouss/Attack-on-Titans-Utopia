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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();

        // Background Image
        Image backgroundImage = new Image("Attack on Titan Game Start Menu.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundImageView.fitHeightProperty().bind(primaryStage.heightProperty());

        // Mode Buttons
        Label gameMode = new Label("Game Mode");
        gameMode.setTextFill(Color.RED);
        RadioButton easyMode = new RadioButton("Easy");
        RadioButton hardMode = new RadioButton("Hard");
        ToggleGroup gameModes = new ToggleGroup();
        easyMode.setToggleGroup(gameModes);
        hardMode.setToggleGroup(gameModes);
        easyMode.setTextFill(Color.RED);
        hardMode.setTextFill(Color.RED);
        easyMode.setSelected(true);
        HBox gameModesBox = new HBox(easyMode, hardMode);
        gameModesBox.setAlignment(Pos.CENTER);
        gameModesBox.setSpacing(20);

        // Start menu Buttons
        Button start = new Button("Start Playing");
        Button credits = new Button("Credits");
        VBox buttonsBox = new VBox(start, credits);
        buttonsBox.setSpacing(10);
        buttonsBox.setAlignment(Pos.CENTER);

        // Action of Start Button
        start.setOnAction(e -> {
            if (easyMode.isSelected()) {
                // Show instructions for easy mode
                showEasyModeInstructionsDialog(primaryStage, this);
            } else {
                // Show instructions for hard mode
                showHardModeInstructionsDialog(primaryStage, this);
            }
        });

        // BorderPane for UI components
        BorderPane borderPane = new BorderPane();
        VBox uiContainer = new VBox(gameMode, gameModesBox, buttonsBox);
        uiContainer.setSpacing(30);
        uiContainer.setAlignment(Pos.CENTER);
        borderPane.setCenter(uiContainer);

        // Adding BorderPane to StackPane
        root.getChildren().addAll(backgroundImageView, borderPane);

        // Scene and Title
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Attack On Titan Utopia");
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void showEasyModeInstructionsDialog(Stage primaryStage, Main mainApp) {
        // Create a new stage for the instructions dialog
        Stage dialog = new Stage();
        dialog.initOwner(primaryStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle("Easy Mode Instructions");

        // Background Image
        Image backgroundImage = new Image("easy_mode_background.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(600); // Set the width to match the image
        backgroundImageView.setFitHeight(400); // Set the height to match the image

        // Create a label with easy mode instructions
        Label instructionsLabel = new Label("Welcome to 'Attack on Titans: Tower Defense'!\n\n"
                + "Defend humanity's last bastion against towering titans in this intense tower defense game.\n"
                + "With only three lanes to hold back the relentless titan onslaught, strategic planning is crucial.\n"
                + "Deploy an arsenal of powerful weapons, face ever-growing titan hordes, and lead humanity to victory.\n"
                + "Can you withstand the assault and protect the walls, or will the titans breach your defenses? Find out now!");
        instructionsLabel.setAlignment(Pos.CENTER);
        instructionsLabel.setTextAlignment(TextAlignment.CENTER);
        instructionsLabel.setTextFill(Color.WHITE);
        instructionsLabel.setWrapText(true);
        instructionsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Set font to bold

        // "Continue" button
        Button continueButton = new Button("Continue");
        continueButton.setOnAction(e -> {
            dialog.close();
            // Proceed to the easy mode game
            easyModeClass easyMode = new easyModeClass();
            try {
                easyMode.showEasyModeScene(primaryStage, mainApp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // VBox to hold the instructions label and the "Continue" button
        VBox root = new VBox(instructionsLabel, continueButton);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        // StackPane to overlay the instructions label and button on top of the background image
        StackPane stackPane = new StackPane(backgroundImageView, root);
        stackPane.setAlignment(Pos.CENTER);

        // Set the scene
        Scene dialogScene = new Scene(stackPane, 600, 400);
        dialog.setScene(dialogScene);

        // Show the dialog
        dialog.show();
    }

    public static void showHardModeInstructionsDialog(Stage primaryStage, Main mainApp) {
        // Create a new stage for the instructions dialog
        Stage dialog = new Stage();
        dialog.initOwner(primaryStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle("Hard Mode Instructions");

        // Background Image
        Image backgroundImage = new Image("hard_mode_background.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(600); // Set the width to match the image
        backgroundImageView.setFitHeight(400); // Set the height to match the image

        // Create a label with hard mode instructions
        Label instructionsLabel = new Label("Welcome to 'Attack on Titans: Tower Defense'!\n\n"
                + "Defend humanity's last bastion against towering titans in this intense tower defense game.\n"
                + "With only five lanes to hold back the relentless titan onslaught, strategic planning is crucial.\n"
                + "Deploy an arsenal of powerful weapons, face ever-growing titan hordes, and lead humanity to victory.\n"
                + "Can you withstand the assault and protect the walls, or will the titans breach your defenses? Find out now!");
        instructionsLabel.setAlignment(Pos.CENTER);
        instructionsLabel.setTextAlignment(TextAlignment.CENTER);
        instructionsLabel.setTextFill(Color.WHITE);
        instructionsLabel.setWrapText(true);
        instructionsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Set font to bold

        // "Continue" button
        Button continueButton = new Button("Continue");
        continueButton.setOnAction(e -> {
            dialog.close();
            // Proceed to the hard mode game
            hardModeClass hardMode = new hardModeClass();
            try {
                hardMode.showHardModeScene(primaryStage, mainApp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // VBox to hold the instructions label and the "Continue" button
        VBox root = new VBox(instructionsLabel, continueButton);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        // StackPane to overlay the instructions label and button on top of the background image
        StackPane stackPane = new StackPane(backgroundImageView, root);
        stackPane.setAlignment(Pos.CENTER);

        // Set the scene
        Scene dialogScene = new Scene(stackPane, 600, 400);
        dialog.setScene(dialogScene);

        // Show the dialog
        dialog.show();
    }
}
