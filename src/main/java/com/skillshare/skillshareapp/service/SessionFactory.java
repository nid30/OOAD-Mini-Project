package com.skillshare.skillshareapp.service;

import com.skillshare.skillshareapp.model.MentorshipSession;
import com.skillshare.skillshareapp.model.Skill;
import com.skillshare.skillshareapp.model.Student;

import java.time.LocalDateTime;

public class SessionFactory {

    public static MentorshipSession createSession(
            Student mentor,
            Student mentee,
            Skill skill,
            LocalDateTime time
    ) {
        MentorshipSession session = new MentorshipSession();

        session.setMentor(mentor);
        session.setMentee(mentee);
        session.setSkill(skill);
        session.setScheduleTime(time);
        session.setStatus(MentorshipSession.Status.PENDING);

        return session;
    }
}