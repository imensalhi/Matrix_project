
public class Multiply {
    //VALIDEEEEEEE
    public double[][] produitDenseDense(double[][] matrix1, double[][] matrix2) {
        int n = matrix1.length;

        double[][] result = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double sum = 0.0;
                for (int k = 0; k < n; k++) {
                    sum += matrix1[i][k] * matrix2[k][j];
                }
                result[i][j] = sum;
            }
        }
        return result;
    }

    
    /// Fonction pour multiplier une matrice bande par une matrice demi-bande inférieure
 public   double[][] multiplierMatricesBandeDemiBande(double[][] matriceBande, double[][] matriceDemiBande) {
    int n = matriceBande.length;//nb des lignes et des colonnes dans les deux matrice 
    int m = determineLargeurDemiBandeInf(matriceDemiBande);
    double[][] resultat = new double[n][n];
    if (n != matriceDemiBande.length) {
        throw new IllegalArgumentException("La taille des deux matrice doit être égaux.");
    }
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            // Initialiser la valeur du résultat à zéro
            resultat[i][j] = 0;

            // Calculer la somme des produits des éléments
            for (int k =Math.max(j,i-m); k <=Math.min(n-1, j + m); k++) {
                
                resultat[i][j] += matriceBande[i][k] * matriceDemiBande[k][j];
            }
        }
    }

    return resultat;}
// Fonction pour multiplier une matrice demi-bande inférieure par une matrice demi-bande supérieure
public  double[][] multiplierMatricesDemiBande(double[][] matriceDemiBandeInf, double[][] matriceDemiBandeSup) {
 int n = matriceDemiBandeInf.length;
 int r = matriceDemiBandeInf[0].length - 1; // Largeur de la bande inférieure
 int s = matriceDemiBandeSup[0].length - 1; // Largeur de la bande supérieure

 double[][] resultat = new double[n][n];

 for (int i = 0; i < n; i++) {
     for (int j =0; j < n; j++) {
          resultat[i][j]= 0.0;

         // Calcul de la somme des produits
          for (int k = Math.max(0, i - r), l = Math.max(0, j - s); k <= i && l <= j; k++, l++) {
              resultat[i][j] += matriceDemiBandeInf[i][k] * matriceDemiBandeSup[l][j];
          }

          /***** ou bien (methode2)
           * for (int k =Math.min (Math.max(0, i - r),  Math.max(0, j - s)); k<= Math.min(i,j); k++) {
             resultat[i][j] += matriceDemiBandeInf[i][k] * matriceDemiBandeSup[k][j];
         }*/

         
     }
 }

 return resultat;
}


// Fonction pour multiplier une matrice bande par son inverse (calculé par Gauss-Jordan)
public  double[][] multiplierMatriceBandeParInverse(double[][] matriceBande) {
 int n = matriceBande.length;
 int largeurBande = determineLargeurDemiBandeInf(matriceBande);
 double [][]resultat=new double[n][n];
 // Créer une matrice augmentée pour stocker à la fois la matrice originale et l'identité
 double[][] matriceAugmentee = new double[n][2 * n];

 // Initialiser la matrice augmentée
 for (int i = 0; i < n; i++) {
     for (int j = 0; j < n; j++) {
         matriceAugmentee[i][j] = matriceBande[i][j];
         // Ajouter l'identité à droite de la matrice originale
         matriceAugmentee[i][j + n] = (i == j) ? 1 : 0;
     }
 }

 // Appliquer la méthode de Gauss-Jordan
 for (int i = 0; i < n; i++) {

     // Réduire la ligne pivot à 1
     double pivot = matriceAugmentee[i][i];
     for (int j = 0; j < 2 * n; j++) {
         matriceAugmentee[i][j] /= pivot;
     }

     // Éliminer les autres lignes
     for (int k = 0; k < n; k++) {
         if (k != i) {
             double factor = matriceAugmentee[k][i];
             for (int j = Math.max(0, i -largeurBande); j < 2 * n; j++) {
                 matriceAugmentee[k][j] -= factor * matriceAugmentee[i][j];
             }
         }
     }
 }

 // Extraire la matrice inverse de la partie à droite de la matrice augmentée
 double[][] matriceInverse = new double[n][n];
 for (int i = 0; i < n; i++) {
     System.arraycopy(matriceAugmentee[i], n, matriceInverse[i], 0, n);
 }
//**2 etape de multuplication 
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            // Initialiser la valeur du résultat à zéro
            resultat[i][j] = 0;

            // Calculer la somme des produits des éléments
            for (int k = Math.max(0, i -largeurBande); k <= Math.min(n - 1, i + largeurBande); k++) {
                resultat[i][j] += matriceBande[i][k] * matriceInverse[k][j];
        }
    }
    }
    return resultat;
}
    
