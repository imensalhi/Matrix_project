import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class scene5_0_ResolutionController {

    private Stage stage;
    private Scene scene;
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

    @FXML
    void moveToIDirecte(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene5_DirectResolution.fxml"));
        Parent root = loader.load();
        scene5_DirectResolution controller = loader.getController();
        Scene newScene = new Scene(root);
        controller.setScene(newScene);
        controller.generateMatrixVector();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }

    @FXML
    void moveToItterative(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene5_IterativeResolution.fxml"));
        Parent root = loader.load();
        scene5_IterativeResolution controller = loader.getController();
        Scene newScene = new Scene(root);
        controller.setScene(newScene);
        controller.generateMatrixVector();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }

    @FXML
    void moveToSysTriang(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene5_SystemTriangResolution.fxml"));
        Parent root = loader.load();
        scene5_SystemTriangResolution controller = loader.getController();
        Scene newScene = new Scene(root);
        controller.setScene(newScene);
        controller.generateMatrixVector();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene); 
        stage.show();
    }
    
    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
    }
}
