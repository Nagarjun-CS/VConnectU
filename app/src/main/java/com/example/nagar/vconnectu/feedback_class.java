package com.example.nagar.vconnectu;

/**
 * Created by nagar on 09-11-2019.
 */

public class feedback_class {

    String feedbackId;
    String username;
    String feedback;

    public feedback_class()
    {

    }

    public feedback_class(String feedbackId, String username, String feedback) {
        this.feedbackId = feedbackId;
        this.username = username;
        this.feedback = feedback;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public String getUsername() {
        return username;
    }

    public String getFeedback() {
        return feedback;
    }
}
