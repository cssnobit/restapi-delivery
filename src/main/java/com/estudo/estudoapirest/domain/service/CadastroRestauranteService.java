package com.estudo.estudoapirest.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.estudo.estudoapirest.domain.exception.EntidadeEmUsoException;
import com.estudo.estudoapirest.domain.exception.RestauranteNaoEncontradoException;
import com.estudo.estudoapirest.domain.model.Cozinha;
import com.estudo.estudoapirest.domain.model.Restaurante;
import com.estudo.estudoapirest.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	private static final String MSG_RESTAURANTE_EM_USO 
	= "Restaurante de código %d não pode ser removida, pois está em uso";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
		
		restaurante.setCozinha(cozinha);
		
		return restauranteRepository.save(restaurante);
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
			.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}
	
	public void remove(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		try {			
			restauranteRepository.delete(restaurante);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(MSG_RESTAURANTE_EM_USO);
		}
	}
	
}
