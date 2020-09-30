package com.devsuperior.dscatolog.services.execeptions;

public class EntityNotFoundExcepction extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public EntityNotFoundExcepction(String msg) {
		super(msg);
	}

}
