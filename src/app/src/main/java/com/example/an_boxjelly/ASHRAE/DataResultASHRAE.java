package com.example.an_boxjelly.ASHRAE;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.an_boxjelly.BaseActivity;
import com.example.an_boxjelly.MainActivity;
import com.example.an_boxjelly.R;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataResultASHRAE extends BaseActivity {
    // page for IGSHPA_HB result page
    private String NB, result, f10y, f1m, f6h, innerPipeConvectionResistance, pipeConvectionResistance, groutResistance, boreholeResistance, correlationFunc, dimensionlessT, outletWaterTempCooling, outletWaterTempHeating;
    private String meanWaterTempCooling, meanWaterTempHeating, tempPenaltyCooling, tempPenaltyHeating, undisturbedCoolingLength, undisturbedHeatingLength, totalCoolingBoreholeLength, totalHeatingBoreholeLength;
    @Override
    @SuppressLint("DefaultLocale")
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ashrae_data_result);
//        Bundle bundle = this.getIntent().getExtras();
//        f10y = String.format("%.5f", Float.parseFloat(bundle.getString("tenyearResistance")));//R(10y)
//        f1m = String.format("%.5f", Float.parseFloat(bundle.getString("onemonthResistance")));//R(1m)
//        f6h = String.format("%.5f", Float.parseFloat(bundle.getString("sixhourResistance")));//R(6h)
//        innerPipeConvectionResistance = String.format("%.5f", Float.parseFloat(bundle.getString("innerPipeConvectionResistance")));//R(conv)
//        pipeConvectionResistance = String.format("%.5f", Float.parseFloat(bundle.getString("pipeConvectionResistance")));//R(p)
//        groutResistance = String.format("%.5f", Float.parseFloat(bundle.getString("groutResistance")));//R(grout)
//        boreholeResistance = String.format("%.5f", Float.parseFloat(bundle.getString("boreholeResistance")));//R(b)
//        correlationFunc = String.format("%.5f", Float.parseFloat(bundle.getString("correlationFunc")));//F
//        dimensionlessT = String.format("%.5f", Float.parseFloat(bundle.getString("dimensionlessTime")));//ln(t/ts)
//        outletWaterTempCooling = String.format("%.5f",Float.parseFloat(bundle.getString("outletWaterTempCooling")));//T(outCHP)
//        outletWaterTempHeating = String.format("%.5f",Float.parseFloat(bundle.getString("outletWaterTempHeating")));//T(outHHP)
//        meanWaterTempCooling = String.format("%.5f", Float.parseFloat(bundle.getString("meanWaterTempCooling")));//T(mc)
//        meanWaterTempHeating = String.format("%.5f",Float.parseFloat(bundle.getString("meanWaterTempHeating")));//T(mh)
//        tempPenaltyCooling = String.format("%.5f", Float.parseFloat(bundle.getString("tempPenaltyCooling")));//T(pc)
//        tempPenaltyHeating = String.format("%.5f", Float.parseFloat(bundle.getString("tempPenaltyHeating")));//T(ph)
//        undisturbedCoolingLength = String.format("%.5f", Float.parseFloat(bundle.getString("undisturbedCoolingLength")));//L(CU)
//        undisturbedHeatingLength = String.format("%.5f", Float.parseFloat(bundle.getString("undisturbedHeatingLength")));//L(HU)
//        totalCoolingBoreholeLength = String.format("%.5f", Float.parseFloat(bundle.getString("totalCoolingBoreholeLength")));//L(CT)
//        totalHeatingBoreholeLength = String.format("%.5f", Float.parseFloat(bundle.getString("totalHeatingBoreholeLength")));//L(HT)
//        NB = bundle.getString("NumberofHoles");//Number of boreholes
//        NB = NB.substring(0,NB.length()-2);
//        result = bundle.getString("MinimumBoreholeLength");//minimum borehore length
//        //The last line is result * NB
//        result = result.substring(0,result.length()-2);
//        TextView r10y = findViewById(R.id.R10yResult);
//        r10y.setText(f10y + "m k/W");
//        TextView r1m = findViewById(R.id.R1mResult);
//        r1m.setText(f1m + "m k/W");
//        TextView r6h = findViewById(R.id.R6hResult);
//        r6h.setText(f6h + "m k/W");
//        TextView innerpipe = findViewById(R.id.RconvResult);
//        innerpipe.setText(innerPipeConvectionResistance + "m k/W");
//        TextView pipeConv = findViewById(R.id.RpResult);
//        pipeConv.setText(pipeConvectionResistance + "m k/W");
//        TextView groutResis = findViewById(R.id.groutResult);
//        groutResis.setText(groutResistance + "m k/W");
//        TextView boreholeRe = findViewById(R.id.RbResult);
//        boreholeRe.setText(boreholeResistance + "m k/W");
//        TextView correlationF = findViewById(R.id.FResult);
//        correlationF.setText(correlationFunc);
//        TextView dimensionlessTview = findViewById(R.id.LnResult);
//        dimensionlessTview.setText(dimensionlessT);
//        TextView outletWaterCooling = findViewById(R.id.ToutCHPResult);
//        outletWaterCooling.setText(outletWaterTempCooling + "℃");
//        TextView outletWaterHeating = findViewById(R.id.ToutHHPResult);
//        outletWaterHeating.setText(outletWaterTempHeating + "℃");
//        TextView meanWaterCooling = findViewById(R.id.TmcResult);
//        meanWaterCooling.setText(meanWaterTempCooling + "℃");
//        TextView meanWaterHeating = findViewById(R.id.TmhResult);
//        meanWaterHeating.setText(meanWaterTempHeating + "℃");
//        TextView tempPCooling = findViewById(R.id.TpcResult);
//        tempPCooling.setText(tempPenaltyCooling + "℃");
//        TextView tempPHeating = findViewById(R.id.TphResult);
//        tempPHeating.setText(tempPenaltyHeating + "℃");
//        TextView undisturbedCooling = findViewById(R.id.LCUResult);
//        undisturbedCooling.setText(undisturbedCoolingLength + "km");
//        TextView undisturbedHeating = findViewById(R.id.LHUResult);
//        undisturbedHeating.setText(undisturbedHeatingLength + "km");
//        TextView totalCooling = findViewById(R.id.LCTResult);
//        totalCooling.setText(totalCoolingBoreholeLength + "km");
//        TextView totalHeating = findViewById(R.id.LHTResult);
//        totalHeating.setText(totalHeatingBoreholeLength + "km");
//        TextView NBR = findViewById(R.id.NBResult);
//        NBR.setText(NB);
//        TextView ind = findViewById(R.id.indblen);
//        ind.setText("Individual borehole length: " + result + "m");
//        TextView fin1 = findViewById(R.id.lengthResult);
//        fin1.setText(result +"m  ×  " +  NB + " boreholes");
//        this.setTitle("ASHRAE Result");
//    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ashrae_data_result);

        Bundle bundle = this.getIntent().getExtras();

        String[] keys = {"tenyearResistance", "onemonthResistance", "sixhourResistance", "innerPipeConvectionResistance",
                "pipeConvectionResistance", "groutResistance", "boreholeResistance", "correlationFunc",
                "dimensionlessTime", "outletWaterTempCooling", "outletWaterTempHeating", "meanWaterTempCooling",
                "meanWaterTempHeating", "tempPenaltyCooling", "tempPenaltyHeating", "undisturbedCoolingLength",
                "undisturbedHeatingLength", "totalCoolingBoreholeLength", "totalHeatingBoreholeLength"};

        int[] textViewsIds = {R.id.R10yResult, R.id.R1mResult, R.id.R6hResult, R.id.RconvResult,
                R.id.RpResult, R.id.groutResult, R.id.RbResult, R.id.FResult,
                R.id.LnResult, R.id.ToutCHPResult, R.id.ToutHHPResult, R.id.TmcResult,
                R.id.TmhResult, R.id.TpcResult, R.id.TphResult, R.id.LCUResult,
                R.id.LHUResult, R.id.LCTResult, R.id.LHTResult};

        for (int i = 0; i < keys.length; i++) {
            String formattedValue = String.format("%.5f", Float.parseFloat(bundle.getString(keys[i])));
            TextView textView = findViewById(textViewsIds[i]);
            textView.setText(formattedValue + (i < 7 ? "m k/W" : i < 9 ? "" : i < 15 ? "℃" : "km"));
        }

        String NB = bundle.getString("NumberofHoles");
        NB = NB.substring(0, NB.length() - 2);

        String result = bundle.getString("MinimumBoreholeLength");
        result = result.substring(0, result.length() - 2);

        TextView NBR = findViewById(R.id.NBResult);
        NBR.setText(NB);

        TextView ind = findViewById(R.id.NBINResult);
        ind.setText(result + "m");

        TextView fin1 = findViewById(R.id.lengthResult);
        fin1.setText(result + "m  ×  " + NB + " boreholes");
