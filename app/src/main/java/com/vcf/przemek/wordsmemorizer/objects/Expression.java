package com.vcf.przemek.wordsmemorizer.objects;

import java.util.InputMismatchException;

/**
 * Created by Przemek on 2017-05-07.
 */

public class Expression {

    private Integer ID;
    private String key;
    private String translation;
    private String example;

    public Expression(Integer ID, String key, String translation, String example) {
        this.ID = ID;
        this.key = key;
        this.translation = translation;
        this.example = example;
    }

    @Override
    public String toString(){
        return this.key;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
