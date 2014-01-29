package edu.ufcg;

import java.util.Arrays;
import java.util.Scanner;

public class Teste {
	public static void main(String[] args) {
		RSA chave = null;
		int[] mensagemCodificada = null;
		final String fimDeLinha = System.getProperty("line.separator");
		final int criarChave = 1;
		final int chavePub = 2;
		final int codificarComPub = 3;
		final int decodificarComPriv = 4;
		final int codificarComPriv = 5;
		final int decodificarComPub = 6;
		final int sair = 7;

		final String prompt = "Digite a opcao desejada:"
				+ fimDeLinha
				+ criarChave
				+ ". Criar chave RSA (n,e,d)."
				+ fimDeLinha
				+ chavePub
				+ ". Visualizar a chave publica (n,e)."
				+ fimDeLinha
				+ codificarComPub
				+ ". Codificar uma mensagem com a chave publica."
				+ fimDeLinha
				+ decodificarComPriv 
				+ ". Decodificar uma mensagem criptografada com a chave publica."
				+ fimDeLinha 
				+ codificarComPriv
				+ ". Codificar uma mensagem com a chave privada." 
				+ fimDeLinha
				+ decodificarComPub
				+ ". Decodificar uma mensagem criptografada com a chave privada."
				+ fimDeLinha 
				+ sair
				+ ". Sair"
				+fimDeLinha
				+"Opcao: ";

		Scanner sc = new Scanner(System.in);

		System.out.println(prompt);
		int escolha = sc.nextInt();
		while (escolha != sair) {
			switch(escolha) {
			case criarChave:
				chave = new RSA();
				mensagemCodificada = null;
				System.out.println("Chave RSA criada com sucesso."+fimDeLinha);
				break;
			case chavePub:
				if (chave==null) {
					System.out.println("Chave RSA ainda nao definida. Escolha a opcao 1."+fimDeLinha);
					break;
				}
				System.out.println("Chave publica: "+Arrays.toString(RSA.getChavePublica()));
				break;
			case codificarComPub:
				if (chave==null) {
					System.out.println("Chave RSA ainda nao definida. Escolha a opcao 1."+fimDeLinha);
					break;
				}
				System.out.println("Digite a mensagem a ser codificada: ");
				String mensagem = sc.next();
				mensagemCodificada = RSA.criptografarComChave(mensagem, RSA.getChavePublica());
				System.out.println("Mensagem codificada: "+Arrays.toString(mensagemCodificada));
				break;
			case decodificarComPriv:
				if (chave==null) {
					System.out.println("Chave RSA ainda nao definida. Escolha a opcao 1."+fimDeLinha);
					break;
				}
				if (mensagemCodificada==null) {
					System.out.println("Nao ha uma mensagem codificada."+fimDeLinha);
					break;
				}
				System.out.println("Mensagem decodificada: "+RSA.descriptografarComChavePrivada(mensagemCodificada));
				break;
			case codificarComPriv:
				if (chave==null) {
					System.out.println("Chave RSA ainda nao definida. Escolha a opcao 1."+fimDeLinha);
					break;
				}
				System.out.println("Digite a mensagem a ser codificada: ");
				String mensagemPriv = sc.next();
				mensagemCodificada = RSA.criptografarComChavePrivada(mensagemPriv);
				System.out.println("Mensagem codificada: " +Arrays.toString(mensagemCodificada));
				break;
			case decodificarComPub:
				if (chave==null) {
					System.out.println("Chave RSA ainda nao definida. Escolha a opcao 1."+fimDeLinha);
					break;
				}
				if (mensagemCodificada==null) {
					System.out.println("Nao ha uma mensagem codificada."+fimDeLinha);
					break;
				}
				System.out.println("Mensagem decodificada: "+RSA.descriptografarComChave(mensagemCodificada, chave.getChavePublica()));
				break;
			default:
				System.err.println("Opcao " + escolha + " desconhecida.");
				break;
			}
			sc.nextLine();
			System.out.println(prompt);
			escolha = sc.nextInt();
		}
		sc.close();
		System.out.println("Programa finalizado.");
		}
}
