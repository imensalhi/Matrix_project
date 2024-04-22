import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
public class scene5_DirectResolution implements Initializable{
    private Scene scene;
    private Stage stage;

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

    private double[][] matrix1;
    private double[] vector;
    
    @FXML
    public void generateMatrixVector() {
        // Assuming the matrixPane1 and matrixPane2 GridPanes are defined in the FXML file
        GridPane matrixPane1 = (GridPane) scene.lookup("#matrixPane1");
        GridPane matrixPane2 = (GridPane) scene.lookup("#matrixPane2");
    
        if (matrixPane1 != null && matrixPane2 != null) {
            matrixPane1.getChildren().clear();
            matrixPane2.getChildren().clear();
    
            // Use the logic from the App class to generate the matrices
            int size = getSizeFromTextField(); // Implement a method to get the size from the TextField
            int length = size;
            int width = size;
    
            // Create data structures to store the user inputs for each matrix
            matrix1 = new double[length][width];
            vector = new double[length];
    
            for (int y = 0; y < length; y++) {
                for (int x = 0; x < width; x++) {
                    TextField tf1 = new TextField();
                    tf1.setText("0");
                    tf1.setPrefHeight(50);
                    tf1.setPrefWidth(50);
                    tf1.setAlignment(Pos.CENTER);
                    GridPane.setRowIndex(tf1, y);
                    GridPane.setColumnIndex(tf1, x);
                    matrixPane1.getChildren().add(tf1);
    
                    // Add a listener to each TextField to capture user inputs for matrix
                    final int row1 = y;
                    final int column1 = x;
                    tf1.textProperty().addListener((observable, oldValue, newValue) ->
                            matrix1[row1][column1] = Double.parseDouble(newValue));
                }
            }
    
            for (int z = 0; z < length; z++) {
                TextField tf2 = new TextField();
                tf2.setText("0");
                tf2.setPrefHeight(50);
                tf2.setPrefWidth(50);
                tf2.setAlignment(Pos.CENTER);
                GridPane.setRowIndex(tf2, z);
                matrixPane2.getChildren().add(tf2);
    
                // Add a listener to each TextField to capture user inputs for vector
                final int index = z;
                tf2.textProperty().addListener((observable, oldValue, newValue) ->
                        vector[index] = Double.parseDouble(newValue));
            }
        }
    }
    
    // Access the matrix and vector from other parts of your code
    public double[][] getMatrix1() {
        return matrix1;
    }
    
    public double[] getVector() {
        return vector;
    }

    public int getSizeFromTextField() {
        TextField tfsize = (TextField) scene.lookup("#tfsize");
        if (tfsize != null) {
            try {
                return Integer.parseInt(tfsize.getText());
            } catch (NumberFormatException e) {
                // Handle the case where the input is not a valid integer
                e.printStackTrace();
            }
        }
    return 0;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
    
    public void printMatrices() {
        System.out.println("Matrix :");
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[i].length; j++) {
                System.out.print(matrix1[i][j] + " ");
            }
            System.out.println();
        }
    
