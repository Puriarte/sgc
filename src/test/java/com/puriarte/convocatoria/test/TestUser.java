package com.puriarte.convocatoria.test;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.puriarte.convocatoria.core.domain.services.Facade;



public class TestUser {

	@Test
	public void registrarSMSEntrante() {

		try{
		Facade.getInstance().selectUser("puriarte", "puriarte");
		
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
}
