package io.ztc.indexlistview.IndexListUtils;

import java.util.Comparator;

import io.ztc.indexlistview.SortModel;

public class PinyinComparator implements Comparator<SortModel> {

    /**
     * 数据排序
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(SortModel o1, SortModel o2) {
        if (o2.getSortLetters().equals("#")){
            return -1;
        }else if (o1.getSortLetters().equals("#")){
            return 1;
        }else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
