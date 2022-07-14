package pl.edu.mimuw.matrix;

public class Zero extends Matrix implements IDoubleMatrix{

    public Zero(Shape shape) {
        assert shape != null;
        assert shape.rows > 0;
        assert shape.columns > 0;
        
        this.shape = shape;
    }
    
    @Override
    public IDoubleMatrix times(Full other) {
    	return new Zero(Shape.matrix(other.shape().rows, this.shape().columns));
    }

    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
        assert this.shape().canMultiply(other.shape());
        
        return new Zero(Shape.matrix(this.shape().rows, other.shape().columns));
    }

    
    public IDoubleMatrix times(double scalar) {
        return new Zero(this.shape);
    }

    
    public IDoubleMatrix plus(IDoubleMatrix other) {
    	assert this.shape().equals(other.shape());
    	
    	return other;
    }

    
    public IDoubleMatrix plus(double scalar) {
        return new Constant(shape, scalar);
    }

    
    public IDoubleMatrix minus(double scalar) {
        return this.plus((-1) * scalar);
    }

    
    public double get(int row, int column) {
        this.shape().assertInShape(row, column);
        
        return 0;
    }

    
    public double[][] data() {
        double [][] data_matrix = new double[shape.rows][shape.columns];
        for (int i = 0; i < shape.rows; i++)
            for (int j = 0; j < shape.columns; j++)
                data_matrix[i][j] = 0;
        
        return data_matrix;
    }

    
    public double normOne() {
        return 0;
    }

    
    public double normInfinity() {
        return 0;
    }

    
    public double frobeniusNorm() {
        return 0;
    }

    public String toString() {
    	String matrix = "";
    	for (int i = 0; i < this.shape().rows;i++) {
    		if (this.shape().columns >= 3)
    			matrix += "0 ... 0";
    		else
    			for (int j = 0; j < this.shape().columns; j++)
    				matrix += "0 ";
    		matrix += "\n";
    	}
    	
    	return matrix;
    }
}
