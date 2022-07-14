package pl.edu.mimuw.matrix;

public class Identity extends Matrix implements IDoubleMatrix {

    private int size;

    public Identity(int size) {
        assert size > 0;

        this.shape = Shape.matrix(size, size);
        this.size = size;
    }

    public IDoubleMatrix times(Full other) {
    	return other;
    }
    
    public IDoubleMatrix times(IDoubleMatrix other) {
        assert this.shape().canMultiply(other.shape());
        
        return other;
    }

    
    public IDoubleMatrix times(double scalar) {
        double[] diagonalValues = new double[size];
        for (int i = 0; i < diagonalValues.length; i++)
            diagonalValues[i] = scalar;
        
        return new Diagonal(diagonalValues);
    }
    
    @Override
    public IDoubleMatrix plus(Full other) {
        double [][] other_data = other.data();
        for (int i = 0; i < size; i++)
            other_data[i][i] += 1;
        return new Full(other_data);
    }
    
    public IDoubleMatrix plus(IDoubleMatrix other) {
    	assert this.shape().equals(other.shape());
    	
    	return other.plus((Identity) this);
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
            return 1;
        return 0;
    }

    
    public double[][] data() {
        double data_matrix[][] = new double[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (i == j)
                    data_matrix[i][j] = 1;
                else
                    data_matrix[i][j] = 0;
        
        return data_matrix;
    }

    
    public double normOne() {
        return 1;
    }

    
    public double normInfinity() {
        return 1;
    }

    
    public double frobeniusNorm() {
        return Math.sqrt((double) size);
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
