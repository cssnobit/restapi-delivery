package com.estudo.estudoapirest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.estudo.estudoapirest.domain.model.Cozinha;
import com.estudo.estudoapirest.domain.service.CadastroCozinhaService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CadastroDeCozinhaIntegrationTests {
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	// teste que passa
	@Test
	public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
		// cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");
		
		// ação
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
		
		// validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();	
	}
	
	// teste que falha
	@Test
	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
		Throwable ex = assertThrows(ConstraintViolationException.class, () -> {
			Cozinha novaCozinha = new Cozinha();
			novaCozinha.setNome(null);
			
			novaCozinha = cadastroCozinha.salvar(novaCozinha);
		});
	}
}
