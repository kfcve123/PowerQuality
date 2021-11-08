package com.cem.powerqualityanalyser.view;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.userobject.BaseBottomAdapterObj;
import com.cem.powerqualityanalyser.userobject.MeterKeyValue;

import java.util.ArrayList;
import java.util.List;

public class PublicPopupWindow extends PopupWindow implements AdapterView.OnItemClickListener, View.OnKeyListener {

    private ListView listView;
    private View contentView;
    private List<String> datalist;
    private ArrayAdapter<String> arrayAdapter;
    private PopupWindowClickCallback oncallback;
    private int popItemHight;
    private BaseBottomAdapterObj selectObj;
    private View dropDownView;
    private PopupWindowKeyDown keyCallback;


    public PublicPopupWindow(Activity activity) {

        View popupViewItem = LayoutInflater.from(activity).inflate(R.layout.listview_item, null);
        popupViewItem.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popItemHight = popupViewItem.getMeasuredHeight();
        this.datalist = new ArrayList<>();
        contentView = LayoutInflater.from(activity).inflate(R.layout.public_popupwindow, null);
        this.setContentView(contentView);
        listView = contentView.findViewById(R.id.list);
        arrayAdapter = new ArrayAdapter<String>(activity, R.layout.listview_item, R.id.tv, datalist);
        listView.setOnItemClickListener(this);
        listView.setAdapter(arrayAdapter);
     /*   contentView.setFocusableInTouchMode(true);
        contentView.setOnKeyListener(this);*/
        listView.setFocusableInTouchMode(true);
        listView.setOnKeyListener(this);
    }


    public void setPopListData(BaseBottomAdapterObj obj) {
        this.selectObj = obj;
        arrayAdapter.clear();
        arrayAdapter.addAll(obj.getMoreArray());
        arrayAdapter.notifyDataSetChanged();
    }

    public int getPopHight() {
        return popItemHight * arrayAdapter.getCount();
    }

    public View getDropDownView() {
        return dropDownView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dismiss();
        if (oncallback != null)
            oncallback.onPopupWindowItemClick(selectObj, position);

    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        dropDownView = parent;
        super.showAtLocation(parent, gravity, x, y);
    }

    public void setOnPopupWindowItemClick(PopupWindowClickCallback callback) {
        this.oncallback = callback;
    }

    public void setOnPopupWindowKeyDown(PopupWindowKeyDown callback) {
        this.keyCallback = callback;
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        int keycode = keyEvent.getKeyCode();
        if (keycode != MeterKeyValue.Up.value() && keycode != MeterKeyValue.Down.value() && keycode != MeterKeyValue.Left.value() && keycode != MeterKeyValue.Right.value() && keycode != MeterKeyValue.Enter.value()) {
            if (keyCallback != null && keyEvent.getAction() == 0) {
                return keyCallback.onKeyDown(keycode, keyEvent);
            }
        }
        return false;
    }

    public interface PopupWindowClickCallback {
        void onPopupWindowItemClick(BaseBottomAdapterObj obj, int position);
    }

    public interface PopupWindowKeyDown {
        boolean onKeyDown(int keyCode, KeyEvent keyEvent);
    }
}
