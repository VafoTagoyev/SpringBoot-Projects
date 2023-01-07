package com.whitehacker.entities;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long notificationId;
    private LocalDate notificationDate;
    private LocalDate validUntilDate;
    private String notificationMessage;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private User notificationReceiver;


    public Notification() {

    }

    public Notification (LocalDate notificationDate, LocalDate validUntilDate, String notificationMessage) {
        this.notificationDate = notificationDate;
        this.validUntilDate = validUntilDate;
        this.notificationMessage = notificationMessage;
    }

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    public LocalDate getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDate notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public User getNotificationReceiver() {
        return notificationReceiver;
    }

    public void setNotificationReceiver(User notificationReceiver) {
        this.notificationReceiver = notificationReceiver;
    }

    public void setValidUntilDate(LocalDate validUntilDate) {
        this.validUntilDate = validUntilDate;
    }

    public LocalDate getValidUntilDate() {
        return validUntilDate;
    }


}
