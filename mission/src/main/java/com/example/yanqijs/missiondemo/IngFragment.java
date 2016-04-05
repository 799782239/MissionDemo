package com.example.yanqijs.missiondemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import adapter.MissionAdapter;
import single.Mission;


/**
 * A simple {@link Fragment} subclass.
 */
public class IngFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private MissionAdapter mMissionAdapter;

    public IngFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ing, null, false);
        initView(view);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        Log.i("fragment", "--------------------ing");
        mMissionAdapter = new MissionAdapter(getActivity());
        mRecyclerView.setAdapter(mMissionAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
    }

}
