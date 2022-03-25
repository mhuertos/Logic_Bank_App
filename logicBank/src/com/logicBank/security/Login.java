package com.logicBank.security;

import java.util.Scanner;

import com.logicBank.model.Client;
import com.logicBank.service.ClientService;
/*
 * Aquesta classe inclou un métode per logejar un nou usuari
 */
public class Login {
	
	private final Scanner scan = new Scanner(System.in);
	private ClientService clientService = new ClientService();

	public Login() {
	}
	
	/* Demana el DNI a l'usuari. Comprova la seva existència. Si existeix
	 * obté la resta de dades del client directament de la bbdd.
	 * Printa un missatge de benvinguda.
	 * Retorna una instància de la classe 'Client' per operar amb ell.
	 */
	public Client userLogin() {
		Client client = null;
		System.out.print("Enter your DNI: ");
		String nif = scan.next();
		scan.nextLine();
		if(clientService.clientExists(nif)) {
			client = clientService.getClientByNif(nif);
			System.out.println("\n\n >> Welcome "+client.getName()+"! <<\n\n");
		}else {		
			System.err.println("INVALID LOGIN: Given NIF wasn't found on database.");
		}
		return client;
	}
}
