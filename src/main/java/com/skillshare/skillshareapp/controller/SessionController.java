package com.skillshare.skillshareapp.controller;


import com.skillshare.skillshareapp.model.MentorshipSession;
import com.skillshare.skillshareapp.model.Skill;
import com.skillshare.skillshareapp.model.Student;
import com.skillshare.skillshareapp.repository.SkillRepository;
import com.skillshare.skillshareapp.repository.StudentRepository;
import com.skillshare.skillshareapp.repository.FeedbackRepository;
import com.skillshare.skillshareapp.service.NotificationService;
import com.skillshare.skillshareapp.service.SessionService;
import com.skillshare.skillshareapp.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.skillshare.skillshareapp.model.Notification;


@Controller
@RequestMapping("/sessions")
public class SessionController {


   @Autowired
   private SessionService sessionService;


   @Autowired
   private StudentRepository studentRepository;


   @Autowired
   private SkillRepository skillRepository;


   @Autowired
   private FeedbackRepository feedbackRepository;


   @Autowired
   private NotificationService notificationService;


   // Load page according to the role of the user who has logged into the website
   @GetMapping
   public String showSessionsPage(HttpSession session, Model model) {


       Student user = (Student) session.getAttribute("user");
       List<Notification> notifs = notificationService.getNotifications(user);
       model.addAttribute("notifications", notifs);
       model.addAttribute("notificationCount", notifs.size());


       model.addAttribute("skills", skillRepository.findAll());
       model.addAttribute("mentors", studentRepository.findByRole("MENTOR"));
       model.addAttribute("sessionUser", user);
       if (user == null) return "redirect:/login";


       List<MentorshipSession> sessions;


      
       if ("MENTOR".equalsIgnoreCase(user.getRole())) {
           sessions = sessionService.getSessionsByMentor(user);
       } else {
           sessions = sessionService.getSessionsByMentee(user);
       }


      
       Map<Long, Boolean> feedbackMap = new HashMap<>();


       for (MentorshipSession s : sessions) {
           boolean exists = feedbackRepository.existsBySession_SessionId(s.getSessionId());
           feedbackMap.put(s.getSessionId(), exists);
       }


       model.addAttribute("sessions", sessions);
       model.addAttribute("feedbackMap", feedbackMap);


       
       model.addAttribute("skills", skillRepository.findAll());
       model.addAttribute("mentors", studentRepository.findByRole("MENTOR"));
       model.addAttribute("sessionUser", user);


       return "sessions";
   }


   // Creates a session only if the user logged in is a student
   @PostMapping("/create")
   public String createSession(
           @RequestParam("mentorId") Long mentorId,
           @RequestParam("skillId") Long skillId,
           @RequestParam("time") String time,
           HttpSession session
   ) {


       Student user = (Student) session.getAttribute("user");


       if (user == null) return "redirect:/login";


      
       if (!"STUDENT".equalsIgnoreCase(user.getRole())) {
           return "redirect:/sessions";
       }


       Student mentor = studentRepository.findById(mentorId).orElse(null);
       Skill skill = skillRepository.findById(skillId).orElse(null);


       if (mentor == null || skill == null) {
           return "redirect:/sessions";
       }


      
       if (mentorId.equals(user.getStudentId())) {
           return "redirect:/sessions";
       }


       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
       LocalDateTime dateTime = LocalDateTime.parse(time, formatter);


       sessionService.createSession(mentor, user, skill, dateTime);


       return "redirect:/sessions";
   }


   // Allows only the mentor to mark a session as completed
   @PostMapping("/complete/{id}")
   public String completeSession(@PathVariable("id") Long id, HttpSession session) {


       Student user = (Student) session.getAttribute("user");


       if (user == null) return "redirect:/login";


       MentorshipSession s = sessionService.getSessionById(id);


       if (s == null) return "redirect:/sessions";


      
       if (!s.getMentor().getStudentId().equals(user.getStudentId())) {
           return "redirect:/sessions";
       }


      
       if (s.getStatus() == MentorshipSession.Status.ACCEPTED) {
           sessionService.markCompleted(id);
       }


       return "redirect:/sessions";
   }


  
   @PostMapping("/respond")
   public String respondSession(
           @RequestParam Long sessionId,
           @RequestParam String status,
           HttpSession session
   ) {


       Student user = (Student) session.getAttribute("user");


       if (user == null) return "redirect:/login";


       MentorshipSession s = sessionService.getSessionById(sessionId);


       if (s == null) return "redirect:/sessions";


      
       if (!s.getMentor().getStudentId().equals(user.getStudentId())) {
           return "redirect:/sessions";
       }


       sessionService.respondSession(
               sessionId,
               MentorshipSession.Status.valueOf(status)
       );


       return "redirect:/sessions";
   }


   // Loads the feedback page only for the user who is a student
   @GetMapping("/feedback/{id}")
   public String showFeedbackPage(
           @PathVariable("id") Long sessionId,
           HttpSession session,
           Model model
   ) {


       Student user = (Student) session.getAttribute("user");


       if (user == null) return "redirect:/login";


       MentorshipSession s = sessionService.getSessionById(sessionId);


       if (s == null) return "redirect:/sessions";


      
       if (!s.getMentee().getStudentId().equals(user.getStudentId())) {
           return "redirect:/sessions";
       }


      
       if (s.getStatus() != MentorshipSession.Status.COMPLETED) {
           return "redirect:/sessions";
       }


      
       if (feedbackRepository.existsBySession_SessionId(sessionId)) {
           return "redirect:/sessions";
       }


       model.addAttribute("session", s);


       return "feedback";
   }
}
