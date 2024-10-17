package com.micro_services.question_service.service;

import com.micro_services.question_service.dao.QuestionDao;
import com.micro_services.question_service.model.Question;
import com.micro_services.question_service.model.QuestionWrapper;
import com.micro_services.question_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestion() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<Question> getQuestionById(int id) {
        try {
            return new ResponseEntity<>(questionDao.findById(id).orElse(null), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new Question(), HttpStatus.BAD_REQUEST);
        }
    }

    public List<Question> getQuestionByCategory(String category) {
        return questionDao.findByCategory(category);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public String deleteQuestion(int id) {
        questionDao.deleteById(id);
        return "Deleted";
    }

    public String updateQuestion(Question question) {
        questionDao.save(question);
        return "updated";
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, int noOfQuestions) {
        List<Integer> questions = questionDao.getRandomQuestionByCategory(category, noOfQuestions);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrapper = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        for(Integer id : questionIds){
            questions.add(questionDao.findById(id).get());
        }
        for (Question q : questions) {
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            wrapper.add(qw);
        }
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = 0;
        for(Response response : responses){
            Question question = questionDao.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightAnswer())){
                right++;
            }
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
