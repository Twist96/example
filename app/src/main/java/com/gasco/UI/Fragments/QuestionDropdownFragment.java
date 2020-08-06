package com.gasco.UI.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gasco.Enums.QuestionType;
import com.gasco.Models.ApiListResponse;
import com.gasco.Models.Question;
import com.gasco.Models.Skid;
import com.gasco.R;
import com.gasco.UI.Activities.CNGProcedureActivity;
import com.gasco.services.API;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionDropdownFragment extends QuestionFragment {

    List<String> skidNames;
    List<Skid> skids;
    Gson gson = new Gson();

    private int selectedDropdownIndexNumber;
    private TextView questionNumberView, questionView;

    public QuestionDropdownFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_question_dropdown, container, false);

        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setProgress(getQuestionProgressValue());
        TextView questionFraction = view.findViewById(R.id.question_fraction);
        questionFraction.setText(String.format("%d\\%d", questionNumber + 1, questionSize)); //mQuestions.size()));

        nextBtn = view.findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.setSelectedSkidId(skids.get(selectedDropdownIndexNumber).getId());
                parentActivity.onNext();
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

        view.findViewById(R.id.previous_btn).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.previous_btn).setClickable(false);

        setHasOptionsMenu(true);
        questionView = view.findViewById(R.id.question);
        questionNumberView = view.findViewById(R.id.question_number);

        questionNumberView.setText(String.valueOf(questionNumber + 1));
        questionView.setText(question.getDescription());

        String value = getArguments().getString("skids");
        Type type = new TypeToken<List<Skid>>(){}.getType();
        skids = gson.fromJson(value, type);
        skidNames = getSkidNames(skids);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getContext(),
                        R.layout.question_dropdown_item,
                        getSkidNames(skids));
        AutoCompleteTextView editTextFilledExposedDropdown =
                view.findViewById(R.id.filled_exposed_dropdown);
        editTextFilledExposedDropdown.setText(skidNames.get(selectedDropdownIndexNumber));
        editTextFilledExposedDropdown.setAdapter(adapter);

        editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedDropdownIndexNumber = position;
            }
        });

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
        getResult.onResult(QuestionType.dropdown, questionNumber, selectedDropdownIndexNumber);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){

        }
    }

    private List<String> getSkidNames(List<Skid> skids){
        List<String> mList = new ArrayList<>();

        for (Skid skid:
                skids) {
            mList.add(skid.getSkidNo());
        }

        return mList;
    }
}
