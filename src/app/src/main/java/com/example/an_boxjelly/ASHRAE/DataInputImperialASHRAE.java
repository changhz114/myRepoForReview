package com.example.an_boxjelly.ASHRAE;


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

import com.example.an_boxjelly.BaseActivity;
import com.example.an_boxjelly.R;

import java.util.regex.Pattern;

public class DataInputImperialASHRAE extends BaseActivity {

    private EditText mEtRpin, mEtRpext, mEtRbore, mEtKG, mEtKgrout, mEtKpipe, mEtLu, mEtHconv, mEtAlpha,mEtTin;
    private EditText mEtMfls, mEtCp, mEtB, mEtH, mEtNumberOfBoreholes, mEtA, mEtTg, mEtQy,mEtQh,mEtQmc;
    private double rpin, rpext, rbore, kG, kgrout, kpipe, Lu, hconv, alpha, Tin, mfls, Cp, B, H, numberOfBoreholes, A, Tg, qy, qh, qmc;
    private double PI = Math.PI;
    private double f10y, f1m, f6h, innerPipeConvectionResistance, pipeConvectionResistance, groutResistance, boreholeResistance, correlationFunc, dimensionlessTime, outletWaterTempCooling, outletWaterTempHeating;
    private double meanWaterTempCooling, meanWaterTempHeating, tempPenaltyCooling, tempPenaltyHeating, undisturbedCoolingLength, undisturbedHeatingLength, totalCoolingBoreholeLength, totalHeatingBoreholeLength;

