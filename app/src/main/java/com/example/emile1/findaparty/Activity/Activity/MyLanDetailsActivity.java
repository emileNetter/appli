package com.example.emile1.findaparty.Activity.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.emile1.findaparty.Activity.Lan;
import com.example.emile1.findaparty.Activity.Participant;
import com.example.emile1.findaparty.Activity.adapter.ParticipantAdapter;
import com.example.emile1.findaparty.R;
import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyLanDetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    private Button btn_delete;
    Participant p1;
    Participant p2;
    CircleImageView c;
    List<Participant> participantList;
    ListView lv;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lan_details);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutList);
        participantList = genereParticipant();
        ParticipantAdapter participantAdapter = new ParticipantAdapter(this,participantList);
        for (int i =0;i<participantAdapter.getCount();i++){
            View view = participantAdapter.getView(i,null,linearLayout);
            linearLayout.addView(view);
        }
        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar_lanDetails);
        btn_delete = (Button) findViewById(R.id.button_delete);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent= getIntent();
        final Lan mLan = (Lan)intent.getSerializableExtra("Lan");
        final ParseObject lan = ParseObject.createWithoutData("Lans",mLan.getIdLan());

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyLanDetailsActivity.this);
                builder.setTitle(getString(R.string.delete_title));
                builder.setMessage(getString(R.string.delete_message));
                builder.setCancelable(true);
                builder.setPositiveButton(
                        android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                lan.deleteInBackground(new DeleteCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if(e==null){
                                            Toast.makeText(getApplicationContext(),getString(R.string.deleted_message),Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(),getString(R.string.error) + e,Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });

                builder.setNegativeButton(
                        android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert1 = builder.create();
                alert1.show();

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private List<Participant> genereParticipant(){
        List<Participant> list = new ArrayList<>();
        list.add(new Participant("Emile N",c));
        list.add(new Participant("Jean",c));
        list.add(new Participant("Paul p",c));
        return list;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, Toolbar.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
