package com.micro_services.quiz_service.service;

import com.micro_services.quiz_service.dao.QuizDao;
import com.micro_services.quiz_service.feing.QuizInterface;
import com.micro_services.quiz_service.model.QuestionWrapper;
import com.micro_services.quiz_service.model.Quiz;
import com.micro_services.quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;


    public ResponseEntity<String> createQuiz(String category, int noOfQuestions, String title) {
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, noOfQuestions).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionsIds(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("quiz created", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(int id) {
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionsIds();
        ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionFromId(questionIds);
        return questions;

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}
