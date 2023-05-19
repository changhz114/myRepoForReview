package com.example.an_boxjelly;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import com.example.an_boxjelly.utils.ConstUtil;
import com.example.an_boxjelly.utils.ConvertToFloatUtil;
import com.example.an_boxjelly.utils.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IGSHPA_VB_Data_Input extends BaseActivity {
    //page for IGSHPA_VB
    private EditText mEtCop, mEtEerd, mEtHcd, mEtTcd, mEtEwtMin, mEtLwtMin, mEtEwtMax, mEtLwtMax, mEtRtclg,mEtRthlg, mEtNumBoreholeOptional;
    private EditText input_tg, mEtKg, mEtDgo, mEtDb, mEtDpo, mEtDpi,mEtKp, mEtKGrout;
    private double cop, eerd ,hcd ,tcd ,ewtmin , lwtmin,ewtmax , lwtmax,rtclg , rthlg , tg ,kg , dgo , db , dpo , dpi , kp ,kGrout;
    private double groundThermalResistantE , boreholePipeOuterDiameterRatio, boreholeShapeFactor, groutThermalResistantE , pipeWallThermalResistantE , boreholeThermalResistantE,coolingRunFraction, heatingRunFraction, heatingBoreholeLength, coolingBoreholeLength , totalBoreholeLength , numberOfBoreholes , minimumBoreholeLength;
    private double rg, sb, rpp, rgrout, rb, fh, fc, lht, lct, numBoreholeOptional;

    /**
     * onCreate method is called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_igshpa_vb_data_input);
        this.setTitle("IGSHPA_VB");
        mEtCop = findViewById(R.id.input_vb_cop);
        mEtEerd = findViewById(R.id.input_vb_eerd);
        mEtHcd = findViewById(R.id.input_vb_hcd);
        mEtTcd = findViewById(R.id.input_vb_tcd);
        mEtEwtMin = findViewById(R.id.input_vb_ewtmin);
        mEtLwtMin = findViewById(R.id.input_vb_lwtmin);
        mEtEwtMax = findViewById(R.id.input_vb_ewtmax);
        mEtLwtMax = findViewById(R.id.input_vb_lwtmax);
        mEtRtclg = findViewById(R.id.input_vb_rttlg);
        mEtRthlg = findViewById(R.id.input_vb_rthlg);
        input_tg = findViewById(R.id.input_vb_tg);
        mEtKg = findViewById(R.id.input_vb_kg);
        mEtDgo = findViewById(R.id.input_vb_dgo);
        mEtDb = findViewById(R.id.input_vb_db);
        mEtDpo = findViewById(R.id.input_vb_dpo);
        mEtDpi = findViewById(R.id.input_vb_dpi);
        mEtKp = findViewById(R.id.input_vb_kp);
        mEtKGrout = findViewById(R.id.input_vb_kGrout);
        mEtNumBoreholeOptional = findViewById(R.id.input_vb_num_boreholes_optional);


        setAutoFillConfig();
        autofillEditTexts(AUTO_FULL_CONFIG);
    }

    /**
     * This method is called initially when the activity is created and is to set the autofill configuration hashmap
     */
    @Override
    public void setAutoFillConfig(){
        addAutoFillConfig(mEtCop,getString(R.string.IGSHPA_VB_Data_Input_mEtCop));
        addAutoFillConfig(mEtEerd,getString(R.string.IGSHPA_VB_Data_Input_mEtEerd));
        addAutoFillConfig(mEtHcd,getString(R.string.IGSHPA_VB_Data_Input_mEtHcd));
        addAutoFillConfig(mEtTcd,getString(R.string.IGSHPA_VB_Data_Input_mEtTcd));
        addAutoFillConfig(mEtEwtMin,getString(R.string.IGSHPA_VB_Data_Input_mEtEwtMin));
        addAutoFillConfig(mEtLwtMin,getString(R.string.IGSHPA_VB_Data_Input_mEtLwtMin));
        addAutoFillConfig(mEtEwtMax,getString(R.string.IGSHPA_VB_Data_Input_mEtEwtMax));
        addAutoFillConfig(mEtLwtMax,getString(R.string.IGSHPA_VB_Data_Input_mEtLwtMax));
        addAutoFillConfig(mEtRtclg,getString(R.string.IGSHPA_VB_Data_Input_mEtRtclg));
        addAutoFillConfig(mEtRthlg,getString(R.string.IGSHPA_VB_Data_Input_mEtRthlg));
        addAutoFillConfig(input_tg,getString(R.string.IGSHPA_VB_Data_Input_input_tg));
        addAutoFillConfig(mEtKg,getString(R.string.IGSHPA_VB_Data_Input_mEtKg));
        addAutoFillConfig(mEtDgo,getString(R.string.IGSHPA_VB_Data_Input_mEtDgo));
        addAutoFillConfig(mEtDb,getString(R.string.IGSHPA_VB_Data_Input_mEtDb));
        addAutoFillConfig(mEtDpo,getString(R.string.IGSHPA_VB_Data_Input_mEtDpo));
        addAutoFillConfig(mEtDpi,getString(R.string.IGSHPA_VB_Data_Input_mEtDpi));
        addAutoFillConfig(mEtKp,getString(R.string.IGSHPA_VB_Data_Input_mEtKp));
        addAutoFillConfig(mEtKGrout,getString(R.string.IGSHPA_VB_Data_Input_mEtKGrout));
    }



    /**
     * Validates the input fields and calculates the required borehole length if input is valid.
     *
     * @param view The view that was clicked.
     */
    public void clickIGSHPAVBConfirm (View view) {
        boolean isValid = true;

        EditText[] inputs = {mEtCop, mEtEerd, mEtHcd, mEtTcd, mEtEwtMin, mEtLwtMin,
                mEtEwtMax, mEtLwtMax, mEtRtclg, mEtRthlg, input_tg, mEtKg,
                mEtDgo, mEtDb, mEtDpo, mEtDpi, mEtKp, mEtKGrout};
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
        else if(isValid == true){
            calculateVB();
            turnToResultPage(view);
        }
    }

    /**
     * Calculates the required borehole length for IGSHPA Vertical
     * Borehole systems based on the input values.
     */
    public void calculateVB (){
        castToFloat();
        groundThermalResistantE = (Math.log(dgo/db))/(2*Math.PI * kg/1.73);
        boreholePipeOuterDiameterRatio = db/dpo;
        boreholeShapeFactor = ((17.44*Math.pow(boreholePipeOuterDiameterRatio,-0.6025))+(21.91*Math.pow(boreholePipeOuterDiameterRatio,-0.3796)))/2;
        groutThermalResistantE = 1/(boreholeShapeFactor * kGrout/ 1.73);
        pipeWallThermalResistantE = (Math.log(dpo/dpi)/(2*Math.PI*(kp/1.73)))/2;
        boreholeThermalResistantE = groutThermalResistantE + pipeWallThermalResistantE;
        coolingRunFraction= rtclg/744;
        heatingRunFraction= rthlg/744;
        heatingBoreholeLength = (((hcd*1000/0.293)*((cop-1)/cop)*(boreholeThermalResistantE + groundThermalResistantE * heatingRunFraction))/
                ((tg * 1.8 +32)-((ewtmin * 1.8 +32)+(lwtmin *1.8 + 32))/2))*0.305;
        coolingBoreholeLength = (((tcd*1000/0.293)*((eerd+3.412)/eerd)*(boreholeThermalResistantE + groundThermalResistantE * coolingRunFraction))/
                (((ewtmax * 1.8 +32)+(lwtmax * 1.8 + 32))/2 - (tg * 1.8 +32)))*0.305;
        totalBoreholeLength = Math.max(heatingBoreholeLength,coolingBoreholeLength);
        if (mEtNumBoreholeOptional.getText().toString().isEmpty()){
            numberOfBoreholes = 1;
            minimumBoreholeLength = 90;
            while (totalBoreholeLength/numberOfBoreholes > 90 ){
                numberOfBoreholes += 1;
                minimumBoreholeLength = Math.ceil(totalBoreholeLength / numberOfBoreholes);
            }
        } else {
            numBoreholeOptional = Float.parseFloat(mEtNumBoreholeOptional.getText().toString());
            numberOfBoreholes = numBoreholeOptional;
            minimumBoreholeLength = Math.ceil(totalBoreholeLength / numberOfBoreholes);
        }
        //return (int)minimumBoreholeLength;
    }

    /**
     * Casts the input fields' values to float.
     */
//    public void castToFloat (){
//
//        cop = Float.parseFloat(mEtCop.getText().toString());
//        eerd = Float.valueOf(mEtEerd.getText().toString());
//        hcd = Float.valueOf(mEtHcd.getText().toString());
//        tcd = Float.valueOf(mEtTcd.getText().toString());
//        ewtmin = Float.valueOf(mEtEwtMin.getText().toString());
//        lwtmin = Float.valueOf(mEtLwtMin.getText().toString());
//        ewtmax = Float.valueOf(mEtEwtMax.getText().toString());
//        lwtmax = Float.valueOf(mEtLwtMax.getText().toString());
//        rtclg = Float.valueOf(mEtRtclg.getText().toString());
//        rthlg = Float.valueOf(mEtRthlg.getText().toString());
//        tg = Float.valueOf(input_tg.getText().toString());
//        kg = Float.valueOf(mEtKg.getText().toString());
//        dgo = Float.valueOf(mEtDgo.getText().toString());
//        db = Float.valueOf(mEtDb.getText().toString());
//        dpo = Float.valueOf(mEtDpo.getText().toString());
//        dpi = Float.valueOf(mEtDpi.getText().toString());
//        kp = Float.valueOf(mEtKp.getText().toString());
//        kGrout = Float.valueOf(mEtKGrout.getText().toString());
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
        tg = ConvertToFloatUtil.convertToFloat(input_tg);
        kg = ConvertToFloatUtil.convertToFloat(mEtKg);
        dgo = ConvertToFloatUtil.convertToFloat(mEtDgo);
        db = ConvertToFloatUtil.convertToFloat(mEtDb);
        dpo = ConvertToFloatUtil.convertToFloat(mEtDpo);
        dpi = ConvertToFloatUtil.convertToFloat(mEtDpi);
        kp = ConvertToFloatUtil.convertToFloat(mEtKp);
        kGrout = ConvertToFloatUtil.convertToFloat(mEtKGrout);
    }


    /**
     * Navigates to the result page and passes the calculated values.
     *
     * @param view The view that was clicked.
     */
    public void turnToResultPage (View view) { // executed when user click confirm button, and jump to IGSHPA_VB result page
        //String result = String.valueOf (calculate_VB());
        //Toast.makeText(IGSHPA_VB_Data_Input.this,result, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, IGSHPA_VB_Result.class);
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
