package com.skillshare.skillshareapp.service;

import com.skillshare.skillshareapp.model.Feedback;
import com.skillshare.skillshareapp.model.MentorshipSession;
import com.skillshare.skillshareapp.model.Student;

public class FeedbackBuilder {

    private Feedback feedback;

    public FeedbackBuilder() {
        feedback = new Feedback();
    }

    public FeedbackBuilder setSession(MentorshipSession session) {
        feedback.setSession(session);
        return this;
    }

    public FeedbackBuilder setReviewer(Student reviewer) {
        feedback.setReviewer(reviewer);
        return this;
    }

    public FeedbackBuilder setRatee(Student ratee) {
        feedback.setRatee(ratee);
        return this;
    }

    public FeedbackBuilder setRating(int rating) {
        feedback.setRating(rating);
        return this;
    }

    public FeedbackBuilder setComment(String comment) {
        feedback.setComment(comment);
        return this;
    }

    public Feedback build() {
        return feedback;
    }
}