package com.gasco.UI.Fragments;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.gasco.Enums.QuestionType;
import com.gasco.Models.Question;
import com.gasco.Models.Skid;
import com.gasco.UI.Activities.CNGProcedureActivity;
import com.google.android.material.button.MaterialButton;

public class QuestionFragment extends Fragment {
    public Question question;
    public void setQuestion(Question question, int questionNumber, int questionSize){
        this.question = question;
        this.questionNumber = questionNumber;
        this.questionSize = questionSize;
    }
    public MaterialButton nextBtn;
    public MaterialButton previousButton;

    public void GetSelectedResult(GetResult getResult){

    }

    public interface GetResult{
        void onResult(QuestionType questionType, int questionNumber, Object answer);
        void onNext();
        void onPrevious();
    }

    CNGProcedureActivity parentActivity;
    public void setParentActivity(CNGProcedureActivity activity){
        this.parentActivity = activity;
    }

    private String skidId;
    public void setSkid(String skid){
        this.skidId = skid;
    }

    public int questionNumber;
    public int questionSize;
    public int getQuestionProgressValue(){
        double m = (questionNumber + 1) * 100;
        double b = (m /  questionSize);
        return (int) Math.round(b);
    }

    public void deactivateNextButton(){
        nextBtn.setAlpha(0.5f);
        nextBtn.setActivated(false);
    }

    public void hidePreviousButton(){
        previousButton.setVisibility(View.INVISIBLE);
        previousButton.setActivated(false);
    }
}
