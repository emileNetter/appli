package com.example.emile1.findaparty.Activity.fragment;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emile1.findaparty.Activity.Activity.MyLanDetailsActivity;
import com.example.emile1.findaparty.Activity.Lan;
import com.example.emile1.findaparty.Activity.adapter.CardViewAdapter;
import com.example.emile1.findaparty.R;
import com.google.android.gms.vision.text.Text;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentFragment extends Fragment implements CardViewAdapter.OnCardClickListener{

    private List<Lan> lans;
    private ProgressBar progressBar;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private CardViewAdapter mCardViewAdapter;
    private ImageView amumuImage;
    private TextView noLan;


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
        lans = new ArrayList<>();
        mCardViewAdapter = new CardViewAdapter(lans);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recent, container, false);

        compareDates();
        amumuImage = (ImageView)v.findViewById(R.id.amumu_background);
        noLan = (TextView)v.findViewById(R.id.no_lan_textview);
        getLans();

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recent_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        progressBar.bringToFront();

        mCardViewAdapter.setOnCardClickListener(this);

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
        LocalDate date = new LocalDate();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Lans");
        query.whereContains("IdOwner", ParseUser.getCurrentUser().getObjectId());
        query.orderByAscending("Date");
        query.whereGreaterThanOrEqualTo("Date",date.toString("dd MMM yyyy"));
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size()!=0) {
                    noLan.setVisibility(View.INVISIBLE);
                    if(amumuImage.getVisibility()==View.VISIBLE){
                        amumuImage.setVisibility(View.INVISIBLE);
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    for(ParseObject lan : objects){
                        lans.add(new Lan(lan.getObjectId(),
                                lan.getString("IdOwner"),
                                lan.getString("Date"),
                                lan.getString("Start"),
                                lan.getString("End"),
                                lan.getInt("MaxPeople"),
                                lan.getInt("Number")));
                    }
                    mRecyclerView.setAdapter(mCardViewAdapter);
                } else if(objects.size()==0){
                    Log.i("Size =0","Yes");
                    progressBar.setVisibility(View.INVISIBLE);
                    amumuImage.setVisibility(View.VISIBLE);
                    noLan.setVisibility(View.VISIBLE);

                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    Log.i("error",e.getMessage());
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
                                lan.getString("IdOwner"),
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

    public void compareDates(){
        try{
            String sdate1 = "20 avr. 2017";
            String sdate2 = "22 avr. 2017";
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            Date date1 = sdf.parse(sdate1);
            Date date2 =sdf.parse(sdate2);
            if(date1.compareTo(date2)>0){
                Log.i("compare1","Date 1 after date2");
            }else if(date1.compareTo(date2)<0){
                Log.i("compare2","Date 1 before date 2");
            }
        }catch (java.text.ParseException e){
            Log.i("error",e.getMessage());
        }

    }

}
