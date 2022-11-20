package br.edu.infnet.apppousada.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.infnet.apppousada.enuns.StatusQuarto;
import br.edu.infnet.apppousada.exception.ResourceNotFoundException;
import br.edu.infnet.apppousada.model.Hospede;
import br.edu.infnet.apppousada.model.Quarto;
import br.edu.infnet.apppousada.model.Reserva;
import br.edu.infnet.apppousada.repository.ReservaRepository;

@Service
public class ReservaService {
	@Autowired
	private ReservaRepository reservaRepository;

	@Autowired
	private QuartoService quartoService;

	@Autowired
	private HospedeService hospedeService;

	public List<Reserva> lista() {
		return reservaRepository.findAll();
	}

	public Reserva listaId(Integer reservaId) throws ResourceNotFoundException {
		Reserva reserva = reservaRepository.findById(reservaId)
				.orElseThrow(() -> new ResourceNotFoundException("reserva com id nao existente::: " + reservaId));
		return reserva;
	}

	public Reserva inserir(Reserva reserva) throws Exception {

		if (reserva.getQuarto() != null) {
			Quarto quarto = quartoService.listaId(reserva.getQuarto().getId());

			if (quarto.getStatus() == StatusQuarto.OCUPADO) {
				throw new Exception("quarto ocupado");

			}

			reserva.setQuarto(quarto);
		} else {
			reserva.setQuarto(null);
		}

		if (reserva.getHospede() != null) {
			Hospede hospede = hospedeService.listaId(reserva.getHospede().getId());
			reserva.setHospede(hospede);
		} else {
			reserva.setQuarto(null);
		}

		Reserva createdReserva = reservaRepository.save(reserva);

		if (createdReserva.getId() > 0) {
			Quarto quarto = quartoService.listaId(reserva.getQuarto().getId());
			quarto.setStatus(StatusQuarto.OCUPADO);
			quartoService.alterar(quarto.getId(), quarto);
		}

		return createdReserva;

	}

	public Reserva alterar(Integer reservaId, Reserva reservaDetails) throws ResourceNotFoundException {
		Reserva reserva = reservaRepository.findById(reservaId)
				.orElseThrow(() -> new ResourceNotFoundException("reserva com id nao existente::: " + reservaId));

		if (reservaDetails.getQuarto() != null) {
			Quarto quarto = quartoService.listaId(reservaDetails.getQuarto().getId());
			reserva.setQuarto(quarto);
		} else {
			reserva.setQuarto(null);
		}

		if (reservaDetails.getHospede() != null) {
			Hospede hospede = hospedeService.listaId(reservaDetails.getHospede().getId());
			reserva.setHospede(hospede);
		} else {
			reserva.setQuarto(null);
		}

		reserva.setDataInicio(reservaDetails.getDataInicio());
		reserva.setDataLanc(reservaDetails.getDataLanc());
		reserva.setDataFim(reservaDetails.getDataFim());

		final Reserva updatereserva = reservaRepository.save(reserva);

		return updatereserva;
	}

	public void finalizaReserva( Integer reservaId) throws ResourceNotFoundException {
		Reserva reserva = reservaRepository.findById(reservaId)
		.orElseThrow(() -> new ResourceNotFoundException("reserva com id nao existente::: " + reservaId));

		reserva.setDataFim(LocalDate.now());
		
		final Reserva updatereserva = reservaRepository.save(reserva);
		
		if (updatereserva.getId() > 0) {
			Quarto quarto = quartoService.listaId(reserva.getQuarto().getId());
			quarto.setStatus(StatusQuarto.DISPONIVEL);
			quartoService.alterar(quarto.getId(), quarto);
		}
		

		}

	public Map<String, Boolean> excluir(Integer reservaId) throws ResourceNotFoundException {
		Reserva reserva = reservaRepository.findById(reservaId)
				.orElseThrow(() -> new ResourceNotFoundException("reserva com id nao existente::: " + reservaId));

		reservaRepository.delete(reserva);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
