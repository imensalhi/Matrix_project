public class Matrix {
    private double[][] matrix;
    private int size;

    public Matrix(double[][] matrix2) {
        if (matrix2 != null && matrix2.length > 0 && matrix2[0].length > 0) {
            this.matrix = matrix2;
            this.size = matrix2.length;
        } else {
            throw new IllegalArgumentException("Matrix is null or empty");
        }
    }

    

    public double[][] getMatrixValues() {
        return matrix;
    }

    public int getSize() {
        return size;
    }
}
