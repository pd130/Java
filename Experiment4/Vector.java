import java.util.Arrays;

public class Vector {
    private double[] components;
    private int dimension;

    public Vector(double... components) throws VectorException {
        if (components.length != 2 && components.length != 3) {
            throw new VectorException("Invalid dimension! Only 2D or 3D vectors are allowed.");
        }
        this.components = components.clone();
        this.dimension = components.length;
    }

    public int getDimension() {
        return dimension;
    }

    public double getComponent(int index) {
        return components[index];
    }

    private void checkDimensionality(Vector v) throws VectorException {
        if (this.dimension != v.dimension) {
            throw new VectorException("Dimension of the vectors do not match! Cannot perform  operations.");
        }
    }

    public Vector add(Vector v) throws VectorException {
        checkDimensionality(v);
        double[] result = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            result[i] = this.components[i] + v.components[i];
        }
        return new Vector(result);
    }

    public Vector subtract(Vector v) throws VectorException {
        checkDimensionality(v);
        double[] result = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            result[i] = this.components[i] - v.components[i];
        }
        return new Vector(result);
    }

    public double dotProduct(Vector v) throws VectorException {
        checkDimensionality(v);
        double result = 0;
        for (int i = 0; i < dimension; i++) {
            result += this.components[i] * v.components[i];
        }
        return result;
    }

    public void display() {
        System.out.println(this.dimension + "D Vector: " + Arrays.toString(this.components));
    }
}
