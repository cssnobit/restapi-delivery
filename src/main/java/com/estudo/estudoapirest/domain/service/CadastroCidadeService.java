package com.estudo.estudoapirest.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.estudo.estudoapirest.domain.exception.CidadeNaoEncontradaException;
import com.estudo.estudoapirest.domain.exception.EntidadeEmUsoException;
import com.estudo.estudoapirest.domain.model.Cidade;
import com.estudo.estudoapirest.domain.model.Estado;
import com.estudo.estudoapirest.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	private static final String MSG_CIDADE_EM_USO 
		= "Cidade de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();

		Estado estado = cadastroEstado.buscarOuFalhar(estadoId);
		
		cidade.setEstado(estado);
		
		return cidadeRepository.save(cidade);
	}
	
	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.delete(buscarOuFalhar(cidadeId));
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_CIDADE_EM_USO, cidadeId));
		}
	}
	
	public Cidade buscarOuFalhar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId)
			.orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
	}
	
}