public double[][] inverseMatrix(double[][] matrix) {
    int n = matrix.length;
    int bandWidth = determineLargeurDemiBandeInf(matrix);
    double[][] augmentedMatrix = new double[n][2 * n];

    // Initialize the augmented matrix
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            augmentedMatrix[i][j] = matrix[i][j];
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
                for (int j = Math.max(0, i - bandWidth); j < 2 * n; j++) {
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

    //Valideeeeeee
    public double[][] multiplierMatriceBandeParTransposee(double[][] matriceBande, double[][] matriceTransposee) {
        int n = matriceBande.length;
        int m = (matriceBande[0].length - 1) / 2;
        int p = matriceTransposee[0].length;
        double[][] resultat = new double[n][p];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                for (int k = 0; k < 2 * m + 1; k++) {
                    resultat[i][j] += matriceBande[i][k] * matriceTransposee[k][j];
                }
            }
        }
        return resultat;
    }
    //valideeeeeeeeee
    public double[] dense(double[][] matrix, double[] vector) {
        int numRows = matrix.length;
        int numCols = matrix[0].length;

        if (numCols != vector.length) {
            throw new IllegalArgumentException("Incompatible matrix and vector dimensions");
        }

        double[] result = new double[numRows];
        
        
        for (int i = 0; i < numRows; i++) {
            result[i]=0;
            for (int j = 0; j < numCols; j++) {
                result[i] = result[i]+matrix[i][j] * vector[j];
            }
        }
        return result;
    }
    
    //Valideeeeeeeeeeee

    public  double[] tringinf(double[][] matrice, double[] vecteur) {
        int size = matrice.length;
        if (size != vecteur.length) {
            throw new IllegalArgumentException("La taille de la matrice doit être égale à la taille du vecteur.");
        }
        double[] result = new double[size];
        for (int i = 0; i < size; i++) {
            result[i] = 0;
            for (int j = 0; j <= i; j++) {
                result[i] += matrice[i][j] * vecteur[j];
            }
        }
        return result;
    }

    //Valideeeeeee

    public  double[] tringsup(double[][] matrice, double[] vecteur) {
        int size = matrice.length;
        if (size != vecteur.length) {
            throw new IllegalArgumentException("La taille de la matrice doit être égale à la taille du vecteur.");
        }
        double[] result = new double[size];
        for (int i = 0; i < size; i++) {
            result[i] = 0;
            for (int j = i; j < size; j++) {
                result[i] += matrice[i][j] * vecteur[j];
            }
        }
        return result;
    }

    
    public double[] infdemibande(double[][] matrice, double[] vecteur) {
        int m = determineLargeurDemiBandeInf(matrice);
        int size = matrice.length;
    
        if (size != vecteur.length) {
            throw new IllegalArgumentException("La taille de la matrice doit être égale à la taille du vecteur.");
        }
    
        double[] result = new double[size];
    
        for (int i = 0; i < size; i++) {
            result[i] = 0;
            for (int j = Math.max(0, i - m); j <= i; j++) {
                result[i] += matrice[i][j] * vecteur[j];
            }
        }
        return result;
    }

    //valideeeeeeeeee

    public double[] supdemibande(double[][] matrice, double[] vecteur) {
        int m = determineLargeurDemiBandeSup(matrice);
        int size = matrice.length;
    
        if (size != vecteur.length) {
            throw new IllegalArgumentException("La taille de la matrice doit être égale à la taille du vecteur.");
        }
    
        double[] result = new double[size];
    
        for (int i = 0; i < size; i++) {
            result[i] = 0;
            for (int j = i; j <= Math.min(size - 1, i + m); j++) {
                result[i] += matrice[i][j] * vecteur[j];
            }
        }
    
        return result;
    }

    // Méthode pour déterminer la largeur de bande inferieur
    public int determineLargeurDemiBandeInf(double[][] matrice) {
        int size = matrice.length;
        int m = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Vérifie si l'élément est en dehors de la demi-bande inférieure
                if (j > i) {
                    break;
                }
                // Vérifie si l'élément est non nul
                if (matrice[i][j] != 0) {
                    m = Math.max(m, i - j);
                }
            }
        }

        return m;
    }

    // Méthode pour déterminer la largeur de bande superieur
    public int determineLargeurDemiBandeSup(double[][] matrice) {
        int m = 0;

        for (int i = 0; i < matrice.length; i++) {
            for (int j = i + 1; j < matrice[i].length; j++) {
                // Si l'élément n'est pas nul, il est dans la bande supérieure
                if (matrice[i][j] != 0) {
                    m++;
                }
            }
        }

        return m;
}


}


