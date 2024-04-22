public class TypeRecognizer {
  
    private int largeurBande;

    public int getLargeurBande() {
        return largeurBande;
    }

    public void setLargeurBande(int largeurBande) {
        this.largeurBande = largeurBande;
    }

    public int recognize(double[][] matrix1) {
        int sup = matrix1.length - 1;
        double x = matrix1[0][sup];
        int inf = matrix1.length - 1;
        double y = matrix1[inf][0];
        
        while ((sup > 0) && (x == 0)) {
            int i = 0;
            System.out.println("sup " + sup);
            while ((i < matrix1.length - sup) && (x == 0)) {
                x = matrix1[i][sup + i];
                System.out.println("x " + x);
                i++;
            }
            if (x == 0) {
                sup--;
                System.out.println("sup " + sup);
            }
        }
    
        while ((inf > 0) && (y == 0)) {
            int j = 0;
            System.out.println("inf " + inf);
            while ((j < matrix1.length - inf) && (y == 0)) {
                y = matrix1[inf + j][j];
                System.out.println("y " + y);
                j++;
            }
            if (y == 0) {
                inf--;
                System.out.println("inf " + inf);
            }
        }
    
        if ((sup == matrix1.length - 1) && (inf == matrix1.length - 1)) {
            return 0; // matrice dense
        } else {
            if ((sup == matrix1.length - 1) && (inf == 0)) {
                return 1; // matrice triangulaire supérieure
            } else {
                if ((sup == 0) && (inf == matrix1.length - 1)) {
                    return 2; // matrice triangulaire inférieure
                } else {
                    if ((sup < matrix1.length - 1) && (inf == 0)) {
                        largeurBande = sup;
                        return 3; // matrice bande supérieure
                    } else {
                        if ((sup == 0) && (inf < matrix1.length - 1)) {
                            largeurBande = inf;
                            return 4; // matrice bande inférieure
                        } else {
                            if ((sup < matrix1.length - 1) && (sup == inf)) {
                                largeurBande = inf;
                                return 5; // matrice bande
                            } else {
                                return 6; // matrice dense
                            }
                        }
                    }
                }
            }
        }
    }
}
