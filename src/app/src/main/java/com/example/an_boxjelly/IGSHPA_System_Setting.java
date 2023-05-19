package com.example.an_boxjelly;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class IGSHPA_System_Setting extends BaseActivity {
    // page for IGSHPA
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_igshpa_system_setting);
        this.setTitle(getString(R.string.igshpa_method));
    }

    public void clickVB (View view) { // executed when user click button IGSHPA, and jump to IGSHA page
        Intent intent = new Intent(this, IGSHPA_VB_Data_Input.class);
        startActivity(intent);
    }

    public void clickHB(View view) {
        Intent intent = new Intent(this, IGSHPA_HB_Data_Input.class);
        startActivity(intent);
    }

    public void clickHT(View view) {
        Intent intent = new Intent(this, IGSHPA_HT_Data_Input.class);
        startActivity(intent);
    }

    public void clickVBIU (View view) { // executed when user click button IGSHPA, and jump to IGSHA page
        Intent intent = new Intent(this, IGSHPA_VB_Data_Input_imperial.class);
        startActivity(intent);
    }

    public void clickHBIU(View view) {
        Intent intent = new Intent(this, IGSHPA_HB_Data_Input_imperial.class);
        startActivity(intent);
    }

    public void clickHTIU(View view) {
        Intent intent = new Intent(this, IGSHPA_HT_Data_Input_imperial.class);
        startActivity(intent);
    }

    public void clickIGSHPAVBIN(View view) {
        AlertDialog.Builder IGSHPA_info = new AlertDialog.Builder(IGSHPA_System_Setting.this);
        IGSHPA_info.setTitle(getString(R.string.information))
                .setMessage(R.string.IGSHPAVB_info)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog IGSHPA_dialog = IGSHPA_info.create();
        IGSHPA_dialog.show();
    }

    public void clickIGSHPAHBIN(View view) {
        AlertDialog.Builder IGSHPA_info = new AlertDialog.Builder(IGSHPA_System_Setting.this);
        IGSHPA_info.setTitle(getString(R.string.information))
                .setMessage(R.string.IGSHPAHB_info)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog IGSHPA_dialog = IGSHPA_info.create();
        IGSHPA_dialog.show();
    }

    public void clickIGSHPAHRIN(View view) {
        AlertDialog.Builder IGSHPA_info = new AlertDialog.Builder(IGSHPA_System_Setting.this);
        IGSHPA_info.setTitle(getString(R.string.information))
                .setMessage(R.string.IGSHPAHT_info)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog IGSHPA_dialog = IGSHPA_info.create();
        IGSHPA_dialog.show();
    }
}