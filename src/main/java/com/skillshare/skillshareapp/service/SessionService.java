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


   // Create a session using the factory pattern
   public void createSession(Student mentor, Student mentee, Skill skill, LocalDateTime time) {


      
       MentorshipSession session = SessionFactory.createSession(
               mentor,
               mentee,
               skill,
               time
       );


       sessionRepository.save(session);


       // notify the mentor that a session has been requested
       notificationService.sendNotification(
               mentor,
               "New session request from " + mentee.getFirstName()
       );
   }


   // Respond to session request
   public void respondSession(Long sessionId, MentorshipSession.Status status) {


       MentorshipSession session = sessionRepository.findById(sessionId).orElse(null);


       if (session != null) {
           session.setStatus(status);
           sessionRepository.save(session);


           // Notify the student whether the session was accepted or rejected
           notificationService.sendNotification(
                   session.getMentee(),
                   "Your session was " + status
           );
       }
   }


   // Mark the session as completed
   public void markCompleted(Long id) {
       MentorshipSession session = sessionRepository.findById(id).orElse(null);


       if (session != null) {
           session.setStatus(MentorshipSession.Status.COMPLETED);
           sessionRepository.save(session);
       }
   }


  
   public List<MentorshipSession> getAllSessions() {
       return sessionRepository.findAll();
   }


  
   public MentorshipSession getSessionById(Long id) {
       return sessionRepository.findByIdWithDetails(id).orElse(null);
   }


  
   public List<MentorshipSession> getSessionsByMentor(Student mentor) {
       return sessionRepository.findByMentor(mentor);
   }


  
   public List<MentorshipSession> getSessionsByMentee(Student mentee) {
       return sessionRepository.findByMentee(mentee);
   }
}