package com.example.emile1.findaparty.Activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.emile1.findaparty.Activity.Activity.MyLanDetailsActivity;
import com.example.emile1.findaparty.Activity.Lan;
import com.example.emile1.findaparty.Activity.adapter.CardViewAdapter;
import com.example.emile1.findaparty.Activity.adapter.LanAdapter;
import com.example.emile1.findaparty.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentFragment extends Fragment implements CardViewAdapter.OnCardClickListener{

    private ListView mListView;
    private List<Lan> lans;
    private LanAdapter lanAdapter;
    private ProgressBar progressBar;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private CardViewAdapter mCardViewAdapter;

    public RecentFragment() {
        // Required empty public constructor
    }

    public static RecentFragment newInstance() {
        RecentFragment fragment = new RecentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recent, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recent_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        progressBar.bringToFront();

        lans = new ArrayList<>();
        getLans();
        mCardViewAdapter = new CardViewAdapter(lans);
        mCardViewAdapter.setOnCardClickListener(this);

//        mRecyclerView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getActivity(),MyLanDetailsActivity.class);
//                intent.putExtra("Lan",lans.get(i));
//                startActivity(intent);
//            }
//        });
        return v;
    }

    @Override
    public void OnCardClicked(View view, int position) {
        Log.d("ONCLICK",position +"");
        Intent intent = new Intent(getActivity(),MyLanDetailsActivity.class);
        intent.putExtra("Lan",lans.get(position));
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
//        lans.clear();
//        Log.i("Size",String.valueOf(lans.size()));
//        getLans();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onStop(){
        super.onStop();
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

    //retrieve all the user's Lans and display it in a listview using a custom adapter
    public void getLans(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Lans");
        query.whereContains("IdOwner", ParseUser.getCurrentUser().getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                   progressBar.setVisibility(View.INVISIBLE);
                    for(ParseObject lan : objects){
                        lans.add(new Lan(lan.getObjectId(),
                                lan.getString("Date"),
                                lan.getString("Start"),
                                lan.getString("End"),
                                lan.getInt("MaxPeople"),
                                lan.getInt("Remaining_Places")));
                    }
                    mRecyclerView.setAdapter(mCardViewAdapter);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void refreshLans(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Lans");
        query.whereContains("IdOwner", ParseUser.getCurrentUser().getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for(ParseObject lan : objects){
                        lans.add(new Lan(lan.getObjectId(),
                                lan.getString("Date"),
                                lan.getString("Start"),
                                lan.getString("End"),
                                lan.getInt("MaxPeople"),
                                lan.getInt("Remaining_Places")));
                    }

                } else {
                    Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
