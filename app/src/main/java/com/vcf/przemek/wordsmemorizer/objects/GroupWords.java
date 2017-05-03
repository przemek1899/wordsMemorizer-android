package com.vcf.przemek.wordsmemorizer.objects;

/**
 * Created by Przemek on 2017-05-03.
 */

public class GroupWords {

    private Integer id;
    private String name;
    private Integer language_id;

    public GroupWords(Integer id, String name, Integer language_id) {
        this.id = id;
        this.name = name;
        this.language_id = language_id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getLanguage_id() {
        return language_id;
    }
}
