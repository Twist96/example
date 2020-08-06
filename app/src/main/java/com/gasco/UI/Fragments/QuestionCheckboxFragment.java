package com.gasco.UI.Fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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
import com.gasco.services.gescoServices.SkidProcedureService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;

public class QuestionCheckboxFragment extends QuestionFragment implements CompoundButton.OnCheckedChangeListener {

    private boolean isChecked = false;
    private TextView questionNumberView, questionView;
    private MaterialCheckBox checkBox;

    public QuestionCheckboxFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_checkbox, container, false);
        nextBtn = view.findViewById(R.id.next_btn);
        if (parentActivity.isLastQuestion()){
            nextBtn.setText(R.string.finish);
        }

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitResult();
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

        if (questionNumber == 3){
            view.findViewById(R.id.previous_btn).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.previous_btn).setClickable(false);
        }

        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setProgress(getQuestionProgressValue());
        TextView questionFraction = view.findViewById(R.id.question_fraction);
        questionFraction.setText(String.format("%d\\%d", questionNumber + 1, questionSize));

        checkBox = view.findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(this);

        questionView = view.findViewById(R.id.question);
        questionNumberView = view.findViewById(R.id.question_number);

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
        getResult.onResult(QuestionType.selectCompressor, questionNumber, isChecked);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        this.isChecked = isChecked;
    }

    private void submitResult(){
        if (isChecked){
            DispenseLogUpdate body = new DispenseLogUpdate(question.getId(), null, null);
            API.SKID_PROCEDURE_SERVICE.UpdateLog(parentActivity.getCreatedDispenseLog().getId(), body, updateLogCallback);

        }else{
            Toast.makeText(getContext(), "Please ensure all process is complete", Toast.LENGTH_SHORT).show();
        }
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
