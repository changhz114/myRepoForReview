package com.example.an_boxjelly;



import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.an_boxjelly.utils.ConvertToFloatUtil;
import com.example.an_boxjelly.utils.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IGSHPA_HT_Data_Input extends BaseActivity {
    private EditText mEtCop, mEtEerd, mEtHcd, mEtTcd, mEtEwtMin, mEtLwtMin, mEtEwtMax, mEtLwtMax, mEtRtclg, mEtRthlg, mEtNumTrenchOptional, mEtPipesPerTrenchOptional;
    private EditText mEtTm, mEtAs, mEtRs, mEtAlpha, mEtSm, mEtPm, mEtDpo, mEtDpi, mEtKp, mEtAvgPipeDepth;
    private double cop, eerd, hcd, tcd, ewtmin, lwtmin, ewtmax, lwtmax, rtclg, rthlg, tm, rs, sm, pm, dpo, dpi, kp, avgpipedepth, as, alpha;

    private double pipeThermalResistantE, coolingRunFraction, hoolingRunFraction, meanEarthTemperatureE,
            surfaceTempAmplitudeE, constant, expValue, cosValue, designHeatingEarthTempE,
            designCoolingEarthTempE, heatingBoreholeLength, coolingBoreholeLength, totalBoreholeLength,
            minimumTrenchLength, numberOfPipesPerTrench, numberOfTrenches;
    private double rp, fh, fc, tsl, tsh, lhp, lcp, numTrenchOptional, pipesPerTrenchOptional;






    /**
     * Called when the activity is starting.
     * This method initializes the activity, sets the content view, and finds the views for input fields.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_igshpa_ht_data_input);
        this.setTitle("IGSHPA_HT");
        mEtCop = findViewById(R.id.input_ht_cop);
        mEtEerd = findViewById(R.id.input_ht_eerd);
        mEtHcd = findViewById(R.id.input_ht_hcd); // imperial
        mEtTcd = findViewById(R.id.input_ht_tcd);// imperial
        mEtEwtMin = findViewById(R.id.input_ht_ewtmin);// imperial
        mEtLwtMin = findViewById(R.id.input_ht_lwtmin);// imperial
        mEtEwtMax = findViewById(R.id.input_ht_ewtmax);// imperial
        mEtLwtMax = findViewById(R.id.input_ht_lwtmax);// imperial
        mEtRtclg = findViewById(R.id.input_ht_rttlg);
        mEtRthlg = findViewById(R.id.input_ht_rthlg);
        mEtTm = findViewById(R.id.input_ht_tm);// imperial
        mEtAs = findViewById(R.id.input_ht_as);// imperial
        mEtRs = findViewById(R.id.input_ht_rs);// imperial
        mEtAlpha = findViewById(R.id.input_ht_alpha);// imperial
        mEtSm = findViewById(R.id.input_ht_sm);
        mEtPm = findViewById(R.id.input_ht_pm);
        mEtDpo = findViewById(R.id.input_ht_dpo);// imperial
        mEtDpi = findViewById(R.id.input_ht_dpi);// imperial
        mEtKp = findViewById(R.id.input_ht_kp);// imperial
        mEtAvgPipeDepth = findViewById(R.id.input_ht_avgPipeDepth);
        mEtNumTrenchOptional = findViewById(R.id.input_ht_num_trenches_optional);
        mEtPipesPerTrenchOptional = findViewById(R.id.input_ht_pipesPerTrenchOptional);

        setAutoFillConfig();
        autofillEditTexts(this.AUTO_FULL_CONFIG);
//        EditText[] editTexts = {mEtCop, mEtEerd, mEtHcd, mEtTcd, mEtEwtMin, mEtLwtMin,
//                mEtEwtMax, mEtLwtMax, mEtRtclg, mEtRthlg, mEtTm, mEtAs,
//                mEtRs, mEtAlpha, mEtDpo, mEtDpi, mEtKp, mEtAvgPipeDepth,
//                mEtSm, mEtPm};
//        String[] defaultValues = {"3.63", "14.5", "14", "14", "-1.11", "-3.667", "24.44",
//                "30", "476", "476", "5.56", "0.556", "4.74", "0.047",
//                "0.027", "0.022", "0.4", "2.438", "1.1", "1"};
//        for (int i = 0; i < editTexts.length; i++) {
//            editTexts[i].setText(defaultValues[i]);
//        }
    }


    /**
     * This method is called initially when the activity is created and is to set the autofill configuration hashmap
     */
    @Override
    public void setAutoFillConfig(){
        this.addAutoFillConfig(mEtCop, getString(R.string.IGSHPA_HT_Data_Input_mEtCop));
        this.addAutoFillConfig(mEtEerd, getString(R.string.IGSHPA_HT_Data_Input_mEtEerd));
        this.addAutoFillConfig(mEtHcd, getString(R.string.IGSHPA_HT_Data_Input_mEtHcd));
        this.addAutoFillConfig(mEtTcd, getString(R.string.IGSHPA_HT_Data_Input_mEtTcd));
        this.addAutoFillConfig(mEtEwtMin, getString(R.string.IGSHPA_HT_Data_Input_mEtEwtMin));
        this.addAutoFillConfig(mEtLwtMin, getString(R.string.IGSHPA_HT_Data_Input_mEtLwtMin));
        this.addAutoFillConfig(mEtEwtMax, getString(R.string.IGSHPA_HT_Data_Input_mEtEwtMax));
        this.addAutoFillConfig(mEtLwtMax, getString(R.string.IGSHPA_HT_Data_Input_mEtLwtMax));
        this.addAutoFillConfig(mEtRtclg, getString(R.string.IGSHPA_HT_Data_Input_mEtRtclg));
        this.addAutoFillConfig(mEtRthlg, getString(R.string.IGSHPA_HT_Data_Input_mEtRthlg));
        this.addAutoFillConfig(mEtTm, getString(R.string.IGSHPA_HT_Data_Input_mEtTm));
        this.addAutoFillConfig(mEtAs, getString(R.string.IGSHPA_HT_Data_Input_mEtAs));
        this.addAutoFillConfig(mEtRs, getString(R.string.IGSHPA_HT_Data_Input_mEtRs));
        this.addAutoFillConfig(mEtAlpha, getString(R.string.IGSHPA_HT_Data_Input_mEtAlpha));
        this.addAutoFillConfig(mEtDpo, getString(R.string.IGSHPA_HT_Data_Input_mEtDpo));
        this.addAutoFillConfig(mEtDpi, getString(R.string.IGSHPA_HT_Data_Input_mEtDpi));
        this.addAutoFillConfig(mEtKp, getString(R.string.IGSHPA_HT_Data_Input_mEtKp));
        this.addAutoFillConfig(mEtAvgPipeDepth, getString(R.string.IGSHPA_HT_Data_Input_mEtAvgPipeDepth));
        this.addAutoFillConfig(mEtSm, getString(R.string.IGSHPA_HT_Data_Input_mEtSm));
        this.addAutoFillConfig(mEtPm, getString(R.string.IGSHPA_HT_Data_Input_mEtPm));
    }


    public void clickIGSHPAHTConfirm(View view) {
        boolean isValid = true;
        EditText[] inputs = {
                mEtCop, mEtEerd, mEtHcd, mEtTcd, mEtEwtMin, mEtLwtMin,
                mEtEwtMax, mEtLwtMax, mEtRtclg, mEtRthlg, mEtTm, mEtDpo,
                mEtDpi, mEtKp, mEtRs, mEtSm, mEtPm, mEtDpo,
                mEtAs, mEtAlpha, mEtAvgPipeDepth
        };
        for (EditText input : inputs) {
            if (!validateInput(input)) {
                isValid = false;
                break;
            }
        }
        if (isValid) {
            calculateHT();
            turnToResultPage(view);
        } else {
            ToastUtil.showToast(this, getString(R.string.error_invalid_input));
        }
    }

    /**
     * Calculates the heat transfer values for the IGSHPA heat transfer design method using user-provided inputs.
     * This method computes various intermediate values and then calculates the heating and cooling borehole lengths,
     * total borehole length, and the minimum trench length.
     */
    public void calculateHT() {
        //cast to float
        castToFloat();
        //Calculate pipe thermal resistance, cooling and heating run fractions, and other intermediate values
        pipeThermalResistantE = Math.log(dpo / dpi) / (2 * PI * kp / 1.73);
        coolingRunFraction = rtclg / 744;
        hoolingRunFraction = rthlg / 744;
        meanEarthTemperatureE = tm * 1.8 + 32;
        surfaceTempAmplitudeE = as * 1.8 + 32;
        constant = PI / (365 * 0.093 * alpha);
        expValue = -Math.exp((-avgpipedepth * 0.305) * Math.pow(constant, 0.5));
        cosValue = Math.cos((2 * PI / 365) * (180 - (avgpipedepth * 0.305 / 2) * Math.pow(365 / (PI * alpha * 0.093), 0.5)));

        // Calculate design heating and cooling earth temperatures
        designHeatingEarthTempE = (-Math.exp((-avgpipedepth * 0.305) * Math.pow(constant, 0.5)))
                * Math.cos((2 * PI / 365) * (-avgpipedepth * 0.305 / 2) * Math.pow(365 / (PI * alpha * 0.093), 0.5))
                * (as * 1.8 + 32) + meanEarthTemperatureE;
        designCoolingEarthTempE = expValue * cosValue * surfaceTempAmplitudeE + meanEarthTemperatureE;
        // Calculate heating and cooling borehole lengths, total borehole length, and minimum trench length
        heatingBoreholeLength = (
                ((hcd * 1000 / 0.293) * ((cop - 1) / cop) * (pipeThermalResistantE + (rs / 1.73) * hoolingRunFraction * pm * sm)) /
                        (designHeatingEarthTempE - ((ewtmin * 1.8 + 32) + (lwtmin * 1.8 + 32)) / 2)
        ) * 0.305;
        // Calculate total borehole length
        coolingBoreholeLength = (
                ((tcd * 1000 / 0.293) * ((eerd + 3.412) / eerd) * (pipeThermalResistantE + (rs / 1.73) * coolingRunFraction * pm * sm)) /
                        (((ewtmax * 1.8 + 32) + (lwtmax * 1.8 + 32)) / 2 - designCoolingEarthTempE)
        ) * 0.305;
        totalBoreholeLength = Math.max(heatingBoreholeLength, coolingBoreholeLength);

       // Determine the number of trenches, number of pipes per trench, and minimum trench length
        double noT = 1, noP = 1, length = 90;
        if (!mEtNumTrenchOptional.getText().toString().isEmpty()) {
            numTrenchOptional = Float.parseFloat(mEtNumTrenchOptional.getText().toString());
            numberOfTrenches = numTrenchOptional;
        }
        if (!mEtPipesPerTrenchOptional.getText().toString().isEmpty()) {
            pipesPerTrenchOptional = Float.parseFloat(mEtPipesPerTrenchOptional.getText().toString());
            numberOfPipesPerTrench = pipesPerTrenchOptional;
        }
        if (numberOfTrenches == 0) {
            if (numberOfPipesPerTrench == 0) {
                while (totalBoreholeLength / (noP * noT) > 90) {
                    if (noT < 5) {
                        noT += 1;
                    } else {
                        noP += 1;
                    }
                    length = Math.ceil(totalBoreholeLength / (noT * noP));
                }
                numberOfTrenches = noT;
                numberOfPipesPerTrench = noP;
                minimumTrenchLength = length;
            } else {
                noP = numberOfPipesPerTrench;
                while (totalBoreholeLength / (noP * noT) > 90) {
                    noT += 1;
                    length = Math.ceil(totalBoreholeLength / (noT * noP));
                }
                numberOfTrenches = noT;
                numberOfPipesPerTrench = noP;
                minimumTrenchLength = length;
            }
        } else {
            noT = numberOfTrenches;
            if (numberOfPipesPerTrench == 0) {
                noP = 1;
                length = 90;
                while (totalBoreholeLength / (numberOfPipesPerTrench * numberOfTrenches) > 90) {
                    noP += 1;
                    length = Math.ceil(totalBoreholeLength / (noT * noP));
                }
                numberOfTrenches = noT;
                numberOfPipesPerTrench = noP;
                minimumTrenchLength = length;
            } else {
                noP = numberOfPipesPerTrench;
                length = Math.ceil(totalBoreholeLength / (noT * noP));
                numberOfTrenches = noT;
                numberOfPipesPerTrench = noP;
                minimumTrenchLength = length;
            }
        }
        //return (int)totalBoreholeLength;
    }

    /**
     * Casts the text input values to float variables for calculation purposes.
     */
//    public void castToFloat() {
//        cop = Float.parseFloat(mEtCop.getText().toString());
//        eerd = Float.parseFloat(mEtEerd.getText().toString());
//        hcd = Float.parseFloat(mEtHcd.getText().toString());
//        tcd = Float.parseFloat(mEtTcd.getText().toString());
//        ewtmin = Float.parseFloat(mEtEwtMin.getText().toString());
//        lwtmin = Float.parseFloat(mEtLwtMin.getText().toString());
//        ewtmax = Float.parseFloat(mEtEwtMax.getText().toString());
//        lwtmax = Float.parseFloat(mEtLwtMax.getText().toString());
//        rtclg = Float.parseFloat(mEtRtclg.getText().toString());
//        rthlg = Float.parseFloat(mEtRthlg.getText().toString());
//        tm = Float.parseFloat(mEtTm.getText().toString());
//        rs = Float.parseFloat(mEtRs.getText().toString());
//        sm = Float.parseFloat(mEtSm.getText().toString());
//        pm = Float.parseFloat(mEtPm.getText().toString());
//        dpo = Float.parseFloat(mEtDpo.getText().toString());
//        dpi = Float.parseFloat(mEtDpi.getText().toString());
//        kp = Float.parseFloat(mEtKp.getText().toString());
//        as = Float.parseFloat(mEtAs.getText().toString());
//        alpha = Float.parseFloat(mEtAlpha.getText().toString());
//        avgpipedepth = Float.parseFloat(mEtAvgPipeDepth.getText().toString());
//    }

    public void castToFloat() {
        cop = ConvertToFloatUtil.convertToFloat(mEtCop);
        eerd = ConvertToFloatUtil.convertToFloat(mEtEerd);
        hcd = ConvertToFloatUtil.convertToFloat(mEtHcd);
        tcd = ConvertToFloatUtil.convertToFloat(mEtTcd);
        ewtmin = ConvertToFloatUtil.convertToFloat(mEtEwtMin);
        lwtmin = ConvertToFloatUtil.convertToFloat(mEtLwtMin);
        ewtmax = ConvertToFloatUtil.convertToFloat(mEtEwtMax);
        lwtmax = ConvertToFloatUtil.convertToFloat(mEtLwtMax);
        rtclg = ConvertToFloatUtil.convertToFloat(mEtRtclg);
        rthlg = ConvertToFloatUtil.convertToFloat(mEtRthlg);
        tm = ConvertToFloatUtil.convertToFloat(mEtTm);
        rs = ConvertToFloatUtil.convertToFloat(mEtRs);
        sm = ConvertToFloatUtil.convertToFloat(mEtSm);
        pm = ConvertToFloatUtil.convertToFloat(mEtPm);
        dpo = ConvertToFloatUtil.convertToFloat(mEtDpo);
        dpi = ConvertToFloatUtil.convertToFloat(mEtDpi);
        kp = ConvertToFloatUtil.convertToFloat(mEtKp);
        as = ConvertToFloatUtil.convertToFloat(mEtAs);
        alpha = ConvertToFloatUtil.convertToFloat(mEtAlpha);
        avgpipedepth = ConvertToFloatUtil.convertToFloat(mEtAvgPipeDepth);
    }

    /**
     * Passes the calculation results to the result page and starts the new activity.
     * @param view The current view of the application
     */
    public void turnToResultPage(View view) {
        Intent intent = new Intent(this, IGSHPA_HT_Result.class);
        Bundle bundle = new Bundle();
        rp = pipeThermalResistantE * 1.73;
        bundle.putString("pipeThermalResistanceResult", String.valueOf(rp));
        fh = hoolingRunFraction;
        bundle.putString("heatingRunFractionResult", String.valueOf(fh));
        fc = coolingRunFraction;
        bundle.putString("coolingRunFractionResult", String.valueOf(fc));
        tsh = (designCoolingEarthTempE - 32) / 1.8;
        bundle.putString("designCoolingEarthTempResult", String.valueOf(tsh));
        tsl = (designHeatingEarthTempE - 32) / 1.8;
        bundle.putString("designHeatingEarthTempResult", String.valueOf(tsl));
        lhp = heatingBoreholeLength;
        bundle.putString("heatingBoreholeLengthResult", String.valueOf(lhp));
        lcp = coolingBoreholeLength;
        bundle.putString("coolingBoreholeLengthResult", String.valueOf(lcp));
        bundle.putString("MinimumTrenchLength", String.valueOf(minimumTrenchLength));
        bundle.putString("NumberofPipesPerTrench", String.valueOf(numberOfPipesPerTrench));
        bundle.putString("NumberofTrenches", String.valueOf(numberOfTrenches));
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
