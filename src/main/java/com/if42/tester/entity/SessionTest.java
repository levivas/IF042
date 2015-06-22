package com.if42.tester.entity;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="session_test")
@NamedQuery(name="SessionTest.findAll", query="SELECT t FROM SessionTest t")
public class SessionTest {

    private Integer sessionTestId;
    private Timestamp startTime;
    private Timestamp finishTime;
    private List<SessionQuestion> sessionQuestions;
    private AvailableTest availableTest;
    private TestsResult testsResult;
    private Test test;

    public SessionTest() {
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "session_test_id", unique = true, nullable = false)
    public Integer getSessionTestId() {
        return sessionTestId;
    }

    public void setSessionTestId(Integer sessionTestId) {
        this.sessionTestId = sessionTestId;
    }

    @Column(name="start_time")
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Column(name="finish_time")
    public Timestamp getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    @OneToOne(mappedBy = "sessionTest")
    public AvailableTest getAvailableTest() {
        return availableTest;
    }

    public void setAvailableTest(AvailableTest availableTest) {
        this.availableTest = availableTest;
    }

    @OneToOne(mappedBy = "sessionTest")
    public TestsResult getTestsResult() {
        return testsResult;
    }

    public void setTestsResult(TestsResult testsResult) {
        this.testsResult = testsResult;
    }

    @OneToMany(mappedBy="sessionTest")
    public List<SessionQuestion> getSessionQuestions() {
        return sessionQuestions;
    }

    public void setSessionQuestions(List<SessionQuestion> sessionQuestions) {
        this.sessionQuestions = sessionQuestions;
    }

    public void addSessionQuestion(SessionQuestion sessionQuestion){
        this.sessionQuestions.add(sessionQuestion);
    }

    public void removeSessionQuestion(SessionQuestion sessionQuestion){
        this.sessionQuestions.remove(sessionQuestion);
    }

    @ManyToOne
    @JoinColumn(name = "test_id")
    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionTest that = (SessionTest) o;

        if (finishTime != null ? !finishTime.equals(that.finishTime) : that.finishTime != null) return false;
        if (!sessionTestId.equals(that.sessionTestId)) return false;
        if (!startTime.equals(that.startTime)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sessionTestId.hashCode();
        result = 31 * result + startTime.hashCode();
        result = 31 * result + (finishTime != null ? finishTime.hashCode() : 0);
        return result;
    }
}
