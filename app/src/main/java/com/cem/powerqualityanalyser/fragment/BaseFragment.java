package com.cem.powerqualityanalyser.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cem.powerqualityanalyser.tool.log;

public abstract class BaseFragment extends Fragment {

    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(container==null)
            return null;
        if (rootView==null) {
            int layoutID=setContentView();
            if (layoutID>0) {
                rootView = inflater.inflate(layoutID, container, false);
                onInitView();
            }
        }
        return rootView;
    }

    public abstract int setContentView();
    public abstract void onInitView();

    public View findViewById(int id){
        return  rootView.findViewById(id);
    }

    public void setFocus(View view) {

        view.setFocusable(true);
        view.setFocusableInTouchMode(true);

        view.requestFocus();
        view.requestFocusFromTouch();
    }

    protected  String Res2String(int resID){
        return  getResources().getString(resID);
    }

}
