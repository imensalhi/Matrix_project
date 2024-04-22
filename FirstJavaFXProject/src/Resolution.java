
import java.util.Arrays;

public class Resolution {
//-------------------------------------------------------------------------Résolution d’un système linéaire avec les Méthode directes(matrice dense)
  	//******************************************** Méthode d'élimination de Gauss avec une matrice dense 
	
    public  double[] resoudreAvecGauss(double[][] matrice, double[] vecteur) {
        int n = vecteur.length;
        double[] solution = Arrays.copyOf(vecteur, n);
//gauss ----> tringularisation du matrice 
        for (int k = 0; k < n - 1; k++) {
            for (int i = k + 1; i < n; i++) {
                double ratio = matrice[i][k] / matrice[k][k];
                for (int j = k+1; j < n; j++) {
                    matrice[i][j] -= ratio * matrice[k][j];
                }
                matrice[i][k]=0;
                solution[i] -= ratio * solution[k];// trait de second membre (le vecteur)
            }
        }
//resolution du systeme
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                solution[i] -= matrice[i][j] * solution[j];
            }
            solution[i] /= matrice[i][i];
        }

        return solution;
    }

                   //******************************************* Méthode d'élimination de Gauss-Jordan avec une matrice dense 
   
    
   public  double[] resoudreAvecGaussJordan(double[][] dense, double[] vecteur) {
        int n = vecteur.length;
        double[][] augmentedMatrix = new double[n][n + 1];

        // Création de la matrice augmentée
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmentedMatrix[i][j] = dense[i][j];
            }
            augmentedMatrix[i][n] = vecteur[i];
        }
        
     // Élimination de gauss-jordan
        for (int k = 0;k < n; k++) {
        	 for (int j = k+1; j <= n; j++) {
        		 augmentedMatrix[k][j]= augmentedMatrix[k][j]/augmentedMatrix[k][k]; 
        	 }
        	 augmentedMatrix[k][k]=1;
            for (int i = 0; i < n; i++) {
                if (i != k) {
                    for (int j = k+1; j <= n; j++) {
                        augmentedMatrix[i][j] -= augmentedMatrix[i][k] * augmentedMatrix[k][j];
                        
                    }
               augmentedMatrix[i][k]=0; }
                
            }
             
        }
     
        // Extraction de la solution
        double[] solution = new double[n];
        for (int i = 0; i < n; i++) {
            solution[i] = augmentedMatrix[i][n];
        }

        return solution;
    }

   //***************** Méthode de décomposition LU avec une matrice dense 
    public  double[] resoudreavecLU(double[][] matrice, double[] vecteur) {
        int n = matrice.length;
        double[][] l = new double[n][n];
        double[][] u = new double[n][n];

        // Étape de décomposition
        for (int i = 0; i < n; i++) {
            // Calcul de la matrice L et U
            for (int j = 0; j <= i; j++) {
                double sum = 0.0;
                for (int k = 0; k < j; k++) {
                    sum += l[i][k] * u[k][j];
                }

                if (i == j) {
                    l[i][j] = 1;
                } else {
                    l[i][j] = (matrice[i][j] - sum) / u[j][j];
                }
            }

            for (int j = i; j < n; j++) {
                double sum = 0.0;
                for (int k = 0; k < i; k++) {
                    sum += l[i][k] * u[k][j];
                }
                u[i][j] = matrice[i][j] - sum;
            }
        }

        // Résolution du système (deux matrices L et U)
        double[] y = new double[n];
        double[] solution = new double[n];

        for (int i = 0; i < n; i++) {
            double sum = 0.0;
            for (int j = 0; j < i; j++) {
                sum += l[i][j] * y[j];
            }
            y[i] = (vecteur[i] - sum) / l[i][i];
        }

        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += u[i][j] * solution[j];
            }
            solution[i] = (y[i] - sum) / u[i][i];
        }

        return solution;
    }
 //*********resoudre avec Méthode d’élimination de Gauss avec pivotage partiel (matrice dense)
    public  double[] GaussPivotage(double[][] matrice, double[] vecteur) {
        int n = vecteur.length;

        // Étape d'élimination
        for (int k = 0; k < n - 1; k++) {
            // Recherche du pivot partiel
            int pivotPartiel = k;
            for (int i = k + 1; i < n; i++) {
                if (Math.abs(matrice[i][k]) > Math.abs(matrice[pivotPartiel][k])) {
                    pivotPartiel = i;
                }
            }

            // Échange des lignes (pivotage partiel)
             for (int j = 0; j < n; j++) {
         
           double x = matrice[k][j];
            matrice[k] [j]= matrice[pivotPartiel][j];
            matrice[pivotPartiel] [j]= x;}

            double tempVecteur = vecteur[k];
            vecteur[k] = vecteur[pivotPartiel];
            vecteur[pivotPartiel] = tempVecteur;
            // Élimination
            for (int i = k + 1; i < n; i++) {
                double facteur = matrice[i][k] / matrice[k][k];
                for (int j = k+1; j < n; j++) {
                    matrice[i][j] -= facteur * matrice[k][j];
                }
                vecteur[i] -= facteur * vecteur[k];
            }
        }

        // resolution 
        double[] solution = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double somme = 0;
            for (int j = i + 1; j < n; j++) {
                somme += matrice[i][j] * solution[j];
            }
            solution[i] = (vecteur[i] - somme) / matrice[i][i];
        }
