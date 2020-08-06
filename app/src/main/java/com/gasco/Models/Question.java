package com.gasco.Models;

import com.gasco.Enums.QuestionType;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private String id = "";
    private String href;
    private String description;
    private String answer;
    private QuestionType questionType;
    private String comment;
    private String property;

    public Question(String description, QuestionType questionType) {
        this.description = description;
        this.questionType = questionType;
        this.property = "";
    }


    public String getId() {
        return id;
    }

    public String getHref() {
        return href;
    }

    public String getDescription() {
        return description;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public QuestionType getQuestionType() {
        if (getId().equals("SF_RESIDUAL_PRESSURE")){
            //fifth
            property = "residualPressure";
            return QuestionType.text;
        }else
            if (getId().equals("SF_READ_TEMPERATURE")){
            //sixth
            property = "temperature";
            return QuestionType.text;
        }
//            else
//            if (getId().equals("SF_CHECK_OIL_GAUGES")){ //no follow
//            //first
//            property = "RES_PRESSURE";
//            return QuestionType.text;
//        }
            else
            if (getId().equals("SF_INIT_DSP_READING")){
            //second
            property = "initialDispenserReading";
            return QuestionType.text;
        }else
            if (getId().equals("SF_FIN_PRESSURE")){
            //third
            property = "finalPressure";
            return QuestionType.text;
        }else
            if (getId().equals("SF_FIN_DSP_READING")){
            //forth
            property = "finalDispenserReading";
            return QuestionType.text;
        }
        else if (questionType == null){
            return QuestionType.checkbox;
        }else{
            return questionType;
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getProperty() {
        return property;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }
}

// ||
//          ||
//