package com.skillshare.skillshareapp.model;

import com.skillshare.skillshareapp.service.Observer;
import jakarta.persistence.*;

@Entity
public class Student implements Observer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    private String firstName;
    private String lastName;
    private String department;
    private int year;
    private String contact;
    private double avgRating;
    private String password;
    private String role;

    // Observer Pattern method
    @Override
    public void update(String message) {
        System.out.println("Notification for " + this.firstName + ": " + message);
    }

    // ===== GETTERS & SETTERS =====

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getPassword() {   
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getAvgRating() { return avgRating; }
    public void setAvgRating(double avgRating) { this.avgRating = avgRating; }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}