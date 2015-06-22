package com.if42.tester.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="session_question")
@NamedQuery(name="SessionQuestion.findAll", query="SELECT q FROM SessionQuestion q")
public class SessionQuestion {

    private Integer sessionQuestionId;
    private SessionTest sessionTest;
    private Question question;
    private List<SessionAnswer> sessionAnswers = new ArrayList<SessionAnswer>();

    public SessionQuestion() {
    }

    public SessionQuestion(Question question, SessionTest sessionTest) {
        this.question = question;
        this.sessionTest = sessionTest;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "session_question_id", unique = true, nullable = false)
    public Integer getSessionQuestionId() {
        return sessionQuestionId;
    }

    public void setSessionQuestionId(Integer sessionQuestionId) {
        this.sessionQuestionId = sessionQuestionId;
    }

    @ManyToOne()
    @JoinColumn(name = "session_test_id")
    @JsonIgnore
    public SessionTest getSessionTest() {
        return sessionTest;
    }

    public void setSessionTest(SessionTest sessionTest) {
        this.sessionTest = sessionTest;
    }

    @OneToOne
    @JoinColumn(name = "question_id")
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @OneToMany(mappedBy="sessionQuestion", cascade = CascadeType.ALL, orphanRemoval=true)
    public List<SessionAnswer> getSessionAnswers() {
        return sessionAnswers;
    }
    public void setSessionAnswers(List<SessionAnswer> sessionAnswers) {
        this.sessionAnswers = sessionAnswers;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionQuestion that = (SessionQuestion) o;

        return sessionQuestionId.equals(that.sessionQuestionId);
    }

    @Override
    public int hashCode() {
        return sessionQuestionId.hashCode();
    }
}