return solution;}

//**************************************************prend en compte que la matrise doit etre symetrique definie positive
    //**************************************cholsky matrice dense **********************************
    public  double[] resoudreCholesky(double[][] matrice, double[] vecteur) {
        //int n = vecteur.length;
        double[][] L = decompositionCholesky(matrice);

        // Résolution du système L*y = vecteur
        double[] y = resoudreTriangulaireInferieur(L, vecteur);

        // Transposition de la matrice L pour résoudre le système L^T*x = y
        double[][] LT = transposee(L);

        // Résolution du système L^T*x = y
        return resoudreTriangulaireSuperieur(LT, y);
    }
    //*********methode de cholsky
    public  double[][] decompositionCholesky(double[][] matrice) {
        int n = matrice.length;
        double[][] L = new double[n][n];
      for(int j=0;j<n;j++)
        {
        	L[j][j]=matrice[j][j];
        	for(int k=0;k<=j-1;k++)
        	{
        		L[j][j]=L[j][j]-L[j][k]*L[j][k];
        	}
        	L[j][j]=Math.sqrt(L[j][j]);
        	for(int i=j+1;i<n;i++)
        	{
        		L[i][j]=matrice[i][j];
        		for(int k=0;k<=j-1;k++)
        		{
        			L[i][j]=L[i][j]-L[i][k]*L[j][k];
        		}
        		L[i][j]=L[i][j]/L[j][j];
        	}
        }
     

        return L;
    }
// resoudre le systeme avec la matrice tringulaire inferieure 
    public double[] resoudreTriangulaireInferieur(double[][] matrice, double[] vecteur) {
        int n = vecteur.length;
        double[] solution = new double[n];

        for (int i = 0; i < n; i++) {
        	solution[i]=vecteur[i];

            for (int j = 0; j < i; j++) {
            	solution[i]= solution[i]-matrice[i][j] * solution[j];
            }

            solution[i] = solution[i] / matrice[i][i];
        }

        return solution;
    }
//resoudre le systeme avec la matrice tringulaire superieure (pour resoudre le systeme du transposer de l)
    public  double[] resoudreTriangulaireSuperieur(double[][] matrice, double[] vecteur) {
        int n = vecteur.length;
        double[] solution = new double[n];

        for (int i = n - 1; i >= 0; i--) {
           solution[i]=vecteur[i];
            for (int j = i + 1; j < n; j++) {
            	solution[i]=solution[i]- matrice[i][j] * solution[j];
            }

            solution[i] =solution[i] / matrice[i][i];
        }

        return solution;
    }
