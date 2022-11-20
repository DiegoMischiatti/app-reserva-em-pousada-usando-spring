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
import br.edu.infnet.apppousada.model.Hospede;
import br.edu.infnet.apppousada.service.HospedeService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class HospedeController {
	@Autowired
	private HospedeService hospedeService;

	@GetMapping("/hospedes")
	public List<Hospede> getAllhospedes() {
		return hospedeService.lista();
	}

	@GetMapping("/hospedes/{id}")
	public ResponseEntity<Hospede> gethospedeById(@PathVariable(value = "id") Integer hospedeId)
			throws ResourceNotFoundException {
		Hospede hospede = hospedeService.listaId(hospedeId);
		return ResponseEntity.ok().body(hospede);
	}

	@PostMapping("/hospedes")
	public Hospede createhospede(@Valid @RequestBody Hospede hospede) {
		return hospedeService.inserir(hospede);
	}

	@PutMapping("/hospedes/{id}")
	public ResponseEntity<Hospede> updatehospede(@PathVariable(value = "id") Integer hospedeId,
			@Valid @RequestBody Hospede hospedeDetails) throws ResourceNotFoundException {

		final Hospede updatehospede = hospedeService.alterar(hospedeId, hospedeDetails);
		return ResponseEntity.ok(updatehospede);
	}

	@DeleteMapping("/hospedes/{id}")
	public Map<String, Boolean> deletehospede(@PathVariable(value = "id") Integer hospedeId)
			throws ResourceNotFoundException {

		hospedeService.excluir(hospedeId);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
