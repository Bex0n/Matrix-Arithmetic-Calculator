package pl.edu.mimuw.matrix;

public class Row extends Matrix implements IDoubleMatrix {

    private double [] rowValues;

    public Row(Shape shape, double... rowValues) {
        assert rowValues != null;
        assert rowValues.length > 0;
        assert(shape.rows == rowValues.length);
        
        this.rowValues = rowValues;
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
            		multiplication_matrix[i][j] += other.get(i, k) * rowValues[k];
        
        return new Full(multiplication_matrix);
    }
    
    public IDoubleMatrix times(IDoubleMatrix other) {
    	assert this.shape().canMultiply(other.shape());
    	
    	return other.times((Row) this);
    }

    
    public IDoubleMatrix times(double scalar) {
        double [] newRowValues = new double [shape.rows];
        for (int i = 0; i < shape.rows; i++)
            newRowValues[i] = rowValues[i] * scalar;
        
        return new Row(shape, newRowValues);
    }
    
    @Override
    public IDoubleMatrix plus(Full other) {
        double [][] sum_matrix = this.data();
        for (int i = 0; i < shape.rows; i++)
            for (int j = 0; j < shape.rows; j++)
                sum_matrix[i][j] += other.get(i, j);
        
        return new Full(sum_matrix);
    }
    
    public IDoubleMatrix plus(IDoubleMatrix other) {
    	assert this.shape().equals(other.shape());
    	
    	return other.plus((Row) this);
    }

    
    public IDoubleMatrix plus(double scalar) {
    	double [] newRowValues = new double [this.shape().rows];
    	for (int i = 0; i < this.shape().rows; i++)
    			newRowValues[i] = rowValues[i] + scalar;
    	
    	return new Row(shape, newRowValues);
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
        
        return rowValues[row];
    }

    
    public double[][] data() {
        double [][] data_matrix = new double [this.shape().rows][this.shape().columns];
        for (int i = 0; i < this.shape().rows; i++)
            for (int j = 0; j < this.shape().columns; j++)
                data_matrix[i][j] = rowValues[i];
        return data_matrix;
    }

    
    public double normOne() {
        double norm = 0;
        for (int i = 0; i < rowValues.length; i++)
            norm += rowValues[i];
        
        return norm;
    }

    
    public double normInfinity() {
        double norm = 0;
        for (int i = 0; i < rowValues.length; i++)
            norm = Math.max(norm, rowValues[i] * this.shape().columns);
        
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
    	for (int i = 0; i < this.shape().rows;i++) {
    		if(this.shape().columns >= 3)
    			matrix += this.get(i, 0) + " ... " + this.get(i, 0) + " ";
    		else
    			for(int j =0; j < this.shape().columns; j++)
    				matrix += this.get(i, 0) + " ";
    		matrix += "\n";
    	}
    	
    	return matrix;
    }
}
