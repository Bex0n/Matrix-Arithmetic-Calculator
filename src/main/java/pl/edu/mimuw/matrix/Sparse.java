package pl.edu.mimuw.matrix;

import java.util.*;

public class Sparse extends Matrix {

    private MatrixCellValue[] values;

    private static int hashPosition(int r, int c) {
        return Objects.hash(r, c);
    }

    public Sparse(Shape shape, MatrixCellValue... values) {
        assert shape != null;
        assert shape.rows > 0;
        assert shape.columns > 0;
        assert values != null;
        for (int i = 0; i < values.length; i++)
            shape.assertInShape(values[i].row, values[i].column);
        
        this.shape = shape;
        this.values = values;
    }
    
    @Override
    public IDoubleMatrix times(Sparse other) {
    	HashMap <Integer, Double> result_length = new HashMap <Integer, Double>();
    	for (int i = 0; i < other.values.length; i++) {
    		for (int j = 0; j < this.values.length; j++) {
    			if (other.values[i].column == this.values[j].row) {
    				int hash = hashPosition(other.values[i].row, this.values[j].column);
    				if (result_length.get(hash) == null) {
    					result_length.put(hash, 1.0);
    				}
    			}
    		}
        }
    	
    	MatrixCellValue [] multiplication_matrix = new MatrixCellValue [result_length.size()];
    	int pointer = 0;
    	
    	for (int i = 0; i < other.values.length; i++) {
    		for (int j = 0; j < this.values.length; j++) {
    			if (other.values[i].column == this.values[j].row) {
    				boolean added = false;
    				for (int k = 0; k < pointer; k++) {
    					if (multiplication_matrix[k].row == other.values[i].row && multiplication_matrix[k].column == this.values[j].column) {
    						multiplication_matrix[k] = MatrixCellValue.cell(multiplication_matrix[k].row, 
    																		multiplication_matrix[k].column, 
    																		multiplication_matrix[k].value + other.values[i].value * this.values[j].value);
    						added = true;
    					}
    				}
    				if (!added) {
    					multiplication_matrix[pointer] = MatrixCellValue.cell(other.values[i].row,this.values[j].column,other.values[i].value * this.values[j].value);
    					pointer++;
    				}
    			}
    		}
        }
    	
    	return new Sparse(Shape.matrix(other.shape().rows, this.shape.columns),  multiplication_matrix);
    	
    }

    @Override
    public IDoubleMatrix times(Full other) {
        double [][] multiplication_matrix = new double [other.shape().rows][this.shape().columns];
        for (int i = 0; i < values.length; i++)
            for (int j = 0; j < other.shape().rows; j++)
                multiplication_matrix[j][values[i].column] += values[i].value * other.get (j, values[i].row);
        
        return new Full(multiplication_matrix);
    }
    
    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
    	assert this.shape().canMultiply(other.shape());
    	
