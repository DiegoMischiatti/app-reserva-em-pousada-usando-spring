package br.edu.infnet.apppousada.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.infnet.apppousada.exception.ResourceNotFoundException;
import br.edu.infnet.apppousada.model.Quarto;
import br.edu.infnet.apppousada.repository.QuartoRepository;

@Service
public class QuartoService {
	@Autowired
	private QuartoRepository quartoRepository;

	
	public List<Quarto> lista() {
		return quartoRepository.findAll();
	}

	
	public Quarto listaId(Integer quartoId)
			throws ResourceNotFoundException {
		Quarto quarto = quartoRepository.findById(quartoId)
				.orElseThrow(() -> new ResourceNotFoundException("quarto com id nao existente::: " + quartoId));
		return quarto;
	}

	
	public Quarto inserir(Quarto quarto) {
		return quartoRepository.save(quarto);
	}

	
	public Quarto alterar( Integer quartoId,
			                                Quarto quartoDetails) throws ResourceNotFoundException {
		Quarto quarto = quartoRepository.findById(quartoId)
				.orElseThrow(() -> new ResourceNotFoundException("quarto com id nao existente::: " + quartoId));

		quarto.setDescricao(quartoDetails.getDescricao());
		quarto.setValor(quartoDetails.getValor());
		quarto.setTamanho(quartoDetails.getTamanho());
		quarto.setStatus(quartoDetails.getStatus());
		final Quarto updatequarto = quartoRepository.save(quarto);
		return updatequarto;
	}

	
	public Map<String, Boolean> excluir( Integer quartoId)
			throws ResourceNotFoundException {
		Quarto quarto = quartoRepository.findById(quartoId)
				.orElseThrow(() -> new ResourceNotFoundException("quarto com id nao existente::: " + quartoId));

		quartoRepository.delete(quarto);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