//      this.setTitle("ASHRAE Result");
        this.setTitle(getResources().getString(R.string.ashrae_result));
    }

    public void save_share_button(View view) {
        // 22 result values

        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"R(10y), R(1M), R(6H), R(CONV), R(P), " +
                "R(grout), R(b), F, ln(t/ts), T(outCHP), T(outHHP), T(mc), T(mh), T(pc), T(ph), L(CU), " +
                "L(HU), L(CT), L(HT), Number of Boreholes, Minimum Borehole Length"});
        data.add(new String[]{"\n" + f10y + "," + f1m + "," + f6h + "," + innerPipeConvectionResistance + "," + pipeConvectionResistance + "," + groutResistance + "," + boreholeResistance
                + "," + correlationFunc + "," + dimensionlessT + "," + outletWaterTempCooling + "," + outletWaterTempHeating + "," + meanWaterTempCooling + "," + meanWaterTempHeating + "," + tempPenaltyCooling + ","
                + tempPenaltyHeating + "," + undisturbedCoolingLength + "," + undisturbedHeatingLength + "," + totalCoolingBoreholeLength + "," + totalHeatingBoreholeLength + ","
                + NB + "," + result});
        /*
        StringBuilder data_result =new StringBuilder();
        data_result.append("R(10y), R(1M), R(6H), R(CONV), R(P), " +
                "R(grout), R(b), F, ln(t/ts), T(outCHP), T(outHHP), T(mc), T(mh), T(pc), T(ph), L(CU), " +
                "L(HU), L(CT), L(HT), Number of Boreholes, Minimum Borehole Length");
        data_result.append("\n" + f10y + ","+ f1m + ","+ f6h + ","+ innerPipeConvectionResistance + ","+ pipeConvectionResistance + "," + groutResistance +"," + boreholeResistance
                + "," + correlationFunc + "," + dimensionlessT + "," + outletWaterTempCooling + "," + outletWaterTempHeating + ","+ meanWaterTempCooling + "," + meanWaterTempHeating + ","+ tempPenaltyCooling + ","
                + tempPenaltyHeating + "," + undisturbedCoolingLength + "," + undisturbedHeatingLength + "," +totalCoolingBoreholeLength + "," + totalHeatingBoreholeLength+ ","
                + NB + "," + result);
        */

        CSVWriter writer;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
        String saveLocation = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/" +
                sdf.format(new Date()) + "data.csv");
        try {
            writer = new CSVWriter(new FileWriter(saveLocation));
            writer.writeAll(data); // data is adding to csv
            writer.close();

            Toast.makeText(DataResultASHRAE.this, getString(R.string.save_csv_success), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
