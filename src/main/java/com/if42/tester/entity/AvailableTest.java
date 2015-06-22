package com.if42.tester.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the available_tests database table.
 */
@Entity
@Table(name="available_tests")
@NamedQuery(name="AvailableTest.findAll", query="SELECT a FROM AvailableTest a")
public class AvailableTest implements Serializable {
    private static final long serialVersionUID = 1L;
    private int availableTestId;
    private Timestamp finishTime;
    private Timestamp startTime;
    private User user;
    private Test test;
    private SessionTest sessionTest;

    public AvailableTest() {
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="avaliable_test_id")
    public int getAvailableTestId() {
        return this.availableTestId;
    }

    public void setAvailableTestId(int availableTestId) {
        this.availableTestId = availableTestId;
    }

    @Column(name="finish_time")
    public Timestamp getFinishTime() {
        return this.finishTime;
    }

    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }


    @Column(name="start_time")
    public Timestamp getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
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

    //bi-directional many-to-one association to Test
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="session_test_id")
    public SessionTest getSessionTest() {
        return  sessionTest;
    }

    public void setSessionTest(SessionTest sessionTest) {
        this.sessionTest = sessionTest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AvailableTest that = (AvailableTest) o;

        if (availableTestId != that.availableTestId) return false;
        if (!finishTime.equals(that.finishTime)) return false;
        if (!startTime.equals(that.startTime)) return false;
        if (test != null ? !test.equals(that.test) : that.test != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = availableTestId;
        result = 31 * result + finishTime.hashCode();
        result = 31 * result + startTime.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (test != null ? test.hashCode() : 0);
        return result;
    }
}