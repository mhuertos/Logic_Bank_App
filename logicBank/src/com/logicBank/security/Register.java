package com.logicBank.security;

import java.util.Scanner;

import com.logicBank.model.Account;
import com.logicBank.model.Client;
import com.logicBank.service.AccountService;
import com.logicBank.service.ClientService;
import com.logicBank.utiles.Utils;

public class Register {
	
	private Scanner scan = new Scanner(System.in);
	private AccountService accountService = new AccountService();
	private ClientService clientService = new ClientService();
	private final String[] ATTRIBUTE_FIELDS = {"Name: ", "Lastname: ", "NIF: ", "Date of birth (YYYY-MM-DD): "};
	private final int NIF_MAX_CHARACTERS = 9;
	
	public Register() {
	}
	
	
	/* Crea un nou compte.
	 * Primer demana un nom pel compte. Després demana el tipus de compte que serà
	 * Per últim inicialitza un nou objecte de tipus 'Account' amb els atributs 
	 * demanats per consola i retorna aquesta instància de la clase 'Account';
	 */
	public void createNewAccountByConsoleInput(int idClient) {
		Utils.showHeader("CREATE NEW ACCOUNT");
		System.out.println("\nLet's create a new Logic-Bank account");
		String nameAccount = Utils.askForString("account name", 45);
		String typeAccount = Utils.menuTypeOfAccount();
		Account currentOperatingAccount = new Account(nameAccount, typeAccount);
		accountService.insertNewAccount(currentOperatingAccount);
		int idAccount = accountService.getAccountId(currentOperatingAccount);
		clientService.createAccountToClient(idClient, idAccount);
		System.out.println("Account created successfully.");
	}
	
	
	/* Donar d'alta a l'usuari. Demana les dades bàsiques i les guarda en una array amb un tamany
	 * equivalent a l'array de strings definida a la capcelera de la classe;
	 * Identifica que el dni i la data tenen un format correcte.
	 * Finalment inicialitza un objecte de la classe 'Client' amb els valors emmagatzemats
	 * a l'array 'userEnteredValues'
	 */
	public Client createNewClientByConsoleInput() {
		Utils.showHeader("CREATE NEW CLIENT PROFILE");
		System.out.println("\nIt's a pleasure that you join to Logic-Bank");
		System.out.println("Please, enter the following information: \n");
		boolean allDataOk = false;
		String[] userEnteredValues = new String[ATTRIBUTE_FIELDS.length];
		while(!allDataOk) {
			for(int i = 0; i < ATTRIBUTE_FIELDS.length; i++) {
				System.out.print(ATTRIBUTE_FIELDS[i]);
				userEnteredValues[i] = scan.nextLine();
			}
			if(correctNif(userEnteredValues[2]) && Utils.acceptDateFormat(userEnteredValues[3])) {
				allDataOk = true;
			}
		}
		return new Client(userEnteredValues[0], userEnteredValues[1], 
				userEnteredValues[2], userEnteredValues[3]);
	}
	
	public void registerNewClient() {
		Client client = createNewClientByConsoleInput();
		clientService.insertNewClient(client);
		int idClient = clientService.getClientId(client);
		createNewAccountByConsoleInput(idClient);
	}
	
	
	public boolean correctNif(String nif) {
		boolean correctNif = true;
		if(nif.length() != NIF_MAX_CHARACTERS) { //Comprova que el NIF té 9 caracters
			System.err.println("Invalid length for NIF");
			correctNif = false;
		}else {
			if(clientService.clientExists(nif)) { //Comprova que no hi ha cap altre NIF dins la bbdd
				System.err.println("This nif were already registered.");
				correctNif = false;
			}else {
				String nifNumberSequence = nif.substring(0, 8); //Extraiem la part numérica del NIF;
				if(checkNifNumber(nifNumberSequence)) {  //Evaluem que cada caràcter de la part numèrica: evidentment és un número
					if(!checkNifLetter(nif.charAt(NIF_MAX_CHARACTERS-1))) { //Evaluem que l'últim caràcter és una lletra					System.err.println("Not a valid letter to NIF");
						correctNif = false;
						System.out.println("NIF letter it's not Ok.");
					}
				}else {
					System.err.println("NIF numeric sequence it's wrong.");
					correctNif = false;
				}
			}
		}
		return correctNif;
	}
	
	public boolean checkNifNumber(String nifNumber) {
		boolean numberSequenceOk = true;
		int index = 0;
		do {
			if(!Character.isDigit(nifNumber.charAt(index))) {
				numberSequenceOk = false;
			}
			index++;
		}while(numberSequenceOk && index < nifNumber.length());
		return numberSequenceOk;
	}
	
	public boolean checkNifLetter(char letter) {
		boolean isLetter = true;
		System.out.println(letter);
		if(!Character.isLetter(letter)) {
			isLetter = false;
		}
		return isLetter;
	}
}
