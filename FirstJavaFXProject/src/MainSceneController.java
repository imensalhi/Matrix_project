import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainSceneController {

    private Stage stage;
    private Scene scene;
    @FXML
    private TextField tfTitle;
    
    @FXML
    private AnchorPane anchorPane;
    
    public void initialize() {
        Image backgroundImage = new Image(getClass().getResourceAsStream("/icones/bg.jpeg"));

        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );

        anchorPane.setBackground(new Background(background));
    }

    // Call this method when you want to initialize the background, e.g., when switching scenes
    public void initializeBackground() {
        initialize();
    }
    @FXML
    void moveToScene_2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }


    @FXML
    private void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Your Matrix Application");

        // Set the application icon
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icones/locator.png")));

        // Create a GridPane for a more customized layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 20px;");

        // Add application logo
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/icones/matrix.png")));
        logo.setFitWidth(50);
        logo.setFitHeight(50);
        gridPane.add(logo, 0, 0);

        // Add application name
        Label appNameLabel = new Label("Matrix Manipulator");
        appNameLabel.setStyle("-fx-font-size: 21px; -fx-font-weight: bold; -fx-font-family: 'Bodoni MT';");
        gridPane.add(appNameLabel, 1, 0);

        // Add version
        Label versionLabel = new Label("Version 1.0");
        versionLabel.setStyle("-fx-font-size: 19px; -fx-text-fill: #555; -fx-font-weight: bold; -fx-font-family: 'Bodoni MT';");
        gridPane.add(versionLabel, 1, 1);

        // Add description
        Label descriptionLabel = new Label("This application is designed to facilitate the manipulation and resolution of matrix."
                + "\n\nKey Features:\n- Create, edit, and perform operations on matrices."
                + "\n- Solve linear systems and equations using matrix methods."
                + "\n- Visualize matrix transformations and operations."
                + "\n\nWhether you are a student studying linear algebra or a professional , "
                + "dealing with matrix computations this tool aims to simplify your matrix-related tasks.\n"
                
        );
        descriptionLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #333; -fx-font-family: 'Bodoni MT';");
        gridPane.add(descriptionLabel, 0, 2, 2, 1);

        Label creatorsLabel = new Label("Created by: Hamza Zaraii , Imen Salhi , Ibrahim GHali , Ala Eddine Zalfani");
        creatorsLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #555; -fx-font-family: 'Algerian';");
        gridPane.add(creatorsLabel, 0, 3, 2, 1);

        alert.getDialogPane().setContent(gridPane);

        alert.showAndWait();
    }

    
    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
    }
    
    

}
