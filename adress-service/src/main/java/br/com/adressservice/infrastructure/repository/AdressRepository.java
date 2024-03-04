package br.com.adressservice.infrastructure.repository;

import br.com.adressservice.core.model.Adress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdressRepository extends JpaRepository<Adress, Integer> {

    Boolean existsByCode(String code);
}