    /**
     * This function is used to convert a series of input text values from string type to float type.
     * It retrieves string values from input text boxes (input_XXX), parses them as floats, and stores the results in corresponding variables.
     */
    public void castToFloat (){
        //Inner Radius of U loop. Suggested value 0.0137.
        rpin = Float.parseFloat(mEtRpin.getText().toString())*0.3048;
        //Outer Radius of U loop. Suggested value 0.0167.
        rpext = Float.parseFloat(mEtRpext.getText().toString())*0.3048;
        //Radius of Borehole. Suggested value 0.054.
        rbore = Float.parseFloat(mEtRbore.getText().toString())*0.3048;
        //Ground Thermal Conductivity. Suggested value 1.73 for Melbourne.
        kG = Float.parseFloat(mEtKG.getText().toString())*1.730735;
        //groundThermalConductivity
        kgrout = Float.parseFloat(mEtKgrout.getText().toString())*1.730735;
        //pipeThermalConductivity. Suggested value 0.346.
        kpipe = Float.parseFloat(mEtKpipe.getText().toString())*1.730735 ;
        //centreDistanceBetweenPipes
        Lu = Float.parseFloat(mEtLu.getText().toString())*0.3048;
        //Internal Convection Coefficient. Suggested value 1000.
        hconv = Float.parseFloat(mEtHconv.getText().toString())*5.67826;
        //groundThermalDiffusivity
        alpha = Float.parseFloat(mEtAlpha.getText().toString())*0.093;
        //enteringWaterTemp
        Tin = (Float.parseFloat(mEtTin.getText().toString())-32)*(5/9);
        //massFlowRate
        mfls = Float.parseFloat(mEtMfls.getText().toString())*4.882427E-4;
        //specificHeatCapacity
        Cp = Float.parseFloat(mEtCp.getText().toString())*2.326E+3;
        // distanceBetweenBoreholes
        B = Float.parseFloat(mEtB.getText().toString())*0.3048;
        // the borehole depth in IOS line 58 is = distanceBetweenBoreholes/distanceDepthRatio
        // distanceDepthRation = 0.05
        H = Float.parseFloat(mEtH.getText().toString())*0.3048;
        //numberOfBoreholes
        numberOfBoreholes = (int) Float.parseFloat(mEtNumberOfBoreholes.getText().toString());
        //The borefield geometrical aspect ration geoAspectRation
        A = Float.parseFloat(mEtA.getText().toString());

        qy = Float.parseFloat(mEtQy.getText().toString())*0.746;
        qh = Float.parseFloat(mEtQh.getText().toString())*0.746;
        Tg = (Float.parseFloat(mEtTg.getText().toString())-32)*(5/9);
        qmc = Float.parseFloat(mEtQmc.getText().toString())*0.746;
    }
    /**
     * This method calculates the minimum borehole length required for a ground-coupled heat exchanger system using the ASHRAE method.
     * The ASHRAE method takes into account the thermal properties of the soil, grout, and piping materials, as well as the number of boreholes and the heating and cooling loads of the system.
     *
     * @return The minimum borehole length required for the ground-coupled heat exchanger system.
     */
    public double calculateASHRAE (){
        // Convert to float
        castToFloat();
        // Calculate outlet water temperatures for cooling and heating, and mean water temperatures for both cases
        outletWaterTempCooling = Tin - (1000 / (mfls * Cp));
        outletWaterTempHeating = Tin + (1000 / (mfls * Cp));
        meanWaterTempCooling = (Tin + outletWaterTempCooling) / 2;
        meanWaterTempHeating = (Tin + outletWaterTempHeating) / 2;
        //Compute the borehole depth based on the distance-depth ratio
        //double disDepthR = B/H;//distanceDepthRatio
        double distanceDepthRatio = 0.05;
        double boreholeDepth = B/ distanceDepthRatio;
        // Calculate the dimensionless time // Ln(t/ts)
        dimensionlessTime = Math.log(3650/(Math.pow(boreholeDepth,2)/(9*alpha)));
        // Compute the correlation function using a set of 12 equations
        // each step of result from test data
        // correlationFunc1: -4.992888408850543
        // correlationFunc2: 89.560694166167
        // correlationFunc3: -30.613623899227527
        // correlationFunc4: -0.6725491886173123
        // correlationFunc5: 3.1130491623314036
        // correlationFunc6: -0.3496392000000001
        // correlationFunc7: -17.895213080009825
        // correlationFunc8: 0.16141312325055018
        // correlationFunc9: -5.416982590341743
        // correlationFunc10: 0.11657989095577412
        // correlationFunc11: 16.350336
        // correlationFunc12: -28.679477759999997
        // correlationFunc: 20.681698215657775
        double correlationFunc1 = 7.8189 + (-64.27) * distanceDepthRatio + 153.87 * Math.pow(distanceDepthRatio, 2) + (-84.809) * Math.pow(distanceDepthRatio, 3) + 3.461 * dimensionlessTime + (-0.94753) * Math.pow(dimensionlessTime, 2);
        double correlationFunc2 = (-0.060416) * Math.pow(dimensionlessTime, 3) + 1.5631 * numberOfBoreholes + (-0.0089416) * Math.pow(numberOfBoreholes, 2) + 0.00001906 * Math.pow(numberOfBoreholes, 3) + (-2.289) * A + 0.10187 * Math.pow(A, 2);
        double correlationFunc3 = 0.006569 * Math.pow(A, 3) + (-40.918) * distanceDepthRatio * dimensionlessTime + 15.557 * distanceDepthRatio * Math.pow(dimensionlessTime, 2) + (-19.107) * distanceDepthRatio * numberOfBoreholes + 0.10529 * distanceDepthRatio * Math.pow(numberOfBoreholes, 2) + 25.501 * distanceDepthRatio * A;
        double correlationFunc4 = (-2.1177) * distanceDepthRatio * Math.pow(A, 2) + (-2.1177) * distanceDepthRatio * Math.pow(A, 2) + 77.529 * Math.pow(distanceDepthRatio, 2) * dimensionlessTime;
        double correlationFunc5 = (-50.454) * Math.pow(dimensionlessTime * distanceDepthRatio, 2) + 76.352 * Math.pow(distanceDepthRatio, 2) * numberOfBoreholes + (-0.53719) * Math.pow(numberOfBoreholes * distanceDepthRatio, 2);
        double correlationFunc6 = (-132) * Math.pow(distanceDepthRatio, 2) * A + 12.878 * Math.pow(distanceDepthRatio * A, 2);
        double correlationFunc7 = 0.12697 * dimensionlessTime * numberOfBoreholes + (-0.00040284) * dimensionlessTime * Math.pow(numberOfBoreholes, 2);
        double correlationFunc8 = (-0.072065) * dimensionlessTime * A + 0.00095184 * dimensionlessTime * Math.pow(A, 2);
        double correlationFunc9 = (-0.024167) * Math.pow(dimensionlessTime, 2) * numberOfBoreholes + 0.000096811 * Math.pow(dimensionlessTime * numberOfBoreholes, 2);
        double correlationFunc10 = 0.028317 * Math.pow(dimensionlessTime, 2) * A + (-0.0010905) * Math.pow(dimensionlessTime * A, 2);
        double correlationFunc11 = 0.12207 * numberOfBoreholes* A + (-0.007105) * numberOfBoreholes * Math.pow(A, 2);
        double correlationFunc12 = (-0.0011129) * Math.pow(numberOfBoreholes, 2) * A+ (-0.00045566) * Math.pow(numberOfBoreholes * A, 2);

        correlationFunc = correlationFunc1 + correlationFunc2 + correlationFunc3 + correlationFunc4 + correlationFunc5 + correlationFunc6 + correlationFunc7 + correlationFunc8 + correlationFunc9 + correlationFunc10 + correlationFunc11 + correlationFunc12;

        // Calculate the f10y, f1m, and f6h values, which are dimensionless factors used in the calculation of borehole length
        double f10y_1 = 0.3057646 + 0.08987446 * rbore + (-0.09151786) * Math.pow(rbore,2) + (-0.03872451) * alpha + 0.1690853 * Math.pow(alpha,2);
        double f10y_2 = (-0.02881681) * Math.log(alpha) + (-0.002886584) * Math.pow(Math.log(alpha),2) + (-0.1723169) * rbore * alpha;
        double f10y_3 = 0.03112034 * rbore * Math.log(alpha) + (-0.1188438) * alpha * Math.log(alpha);

        f10y = (f10y_1 + f10y_2 + f10y_3) / kG;

        double f1m_1 = 0.4132728 + 0.2912981 * rbore + 0.07589286 * Math.pow(rbore,2) + 0.1563978 * alpha + (-0.2289355) * Math.pow(alpha, 2);
        double f1m_2 = (-0.004927554) * Math.log(alpha) + (-0.002694979) * Math.pow(Math.log(alpha),2) + (-0.6380360) * rbore * alpha;
        double f1m_3 = 0.2950815 * rbore * Math.log(alpha) + 0.1493320 * alpha * Math.log(alpha);

        f1m = (f1m_1 + f1m_2 + f1m_3) / kG;

        double f6h_1 = 0.6619352 + (-4.815693) * rbore + 15.03571 * Math.pow(rbore,2) + (-0.09879421) * alpha + 0.02917889 * Math.pow(alpha,2);
        double f6h_2 = 0.1138498 * Math.log(alpha) + 0.005610933 * Math.pow(Math.log(alpha),2) + 0.7796329 * rbore * alpha;
        double f6h_3 = (-0.324388) * rbore * Math.log(alpha) + (-0.01824101) * alpha * Math.log(alpha);

        f6h = (f6h_1 + f6h_2 + f6h_3) / kG;

        // Calculate the inner pipe convection resistance, pipe convection resistance, and grout resistance
        innerPipeConvectionResistance = 1 / (2 * PI * rpin * hconv);//224
        pipeConvectionResistance = Math.log(rpext / rpin) / (2 * PI * kpipe);//230

        double groutResistance1 = Math.log(rbore / rpext) + Math.log(rbore / Lu);
        double groutResistance2 = ((kgrout - kG) / (kgrout + kG)) * Math.log(Math.pow(rbore,4) / (Math.pow(rbore,4) - Math.pow(Lu / 2,4)));
        groutResistance = (groutResistance1 + groutResistance2) / (4 * PI * kgrout);

        // Compute the borehole resistance as the sum of grout resistance and the average of pipe convection resistance and inner pipe convection resistance
        boreholeResistance = groutResistance + ((pipeConvectionResistance + innerPipeConvectionResistance) / 2);
        // Calculate the undisturbed cooling and heating lengths
        undisturbedCoolingLength = (qh * boreholeResistance + qy * f10y + qmc * f1m + qh * f6h) / (meanWaterTempCooling - Tg);
        undisturbedHeatingLength = (qh * boreholeResistance + qy * f10y + qmc * f1m + qh * f6h) / (meanWaterTempHeating - Tg);
        // Compute the temperature penalties for cooling and heating
        tempPenaltyCooling = qy * correlationFunc / (2 * PI * kG * undisturbedCoolingLength);
        tempPenaltyHeating = qy * correlationFunc / (2 * PI * kG * undisturbedHeatingLength);

        // Calculate the total cooling and heating borehole lengths, considering the temperature penalties
        totalCoolingBoreholeLength = (qh * boreholeResistance + qy * f10y + qmc * f1m + qh * f6h) / (meanWaterTempCooling - Tg - tempPenaltyCooling);
        totalHeatingBoreholeLength = (qh * boreholeResistance + qy * f10y + qmc * f1m + qh * f6h) / (meanWaterTempHeating - Tg - tempPenaltyHeating);

        double totalBoreholeLength = Math.max(totalCoolingBoreholeLength,totalHeatingBoreholeLength);
        double MinimumBoreholeLength = Math.ceil(1000 * totalBoreholeLength / numberOfBoreholes);
        return (int) MinimumBoreholeLength;
    }
    /**
     * This method calculates the ASHRAE results, creates a new Intent to the ASHRAE_Data_Result activity,
     * and passes the calculated values through a Bundle.
     *
     * @param view The view that was clicked.
     */
    public void turnToResultPage(View view) {
        String result = String.valueOf(calculateASHRAE());
        Intent intent = new Intent(this, DataResultASHRAE.class);
        Bundle bundle = new Bundle();

        String[][] keyValuePairs = {
                {"tenyearResistance", String.valueOf(f10y)},
                {"onemonthResistance", String.valueOf(f1m)},
                {"sixhourResistance", String.valueOf(f6h)},
                {"innerPipeConvectionResistance", String.valueOf(innerPipeConvectionResistance)},
                {"pipeConvectionResistance", String.valueOf(pipeConvectionResistance)},
                {"groutResistance", String.valueOf(groutResistance)},
                {"boreholeResistance", String.valueOf(boreholeResistance)},
                {"correlationFunc", String.valueOf(correlationFunc)},
                {"dimensionlessTime", String.valueOf(dimensionlessTime)},
                {"outletWaterTempCooling", String.valueOf(outletWaterTempCooling)},
                {"outletWaterTempHeating", String.valueOf(outletWaterTempHeating)},
                {"meanWaterTempCooling", String.valueOf(meanWaterTempCooling)},
                {"meanWaterTempHeating", String.valueOf(meanWaterTempHeating)},
                {"tempPenaltyCooling", String.valueOf(tempPenaltyCooling)},
                {"tempPenaltyHeating", String.valueOf(tempPenaltyHeating)},
                {"undisturbedCoolingLength", String.valueOf(undisturbedCoolingLength)},
                {"undisturbedHeatingLength", String.valueOf(undisturbedHeatingLength)},
                {"totalCoolingBoreholeLength", String.valueOf(totalCoolingBoreholeLength)},
                {"totalHeatingBoreholeLength", String.valueOf(totalHeatingBoreholeLength)},
                {"NumberofHoles", String.valueOf(numberOfBoreholes)},
                {"MinimumBoreholeLength", result}
        };

        for (String[] pair : keyValuePairs) {
            bundle.putString(pair[0], pair[1]);
        }

        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Initializes the activity and sets up the user interface.
     * This method is called when the activity is first created.
     * It inflates the layout, sets the title, and binds the EditText fields to the corresponding UI elements.
     *
     * @param savedInstanceState A bundle containing the activity's previously saved state, or null if there is no state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ashrae_data_input_imperial);
        this.setTitle("ASHRAE");
        mEtRpin = findViewById(R.id.input_1);
        mEtRpext = findViewById(R.id.input_2);
        mEtRbore = findViewById(R.id.input_3);
        mEtKG = findViewById(R.id.input_4);
        mEtKgrout = findViewById(R.id.input_5);
        mEtKpipe = findViewById(R.id.input_6);
        mEtLu = findViewById(R.id.input_7);
        mEtHconv = findViewById(R.id.input_8);
        mEtAlpha = findViewById(R.id.input_9);
        mEtTin = findViewById(R.id.input_10);
        mEtMfls = findViewById(R.id.input_11);
        mEtCp = findViewById(R.id.input_12);
        mEtB = findViewById(R.id.input_13);
        mEtH = findViewById(R.id.input_14);
        mEtNumberOfBoreholes = findViewById(R.id.input_15);
        mEtA = findViewById(R.id.input_16);
        mEtTg = findViewById(R.id.input_17);
        mEtQy = findViewById(R.id.input_18);
        mEtQh = findViewById(R.id.input_19);
        mEtQmc = findViewById(R.id.input_20);
        setAutoFillConfig();
        autofillEditTexts(AUTO_FULL_CONFIG);
    }

