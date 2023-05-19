package com.example.an_boxjelly;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.an_boxjelly.utils.PermissionManager;

// For output data
import com.opencsv.CSVWriter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.view.Gravity;
import android.view.MotionEvent;

import androidx.core.content.FileProvider;

import java.io.File;
import java.util.Objects;

public class IGSHPA_VB_Result extends BaseActivity{
    // page for IGSHPA_VB result page
    private String rg, sb, rpp, rgrout, rb, fh, fc, lht, lct;
    //private String groundThermalResistanceResult, boreholeShapeFactorResult, pipeWallThermalResistanceResult, groutThermalResistanceResult, boreholeThermalResistanceResult, heatingRunFractionResult, coolingRunFractionResult, heatingBoreholeLengthResult,coolingBoreholeLengthResult;
    private String numOfBoreholes,minBoreholeLength;
    @Override
    @SuppressLint("DefaultLocale")
    /**
     * Initializes the result view and sets the calculated values received from the previous activity.
     * The results are displayed in the respective TextViews on the screen.
     *
     * @param savedInstanceState Bundle object containing the saved instance state.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_igshpa_vb_result);
        Bundle bundle = this.getIntent().getExtras();
        rg = String.format("%.5f", Float.parseFloat(bundle.getString("groundThermalResistanceResult")));
        sb = String.format("%.5f", Float.parseFloat(bundle.getString("boreholeShapeFactorResult")));
        rpp = String.format("%.5f", Float.parseFloat(bundle.getString("pipeWallThermalResistanceResult")));
        rgrout = String.format("%.5f", Float.parseFloat(bundle.getString("groutThermalResistanceResult")));
        rb = String.format("%.5f", Float.parseFloat(bundle.getString("boreholeThermalResistanceResult")));
        fh = String.format("%.5f", Float.parseFloat(bundle.getString("heatingRunFractionResult")));
        fc = String.format("%.5f", Float.parseFloat(bundle.getString("coolingRunFractionResult")));
        lht = String.format("%.5f", Float.parseFloat(bundle.getString("heatingBoreholeLengthResult")));
        lct = String.format("%.5f", Float.parseFloat(bundle.getString("coolingBoreholeLengthResult")));

        numOfBoreholes = bundle.getString("NumberofHoles");
        numOfBoreholes = numOfBoreholes.substring(0,numOfBoreholes.length()-2);
        minBoreholeLength = bundle.getString("MinimumBoreholeLength");
        minBoreholeLength = minBoreholeLength.substring(0,minBoreholeLength.length()-2);

        TextView rg_t = findViewById(R.id.VB_result_RG_value);
        rg_t.setText(rg + "m ℃/W");
        TextView sb_t = findViewById(R.id.VB_result_SB_value);
        sb_t.setText(sb + "");
        TextView rpp_t = findViewById(R.id.VB_result_RPP_value);
        rpp_t.setText(rpp + "m ℃/W");
        TextView rgrout_t = findViewById(R.id.VB_result_RGrout_value);
        rgrout_t.setText(rgrout + "m ℃/W");
        TextView rb_t = findViewById(R.id.VB_result_RB_value);
        rb_t.setText(rb + "m ℃/W");
        TextView fh_t = findViewById(R.id.VB_result_FH_value);
        fh_t.setText(fh + "");
        TextView fc_t = findViewById(R.id.VB_result_FC_value);
        fc_t.setText(fc + "");
        TextView lht_t = findViewById(R.id.VB_result_LHT_value);
        lht_t.setText(lht + "m");
        TextView lct_t = findViewById(R.id.VB_result_LCT_value);
        lct_t.setText(lct + "m");
        TextView result_numOfBoreholes_t = findViewById(R.id.VB_num_of_borehols_value);
        result_numOfBoreholes_t.setText(numOfBoreholes);
        TextView min_boreholes_length_t = findViewById(R.id.VB_minBoreholeLength_value);
        min_boreholes_length_t.setText(minBoreholeLength);
        TextView result_config_len_t = findViewById(R.id.VB_result_config);
        result_config_len_t.setText(minBoreholeLength + "m  ×  " +  numOfBoreholes + " boreholes");
//        this.setTitle("IGSHPA VB Result");
        this.setTitle(getString(R.string.igshpa_vb_result));


        layout_top = findViewById(R.id.wholeScreen);
    }


    private View popupSave;
    public TextView mainTitle;
    public Button PNGButton, CSVButton, EmailButton;
    private Boolean isSaveImage; // Don't know what it is




    /**
     * Popup a save method selection window
     * @param view the "save" button
     */
    @SuppressLint("ClickableViewAccessibility")
    public void saveAndSharePopup(View view){

        // inflate the layout of the popup window
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupSave = layoutInflater.inflate(R.layout.popup_save, null);

        // create the popup window
        //int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        //int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupSave,
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupWindow.setOutsideTouchable(true);

        // dismiss the popup window when touched
        /*
        popupSave.setOnTouchListener((v, event) -> {
            popupWindow.dismiss();
            return true;
        });
        */
        //Set grey screen
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);

