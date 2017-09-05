package com.noveo.internship.databaseexample.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.noveo.internship.databaseexample.R;
import com.noveo.internship.databaseexample.model.Toy;
import com.noveo.internship.databaseexample.ui.activity.AbleToChangeDatabase;

import butterknife.BindView;
import butterknife.OnClick;

public class AddNewRecordFragment extends BaseFragment {

    @BindView(R.id.cost_edit)
    protected EditText costEditText;

    @BindView(R.id.title_edit)
    protected EditText titleEditText;

    public static AddNewRecordFragment newInstance() {
        return new AddNewRecordFragment();
    }

    @Override
    protected void onPostViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(context instanceof AbleToChangeDatabase)) {
            throw new ClassCastException("Context has to implement AbleToChangeDatabase interface");
        }

    }

    @OnClick(R.id.delete_last_button)
    public void onDeleteButtonClick(View view) {
        ((AbleToChangeDatabase)getActivity()).deleteData();
    }

    @OnClick(R.id.insert_button)
    public void onInsertButtonClick(View view) {
        if (!TextUtils.isEmpty(titleEditText.getText()) && !TextUtils.isEmpty(costEditText.getText())) {
            Toy toy = new Toy(Integer.valueOf(String.valueOf(costEditText.getText())), String.valueOf(titleEditText.getText()));
            ((AbleToChangeDatabase)getActivity()).insertData(toy);
        } else {
            Toast.makeText(getActivity(), getString(R.string.toast_message), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.insert_record_fragment;
    }
}
