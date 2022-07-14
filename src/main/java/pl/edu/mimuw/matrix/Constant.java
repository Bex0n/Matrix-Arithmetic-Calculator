package pl.edu.mimuw.matrix;

public class Constant extends Matrix implements IDoubleMatrix{
	
	private double value;

    public Constant(Shape shape, double value) {
        assert shape != null;
        assert shape.rows > 0;
        assert shape.columns > 0;
        
        this.shape = shape;
        this.value = value;
    }
    
    @Override
    public IDoubleMatrix times(Full other) {
    	double [][] multiplication_matrix = new double [other.shape().rows][this.shape().columns];
        for (int i = 0; i < other.shape().rows; i++)
            for (int j = 0; j < this.shape().columns; j++)
            	multiplication_matrix[i][j] = 0;
        
        
        for (int i = 0; i < other.shape().rows; i++)
            for (int j = 0; j < this.shape().columns; j++)
            	for (int k = 0;k < other.shape().columns; k++)
            		multiplication_matrix[i][j] += other.get(i, k) * value;
        
        return new Full(multiplication_matrix);
    }

    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
        assert this.shape().canMultiply(other.shape());
        
        return other.times((Constant) this);
    }

    
    public IDoubleMatrix times(double scalar) {
    	if(scalar == 0)
    		return new Zero(shape);
        return new Constant(shape, value * scalar);
    }

    
    public IDoubleMatrix plus(IDoubleMatrix other) {
    	assert this.shape().equals(other.shape());
    	
    	return other.plus((Constant) this);
    }
    
    public IDoubleMatrix plus(double scalar) {
    	if(value + scalar == 0)
    		return new Zero(shape);
        return new Constant(shape, value + scalar);
    }

    
    public IDoubleMatrix minus(IDoubleMatrix other) {
        assert shape.equals(other.shape());
        
        return other.times(-1);
    }

    
    public IDoubleMatrix minus(double scalar) {
        return this.plus((-1) * scalar);
    }

    
    public double get(int row, int column) {
        this.shape().assertInShape(row, column);
        
        return value;
    }

    
    public double[][] data() {
        double [][] data_matrix = new double[shape.rows][shape.columns];
        for (int i = 0; i < shape.rows; i++)
            for (int j = 0; j < shape.columns; j++)
                data_matrix[i][j] = value;
        
        return data_matrix;
    }

    
    public double normOne() {
        return value * shape.rows;
    }

    
    public double normInfinity() {
        return value * shape.columns;
    }

    
    public double frobeniusNorm() {
    	double norm = 0;
        for (int i = 0; i < this.shape().rows; i++)
        	for (int j = 0; j < this.shape().columns; j++)
        		norm += this.get(i, j) * this.get(i, j);
        
        return Math.sqrt(norm);
    }

    public String toString() {
    	String matrix = "";
    	for (int i = 0; i < this.shape().rows;i++) {
    		if (this.shape().columns >= 3)
    			matrix += value + " ... " + value;
    		else
    			for (int j = 0; j < this.shape().columns; j++)
    				matrix += value + " ";
    		matrix += "\n";
    	}
    	
    	return matrix;
    }
}
