package pl.edu.mimuw.matrix;

public class DoubleMatrixFactory {

	private DoubleMatrixFactory() {
	}

	public static IDoubleMatrix sparse(Shape shape, MatrixCellValue... values){
		return new Sparse(shape, values);
	}

	public static IDoubleMatrix full(double[][] values) {
		return new Full(values);
	}

	public static IDoubleMatrix identity(int size) {
		return new Identity(size);
	}

	public static IDoubleMatrix diagonal(double... diagonalValues) {
		return new Diagonal(diagonalValues);
	}

	public static IDoubleMatrix antiDiagonal(double... antiDiagonalValues) {
		return new Antidiagonal(antiDiagonalValues);
	}

	public static IDoubleMatrix vector(double... values) {
		return new Vector(values);
	}

	public static IDoubleMatrix zero(Shape shape) {
		return new Zero(shape);
	}
	
	public static IDoubleMatrix row(Shape shape, double... rowValues) {
		return new Row(shape, rowValues);
	}
	
	public static IDoubleMatrix column(Shape shape, double... columnValues) {
		return new Column(shape, columnValues);
	}
	
	public static IDoubleMatrix constant(Shape shape, double value) {
		if (value == 0)
			return new Zero(shape);
		return new Constant(shape, value);
	}
}