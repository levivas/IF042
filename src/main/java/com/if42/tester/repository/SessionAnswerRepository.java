package com.if42.tester.repository;

import com.if42.tester.entity.SessionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SessionAnswerRepository
        extends JpaRepository<SessionAnswer, Integer>
        , PagingAndSortingRepository<SessionAnswer, Integer> {

    @Query(value =
            "SELECT "
                    + " COUNT(a) "
                    + " FROM "
                    + " SessionAnswer a "
                    + " WHERE"
                    + " (a.sessionQuestion.sessionTest.testsResult.testResultId = :testResultId"
                    + " OR :testResultId = -1)"
                    + " AND a.sessionQuestion.question.rank = :difficulty"
                    + " AND a.answer.correct = TRUE"
                    + " AND (a.sessionQuestion.sessionTest.testsResult.user.userId = :userId"
                            + " OR :userId = -1)"
    )
    Long getCorrectUserAnswersCountForTestResultByDifficulty(@Param("testResultId") Integer testResultId,
                                                             @Param("difficulty") Integer rank,
                                                             @Param("userId") Integer userId);

    @Query(value =
            "SELECT "
                    + " COUNT(a) "
                    + " FROM "
                    + " SessionAnswer a "
                    + " WHERE"
                    + " (a.sessionQuestion.sessionTest.testsResult.testResultId = :testResultId"
                    + " OR :testResultId = -1)"
                    + " AND a.sessionQuestion.question.rank = :difficulty"
                    + " AND (a.sessionQuestion.sessionTest.testsResult.user.userId = :userId"
                        + " OR :userId = -1)"
    )
    Long getAllUserAnswersCountForTestResultByDifficulty(@Param("testResultId") Integer testResultId,
                                                         @Param("difficulty") Integer rank,
                                                         @Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM SessionAnswer sa WHERE sa.sessionQuestion.sessionQuestionId = :sessionQuestionId")
    public void removeBySessionQuestionId(@Param("sessionQuestionId") Integer sessionQuestionId);



    @Query(value =
            "SELECT a FROM SessionAnswer a " +
                    " WHERE  1 = 1"
                    //" WHERE a.sessionQuestion.sessionTest.testsResult.testResultId = :testResultId"
                    + " GROUP BY a.sessionQuestion.question"
    )
    public List<Object[]> getQuestionAnswerByTestResult(@Param("testResultId") Integer testResultId);


}


