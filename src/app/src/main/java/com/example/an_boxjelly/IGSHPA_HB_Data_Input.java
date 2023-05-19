package com.example.an_boxjelly;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.an_boxjelly.utils.ConstUtil;
import com.example.an_boxjelly.utils.ConvertToFloatUtil;
import com.example.an_boxjelly.utils.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IGSHPA_HB_Data_Input extends BaseActivity {
    private EditText mEtCop, mEtEerd, mEtHcd, mEtTcd, mEtEwtMin, mEtLwtMin, mEtEwtMax, mEtLwtMax, mEtRtclg,mEtRthlg, mEtNumBoreholeOptional;
    private EditText mEtTm, mEtAs, mEtKg, mEtAlpha, mEtDgo, mEtDb, mEtDpo, mEtDpi,mEtKp,mEtKGrout, mEtAvgPipeDepth;
    private double cop, eerd ,hcd ,tcd ,ewtmin , lwtmin,ewtmax , lwtmax,rtclg , rthlg , tm,kg , dgo , db , dpo , dpi , kp ,kGrout, avgpipedepth, as , alpha;
    private double groundThermalResistantE , boreholePipeOuterDiameterRatio, boreholeShapeFactor, groutThermalResistantE ,
            pipeWallThermalResistantE , boreholeThermalResistantE,coolingRunFraction, heatingRunFraction,
            heatingBoreholeLength, coolingBoreholeLength , totalBoreholeLength , numberOfBoreholes , minimumBoreholeLength,
            meanEarthTemperatureE, surfaceTempAmplitudeE, constant , expvalue , cosvalue  , designHeatingEarthTempE , designCoolingEarthTempE;
    private double rg, sb, rpp, rgrout, rb, fh, fc, tsl, tsh, lht, lct, numBoreholeOptional ;

    /**
     * onCreate method is called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_igshpa_hb_data_input);
        this.setTitle("IGSHPA_HB");
        mEtCop = findViewById(R.id.input_hb_cop);
        mEtEerd = findViewById(R.id.input_hb_eerd);
        mEtHcd = findViewById(R.id.input_hb_hcd);
        mEtTcd = findViewById(R.id.input_hb_tcd);
        mEtEwtMin = findViewById(R.id.input_hb_ewtmin);
        mEtLwtMin = findViewById(R.id.input_hb_lwtmin);
        mEtEwtMax = findViewById(R.id.input_hb_ewtmax);
        mEtLwtMax = findViewById(R.id.input_hb_lwtmax);
        mEtRtclg = findViewById(R.id.input_hb_rttlg);
        mEtRthlg = findViewById(R.id.input_hb_rthlg);
        mEtTm = findViewById(R.id.input_hb_tm);
        mEtAs = findViewById(R.id.input_hb_as);
        mEtKg = findViewById(R.id.input_hb_kg);
        mEtAlpha = findViewById(R.id.input_hb_alpha);
        mEtDgo = findViewById(R.id.input_hb_dgo);
        mEtDb = findViewById(R.id.input_hb_db);
        mEtDpo = findViewById(R.id.input_hb_dpo);
        mEtDpi = findViewById(R.id.input_hb_dpi);
        mEtKp = findViewById(R.id.input_hb_kp);
        mEtKGrout = findViewById(R.id.input_hb_kGrout);
        mEtAvgPipeDepth = findViewById(R.id.input_hb_avgPipeDepth);
        mEtNumBoreholeOptional = findViewById(R.id.input_hb_num_boreholes_optional);
        setAutoFillConfig();
        autofillEditTexts(AUTO_FULL_CONFIG);
    }


    /**
     * This method is called initially when the activity is created and is to set the autofill configuration hashmap
     */
    @Override
    public void setAutoFillConfig(){
        addAutoFillConfig(mEtCop,getString(R.string.IGSHPA_HB_Data_Input_mEtCop));
        addAutoFillConfig(mEtEerd,getString(R.string.IGSHPA_HB_Data_Input_mEtEerd));
        addAutoFillConfig(mEtHcd,getString(R.string.IGSHPA_HB_Data_Input_mEtHcd));
        addAutoFillConfig(mEtTcd,getString(R.string.IGSHPA_HB_Data_Input_mEtTcd));
        addAutoFillConfig(mEtEwtMin,getString(R.string.IGSHPA_HB_Data_Input_mEtEwtMin));
        addAutoFillConfig(mEtLwtMin,getString(R.string.IGSHPA_HB_Data_Input_mEtLwtMin));
        addAutoFillConfig(mEtEwtMax,getString(R.string.IGSHPA_HB_Data_Input_mEtEwtMax));
        addAutoFillConfig(mEtLwtMax,getString(R.string.IGSHPA_HB_Data_Input_mEtLwtMax));
        addAutoFillConfig(mEtRtclg,getString(R.string.IGSHPA_HB_Data_Input_mEtRtclg));
        addAutoFillConfig(mEtRthlg,getString(R.string.IGSHPA_HB_Data_Input_mEtRthlg));
        addAutoFillConfig(mEtTm,getString(R.string.IGSHPA_HB_Data_Input_mEtTm));
        addAutoFillConfig(mEtAs,getString(R.string.IGSHPA_HB_Data_Input_mEtAs));
        addAutoFillConfig(mEtKg,getString(R.string.IGSHPA_HB_Data_Input_mEtKg));
        addAutoFillConfig(mEtDgo,getString(R.string.IGSHPA_HB_Data_Input_mEtDgo));
        addAutoFillConfig(mEtAlpha,getString(R.string.IGSHPA_HB_Data_Input_mEtAlpha));
        addAutoFillConfig(mEtDb,getString(R.string.IGSHPA_HB_Data_Input_mEtDb));
        addAutoFillConfig(mEtDpo,getString(R.string.IGSHPA_HB_Data_Input_mEtDpo));
        addAutoFillConfig(mEtDpi,getString(R.string.IGSHPA_HB_Data_Input_mEtDpi));
        addAutoFillConfig(mEtKGrout,getString(R.string.IGSHPA_HB_Data_Input_mEtKGrout));
        addAutoFillConfig(mEtKp,getString(R.string.IGSHPA_HB_Data_Input_mEtKp));
        addAutoFillConfig(mEtAvgPipeDepth,getString(R.string.IGSHPA_HB_Data_Input_mEtAvgPipeDepth));
    }

    /**
     * Confirm the input parameters and calculate the result.
     *
     * @param view The current view.
     */
    public void clickIGSHPAHBConfirm (View view){
        boolean isValid = true;

        EditText[] inputs = {mEtCop, mEtEerd, mEtHcd, mEtTcd, mEtEwtMin, mEtLwtMin,
                mEtEwtMax, mEtLwtMax, mEtRtclg, mEtRthlg, mEtTm, mEtKg,
                mEtDgo, mEtDb, mEtDpo, mEtDpi, mEtKp, mEtKGrout,
                mEtAs, mEtAlpha, mEtAvgPipeDepth};
        for (EditText input : inputs) {
            if (!validateInput(input)) {
                isValid = false;
                break;
            }
        }
        Matcher mer = Pattern.compile(ConstUtil.REGEX_PATTERN).matcher(mEtNumBoreholeOptional.getText().toString());
        if (!mEtNumBoreholeOptional.getText().toString().isEmpty()){
            if (mEtNumBoreholeOptional.getText().toString().length() > ConstUtil.INPUT_NUMBER_MAX_LENGTH){
                mEtNumBoreholeOptional.setError(getString(R.string.error_exceed_max_range));
                isValid = false;
            } else if (mer.matches() == false){
                mEtNumBoreholeOptional.setError(getString(R.string.error_invalid_input));
                isValid = false;
            }
        }
        if(isValid == false){
            ToastUtil.showToast(this, getString(R.string.error_invalid_input));
        }
        else if (isValid == true){
            calculateHB();
            turnToResultPage(view);
        }
    }

    /**
     * Calculate the HB result.
     */
    public void calculateHB (){
        castToFloat();
        groundThermalResistantE = (Math.log(dgo/db))/(2*PI * kg/1.73);
        boreholePipeOuterDiameterRatio = db/dpo;
        boreholeShapeFactor = ((17.44*Math.pow(boreholePipeOuterDiameterRatio,-0.6025))+(21.91*Math.pow(boreholePipeOuterDiameterRatio,-0.3796)))/2;
        groutThermalResistantE = 1/(boreholeShapeFactor * kGrout/ 1.73);
        pipeWallThermalResistantE = (Math.log(dpo/dpi)/(2*PI*(kp/1.73)))/2;
        boreholeThermalResistantE = groutThermalResistantE + pipeWallThermalResistantE;
        coolingRunFraction= rtclg/744;
        heatingRunFraction= rthlg/744;
        meanEarthTemperatureE = tm * 1.8 + 32;
        surfaceTempAmplitudeE = as * 1.8 + 32;
        constant = PI/(365 * 0.093 * alpha);
        expvalue = -Math.exp((-avgpipedepth * 0.305) * Math.pow(constant,0.5));
        cosvalue = Math.cos((2*PI/365)*(180 - (avgpipedepth * 0.305 /2) * Math.pow(365/(PI * alpha * 0.093),0.5)));
        designHeatingEarthTempE = (-Math.exp((-avgpipedepth * 0.305) * Math.pow(constant,0.5)))
                * Math.cos((2 * PI / 365) * (-avgpipedepth * 0.305 / 2) * Math.pow(365 / (PI * alpha * 0.093), 0.5))
                * (as * 1.8 + 32) + meanEarthTemperatureE;
        designCoolingEarthTempE = expvalue* cosvalue * surfaceTempAmplitudeE + meanEarthTemperatureE;
        heatingBoreholeLength = (
                ((hcd*1000/0.293)*((cop-1)/cop) * (boreholeThermalResistantE + groundThermalResistantE * heatingRunFraction))/
                        (designHeatingEarthTempE -((ewtmin * 1.8 +32)+(lwtmin *1.8 + 32))/2)
        ) * 0.305;
        coolingBoreholeLength = (
                ((tcd*1000/0.293)*((eerd+3.412)/eerd) * (boreholeThermalResistantE + groundThermalResistantE * coolingRunFraction))/
                        (((ewtmax * 1.8 +32)+(lwtmax * 1.8 + 32))/2 - designCoolingEarthTempE)
        ) * 0.305;
        totalBoreholeLength = Math.max(heatingBoreholeLength,coolingBoreholeLength);
        if (mEtNumBoreholeOptional.getText().toString().isEmpty()){
            numberOfBoreholes = 1;
            minimumBoreholeLength = 90;
            while (totalBoreholeLength/numberOfBoreholes > 90 ){
                numberOfBoreholes += 1;
                minimumBoreholeLength = Math.ceil(totalBoreholeLength / numberOfBoreholes);
            }
        }else {
            numBoreholeOptional = Float.parseFloat(mEtNumBoreholeOptional.getText().toString());
            numberOfBoreholes = numBoreholeOptional;
            minimumBoreholeLength = Math.ceil(totalBoreholeLength / numberOfBoreholes);
        }
        //return (int)minimumBoreholeLength;
    }

    /**
     * Cast the input values to float data type.
     */
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
        kg = ConvertToFloatUtil.convertToFloat(mEtKg);
        dgo = ConvertToFloatUtil.convertToFloat(mEtDgo);
        db = ConvertToFloatUtil.convertToFloat(mEtDb);
        dpo = ConvertToFloatUtil.convertToFloat(mEtDpo);
        dpi = ConvertToFloatUtil.convertToFloat(mEtDpi);
        kp = ConvertToFloatUtil.convertToFloat(mEtKp);
        kGrout = ConvertToFloatUtil.convertToFloat(mEtKGrout);
        as = ConvertToFloatUtil.convertToFloat(mEtAs);
        alpha = ConvertToFloatUtil.convertToFloat(mEtAlpha);
        avgpipedepth = ConvertToFloatUtil.convertToFloat(mEtAvgPipeDepth);
    }

    /**
     * Turn to the result page, passing the calculated values.
     *
     * @param view The current view.
     */
    public void turnToResultPage (View view) { // executed when user click confirm button, and jump to IGSHPA_VB result page
        //String result = String.valueOf (calculate_HB());
        //Toast.makeText(IGSHPA_HB_Data_Input.this,result, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, IGSHPA_HB_Result.class);
        Bundle bundle = new Bundle();
        rg = groundThermalResistantE / 1.73;
        bundle.putString("groundThermalResistanceResult", String.valueOf(rg));
        sb = boreholeShapeFactor;
        bundle.putString("boreholeShapeFactorResult", String.valueOf(sb));
        rpp = pipeWallThermalResistantE / 1.73;
        bundle.putString("pipeWallThermalResistanceResult", String.valueOf(rpp));
        rgrout = groutThermalResistantE / 1.73;
        bundle.putString("groutThermalResistanceResult", String.valueOf(rgrout));
        rb = boreholeThermalResistantE / 1.73;
        bundle.putString("boreholeThermalResistanceResult", String.valueOf(rb));
        fh = heatingRunFraction;
        bundle.putString("heatingRunFractionResult", String.valueOf(fh));
        fc = coolingRunFraction;
        bundle.putString("coolingRunFractionResult", String.valueOf(fc));
        tsh = (designCoolingEarthTempE - 32)/1.8;
        bundle.putString("designCoolingEarthTempResult", String.valueOf(tsh));
        tsl = (designHeatingEarthTempE - 32)/1.8;
        bundle.putString("designHeatingEarthTempResult", String.valueOf(tsl));
        lht = heatingBoreholeLength;
        bundle.putString("heatingBoreholeLengthResult", String.valueOf(lht));
        lct = coolingBoreholeLength;
        bundle.putString("coolingBoreholeLengthResult", String.valueOf(lct));
        bundle.putString("NumberofHoles", String.valueOf(numberOfBoreholes));
        bundle.putString("MinimumBoreholeLength", String.valueOf(minimumBoreholeLength));
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
