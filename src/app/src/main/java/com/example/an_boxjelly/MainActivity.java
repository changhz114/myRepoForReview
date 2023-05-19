package com.example.an_boxjelly;

import androidx.appcompat.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.example.an_boxjelly.ASHRAE.UnitSelectionASHRAE;


public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //this class related activity_main file
//        this.setTitle("Design Method");
        this.setTitle(getString(R.string.design_method));

        // For term and conditions dialog.
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean accept = sharedPreferences.getBoolean(getString(R.string.accept), false);

        // If users run this application for first time and not accept our term and conditions,
        // then show the term and conditions dialog.
        if(!accept){
            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.term_and_condition_layout,null); //什么玩意
            TextView term_and_conditions = (TextView)view.findViewById(R.id.textView_term_and_conditions);
            term_and_conditions.setText(R.string.Term_and_Conditions);
            AlertDialog.Builder Term_and_Condition = new AlertDialog.Builder(MainActivity.this);
            Term_and_Condition.setTitle(getString(R.string.term_and_condition_title))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.accept), (dialog, i) -> {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(getString(R.string.accept), true);
                        editor.apply();
                        dialog.cancel();
                    })
                    .setNegativeButton(getString(R.string.decline), (dialogInterface, i) -> {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    })
                    .setView(view);
            AlertDialog term_dialog = Term_and_Condition.create();
            term_dialog.show();
        }

    }
    // Information view
    public void ASHRAEInfo(View view){
        AlertDialog.Builder ASHRAE_info = new AlertDialog.Builder(MainActivity.this);
        ASHRAE_info.setTitle("ASHRAE"+getString(R.string.information))
                .setMessage(getString(R.string.ASHRAE_info))
                .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> {});
        AlertDialog ASHRAE_dialog = ASHRAE_info.create();
        ASHRAE_dialog.show();
    }
    public void IGSHPAInfo(View view){
        AlertDialog.Builder IGSHPA_info = new AlertDialog.Builder(MainActivity.this);
        IGSHPA_info.setTitle("IGSHPA"+getString(R.string.information))
                .setMessage(getString(R.string.IGSHPA_info))
                .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> {});
        AlertDialog IGSHPA_dialog = IGSHPA_info.create();
        IGSHPA_dialog.show();
    }
    // executed when user click button IGSHPA, and jump to IGSHA page
    public void clickIGSHPA(View view) {
        Intent intent = new Intent(this, IGSHPA_System_Setting.class);
        startActivity(intent);
    }
    // executed when user click button ASHRAE, and jump to AHSRAE page
    public void clickASHRAE(View view){
        Intent intent = new Intent(this, UnitSelectionASHRAE.class);
        startActivity(intent);
    }
}