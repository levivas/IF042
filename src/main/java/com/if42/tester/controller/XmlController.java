package com.if42.tester.controller;

import com.if42.tester.entity.Answer;
import com.if42.tester.entity.Question;
import com.if42.tester.entity.Test;
import com.if42.tester.entity.Tests;
import com.if42.tester.service.CategoryService;
import com.if42.tester.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.castor.CastorMarshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Controller
@RequestMapping(value = "/xml")
public class XmlController {
    private static final Logger logger = LoggerFactory
            .getLogger(XmlController.class);

    @Autowired
    private CastorMarshaller castorMarshaller;

    @Autowired
    private TestService testService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/tests", method = RequestMethod.GET)
    @ResponseBody
    public Tests exportAllTests(HttpServletResponse res) throws IOException {
        res.setHeader("Content-Disposition", "attachment; filename=" + "itester.xml");
        logger.info("return list objects through http response - export all tests");
        return new Tests(testService.findAllTest());
    }

    @RequestMapping(value = "/testcat", method = RequestMethod.GET)
    @ResponseBody
    public Tests exportByCategoryId(HttpServletResponse res,
                                    @RequestParam(value = "categoryId") Integer categoryId) throws IOException {
        res.setHeader("Content-Disposition", "attachment; filename="+testService.getCategoryNameByCategoryId(categoryId)+".xml");
        logger.info("return list objects through http responce - export tests by category");
        return new Tests(testService.findByCategoryIdXml(categoryId));
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public Tests exportByTestId(HttpServletResponse res,
                                @RequestParam(value = "testId") Integer testId) throws IOException {
        res.setHeader("Content-Disposition", "attachment; filename="+testService.getTestNameByTestId(testId)+".xml");
        logger.info("return list objects through http responce - export single test");
        return new Tests(testService.findByTestIdXml(testId));
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFileHandler(@RequestParam("file1") MultipartFile file,
                                    @RequestParam(value = "categorySelect") Integer categorySelect,
                                    Map<String, Object> map) throws IOException {

        if (!file.isEmpty()) {
            logger.info("File name: " + file.getName());
            logger.info("File size: " + file.getSize());
            logger.info("File content type: " + file.getContentType());

            InputStream in = file.getInputStream();
            BufferedInputStream stream = new BufferedInputStream(in);

            try {
                Tests tests   = (Tests) castorMarshaller.unmarshal(new StreamSource(stream));

                for (Test test: tests.getTests()) {
                    if(testService.testExists(test)) {
                        throw new FileUploadException("Copy name test", "Try again");
                    }
                    test.setCategory(categoryService.findCategoryById(categorySelect));
                    for (Question question : test.getQuestions()) {
                        question.setTest(test);
                        for (Answer answer : question.getAnswers()) {
                            answer.setQuestion(question);
                        }
                    }
                }
                testService.addAll(tests.getTests());
                return "redirect:/tests/"+categorySelect;
            } catch (IOException e) {
                logger.error("Error saving uploaded file " + e.getStackTrace());
                throw new FileUploadException("File Error", "UnexpectedException");
//                handleAllException("File Error", "UnexpectedException");
            }
        } else {
            throw new FileUploadException("File Error", "File is empty");
        }

    }


    @ExceptionHandler(FileUploadException.class)
    public ModelAndView handleAllException(FileUploadException ex) {

        ModelAndView model = new ModelAndView("generic_error");
        model.addObject("errMsg", ex.getErrMsg());
        model.addObject("errCode", ex.getErrCode());

        return model;
    }

 }
