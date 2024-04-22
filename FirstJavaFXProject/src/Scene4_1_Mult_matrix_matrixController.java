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

public class Scene4_1_Mult_matrix_matrixController implements Initializable {
    private Scene scene;
    private Stage stage;

    @FXML
    private AnchorPane anchorPane;

    public void initialize() {
        try {
            Image backgroundImage = new Image(getClass().getResource("/icones/bg2.jpeg").toExternalForm());
            System.out.println("Background Image Loaded Successfully");
            BackgroundImage background = new BackgroundImage(
                    backgroundImage,
                    BackgroundRepeat.REPEAT,
                    BackgroundRepeat.REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            anchorPane.setBackground(new Background(background));
        } catch (Exception e) {
            System.out.println("Error loading background image: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    // Declare matrix1 and matrix2 as instance variables
    private double[][] matrix1;
    private double[][] matrix2;

    @FXML
    public void generateMatrix() {
        // Assuming the matrixPane1 and matrixPane2 GridPanes are defined in the FXML
        // file
        GridPane matrixPane1 = (GridPane) scene.lookup("#matrixPane1");
        GridPane matrixPane2 = (GridPane) scene.lookup("#matrixPane2");

        if (matrixPane1 != null && matrixPane2 != null) {
            matrixPane1.getChildren().clear();
            matrixPane2.getChildren().clear();

            // Use the logic from the App class to generate the matrices
            int size = getSizeFromTextField(); // Implement a method to get the size from the TextField
            int length = size;
            int width = size;

            // Create a data structure to store the user inputs for each matrix
            matrix1 = new double[length][width];
            matrix2 = new double[length][width];

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

                    TextField tf2 = new TextField();
                    tf2.setText("0");
                    tf2.setPrefHeight(50);
                    tf2.setPrefWidth(50);
                    tf2.setAlignment(Pos.CENTER);
                    GridPane.setRowIndex(tf2, y);
                    GridPane.setColumnIndex(tf2, x);
                    matrixPane2.getChildren().add(tf2);

                    // Add a listener to each TextField to capture user inputs
                    tf1.textProperty().addListener((observable, oldValue,
                            newValue) -> matrix1[GridPane.getRowIndex(tf1)][GridPane.getColumnIndex(tf1)] = Double
                                    .parseDouble(newValue));

                    tf2.textProperty().addListener((observable, oldValue,
                            newValue) -> matrix2[GridPane.getRowIndex(tf2)][GridPane.getColumnIndex(tf2)] = Double
                                    .parseDouble(newValue));
                }
            }
        }
    }

    // Access the matrices from other parts of your code
    public double[][] getMatrix1() {
        return matrix1;
    }

    public double[][] getMatrix2() {
        return matrix2;
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

    @FXML
    public double[][] inverseMatrix(double[][] matrix1) {
        int n = matrix1.length;
        Multiply multiply = new Multiply();
        int largeurBande = multiply.determineLargeurDemiBandeInf(matrix1);
        double[][] augmentedMatrix = new double[n][2 * n];

        // Initialize the augmented matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmentedMatrix[i][j] = matrix1[i][j];
                augmentedMatrix[i][j + n] = (i == j) ? 1 : 0;
            }
        }

        // Apply Gauss-Jordan elimination
        for (int i = 0; i < n; i++) {
            double pivot = augmentedMatrix[i][i];

            // Reduce the pivot row to 1
            for (int j = 0; j < 2 * n; j++) {
                augmentedMatrix[i][j] /= pivot;
            }

            // Eliminate other rows
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = augmentedMatrix[k][i];
                    for (int j = Math.max(0, i - largeurBande); j < 2 * n; j++) {
                        augmentedMatrix[k][j] -= factor * augmentedMatrix[i][j];
                    }
                }
            }
        }

