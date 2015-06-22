package com.if42.tester.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the categories database table.
 *
 */
@Entity
@Table(name="categories")
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    private int categoryId;
    private String title;
    private List<Test> tests;
    private List<User> users;

    public Category() {
    }

    public Category(int categoryId) {
        this.categoryId = categoryId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="category_id")
    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Size(min = 2, message = "name of category must be between 2 and 30 characters long.")
    @Column(name="title")
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    //bi-directional many-to-one association to Test
    @OneToMany(mappedBy="category")
    @JsonIgnore
    public List<Test> getTests() {
        return this.tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public Test addTest(Test test) {
        getTests().add(test);
        test.setCategory(this);

        return test;
    }

    public Test removeTest(Test test) {
        getTests().remove(test);
        test.setCategory(null);

        return test;
    }


    //bi-directional many-to-many association to User
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="users_has_categories"
            , joinColumns={
            @JoinColumn(name="categories_category_id")
    }
            , inverseJoinColumns={
            @JoinColumn(name="users_user_id")
    }
    )
    @JsonIgnore
    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (categoryId != category.categoryId) return false;
        if (tests != null ? !tests.equals(category.tests) : category.tests != null) return false;
        if (title != null ? !title.equals(category.title) : category.title != null) return false;
        if (users != null ? !users.equals(category.users) : category.users != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = categoryId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (tests != null ? tests.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }
}