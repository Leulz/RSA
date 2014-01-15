package edu.ufcg;

public class RSA {
  
	/*
	 * Metodo auxiliar para calcular o Maximo Divisor Comum entre A e B.
	 * ~Gustavo~
	 */
	private static int MDC(int a, int b) {
	    
		a = Math.abs(a);
		b = Math.abs(b);
		    
		if (a < b) {
		      
			int temp;
		      
			temp = a;
			a = b;
			b = temp;
		}
		    
		while (b != 0) {
		      
			int temp;
		      
			temp = a;
			a = b;
			b = temp % b;
		}

		return a;
	}


}
