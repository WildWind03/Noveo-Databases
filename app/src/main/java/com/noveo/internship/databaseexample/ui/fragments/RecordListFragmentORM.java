package com.noveo.internship.databaseexample.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.noveo.internship.databaseexample.R;
import com.noveo.internship.databaseexample.dao.ToyDao;
import com.noveo.internship.databaseexample.db.ORMLiteOpenHelper;
import com.noveo.internship.databaseexample.model.Toy;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class RecordListFragmentORM extends BaseFragment {

    private static final Logger logger = Logger.getLogger(RecordListFragmentORM.class.getName());

    @BindView(R.id.list)
    protected ListView listView;

    private List<Toy> toys;
    private ArrayAdapter listAdapter;

    private ORMLiteOpenHelper openHelper;

    public static RecordListFragmentORM newInstance() {
        return new RecordListFragmentORM();
    }

    @Override
    protected void onPostViewCreated(View view, @Nullable Bundle savedInstanceState) {
        openHelper = OpenHelperManager.getHelper(getActivity(), ORMLiteOpenHelper.class);

        Observable
                .just(null)
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, toys);
                                listView.setAdapter(listAdapter);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.info(e.getMessage());
                    }

                    @Override
                    public void onNext(Object o) {
                        try {
                            toys = openHelper.getToyDAO().getAllToys();
                        } catch (SQLException e) {
                            logger.info(e.getMessage());
                        }
                    }
                });
    }

    @Override
    protected int getLayout() {
        return R.layout.list_of_records_fragment;
    }

    public void insertData(Toy toy) throws SQLException {
        Observable
                .just(toy)
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Toy>() {
                    @Override
                    public void onCompleted() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.info(e.toString());
                    }

                    @Override
                    public void onNext(Toy toy) {
                        try {
                            openHelper.getToyDAO().create(toy);
                            toys.add(toy);
                        } catch (SQLException e) {
                            logger.info(e.toString());
                        }
                    }
                });
    }

    public void deleteLast() throws SQLException {
        Observable
                .just(null)
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        logger.info(e.toString());
                    }

                    @Override
                    public void onNext(Object o) {
                        try {
                            openHelper.getToyDAO().deleteLastRecord();

                            if (toys.size() > 0) {
                                toys.remove(toys.size() - 1);
                            }
                        } catch (SQLException e) {
                            logger.info(e.toString());
                        }
                    }
                });
    }
}
