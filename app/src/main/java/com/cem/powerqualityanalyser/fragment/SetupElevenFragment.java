package com.cem.powerqualityanalyser.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cem.powerqualityanalyser.AppConfig.AppConfig;
import com.cem.powerqualityanalyser.R;
import com.cem.powerqualityanalyser.adapter.SetAdapter;
import com.cem.powerqualityanalyser.view.PagingItemDecoration;


public class SetupElevenFragment extends BaseFragment{


    private AppConfig config;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private SetAdapter setAdapter;
    private PagingItemDecoration pagingItemDecoration = null;
    private SetAdapter.SetOnItemClickListner setOnItemClickListner;

    @Override
    public int setContentView() {
        return R.layout.fragment_set_eleven;
    }

    @Override
    public void onInitView() {
        config = AppConfig.getInstance();
        setAdapter = new SetAdapter(this.getActivity());
        recyclerView = (RecyclerView) findViewById(R.id.set_recyclerview);
        layoutManager = new GridLayoutManager(this.getActivity(), 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(setAdapter);
        setAdapter.setSetOnItemClickListner(setOnItemClickListner);

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public void setSetOnItemClickListner(SetAdapter.SetOnItemClickListner listner){
        this.setOnItemClickListner = listner;
    }


}
