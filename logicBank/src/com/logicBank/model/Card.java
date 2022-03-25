package com.logicBank.model;

import java.sql.Date;
import java.time.LocalDate;

import com.logicBank.utiles.Utils;


public class Card {
	
	private int id;
	private String nombre;
	private String tipo_targeta;
	private String numtargeta;
	private Date creation_date;
	private Date expiration_date;
	private String pin;
	private String numSeguridad;
	private double limite;
	private double TAE;
	public static final int PIN_LENGTH = 4;
	
	public Card() {
	}
	
	@SuppressWarnings("static-access")
	public Card(String nombre, String tipo_targeta) {
		super();
		this.nombre = nombre;
		this.tipo_targeta = tipo_targeta;
		this.numtargeta = Utils.generarNumeroAleatorio(16);;
		long millis = System.currentTimeMillis();
		this.creation_date = new Date(millis);
		LocalDate ld = LocalDate.now();
		LocalDate inAYear = ld.plusYears(3);
		this.expiration_date = this.creation_date.valueOf(inAYear);
		this.pin = Utils.generarNumeroAleatorio(4);
		this.numSeguridad = Utils.generarNumeroAleatorio(3);
		this.limite = 1500;
		this.TAE = 20.50;
	}
	

	@SuppressWarnings("static-access")
	public Card(int id, String nombre, String tipo_targeta, 
			String numtargeta, String pin, String numSeguridad, 
			double limite, double tAE) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo_targeta = tipo_targeta;
		this.numtargeta = numtargeta;
		long millis = System.currentTimeMillis();
		this.creation_date = new Date(millis);
		LocalDate ld = LocalDate.now();
		LocalDate inAYear = ld.plusYears(3);
		this.expiration_date = this.creation_date.valueOf(inAYear);
		this.pin = pin;
		this.numSeguridad = numSeguridad;
		this.limite = limite;
		TAE = tAE;
	}
	
	


	public Card(int id, String nombre, String tipo_targeta, String numtargeta, Date creation_date,
			Date expiration_date, String pin, String numSeguridad, double limite, double tAE) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo_targeta = tipo_targeta;
		this.numtargeta = numtargeta;
		this.creation_date = creation_date;
		this.expiration_date = expiration_date;
		this.pin = pin;
		this.numSeguridad = numSeguridad;
		this.limite = limite;
		TAE = tAE;
	}
	
	


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getTipo_targeta() {
		return tipo_targeta;
	}


	public void setTipo_targeta(String tipo_targeta) {
		this.tipo_targeta = tipo_targeta;
	}


	public String getNumtargeta() {
		return numtargeta;
	}


	public void setNumtargeta(String numtargeta) {
		this.numtargeta = numtargeta;
	}


	public Date getCreation_date() {
		return creation_date;
	}


	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}


	public Date getExpiration_date() {
		return expiration_date;
	}


	public void setExpiration_date(Date expiration_date) {
		this.expiration_date = expiration_date;
	}


	public String getPin() {
		return pin;
	}


	public void setPin(String pin) {
		this.pin = pin;
	}


	public String getNumSeguridad() {
		return numSeguridad;
	}


	public void setNumSeguridad(String numSeguridad) {
		this.numSeguridad = numSeguridad;
	}


	public double getLimite() {
		return limite;
	}


	public void setLimite(double limite) {
		this.limite = limite;
	}


	public double getTAE() {
		return TAE;
	}


	public void setTAE(double tAE) {
		TAE = tAE;
	}


	@Override
	public String toString() {
		return "\nName: " + nombre + "\nType: " + tipo_targeta + "\nNumber: " + numtargeta + 
				"\nExpiration date: "+this.expiration_date;
	}


	
	
	
	
	
	
	
	
	
	
	

}
