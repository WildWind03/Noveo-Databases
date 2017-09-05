package com.noveo.internship.databaseexample.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;

import com.noveo.internship.databaseexample.R;
import com.noveo.internship.databaseexample.db.ContentDescriptor;
import com.noveo.internship.databaseexample.model.Toy;
import com.noveo.internship.databaseexample.ui.fragments.AddNewRecordFragment;
import com.noveo.internship.databaseexample.ui.fragments.RecordListFragmentSQL;

import java.util.logging.Logger;

public class SQLiteActivity extends Activity implements AbleToChangeDatabase{
    private final static Logger logger = Logger.getLogger(SQLiteActivity.class.getName());

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_activity);

        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction().add(R.id.insert_record_fragment_layout, AddNewRecordFragment.newInstance(), AddNewRecordFragment.class.getName()).commit();
            getFragmentManager().beginTransaction().add(R.id.list_of_records_fragment_layout, RecordListFragmentSQL.newInstance(), RecordListFragmentSQL.class.getName()).commit();
        }
    }

    @Override
    public void insertData(Toy toy) {
        Fragment fragment = getFragmentManager().findFragmentByTag(RecordListFragmentSQL.class.getName());
        if (fragment instanceof RecordListFragmentSQL) {

            ContentValues values = new ContentValues();
            values.put(ContentDescriptor.Toys.Cols.TITLE, toy.getTitle());
            values.put(ContentDescriptor.Toys.Cols.COST, toy.getCost());
            ((RecordListFragmentSQL) fragment).insertData(values);
        }
    }

    @Override
    public void deleteData() {
        Fragment fragment = getFragmentManager().findFragmentByTag(RecordListFragmentSQL.class.getName());
        if (fragment instanceof RecordListFragmentSQL) {
            ((RecordListFragmentSQL) fragment).deleteLast();
        }
    }

}
