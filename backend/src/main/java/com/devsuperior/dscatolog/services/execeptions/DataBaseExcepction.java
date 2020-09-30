package com.devsuperior.dscatolog.services.execeptions;

public class DataBaseExcepction extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DataBaseExcepction(String msg) {
		super(msg);
	}

}
