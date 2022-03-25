package com.logicBank.utiles;

import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Utils {
	static Scanner scan = new Scanner(System.in);
	
	private static final String SELECT_MESSAJE = "\nPlease choose one of the following options:\n";
	private static final String ERROR_NOT_INT = "This is not a valid options. "
			+ "Please enter the number corresponding to your preference";
	private static final String ERROR_NOT_VALID_OPTION = "This number is not between available options";
	private static final String ERROR_OPTION = "Please select one option between 1 to ";
	private static final String ERROR_TYPE = "This is not a number, please enter a numeric value.\n"
			+ "Note: Make sure you're using comma to refer decimal values";
	private static final String ERROR_NEGATIVE_NUMBER = "Enter a positive number";
	
	private static final String[] ACCOUNT_TYPE_INITIAL_MENU = {
			"1. Current account",
			"2. Payslip account",
			"3. Saving account",
			"4. Deposit account",
			"5. Need more information about accounts?"};
	
	private static final int ACCOUNT_INFO_OPTION = 5;
	
	private static final String[] ACCOUNT_TYPE_INFO_MENU = {
			"1. Current account",
			"2. Payslip account",
			"3. Saving account",
			"4. Deposit account",
			"5. Exit"};
	
	private static final String CURRENT_ACCOUNT_DESCRIPTION = 
			"The most typical type of account for undertaking essential,\n"
			+ "day-to-day banking activities. The interest rates for current\n"
			+ "accounts that remain in credit are usually quite low,\n"
			+ "if they pay any interest at all.";
	
	private static final String SAVING_ACCOUNT_DESCRIPTION =
			"Offer higher interest rates, but with a limited range of banking\n"
			+ " services and/or limited access to money, particularly at short notice.";
	
	private static final String PAYSLIP_ACCOUNT_DESCRIPTION = 
			"Allows an employer to pay directly into the employee’s account and\n"
			+ "there arewith fewer withdrawal restrictions";
	
	private static final String DEPOSIT_ACCOUNT_DESCRIPTION =
			"As with savings accounts, these are aimed at customers who want to earn\n"
			+ " a higher rate of interest on their money, but this type of account does\n"
			+ " not permit day-to-day banking operations.";
				
	private static final String[] ACCOUNT_TYPE = {
			"Current account",
			"Payslip account",
			"Saving account",
			"Deposit account"};
	
	private static final String[] CARD_TYPE_MENU = {
			"1. VISA",
			"2. MasterCard",
			"3. American Express",
	};
	
	private static final String[] CARD_TYPE = {
			"VISA",
			"MasterCard",
			"American Express",
	};
	
	/* Demana una cadena de text amb un número màxim de caràcters possible. Aquest
	 * control del nº de caràcters és necessari per afavorir la no aparició de 
	 * problemes a la bbdd, al incorporar valors a camps definits amb un límit concret.
	 */
	public static String askForString(String attribute, int maxLength) {
		String s = "";
		boolean dataOk = false;
		while(!dataOk) {
			System.out.print(String.format("Please enter the %s: ", attribute));
			s = scan.nextLine();
			if(!s.isEmpty()) {
				if(s.length() <= maxLength) {
					dataOk = true;
				}else {
					System.err.println(String.format("Please, only use %d characters.", 
							maxLength));
				}
			}
		}
		return s;
	}
	
	/* Demana un número pin, que serà tractat com un 'String'. S'evalua que el pin introduit
	 * tingui el tamany definit per la clase Card, amb la constant 'PIN_LENGTH'
	 */
	public static String askForNewPin(int pin_length) {
		String newPin = "";
		boolean correctPin = false;
		while(!correctPin) {
			System.out.println(String.format("Please, select %d numbers to set your pin: ", pin_length));
			System.out.print("> ");
			if(scan.hasNextInt()) {
				int selectedPin = scan.nextInt();
				scan.nextLine();
				String stringPin = String.valueOf(selectedPin);
				if(stringPin.length() != pin_length) {
					System.err.println(String.format("Please, only use %d numbers to set your pin.",
							pin_length));
				}else {
					newPin = stringPin;
					correctPin = true;
				}
			}else {
				System.err.println("Please, only use integer numbers to configure your pin");
				scan.nextLine();
			}
		}
		return newPin;
	}
	
	 
	/* Llegeix la resposta, en forma de número, d'un usuari, controlant que aquest 
	 * número es troba dins del rang de respostes possibles.
	 * Retorna el número amb l'opció corresponent;
	 */
	public static int getUserPickBetween(String[] menu) {
		boolean haveAnOption = false;
		int number = 0;
		while(!haveAnOption) {
			System.out.print("> ");
			if(scan.hasNextInt()) {
				number = scan.nextInt();
				scan.nextLine();
				if(number <= menu.length && number >= 1) {
					haveAnOption = true;
				}else {
					System.err.println(ERROR_NOT_VALID_OPTION);
					System.out.println(ERROR_OPTION + menu.length);
				}
			}else {
				System.err.println(ERROR_NOT_INT);
				scan.nextLine();
			}
		}
		return number;
	}
	
	/* Sobrecarrega de la funció getUserPickBetween(String[] menu)
	 * amb canvi de paràmetre;
	 */
	public static int getUserPickBetween(List<String> menu) {
		boolean haveAnOption = false;
		int number = 0;
		while(!haveAnOption) {
			System.out.print("> ");
			if(scan.hasNextInt()) {
				number = scan.nextInt();
				scan.nextLine();
				if(number <= menu.size() && number >= 1) {
					haveAnOption = true;
				}else {
					System.err.println(ERROR_NOT_VALID_OPTION);
					System.out.println(ERROR_OPTION + menu.size());
				}
			}else {
				System.err.println(ERROR_NOT_INT);
				scan.nextLine();
			}
		}
		return number;
	}
	
	/*Demana una data. Adapta el missatge de sol·licitud de dades en funció del temps,
	 * p.e.: "desde", "fins"
	 * Retorna una data en format Date (package java.sql.Date);
	 */
	public static java.sql.Date askForDate(String adverb) {
		boolean allDataOk = false;
		Date date = new Date(0);
		while(!allDataOk) {
			System.out.print("Enter date (YYYY-MM-DD) "+adverb+" you want to see movements: ");
			String fecha = scan.next();
			if(acceptDateFormat(fecha)) {
				date = Date.valueOf(fecha);
				allDataOk = true;
			}
		}
		return date;
	}
	
	/* Controla que el format de les dates segueix: YYYY-MM-DD
	 * 1er Controla que tingui un tamany concret 
	 * 2on Controla que els guions estan a les posicions on s'espera i no a d'altres.
	 * 3er Controla que tots els valors entre guions son números
	 * Retorna booleà
	 */
	public static boolean acceptDateFormat(String fecha) {
		boolean formatOk = true;
		if(fecha.length() == 10) {
			if(fecha.charAt(4) == '-' && fecha.charAt(7) == '-') {
					String[] numeros = fecha.split("-");
					for(int i = 0; i < numeros.length; i++) {
						for(int j = 0; j < numeros[i].length(); j++) {
							if(!Character.isDigit(numeros[i].charAt(j))) {
								formatOk = false;
								System.err.println("Invalid format for date. Please use integer numbers");
							}
						}
					}
					
			}else {
				System.err.println("Invalid format for date. Please enter [YYYY-MM-DD]");
				formatOk = false;
			}
		}else {
			System.err.println("Invalid format for date. Please enter [YYYY-MM-DD]");
			formatOk = false;
		}
		return formatOk;
	}


	/* Demana un double i el retorna. Incorpora un bucle per controlar quan l'usuari no insereix 
	 * números negatius o caracters no numèrics
	 */
	public static double askForQuantity() {
		boolean quantityOk = false;
		double quantity = 0.0;
		while(!quantityOk) {
			System.out.print("> ");
			if(scan.hasNextDouble()) {
				quantity = scan.nextDouble();
				scan.nextLine();
				if(quantity <= 0) {
					System.err.println(ERROR_NEGATIVE_NUMBER);
				}else {
					quantityOk = true;
				}
			}else {
				System.err.println(ERROR_TYPE);
				scan.nextLine();
			}
		}
		return quantity;
	}
	
	/* Demana confirmació a un usuari a partir de la introducció de valors
	 * 1 o 0;
	 */
	public static boolean askConfirmation() {
		boolean exit = false;
		boolean confirmation = false;
		while(!exit) {
			System.out.println("Please, press 1 to confirm or number 0 to refuse operation");
			System.out.print("> ");
			int option = 1;
			if(scan.hasNextInt()) {
				option = scan.nextInt();
				scan.nextLine();
				if(option == 0) {
					exit = true;
				}else if(option == 1) {
					exit = true;
					confirmation = true;
				}else {
					System.err.println("That is not a valid option");
				}
			}else {
				System.err.println("That is not a valid option");
			}
		}
		return confirmation;
	}
	
	/*Genera una cadena de text composta per números aleators
	 * Retorna String
	 */
	public static String generarNumeroAleatorio(int numCifras) {
		Random random = new Random();
		String numero = "";
		for(int i = 0; i < numCifras; i++) {
			numero += (String.valueOf(random.nextInt(9)));
		}
		return numero;
	}
	
	public static String menuTypeOfCard() {
		int userSelectedOption = getOptionFromMenu(CARD_TYPE_MENU);
		return CARD_TYPE[userSelectedOption - 1];
	}
	
	public static String menuTypeOfAccount() {
		String type = "";
		do {
			showHeader("ACCOUNT CREATION");
			int userSelectedOption = getOptionFromMenu(ACCOUNT_TYPE_INITIAL_MENU);
			if(userSelectedOption == ACCOUNT_INFO_OPTION) {
				menuInfoTypeOfAccount();
			}else{
				type = ACCOUNT_TYPE[userSelectedOption - 1];
			}
		}while(type.isEmpty());
		return type;
	}
	
	
	/* Menú descriptiu
	 * Desplega descripció addicional sobre les caracteristiques de cada compte
	 * Merament informatiu, per ajudar al client a decidir;
	 */
	public static void menuInfoTypeOfAccount() {
		boolean exit = false;
		while(!exit) {
			showHeader("INFORMATION ABOUT TYPES OF ACCOUNT");
			int userSelectedOption = getOptionFromMenu(ACCOUNT_TYPE_INFO_MENU);
			switch(userSelectedOption) {
			case 1: 
				System.out.println(CURRENT_ACCOUNT_DESCRIPTION);
				break;
			case 2:
				System.out.println(PAYSLIP_ACCOUNT_DESCRIPTION);
				break;
			case 3:
				System.out.println(SAVING_ACCOUNT_DESCRIPTION);
				break;
			case 4:
				System.out.println(DEPOSIT_ACCOUNT_DESCRIPTION);
				break;
			case 5:
				exit = true;
			}
		}
	}
	
	/*Desplega un menú encapçalat per números en funció del nombre d'opcions
	 * Aquesta funció está pensada per menús dels quals desconeixem els valors préviament
	 * ja que es van definint a mesura que avanci el funcionament de l'app, p.e. el
	 * número de targetes que tingui en un moment donat.
	 * Retorna l'opció escollida per l'usuari.
	 */
	public static int getOptionFromUnknownLengthMenu(String[] menu) {
		for(int i = 0; i < menu.length; i++) {
			System.out.println((i+1)+". "+menu[i]);
		}
		return Utils.getUserPickBetween(menu) - 1;
	}

	/*Desplega un menú amb opcions conegudes préviament a la posada en marxa de l'app.
	 * Normalment s'utilitzará per menús amb opcions ja conegudes, com p.e. el tipus 
	 * d'opcions que pot escollir fer l'usuari en la següent pantalla, 
	 * el tipus de comptes o targetes que pot escollir.
	 * Retorna l'opció escollida per l'usuari.
	 */
	public static int getOptionFromMenu(String[] menu) {
		showMenu(menu);
		return Utils.getUserPickBetween(menu);
	}
	
	//print by screen all menu's options
	public static void showMenu(String[] menu) {
		System.out.println(SELECT_MESSAJE);
		for(String option: menu) {
			System.out.println(option);
		}
	}
	
	
	/*
	 * Sobrecarrega de funcións per possibilitar el treball amb la classe List;
	 * Fa el mateix que el mètode getOptionFromUnknownLengthMenu(String[] menu);
	 */
	public static int getOptionFromUnknownLengthMenu(List<String> menu) {
		for(int i = 0; i < menu.size(); i++) {
			System.out.println((i+1)+". "+menu.get(i));
		}
		return Utils.getUserPickBetween(menu) - 1;
	}
	
	/*
	 * Sobrecarrega de funcións per possibilitar el treball amb la classe List;
	 * Fa el mateix que el mètode getOptionFromMenu(String[] menu);
	 */
	public static int getOptionFromMenu(List<String> menu) {
		showMenu(menu);
		return Utils.getUserPickBetween(menu);
	}
	
	/*
	 * Sobrecarrega de funcións per possibilitar el treball amb la classe List;
	 * Fa el mateix que el mètode showMenu(String[] menu);
	 */
	public static void showMenu(List<String> menu) {
		System.out.println(SELECT_MESSAJE);
		for(String option: menu) {
			System.out.println(option);
		}
	}
	
	/*
	 * Mostra per pantalla un encapçalament
	 */
	public static void showHeader(String header) {
		printDash(header);
		System.out.println(header);
		printDash(header);
	}
	
	/*
	 * Mostra per pantalla una linea puntejada de 'guions' al voltant de l'encapçalament
	 */
	public static void printDash(String header) {
		for(int i = 0; i < header.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}
}
