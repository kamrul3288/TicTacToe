package com.android.kamrulhasan.tictactoe;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {


    private int activePlayer = 1;
    private boolean gameIsActive = true;
    private int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    private int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private ImageView counter;
    private TextView winnerMessage,hintText;
    private LinearLayout layout;
    private GridLayout gridLayout;

    private Bundle extra;
    private TextView firstName,secondName;
    private String fN,sN;
    private static int gameControl = 1;
    private AlertDialog.Builder dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        winnerMessage = (TextView) findViewById(R.id.winnerMessage);
        layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        firstName = (TextView) findViewById(R.id.player1);
        secondName = (TextView) findViewById(R.id.player2);
        hintText = (TextView) findViewById(R.id.hintText);

        extra = getIntent().getExtras();
        if (extra != null){
            fN = extra.getString("fName");
            sN = extra.getString("sName");

            firstName.setText(fN);
            secondName.setText(sN);
        }
    }

    public void dropIn(View view) {

        counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameIsActive) {
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                hintText.setTextColor(Color.parseColor("#FF1744"));
                hintText.setText("It's time to turn RED holder player");
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;

            } else {
                hintText.setTextColor(Color.parseColor("#FFEB3B"));
                hintText.setText("It's time to turn YELLOW holder player");
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;

            }
            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);

            for (int[] winningPosition : winningPositions) {

                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2) {


                    gameIsActive = false;
                    String winner = fN;

                    if (gameState[winningPosition[0]] == 0) {
                        winner = sN;
                    }

                    winnerMessage.setText(winner + " has won!");
                    layout.setVisibility(View.VISIBLE);

                } else {

                    boolean gameIsOver = true;

                    for (int counterState : gameState) {
                        if (counterState == 2) gameIsOver = false;

                    }

                    if (gameIsOver) {

                        winnerMessage.setText("The Game Has Draw!!!");
                        layout.setVisibility(View.VISIBLE);

                    }

                }

            }
        }


    }


    public void playAgain(View view) {

        // 1 red 0 yellow

        if (gameControl==1){
            hintText.setText("It's time to turn YELLOW holder player");
            gameControl = 0;
            activePlayer = gameControl;
        }else {
            hintText.setText("It's time to turn RED holder player");
            gameControl = 1;
            activePlayer = gameControl;
        }


        gameIsActive = true;
        layout.setVisibility(View.INVISIBLE);

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;

        }


        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }

    }


    public void exit(View view){
        dialogBuilder = new AlertDialog.Builder(GameActivity.this);
        dialogBuilder.setTitle("EXIT!!")
                .setMessage("Are you want to exit???")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GameActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        Dialog dialog = dialogBuilder.create();
        dialog.show();
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Use exit icon for exit this game",Toast.LENGTH_SHORT).show();
    }
}
