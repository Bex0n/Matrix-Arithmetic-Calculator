package pl.edu.mimuw.matrix;

public class Vector extends Matrix implements IDoubleMatrix{

    private double [] values;

    public Vector(double... values) {
        assert values != null;
        assert values.length > 0;

        this.values = values;
        this.shape = Shape.matrix(values.length, 1);
    }

    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
    	assert this.shape().canMultiply(other.shape());
    	
    	return other.times((Vector) this);
    }

    
    public IDoubleMatrix times(double scalar) {
        double [] new_values = new double [values.length];
        for (int i = 0; i < values.length; i++)
            new_values[i] = values[i] * scalar;
        
        return new Vector(new_values);
    }
    
    @Override
    public IDoubleMatrix plus(Full other) {
        double [] sum_matrix = new double [values.length];
        for (int i = 0; i < values.length; i++)
            sum_matrix[i] = values[i] + other.get(i, 0);
        
        return new Vector(sum_matrix);
    }
    
    public IDoubleMatrix plus(IDoubleMatrix other) {
    	assert this.shape().equals(other.shape());
    	
    	return other.plus((Vector) this);
    }

    
    public IDoubleMatrix plus(double scalar) {
        double [] sum_matrix = new double [values.length];
        for (int i = 0; i < values.length; i++)
            sum_matrix[i] = values[i] + scalar;
        
        return new Vector(sum_matrix);
    }
    
    public IDoubleMatrix minus(double scalar) {
        return this.plus((-1) * scalar);
    }

    
    public double get(int row, int column) {
        this.shape().assertInShape(row, column);
        return values[row];
    }

    
    public double[][] data() {
        double [][] data_matrix = new double[values.length][1];
        for (int i = 0; i < values.length; i++)
            data_matrix[i][0] = values[i];
        
        return data_matrix;
    }

    
    public double normOne() {
        double norm = 0;
        for (int i = 0; i < values.length; i++)
            norm += Math.abs(values[i]);
        
        return norm;
    }

    
    public double normInfinity() {
        double norm = 0;
        for (int i = 0; i < values.length; i++)
            norm = Math.max(norm, Math.abs(values[i]));
        
        return norm;
    }

    
    public double frobeniusNorm() {
        double norm = 0;
        for (int i = 0; i < values.length; i++)
            norm += values[i] * values[i];
        
        return Math.sqrt(norm);
    }
    

    public String toString() {
    	String matrix = "";
    	for (int i = 0; i < this.shape().rows; i++) {
    		matrix += this.get(i, 0) + "\n";
    	}
    	
    	return matrix;
    }
}
