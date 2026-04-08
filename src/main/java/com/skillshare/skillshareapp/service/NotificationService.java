package com.skillshare.skillshareapp.service;

import com.skillshare.skillshareapp.model.Notification;
import com.skillshare.skillshareapp.model.Student;
import com.skillshare.skillshareapp.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // Observer Pattern: list of observers
    private List<Observer> observers = new ArrayList<>();

    // Observer Pattern: add observer
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // Observer Pattern: notify observers
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    // REQUIRED: keep this method (used by other classes)
    public void sendNotification(Student student, String message) {

        Notification notification = new Notification();
        notification.setStudent(student);
        notification.setMessage(message);
        notification.setTimestamp(LocalDateTime.now());
        notification.setStatus(Notification.Status.UNREAD);

        notificationRepository.save(notification);
    }

    // Observer Pattern integration (ONLY ONE METHOD)
    public void notifyStudent(Student student, String message) {
        addObserver(student);
        notifyObservers(message);
        sendNotification(student, message);
    }

    // Get notifications
    public List<Notification> getNotifications(Student student) {
        return notificationRepository.findByStudent(student);
    }
}