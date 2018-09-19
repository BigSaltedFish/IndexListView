package io.ztc.indexlistview;

import android.support.v7.widget.RecyclerView;

public class UserAdapter extends IndexBaseAdapter<SortModel> {

    public UserAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_index_name);
    }

    @Override
    protected void fillData(IndexBaseHolder helper, int position, SortModel model) {
        helper.setText(R.id.item_name, model.name);
    }

    public boolean isCategory(int position) {
        int category = getItem(position).sortLetters.charAt(0);
        return position == getPositionForCategory(category);
    }

    public int getPositionForCategory(int category) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = String.valueOf(getItem(i).sortLetters.charAt(0));
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == category) {
                return i;
            }
        }
        return -1;
    }
}
