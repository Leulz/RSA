package edu.ufcg;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class RSA {
	
	private static int[] chavePublica;
	private static int[] chavePrivada;
	
	/*
	 * Metodo auxiliar para calcular o Maximo Divisor Comum entre A e B. 
	 */
	private static int mdc(int a, int b) {

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
	 */
	private static boolean coprimo(int a, int b) {

		if (mdc(a,b) == 1) {

			return true;
		} else {

			return false;
		}

	}

	/*
	 * Metodo auxiliar que retorna um array com x, y e z
	 * sendo que x = MDC (a , b) e
	 * x = a * z + b * z
	 */
	private static int[] euclidesEstendido(int a, int b) {
		if (b == 0)
			return new int[] { a, 1, 0 };

		int[] vals = euclidesEstendido(b, a % b);
		int d = vals[0];
		int c = vals[2];
		int f = vals[1] - (a / b) * vals[2];
		return new int[] { d, c, f };
	}

	/*
	 * Metodo auxiliar para calcular o inverso multiplicativo de a mod m.
	 * Como um valor positivo entre 0 e m-1.
	 */
	private static int modInv(int a, int m) {

		if (!(coprimo(a, m))) {

			return 0;
		} else {
			BigInteger bi1 = new BigInteger(Integer.toString(a));
			BigInteger bi2 = new BigInteger(Integer.toString(m));

			return bi1.modInverse(bi2).intValue();
		}
		
	}	
	
	/*
	 * Testa se um numero e realmente um primo.
	 * Retorna true se for e false se nao for.
	 */
	private static boolean testePrimo(int n) {

		if (n == 1) {

			return false;
		}

		for (int i = 2; i <= Math.sqrt(n); i++) {

			if (n % i == 0) {

				return false;
			}
		}

		return true;
	}

	/*
	 * Retorna um array de tamanho k+1, se retorno[i] == 1, i e primo.
	 * se retorno[i] == 0, i nao e primo.
	 */
	private static int[] primoSieve(int k) {

		int[] resultado = new int[k+1];

		for (int i = 1; i <= k; i++) {

			if (testePrimo(i)) {

				resultado[i] = 1;
			} else {

				resultado[i] = 0;
			}
		}

		return resultado;
	}
	/*Retorna uma lista de inteiros [s,d] tal que o argumento m = Math.pow(2,s)*d
	 */
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
	/*Retorna um inteiro c tal que c = Math.pow(a,b) mod n	 * 
	 */
	private static int modexp(int a, int b, int n) {
		if (b == 0) return 1;
		long t = modexp(a, b/2, n);
		long c = (t * t) % n;
		
		if (b % 2 == 1)
			c = (c * a) % n;
		
		return (int) c;
	}
	/*
	 * Retorna true ou false dependendo de se "n" é ou não
	 * capaz de ser primo, usando-se o parâmetro "a" internamente
	 */
	private static boolean testaSeComposto(int a, int n) {
		int[] tupla2 = extraiDois(n-1);

		int s = tupla2[0];
		int d = tupla2[1];

		int x = modexp(a,d,n);
		if ((x==1) || (x==(n-1))) {
			return true;
		}else {
			for (int i=1; i<s;i++) {
				x = modexp(x,2,n);
				if (x==1) return false;
				if (x==(n-1)) return true;
			}
			return false;
		}
	}
	
	private static boolean millerRabin(int n,int k) {
		assert n>=1;
		assert k>0;

		if (n==2) return true;
		if (n%2==0) return false;

		int[] tupla2 = extraiDois(n-1);

		int s = tupla2[0];
		int d = tupla2[1];
		
		assert (Math.pow(2, s) * d) == n-1;
		
		for (int i=0;i<k;i++) {
			Random a = new Random();
			int numero = a.nextInt(n-2);
			while (numero<2) {
				numero = a.nextInt(n-2);
			}
			if (testaSeComposto(numero,n) == false) return false;
		}
		return true;
	}
	/*
	 * Retorna um primo aleatório mais ou menos entre a e b (pode ser maior que b).
	 * Lançará uma exceção caso as tentativas de se achar um primo superem
	 * 10*Math.log(c)+3
	 */
	private static Integer retornaUmPrimo(int a, int b, int k) throws Exception{
		Random c = new Random();
		int numero = (b-a) + c.nextInt(a+1);
		
		for (int i=0;i<10*Math.log(numero)+3;i++) {
			if (millerRabin(numero, k)) {
				return numero;
			}else {
				numero++;
			}
		}
		throw new Exception("Tentativas de achar um primo no intervalo dado foram esgotadas.");
	}
	/*
	 * Retorna uma chave RSA [n,e,d], sendo "n" o mod da chave, 
	 * "e" o expoente da chave pública e "d" o expoente da chave privada
	 */
	private static int[] novaChave(int a, int b, int k) {
		int p = 0, q = 0, numero = 0;
		int[] retorno = new int[3];
		
		try {
			p = retornaUmPrimo(a,b,k);
			while (true) {
				q = retornaUmPrimo(a,b,k);
				if (q!=p) {
					break;
				}
			}
		}catch (Exception e) {
			e.getMessage();
		}

		int n = p*q;
		int m = (p-1) * (q-1);

		while (true) {
			Random c = new Random();
			numero = c.nextInt(m);
			while (numero==0) {
				numero = c.nextInt(m);
			}
			if (coprimo(m, numero)) {
				break;
			}
		}
		int d = modInv(numero, m);
		retorno[0] = n;
		retorno[1] = numero;
		retorno[2] = d;
		return retorno;
	}
	/*
	 * Codifica uma string numa lista de números, usando-se o cast
	 * (int) para isso .
	 */
	private static int[] stringParaListaNumeros(String string) {
		int[] listaRetorno = new int[string.length()]; 
		
		for (int i=0; i<string.length(); i++) {
			listaRetorno[i] = (int) string.charAt(i);
		}
		return listaRetorno;
	}
	/*
	 * Método inverso de stringParaListaNumeros.
	 */
	private static String listaNumerosParaString(int[] listaNumeros) {
		String retorno = "";
		
		for (int i=0;i<listaNumeros.length; i++) {
			retorno+=(char)listaNumeros[i];
		}
		return retorno;
	}
	/*
	 * Transforma uma lista de números (string codificada) em um ArrayList de inteiros,
	 * no qual cada elemento representa "bloco" numeros da lista de números passada
	 * como argumento, codificados tal que se possa recuperar quais números eram na
	 * lista de números.
	 */
	
	private static ArrayList<Integer> listaNumeroParaBlocos(int[] listaNumeros, int bloco) {
		ArrayList<Integer> listaRetorno = new ArrayList<Integer>();
		ArrayList<Integer> listaCopia = new ArrayList<Integer>();
		Random c = new Random();
		
		for (int i=0; i<listaNumeros.length;i++) {
			listaCopia.add(listaNumeros[i]);
		}
		
		if (listaCopia.size()%bloco!=0) {
			int num = Math.abs(bloco-(listaCopia.size()%bloco));
			for (int i=0;i<num;i++) {
				listaCopia.add(c.nextInt(95) + 32);
			}
		}
		
		for (int i=0;i<listaCopia.size();i+=bloco) {
			int numero = 0;
			for (int j=0;j<bloco;j++) {
				numero+=listaCopia.get(i+j)<<(8*(bloco-j-1));
			}
			listaRetorno.add(numero);
		}
		return listaRetorno;
	}

	/*
	 * Funcao inversa ao listaNumeroParaBlocos.
	 * 
	 */
	private static int[] blocosParaListaNumero(ArrayList<Integer> blocos, int tamanhoBlocos) {
		int[] listaRetorno = new int[blocos.size()*tamanhoBlocos];
		ArrayList<Integer> listaCopia = new ArrayList<Integer>();
		int indice = 0;

		for (int e : blocos) {
			listaCopia.add(e);
		}

		for (int numBloco : listaCopia) {
			ArrayList<Integer> listaProcessar = new ArrayList<Integer>();

			for (int i = 0; i < tamanhoBlocos; i++) {

				listaProcessar.add(numBloco % 256);
				numBloco >>= 8;
			}

			Collections.reverse(listaProcessar);

			for (int e : listaProcessar) {
				listaRetorno[indice] = e;
				indice++;
			}
		}

		return listaRetorno;
	}
	/*
	 * Criptografa uma mensagem, sendo "modN" o mod da chave RSA, e "e" o expoente
	 * da chave pública.
	 */
	private static int[] criptografar(String mensagem, int modN, int e) {
		int[] listaNumeros = stringParaListaNumeros(mensagem);
		ArrayList<Integer> listaBlocos = listaNumeroParaBlocos(listaNumeros, 3);
		int[] listaRetorno = new int[listaBlocos.size()];
		
		for(int i=0;i<listaBlocos.size();i++) {
			listaRetorno[i] = modexp(listaBlocos.get(i), e, modN);
		}
		return listaRetorno;
	}
	/*
	 * Inverso de criptografar, sendo "d" o expoente da chave privada.
	 */
	private static String descriptografar(int[] mensagem, int modN, int d) {
		ArrayList<Integer> blocoNumeros = new ArrayList<Integer>();
		
		for(int i=0;i<mensagem.length;i++) {
			blocoNumeros.add(modexp(mensagem[i], d, modN));
		}
		
		int[] listaNumeros = blocosParaListaNumero(blocoNumeros, 3);
		
		return listaNumerosParaString(listaNumeros);
	}
	/*
	 * Cria uma chave RSA.
	 */
	public RSA() {
		int[] chaves = novaChave(4000,40000,50);
		chavePublica = new int[] { chaves[0], chaves[1] };
		chavePrivada = new int[] { chaves[0], chaves[2] };
	}
	/*
	 * Retorna a chave pública.
	 */
	public static int[] getChavePublica() {
		return chavePublica;
	}
	/*
	 * Criptografa uma mensagem usando-se "d" como expoente da criptografia.
	 */
	public static int[] criptografarComChavePrivada(String mensagem) {
		
		return criptografar(mensagem, chavePrivada[0], chavePrivada[1]);
	}
	/*
	 * Criptografa uma mensagem usando-se "e" como expoente da criptografia.
	 */
	public static int[] criptografarComChave(String mensagem, int[] chave) {
		
		return criptografar(mensagem, chave[0], chave[1]);
	}
	/*
	 * Descriptografa uma mensagem criptografada com chave pública.
	 */
	public static String descriptografarComChavePrivada(int[] mensagem) {
		
		return descriptografar(mensagem, chavePrivada[0], chavePrivada[1]);
	}
	/*
 	 * Descriptografa uma mensagem criptografada com chave privada.
	 */
	public static String descriptografarComChave(int[] mensagem, int[] chave) {
		
		return descriptografar(mensagem, chave[0], chave[1]);
	}
	
	/*public static void main(String[] args) {
		RSA chave = new RSA();
		String mensagem = "Ser ou não ser.";
		System.out.println(Arrays.toString(getChavePublica()));
		int[] mensagemCod = criptografarComChave(mensagem, getChavePublica());
		System.out.println(Arrays.toString(criptografarComChave(mensagem, getChavePublica())));
		System.out.println(descriptografarComChavePrivada(mensagemCod));
	}*/
}