package com.example.emile1.findaparty.Activity.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emile1.findaparty.Activity.Lan;
import com.example.emile1.findaparty.Activity.Participant;
import com.example.emile1.findaparty.R;
import com.parse.ParseObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LanDetailsActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_lan_details);
//        instantiateUI();
        Intent intent= getIntent();
        final Lan mLan = (Lan)intent.getSerializableExtra("Lan");
        final ParseObject lan = ParseObject.createWithoutData("Lans",mLan.getIdLan());
//        setUIText(mLan);
    }
}