    /**
     * This method is called when the activity is about to become visible.
     * It initializes the activity's UI and restores any state that was previously saved by the onSaveInstanceState() method.
     */
    public void setAutoFillConfig(){
        addAutoFillConfig(mEtCp,getString(R.string.DataInputImperialASHRAE_mEtCp));
        addAutoFillConfig(mEtTin,getString(R.string.DataInputImperialASHRAE_mEtTin));
        addAutoFillConfig(mEtMfls,getString(R.string.DataInputImperialASHRAE_mEtMfls));
        addAutoFillConfig(mEtAlpha,getString(R.string.DataInputImperialASHRAE_mEtAlpha));
        addAutoFillConfig(mEtKG,getString(R.string.DataInputImperialASHRAE_mEtKG));
        addAutoFillConfig(mEtTg,getString(R.string.DataInputImperialASHRAE_mEtTg));
        addAutoFillConfig(mEtQy,getString(R.string.DataInputImperialASHRAE_mEtQy));
        addAutoFillConfig(mEtQmc,getString(R.string.DataInputImperialASHRAE_mEtQmc));
        addAutoFillConfig(mEtQh,getString(R.string.DataInputImperialASHRAE_mEtQh));
        addAutoFillConfig(mEtB,getString(R.string.DataInputImperialASHRAE_mEtB));
        addAutoFillConfig(mEtNumberOfBoreholes,getString(R.string.DataInputImperialASHRAE_mEtNumberOfBoreholes));
        addAutoFillConfig(mEtA,getString(R.string.DataInputImperialASHRAE_mEtA));
        addAutoFillConfig(mEtH,getString(R.string.DataInputImperialASHRAE_mEtH));
        addAutoFillConfig(mEtRbore,getString(R.string.DataInputImperialASHRAE_mEtRbore));
        addAutoFillConfig(mEtKgrout,getString(R.string.DataInputImperialASHRAE_mEtKgrout));
        addAutoFillConfig(mEtLu,getString(R.string.DataInputImperialASHRAE_mEtLu));
        addAutoFillConfig(mEtRpin,getString(R.string.DataInputImperialASHRAE_mEtRpin));
        addAutoFillConfig(mEtRpext,getString(R.string.DataInputImperialASHRAE_mEtRpext));
        addAutoFillConfig(mEtKpipe,getString(R.string.DataInputImperialASHRAE_mEtKpipe));
        addAutoFillConfig(mEtHconv,getString(R.string.DataInputImperialASHRAE_mEtHconv));
    }
    /**
     * This method validates the input fields and, if valid, proceeds to the result page.
     * If not valid, it shows a Toast with an error message.
     * @param view The view that was clicked.
     */
    public void clickASHREAConfirm(View view) {
    boolean isValid = true;
    EditText[] inputs = {mEtRpin, mEtRpext, mEtRbore, mEtKG, mEtKgrout, mEtKpipe,
            mEtLu, mEtHconv, mEtAlpha, mEtTin, mEtMfls, mEtCp,
            mEtB, mEtH, mEtNumberOfBoreholes, mEtA, mEtTg, mEtQy,
            mEtQmc, mEtQh};
    for (EditText input : inputs) {
        if (!validateInput(input)) {
            isValid = false;
            break;
        }
    }
    if (isValid) {
        turnToResultPage(view);
    } else {
        Toast.makeText(DataInputImperialASHRAE.this, "You entered an invalid input!", Toast.LENGTH_SHORT).show();
    }
}

    }
