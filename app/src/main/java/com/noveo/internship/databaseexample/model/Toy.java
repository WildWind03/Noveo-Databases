package com.noveo.internship.databaseexample.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.noveo.internship.databaseexample.db.ContentDescriptor;

@DatabaseTable(tableName = ContentDescriptor.Toys.TABLE_NAME)
public class Toy {

    @DatabaseField(generatedId = true, columnName = ContentDescriptor.Toys.Cols.ID)
    private int _id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = ContentDescriptor.Toys.Cols.TITLE)
    private String title;

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = ContentDescriptor.Toys.Cols.COST)
    private int cost;

    public Toy(int cost, String title) {
        this.cost = cost;
        this.title = title;
    }

    public Toy() {

    }

    public int getCost() {
        return cost;
    }

    public String getTitle() {
        return title;
    }

    public int getID() {
        return _id;
    }

    @Override
    public String toString() {
        return getTitle() + " " + getCost();
    }
}
