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
	
	/*
	 * Metodo auxiliar que retorna um array com x, y e z
	 * sendo que x = MDC (a , b) e
	 * x = a * z + b * z
	 * ~~Gustavo
	 */
	private static int[] EuclidesEstendido(int a, int b) {
		
		int[] retorno = new int[3];
		
		if (a == 0) {
			
			retorno[0] = b;
			retorno[1] = 0;
			retorno[2] = 1;
			
			return retorno;
		
		} else {
			
			int[] valorTemp = EuclidesEstendido(b % a, a);
			
			retorno[0] = valorTemp[0];
			retorno[2] = valorTemp[1];
			retorno[1] = valorTemp[2] - (b / a) * valorTemp[1];
			
			return retorno;
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
        
	public static int modexp(int a, int b, int n) {
        if (b == 0) return 1;
        long t = modexp(a, b/2, n);
        long c = (t * t) % n;
        if (b % 2 == 1)
           c = (c * a) % n;
        return (int) c;
    }
}
