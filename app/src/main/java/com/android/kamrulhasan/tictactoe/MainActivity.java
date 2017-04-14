package com.android.kamrulhasan.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout userInputLayout;
    private EditText firstPlayerName,secondPlayerName;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String player1Name = "player1";
    public static final String player2Name = "player2";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInputLayout = (RelativeLayout) findViewById(R.id.userInformation);
        firstPlayerName = (EditText) findViewById(R.id.firstPlayer);
        secondPlayerName = (EditText) findViewById(R.id.secondPlayer);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences !=null){
            firstPlayerName.setText(sharedPreferences.getString(player1Name,null));
            secondPlayerName.setText(sharedPreferences.getString(player2Name,null));
        }
    }

    public void play(View view){
        userInputLayout.setVisibility(View.VISIBLE);
    }

    public void go(View view){

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String firstPN = firstPlayerName.getText().toString();
        String secondPN = secondPlayerName.getText().toString();

        editor = sharedPreferences.edit();
        editor.putString(player1Name,firstPN);
        editor.putString(player2Name,secondPN);
        editor.commit();

        if (!firstPN.equals("") && !secondPN.equals("")){
            startActivity(new Intent(MainActivity.this,GameActivity.class).putExtra("fName",firstPN).putExtra("sName",secondPN));
            MainActivity.this.finish();
        }else {
            userInputLayout.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplication(),"Please enter two player name",Toast.LENGTH_SHORT).show();
        }
    }


}
