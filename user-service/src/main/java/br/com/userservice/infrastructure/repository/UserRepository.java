package br.com.userservice.infrastructure.repository;


import br.com.userservice.core.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
