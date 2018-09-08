package com.example.hp.passcode1.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.passcode1.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.hp.passcode1.activities.DbHandler.KEY_DAYS;
import static com.example.hp.passcode1.activities.DbHandler.KEY_END_TIME;
import static com.example.hp.passcode1.activities.DbHandler.KEY_PASSCODE_NAME;
import static com.example.hp.passcode1.activities.DbHandler.KEY_PASSCODE_TYPE;
import static com.example.hp.passcode1.activities.DbHandler.KEY_START_TIME;
import static com.example.hp.passcode1.activities.DbHandler.TABLE_PASSCODE;

public class MainActivity extends AppCompatActivity {
    Button btnAdd;
    RecyclerView recyclerView;
    PassCodeAdapter adapter;
    ArrayList<PassCode> passCodeList;

    RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.button_addNew);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewPCodeActivity.class);
                startActivity(intent);
            }
        });

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(MainActivity.this, "Position " + position, Toast.LENGTH_SHORT).show();
                showMessage();
//                StringBuffer buffer = new StringBuffer();
//                buffer.append("edit" + "\n");
//                buffer.append("delete" + "\n");
//                buffer.append("share" + "\n");
//                showMessage(null, buffer.toString());

            }

        };

        retrive();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PassCodeAdapter adapter = new PassCodeAdapter(this, passCodeList, listener);
        recyclerView.setAdapter(adapter);
        Intent intent1 = getIntent();

    }



    private void retrive() {
        DbHandler dbHandler = new DbHandler(this);
        dbHandler.getReadableDatabase();
        passCodeList = new ArrayList<>();
        passCodeList = dbHandler.getallPascode();


    }

    public void showMessage() {

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dailog);

        TextView edit = (TextView) dialog.findViewById(R.id.edit_query);
        TextView delete = (TextView) dialog.findViewById(R.id.delete_query);
        TextView share = (TextView) dialog.findViewById(R.id.share);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "" + passCodeList.get(0));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        dialog.setCancelable(true);
        Window window=dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setCancelable(true);
//        builder.setMessage(msg);
//        builder.show();
//    }

    }
}


