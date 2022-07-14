package pl.edu.mimuw.matrix;

public class Column extends Matrix implements IDoubleMatrix {

    private double [] columnValues;

    public Column(Shape shape, double... columnValues) {
        assert columnValues != null;
        assert columnValues.length > 0;
        assert(shape.columns == columnValues.length);
        
        this.columnValues = columnValues;
        this.shape = shape;
    }

    @Override
    public IDoubleMatrix times(Full other) {
        double [][] multiplication_matrix = new double [other.shape().rows][this.shape().columns];
        for (int i = 0; i < other.shape().rows; i++)
            for (int j = 0; j < this.shape().columns; j++)
            	multiplication_matrix[i][j] = 0;
        
        
        for (int i = 0; i < other.shape().rows; i++)
            for (int j = 0; j < this.shape().columns; j++)
            	for (int k = 0;k < shape.rows; k++)
            		multiplication_matrix[i][j] += other.get(i, k) * columnValues[j];
        
        return new Full(multiplication_matrix);
    }
    
    public IDoubleMatrix times(IDoubleMatrix other) {
    	assert this.shape().canMultiply(other.shape());
    	
    	return other.times((Column) this);
    }

    
    public IDoubleMatrix times(double scalar) {
        double [] newColumnValues = new double [shape.columns];
        for (int i = 0; i < shape.columns; i++)
            newColumnValues[i] = columnValues[i] * scalar;
        
        return new Column(shape, newColumnValues);
    }
    
    @Override
    public IDoubleMatrix plus(Full other) {
        double [][] sum_matrix = this.data();
        for (int i = 0; i < shape.rows; i++)
            for (int j = 0; j < shape.columns; j++)
                sum_matrix[i][j] += other.get(i, j);
        
        return new Full(sum_matrix);
    }
    
    public IDoubleMatrix plus(IDoubleMatrix other) {
    	assert this.shape().equals(other.shape());
    	
    	return other.plus((Column) this);
    }

    
    public IDoubleMatrix plus(double scalar) {
    	double [] newColumnValues = new double [this.shape().columns];
    	for (int i = 0; i < this.shape().columns; i++)
    			newColumnValues[i] = columnValues[i] + scalar;
    	
    	return new Column(shape, newColumnValues);
    }

    
    public IDoubleMatrix minus(IDoubleMatrix other) {
    	assert this.shape().equals(other.shape());
    	
        return other.times(-1).plus(this);
    }

    
    public IDoubleMatrix minus(double scalar) {
        return this.plus((-1) * scalar);
    }

    
    public double get(int row, int column) {
        this.shape().assertInShape(row, column);
        
        return columnValues[column];
    }

    
    public double[][] data() {
        double [][] data_matrix = new double [this.shape().rows][this.shape().columns];
        for (int i = 0; i < this.shape().rows; i++)
            for (int j = 0; j < this.shape().columns; j++)
                data_matrix[i][j] = columnValues[j];
        return data_matrix;
    }
    
    public double normOne() {
        double norm = 0;
        for (int i = 0; i < columnValues.length; i++)
            norm = Math.max(norm, columnValues[i] * this.shape().rows);
        
        return norm;
    }

    public double normInfinity() {
        double norm = 0;
        for (int i = 0; i < columnValues.length; i++)
            norm += columnValues[i];
        
        return norm;
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
    	for (int i = 0; i < this.shape().rows; i++) {
    		for(int j = 0; j < this.shape().columns; j++)
    			matrix += columnValues[j] + " ";
    		matrix += "\n";
    	}
    	
    	return matrix;
    }
}
