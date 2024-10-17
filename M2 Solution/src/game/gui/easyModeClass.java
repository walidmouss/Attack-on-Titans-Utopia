package game.gui;

import java.util.Iterator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import game.engine.Battle;
import game.engine.lanes.Lane;
import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.Titan;

public class easyModeClass {
    private double laneWidth = 300;
    private double laneHeight = 100;
    private Battle battle;
    private GridPane gridPane;
    private VBox vBoxInfo;
    private BorderPane borderPane;
    private StackPane stackPane;
    private Scene easyModeScene;
    private Stage primaryStage;

    public void showEasyModeScene(Stage primaryStage, Main mainApp) throws Exception {
        this.primaryStage = primaryStage;

        vBoxInfo = createInfoVBox();
        gridPane = createGridPane();

        borderPane = new BorderPane();
        borderPane.setTop(vBoxInfo);
        borderPane.setCenter(gridPane);
        BorderPane.setMargin(gridPane, new Insets(10, 0, 0, 0));

        StackPane actionPane = createActionPane(primaryStage, mainApp);

        borderPane.setBottom(actionPane);

        easyModeScene = new Scene(borderPane, 1200, 700);
        primaryStage.setFullScreen(true);
        primaryStage.setScene(easyModeScene);

        battle = new Battle(1, 0, 500, 3, 125);
        initializeLanes();
        updateUI();
    }

    private VBox createInfoVBox() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);

        Label labelLaneCount = new Label();
        Label labelScore = new Label();
        Label labelResources = new Label();
        Label labelTurns = new Label();
        Label labelPhase = new Label();

        vBox.getChildren().addAll(labelLaneCount, labelScore, labelResources, labelTurns, labelPhase);

        return vBox;
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        gridPane.setPadding(new Insets(30));
        return gridPane;
    }

    private StackPane createActionPane(Stage primaryStage, Main mainApp) throws Exception {
        StackPane actionPane = new StackPane();
        Button purchaseButton = new Button("Go to Weapon Shop");
        purchaseButton.setOnAction(e -> {
            try {
                WeaponShop.ShowWeaponFactoryScene(primaryStage, mainApp, battle, easyModeScene, this);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        purchaseButton.setTranslateX(-300);

        Button passTurnButton = new Button("Pass Turn");
        passTurnButton.setOnAction(e -> {
        	passTurn();

            for (int i = 0; i < battle.getOriginalLanes().size(); i++) {
                Lane lane = battle.getOriginalLanes().get(i);
				if(lane.getLaneWall().getCurrentHealth() <= 0) {
					continue;
				}
				else
                for (Titan titan : lane.getTitans()) {
                    Circle titanCircle = createTitanCircle(titan);
                    titanCircle.setRadius(10);
                    gridPane.add(titanCircle, 0, i);
                    titanCircle.setTranslateX(titan.getDistance());
					titanCircle.setTranslateY((Math.random() * 40));
					if(titan instanceof ArmoredTitan) {
						titanCircle.setFill(Color.BLUE);
					}
					if(titan instanceof AbnormalTitan) {
						titanCircle.setFill(Color.BROWN);
					}
					if(titan instanceof ColossalTitan) {
						titanCircle.setFill(Color.DARKGREEN);
					}
					if(titan.getCurrentHealth() <= 0) {
						gridPane.getChildren().remove(titanCircle);
					}

                }
            }
        
        });

        passTurnButton.setTranslateX(-100);
        actionPane.getChildren().addAll(purchaseButton, passTurnButton);
        actionPane.setPadding(new Insets(0, 0, 20, 0));

        return actionPane;
    }

    private void passTurn() {
        battle.passTurn();
        System.out.println("Passing turn...");
        updateUI();
    }

    private void initializeLanes() {
        for (int i = 0; i < battle.getOriginalLanes().size(); i++) {
            Lane lane = battle.getOriginalLanes().get(i);

            VBox laneBox = new VBox();
            laneBox.setAlignment(Pos.CENTER_LEFT);
            laneBox.setSpacing(5);

            Label laneLabel = new Label("Lane " + (i + 1));
            laneLabel.setPrefSize(laneWidth, laneHeight);
            laneBox.getChildren().add(laneLabel);

            Label dangerLevelLabel = new Label("Danger Level: " + lane.getDangerLevel());
            laneBox.getChildren().add(dangerLevelLabel);

            gridPane.add(laneBox, 0, i);

            Label titanSpawnLabel = new Label("Titan Spawn " + (i + 1));
            titanSpawnLabel.setAlignment(Pos.CENTER);
            gridPane.add(titanSpawnLabel, 1, i);
        }
    }

    private void updateUI() {
        // Clear existing Titan circles from the grid
        for (Iterator<javafx.scene.Node> it = gridPane.getChildren().iterator(); it.hasNext();) {
            javafx.scene.Node node = it.next();
            if (node instanceof Circle) {
                it.remove();
            }
        }

        // Add Titans as circles to the grid
        for (int i = 0; i < battle.getOriginalLanes().size(); i++) {
            Lane lane = battle.getOriginalLanes().get(i);
            for (Titan titan : lane.getTitans()) {
                Circle titanCircle = createTitanCircle(titan);
                gridPane.add(titanCircle, 1, i);
            }
        }

        // Update the labels in the info VBox
        Label labelLaneCount = (Label) vBoxInfo.getChildren().get(0);
        Label labelScore = (Label) vBoxInfo.getChildren().get(1);
        Label labelResources = (Label) vBoxInfo.getChildren().get(2);
        Label labelTurns = (Label) vBoxInfo.getChildren().get(3);
        Label labelPhase = (Label) vBoxInfo.getChildren().get(4);

        labelLaneCount.setText("Lane Count: " + battle.getOriginalLanes().size());
        labelScore.setText("Score: " + battle.getScore());
        labelResources.setText("Total Resources: " + battle.getResourcesGathered());
        labelTurns.setText("Number of Turns: " + battle.getNumberOfTurns());
        labelPhase.setText("Phase: " + battle.getBattlePhase());
    }

    private Circle createTitanCircle(Titan titan) {
        Circle circle = new Circle(20);
        circle.setFill(Color.RED);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        // Adjust position if needed
        return circle;
    }

    public void addWeaponImageToLane(int laneIndex, ImageView weaponImageView) {
        gridPane.add(weaponImageView, 0, laneIndex);
    }
}
