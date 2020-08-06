package com.gasco.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gasco.Enums.AlertType;
import com.gasco.Enums.QuestionType;
import com.gasco.Models.DispenseLog;
import com.gasco.Models.DispenseLogUpdate;
import com.gasco.Models.Equipment;
import com.gasco.Models.Question;
import com.gasco.Models.Skid;
import com.gasco.R;
import com.gasco.UI.Dialogs.PopUpAlertDialog;
import com.gasco.UI.Dialogs.ProcessDialog;
import com.gasco.UI.Fragments.QuestionCheckboxFragment;
import com.gasco.UI.Fragments.QuestionCustomSkidSelectionFragment;
import com.gasco.UI.Fragments.QuestionDropdownFragment;
import com.gasco.UI.Fragments.QuestionFragment;
import com.gasco.UI.Fragments.QuestionTextFragment;
import com.gasco.services.API;
import com.gasco.services.gescoServices.DispenseLogService;
import com.gasco.services.gescoServices.SkidProcedureService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.List;

public class CNGProcedureActivity extends AppCompatActivity implements QuestionFragment.GetResult {

    private Question currentQuestion;
    private int currentQuestionIndex = 0;
    private ProcessDialog processDialog;
    private PopUpAlertDialog popUpAlertDialog;

    public ProcessDialog getProcessDialog() {
        return processDialog;
    }

    public PopUpAlertDialog getPopUpAlertDialog() {
        return popUpAlertDialog;
    }

    private List<Question> mQuestions;
    private FragmentManager fragmentManager;
    private int questionProgressValue = 0;
    private QuestionFragment currentQuestionFragment;
    private List<Skid> mSkids;
    Gson gson =  new Gson();
    private String selectedSkidId;
    public void setSelectedSkidId(String selectedSkidId){
        this.selectedSkidId = selectedSkidId;
    }
    public String getSelectedSkidId(){
        return selectedSkidId;
    }
    private DispenseLog createdDispenseLog;

    public DispenseLog getCreatedDispenseLog() {
        return createdDispenseLog;
    }

    public void setCreatedDispenseLog(DispenseLog createdDispenseLog) {
        this.createdDispenseLog = createdDispenseLog;
    }

    public boolean isLastQuestion(){
        return (currentQuestionIndex + 1) == mQuestions.size();
    }

    //private ProgressBar questionProgressBar;
    //private MaterialButton previousButton, nextButton;
    //private TextView questionFraction;


