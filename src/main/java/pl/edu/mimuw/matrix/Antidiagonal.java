package pl.edu.mimuw.matrix;

public class Antidiagonal extends Matrix implements IDoubleMatrix{

    private double [] antiDiagonalValues;    

    public Antidiagonal(double... antiDiagonalValues) {
        assert antiDiagonalValues != null;
        assert antiDiagonalValues.length > 0;
        
        int size = antiDiagonalValues.length; 
        this.antiDiagonalValues = antiDiagonalValues;
        this.shape = Shape.matrix(size, size);
    }
    
    @Override
    public IDoubleMatrix times(Full other) {        
        double [][] result_values = new double [other.shape().rows][this.shape().columns];
        for (int i = 0; i < other.shape().rows; i++)
            for (int j = 0; j < this.shape().columns; j++)
                result_values[i][j] = other.get(i, j) * antiDiagonalValues[j];
        
        return new Full(result_values);
    }
    
    
    public IDoubleMatrix times(IDoubleMatrix other) {
    	assert this.shape().canMultiply(other.shape());
    	
    	return other.times((Antidiagonal) this);
    }
    

    public IDoubleMatrix times(double scalar) {
        double [] new_antiDiagonalValues = new double [antiDiagonalValues.length];
        for (int i = 0; i < antiDiagonalValues.length; i++)
            new_antiDiagonalValues[i] = antiDiagonalValues[i] * scalar;
        
        return new Antidiagonal(new_antiDiagonalValues);
    }
  
    @Override
    public IDoubleMatrix plus(Full other) {
        int size = antiDiagonalValues.length;
        double [][] sum_matrix = this.data();   
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                sum_matrix[i][j] += other.get(i, j);
        
        return new Full(sum_matrix);
    }
  
    
    public IDoubleMatrix plus(IDoubleMatrix other) {
    	assert this.shape().equals(other.shape());
    	
    	return other.plus((Antidiagonal) this);
    }


    public IDoubleMatrix plus(double scalar) {
    	double [][] sum_matrix = this.data();
    	for (int i = 0; i < this.shape().rows; i++)
    		for (int j = 0; j < this.shape().columns; j++)
    			sum_matrix[i][j] += scalar;
    	
    	return new Full(sum_matrix);
    }
    
    
    public IDoubleMatrix minus(double scalar) {
        return this.plus((-1) * scalar);
    }

    
    public double get(int row, int column) {
        this.shape().assertInShape(row, column);
        
        if (row + column == antiDiagonalValues.length - 1)
            return antiDiagonalValues[row];
        
        return 0;
    }

    
    public double[][] data() {
        int size = antiDiagonalValues.length;
        double [][] data_matrix = new double [size][size];
        
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (i + j == size - 1)
                    data_matrix[i][j] = antiDiagonalValues[i];
                else
                    data_matrix[i][j] = 0;
        
        return data_matrix;
    }

    public double normOne() {
        double norm = 0;
        for (int i = 0; i < antiDiagonalValues.length; i++)
            norm = Math.max(norm, Math.abs(antiDiagonalValues[i]));
        
        return norm;
    }

    public double normInfinity() {
        return this.normOne();
    }

    public double frobeniusNorm() {
        double norm = 0;
        for (int i = 0; i < antiDiagonalValues.length; i++)
            norm += antiDiagonalValues[i] * antiDiagonalValues[i];
        
        return Math.sqrt(norm);
    }
    
    public String toString() {
    	String matrix = "";
    	for (int i = 0; i < this.shape().rows;i++) {
    		if (this.shape().rows - 1 - i >= 3) 
    			matrix += "0 ... 0 ";
    		else 
    			for (int j = 0; j < this.shape().rows - 1 - i; j++)
    				matrix += "0 ";
    		matrix += this.get(this.shape().rows - 1 - i, i) + " ";
    		if (i >= 3)
    			matrix += "0 ... 0 ";
    		else
    			for (int j = 0; j < i; j++)
    				matrix += "0 ";
    		matrix += "\n";
    	}
    	
    	return matrix;
    }
}
