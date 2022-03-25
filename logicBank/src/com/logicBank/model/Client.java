package com.logicBank.model;

import java.sql.Date;

public class Client {
	
	private final String TABLE_NAME = "cliente";
	private int id;
	private String name;
	private String lastnames;
	private String nif;
	private Date data_of_birth;
	
	public Client() {
	}
	
	public Client (String name, String lastnames, String nif, String data_of_birth) {
		this.name = name;
		this.lastnames = lastnames;
		this.nif = nif;
		this.data_of_birth = Date.valueOf(data_of_birth);
	}
	
	public Client (int id, String name, String lastnames, String nif, String data_of_birth) {
		this.id = id;
		this.name = name;
		this.lastnames = lastnames;
		this.nif = nif;
		this.data_of_birth = Date.valueOf(data_of_birth);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastnames() {
		return lastnames;
	}

	public void setLastnames(String lastnames) {
		this.lastnames = lastnames;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public Date getData_of_birth() {
		return data_of_birth;
	}

	public void setData_of_birth(Date data_of_birth) {
		this.data_of_birth = data_of_birth;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", lastnames=" + lastnames + ", nif=" + nif + ", data_of_birth="
				+ data_of_birth + "]";
	}

	public String getTABLE_NAME() {
		return TABLE_NAME;
	}

	/*
	 * Construeix la 'query' a partir dels valors dels atributs de la clase 'Client'
	 * Retorna la query per ser passada a la capa de comunicació amb la bbdd.
	 */
	public String getQuery() {
		return "INSERT INTO "+TABLE_NAME+" VALUES (DEFAULT, '"+this.nif+"', '"+this.name+"', '"
				+ this.lastnames+"', '"+this.data_of_birth+"')";
	}

}
