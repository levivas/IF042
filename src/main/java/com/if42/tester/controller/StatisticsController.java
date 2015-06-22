package com.if42.tester.controller;

import com.if42.tester.entity.GraphPoint;
import com.if42.tester.entity.TestsResult;
import com.if42.tester.service.SessionAnswerService;
import com.if42.tester.service.TestsResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class StatisticsController {
    final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private TestsResultService testsResultService;

    @Autowired
    private SessionAnswerService sessionAnswerService;

    /**
     * Show page with user statistics
     * @param map
     * @param resultId if given - only info for this result will be shown
     * @return
     */
    @RequestMapping(value = "/userStats", method = RequestMethod.GET)
    public String showStatistics(Map<String, Object> map,
                                 @RequestParam(value = "resultId", defaultValue = "-1") Integer resultId) {
        TestsResult result = testsResultService.findTestsResultById(resultId);
        if(result != null)
            map.put("test", result.getTest().getName());
        logger.info("userStats page opened");
        return "userStats";
    }

    /**
     *
     * @param testResultId
     * @param userId
     * @return JSON for userStats page
     */
    @ResponseBody
    @RequestMapping(value = "/getstats", method = RequestMethod.GET)
    public List< List<GraphPoint> > getStatisticsForResult(@RequestParam(value = "resultId", defaultValue = "-1") Integer testResultId,
                                                   @RequestParam(value = "userId", defaultValue = "-1") Integer userId) {
        TestsResult result = testsResultService.findTestsResultById(testResultId);

        List< List<GraphPoint> > list = new ArrayList< List<GraphPoint> >();
        list.add(sessionAnswerService.statisticsForTestResult(testResultId, userId));
        if(result != null)
            list.add(testsResultService.getStatsForUserTest(result.getTest().getTestId(), result.getUser().getUserId()));

        logger.info("Statistics for user id {} and result id {} sent", userId, testResultId);
        return list;
    }

    /**
     *
     * @param testId
     * @return JSON with information about results for given test
     */
    @ResponseBody
    @RequestMapping(value = "/getTestStats", method = RequestMethod.GET)
    public List<GraphPoint> getPercentage(@RequestParam("testId") Integer testId) {
        List<GraphPoint> graphPoints = testsResultService.getTestStats(testId);
        logger.info("Test statistics sent. Test id = {}; {} points", testId, graphPoints.size());
        return graphPoints;
    }

    /**
     * Show page with test statistics
     * @return
     */
    @RequestMapping(value = "/testStats", method = RequestMethod.GET)
    public String getTestStatistics() {
        logger.info("/testStats page opened");
        return "testStats";
    }
}
