package br.edu.infnet.apppousada.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.apppousada.exception.ResourceNotFoundException;
import br.edu.infnet.apppousada.model.Quarto;
import br.edu.infnet.apppousada.service.QuartoService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class QuartoController {
	@Autowired
	private QuartoService quartoService;

	@GetMapping("/quartos")
	public List<Quarto> getAllquartos() {
		return quartoService.lista();
	}

	@GetMapping("/quartos/{id}")
	public ResponseEntity<Quarto> getquartoById(@PathVariable(value = "id") Integer quartoId)
			throws ResourceNotFoundException {
		Quarto quarto = quartoService.listaId(quartoId);
		return ResponseEntity.ok().body(quarto);
	}

	@PostMapping("/quartos")
	public Quarto createquarto(@Valid @RequestBody Quarto quarto) {
		return quartoService.inserir(quarto);
	}

	@PutMapping("/quartos/{id}")
	public ResponseEntity<Quarto> updatequarto(@PathVariable(value = "id") Integer quartoId,
			@Valid @RequestBody Quarto quartoDetails) throws ResourceNotFoundException {

		final Quarto updatequarto = quartoService.alterar(quartoId, quartoDetails);
		return ResponseEntity.ok(updatequarto);
	}

	@DeleteMapping("/quartos/{id}")
	public Map<String, Boolean> deletequarto(@PathVariable(value = "id") Integer quartoId)
			throws ResourceNotFoundException {

		quartoService.excluir(quartoId);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