//calcule le transposer de facteur de cholisky 
    public double[][] transposee(double[][] matrice) {
        int n = matrice.length;
       
        double[][] transposee = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                transposee[i][j] = matrice[j][i];
            }
        }

        return transposee;
    }

    // -------------------------------------------------------------------------------------------------------------------------Résolution d’un système linéaire triangulaire

    //******************** Résolution d'un système linéaire avec une matrice triangulaire inférieure
    // dense
    public double[] resoudreTriangulaireInferieureDense(double[][] matrice, double[] vecteur) {
        int n = vecteur.length;
        double[] solution = new double[n];

        for (int i = 0; i < n; i++) {
            solution[i] = vecteur[i];
            for (int j = 0; j < i; j++) {
                solution[i] = solution[i] - matrice[i][j] * solution[j];
            }
            solution[i] = solution[i] / matrice[i][i];
        }

        return solution;
    }

    //************************ Résolution d'un système linéaire avec une matrice triangulaire supérieure
    // dense
    public double[] resoudreTriangulaireSuperieureDense(double[][] matrice, double[] vecteur) {
        int n = vecteur.length;
        double[] solution = new double[n];

        for (int i = n - 1; i >= 0; i--) {
            solution[i] = vecteur[i];
            for (int j = i + 1; j < n; j++) {
                solution[i] = solution[i] - matrice[i][j] * solution[j];
            }
            solution[i] = solution[i] / matrice[i][i];
        }

        return solution;
    }

    // ***********************Résolution d'un système linéaire avec une matrice triangulaire inférieure
    // demi-bande
    public double[] resoudreTriangulaireInferieureDemiBande(double[][] matrice, double[] vecteur) {
        int n = vecteur.length;
        int largeurDemiBande = determineLargeurDemiBandeInf(matrice);
        double[] solution = new double[n];

        for (int i = 0; i < n; i++) {
            solution[i] = vecteur[i];
            for (int j = Math.max(0, i - largeurDemiBande); j < i; j++) {
                solution[i] = solution[i] - matrice[i][j] * solution[j];
            }

            solution[i] = solution[i] / matrice[i][i];
        }

        return solution;
    }

    // ***********************Résolution d'un système linéaire avec une matrice triangulaire supérieure
    // demi-bande
    public double[] resoudreTriangulaireSuperieureDemiBande(double[][] matrice, double[] vecteur) {
        int n = vecteur.length;
        int largeurDemiBande = determineLargeurDemiBandeSup(matrice);
        double[] solution = new double[n];

        for (int i = n - 1; i >= 0; i--) {
            solution[i] = vecteur[i];
            for (int j = i + 1; j <= Math.min(n - 1, i + largeurDemiBande); j++) {
                solution[i] = solution[i] - matrice[i][j] * solution[j];
            }

            solution[i] = solution[i] / matrice[i][i];
        }

        return solution;
    }
    // -------------------------------------------------------Résolution d’un système linéaire avec les Méthode directes (matrice bande )

 //**************Méthodes d’élimination de Gauss
	 public double[] gaussBande(double[][] matrice, double[] vecteur) {
		        int n = vecteur.length;
                int m = determineLargeurDemiBandeSup(matrice);
		        double[] solution = Arrays.copyOf(vecteur, n);
		//gauss ----> tringularisation du matrice 
		        for (int k = 0; k < n - 1; k++) {
		            for (int i = k + 1; i < n; i++) {
		                double ratio = matrice[i][k] / matrice[k][k];
		                for (int j =Math.max(i-m,k+1); j <= Math.min(m+i, n-1); j++) {
		                    matrice[i][j] -= ratio * matrice[k][j];
		                }
		                matrice[i][k]=0;
		                solution[i] -= ratio * solution[k];// trait de second membre (le vecteur)
		            }
		        }
		        
		//**********resolution du systeme***************
		        for (int i = n - 1; i >= 0; i--) {
		            for (int j = i + 1; j < n; j++) {
		                solution[i] -= matrice[i][j] * solution[j];
		            }
		            solution[i] /= matrice[i][i];
		        }

		        return solution;
		    }

  //***********************Méthode d’élimination de Gauss avec pivotage partiel
	 public  double[] gaussavecpivotageBande(double[][] matrice, double[] vecteur) {
	        int n = vecteur.length;
                             int m =determineLargeurDemiBandeInf(matrice);
	        // Étape d'élimination
	        for (int k = 0; k < n - 1; k++) {
	            // Recherche du pivot partiel
	            int pivotPartiel = k;
	            for (int i = k + 1; i < k + m; i++) {
	                if (Math.abs(matrice[i][k]) > Math.abs(matrice[pivotPartiel][k])) {
	                    pivotPartiel = i;
	                }
	            }

	            // Échange des lignes (pivotage partiel)
	             for (int j = 0; j < n; j++) {
	         
	           double x = matrice[k][j];
	            matrice[k] [j]= matrice[pivotPartiel][j];
	            matrice[pivotPartiel] [j]= x;}

	            double tempVecteur = vecteur[k];
	            vecteur[k] = vecteur[pivotPartiel];
	            vecteur[pivotPartiel] = tempVecteur;
	            // Élimination
	            for (int i = k + 1; i < n; i++) {
	                double facteur = matrice[i][k] / matrice[k][k];
	                for (int j =Math.max(i-m,k+1); j < Math.min(m+i, n); j++){
	                    matrice[i][j] -= facteur * matrice[k][j];
	                }
	                vecteur[i] -= facteur * vecteur[k];
	            }
	        }

	        // resolution 
	        double[] solution = new double[n];
	        for (int i = n - 1; i >= 0; i--) {
	            double somme = 0;
	            for (int j = i + 1; j < n; j++) {
	                somme += matrice[i][j] * solution[j];
	            }
	            solution[i] = (vecteur[i] - somme) / matrice[i][i];
	        }
	return solution;
	    }
    //*****************gauss jordon 
	 public double[] JordanBande(double[][] bande, double[] vecteur) {
	        int n = vecteur.length;
                                 int m=determineLargeurDemiBandeInf(bande);
	        double[][] augmentedMatrix = new double[n][n + 1];

	        // Création de la matrice augmentée
	        for (int i = 0; i < n; i++) {
	            for (int j =Math.max(i-m,0); j <= Math.min(m+i, n-1); j++) {
	                augmentedMatrix[i][j] = bande[i][j];
	            }
	            augmentedMatrix[i][n] = vecteur[i];
	        }
	        // Élimination de gauss-jordan
	        for (int k = 0;k < n; k++) {
	        	 for (int j = Math.max(k+1,k-m); j <= Math.min(m+k, n-1); j++) {
	        		 augmentedMatrix[k][j]= augmentedMatrix[k][j]/augmentedMatrix[k][k]; 
	        	 }
	        	 augmentedMatrix[k][n]= augmentedMatrix[k][n]/augmentedMatrix[k][k];
	        	 augmentedMatrix[k][k]=1;
	            for (int i = 0; i < n; i++) {
	                if (i != k) {
	                    for (int j = Math.max(k+1,i-m); j <= n; j++) {
	                        augmentedMatrix[i][j] -= augmentedMatrix[i][k] * augmentedMatrix[k][j];
	                        
	                    }
                       
	                    augmentedMatrix[i][k]=0;}
	            }
	        }
	       
	        // Extraction de la solution
	        double[] solution = new double[n];
	        for (int i = 0; i < n; i++) {
	            solution[i] = augmentedMatrix[i][n];
	        }

	        return solution;
	    }
 //*****************************************Méthode de décomposition LU avec une matrice bande
	 public double[] LUBande(double[][] matrice, double[] vecteur) {
	        int n = matrice.length;
            int m=determineLargeurDemiBandeInf(matrice);
	        double[][] l = new double[n][n];
	        double[][] u = new double[n][n];

	        // Étape de décomposition
	        for (int i = 0; i < n; i++) {
	            // Calcul de la matrice L et U
	            for (int j =Math.max(i-m,0); j < Math.min(m+i, n); j++) {
	                double sum = 0.0;
	                for (int k =Math.max(i-m,0); k < Math.min(j,n); k++) {
	                    sum += l[i][k] * u[k][j];
	                }

	                if (i == j) {
	                    l[i][j] = 1;
	                } else {
	                    l[i][j] = (matrice[i][j] - sum) / u[j][j];
	                }
	            }

	            for (int j =i; j < n; j++) {
	                double sum = 0.0;
	                for (int k =Math.max(i-m,0); k < Math.min(j,n); k++) {
	                    sum += l[i][k] * u[k][j];
	                }
	                u[i][j] = matrice[i][j] - sum;
	            }
	        }

	        // Résolution du système (deux matrices L et U)
	        double[] y = new double[n];
	        double[] solution = new double[n];

	        for (int i = 0; i < n; i++) {
	            double sum = 0.0;
	            for (int j = 0; j < i; j++) {
	                sum += l[i][j] * y[j];
	            }
	            y[i] = (vecteur[i] - sum) / l[i][i];
	        }

	        for (int i = n - 1; i >= 0; i--) {
	            double sum = 0.0;
	            for (int j = i + 1; j < n; j++) {
	                sum += u[i][j] * solution[j];
	            }
	            solution[i] = (y[i] - sum) / u[i][i];
	        }

	        return solution;
	    }
 //**************************************cholsky matrice bande **********************************
	 public  double[] CholeskyBande(double[][] matrice, double[] vecteur) {
	        int m=determineLargeurDemiBandeInf(matrice);
	        double[][] L = decompositionCholeskybande(matrice,m);

	        // Résolution du système L*y = vecteur
	        double[] y = resoudreTriangulaireInferieure(L, vecteur);

	        // Transposition de la matrice L pour résoudre le système L^T*x = y
	        double[][] LT = transposer(L);

	        // Résolution du système L^T*x = y
	        return resoudreTriangulaireSuperieure(LT, y);
	    }
	    //*********methode de cholsky
	 public  double[][] decompositionCholeskybande(double[][] matrice,int m) {
	        int n = matrice.length;
	        double[][] L = new double[n][n];
	        for (int j = 0; j < n; j++) {
	            L[j][j] = matrice[j][j];
	            int startRow = Math.max(0, j - m);
	            for (int k = startRow; k < j; k++) {
	                L[j][j] -= L[j][k] * L[j][k];
	            }
	            L[j][j] = Math.sqrt(L[j][j]);

	            int endRow = Math.min(n - 1, j + m);
	            for (int i = j + 1; i <= endRow; i++) {
	                L[i][j] = matrice[i][j];
	                int startCol = Math.max(startRow, j - m);
	                for (int k = startCol; k < j; k++) {
	                    L[i][j] -= L[i][k] * L[j][k];
	                }
	                L[i][j] /= L[j][j];
	            }
	        }
	        return L;
	    }
	// resoudre le systeme avec la matrice tringulaire inferieure 
	    public  double[] resoudreTriangulaireInferieure(double[][] matrice, double[] vecteur) {
	        int n = vecteur.length;
	        double[] solution = new double[n];

	        for (int i = 0; i < n; i++) {
	        	solution[i]=vecteur[i];

	            for (int j = 0; j < i; j++) {
	            	solution[i]= solution[i]-matrice[i][j] * solution[j];
	            }

	            solution[i] = solution[i] / matrice[i][i];
	        }

	        return solution;
	    }
	//resoudre le systeme avec la matrice tringulaire superieure (pour resoudre le systeme du transposer de l)
	    public  double[] resoudreTriangulaireSuperieure(double[][] matrice, double[] vecteur) {
	        int n = vecteur.length;
	        double[] solution = new double[n];

	        for (int i = n - 1; i >= 0; i--) {
	           solution[i]=vecteur[i];
	            for (int j = i + 1; j < n; j++) {
	            	solution[i]=solution[i]- matrice[i][j] * solution[j];
	            }

	            solution[i] =solution[i] / matrice[i][i];
	        }

	        return solution;
	    }
	//calcule le transposer de facteur de cholisky 
	    public  double[][] transposer(double[][] matrice) {
	        int n = matrice.length;
	       
	        double[][] transposee = new double[n][n];

	        for (int i = 0; i < n; i++) {
	            for (int j = 0; j < n; j++) {
	                transposee[i][j] = matrice[j][i];
	            }
	        }

	        return transposee;
	    }

    // ---------------------------------------------------------------------Méthodes itératives (matrice dense)

    public double[] jacobieEpsilon(double[][] matrix1, double[] vector, double eps) {
        int n = matrix1.length;
        double[] x = new double[n];
        double[] y = new double[n];
        double s, max;
        do {
            max = 0;
            for (int i = 0; i < n; i++) {
                x[i] = y[i];
            }
            for (int i = 0; i < n; i++) {
                s = vector[i];
                for (int j = 0; j < i; j++) {
                    s = s - (matrix1[i][j] * x[j]);
                }
                for (int j = i + 1; j < n; j++) {
                    s = s - (matrix1[i][j] * x[j]);
                }
                y[i] = s / matrix1[i][i];
                if (max < Math.abs(x[i] - y[i])) {
                    max = Math.abs(x[i] - y[i]);
                }
            }
        } while (max > eps);
        return y;
    }

    public double[] jacobieIteration(double[][] matrix1, double[] vector, double nbrItMax) {
        int n = matrix1.length;
        double[] x = new double[n];
        double[] y = new double[n];
        double s;
        for (int k = 0; k < nbrItMax; k++) {
            for (int i = 0; i < n; i++) {
                x[i] = y[i];
            }
            for (int i = 0; i < n; i++) {
                s = vector[i];
                for (int j = 0; j < i; j++) {
                    s = s - (matrix1[i][j] * x[j]);
                }
                for (int j = i + 1; j < n; j++) {
                    s = s - (matrix1[i][j] * x[j]);
                }
                y[i] = s / matrix1[i][i];

            }
        }
        return y;
    }

    public double[] GaussSeidelEpsilon(double[][] matrix1, double[] b, double eps) {
        int n = matrix1.length;
        double[] x = new double[n];
        double s, max;
        do {
            max = 0;
            for (int i = 0; i < n; i++) {
                s = 0;
                for (int j = 0; j < i; j++) {
                    s = s + (matrix1[i][j] * x[j]);
                }
                for (int j = i + 1; j < n; j++) {
                    s = s + (matrix1[i][j] * x[j]);
                }
                s = (b[i] - s) / matrix1[i][i];
                if (max < Math.abs(x[i] - s)) {
                    max = Math.abs(x[i] - s);
                }
                x[i] = s;
            }
        } while (max > eps);
        return x;
    }

    public double[] GaussSeidelIteration(double[][] matrix1, double[] b, double eps) {
        int n = matrix1.length;
        double[] x = new double[n];
        double s;
        for (int k = 0; k < eps; k++) {

            for (int i = 0; i < n; i++) {
                s = 0;

                for (int j = 0; j < i; j++) {
                    s = s + (matrix1[i][j] * x[j]);
                }
                for (int j = i + 1; j < n; j++) {
                    s = s + (matrix1[i][j] * x[j]);
                }
                s = (b[i] - s) / matrix1[i][i];

                x[i] = s;
            }
        }
        return x;
    }

    //-------------------------------------------- Méthode pour déterminer la largeur de bande inferieur
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
    ////////////////**********condition nécessaire sur largeur de matrice bande 
    public  boolean estLargeurValide(double [][] matrice, int n) {
        // Vérifie si la matrice est vide
        if (n == 0 || matrice[0].length == 0) {
            return false;
        }

        // Récupère la largeur de la matrice
        int largeur = determineLargeurDemiBandeSup(matrice);

        // Vérifie si la largeur est supérieure à 0 et inférieure à n-1/2
        return largeur > 0 && largeur < n - 1 / 2;
    }

}
