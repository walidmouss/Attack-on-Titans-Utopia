package game.gui;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import game.engine.Battle;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.lanes.Lane;

public class WeaponShop {

    // ShowWeaponFactoryScene method for HardModeClass
    public static void ShowWeaponFactoryScene(Stage primaryStage, Main mainApp, Battle battle, Scene gameScene, hardModeClass hardMode) {
        createWeaponFactoryScene(primaryStage, mainApp, battle, gameScene, hardMode, null);
    }

    // ShowWeaponFactoryScene method for EasyModeClass
    public static void ShowWeaponFactoryScene(Stage primaryStage, Main mainApp, Battle battle, Scene gameScene, easyModeClass easyMode) {
        createWeaponFactoryScene(primaryStage, mainApp, battle, gameScene, null, easyMode);
    }

    // General method to create the weapon factory scene
    private static void createWeaponFactoryScene(Stage primaryStage, Main mainApp, Battle battle, Scene gameScene, hardModeClass hardMode, easyModeClass easyMode) {
        BorderPane shop = new BorderPane();
        GridPane weapons = new GridPane();
        weapons.setGridLinesVisible(true);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setItems(FXCollections.observableArrayList(
                "Lane 1", "Lane 2", "Lane 3"));
        comboBox.getSelectionModel().selectFirst();

        int[] selectedLane = new int[1];

        comboBox.setOnAction(e -> {
            int selectedIndex = comboBox.getSelectionModel().getSelectedIndex();
            selectedLane[0] = selectedIndex;
            System.out.println("Selected Lane: " + selectedLane[0]);
        });

        VBox dropDown = new VBox(comboBox);
        dropDown.setAlignment(Pos.CENTER_LEFT); // Align to the left
        dropDown.setSpacing(20);
        dropDown.setPadding(new Insets(20));

        int[] selectedWeapon = new int[1];

        Button weapon1 = new Button("Piercing Cannon");
        weapon1.setOnAction(e -> selectedWeapon[0] = 1);
        Button weapon2 = new Button("Sniper Cannon");
        weapon2.setOnAction(e -> selectedWeapon[0] = 2);
        Button weapon3 = new Button("Volley Spread Cannon");
        weapon3.setOnAction(e -> selectedWeapon[0] = 3);
        Button weapon4 = new Button("Wall Trap");
        weapon4.setOnAction(e -> selectedWeapon[0] = 4);
        Button add = new Button("Add Weapon");
        Button backButton = new Button("Back to Game");

        add.setOnAction(e -> {
            try {
                Lane currentLane = battle.getOriginalLanes().get(selectedLane[0]);
                System.out.println("Titans of Lane: " + currentLane.getTitans());
                System.out.println("Health of Wall: " + currentLane.getLaneWall().getCurrentHealth());

                battle.purchaseWeapon(selectedWeapon[0], currentLane);

                Image weaponImage = null;
                switch (selectedWeapon[0]) {
                    case 1:
                        weaponImage = new Image("piercing arrow.png");
                        break;
                    case 2:
                        weaponImage = new Image("Sniper.jpeg");
                        break;
                    case 3:
                        weaponImage = new Image("VoliSpreadCannon.png");
                        break;
                    case 4:
                        weaponImage = new Image("wallTrap.jpeg");
                        break;
                }

                if (weaponImage != null) {
                    ImageView weaponImageView = new ImageView(weaponImage);
                    weaponImageView.setFitWidth(50);
                    weaponImageView.setFitHeight(50);

                    if (hardMode != null) {
                        hardMode.addWeaponImageToLane(selectedLane[0], weaponImageView);
                    } else if (easyMode != null) {
                        easyMode.addWeaponImageToLane(selectedLane[0], weaponImageView);
                    }
                }

                primaryStage.setScene(gameScene);
            } catch (InsufficientResourcesException ex) {
                ex.printStackTrace();
                // Show popup for insufficient resources
                showInsufficientResourcesPopup(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Action for the back button
        backButton.setOnAction(e -> primaryStage.setScene(gameScene));

        weapon1.setPrefSize(300, 200);
        weapon2.setPrefSize(300, 200);
        weapon3.setPrefSize(300, 200);
        weapon4.setPrefSize(300, 200);
        add.setPrefSize(100, 50);
        backButton.setPrefSize(100, 50);

        weapons.add(weapon1, 0, 0);
        weapons.add(weapon2, 1, 0);
        weapons.add(weapon3, 0, 1);
        weapons.add(weapon4, 1, 1);

        StackPane buttons = new StackPane(dropDown, add, backButton); // Include the back button
        StackPane.setAlignment(dropDown, Pos.TOP_LEFT); // Align to the top left
        StackPane.setAlignment(add, Pos.BOTTOM_CENTER); // Center the add button
        StackPane.setAlignment(backButton, Pos.BOTTOM_RIGHT); // Align the back button to the bottom right
        buttons.setPadding(new Insets(10)); // Add padding to the buttons container

        shop.setBottom(buttons);
        shop.setCenter(weapons);

        Scene scene = new Scene(shop, 600, 500);
        primaryStage.setScene(scene);
    }

    // Method to show a popup for insufficient resources
    private static void showInsufficientResourcesPopup(Stage primaryStage) {
        Stage popupStage = new Stage();
        popupStage.initOwner(primaryStage);
        popupStage.initModality(Modality.WINDOW_MODAL);

        Label messageLabel = new Label("You do not have enough resources to purchase this weapon.");

        Button closeButton = new Button("OK");
        closeButton.setOnAction(e -> popupStage.close());

        VBox popupLayout = new VBox(20);
        popupLayout.setAlignment(Pos.CENTER);
        popupLayout.getChildren().addAll(messageLabel, closeButton);

        Scene popupScene = new Scene(popupLayout, 300, 200);

        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }
}