    QuestionCheckboxFragment questionCheckboxFragment;
    QuestionDropdownFragment questionDropdownFragment;
    QuestionTextFragment questionTextFragment;
    QuestionCustomSkidSelectionFragment questionCustomSkidSelectionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_c_n_g_procedure);
        questionDropdownFragment = new QuestionDropdownFragment();
        questionCheckboxFragment = new QuestionCheckboxFragment();
        questionTextFragment = new QuestionTextFragment();
        questionCustomSkidSelectionFragment = new QuestionCustomSkidSelectionFragment();


        //questionProgressBar.setProgress(questionProgressValue);

        popUpAlertDialog = new PopUpAlertDialog(this);
        processDialog = new ProcessDialog(this);
        processDialog.setMessage("Fetching Questions");
        processDialog.show();

         fragmentManager = getSupportFragmentManager();

         API.SKID_PROCEDURE_SERVICE.GetSkids(skidCallback);
        Intent intent = new Intent();
        intent.putExtra("refreshTable", true);
        setResult(3, intent);
    }

    public void DismissActivity(View view) {
        Intent intent = new Intent();
        intent.putExtra("refreshTable", true);
        setResult(3, intent);
        finish();
    }

    public void Next() {
        if (currentQuestionIndex == (mQuestions.size() - 1)){
            popUpAlertDialog.setValues("Completed", "", AlertType.success);
            popUpAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CNGProcedureActivity.this.finish();
                        }
                    });
                }
            });
            popUpAlertDialog.show();
            return;
        }

        //setListener for question
        currentQuestionFragment.GetSelectedResult(CNGProcedureActivity.this);

        if (mQuestions.get(currentQuestionIndex).getQuestionType() == QuestionType.text){
            return;
        }

        if (currentQuestionIndex == 1){
            return;
        }

        currentQuestionIndex++;
        updateQuestion();
    }

    public void Previous() {

        currentQuestionIndex--;
        updateQuestion();
    }

    private void updateQuestion(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (currentQuestionFragment != null){
                    fragmentManager.beginTransaction().remove(currentQuestionFragment).commit();
                }
                currentQuestionFragment = getQuestionFragment(mQuestions.get(currentQuestionIndex).getQuestionType());
                currentQuestionFragment.setParentActivity(CNGProcedureActivity.this);

                if (currentQuestionIndex == 0){
                    Bundle bundle = new Bundle();
                    String skidString = gson.toJson(mSkids);
                    bundle.putString("skids", skidString);
                    currentQuestionFragment.setArguments(bundle);
                }
                if (currentQuestionIndex == 1){
                    currentQuestionFragment.setSkid(selectedSkidId);
                }

                fragmentManager.popBackStack();
                fragmentManager.beginTransaction().replace(R.id.question_options_holder, currentQuestionFragment).commit();
                currentQuestionFragment.setQuestion(mQuestions.get(currentQuestionIndex), currentQuestionIndex, mQuestions.size());
                //questionFraction.setText(String.format("%d\\%d", currentQuestionIndex + 1, mQuestions.size()));

            }
        });
    }

    private int getQuestionProgressValue(){
        double m = (currentQuestionIndex + 1) * 100;
        double b = (m / (mQuestions.size()));
        return (int) Math.round(b);
    }

    SkidProcedureService.QuestionsCallback questionsCallBack = new SkidProcedureService.QuestionsCallback() {
        @Override
        public void onSuccess(List<Question> questions) {
            processDialog.dismiss();
            mQuestions = questions;
            currentQuestion = mQuestions.get(currentQuestionIndex);
            updateQuestion();
        }

        @Override
        public void onError(String errorMessage) {
            processDialog.dismiss();
            popUpAlertDialog.setValues("Error", "Please check your internet", AlertType.error);
            popUpAlertDialog.show();

        }
    };

    @Override
    public void onResult(QuestionType questionType, int questionNumber, final Object answer) {

        if (questionNumber == 0){
            int selectedIndex = (int) answer;
            selectedSkidId = mSkids.get(selectedIndex).getId();
        }else if (questionNumber == 1){
            CNGProcedureActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    processDialog.setMessage("Creating log. . .");
                    processDialog.show();
                }
            });
            List<Equipment> equipment = (List<Equipment>) answer;
            // THIS IS USED FOR SELECTING SKID
            //API.SKID_PROCEDURE_SERVICE.SelectSkid(selectedSkidId, equipment, selectSkidCallback);
        }else if (questionType == QuestionType.text){
            String a = (String) answer;
            if (a.isEmpty()){
                Toast.makeText(this, "An answer is required", Toast.LENGTH_SHORT).show();
                return;
            }
            processDialog.setMessage("Loading. . .");
            processDialog.show();
        }

    }

    //@Override
    public void onNext() {
        //todo: get next question, set the correct fragment, update question, check if it the last question
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (processDialog != null){
                    processDialog.dismiss();
                }
                if (currentQuestionIndex == (mQuestions.size() -1 )){
                    Intent intent = new Intent();
                    intent.putExtra("data", true);
                    setResult(3, intent);
                    popUpAlertDialog.setValues("Completed", "", AlertType.success);
                    popUpAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(final DialogInterface dialog) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CNGProcedureActivity.this.finish();
                                }
                            });
                        }
                    });
                    popUpAlertDialog.show();
                    return;
                }
                currentQuestionIndex++;
                updateQuestion();
                currentQuestionFragment.setQuestion(mQuestions.get(currentQuestionIndex), currentQuestionIndex, mQuestions.size());
            }
        });
    }

    //@Override
    public void onPrevious() {
        //todo: get previous question, set the correct fragment, update question
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (processDialog != null){
                    processDialog.dismiss();
                }
                if (currentQuestionIndex > 0){
                    currentQuestionIndex--;
                    updateQuestion();
                    currentQuestionFragment.setQuestion(mQuestions.get(currentQuestionIndex), currentQuestionIndex, mQuestions.size());
                }
            }
        });
    }

    SkidProcedureService.SkidCallback skidCallback = new SkidProcedureService.SkidCallback() {
        @Override
        public void onSuccess(List<Skid> skids) {
            mSkids = skids;
            API.SKID_PROCEDURE_SERVICE.GetQuestions(questionsCallBack);
        }

        @Override
        public void onFailure(String errorMessage) {
            processDialog.dismiss();
            popUpAlertDialog.setValues("Error!!!", errorMessage, AlertType.error);
            popUpAlertDialog.show();
        }
    };
    SkidProcedureService.SelectSkidCallback selectSkidCallback = new SkidProcedureService.SelectSkidCallback() {
        @Override
        public void onSuccess(final DispenseLog dispenseLog) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    processDialog.dismiss();
                    createdDispenseLog = dispenseLog;
                    currentQuestionIndex++;
                    updateQuestion();
                }
            });
        }

        @Override
        public void onFailure(final String errorMessage) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    processDialog.dismiss();
                    popUpAlertDialog.setValues("Network Error", errorMessage, AlertType.error);
                    popUpAlertDialog.show();
                }
            });
        }
    };


    private QuestionFragment getQuestionFragment(QuestionType questionType){
        switch (questionType){
            case checkbox:
                return new QuestionCheckboxFragment();
                //return questionCheckboxFragment;
            case dropdown:
                return new QuestionDropdownFragment();
                //return questionDropdownFragment;
            case text:
                return new QuestionTextFragment();
                //return questionTextFragment;
            case selectCompressor:
                return new QuestionCustomSkidSelectionFragment();
                //return questionCustomSkidSelectionFragment;
            default:
                return null;
        }
    }

    public void viewClicked(View view) {
//        commentTextView.clearFocus();
//        InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(commentTextView.getWindowToken(), 0);
    }
}
