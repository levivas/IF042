package com.if42.tester.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the questions database table.
 * 
 */
@Entity
@Table(name="questions")
@NamedQuery(name="Question.findAll", query="SELECT q FROM Question q")
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;
	private int questionId;
	private int rank;
	private String title;
	private List<Answer> answers;
	private Test test;
    private int type;

	public Question() {
	}
	public Question(int rank, String title) {
		this.rank = rank;
		this.title = title;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="question_id")
	public int getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}


	public int getRank() {
		return this.rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}


	@Lob
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	//bi-directional many-to-one association to Answer
	@OneToMany(mappedBy="question", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Answer> getAnswers() {
		return this.answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Answer addAnswer(Answer answer) {
		answers.add(answer);
		answer.setQuestion(this);
		return answer;
	}

	public Answer removeAnswer(Answer answer) {
		getAnswers().remove(answer);
		answer.setQuestion(null);
		return answer;
	}


	//bi-directional many-to-one association to Test
	@ManyToOne
	@JoinColumn(name="test_id")
    @JsonIgnore
	public Test getTest() {
		return this.test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (questionId != question.questionId) return false;
        if (rank != question.rank) return false;
        if (answers != null ? !answers.equals(question.answers) : question.answers != null) return false;
        if (test != null ? !test.equals(question.test) : question.test != null) return false;
        if (title != null ? !title.equals(question.title) : question.title != null) return false;

        return true;
    }
}