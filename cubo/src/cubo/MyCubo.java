package cubo;
/*
 * 
 * Ejercicio 7.
		Tablero mágico. Dado un tablero de tamaño n x n, construir un algoritmo que ubique 
		(si es	posible) n*n números naturales diferentes, entre 1 y un cierto k (con k>n*n), 
		de manera tal que la suma de las columnas y de las filas sea igual a S.

 */

public class MyCubo {

	public static void main(String[] args) {
		int n = 3; // tamaño de la matriz
		int k = 9; // valores posibles 1...k
		int s = 15; // valor que debe sumar la fila y la columna

		int[] solucion = new int[(n * n)];
		for (int i = 0; i < (n * n); i++)
			solucion[i] = 0;

		completarCubo(solucion, n, k, s, 0);
		System.out.println("--FIN--");

	}

	// ---------------

	public static void completarCubo(int[] solucion, int n, int k, int s, int pos) { // metodo recursivo de backtracking

		if (solucion(solucion, n, s))
			imprimirSolucion(solucion, n);
		else {
			if (!poda(solucion, n, k, s, pos)) {
				for (int valor = 1; valor <= k; valor++) {
					if ((!use(solucion, valor, k, n)) && (!solucion(solucion, n, s))) {
						solucion[pos] = valor;
						// imprimirSolucion2(solucion, n);
						completarCubo(solucion, n, k, s, pos + 1);
					}
				}
			}
			if (!solucion(solucion, n, s)) {
				estadoAnterior(solucion, pos);
			}
		}
	}

	public static boolean solucion(int[] solucion, int n, int s) { // retorna verdadero si las filas y columnas suman S
		boolean r = false;
		int[] f = new int[n];
		int[] c = new int[n];

		if (solucion[(n * n) - 1] != 0) {
			contarFilaColumna(solucion, n, f, c);
			for (int i = 0; i < n; i++) // que todas sumen s
				if ((f[i] == s) && (c[i] == s))
					r = true;
				else {
					r = false;
					break;
				}
		}
		return r;

	}

	public static void imprimirSolucion(int[] solucion, int n) { // Imprime el tablero
		int pos = 0;

		System.out.println("--Solucion--");
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				System.out.print(solucion[pos] + ", ");
				pos++;
			}
			System.out.println("");
		}
	}

	public static void imprimirSolucion2(int[] solucion, int n) { // Imprime el tablero pero Lineal

		for (int i = 0; i < (n * n); i++)
			System.out.print(solucion[i] + ", ");
		System.out.println("");

	}

	public static void contarFilaColumna(int[] solucion, int n, int[] f, int[] c) { // calcula las sumas de las filas y
																					// las columans hasta el momento

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				f[i] = f[i] + solucion[i * n + j];
				c[j] = c[j] + solucion[i * n + j];
			}
	}

	public static void estadoAnterior(int[] solucion, int pos) { // vuelve al estado anterior de la solucion para subir
		// un nivel de la rama
		pos--;
		if (pos >= 0)
			solucion[pos] = 0;
	}

	public static boolean sePaso(int[] solucion, int n, int s) { // retorna verdadero si la suma de una columna o una
																	// fila da mas de S
		boolean r = true;
		int[] f = new int[n];
		int[] c = new int[n];

		contarFilaColumna(solucion, n, f, c);
		for (int i = 0; i < n; i++)
			if ((f[i] > s) || (c[i] > s)) {
				r = false;
				break;
			}
		return r;
	}

	public static boolean imposibleLlegar(int[] solucion, int n, int s) { // retorna verdadero si una fila o culumna
																			// completa suma menos de S
		boolean r = true;
		int[] f = new int[n];
		int[] c = new int[n];

		contarFilaColumna(solucion, n, f, c);
		for (int i = 0; i < n; i++) {
			if ((solucion[(((i + 1) * n) - 1)] != 0) && (f[i] < s))
				r = false;
			if ((solucion[((n * (n - 1)) + i)] != 0) && (c[i] < s))
				r = false;
		}
		return r;
	}

	public static boolean poda(int[] solucion, int n, int k, int s, int pos) { // retorna verdadero si ya no hay
																				// solucion por esa rama
		boolean r = false;

		if ((pos == (n * n)) || (!imposibleLlegar(solucion, n, s)) || (!sePaso(solucion, n, s)))
			r = true;
		return r;
	}

	public static boolean use(int[] solucion, int valor, int k, int n) { // retorna verdadero si el valor que voy a usar
																			// ya esta usado en el tablero
		int aux = 0;
		while ((aux < (n * n)) && (solucion[aux] != 0) && (solucion[aux] != valor)) {
			aux++;
		}
		if ((aux < (n * n)) && (solucion[aux] == valor))
			return true;
		else
			return false;
	}

}
