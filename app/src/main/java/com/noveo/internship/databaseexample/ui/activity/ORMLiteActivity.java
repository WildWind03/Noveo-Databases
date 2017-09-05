package com.noveo.internship.databaseexample.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.noveo.internship.databaseexample.R;
import com.noveo.internship.databaseexample.model.Toy;
import com.noveo.internship.databaseexample.ui.fragments.AddNewRecordFragment;
import com.noveo.internship.databaseexample.ui.fragments.RecordListFragmentORM;

import java.sql.SQLException;

public class ORMLiteActivity extends Activity implements AbleToChangeDatabase{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.database_activity);

        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction().add(R.id.insert_record_fragment_layout, AddNewRecordFragment.newInstance(), AddNewRecordFragment.class.getName()).commit();
            getFragmentManager().beginTransaction().add(R.id.list_of_records_fragment_layout, RecordListFragmentORM.newInstance(), RecordListFragmentORM.class.getName()).commit();
        }

    }

    @Override
    public void insertData(Toy toy) {
        Fragment listOfRecordsFragment = getFragmentManager().findFragmentByTag(RecordListFragmentORM.class.getName());

        if (listOfRecordsFragment instanceof RecordListFragmentORM) {
            try {
                ((RecordListFragmentORM) listOfRecordsFragment).insertData(toy);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteData() {
        Fragment listOfRecordsFragment = getFragmentManager().findFragmentByTag(RecordListFragmentORM.class.getName());
        if (listOfRecordsFragment instanceof RecordListFragmentORM) {
            try {
                ((RecordListFragmentORM) listOfRecordsFragment).deleteLast();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
