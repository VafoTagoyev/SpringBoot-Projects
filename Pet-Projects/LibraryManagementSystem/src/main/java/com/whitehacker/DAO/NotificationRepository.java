package com.whitehacker.DAO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.whitehacker.entities.Notification;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

}
