cnncnnetup() {

    convNeuralNetwork cnn = new convNeuralNetwork();

    chanelMatrix c = new chanelMatrix(1,1);
    chanelMatrix k = new chanelMatrix(2,1);
    k.setSize(5);
    c.setSize(3);
    k.randomize();
    c.randomize();
    k.mult(c).show();

}