    	return other.times((Sparse) this);
    }

    
    public IDoubleMatrix times(double scalar) {
        MatrixCellValue [] new_values = new MatrixCellValue [values.length];
        for (int i = 0; i < values.length; i++)
            new_values[i] = MatrixCellValue.cell(values[i].row, values[i].column, values[i].value * scalar);
        return new Sparse(shape, new_values);
    }

    @Override
    public IDoubleMatrix plus(Sparse other) {
        HashMap <Integer, Double> sum_values = new HashMap <Integer, Double>();
        for (int i = 0; i < values.length; i++) {
            int hash = hashPosition(values[i].row, values[i].column);
            if (sum_values.get(hash) == null)
                sum_values.put(hash, values[i].value);
            else
                assert false;
        }

        for (int i = 0; i < other.values.length; i++) {
            int hash = hashPosition(other.values[i].row, other.values[i].column);
            if (sum_values.get(hash) == null)
                sum_values.put(hash, other.values[i].value);
            else {
                double new_value = sum_values.get(hash) + other.values[i].value;
                sum_values.remove(hash);
                sum_values.put(hash, new_value);
            }
        }
        

        MatrixCellValue [] result = new MatrixCellValue [sum_values.size()];
        int pointer = 0;

        for (int i = 0; i < values.length; i++) {
            int hash = hashPosition(values[i].row, values[i].column);
            if (sum_values.get(hash) != null) {
                result[pointer] = MatrixCellValue.cell(values[i].row, values[i].column, sum_values.get(hash));
                pointer++;
                sum_values.remove(hash);
            }
        }

        for (int i = 0; i < other.values.length; i++) {
            int hash = hashPosition(other.values[i].row, other.values[i].column);
            if (sum_values.get(hash) != null) {
                result[pointer] = MatrixCellValue.cell(other.values[i].row, other.values[i].column, sum_values.get(hash));
                pointer++;
                sum_values.remove(hash);
            }
        }

        return new Sparse(shape, result);
    }

    @Override
    public IDoubleMatrix plus(Full other) {
        double[][] sum_matrix = new double [shape.rows][shape.columns];
        for (int i = 0; i < shape.rows; i++)
            for (int j = 0; j < shape.columns; j++)
                sum_matrix[i][j] = other.get(i, j);
        for (int i = 0; i < values.length; i++)
            sum_matrix[values[i].row][values[i].column] += values[i].value;
        
        return new Full(sum_matrix);
    }
    
    public IDoubleMatrix plus(IDoubleMatrix other) {
    	assert this.shape().equals(other.shape());
    	
    	return other.plus((Sparse) this);
    }
    
    public IDoubleMatrix plus(double scalar) {
        MatrixCellValue [] new_values = new MatrixCellValue [values.length];
        for (int i = 0; i < values.length; i++)
            new_values[i] = MatrixCellValue.cell(values[i].row, values[i].column, values[i].value + scalar);
        
        return new Sparse(shape, new_values);
    }

    
    public IDoubleMatrix minus(double scalar) {
        return this.plus((-1) * scalar);
    }

    
    public double get(int row, int column) {
        this.shape().assertInShape(row, column);
        
        for(int i = 0; i < values.length; i++)
            if(values[i].row == row && values[i].column == column)
                return values[i].value;
        return 0;
    }

    
    public double[][] data() {
        double [][] data_matrix = new double [shape.rows][shape.columns];
        for (int i = 0; i < shape.rows; i++)
            for (int j = 0; j < shape.columns; j++)
                data_matrix[i][j] = 0;
        for (int i = 0; i < values.length; i++)
            data_matrix[values[i].row][values[i].column] = values[i].value;
        return data_matrix;
    }

    
    public double normOne() {
        double [] columns_sum = new double [this.shape().columns];
        for (int i = 0; i < values.length; i++)
            columns_sum[values[i].column] += Math.abs(values[i].value);
        double norm = 0;
        for (int i = 0; i < this.shape().columns; i++)
            norm = Math.max(norm, columns_sum[i]);
        
        return norm;
    }

    
    public double normInfinity() {
        double [] rows_sum = new double [this.shape().rows];
        for (int i = 0; i < values.length; i++)
            rows_sum[values[i].row] += Math.abs(values[i].value);
        double norm = 0;
        for (int i = 0; i < this.shape().rows; i++)
            norm = Math.max(norm, rows_sum[i]);
        
        return norm;
    }

    
    public double frobeniusNorm() {
        double norm = 0;
        for (int i = 0; i < values.length; i++)
            norm += values[i].value * values[i].value;
        
        return Math.sqrt(norm);
    }
    
    public String toString() {
    	String matrix = "";
    	for (int i = 0; i < this.shape().rows; i++) {
    		for (int j = 0; j < this.shape().columns; j++) {
    			int minimum = this.shape().columns;
    			double minimum_value = 0;
    			for (int k = 0; k < values.length; k++) {
    				if (values[k].row == i && values[k].column >= j)
    					if (values[k].column < minimum) {
    						minimum = values[k].column;
    						minimum_value = values[k].value;
    					}
    			}
    			if (minimum - j >= 3)
    				matrix += "0 ... 0 ";
    			else
    				for (int k = 0; k < minimum - j; k++)
    					matrix += "0 ";
    			if (minimum < this.shape().columns)
    				matrix += minimum_value + " ";
    			j = minimum;	
    		}
    		matrix += "\n";
    	}
    	
    	return matrix;
    }
}
