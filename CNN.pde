class convNeuralNetwork {
    int[] numNodes;
    chanelMatrix[] layers, weights;
    float[] bias;

    convNeuralNetwork() {

    }
}

class chanelMatrix {
    int cols, rows, size = 5;
    matrix[][] chanel;

    chanelMatrix(int r, int c) {
        this.rows = r;
        this.cols = c;
        this.chanel = new matrix[rows][cols];

        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                this.chanel[i][j] = new matrix(size, size);
    }

    void setSize(int s) {
        this.size = s;
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                this.chanel[i][j] = new matrix(s, s);
    }

    chanelMatrix mult(chanelMatrix m1) {
        int border = (int) floor(m1.chanel[0][0].cols / 2);
        if (this.cols == m1.rows) {
            chanelMatrix m2 = new chanelMatrix(this.rows, m1.cols);

            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < m1.cols; j++) {

                    matrix sum = new matrix(this.chanel[0][0].rows - 2 * border, this.chanel[0][0].rows - 2 * border);

                    for (int k = 0; k < this.cols; k++) {
                        sum = sum.add(this.chanel[i][k].convolute(m1.chanel[k][j]));
                    }
                    m2.chanel[i][j] = sum.copy();
                }
            }

            return m2;
        } else {
            return null;
        }
    }

    void randomize() {
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                this.chanel[i][j].randomize();
    }

    void show() {
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                this.chanel[i][j].show();
    }
}
