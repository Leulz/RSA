package edu.ufcg;

public class RSA {
  
	/*
	 * Metodo auxiliar para calcular o Maximo Divisor Comum entre A e B.
	 * ~~Gustavo
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
	
	/*
	 * Metodo auxiliar para testar se dois numeros sao coprimos.
	 * ~~Gustavo
	 */
	private static boolean coprimo(int a, int b) {
		
		if (MDC(a,b) == 1) {
			
			return true;
		} else {
			
			return false;
		}
		
	}


}
