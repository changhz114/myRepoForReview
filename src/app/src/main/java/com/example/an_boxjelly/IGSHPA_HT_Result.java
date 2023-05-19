package com.example.an_boxjelly;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
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
import androidx.core.content.FileProvider;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class IGSHPA_HT_Result extends BaseActivity {
    // page for IGSHPA_HT result page
    private String rp, fh, fc, tsl, tsh, lhp, lcp;
    //private String groundThermalResistanceResult, boreholeShapeFactorResult, pipeWallThermalResistanceResult, groutThermalResistanceResult, boreholeThermalResistanceResult, heatingRunFractionResult, coolingRunFractionResult, heatingBoreholeLengthResult,coolingBoreholeLengthResult;
    private String minTrenchLength, numOfPipesPerTrench, numOfTrenches;

    @SuppressLint("DefaultLocale")
    /**
     * The onCreate method is called when the activity is starting.
     * It initializes the activity and sets up the user interface.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down, this Bundle contains
     *                           the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_igshpa_ht_result);
        Bundle bundle = this.getIntent().getExtras();
        rp = String.format("%.5f", Float.parseFloat(bundle.getString("pipeThermalResistanceResult")));
        fh = String.format("%.5f", Float.parseFloat(bundle.getString("heatingRunFractionResult")));
        fc = String.format("%.5f", Float.parseFloat(bundle.getString("coolingRunFractionResult")));
        // for temperature, 1 bit after the decimal is enough
        tsl = String.format("%.5f", Float.parseFloat(bundle.getString("designCoolingEarthTempResult")));
        tsh = String.format("%.5f", Float.parseFloat(bundle.getString("designHeatingEarthTempResult")));
        lhp = String.format("%.5f", Float.parseFloat(bundle.getString("heatingBoreholeLengthResult")));
        lcp = String.format("%.5f", Float.parseFloat(bundle.getString("coolingBoreholeLengthResult")));

        minTrenchLength = bundle.getString("MinimumTrenchLength");
        minTrenchLength = minTrenchLength.substring(0, minTrenchLength.length() - 2);
        numOfPipesPerTrench = bundle.getString("NumberofPipesPerTrench");
        numOfPipesPerTrench = numOfPipesPerTrench.substring(0, numOfPipesPerTrench.length() - 2);
        numOfTrenches = bundle.getString("NumberofTrenches");
        numOfTrenches = numOfTrenches.substring(0, numOfTrenches.length() - 2);

        TextView rp_t = findViewById(R.id.HT_result_RP_value);
        rp_t.setText(rp + "m ℃/W");
        TextView fh_t = findViewById(R.id.HT_result_FH_value);
        fh_t.setText(fh + "");
        TextView fc_t = findViewById(R.id.HT_result_FC_value);
        fc_t.setText(fc + "");
        TextView tsl_t = findViewById(R.id.HT_result_TSL_value);
        tsl_t.setText(tsl + " ℃");
        TextView tsh_t = findViewById(R.id.HT_result_TSH_value);
        tsh_t.setText(tsh + " ℃");
        TextView lhp_t = findViewById(R.id.HT_result_LHP_value);
        lhp_t.setText(lhp + "m");
        TextView lcp_t = findViewById(R.id.HT_result_LCP_value);
        lcp_t.setText(lcp + "m");

        TextView result_num_of_trenches_t = findViewById(R.id.HT_num_of_trenches_value);
        result_num_of_trenches_t.setText(numOfTrenches);
        TextView result_num_of_pipes_per_trench_t = findViewById(R.id.HT_num_of_pipes_in_trench_value);
        result_num_of_pipes_per_trench_t.setText(numOfPipesPerTrench);
        TextView min_boreholes_length_t = findViewById(R.id.HT_min_trench_length_value);
        min_boreholes_length_t.setText(minTrenchLength);

        TextView result_config_len_t = findViewById(R.id.HT_result_config);
        result_config_len_t.setText(minTrenchLength + "m  ×  " + numOfPipesPerTrench + " Pipes in " + numOfTrenches + " Trenches");
//        this.setTitle("IGSHPA HT Result");
        this.setTitle(getString(R.string.igshpa_ht_result));
    }

    /**
     * Saves the calculated results as a CSV file and stores it in the device's "Download" folder.
     *
     * @param view The view that triggers this method when clicked.
     */
    public void saveAndShareHT(View view) {
        // 22 result values
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"R(P), F(H), F(C), T(S.L), T(S.H), L(H.P), L(C.P), " +
                "Number of Trenches, Num of Pipes in each trench, Minimum Trench Length"});
        data.add(new String[]{"\n" + rp + "," + fh + "," + fc + "," + tsl + "," + tsh + "," + lhp +
                "," + lcp + "," + numOfTrenches + "," + numOfPipesPerTrench + "," + minTrenchLength});

        /*
        StringBuilder data_result =new StringBuilder();
        data_result.append("R(P), F(H), F(C), T(S.L), T(S.H), L(H.P), L(C.P), " +
                "Number of Trenches, Num of Pipes in each trench, Minimum Trench Length");
        data_result.append("\n" + rp +","+ fh+"," + fc+"," + tsl +","+ tsh+"," + lhp +
                ","+ lcp +"," + num_of_trenches+"," + num_of_pipes_per_trench+"," + min_trench_length);
        */


        CSVWriter writer;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
        String saveLocation = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/" +
                sdf.format(new Date()) + "data.csv");
        try {
            writer = new CSVWriter(new FileWriter(saveLocation));
            writer.writeAll(data); // data is adding to csv
            writer.close();

            Toast.makeText(IGSHPA_HT_Result.this, getString(R.string.save_csv_success), Toast.LENGTH_SHORT).show();
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