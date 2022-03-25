package com.logicBank.menus;

import java.util.List;

import com.logicBank.model.Account;
import com.logicBank.model.Card;
import com.logicBank.model.Client;
import com.logicBank.security.Login;
import com.logicBank.security.Register;
import com.logicBank.service.AccountService;
import com.logicBank.service.CardService;
import com.logicBank.service.ClientService;
import com.logicBank.service.MovementService;
import com.logicBank.utiles.Utils;


public class BankMenu {
	
	private AccountService accountService = new AccountService();
	private MovementService movementService = new MovementService();
	private CardService cardService = new CardService();
	private ClientService clientService = new ClientService();
	private Register register = new Register();
	private Login login = new Login();
	
	public BankMenu() {
	}
	
	/* Menu d'inici
	 * Possibilita: Fer login amb un dni registrat, Registrarse si és nou usuari i/o Cancelar
	 */
	public void start() {
		boolean exitMainMenu = false;
		while(!exitMainMenu) {
			System.out.println(WELCOME_MESSAJE);
			int mainMenuOption = Utils.getOptionFromMenu(MAIN_MENU); //Ens retorna l'opció escollida per l'usuari
			switch(mainMenuOption) {
				case 1:
					Utils.showHeader("LOGIN"); //Es un títol informatiu de secció
					Client client = login.userLogin(); //Amb el login obtenim un client amb el que operar dins l'App
					if(client != null) {
						Account account = getClientAccount(client);
						operationsMenu(client, account);
						exitMainMenu = true;
					}
					break;
				case 2:
					Utils.showHeader("REGISTRATION");
					register.registerNewClient();
					break;
				case 3:
					exitMainMenu = true;
					System.out.println("See you soon!");
					break;
			}
		}
	}
	
	/*Menu d'operacions.
	 * Amb el client i el seu compte bancari, mostrem un menu d'opcions per operar
	 */
	private void operationsMenu(Client client, Account account) {
		boolean exit = false;
		while(!exit) {
			Utils.showHeader("OPERATIONS MENU");
			int userSelectedOption = Utils.getOptionFromMenu(BANK_OPERATIONS_MENU);
			switch(userSelectedOption) {
			case 1:
				movementMakerMenu(client, account);
				break;
			case 2:
				movementsViewsMenu(client, account);
				break;
			case 3:
				cardInitialMenu(client, account);
				break;	
			case 4:
				createAddicionalAccount(client);
				break;
			case 5:
				System.out.println("Bye!");
				exit = true;
			}
		}
	}
	
	/*Menú per escollir acció de moviment bancari
	 * Aquest menú ja incorpora la crida a funcions ubicades fora d'aquesta classe
	 * perqué són especialitzades en la construcció de Querys (package Service o capa de negoci) que
	 * després es traslladaran a la capa de dades (package Repository) que es comunica amb la bbdd 
	 */
	private void movementMakerMenu(Client client, Account account) {
		Utils.showHeader("MOVEMENTS MENU");
		int userSelectedOption = Utils.getOptionFromMenu(MOVEMENTS_MAKER_MENU);
		switch(userSelectedOption) {
		case 1:
			movementService.makeNewDeposit(client, account);
			break;
		case 2:
			movementService.makeNewExtract(client, account);
			break;
		case 3:
			movementService.makeTransfer(client, account);
			break;
		}
	}
	
	/*
	 * Menú de control i supervisió de moviments.
	 * Pregunta al client qué vol fer i enllaça amb la funció corresponent a l'opció escollida.
	 */
	private void movementsViewsMenu(Client client, Account account) {
		Utils.showHeader("MOVEMENTS MENU");
		int userSelectedOption = Utils.getOptionFromMenu(MOVEMENT_VIEWS_MENU);
		switch(userSelectedOption) {
		case 1:
			movementService.getAllMovements(client, account);
			break;
		case 2:
			movementService.getAllMovementsBetweenDates(client, account);
			break;
		case 3:
			movementService.printTotalBalance(/*client, */account);
			break;
		}
	}
	
		
						////////////MENU TARGETES/////////////
	
