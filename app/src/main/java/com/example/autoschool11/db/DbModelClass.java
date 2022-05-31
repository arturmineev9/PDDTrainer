package com.example.autoschool11.db;

import android.graphics.Bitmap;

public class DbModelClass {
    String question;
    Bitmap image;

    public DbModelClass(String question, Bitmap image) {
        this.question = question;
        this.image = image;

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}



