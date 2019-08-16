package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {
    private boolean isPlayerRedTurn;
    private ImageView cells[];
    private ConstraintLayout announcementLayout;
    private boolean isGameOn;
    private int possibilities[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout mlayout = findViewById(R.id.gridl_panel);
        int count = mlayout.getChildCount();

        this.announcementLayout = findViewById(R.id.ly_winner);
        this.cells = new ImageView[count];
        this.isGameOn = true;
        this.isPlayerRedTurn = true;
        this.possibilities = new int[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        //To access the cells
        for(int i = 0 ; i <count ; i++){
            View child = mlayout.getChildAt(i);
            child.setAlpha(0.0f);
            cells[i] = (ImageView) child;
        }


    }

    public void clickCell(View view){
        if (!this.isGameOn)
            return;

        ImageView cell = (ImageView) view;
        String tag = cell.getTag().toString();

        if (tag.equals("Unplayed")){
            String team = isPlayerRedTurn ? "Red" : "Blue";
            int icon =  isPlayerRedTurn ? R.drawable.red : R.drawable.blue;

            cell.setTag(team);
            cell.setImageDrawable(getResources().getDrawable(icon));
            cell.animate().alpha(1.0f);

            if (didSomeoneWon(team))
                finishGame();

            isPlayerRedTurn = !isPlayerRedTurn;
        }
    }

    public void finishGame(){
        this.isGameOn = false;
        this.announcementLayout.setAlpha(0.0f);
        this.announcementLayout.setVisibility(View.VISIBLE);
        this.announcementLayout.animate().alpha(1.0f);

        String winnerMessage = isPlayerRedTurn ? "Reliable Excavation Demolition" : "Builders League United";
        winnerMessage += " have won";
        TextView txt = findViewById(R.id.txt_winner);
        txt.setText(winnerMessage);

    }

    public boolean didSomeoneWon(String team){
        task:
        for (int[] possibility : this.possibilities){

            for (int i = 0; i < 3; i++){
                if (!cells[possibility[i]].getTag().toString().equals(team)){
                    continue task;
                }
            }
            return true;
        }
        return false;
    }

    //public boolean

    public void startGame(View view){
        int count = cells.length;

        for (int i = 0; i < count; i++){
            ImageView cell = cells[i];
            cell.setTag("Unplayed");
            cell.setAlpha(0.0f);
        }
        ConstraintLayout olayout = findViewById(R.id.ly_winner);
        olayout.setAlpha(0.0f);
        olayout.setVisibility(View.GONE);

        this.isGameOn = true;
        this.isPlayerRedTurn = true;
    }
}
