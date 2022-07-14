package pl.edu.mimuw.matrix;

public class Full extends Matrix{

    private double[][] values;

    public Full(double [][] values) {
        assert values != null;
        assert values.length > 0;
        assert values[0] != null;
        assert values[0].length > 0;
        for (int i = 0; i < values.length;i++)
            assert values[i].length == values[0].length;
        
        this.values = values;
        this.shape = Shape.matrix(values.length, values[0].length);
    }

    @Override
    public IDoubleMatrix times(Full other) {    	
        double [][] multiplication_matrix = new double[other.shape().rows][this.shape().columns];
        for (int i = 0; i < other.shape().rows; i++)
            for (int j = 0; j < this.shape().columns; j++)
                multiplication_matrix[i][j] = 0;
        for (int i = 0; i < other.shape().rows; i++)
            for (int j = 0; j < this.shape().columns; j++)
                for (int k = 0; k < other.shape().columns; k++)
                    multiplication_matrix[i][j] += other.get(i, k) * this.values[k][j];
        
        return new Full(multiplication_matrix);
    }
    
    public IDoubleMatrix times(IDoubleMatrix other) {
    	assert this.shape().canMultiply(other.shape());
    	
    	return other.times((Full) this);
    }

    
    public IDoubleMatrix times(double scalar) {
    	assert values.length == shape.rows;	
    	assert values[0].length == shape.columns;
    	
        double [][] multiplication_matrix = this.data();
        for (int i = 0; i < shape.rows; i++)
            for (int j = 0; j < shape.columns; j++) 
                multiplication_matrix[i][j] *= scalar;
        
        return new Full(multiplication_matrix);
    }
    
    @Override
    public IDoubleMatrix plus(Full other) {
        int rows = values.length;
        int columns = values[0].length;
        double [][] sum_matrix = new double[rows][columns];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j< columns; j++)
                sum_matrix[i][j] = values[i][j] + other.get(i, j);
        
        return new Full(sum_matrix);
        
    }

    public IDoubleMatrix plus(IDoubleMatrix other) {
    	assert this.shape().equals(other.shape());
    	
    	return other.plus((Full) this);
    }

    
    public IDoubleMatrix plus(double scalar) {
        double [][] sum_matrix = values;
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
        
        return values[row][column];
    }

    
    public double[][] data() {
    	double [][] data_matrix = new double [shape.rows][shape.columns];
    	for (int i = 0; i < shape.rows; i++)
    		for (int j = 0; j < shape.columns; j++)
    			data_matrix[i][j] = values[i][j];
        return data_matrix;
    }

    
    public double normOne() {
        double [] columns_sum = new double [values[0].length];
        for (int i = 0; i < values.length; i++)
            for (int j = 0; j < values[0].length; j++)
                columns_sum[j] += Math.abs(values[i][j]);
        double norm = 0;
        for (int i = 0; i < values[0].length; i++)
            norm = Math.max(norm, columns_sum[i]);
        
        return norm;
    }

    
    public double normInfinity() {
        double [] rows_sum = new double [values.length];
        for (int i = 0; i < values.length; i++)
            for (int j = 0; j < values[0].length; j++)
                rows_sum[i] += Math.abs(values[i][j]);
        double norm = 0;
        for (int i = 0; i < values.length; i++)
            norm = Math.max(norm, rows_sum[i]);
        
        return norm;
    }
    

    public double frobeniusNorm() {
        double norm = 0;
        for (int i = 0; i < values.length; i++)
            for (int j = 0; j < values[0].length; j++)
                norm += values[i][j] * values[i][j];
        
        return Math.sqrt(norm);
    }
    
    
    public String toString() {
    	String matrix = "";
    	for (int i = 0; i < this.shape().rows; i++) {
    		for (int j = 0; j < this.shape().columns; j++)
    			matrix += this.get(i, j) + " ";
    		matrix += "\n";
    	}
    	
    	return matrix;
    }
}
