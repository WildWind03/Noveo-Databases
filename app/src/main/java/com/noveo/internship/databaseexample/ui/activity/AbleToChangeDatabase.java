package com.noveo.internship.databaseexample.ui.activity;

import com.noveo.internship.databaseexample.model.Toy;

public interface AbleToChangeDatabase {
    void insertData(Toy toy);
    void deleteData();
}
