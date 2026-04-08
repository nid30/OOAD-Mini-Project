package com.skillshare.skillshareapp.controller;

import com.skillshare.skillshareapp.model.Feedback;
import com.skillshare.skillshareapp.model.MentorshipSession;
import com.skillshare.skillshareapp.service.FeedbackService;
import com.skillshare.skillshareapp.repository.SessionRepository;
import com.skillshare.skillshareapp.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private StudentRepository studentRepository;

    // Show feedback form (loads required data for hidden fields)
    @GetMapping("/feedback")
    public String showFeedbackForm(
            @RequestParam Long sessionId,
            Model model
    ) {
        MentorshipSession session = sessionRepository.findById(sessionId).orElse(null);

        if (session == null) return "redirect:/sessions";

        model.addAttribute("sessionId", session.getSessionId());
        model.addAttribute("mentorId", session.getMentor().getStudentId());
        model.addAttribute("menteeId", session.getMentee().getStudentId());

        model.addAttribute("mentorName", session.getMentor().getFirstName());
        model.addAttribute("menteeName", session.getMentee().getFirstName());
        model.addAttribute("skillName", session.getSkill().getName());

        return "feedback";
    }

    // Show all feedbacks
    @GetMapping("/feedbacks")
    public String showAllFeedbacks(Model model) {

        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        model.addAttribute("feedbacks", feedbacks);

        return "feedback-list";
    }

    // Submit feedback
    @PostMapping("/feedback")
    public String submitFeedback(
            @RequestParam Long sessionId,
            @RequestParam Long reviewerId,
            @RequestParam Long rateeId,
            @RequestParam int rating,
            @RequestParam String comment
    ) {

        feedbackService.saveFeedback(sessionId, reviewerId, rateeId, rating, comment);

        return "redirect:/sessions";
    }
}