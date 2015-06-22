package com.if42.tester.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the tests_results database table.
 *
 */
@Entity
@Table(name="tests_results")
@NamedQuery(name="TestsResult.findAll", query="SELECT t FROM TestsResult t ORDER BY t.test.name")
public class TestsResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private int testResultId;
    private int markPercents;
    private Timestamp passDate;
    private User user;
    private Test test;
    private SessionTest sessionTest;

    public TestsResult() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="test_result_id")
    public int getTestResultId() {
        return this.testResultId;
    }

    public void setTestResultId(int testResultId) {
        this.testResultId = testResultId;
    }


    @Column(name="mark_percents")
    public int getMarkPercents() {
        return this.markPercents;
    }

    public void setMarkPercents(int markPercents) {
        this.markPercents = markPercents;
    }


    @Column(name="pass_date")
    public Timestamp getPassDate() {
        return this.passDate;
    }

    public void setPassDate(Timestamp passDate) {
        this.passDate = passDate;
    }

    //bi-directional many-to-one association to User
    @ManyToOne
    @JoinColumn(name="user_id")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    //bi-directional many-to-one association to Test
    @ManyToOne
    @JoinColumn(name="test_id")
    public Test getTest() {
        return this.test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @OneToOne
    @JoinColumn(name="session_test_id")
    public SessionTest getSessionTest() {
        return sessionTest;
    }

    public void setSessionTest(SessionTest sessionTest) {
        this.sessionTest = sessionTest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestsResult that = (TestsResult) o;
        if (testResultId != that.testResultId) return false;

        return true;
    }
}