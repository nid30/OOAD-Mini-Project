package com.skillshare.skillshareapp.service;

import com.skillshare.skillshareapp.model.Feedback;
import com.skillshare.skillshareapp.model.MentorshipSession;
import com.skillshare.skillshareapp.model.Student;
import com.skillshare.skillshareapp.repository.FeedbackRepository;
import com.skillshare.skillshareapp.repository.SessionRepository;
import com.skillshare.skillshareapp.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 🔥 IMPORT BUILDER
import com.skillshare.skillshareapp.service.FeedbackBuilder;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private StudentRepository studentRepository;

    // ✅ GET ALL FEEDBACKS
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    // ✅ SAVE FEEDBACK (UPDATED WITH BUILDER PATTERN)
    public void saveFeedback(Long sessionId, Long reviewerId, Long rateeId, int rating, String comment) {

        boolean exists = feedbackRepository
                .existsBySession_SessionIdAndReviewer_StudentId(sessionId, reviewerId);

        if (exists) return;

        MentorshipSession session = sessionRepository.findById(sessionId).orElse(null);
        Student reviewer = studentRepository.findById(reviewerId).orElse(null);
        Student ratee = studentRepository.findById(rateeId).orElse(null);

        if (session == null || reviewer == null || ratee == null) return;

        // 🔥 BUILDER PATTERN USED HERE
        Feedback feedback = new FeedbackBuilder()
                .setSession(session)
                .setReviewer(reviewer)
                .setRatee(ratee)
                .setRating(rating)
                .setComment(comment)
                .build();

        feedbackRepository.save(feedback);

        // ✅ UPDATE AVERAGE RATING
        double sum = 0;
        int count = 0;

        for (Feedback f : feedbackRepository.findAll()) {
            if (f.getRatee() != null &&
                f.getRatee().getStudentId().equals(rateeId)) {
                sum += f.getRating();
                count++;
            }
        }

        if (count > 0) {
            ratee.setAvgRating(sum / count);
            studentRepository.save(ratee);
        }
    }
}