        popupSave.setOnTouchListener((listenView, motionEvent) -> {
            int height = popupSave.findViewById(R.id.pop_layout).getTop();
            int y = (int) motionEvent.getY();
            System.out.println(height);
            System.out.println(y);
            if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                if (y < height){
                    WindowManager.LayoutParams lp1 = getWindow().getAttributes();
                    lp1.alpha = 1f;
                    getWindow().setAttributes(lp1);
                    popupWindow.dismiss();
                }
            }
            return true;
        });

        mainTitle = popupSave.findViewById(R.id.main_title);

        PNGButton = popupSave.findViewById(R.id.download_method_1);
        PNGButton.setOnClickListener(v -> {
            if (!PermissionManager.Query(IGSHPA_VB_Result.this, PermissionManager.STORAGE)) {
                PermissionManager.Granted(IGSHPA_VB_Result.this, PermissionManager.STORAGE, 1);
                return;
            } else {
                saveImage();
            }
        });

        CSVButton = popupSave.findViewById(R.id.download_method_2);
        CSVButton.setOnClickListener(v -> {
            if (!PermissionManager.Query(IGSHPA_VB_Result.this, PermissionManager.STORAGE)) {
                PermissionManager.Granted(IGSHPA_VB_Result.this, PermissionManager.STORAGE, 1);
                return;
            } else {
                saveCSV();
            }
        });


        EmailButton = popupSave.findViewById(R.id.download_method_3);
        EmailButton.setOnClickListener(v -> {
            if (!PermissionManager.Query(IGSHPA_VB_Result.this, PermissionManager.STORAGE)) {
                PermissionManager.Granted(IGSHPA_VB_Result.this, PermissionManager.STORAGE, 1);
                return;
            } else {
                sendEmail();
            }
        });
    }


    /**
     * Saves the calculated results as a CSV file on the device's storage and provides the option to
     * share the CSV file via email or other supported apps.
     */
    public void saveCSV(){
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"R(G), S(B), R(PP), R(Grout), R(B), " +
                "F(H), F(C), L(H.T), L(C.T), Number of Boreholes, Minimum Borehole Length"});
        data.add(new String[]{"\n" + rg + ","+ sb + ","+ rpp + ","+ rgrout + ","+ rb + "," + fh +"," + fc
                + "," + lht + "," + lct + ","+ numOfBoreholes + "," + minBoreholeLength});

        // Possible function StringBuilder data_result =new StringBuilder();

        CSVWriter writer;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
        String saveLocation = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/" +
                sdf.format(new Date()) + "ThermalData.csv");

        writer = null;
        try {
            writer = new CSVWriter(new FileWriter(saveLocation));
            writer.writeAll(data); // data is adding to csv
            //writer.close();
            Toast.makeText(IGSHPA_VB_Result.this, getString(R.string.save_csv_success), Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Log.e("MyApp", "Error", e);
            Toast.makeText(IGSHPA_VB_Result.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }


    public RelativeLayout layout_top;

    /**
     * Save the screen as an image.
     */
    public void saveImage() {
        layout_top.setDrawingCacheEnabled(true);
        layout_top.buildDrawingCache();
        layout_top.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        Bitmap bitmap = layout_top.getDrawingCache();

        //System.out.println("start output");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
        File imageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/",
                sdf.format(new Date()) + "ThermalData.jpeg");

        if (imageFile.exists()) {
            imageFile.delete();
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(imageFile)) {
            //System.out.println("start output 2");
            imageFile.createNewFile();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);


            List<String[]> data = new ArrayList<>();
            String saveLocation = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/" +
                    sdf.format(new Date()) + "tempoutput.csv");
            CSVWriter writer;
            writer = new CSVWriter(new FileWriter(saveLocation));
            data.add(new String[]{"helloworld"});
            writer.writeAll(data); // data is adding to csv
            //writer.close();


            fileOutputStream.flush();

            Toast.makeText(this, "Saving picture succeeded ！", Toast.LENGTH_SHORT).show();
            layout_top.setDrawingCacheEnabled(false);
            //System.out.println("start output 3");
        } catch (FileNotFoundException e) {
            Log.e("MyApp", "File not found", e);
            Toast.makeText(IGSHPA_VB_Result.this, "Error: File not found", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("MyApp", "Error writing to file", e);
            Toast.makeText(IGSHPA_VB_Result.this, "Error: Could not write to file", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("MyApp", "Error", e);
            Toast.makeText(IGSHPA_VB_Result.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * send detail to 4ee team
     */
    public void sendEmail(){
        StringBuilder data_result =new StringBuilder();
        data_result.append("R(G), S(B), R(PP), R(Grout), R(B), " +
                "F(H), F(C), L(H.T), L(C.T), Number of Boreholes, Minimum Borehole Length");
        data_result.append("\n" + rg + ","+ sb + ","+ rpp + ","+ rgrout + ","+ rb + "," + fh +"," + fc
                + "," + lht + "," + lct + ","+ numOfBoreholes + "," + minBoreholeLength);


        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"R(G), S(B), R(PP), R(Grout), R(B), " +
                "F(H), F(C), L(H.T), L(C.T), Number of Boreholes, Minimum Borehole Length"});
        data.add(new String[]{"\n" + rg + ","+ sb + ","+ rpp + ","+ rgrout + ","+ rb + "," + fh +"," + fc
                + "," + lht + "," + lct + ","+ numOfBoreholes + "," + minBoreholeLength});

        // Possible function StringBuilder data_result =new StringBuilder();

        CSVWriter writer;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
        String saveLocation = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/" +
                sdf.format(new Date()) + "ThermalData.csv");

        writer = null;
        try {
            writer = new CSVWriter(new FileWriter(saveLocation));
            writer.writeAll(data); // data is adding to csv
            //writer.close();
            Toast.makeText(IGSHPA_VB_Result.this, getString(R.string.save_csv_success), Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Log.e("MyApp", "Error", e);
            Toast.makeText(IGSHPA_VB_Result.this, "Error", Toast.LENGTH_SHORT).show();
        }

        try{


            // save file into devices

            FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
            out.write((data_result.toString()).getBytes());
            out.close();
            //exporting
            //Context context = getApplicationContext();
            File localCSV = new File(saveLocation);

            //Change path

            //Uri path = FileProvider.getUriForFile(context,"com.example.generatecsvfiles.fileprovider", fileLocation);
            Uri path = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                    BuildConfig.APPLICATION_ID + ".provider", localCSV);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT,"Data"); //send as email, subject is our data
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            fileIntent.putExtra(Intent.EXTRA_CC, new String[]{"info@4ee.com.au"});
            Intent chooser = Intent.createChooser(fileIntent, "Send email");
            List<ResolveInfo> resInfoList = this.getPackageManager().queryIntentActivities(chooser,
                    PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                this.grantUriPermission(packageName, path, Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            startActivity(chooser);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
