import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
public class Scene3MultiplicationController {
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
    void moveToSceneMatrixMatrix(ActionEvent event) throws IOException {   
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene4_1_Mult_matrix_matrix.fxml"));
        Parent root = loader.load();
        Scene4_1_Mult_matrix_matrixController controller = loader.getController();
        Scene newScene = new Scene(root);
        controller.setScene(newScene);
        controller.generateMatrix();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(newScene);
        stage.show();
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @FXML
    void moveToSceneMatrixVector(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene4_2_Mult_matrix_vector.fxml"));
        Parent root = loader.load();
        Scene4_2_Mult_matrix_vectorController controller = loader.getController();
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
