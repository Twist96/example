package com.gasco.UI.Fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gasco.Enums.AlertType;
import com.gasco.Enums.QuestionType;
import com.gasco.Models.DispenseLog;
import com.gasco.Models.DispenseLogUpdate;
import com.gasco.Models.Question;
import com.gasco.R;
import com.gasco.services.API;
import com.gasco.services.gescoServices.DispenseLogService;
import com.gasco.services.gescoServices.SkidProcedureService;
import com.google.android.material.button.MaterialButton;


public class QuestionTextFragment extends QuestionFragment {

    private TextView questionNumberView, questionView, answerView;

    public QuestionTextFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_text, container, false);

        nextBtn = view.findViewById(R.id.next_btn);
        if (parentActivity.isLastQuestion()){
            nextBtn.setText(R.string.finish);
        }

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDispenseLog();
                deactivateNextButton();
            }
        });

        previousButton = view.findViewById(R.id.previous_btn);
        hidePreviousButton();
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.onPrevious();
            }
        });

        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setProgress(getQuestionProgressValue());
        TextView questionFraction = view.findViewById(R.id.question_fraction);
        questionFraction.setText(String.format("%d\\%d", questionNumber + 1, questionSize));

        questionView = view.findViewById(R.id.question);
        questionNumberView = view.findViewById(R.id.question_number);
        answerView = view.findViewById(R.id.answer_view);

        questionNumberView.setText(String.valueOf(questionNumber + 1));
        questionView.setText(question.getDescription());

        return view;
    }

    @Override
    public void setQuestion(Question question, int questionNumber, int questionSize) {
        super.setQuestion(question, questionNumber, questionSize);

        if (questionView != null){
            questionNumberView.setText(String.valueOf(questionNumber + 1));
            questionView.setText(question.getDescription());
        }
    }

    @Override
    public void GetSelectedResult(GetResult getResult){
        getResult.onResult(QuestionType.text, questionNumber, answerView.getText().toString());
    }

    private void updateDispenseLog(){
        String answer = answerView.getText().toString();
        if (answer.isEmpty()){
            Toast.makeText(getContext(), "Please Enter A value", Toast.LENGTH_SHORT).show();
            return;
        }
        DispenseLogUpdate body = new DispenseLogUpdate(question.getId(), answer, question.getProperty());
        API.SKID_PROCEDURE_SERVICE.UpdateLog(parentActivity.getCreatedDispenseLog().getId(), body, updateLogCallback);
    }



    SkidProcedureService.UpdateLogCallback updateLogCallback = new SkidProcedureService.UpdateLogCallback() {
        @Override
        public void onSuccess(DispenseLog dispenseLog) {
            parentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    parentActivity.getProcessDialog().dismiss();
                    parentActivity.onNext();
                }
            });
        }

        @Override
        public void onFailure(final String errorMessage) {
            parentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    parentActivity.getProcessDialog().dismiss();
                    parentActivity.getPopUpAlertDialog().setValues("Network Error", errorMessage, AlertType.error);
                    parentActivity.getPopUpAlertDialog().show();
                }
            });
        }
    };

}
