package com.skillshare.skillshareapp.service;

import com.skillshare.skillshareapp.model.MentorshipSession;
import com.skillshare.skillshareapp.model.Skill;
import com.skillshare.skillshareapp.model.Student;
import com.skillshare.skillshareapp.repository.SessionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private NotificationService notificationService;

    // ✅ CREATE SESSION (UPDATED WITH FACTORY PATTERN)
    public void createSession(Student mentor, Student mentee, Skill skill, LocalDateTime time) {

        // 🔥 USE FACTORY INSTEAD OF NEW
        MentorshipSession session = SessionFactory.createSession(
                mentor,
                mentee,
                skill,
                time
        );

        sessionRepository.save(session);

        // 🔔 notify mentor
        notificationService.sendNotification(
                mentor,
                "New session request from " + mentee.getFirstName()
        );
    }

    // ✅ RESPOND TO SESSION
    public void respondSession(Long sessionId, MentorshipSession.Status status) {

        MentorshipSession session = sessionRepository.findById(sessionId).orElse(null);

        if (session != null) {
            session.setStatus(status);
            sessionRepository.save(session);

            // 🔔 notify student
            notificationService.sendNotification(
                    session.getMentee(),
                    "Your session was " + status
            );
        }
    }

    // ✅ MARK COMPLETED
    public void markCompleted(Long id) {
        MentorshipSession session = sessionRepository.findById(id).orElse(null);

        if (session != null) {
            session.setStatus(MentorshipSession.Status.COMPLETED);
            sessionRepository.save(session);
        }
    }

    // ✅ GET ALL SESSIONS
    public List<MentorshipSession> getAllSessions() {
        return sessionRepository.findAll();
    }

    // ✅ GET SESSION BY ID (WITH DETAILS)
    public MentorshipSession getSessionById(Long id) {
        return sessionRepository.findByIdWithDetails(id).orElse(null);
    }

    // ✅ GET SESSIONS FOR MENTOR
    public List<MentorshipSession> getSessionsByMentor(Student mentor) {
        return sessionRepository.findByMentor(mentor);
    }

    // ✅ GET SESSIONS FOR MENTEE
    public List<MentorshipSession> getSessionsByMentee(Student mentee) {
        return sessionRepository.findByMentee(mentee);
    }
}