        // Extract the inverse matrix from the augmented matrix
        double[][] inverseMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(augmentedMatrix[i], n, inverseMatrix[i], 0, n);
        }

        return inverseMatrix;
    }

    @FXML
    public void displayInverseMatrix() {
        // Assuming matrixPane2 is defined in the FXML file
        GridPane matrixPane2 = (GridPane) scene.lookup("#matrixPane2");

        // Clear existing children in matrixPane2
        matrixPane2.getChildren().clear();

        // Check if the inverse matrix is available
        if (matrix1 != null) {
            // Calculate the inverse matrix
            double[][] inverseMatrix = inverseMatrix(matrix1);

            // Display the inverseMatrix in matrixPane2 using TextField
            for (int y = 0; y < inverseMatrix.length; y++) {
                for (int x = 0; x < inverseMatrix.length; x++) {
                    TextField textField = new TextField(Double.toString(inverseMatrix[y][x]));
                    textField.setPrefHeight(50);
                    textField.setPrefWidth(50);
                    textField.setAlignment(Pos.CENTER);
                    textField.setEditable(false); // Make it non-editable
                    GridPane.setRowIndex(textField, y);
                    GridPane.setColumnIndex(textField, x);
                    matrixPane2.getChildren().add(textField);
                }
            }
        } else {
            // Handle the case where matrix1 is not initialized
            System.out.println("Matrix1 is not initialized. Generate matrices first.");
        }
    }

    public static void printMatrices(double[][] matrix) {
        System.out.println("Matrix :");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Scene3_Multiplication.fxml"));
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
        // Get the matrixPane3 GridPane from the scene
        GridPane matrixPane3 = (GridPane) scene.lookup("#matrixPane3");

        // Clear existing children in matrixPane3
        matrixPane3.getChildren().clear();

        Multiply multiply = new Multiply();
        // Get the selected item from the choice box
        String selectedOperation = choice.getValue();

        // Perform matrix multiplication based on the user's choice
        double[][] resultMatrix = new double[matrix1.length][matrix1.length];

        TypeRecognizer type = new TypeRecognizer();

        switch (selectedOperation) {
            case "Dense * Dense":

                resultMatrix = multiply.produitDenseDense(matrix1, matrix2);
                break;
            case "DemibandeInf * DemibandeSupSup":
                // Implement the multiplication of lower triangular and upper triangular
                // matrices
                if ((type.recognize(matrix1) != 4) || (type.recognize(matrix2) != 3)) {
                    displayAlert("the first and the second matrix should be of the type DemibandeInf and DemibandeSup");
                } else
                    resultMatrix = multiply.multiplierMatricesDemiBande(matrix1, matrix2);
                break;
            case "Bande * DemibandeInf":
                // Implement the multiplication of banded matrix and dense matrix
                if ((type.recognize(matrix1) != 5) || (type.recognize(matrix2) != 4)) {
                    displayAlert("the first and the second matrix should be of the type Bande and DemibandeInf");
                } else
                    resultMatrix = multiply.multiplierMatricesBandeDemiBande(matrix1, matrix2);
                break;
            case "Bande * Inverse":
                // Implement the multiplication of dense matrix and upper triangular matrix
                if ((type.recognize(matrix1) != 5)) {
                    displayAlert("the first matrix should be of the type bande ");
                } else
                    resultMatrix = multiply.multiplierMatriceBandeParInverse(matrix1);
                break;
            case "Bande * transpose":
                if ((type.recognize(matrix1) != 5)) {
                    displayAlert("the first matrix should be of the type bande");
                } else
                    // Implement the multiplication of lower triangular matrix and dense matrix
                    resultMatrix = multiply.multiplierMatriceBandeParTransposee(matrix1, matrix2);
                break;
            // Add more cases as needed for other matrix multiplication types
        }

        // Display the resultMatrix in matrixPane3 using TextField
        for (int y = 0; y < resultMatrix.length; y++) {
            for (int x = 0; x < resultMatrix.length; x++) {
                TextField textField = new TextField(Double.toString(resultMatrix[y][x]));
                textField.setPrefHeight(50);
                textField.setPrefWidth(50);
                textField.setAlignment(Pos.CENTER);
                textField.setEditable(false); // Make it non-editable
                GridPane.setRowIndex(textField, y);
                GridPane.setColumnIndex(textField, x);
                matrixPane3.getChildren().add(textField);
            }
        }
    }

    // 0 danse
    // 1 triang sup
    // 2 triang inf
    // 3 bande sup
    // 4 bande inf
    // 5 bande
    // 6 danse

    @FXML
    void help1(ActionEvent event) {

        TypeRecognizer type = new TypeRecognizer();
        String description;
        String imagePath = "/icones/matrice_dense.png"; // Default image path

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

    private void displayAlert(String description) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Matrix Description");
        alert.setHeaderText(description);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icones/locator.png")));

        alert.showAndWait();
    }

    @FXML
    void help2(ActionEvent event) {
        TypeRecognizer type = new TypeRecognizer();
        String description;
        String imagePath = "path/to/default/image.png"; // Default image path

        switch (type.recognize(matrix2)) {
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

    @FXML
    private ChoiceBox<String> choice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        choice.getItems().addAll("Dense * Dense", "DemibandeInf * DemibandeSupSup", "Bande * DemibandeInf",
                "Bande * Inverse", "Bande * transpose");
    }

    public static void main(String[] args) {
        /*
         * double[][] matrix={{1,0,0,0,0,0},
         * {1,1,0,0,0,0},
         * {1,1,1,0,0,0},
         * {0,1,1,1,0,0},
         * {0,0,1,1,1,0},
         * {0,0,0,1,1,1}};
         */

        // double [][] inverse= inverseMatrix(matrix);

        // printMatrices(inverse);
    }
}
