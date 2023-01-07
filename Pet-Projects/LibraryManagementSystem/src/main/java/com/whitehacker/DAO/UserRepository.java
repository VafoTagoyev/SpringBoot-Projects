package com.whitehacker.DAO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.whitehacker.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
