package com.estudo.estudoapirest.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.estudo.estudoapirest.domain.exception.CozinhaNaoEncontradaException;
import com.estudo.estudoapirest.domain.exception.EntidadeEmUsoException;
import com.estudo.estudoapirest.domain.model.Cozinha;
import com.estudo.estudoapirest.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	private static final String MSG_COZINHA_EM_USO 
		= "Cozinha de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	public void excluir(Long cozinhaId) {
		try {
			cozinhaRepository.delete(buscarOuFalhar(cozinhaId));
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_COZINHA_EM_USO, cozinhaId));
		}
	}
	
	public Cozinha buscarOuFalhar(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId)
			.orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
	}
	
}
