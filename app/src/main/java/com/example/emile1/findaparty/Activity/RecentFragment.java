package com.example.emile1.findaparty.Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.emile1.findaparty.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentFragment extends Fragment {
    private ArrayList<String> idList;
    private ListView mListView;
    private List<Lan> lans;

    public RecentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recent, container, false);
//        idList = new ArrayList<String>();
        getLans();
        mListView = (ListView) v.findViewById(R.id.listview_home);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("idList",idList);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            idList = savedInstanceState.getStringArrayList("idList");
        }
    }
    public void getLans(){
        lans = new ArrayList<Lan>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Lan");
        query.whereContains("IdOwner", ParseUser.getCurrentUser().getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for(ParseObject lan : objects){
                        lans.add(new Lan(lan.getString("Date"),
                                lan.getString("Start"),
                                lan.getString("End"),
                                lan.getInt("MaxPeople"),
                                lan.getInt("Remaining_Places")));

                    }
                    LanAdapter lanAdapter= new LanAdapter(getActivity(),lans);
                    mListView.setAdapter(lanAdapter);
                } else {
                    Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    private List<Lan> generateLans(){
//        lans.add(new Lan("20/12/2016","10:00","12:00",3,0));
//        lans.add(new Lan("20/12/2016","15:00","18:00",5,2));
//        lans.add(new Lan("10/08/2017","10:00","12:00",4,1));
//        lans.add(new Lan("03/04/2017","18:00","20:00",5,3));
//        return lans;
//    }
}