	/*
	 * Comprova si el client té 1 o més targetes;
	 */
	public boolean hasManyCards(Client client, Account account) {
		List<String> listOfcards = cardService.getListOfCardNames(client, account);
		if(listOfcards.size() > 1){
			return true;
		}else {
			return false;
		}
	}
	
	
	/*Menú d'opcions segons el número de targetes del client:
	 * Si en té 2 o més: mostra un menú i en fa escollir una
	 * Si només té una l'escolleix per defecte
	 * Si no en té, demana crear una nova targeta. En cas afirmatiu: prosigueix en la seva creació
	 */
	public void cardInitialMenu(Client client, Account account){
		if(cardService.clientHasCard(client, account)) { 
			Card card;
			if(hasManyCards(client, account)) {
				card = selectCardsMenu(client, account);
			}else{
				card = cardService.getCard(client, account);
			}
			manageCardMenu(client, account, card); //menú d'operacions amb la targeta.
		}else {
			boolean wantToCreate = askToCreateNewCard(); //El client no té targeta. Preguntem si desitja crear una targeta.
			if(wantToCreate) {
				createNewCreditCard(client, account);
			}
		}
	}
	
	/*Funció encarregada de construir un menú amb les múltiples targetes que té l'usuari
	 * i que retorna la Targeta escollida per aquest.
	 */
	private Card selectCardsMenu(Client client, Account account) {
		List<String> listOfCards = cardService.getListOfCardNames(client, account);
		int option = Utils.getOptionFromUnknownLengthMenu(listOfCards);
		return cardService.getCardByCardName(listOfCards.get(option));
	}
	
	/*
	 * Menú de possibles accions per operar amb la targeta
	 * 
	 */
	private void manageCardMenu(Client client, Account account, Card card) {
		boolean exit = false;
		while(!exit) {
			Utils.showHeader("CARD MANAGEMENT MENU");
			int userSelectedOption = Utils.getOptionFromMenu(CARD_MANAGE_MENU);
			switch(userSelectedOption) {
			case 1: 
				System.out.println(card.toString());
				break;
			case 2:
				cardService.updateCardPin(card);
				break;
			case 3:
				createNewCreditCard(client, account);
				break;
			case 4:
				deleteOneCreditCard(client, account);
				exit = true;
				break;
			case 5:
				System.out.println("Bye!");
				exit = true;
			}
		}
	}
	
	/*Elimina una targeta
	 * En cas de que el client tingui varies targetes, en demana quina vol eliminar
	 * En cas que només tingui una l'avisa i demana confirmació. Si el client confirma, s'elimina
	 */
	public void deleteOneCreditCard(Client client, Account account) {
		if(hasManyCards(client, account)){
			Card card = selectCardsMenu(client, account);
			cardService.deleteCard(client, account, card);
		}else{
			System.out.println("You only have one card. Are you sure that you want to delete it?");
			if(Utils.askConfirmation()) {
				Card card = cardService.getCard(client, account);
				cardService.deleteCard(client, account, card);
			}
		}
	}
	
