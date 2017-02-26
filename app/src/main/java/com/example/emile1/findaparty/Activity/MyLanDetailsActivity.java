package com.example.emile1.findaparty.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.emile1.findaparty.R;
import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

public class MyLanDetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    private Button btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lan_details);
        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
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
                builder.setTitle("Delete");
                builder.setMessage("Do you really want to delete this event ?");
                builder.setCancelable(true);
                builder.setPositiveButton(
                        android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                lan.deleteInBackground(new DeleteCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if(e==null){
                                            Toast.makeText(getApplicationContext(),"Deleted lan " +mLan.getIdLan(),Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(),"Error " + e,Toast.LENGTH_SHORT).show();
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
}
