package br.com.nordesti.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestUserFX {

	@Test
	public void testUserFX() {
		User user = new User();
		user.setUsername("jackson");
		user.setName("Jackson Pires");
		
		UserFX userFX = new UserFX(user);
		
		assertEquals("Verifica o usuário foi criado...", "jackson", userFX.getUsername());
		
	}

}
