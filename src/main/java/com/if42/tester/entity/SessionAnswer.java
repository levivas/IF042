package com.if42.tester.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="session_answer")
@NamedQuery(name="SessionAnswer.findAll", query="SELECT a FROM SessionAnswer a")
public class SessionAnswer {

    private Integer sessionAnswerId;
    private SessionQuestion sessionQuestion;
    private Answer answer;


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "session_answer_id", unique = true, nullable = false)
    public Integer getSessionAnswerId() {
        return sessionAnswerId;
    }

    public void setSessionAnswerId(Integer sessionAnswerId) {
        this.sessionAnswerId = sessionAnswerId;
    }

    @ManyToOne
    @JoinColumn(name = "session_question_id")
    @JsonIgnore
    public SessionQuestion getSessionQuestion() {
        return sessionQuestion;
    }
    public void setSessionQuestion(SessionQuestion sessionQuestion) {
        this.sessionQuestion = sessionQuestion;
    }

    @OneToOne
    @JoinColumn(name = "answer_id")
    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionAnswer that = (SessionAnswer) o;

        return sessionAnswerId.equals(that.sessionAnswerId);
    }

    @Override
    public int hashCode() {
        return sessionAnswerId.hashCode();
    }
}
