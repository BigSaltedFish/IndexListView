package io.ztc.indexlistview;

import io.ztc.indexlistview.IndexListUtils.CharacterParser;

/**
 * 数据bean
 */


public class SortModel {

    protected String name;//显示的数据
    protected String sortLetters;//显示数据拼音的首字母

    public SortModel(String name){
        this.name = name;
        setSortLetters(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String name) {
        this.sortLetters = new CharacterParser().getSelling(this.name).substring(0, 1).toUpperCase();
    }
}
