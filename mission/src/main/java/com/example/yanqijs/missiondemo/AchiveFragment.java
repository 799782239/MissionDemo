package com.example.yanqijs.missiondemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import adapter.MissionAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AchiveFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private MissionAdapter mMissionAdapter;

    public AchiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("fragment","--------------------achive");
        View view = inflater.inflate(R.layout.fragment_ing, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mMissionAdapter = new MissionAdapter(getActivity());
        mRecyclerView.setAdapter(mMissionAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Inflate the layout for this fragment
        return view;
    }

}
