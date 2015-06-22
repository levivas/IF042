package com.if42.tester.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//import javax.validation.constraints.Pattern;


/**
 * The persistent class for the users database table.
 *
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int userId;
    private Timestamp created;
    private String middleName;
    private String name;
    private String password;
    private String surname;
    private String userName;
    private List<AvailableTest> availableTests;
    private List<TestsResult> testsResults;
    private Group group;
    private List<Category> categories = new ArrayList<Category>();

    public User() {
    }

    public User(int userId) {
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @JsonIgnore
    public Timestamp getCreated() {
        return this.created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }


    @Pattern(regexp="^[a-zA-Z]+$",
            message="middle name must contain only letters with no spaces")
    @Size(min = 2, max = 30, message = "middle_name must be between 2 and 30 characters long.")
    @Column(name="middle_name")
    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Pattern(regexp="^[a-zA-Z]+$",
            message="name must contain only letters with no spaces")
    @Size(min = 2, max = 30, message = "name must be between 2 and 30 characters long.")
    @Column(name="name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Size(min = 4, message = "Password must be at least 4 characters long.")
    @JsonIgnore
    @Column(name="password")
    public String getPassword() { return this.password; }

    public void setPassword(String password) {
        this.password = password;
    }


    @Pattern(regexp="^[a-zA-Z]+$",
            message="Surname must contain only letters with no spaces")
    @Size(min = 2, max = 30, message = "surname must be between 2 and 30 characters long.")
    @Column(name="surname")
    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    @Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            message="incorrect email address")
    @Size(min = 3, max = 65, message = "Login must be between 3 and 30 characters long.")
    @JsonIgnore
    @Column(name="user_name")
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    //bi-directional many-to-one association to AvailableTest
    @OneToMany(mappedBy="user")
    @JsonIgnore
    public List<AvailableTest> getAvailableTests() {
        return this.availableTests;
    }

    public void setAvailableTests(List<AvailableTest> availableTests) {
        this.availableTests = availableTests;
    }

    //bi-directional many-to-one association to TestsResult
    @OneToMany(mappedBy="user")
    @JsonIgnore
    public List<TestsResult> getTestsResults() {
        return this.testsResults;
    }

    public void setTestsResults(List<TestsResult> testsResults) {
        this.testsResults = testsResults;
    }


    //bi-directional many-to-one association to Group
    @ManyToOne
    @JoinColumn(name="group_id")
    @JsonIgnore
    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


    //bi-directional many-to-many association to Category
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name="users_has_categories"
            , joinColumns={
            @JoinColumn(name="users_user_id")
    }
            , inverseJoinColumns={
            @JoinColumn(name="categories_category_id")
    }
    )
    public List<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        User user = (User) o;
//
//        if (userId != user.userId) return false;
////        if (availableTests != null ? !availableTests.equals(user.availableTests) : user.availableTests != null)
////            return false;
////        if (categories != null ? !categories.equals(user.categories) : user.categories != null) return false;
//        if (created != null ? !created.equals(user.created) : user.created != null) return false;
//        if (group != null ? !group.equals(user.group) : user.group != null) return false;
//        if (middleName != null ? !middleName.equals(user.middleName) : user.middleName != null) return false;
//        if (name != null ? !name.equals(user.name) : user.name != null) return false;
//        if (password != null ? !password.equals(user.password) : user.password != null) return false;
//        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
////        if (testsResults != null ? !testsResults.equals(user.testsResults) : user.testsResults != null) return false;
//        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
//
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = userId;
//        result = 31 * result + (created != null ? created.hashCode() : 0);
//        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
//        result = 31 * result + (name != null ? name.hashCode() : 0);
//        result = 31 * result + (password != null ? password.hashCode() : 0);
//        result = 31 * result + (surname != null ? surname.hashCode() : 0);
//        result = 31 * result + (userName != null ? userName.hashCode() : 0);
//        result = 31 * result + (availableTests != null ? availableTests.hashCode() : 0);
//        result = 31 * result + (testsResults != null ? testsResults.hashCode() : 0);
//        result = 31 * result + (group != null ? group.hashCode() : 0);
//        result = 31 * result + (categories != null ? categories.hashCode() : 0);
//        return result;
//    }
}