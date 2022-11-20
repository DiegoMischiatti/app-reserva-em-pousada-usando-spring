package br.edu.infnet.apppousada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.apppousada.model.Hospede;

@Repository
public interface HospedeRepository extends JpaRepository<Hospede, Integer>{

}
