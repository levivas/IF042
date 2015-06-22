package com.if42.tester.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the tests database table.
 * 
 */
@Entity
@Table(name="tests")
@NamedQuery(name="Test.findAll", query="SELECT t FROM Test t")
public class Test implements Serializable {
	private static final long serialVersionUID = 1L;
	private int testId;
	private Timestamp created;
	private String createdBy;
	private int duration;
	private Timestamp edited;
	private String editedBy;
	private int grade;
	private String name;
	//private List<AvailableTest> availableTests;
	private List<Question> questions;
	private Category category;
	private List<TestsResult> testsResults;
    private int easyQuestionsCount;
    private int normalQuestionsCount;
    private int hardQuestionsCount;

	public Test() {
	}

	public Test(int testId, int grade, String name, int duration,
                int easyQuestionsCount, int normalQuestionsCount, int hardQuestionsCount) {
		this.testId = testId;
		this.grade = grade;
		this.name = name;
		this.duration = duration;
        this.easyQuestionsCount = easyQuestionsCount;
        this.normalQuestionsCount = normalQuestionsCount;
        this.hardQuestionsCount = hardQuestionsCount;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="test_id")
	public int getTestId() {
		return this.testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	@Column(name="created_by")
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

    @NotNull(message = "duration can't be empty.")
    @Min(2) @Max(300)
//    @Size(min = 1,max = 3,message = "duration can't be empty.")
    @Column(name="duration")
	public int getDuration() {
		return this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Timestamp getEdited() {
		return this.edited;
	}

	public void setEdited(Timestamp edited) {
		this.edited = edited;
	}

	@Column(name="edited_by")
	public String getEditedBy() {
		return this.editedBy;
	}

	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}

    @NotNull(message = "grade can't be empty.")
    @Min(5) @Max(200)
//    @Size(min = 1, max = 3, message = "grade can't be empty.")
    @Column(name="grade")
	public int getGrade() {
		return this.grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

    @Size(min = 2, message = "name of test must be between 2 and 30 characters long.")
    @Column(name="name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//bi-directional many-to-one association to AvailableTest
//	@OneToMany(mappedBy="test", cascade = CascadeType.ALL, orphanRemoval=true)
//    @JsonIgnore
//	public List<AvailableTest> getAvailableTests() {
//		return this.availableTests;
//	}

//	public void setAvailableTests(List<AvailableTest> availableTests) {
//		this.availableTests = availableTests;
//	}

	//bi-directional many-to-one association to Question
	@OneToMany(mappedBy="test", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
	public List<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public Question addQuestion(Question question) {
		getQuestions().add(question);
		question.setTest(this);

		return question;
	}

	public Question removeQuestion(Question question) {
		getQuestions().remove(question);
		question.setTest(null);

		return question;
	}

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="category_id")
    //@JsonIgnore
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


	//bi-directional many-to-one association to TestsResult
    @OneToMany(mappedBy="test", cascade = CascadeType.ALL, orphanRemoval=true)
    @JsonIgnore
	public List<TestsResult> getTestsResults() {
		return this.testsResults;
	}

	public void setTestsResults(List<TestsResult> testsResults) {
		this.testsResults = testsResults;
	}

	public TestsResult addTestsResult(TestsResult testsResult) {
		getTestsResults().add(testsResult);
		testsResult.setTest(this);

		return testsResult;
	}

    @NotNull(message = "can't be empty.")
    @Min(0) @Max(10)
    @Column(name="easy_questions")
    public int getEasyQuestionsCount() {
        return easyQuestionsCount;
    }

    public void setEasyQuestionsCount(int easyQuestionsCount) {
        this.easyQuestionsCount = easyQuestionsCount;
    }

    @NotNull(message = "can't be empty.")
    @Min(0) @Max(10)
    @Column(name="normal_questions")
    public int getNormalQuestionsCount() {
        return normalQuestionsCount;
    }

    public void setNormalQuestionsCount(int normalQuestionsCount) {
        this.normalQuestionsCount = normalQuestionsCount;
    }

    @NotNull(message = "can't be empty.")
    @Min(0) @Max(10)
    @Column(name="hard_questions")
    public int getHardQuestionsCount() {
        return hardQuestionsCount;
    }

    public void setHardQuestionsCount(int hardQuestionsCount) {
        this.hardQuestionsCount = hardQuestionsCount;
    }
}
