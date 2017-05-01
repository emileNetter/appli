package com.example.emile1.findaparty.Activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.emile1.findaparty.Activity.Activity.MyLanDetailsActivity;
import com.example.emile1.findaparty.Activity.Lan;
import com.example.emile1.findaparty.Activity.adapter.CardViewAdapter;
import com.example.emile1.findaparty.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArchiveFragment extends Fragment implements CardViewAdapter.OnCardClickListener{

    private List<Lan> lans;
    private ProgressBar progressBar;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private CardViewAdapter mCardViewAdapter;
    LocalDate localDate;
    String todayDate;

    public ArchiveFragment() {
        // Required empty public constructor
    }

    public static ArchiveFragment newInstance() {
        ArchiveFragment fragment = new ArchiveFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lans = new ArrayList<>();
        mCardViewAdapter = new CardViewAdapter(lans);
        localDate = new LocalDate();
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd MMM yyyy");
        todayDate = dtf.print(localDate);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_archive, container, false);
        getLans();
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.archive_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar2);
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

    //retrieve all the user's Lans and display it in a listview using a custom adapter
    public void getLans(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        Date date = cal.getTime();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Lans");
        query.whereContains("IdOwner", ParseUser.getCurrentUser().getObjectId());
        query.whereLessThan("Date",date);
        query.orderByDescending("Date");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    progressBar.setVisibility(View.INVISIBLE);
                    for(ParseObject lan : objects){
                        lans.add(new Lan(lan.getObjectId(),
                                lan.getString("IdOwner"),
                                lan.getDate("Date"),
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
}
