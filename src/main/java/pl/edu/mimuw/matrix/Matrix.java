package pl.edu.mimuw.matrix;

/*
 * Każdy programista chcący powiększyć projekt o następne rodzaje macierzy, powinien
 * dodać adekwatne dla jego typu macierzy funkcje w interfejsie IDoubleMatrix,
 * dodać adekwatne dla jego typu macierzy funkcje poniżej, zaimplementować wymienione
 * niżej funkcje abstrakcyjne w swojej klasie, z zaznaczeniem, że funkcja mnożenia
 * przez macierz typu IDoubleMatrix oraz dodawania macierzy typu IDoubleMatrix powinny
 * być zaimplementowane w analogiczny sposób co w pozostałych klasach. Każda nowa klasa
 * powinna mieć zaimplementowaną fukncję mnożenia oraz dodawania macierzy typu Full.
 * Pozostałe optymalizacje mogą zostać dodane poprzez napisanie wymienionych niżej funkcji.
 */
public abstract class Matrix implements IDoubleMatrix {
	
	// Atrybut dowolnej macierzy - kształt
	protected Shape shape;
	
	// Wywołuje funkcję mnożącą macierz other przez macierz o rozpoznanym typie.
	public abstract IDoubleMatrix times(IDoubleMatrix other);

	// Zwraca iloczyn macierzy other i zadanej macierzy.
	public IDoubleMatrix times(Antidiagonal other) { return (new Full(this.data())).times(new Full(other.data())); }
	public IDoubleMatrix times(Diagonal other)     { return (new Full(this.data())).times(new Full(other.data())); }
	public IDoubleMatrix times(Full other)         { return (new Full(this.data())).times(new Full(other.data())); }
	public IDoubleMatrix times(Identity other)     { return (new Full(this.data())).times(new Full(other.data())); }
	public IDoubleMatrix times(Sparse other)       { return (new Full(this.data())).times(new Full(other.data())); }
	public IDoubleMatrix times(Vector other)       { return (new Full(this.data())).times(new Full(other.data())); }
	public IDoubleMatrix times(Zero other)         { return (new Full(this.data())).times(new Full(other.data())); }
	public IDoubleMatrix times(Row other)          { return (new Full(this.data())).times(new Full(other.data())); }
	public IDoubleMatrix times(Column other)       { return (new Full(this.data())).times(new Full(other.data())); }
	public IDoubleMatrix times(Constant other)     { return (new Full(this.data())).times(new Full(other.data())); }

	public abstract IDoubleMatrix times(double scalar);
	
	// Wywołuje funkcję dodającą do macierzy other macierz o rozpoznanym typie.
	public abstract IDoubleMatrix plus(IDoubleMatrix other);
	
	// Zwraca sumę macierzy other i zadanej macierzy.
	public IDoubleMatrix plus(Antidiagonal other) { return (new Full(other.data())).plus(new Full(this.data())); }
	public IDoubleMatrix plus(Diagonal other)     { return (new Full(other.data())).plus(new Full(this.data())); }
	public IDoubleMatrix plus(Full other)         { return (new Full(other.data())).plus(new Full(this.data())); }
	public IDoubleMatrix plus(Identity other)     { return (new Full(other.data())).plus(new Full(this.data())); }
	public IDoubleMatrix plus(Sparse other)       { return (new Full(other.data())).plus(new Full(this.data())); }
	public IDoubleMatrix plus(Vector other)       { return (new Full(other.data())).plus(new Full(this.data())); }
	public IDoubleMatrix plus(Zero other)         { return (new Full(other.data())).plus(new Full(this.data())); }
	public IDoubleMatrix plus(Row other)          { return (new Full(other.data())).plus(new Full(this.data())); }
	public IDoubleMatrix plus(Column other)       { return (new Full(other.data())).plus(new Full(this.data())); }
	public IDoubleMatrix plus(Constant other)     { return (new Full(other.data())).plus(new Full(this.data())); }
	
	public abstract IDoubleMatrix plus(double scalar);

	// Alternatywny zapis odejmowania macierzy
	public IDoubleMatrix minus(IDoubleMatrix other) {
		return this.plus(other.times(-1));
	}

	public abstract IDoubleMatrix minus(double scalar);

	public abstract double get(int row, int column);

	public abstract double[][] data();

	public abstract double normOne();

	public abstract double normInfinity();

	public abstract double frobeniusNorm();

	public abstract String toString();

	public Shape shape() { return shape; }
}
