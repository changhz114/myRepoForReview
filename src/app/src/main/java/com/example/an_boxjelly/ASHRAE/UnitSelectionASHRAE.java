package com.example.an_boxjelly.ASHRAE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.an_boxjelly.BaseActivity;
import com.example.an_boxjelly.R;

public class UnitSelectionASHRAE extends BaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ashrae_unit_selection);
//        this.setTitle("ASHRAE unit selection");
        this.setTitle(getString(R.string.ashrae_unit_selection));//同上一句调用strings.xml中的内容
    }

    public void clickImperial(View view) {
        Intent intent = new Intent(this, DataInputImperialASHRAE.class);
        startActivity(intent);
    }

    public void clickInternational(View view) {
        Intent intent = new Intent(this, DataInputASHRAE.class);
        startActivity(intent);
    }


}
