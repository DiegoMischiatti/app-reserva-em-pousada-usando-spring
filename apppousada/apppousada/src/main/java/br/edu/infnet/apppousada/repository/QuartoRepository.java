package br.edu.infnet.apppousada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.apppousada.model.Quarto;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Integer>{

}
