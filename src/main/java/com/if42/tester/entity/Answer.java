package com.if42.tester.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the answers database table.
 * 
 */
@Entity
@Table(name="answers")
@NamedQuery(name="Answer.findAll", query="SELECT a FROM Answer a")
public class Answer implements Serializable {
	private static final long serialVersionUID = 1L;
	private int answerId;
	private boolean correct;
	private String text;
	private Question question;

	public Answer() {
	}

	public Answer(boolean correct, String text) {
		this.correct = correct;
		this.text = text;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="answer_id")
	public int getAnswerId() {
		return this.answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}


	@Column(name="is_true")
    @JsonIgnore
	public boolean isCorrect() {
		return this.correct;
	}

	public void setCorrect(boolean isTrue) {
		this.correct = isTrue;
	}


	@Lob
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}


	//bi-directional many-to-one association to Question
	@ManyToOne
	@JoinColumn(name="question_id")
    @JsonIgnore
	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (answerId != answer.answerId) return false;
        if (correct != answer.correct) return false;
        if (question != null ? !question.equals(answer.question) : answer.question != null) return false;
        if (!text.equals(answer.text)) return false;

        return true;
    }
 }