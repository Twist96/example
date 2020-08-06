package com.gasco.UI.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gasco.Enums.AlertType;
import com.gasco.Enums.QuestionType;
import com.gasco.Models.DispenseLog;
import com.gasco.Models.Equipment;
import com.gasco.Models.Question;
import com.gasco.Models.Skid;
import com.gasco.R;
import com.gasco.UI.Activities.CNGProcedureActivity;
import com.gasco.UI.Dialogs.ProcessDialog;
import com.gasco.services.API;
import com.gasco.services.gescoServices.SkidProcedureService;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionCustomSkidSelectionFragment extends QuestionFragment implements CompoundButton.OnCheckedChangeListener {

    private Equipment[] dispensers = {new Equipment("DSP1"), new Equipment("DSP2")};
    private List<String> dispenserNames;
    private List<Equipment> selectedEquipment = new ArrayList<>();
    //List<Skid> skids;
    Gson gson = new Gson();
    List<Equipment> equipments = new ArrayList<>();

    private static final String CMP1  = "CMP1";
    private static final String CMP2  = "CMP2";
    private static final String DSP1  = "DSP1";
    private static final String DSP2  = "DSP2";

    MaterialCheckBox compressor1, compressor2;
    private TextView questionNumberView, questionView;


    public QuestionCustomSkidSelectionFragment() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_question_custom_skid_selection, container, false);

        nextBtn = view.findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProcessDialog p = parentActivity.getProcessDialog();
                p.setMessage("Loading. . .");
                p.show();
                submitSkidSelection();
                deactivateNextButton();
            }
        });

        previousButton = view.findViewById(R.id.previous_btn);
        //hidePreviousButton();
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

        questionNumberView.setText(String.valueOf(questionNumber + 1));
        questionView.setText(question.getDescription());

        compressor1 = view.findViewById(R.id.compressor2);
        compressor2 = view.findViewById(R.id.compressor1);
        compressor1.setOnCheckedChangeListener(this);
        compressor2.setOnCheckedChangeListener(this);
        equipments.add(new Equipment(CMP1));
        equipments.add(new Equipment(DSP1));

        dispenserNames = getDispenserNames(dispensers);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getContext(),
                        R.layout.question_dropdown_item,
                        dispenserNames);
        AutoCompleteTextView editTextFilledExposedDropdown =
                view.findViewById(R.id.filled_exposed_dropdown);
        editTextFilledExposedDropdown.setText(dispenserNames.get(0));
        editTextFilledExposedDropdown.setAdapter(adapter);
        editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dispId = position == 0 ? DSP1 : DSP2;
                String oppDispId = position == 0 ? DSP2 : DSP1;

                //check for other dispensers and remove
                int oppDispIdIndex = getIndexWithId(oppDispId, equipments);
                if (oppDispIdIndex != -1){
                    equipments.remove(oppDispIdIndex);
                }
                //check if dispenser exist if null add else do nothing
                int dispIdIndex = getIndexWithId(dispId, equipments);
                if (dispIdIndex == -1){
                    equipments.add(new Equipment(dispId));
                }
            }
        });

        return view;
    }


    @Override
    public void GetSelectedResult(GetResult getResult){
        getResult.onResult(QuestionType.selectCompressor, questionNumber, equipments);
    }

    private List<String> getDispenserNames(Equipment[] equipments){
        List<String> mList = new ArrayList<>();

        for (Equipment equipment:
             equipments) {
            mList.add(equipment.getId());
        }

        return mList;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String compressorId = buttonView.getId() == R.id.compressor1 ? CMP1 : CMP2;

            if (isChecked){
                equipments.add(new Equipment(compressorId));
            }else{
                int ei = getIndexWithId(compressorId, equipments);
                if (ei != -1){
                    equipments.remove(ei);
                }
            }

    }

    private int getIndexWithId(String id, List<Equipment> equipments){
        for (int i = 0; i < equipments.size(); i++) {
            if (equipments.get(i).getId().equals(id))
                return i;
        }
        return -1;
    }

    private void submitSkidSelection(){
        // THIS IS USED FOR SELECTING SKID
        API.SKID_PROCEDURE_SERVICE.SelectSkid(parentActivity.getSelectedSkidId(), question.getId(), equipments, selectSkidCallback);
    }

    SkidProcedureService.SelectSkidCallback selectSkidCallback = new SkidProcedureService.SelectSkidCallback() {
        @Override
        public void onSuccess(final DispenseLog dispenseLog) {

            // todo: navigate to the next screen
            parentActivity.setCreatedDispenseLog(dispenseLog);
            parentActivity.onNext();

        }

        @Override
        public void onFailure(final String errorMessage) {

            //todo: show errorMessage
            parentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   parentActivity.getProcessDialog().dismiss();
                   parentActivity.getPopUpAlertDialog().setValues("Error", errorMessage, AlertType.error);
                   parentActivity.getPopUpAlertDialog().show();
                }
            });
        }
    };
}
