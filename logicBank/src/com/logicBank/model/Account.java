package com.logicBank.model;

import java.sql.Date;

import com.logicBank.utiles.Utils;

//Clase que representa l'objecte 'Cuenta' de la bbdd para poderla gestionar entre clases;
public class Account {

	private int id;
	private String nombre;
	private String numCuenta = "ES01 2100 5678 90 "; //por defecto generamos la mitad del número de cuenta
	private Date date_create;
	private String tipo_cuenta;
	private final String TABLE_NAME = "cuenta";
	
	public Account() {
		
	}
	
	public Account(String nombre, String tipo_cuenta) {
		this.nombre = nombre;
		long millis = System.currentTimeMillis(); //genera els milisegons fins el día d'avui.
		this.date_create = new Date(millis); //transforma els milisegons en la data actual.
		this.numCuenta = this.numCuenta.concat(Utils.generarNumeroAleatorio(10)); //concatena la part 'per defecte' + una part generada aleatoriament
		this.tipo_cuenta = tipo_cuenta;
	}
	
	
	public Account(String nombre, String numCuenta, Date date_create, String tipo_cuenta) {
		this.nombre = nombre;
		this.numCuenta = numCuenta;
		this.date_create = date_create;
		this.tipo_cuenta = tipo_cuenta;
	}
	
	//getters i setters

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumCuenta() {
		return numCuenta;
	}

	public void setNumCuenta(String numCuenta) {
		this.numCuenta = numCuenta;
	}

	public Date getDate_create() {
		return date_create;
	}

	public void setDate_create(Date date_create) {
		this.date_create = date_create;
	}

	public String getTipo_cuenta() {
		return tipo_cuenta;
	}

	public void setTipo_cuenta(String tipo_cuenta) {
		this.tipo_cuenta = tipo_cuenta;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Cuenta [id="+id + ", nombre=" + nombre + ", numCuenta=" + numCuenta + ", date_create=" + date_create
				+ ", tipo_cuenta=" + tipo_cuenta + "]";
	}

	/*
	 * Construeix la 'query' a partir dels valors dels atributs de la clase 'Account'
	 * Retorna la query per ser passada a la capa de comunicació amb la bbdd.
	 */
	public String getQuery() {
		return "INSERT INTO "+TABLE_NAME+" VALUES (DEFAULT, '"+this.nombre+"', '"+this.date_create+"', '"
		+ this.numCuenta+"', '"+this.tipo_cuenta+"')";
	}
	
	

}
