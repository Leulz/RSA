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
    	private static int[] extraiDois(int m) {
    		assert m >= 0;
	    	int i = 0;
    		double potencia = Math.pow(2,i);
	    	int potenciaInteiro = (int) potencia;
    		while ((m & potenciaInteiro) == 0) {
	    	     i += 1;
    		     potencia = Math.pow(2,i);
	    	     potenciaInteiro = (int) potencia;
    		}
	    	int[] retorno = new int[2];
    		retorno[0] = i;
	    	retorno[1] = m>>i;
    		return retorno;    	
    }

}