	/*
	 * Creació d'una nova targeta.
	 */
	public void createNewCreditCard(Client client, Account account){
		String cardName;
		boolean nameOk = false;
		do {
			cardName = Utils.askForString("card name", 45); //45: límit maxim de la llargada del atribut que demana la funció.
			Card card = cardService.getCardByCardName(cardName);
			if(card.getNombre() != null) {
				System.err.println("Card's name incompitibilty.");
			}else {
				nameOk = true;
			}
		}while(!nameOk);
		String cardType = Utils.menuTypeOfCard(); //el tipus de tarjeta ve predefinit.
		Card card = new Card(cardName, cardType);
		cardService.createNewCreditCard(client, account, card);
		System.out.println("Your card has been created successfully.");
	}
	
	
	/*
	 * Missatge per sol·licitar confirmació respecte a la creació d'una targeta nova
	 */
	private boolean askToCreateNewCard() {
		System.out.println("Sorry, you still don't have any credit card on LogicBank.");
		System.out.println("Do you want to create one?");
		return Utils.askConfirmation();
	}
	
	
			////////////////////MENU COMPTES BANCARIS////////////////////
	
	
	/* Creació d'un nou compte bancari, addicional. És important remarcar el detall 'addicional',
	 * pel fet que a l'hora de registrar-se un nou client, s'utilitza una altra funció molt 
	 * semblant emplaçada a com.logicBank.security.Register.java : createNewAccountByConsole().
	 * La diferència entre les dues radica en la comprobació del nom, per tal de que un client
	 * no pugui tenir comptes amb el mateix nom.
	 */
	public void createAddicionalAccount(Client client){
		Utils.showHeader("ACCOUNT CREATION");
		System.out.println("Let's create a new Logic-Bank account");
		String nameAccount;
		boolean nameOk = false;
		do {
			nameAccount = Utils.askForString("account name", 45);
			Account account = accountService.getAccountByNameAndClient(client, nameAccount);
			if(account.getNombre() != null) {
				System.err.println("Account name incompatibility.");
			}else {
				nameOk = true;
			}
		}while(!nameOk);
		
		String typeAccount = Utils.menuTypeOfAccount();
		Account newAccount = new Account(nameAccount, typeAccount);
		accountService.insertNewAccount(newAccount);
		newAccount = accountService.getAccountByIban(newAccount.getNumCuenta());
		clientService.createAccountToClient(client.getId(), newAccount.getId());
	}
	
	/*
	 * Retorna true si el client (parámetre) té més de 1 compte i false en cas contrari;
	 */
	private boolean hasManyAccounts(Client client) {
		List<String> listOfIbans = accountService.getListOfIbanFromAccounts(client);
		if(listOfIbans.size() > 1) {
			return true;
		}else {
			return false;
		}
	}
	
	/*
	 * Demana el compte bancari amb el que es vol operar, d'entre tots els que té el client
	 */
	private Account selectAccountMenu(Client client) {
		List<String> listOfIbans = accountService.getListOfIbanFromAccounts(client);
		Account account = new Account();
		System.out.println("With which account do you want to trade with?\n");
		int userSelectedOption = Utils.getOptionFromUnknownLengthMenu(listOfIbans);
		account = accountService.getAccountByIban(listOfIbans.get(userSelectedOption));
		
		return account;
	}
	
	/*
	 * Retorna un compte bancari per operar
	 */
	private Account getClientAccount(Client client) {
		Account account;
		if(hasManyAccounts(client)) {
			account = selectAccountMenu(client);
		}else {
			account = accountService.getOneAccount(client);
		}
		return account;
	}
	
	
				///////////////////DECLARACIÓ DE CONSTANTS///////////////////
	/*
	 * Declaració de constants: missatges, menús, etc.
	 */
	private final String WELCOME_MESSAJE = "\n\tWelcome to your LogicBank App!";
	
	
	private final String[] MAIN_MENU = {"1. Login", "2. Register", "3. Cancel"}; 
	
	private final String[] BANK_OPERATIONS_MENU = {
			"1. Make a new cash movement",
			"2. Monitor your financial movements",
			"3. Manage your card",
			"4. Get another bank account",
			"5. Exit"};
	
	private final String[] MOVEMENTS_MAKER_MENU = {
			"1. Deposit money",
			"2. Withdraw money from ATM",
			"3. Make a new transfer"
	};
	
	private final String[] MOVEMENT_VIEWS_MENU = {
			"1. Check all your movements",
			"2. Check all your movements between dates",
			"3. Check your total balance"
	};
	
	private final String[] CARD_MANAGE_MENU = {
			"1. Check your card details.",
			"2. Change your pin.",
			"3. Get another card associated",
			"4. Delete one of your current cards",
			"5. Exit"
	};
}