        System.out.println("Vector :");
        for (int i = 0; i < vector.length; i++) {
                System.out.print(vector[i] + " ");
            }
            System.out.println();
        
    }

    @FXML
    void help1(ActionEvent event) {
        TypeRecognizer type = new TypeRecognizer();
        String description;
        String imagePath = "path/to/default/image.png"; // Default image path

        switch (type.recognize(matrix1)) {
            case 1:
                description = "Upper Triangular Matrix\n\nAll elements below the main diagonal are zero. "
                        + "It simplifies computations as zeros eliminate unnecessary calculations in algorithms.";
                imagePath = "/icones/superieur.png";
                break;
            case 2:
                description = "Lower Triangular Matrix\n\nAll elements above the main diagonal are zero. "
                        + "It's commonly used in linear algebra for efficient storage and manipulation of data.";
                imagePath = "/icones/inferieur.png";        
                break;
            case 5:
                description = "Banded Matrix\n\nNon-zero elements are concentrated along the main diagonal, "
                        + "with zeros elsewhere. This structure is useful in various scientific and engineering applications.";
                imagePath = "/icones/bande.png";
                break;
            case 3:
                description = "Upper Half-Banded Matrix\n\nNon-zero elements are in the upper triangular region, "
                        + "creating a band that extends above the main diagonal. It offers advantages in storage efficiency.";
                imagePath = "/icones/demi_bande_seper.png";
                break;
            case 4:
                description = "Lower Half-Banded Matrix\n\nNon-zero elements are in the lower triangular region, "
                        + "forming a band below the main diagonal. It optimizes computations by eliminating unnecessary calculations.";
                imagePath = "/icones/demi_bande_infer.png";
                break;
            default:
                description = "Dense Matrix\n\nNon-zero elements are spread across the matrix. This is a general-purpose matrix "
                        + "with no specific structural constraints, suitable for a wide range of mathematical operations.";
                break;
        }

        displayAlert(description, imagePath);
    }

    private void displayAlert(String description, String imagePath) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Matrix Description");
        alert.setHeaderText(description);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icones/locator.png")));

        // Create an ImageView to display the matrix image
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        alert.getDialogPane().setGraphic(imageView);

        alert.showAndWait();
    }


    @FXML
    void help2(ActionEvent event) {
        

    }
    
    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("scene5_0_Resolution.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene); 
        stage.show();
    }

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void calculer(ActionEvent event) throws IOException {
        // Create a 1x9 vector filled with 1s (you can modify the size as needed)
        //int[] vector = {1, 1, 1};

        // Get the matrixPane3 GridPane from the scene
        GridPane matrixPane3 = (GridPane) scene.lookup("#matrixPane3");

        // Clear existing children in matrixPane3
        matrixPane3.getChildren().clear();

        Resolution resolution = new Resolution();

        String selectedOperation = choice.getValue();

        double[] resultvector = new double[matrix1.length];

        switch (selectedOperation) {
            case "Gaus-dense":
            resultvector = resolution.resoudreAvecGauss(matrix1, vector);
                break;

            case "Gaus-jordan-dense":
            resultvector = resolution.resoudreAvecGaussJordan(matrix1, vector);
                break;

            case "LU-dense":
            resultvector = resolution.resoudreavecLU(matrix1, vector);
                break;
                
            case "Gaus avec pivotage-dense":
            resultvector = resolution.GaussPivotage(matrix1, vector);
                break;
                
            case "Cholesky-dense":
            resultvector = resolution.resoudreCholesky(matrix1, vector);
                break;
            case "Gaus-bande":
            resultvector = resolution.gaussBande(matrix1, vector);
                break;

            case "Gaus-jordan-bande":
            resultvector = resolution.JordanBande(matrix1, vector);
                break;

            case "LU-bande":
            resultvector = resolution.LUBande(matrix1, vector);
                break;
                
            case "Gaus avec pivotage-bande":
            resultvector = resolution.gaussavecpivotageBande(matrix1, vector);
                break;
                
            case "Cholesky-bande":
            resultvector = resolution.CholeskyBande(matrix1, vector);
                break;         
        } 

        // Display the vector in matrixPane3 using TextField
        for (int i = 0; i < vector.length; i++) {
            TextField textField = new TextField(Double.toString(resultvector[i]));
            textField.setPrefHeight(50);
            textField.setPrefWidth(50);
            textField.setAlignment(Pos.CENTER);
            textField.setEditable(false); // Make it non-editable
            GridPane.setRowIndex(textField, i); // Display in a single row
            GridPane.setColumnIndex(textField, 0);
            matrixPane3.getChildren().add(textField);
        }
    }

    @FXML
    private ChoiceBox<String> choice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choice.getItems().addAll("Gaus-dense","Gaus-jordan-dense","LU-dense","Gaus avec pivotage-dense","Cholesky-dense","Gaus-bande","Gaus-jordan-bande","LU-bande","Gaus avec pivotage-bande","Cholesky-bande");
    }
}
