package com.whitehacker.utils;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.whitehacker.entities.Book;
import com.whitehacker.entities.Notification;
import com.whitehacker.entities.User;
import com.whitehacker.services.BookService;
import com.whitehacker.services.NotificationService;
import com.whitehacker.services.UserService;

@Component
public class MidnightApplicationRefresh {

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @Autowired
    NotificationService notifService;

    //Removes overdue reservations and notifications.
    public void midnightApplicationRefresher() {

        for (Book book : bookService.findAll()) {
            if (book.getEndReservationDate() != null) {
                if (book.getEndReservationDate().compareTo(LocalDate.now()) < 0) {
                    if (book.getReservedByUser() != null) {
                        User user = book.getReservedByUser();
                        book.setReservedByUser(null);
                        userService.save(user);
                    }
                    book.setStartReservationDate(null);
                    book.setEndReservationDate(null);
                    book.setReadyForPickup(false);
                    bookService.save(book);
                }
            }
        }

        for (Notification notif : notifService.findAll()) {
            if (notif.getValidUntilDate() != null) {
                if (notif.getValidUntilDate().compareTo(LocalDate.now()) < 0) {
                    notifService.deleteById(notif.getNotificationId());
                }
            }
        }
    }
}
