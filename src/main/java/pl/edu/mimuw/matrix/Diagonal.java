package pl.edu.mimuw.matrix;

public class Diagonal extends Matrix implements IDoubleMatrix {

    private double [] diagonalValues;

    public Diagonal(double... diagonalValues) {
        assert diagonalValues != null;
        assert diagonalValues.length > 0;
        
        int size = diagonalValues.length;
        this.diagonalValues = diagonalValues;
        this.shape = Shape.matrix(size, size);
    }

    @Override
    public IDoubleMatrix times(Full other) {
        double [][] multiplication_matrix = new double [other.shape().rows][this.shape().columns];
        for (int i = 0; i < other.shape().rows; i++)
            for (int j = 0; j < this.shape().columns; j++)
                multiplication_matrix[i][j] = other.get(i, j) * diagonalValues[j];
        
        return new Full(multiplication_matrix);
    }
    
    public IDoubleMatrix times(IDoubleMatrix other) {
    	assert this.shape().canMultiply(other.shape());
    	
    	return other.times((Diagonal) this);
    }

    
    public IDoubleMatrix times(double scalar) {
        double [] new_diagonalValues = new double [diagonalValues.length];
        for (int i = 0; i < diagonalValues.length; i++)
            new_diagonalValues[i] = diagonalValues[i] * scalar;
        
        return new Diagonal(new_diagonalValues);
    }
    
    @Override
    public IDoubleMatrix plus(Full other) {
        int size = diagonalValues.length;
        double [][] sum_matrix = this.data();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                sum_matrix[i][j] += other.get(i, j);
        
        return new Full(sum_matrix);
    }
    
    public IDoubleMatrix plus(IDoubleMatrix other) {
    	assert this.shape().equals(other.shape());
    	
    	return other.plus((Diagonal) this);
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
        
        if(row == column)
            return diagonalValues[row];
        return 0;
    }

    
    public double[][] data() {
        int size = diagonalValues.length;
        double [][] data_matrix = new double [size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if(i == j)
                    data_matrix[i][j] = diagonalValues[i];
                else
                    data_matrix[i][j] = 0;
        return data_matrix;
    }

    
    public double normOne() {
        double norm = 0;
        for (int i = 0; i < diagonalValues.length; i++)
            norm = Math.max(norm, Math.abs(diagonalValues[i]));
        return norm;
    }

    
    public double normInfinity() {
        return this.normOne();
    }

    
    public double frobeniusNorm() {
        double norm = 0;
        for (int i = 0; i < diagonalValues.length; i++)
            norm += diagonalValues[i] * diagonalValues[i];
        return Math.sqrt(norm);
    }
    
    public String toString() {
    	String matrix = "";
    	for (int i = 0; i < this.shape().rows;i++) {
    		if (i >= 3) 
    			matrix += "0 ... 0 ";
    		else 
    			for (int j = 0; j < i; j++)
    				matrix += "0 ";
    		matrix += this.get(i, i) + " ";
    		if (this.shape().rows - i - 1 >= 3)
    			matrix += "0 ... 0 ";
    		else
    			for (int j = 0; j < this.shape().rows - i - 1; j++)
    				matrix += "0 ";
    		matrix += "\n";
    	}
    	
    	return matrix;
    }
}
