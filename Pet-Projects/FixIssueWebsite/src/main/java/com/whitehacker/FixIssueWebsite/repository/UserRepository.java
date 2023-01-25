package com.whitehacker.FixIssueWebsite.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.whitehacker.FixIssueWebsite.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
