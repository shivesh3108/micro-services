package com.micro_services.question_service.controller;

import com.micro_services.question_service.model.Question;
import com.micro_services.question_service.model.QuestionWrapper;
import com.micro_services.question_service.model.Response;
import com.micro_services.question_service.service.QuestionService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> allQuestions(){
        return questionService.getAllQuestion();
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable int id){
        return questionService.getQuestionById(id);
    }

    @GetMapping("/category/{category}")
    public List<Question> getQuestionByCategory(@PathVariable String category){
        return questionService.getQuestionByCategory(category);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable int id){
        return questionService.deleteQuestion(id);
    }

    @PutMapping("/update")
    public String updateQuestion(@RequestBody Question question){
        return questionService.updateQuestion(question);
    }

    // generateQuiz
    // getQuestions
    // getScore

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam int NoOfQuestions){
        return questionService.getQuestionsForQuiz(category, NoOfQuestions);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(@RequestBody List<Integer> questionIds){
        return questionService.getQuestionFromId(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }
}
