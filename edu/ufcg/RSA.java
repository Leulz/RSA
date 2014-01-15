package edu.ufcg;

public class RSA {
  
  
  /*
   * Metodo para calcular o Maximo Divisor Comum - Gustavo
   */
  private static int MDC(int a, int b) {
    
    a = abs(a);
    b = abs(b);
    
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
