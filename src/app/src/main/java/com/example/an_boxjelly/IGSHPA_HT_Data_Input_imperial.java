package com.example.an_boxjelly;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.an_boxjelly.utils.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IGSHPA_HT_Data_Input_imperial extends BaseActivity {
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
        setContentView(R.layout.activity_igshpa_ht_data_input_imperial);
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
        mEtPipesPerTrenchOptional = findViewById(R.id.input_ht_pipes_per_trench_optional);
        setAutoFillConfig();
        autofillEditTexts(AUTO_FULL_CONFIG);
    }


//    private boolean validateOptionalInput(EditText input_parameters) {
//        String inputNumber = input_parameters.getText().toString();
//        Matcher mer = Pattern.compile("^[0-9]+$").matcher(inputNumber);
//        if (!inputNumber.isEmpty()) {
//            if (inputNumber.length() > 6) {
//                input_parameters.setError("Exceeding maximum range");
//                return false;
//            } else if (mer.matches() == false) {
//                input_parameters.setError("Invalid input");
//                return false;
//            }
//        }
//        input_parameters.setError(null);
//        return true;
//    }


    /**
     * This method is called initially when the activity is created and is to set the autofill configuration hashmap
     */
    @Override
    public void setAutoFillConfig(){
        addAutoFillConfig(mEtCop,getString(R.string.IGSHPA_HT_Data_Input_imperial_mEtCop));
        addAutoFillConfig(mEtEerd,getString(R.string.IGSHPA_HT_Data_Input_imperial_mEtEerd));
        addAutoFillConfig(mEtHcd,getString(R.string.IGSHPA_HT_Data_Input_imperial_mEtHcd));
        addAutoFillConfig(mEtTcd,getString(R.string.IGSHPA_HT_Data_Input_imperial_mEtTcd));
        addAutoFillConfig(mEtEwtMin,getString(R.string.IGSHPA_HT_Data_Input_imperial_mEtEwtMin));
        addAutoFillConfig(mEtLwtMin,getString(R.string.IGSHPA_HT_Data_Input_imperial_mEtLwtMin));
        addAutoFillConfig(mEtEwtMax,getString(R.string.IGSHPA_HT_Data_Input_imperial_mEtEwtMax));
        addAutoFillConfig(mEtLwtMax,getString(R.string.IGSHPA_HT_Data_Input_imperial_mEtLwtMax));
        addAutoFillConfig(mEtRtclg,getString(R.string.IGSHPA_HT_Data_Input_imperial_mEtRtclg));
        addAutoFillConfig(mEtRthlg,getString(R.string.IGSHPA_HT_Data_Input_imperial_mEtRthlg));
        addAutoFillConfig(mEtTm,getString(R.string.IGSHPA_HT_Data_Input_imperial_mEtTm));
        addAutoFillConfig(mEtAs,getString(R.string.IGSHPA_HT_Data_Input_imperial_mEtAs));
        addAutoFillConfig(mEtRs,getString(R.string.IGSHPA_HT_Data_Input_imperial_mEtRs));
        addAutoFillConfig(mEtAlpha,getString(R.string.IGSHPA_HT_Data_Input_imperial_mEtAlpha));
        addAutoFillConfig(mEtDpo,getString(R.string.IGSHPA_HT_Data_Input_imperial_mEtDpo));
        addAutoFillConfig(mEtDpi,getString(R.string.IGSHPA_HT_Data_Input_imperial_mEtDpi));
        addAutoFillConfig(mEtKp,getString(R.string.IGSHPA_HT_Data_Input_imperial_mEtKp));
        addAutoFillConfig(mEtAvgPipeDepth,getString(R.string.IGSHPA_HT_Data_Input_imperial_mEtAvgPipeDepth));
        addAutoFillConfig(mEtSm,getString(R.string.IGSHPA_HT_Data_Input_imperial_mEtSm));
        addAutoFillConfig(mEtPm,getString(R.string.IGSHPA_HT_Data_Input_imperial_mEtPm));
    }

    /**
     * Validates the input values for the IGSHPA heat transfer design method when the user clicks the "Confirm" button.
     * If all inputs are valid, it calculates the heat transfer values and navigates to the result page.
     * Otherwise, it shows an error message.
     *
     * @param view The view that triggered the event (the "Confirm" button in this case).
     */
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
        castToFloat();
        pipeThermalResistantE = Math.log(dpo / dpi) / (2 * PI * kp / 1.73);
        coolingRunFraction = rtclg / 744;
        hoolingRunFraction = rthlg / 744;
        meanEarthTemperatureE = tm * 1.8 + 32;
        surfaceTempAmplitudeE = as * 1.8 + 32;
        constant = PI / (365 * 0.093 * alpha);
        expValue = -Math.exp((-avgpipedepth * 0.305) * Math.pow(constant, 0.5));
        cosValue = Math.cos((2 * PI / 365) * (180 - (avgpipedepth * 0.305 / 2) * Math.pow(365 / (PI * alpha * 0.093), 0.5)));

        designHeatingEarthTempE = (-Math.exp((-avgpipedepth * 0.305) * Math.pow(constant, 0.5)))
                * Math.cos((2 * PI / 365) * (-avgpipedepth * 0.305 / 2) * Math.pow(365 / (PI * alpha * 0.093), 0.5))
                * (as * 1.8 + 32) + meanEarthTemperatureE;
        designCoolingEarthTempE = expValue * cosValue * surfaceTempAmplitudeE + meanEarthTemperatureE;
        heatingBoreholeLength = (
                ((hcd * 1000 / 0.293) * ((cop - 1) / cop) * (pipeThermalResistantE + (rs / 1.73) * hoolingRunFraction * pm * sm)) /
                        (designHeatingEarthTempE - ((ewtmin * 1.8 + 32) + (lwtmin * 1.8 + 32)) / 2)
        ) * 0.305;
        coolingBoreholeLength = (
                ((tcd * 1000 / 0.293) * ((eerd + 3.412) / eerd) * (pipeThermalResistantE + (rs / 1.73) * coolingRunFraction * pm * sm)) /
                        (((ewtmax * 1.8 + 32) + (lwtmax * 1.8 + 32)) / 2 - designCoolingEarthTempE)
        ) * 0.305;
        totalBoreholeLength = Math.max(heatingBoreholeLength, coolingBoreholeLength);

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
    public void castToFloat() {
        cop = Float.parseFloat(mEtCop.getText().toString());
        eerd = Float.parseFloat(mEtEerd.getText().toString());
        hcd = Float.parseFloat(mEtHcd.getText().toString())*0.746;
        tcd = Float.parseFloat(mEtTcd.getText().toString())*0.746;
        ewtmin = (Float.parseFloat(mEtEwtMin.getText().toString())-32)/1.8;
        lwtmin = (Float.parseFloat(mEtLwtMin.getText().toString())-32)/1.8;
        ewtmax = (Float.parseFloat(mEtEwtMax.getText().toString())-32)/1.8;
        lwtmax = (Float.parseFloat(mEtLwtMax.getText().toString())-32)/1.8;
        rtclg = Float.parseFloat(mEtRtclg.getText().toString());
        rthlg = Float.parseFloat(mEtRthlg.getText().toString());
        tm = (Float.parseFloat(mEtTm.getText().toString())-32)/1.8;
        rs = Float.parseFloat(mEtRs.getText().toString())*0.5279;
        sm = Float.parseFloat(mEtSm.getText().toString());
        pm = Float.parseFloat(mEtPm.getText().toString());
        dpo = Float.parseFloat(mEtDpo.getText().toString())*0.3048 ;
        dpi = Float.parseFloat(mEtDpi.getText().toString())*0.3048 ;
        kp = Float.parseFloat(mEtKp.getText().toString())*1.730735;
        as = (Float.parseFloat(mEtAs.getText().toString())-32)/1.8;
        alpha = Float.parseFloat(mEtAlpha.getText().toString())*0.093;
        avgpipedepth = Float.parseFloat(mEtAvgPipeDepth.getText().toString())*0.3048 ;
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
