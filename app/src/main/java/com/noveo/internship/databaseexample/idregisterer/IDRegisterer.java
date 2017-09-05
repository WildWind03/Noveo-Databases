package com.noveo.internship.databaseexample.idregisterer;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.concurrent.atomic.AtomicLong;

public class IDRegisterer implements Parcelable {

    private AtomicLong maxId;

    public IDRegisterer(int startId) {
        maxId = new AtomicLong(startId + 1);
    }

    protected IDRegisterer(Parcel in) {
    }

    public static final Creator<IDRegisterer> CREATOR = new Creator<IDRegisterer>() {
        @Override
        public IDRegisterer createFromParcel(Parcel in) {
            return new IDRegisterer(in);
        }

        @Override
        public IDRegisterer[] newArray(int size) {
            return new IDRegisterer[size];
        }
    };

    public long getId() {
        return maxId.getAndIncrement();
    }

    public long getMaxId() {
        return maxId.get() - 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(maxId.get() - 1);
    }
}
