package com.example.emile1.findaparty.Activity.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emile1.findaparty.Activity.Lan;
import com.example.emile1.findaparty.Activity.Participant;
import com.example.emile1.findaparty.Activity.adapter.ParticipantAdapter;
import com.example.emile1.findaparty.R;
import com.google.android.gms.vision.text.Text;
import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyLanDetailsActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private Button btn_delete;
    private CircleImageView c;
    private List<Participant> participantList;
    private LinearLayout linearLayout;
    private Window window;

    private TextView ownerName;
    private TextView ownerAddress;
    private TextView ownerCity;
    private TextView date;
    private TextView start;
    private TextView end;
    private CircleImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lan_details);
        instantiateUI();
        Intent intent= getIntent();
        final Lan mLan = (Lan)intent.getSerializableExtra("Lan");
        final ParseObject lan = ParseObject.createWithoutData("Lans",mLan.getIdLan());
        if(!mLan.getIdOwner().equals(ParseUser.getCurrentUser().getObjectId())){
            btn_delete.setBackground(getDrawable(R.drawable.btn_join_lan));
            btn_delete.setText(getString(R.string.join_lan));
            Log.i("Lan id",mLan.getIdLan());
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyLanDetailsActivity.this);
                    builder.setTitle(getString(R.string.join_title));
                    builder.setMessage(getString(R.string.join_message));
                    builder.setCancelable(true);
                    builder.setPositiveButton(
                            android.R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Lans");
                                    query.getInBackground(mLan.getIdLan(), new GetCallback<ParseObject>() {
                                        @Override
                                        public void done(ParseObject lan, ParseException e) {
                                            if(e==null){
                                                JSONObject object = new JSONObject();
                                                try{
                                                    object.put("PlayerName","John");
                                                    object.put("ID",514145);
                                                    lan.add("Participants",object.toString());
                                                }catch (JSONException error){

                                                }

                                                lan.increment("Number");
                                                lan.saveInBackground();
//                                                try {
//                                                    JSONArray jsonArray = new JSONArray();
//                                                    JSONObject jsonObject = new JSONObject();
//                                                    String firstName = ParseUser.getCurrentUser().getString("firstName");
//                                                    String lastName = ParseUser.getCurrentUser().getString("lastName");
//                                                    Log.i("firstname",firstName);
//                                                    String id = ParseUser.getCurrentUser().getObjectId();
//                                                    jsonObject.put("Name",firstName + lastName);
//                                                    jsonObject.put("Participant_ID",id);
//                                                    jsonArray.put(jsonObject);
//
//                                                }catch (JSONException er){
//                                                    Log.i("JSON ERROR",er.getMessage());
//
//                                                }
                                                Toast.makeText(getApplicationContext(),"JOINED",Toast.LENGTH_SHORT).show();
                                            }else{
                                                Log.i("Parse Error","error");
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
        } else {
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
        }
        setUIText(mLan);
        participantList = getParticipant();
        ParticipantAdapter participantAdapter = new ParticipantAdapter(this,participantList);
        for (int i =0;i<participantAdapter.getCount();i++){
            View view = participantAdapter.getView(i,null,linearLayout);
            linearLayout.addView(view);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private List<Participant> getParticipant(){
        //On récupère les participants d'une LAN
        List<Participant> list = new ArrayList<>();
        list.add(new Participant("Emile N",c));
        list.add(new Participant("Jean",c));
        list.add(new Participant("Paul p",c));
        return list;
    }

    private void instantiateUI(){
        ownerName =(TextView) findViewById(R.id.owner_name);
        ownerAddress = (TextView) findViewById(R.id.owner_address);
        ownerCity =(TextView) findViewById(R.id.owner_city);
        date = (TextView) findViewById(R.id.event_date);
        start = (TextView) findViewById(R.id.event_start);
        end = (TextView) findViewById(R.id.event_end);
        avatar = (CircleImageView) findViewById(R.id.owner_avatar);
        window = this.getWindow();
        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar_lanDetails);
        btn_delete = (Button) findViewById(R.id.button_delete);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.material_indigo_500));
        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutList);
    }

    private void setUIText(Lan lan){
        try{
            JSONObject address = ParseUser.getCurrentUser().getJSONObject("address");
            ownerName.setText(ParseUser.getCurrentUser().getString("firstName") + " " + ParseUser.getCurrentUser().getString("lastName"));
            ownerAddress.setText(address.getString("lane"));
            ownerCity.setText(address.getString("zipcode")+ " " + address.getString("city").toUpperCase());
            date.setText(lan.getDate());
            start.setText(lan.getStartTime());
            end.setText(lan.getEndTime());
        } catch (JSONException e){
            e.printStackTrace();
        }

    }
}
