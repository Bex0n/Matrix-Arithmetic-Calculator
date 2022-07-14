package pl.edu.mimuw.matrix;

public interface IDoubleMatrix {

  IDoubleMatrix times(IDoubleMatrix other);
  
  IDoubleMatrix times(Antidiagonal other);
  IDoubleMatrix times(Diagonal other); 
  IDoubleMatrix times(Full other);        
  IDoubleMatrix times(Identity other);    
  IDoubleMatrix times(Sparse other);     
  IDoubleMatrix times(Vector other);     
  IDoubleMatrix times(Zero other);  
  IDoubleMatrix times(Row other);
  IDoubleMatrix times(Column other);
  IDoubleMatrix times(Constant other);

  IDoubleMatrix times(double scalar);

  IDoubleMatrix plus(IDoubleMatrix other);
  
  IDoubleMatrix plus(Antidiagonal other);
  IDoubleMatrix plus(Diagonal other); 
  IDoubleMatrix plus(Full other);        
  IDoubleMatrix plus(Identity other);    
  IDoubleMatrix plus(Sparse other);     
  IDoubleMatrix plus(Vector other);     
  IDoubleMatrix plus(Zero other);
  IDoubleMatrix plus(Row other); 
  IDoubleMatrix plus(Column other); 
  IDoubleMatrix plus(Constant other); 

  IDoubleMatrix plus(double scalar);

  IDoubleMatrix minus(IDoubleMatrix other);

  IDoubleMatrix minus(double scalar);

  double get(int row, int column);

  double[][] data();

  double normOne();

  double normInfinity();

  double frobeniusNorm();

  String toString();

  Shape shape();
}
