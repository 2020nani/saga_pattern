package br.com.userregistration.infrastructure.repository

import br.com.userregistration.core.model.UserRegister
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRegisterRepository: JpaRepository<UserRegister, Int> {
}