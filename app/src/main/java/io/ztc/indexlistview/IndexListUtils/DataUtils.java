package io.ztc.indexlistview.IndexListUtils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import io.ztc.indexlistview.SortModel;

public class DataUtils {
    /**
     * 根据所给索引进项过滤排序
     */
    public static List<SortModel> filterData(String filter, List<SortModel> mDatas, CharacterParser characterParser, PinyinComparator pinyinComparator){
        List<SortModel> filterDataList = new ArrayList<>();
        if (TextUtils.isEmpty(filter)){
            filterDataList = mDatas;
        }else {
            filterDataList.clear();
            for (SortModel sortModel:mDatas){
                String name = sortModel.getName();
                if (name.toUpperCase().contains(filter.toUpperCase())
                        ||characterParser.getSelling(name).toUpperCase().startsWith(filter.toUpperCase()))
                {
                    filterDataList.add(sortModel);
                }
            }
        }
        //根据A-z排序
        Collections.sort(filterDataList,pinyinComparator);
        return filterDataList;
    }
}
