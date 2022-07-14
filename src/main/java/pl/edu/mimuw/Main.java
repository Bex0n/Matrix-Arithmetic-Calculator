package pl.edu.mimuw;

import pl.edu.mimuw.matrix.DoubleMatrixFactory;

import static pl.edu.mimuw.matrix.MatrixCellValue.cell;
import static pl.edu.mimuw.matrix.Shape.matrix;

public class Main {

  public static void main(String[] args) {
	  
	  System.out.println("Antidiagonal 10x10:");
	  final var a = DoubleMatrixFactory.antiDiagonal(0.5, 1, 2, 3, 4, 5, 6, 7, 8, 9);
	  System.out.println(a.toString());
	  
	  System.out.println("Diagonal 10x10:");
	  final var b = DoubleMatrixFactory.diagonal(0.5, 1, 2, 3, 4, 5, 6, 7, 8, 9);
	  System.out.println(b.toString());
	  
	  System.out.println("Full 10x10:");
	  double [][] c_matrix = new double [10][10];
	  for(int i = 0; i < 10; i++)
		  for(int j = 0;j < 10; j++)
			  c_matrix[i][j] = i*10 + j;
	  final var c = DoubleMatrixFactory.full(c_matrix);
	  System.out.println(c.toString());

	  System.out.println("Identity 10x10:");
	  final var d = DoubleMatrixFactory.identity(10);
	  System.out.println(d.toString());
	  
	  System.out.println("Sparse 10x10:");
	  final var e = DoubleMatrixFactory.sparse(
			  matrix(10, 10),
			  cell(0, 0, 42),
		      cell(7, 7, 24),
		      cell(9, 9, 66)
	  );
	  System.out.println(e.toString());
	  
	  System.out.println("Vector 10x10:");
	  final var f = DoubleMatrixFactory.vector(55, 5, 5, 5, 5, 5, 5, 5, 5, 55);
	  System.out.println(f.toString());
	  
	  System.out.println("Zero 10x10:");
	  final var g = DoubleMatrixFactory.zero(matrix(10, 10));
	  System.out.println(g.toString());
	  
	  System.out.println("Row 10x10:");
	  final var h = DoubleMatrixFactory.row(matrix(10, 10), 1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
	  System.out.println(h.toString());
	  
	  System.out.println("Column 10x10:");
	  final var i = DoubleMatrixFactory.column(matrix(10, 10), 1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
	  System.out.println(i.toString());
	  
	  System.out.println("Constant 10x10:");
	  final var j = DoubleMatrixFactory.constant(matrix(10, 10), 10);
	  System.out.println(j.toString());
  }
}
