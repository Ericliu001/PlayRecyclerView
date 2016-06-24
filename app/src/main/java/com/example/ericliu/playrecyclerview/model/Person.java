package com.example.ericliu.playrecyclerview.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eric.liu on 27/05/15.
 */
public class Person implements Parcelable {



    private String name;
    private int age = 20;
    private Sex sex = Sex.MALE;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }




    public Person(){}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeSerializable(sex);
    }

    public static final Parcelable.Creator<Person> CREATOR
            = new Parcelable.Creator<Person>() {
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    private Person(Parcel in) {
        name = in.readString();
        age = in.readInt();
        sex = (Sex) in.readSerializable();
    }

    public enum Sex{
        MALE, FEMALE;
    }

}
