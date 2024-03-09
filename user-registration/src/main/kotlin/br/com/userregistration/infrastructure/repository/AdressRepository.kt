package br.com.userregistration.infrastructure.repository

import br.com.userregistration.core.model.AdressRegister
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdressRepository: JpaRepository<AdressRegister, Int> {
}