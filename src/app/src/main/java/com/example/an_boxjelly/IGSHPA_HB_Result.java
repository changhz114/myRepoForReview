package com.example.an_boxjelly;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.widget.Toast;

import com.opencsv.CSVWriter;

public class IGSHPA_HB_Result extends BaseActivity {
    // page for IGSHPA_HB result page
    private String rg, sb, rpp, rgrout, rb, fh, fc, tsl, tsh, lht, lct;
    //private String groundThermalResistanceResult, boreholeShapeFactorResult, pipeWallThermalResistanceResult, groutThermalResistanceResult, boreholeThermalResistanceResult, heatingRunFractionResult, coolingRunFractionResult, heatingBoreholeLengthResult,coolingBoreholeLengthResult;
    private String numOfBoreholes,minBoreholeLength;
    @Override
    @SuppressLint("DefaultLocale")

    /**
     * onCreate method is called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_igshpa_hb_result);
        Bundle bundle = this.getIntent().getExtras();
        rg = String.format("%.5f", Float.parseFloat(bundle.getString("groundThermalResistanceResult")));
        sb = String.format("%.5f", Float.parseFloat(bundle.getString("boreholeShapeFactorResult")));
        rpp = String.format("%.5f", Float.parseFloat(bundle.getString("pipeWallThermalResistanceResult")));
        rgrout = String.format("%.5f", Float.parseFloat(bundle.getString("groutThermalResistanceResult")));
        rb = String.format("%.5f", Float.parseFloat(bundle.getString("boreholeThermalResistanceResult")));
        fh = String.format("%.5f", Float.parseFloat(bundle.getString("heatingRunFractionResult")));
        fc = String.format("%.5f", Float.parseFloat(bundle.getString("coolingRunFractionResult")));
        // for temperature, 1 bit after the decimal is enough
        tsl = String.format("%.2f", Float.parseFloat(bundle.getString("designHeatingEarthTempResult")));
        tsh = String.format("%.2f", Float.parseFloat(bundle.getString("designCoolingEarthTempResult")));
        lht = String.format("%.5f", Float.parseFloat(bundle.getString("heatingBoreholeLengthResult")));
        lct = String.format("%.5f", Float.parseFloat(bundle.getString("coolingBoreholeLengthResult")));

        numOfBoreholes = bundle.getString("NumberofHoles");
        numOfBoreholes = numOfBoreholes.substring(0,numOfBoreholes.length()-2);
        minBoreholeLength = bundle.getString("MinimumBoreholeLength");
        minBoreholeLength = minBoreholeLength.substring(0,minBoreholeLength.length()-2);

        TextView rg_t = findViewById(R.id.HB_result_RG_value);
        rg_t.setText(rg + "m ℃/W");
        TextView sb_t = findViewById(R.id.HB_result_SB_value);
        sb_t.setText(sb + "");
        TextView rpp_t = findViewById(R.id.HB_result_RPP_value);
        rpp_t.setText(rpp + "m ℃/W");
        TextView rgrout_t = findViewById(R.id.HB_result_RGrout_value);
        rgrout_t.setText(rgrout + "m ℃/W");
        TextView rb_t = findViewById(R.id.HB_result_RB_value);
        rb_t.setText(rb + "m ℃/W");
        TextView fh_t = findViewById(R.id.HB_result_FH_value);
        fh_t.setText(fh + "");
        TextView fc_t = findViewById(R.id.HB_result_FC_value);
        fc_t.setText(fc + "");
        TextView tsl_t = findViewById(R.id.HB_result_TSL_value);
        tsl_t.setText(tsl + " ℃");
        TextView tsh_t = findViewById(R.id.HB_result_TSH_value);
        tsh_t.setText(tsh + " ℃");
        TextView lht_t = findViewById(R.id.HB_result_LHT_value);
        lht_t.setText(lht + "m");
        TextView lct_t = findViewById(R.id.HB_result_LCT_value);
        lct_t.setText(lct + "m");
        TextView result_numOfBoreholes_t = findViewById(R.id.HB_numOfBoreholes_value);
        result_numOfBoreholes_t.setText(numOfBoreholes);
        TextView min_boreholes_length_t = findViewById(R.id.HB_minBoreholeLength_value);
        min_boreholes_length_t.setText(minBoreholeLength);
        TextView result_config_len_t = findViewById(R.id.HB_result_config);
        result_config_len_t.setText(minBoreholeLength + "m  ×  " +  numOfBoreholes + " boreholes");
//        this.setTitle("IGSHPA HB Result");
        this.setTitle(getString(R.string.igshpa_hb_result));

    }

    /**
     * Save and share the results as a CSV file.
     *
     * @param view The current view.
     */
    public void saveAndShareHB(View view){
        // 22 result values


        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"R(G), S(B), R(PP), R(Grout), R(B), " +
                "F(H), F(C), T(S.L), T(S.H), L(H.T), L(C.T), Number of Boreholes, Minimum Borehole Length"});
        data.add(new String[]{"\n" + rg + ","+ sb + ","+ rpp + ","+ rgrout + ","+ rb + "," + fh +"," + fc
                + "," + tsl + "," + tsh + "," + lht + "," + lct + ","+ numOfBoreholes + "," + minBoreholeLength});

        /*
        StringBuilder data_result =new StringBuilder();
        data_result.append("R(G), S(B), R(PP), R(Grout), R(B), " +
                "F(H), F(C), T(S.L), T(S.H), L(H.T), L(C.T), Number of Boreholes, Minimum Borehole Length");
        data_result.append("\n" + rg + ","+ sb + ","+ rpp + ","+ rgrout + ","+ rb + "," + fh +"," + fc
                + "," + tsl + "," + tsh + "," + lht + "," + lct + ","+ numOfBoreholes + "," + minBoreholeLength);

         */

        CSVWriter writer;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
        String saveLocation = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/" +
                sdf.format(new Date()) + "data.csv");
        try {
            writer = new CSVWriter(new FileWriter(saveLocation));
            writer.writeAll(data); // data is adding to csv
            writer.close();

            Toast.makeText(IGSHPA_HB_Result.this, getString(R.string.save_csv_success), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*
        try{ // save file into devices
            FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
            out.write((data_result.toString()).getBytes());
            out.close();
            //exporting
            Context context = getApplicationContext();
            File filelocation = new File(getFilesDir(),"data.csv");
            Uri path = FileProvider.getUriForFile(context,"com.example.generatecsvfiles.fileprovider", filelocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT,"Data"); //send as email, subject is our data
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            fileIntent.putExtra(Intent.EXTRA_CC, new String[]{"info@4ee.com.au"});


            Intent chooser = Intent.createChooser(fileIntent, "Send email");
            List<ResolveInfo> resInfoList = this.getPackageManager().queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY);

            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                this.grantUriPermission(packageName, path, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

            startActivity(chooser);

        }catch (Exception e){
            e.printStackTrace();
        }


         */

    }

}
