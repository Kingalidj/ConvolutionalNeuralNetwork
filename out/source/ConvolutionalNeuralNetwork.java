import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ConvolutionalNeuralNetwork extends PApplet {

public void setup() {

    convNeuralNetwork cnn = new convNeuralNetwork();

    chanelMatrix c = new chanelMatrix(1,1);
    chanelMatrix k = new chanelMatrix(2,1);
    k.setSize(5);
    c.setSize(3);
    k.randomize();
    c.randomize();
    k.mult(c).show();

}
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

    public void setSize(int s) {
        this.size = s;
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                this.chanel[i][j] = new matrix(s, s);
    }

    public chanelMatrix mult(chanelMatrix m1) {
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

    public void randomize() {
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                this.chanel[i][j].randomize();
    }

    public void show() {
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                this.chanel[i][j].show();
    }
}
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

    public matrix mult(matrix m2) {
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

    public matrix multElement(float n) {
        matrix m2 = new matrix(this);
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++) m2.m[i][j] = this.m[i][j] * n;
        return m2;
    }

    public matrix multElement(matrix m2) {
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

    public matrix add(float n) {
        matrix m2 = new matrix(this);
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++) m2.m[i][j] = this.m[i][j] + n;
        return m2;
    }

    public matrix add(matrix m2) {
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

    public matrix sub(matrix m2) {
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

    public matrix transpose() {
        matrix result = new matrix(this.cols, this.rows);
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++) result.m[j][i] = this.m[i][j];
        return result;
    }

    public void show() {
        println();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                print(this.m[i][j] + " ");
            }
            println();
        }
        println();
    }

    public void randomize() {
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++) this.m[i][j] = random(-1, 1);
    }

    public matrix matrixSigmoid() {
        matrix m2 = new matrix(this);
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++) m2.m[i][j] = sigmoid(this.m[i][j]);
        return m2;
    }

    public matrix matrixDSigmoid() {
        matrix m2 = new matrix(this);
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++) m2.m[i][j] = dSigmoid(this.m[i][j]);
        return m2;
    }

    public matrix segment(int x1, int y1, int x2, int y2) {
        matrix m2 = new matrix(abs(x1 - x2) + 1, abs(y1 - y2) + 1);

        for (int i = 0; i < m2.rows; i++) {
            for (int j = 0; j < m2.cols; j++) {
                m2.m[i][j] = this.m[i + x1][j + y1];
            }
        }
        return m2;
    }

    public float sum() {
        float sum = 0;
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                sum += this.m[i][j];
        return sum;
    }

    public matrix copy() {
        matrix m1 = new matrix(this.rows, this.cols);
        arrayCopy(this.m, m1.m);
        m1.rows = this.rows;
        m1.cols = this.cols;
        return m1;
    }

    public matrix convolute(matrix k) {
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

public matrix arrayToMatrix(float[] array) {
    matrix m = new matrix(array.length, 1);
    for (int i = 0; i < array.length; i++) m.m[i][0] = array[i];
    return m;
}

public float[] matrixToArray(matrix m1) {
    int k = 0;
    float[] array = new float[m1.rows * m1.cols];

    for (int i = 0; i < m1.rows; i++)
        for (int j = 0; j < m1.cols; j++) {
            array[k] = m1.m[i][j];
            k++;
        }

    return array;
}

public String matrixToString(matrix m1) {
    String s = "";
    s += join(nf(matrixToArray(m1)), " ");
    return s;
}

public float sigmoid(float x) {
    return 1 / (1 + exp(-x));
}

public float dSigmoid(float x) {
    return x * (1 - x);
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--hide-stop", "ConvolutionalNeuralNetwork" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
