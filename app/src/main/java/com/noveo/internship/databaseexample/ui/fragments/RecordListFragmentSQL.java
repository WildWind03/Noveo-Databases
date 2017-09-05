package com.noveo.internship.databaseexample.ui.fragments;

import android.app.LoaderManager;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.noveo.internship.databaseexample.R;
import com.noveo.internship.databaseexample.db.ContentDescriptor;
import com.noveo.internship.databaseexample.db.ExampleOpenHelper;
import com.noveo.internship.databaseexample.idregisterer.IDRegisterer;

import butterknife.BindView;

public class RecordListFragmentSQL extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    @BindView(R.id.list)
    protected ListView listView;

    private IDRegisterer idRegisterer;

    private final int INSERT_KEY = 1;
    private final int DELETE_KEY = 2;

    private SimpleCursorAdapter adapter;
    private LoaderManager loaderManager;

    public static RecordListFragmentSQL newInstance() {
        return new RecordListFragmentSQL();
    }

    @Override
    protected void onPostViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, this);

        String[] columns = new String[]{ContentDescriptor.Toys.Cols.TITLE, ContentDescriptor.Toys.Cols.COST};
        int[] toFields = new int[]{R.id.title_text_view, R.id.cost_text_view};
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item_layout, null, columns, toFields, 0);

        listView.setAdapter(adapter);

        new AsyncQueryHandler(getActivity().getContentResolver()) {
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                super.onQueryComplete(token, cookie, cursor);

                if (cursor != null) {
                    cursor.moveToFirst();

                    if (cursor.getCount() > 0) {
                        idRegisterer = new IDRegisterer(cursor.getInt(cursor.getColumnIndex(ContentDescriptor.Toys.Cols.ID)));
                    } else {
                        idRegisterer = new IDRegisterer(0);
                    }

                    cursor.close();
                }
            }
        }.startQuery(3,
                null,
                ContentDescriptor.Toys.TABLE_URI, new String[]{ContentDescriptor.Toys.Cols.ID},
                ContentDescriptor.Toys.Cols.ID + " = (SELECT MAX(" + ContentDescriptor.Toys.Cols.ID + ") FROM " + ContentDescriptor.Toys.TABLE_NAME + ")", null, null);
    }

    public void insertData(ContentValues values) {
        values.put(ContentDescriptor.Toys.Cols.ID, idRegisterer.getId());
        new AsyncQueryHandler(getActivity().getContentResolver()) {
        }.startInsert(INSERT_KEY, null, ContentDescriptor.Toys.TABLE_URI, values);
    }

    public void deleteLast() {
        new AsyncQueryHandler(getActivity().getContentResolver()) {
        }.startDelete(DELETE_KEY, null, ContentDescriptor.Toys.TABLE_URI, ContentDescriptor.Toys.Cols.ID + " = (SELECT MAX(" + ContentDescriptor.Toys.Cols.ID + ") FROM " + ContentDescriptor.Toys.TABLE_NAME + ")", null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), ContentDescriptor.Toys.TABLE_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (adapter != null && cursor != null) {
            adapter.changeCursor(cursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (adapter != null) {
            adapter.changeCursor(null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ExampleOpenHelper.getInstance(getActivity()).close();
    }

    @Override
    protected int getLayout() {
        return R.layout.list_of_records_fragment;
    }
}

