class matrix {
    int rows,
    cols;
    float[][] m;

    matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        m = new float[rows][cols];
    }

    matrix(int rows, int cols, float[] numbers) {
        this.rows = rows;
        this.cols = cols;
        m = new float[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m[i][j] = numbers[j + i * cols];
            }
        }
    }

    matrix(matrix m1) {
        this.rows = m1.rows;
        this.cols = m1.cols;
        m = new float[this.rows][this.cols];
    }

    matrix mult(matrix m2) {
        if (this.cols == m2.rows) {
            matrix m3 = new matrix(this.rows, m2.cols);

            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < m2.cols; j++) {
                    float sum = 0;

                    for (int k = 0; k < this.cols; k++) {
                        sum += this.m[i][k] * m2.m[k][j];
                    }

                    m3.m[i][j] = sum;
                }
            }

            return m3;
        } else {
            println("multiplication not possible! number of cols or rows is incorrect.");
            return null;
        }
    }

    matrix multElement(float n) {
        matrix m2 = new matrix(this);
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++) m2.m[i][j] = this.m[i][j] * n;
        return m2;
    }

    matrix multElement(matrix m2) {
        if (this.cols == m2.cols && this.rows == m2.rows) {
            matrix m3 = new matrix(this);
            for (int i = 0; i < this.rows; i++)
                for (int j = 0; j < this.cols; j++) m3.m[i][j] = this.m[i][j] * m2.m[i][j];
            return m3;
        } else {
            println("Elementwise multiplication not possible: cols and rows dont match!");
            return null;
        }
    }

    matrix add(float n) {
        matrix m2 = new matrix(this);
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++) m2.m[i][j] = this.m[i][j] + n;
        return m2;
    }

    matrix add(matrix m2) {
        if (this.rows == m2.rows && this.cols == m2.cols) {
            matrix m3 = new matrix(m2);
            for (int i = 0; i < this.rows; i++)
                for (int j = 0; j < this.cols; j++) m3.m[i][j] = this.m[i][j] + m2.m[i][j];
            return m3;
        } else {
            println("Addition not possible! Matrix rows or cols don't match.");
            return null;
        }
    }

    matrix sub(matrix m2) {
        if (this.rows == m2.rows && this.cols == m2.cols) {
            matrix m3 = new matrix(this);
            for (int i = 0; i < this.rows; i++)
                for (int j = 0; j < this.cols; j++) m3.m[i][j] = this.m[i][j] - m2.m[i][j];
            return m3;
        } else {
            println("Subtraction not possible! Matrix rows or cols don't match.");
            return null;
        }
    }

    matrix transpose() {
        matrix result = new matrix(this.cols, this.rows);
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++) result.m[j][i] = this.m[i][j];
        return result;
    }

    void show() {
        println();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                print(this.m[i][j] + " ");
            }
            println();
        }
        println();
    }

    void randomize() {
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++) this.m[i][j] = random(-1, 1);
    }

    matrix matrixSigmoid() {
        matrix m2 = new matrix(this);
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++) m2.m[i][j] = sigmoid(this.m[i][j]);
        return m2;
    }

    matrix matrixDSigmoid() {
        matrix m2 = new matrix(this);
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++) m2.m[i][j] = dSigmoid(this.m[i][j]);
        return m2;
    }

    matrix segment(int x1, int y1, int x2, int y2) {
        matrix m2 = new matrix(abs(x1 - x2) + 1, abs(y1 - y2) + 1);

        for (int i = 0; i < m2.rows; i++) {
            for (int j = 0; j < m2.cols; j++) {
                m2.m[i][j] = this.m[i + x1][j + y1];
            }
        }
        return m2;
    }

    float sum() {
        float sum = 0;
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                sum += this.m[i][j];
        return sum;
    }

    matrix copy() {
        matrix m1 = new matrix(this.rows, this.cols);
        arrayCopy(this.m, m1.m);
        m1.rows = this.rows;
        m1.cols = this.cols;
        return m1;
    }

    matrix convolute(matrix k) {
        int border = (int) floor(k.cols / 2);
        int w = this.rows - 2 * border, h = this.cols - 2 * border;
        matrix result = new matrix(w, h);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                matrix section = this.segment(i, j, i + 2 * border, j + 2 * border);
                result.m[i][j] = section.multElement(k).sum();
            }
        }
        return result;
    }
}

matrix arrayToMatrix(float[] array) {
    matrix m = new matrix(array.length, 1);
    for (int i = 0; i < array.length; i++) m.m[i][0] = array[i];
    return m;
}

float[] matrixToArray(matrix m1) {
    int k = 0;
    float[] array = new float[m1.rows * m1.cols];

    for (int i = 0; i < m1.rows; i++)
        for (int j = 0; j < m1.cols; j++) {
            array[k] = m1.m[i][j];
            k++;
        }

    return array;
}

String matrixToString(matrix m1) {
    String s = "";
    s += join(nf(matrixToArray(m1)), " ");
    return s;
}

float sigmoid(float x) {
    return 1 / (1 + exp(-x));
}

float dSigmoid(float x) {
    return x * (1 - x);
}