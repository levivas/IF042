package com.if42.tester.repository;

import com.if42.tester.entity.Test;
import com.if42.tester.entity.TestsResult;
import com.if42.tester.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface TestsResultRepository
        extends JpaRepository<TestsResult, Integer>,
                PagingAndSortingRepository<TestsResult, Integer> {

    public List<TestsResult> findByUser(User user);
    public List<TestsResult> findByTest(Test test);

    /**
     * Custom query for filtering test results by 5 parameters
     * @param testId
     * @param groupId
     * @param categoryId
     * @param dateA
     * @param dateB
     * @param pageable
     * @return Pa
     */
    @Query(value =
              "SELECT r from TestsResult r WHERE "
            + "(r.test.testId = :testId OR :testId = -1)"
            + " AND (r.user.group.groupId = :groupId OR :groupId = -1) "
            + " AND (r.test.category.categoryId = :categoryId OR :categoryId = -1)"
            + " AND (r.passDate BETWEEN :dateA AND :dateB) ORDER BY r.test.name"
    )
    public Page<TestsResult> findByTestGroupCategory(@Param("testId") Integer testId,
                                                     @Param("groupId") Integer groupId,
                                                     @Param("categoryId") Integer categoryId,
                                                     @Param("dateA") Timestamp dateA,
                                                     @Param("dateB") Timestamp dateB,
                                                     Pageable pageable);

    @Query(value =
            "SELECT r from TestsResult r WHERE "
            + " (r.test.testId = :testId OR :testId = -1)"
            + " AND (r.test.category.categoryId = :categoryId OR :categoryId = -1)"
            + " AND (r.passDate BETWEEN :dateA AND :dateB) "
            + " AND r.user.userId = :userId ORDER BY r.test.name"
    )
    Page<TestsResult> findByTestCategoryUserId(@Param("testId") Integer testId,
                                               @Param("categoryId") Integer categoryId,
                                               @Param("dateA") Timestamp dateA,
                                               @Param("dateB") Timestamp dateB,
                                               @Param("userId") Integer userId,
                                               Pageable pageable);


       @Query(value = "SELECT r.user, avg(r.markPercents) as markPercents,r.test.category.categoryId from TestsResult r "
               +"WHERE (r.user.group.groupId = :groupId OR :groupId = -1)"
               + " AND (r.test.category.categoryId = :categoryId OR :categoryId = -1)"
               +"GROUP BY r.user ORDER BY markPercents desc" )
    public Page<Object []> findByGroupForRatingUser(@Param("groupId") Integer groupId,
                                                    @Param("categoryId") Integer categoryId,
                                                    Pageable pageable);

    @Query(value =
            "SELECT r.test.category as caption, avg(r.markPercents) as markPercents from TestsResult r "
                    +"WHERE (r.user.group.groupId = :groupId OR :groupId = -1)"
                    + " GROUP BY r.test.category ORDER BY markPercents desc" )
    public Page<Object[]> getCategoriesAverageForRating(@Param("groupId") Integer groupId, Pageable pageable);

    @Query(value =
            "SELECT r.test.category.title as caption, avg(r.markPercents) as val from TestsResult r "
            + " WHERE r.user.userId = :userId OR :userId = -1 "
            + " GROUP BY r.test.category"
    )
    public List<Object[]> getCategoriesAverage(@Param("userId") Integer userId);

    @Query(value =
            "SELECT r.test.category.title as caption, max(r.markPercents) as val from TestsResult r "
            + " WHERE r.user.userId = :userId OR :userId = -1 "
            + " GROUP BY r.test.category"
    )
    public List<Object[]> getCategoriesMax(@Param("userId") Integer userId);


    @Query(value =
            "SELECT r.test.category.title as caption, min(r.markPercents) as val from TestsResult r "
            + " WHERE r.user.userId = :userId OR :userId = -1 "
            + " GROUP BY r.test.category"

    )
    public List<Object[]> getCategoriesMin(@Param("userId") Integer userId);

    @Query(value =
            "SELECT r from TestsResult r WHERE "
                    + "(r.test.testId = :testId OR :testId = -1)"
                    + " AND (r.user.group.groupId = :groupId OR :groupId = -1) "
                    + " AND (r.test.category.categoryId = :categoryId OR :categoryId = -1)"
                    + " AND (r.passDate BETWEEN :dateA AND :dateB) "
    )
    public List<TestsResult> findByTestGroupCategoryList(@Param("testId") Integer testId,
                                                         @Param("groupId") Integer groupId,
                                                         @Param("categoryId") Integer categoryId,
                                                         @Param("dateA") Timestamp dateA,
                                                         @Param("dateB") Timestamp dateB);

    @Query(value =
            "SELECT avg(t.markPercents), t.user.group.name FROM TestsResult t"
            + " WHERE t.test.testId = :testId"
            + " GROUP BY t.user.group"
    )
    List<Object[]> averageForGroupsByTest(@Param("testId") Integer testId);

    @Query(value =
            "SELECT t.markPercents, t.passDate FROM TestsResult t "
            + " WHERE t.user.userId = :userId "
            + " AND t.test.testId = :testId"
    )
    List<Object[]> getResultsForUserTest(@Param("testId") Integer testId,
                                         @Param("userId") Integer userId);
}
