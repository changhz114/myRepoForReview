package com.example.an_boxjelly;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;



import com.example.an_boxjelly.utils.ConstUtil;
import com.example.an_boxjelly.utils.ToastUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;


public abstract class BaseActivity extends AppCompatActivity {
    private AlertDialog dialog;
    public static final double PI = Math.PI;

    protected final HashMap<EditText, String> AUTO_FULL_CONFIG = new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // In the onCreate method, read the saved theme setting from SharedPreferences
        SharedPreferences stylePrefs = getSharedPreferences(ConstUtil.SETTINGS, MODE_PRIVATE);
        int themeId = stylePrefs.getInt(ConstUtil.MY_THEME, R.style.AppBlueAndWhite); // The default value is R.style.DefaultTheme

        // Set the theme to the saved value
        setTheme(themeId);

        // Initialize the auto-fill configuration
        initAutoFillConfig();
       // In the onCreate method, read the saved language setting from SharedPreferences
        SharedPreferences prefs = getSharedPreferences(ConstUtil.SETTINGS, MODE_PRIVATE);
        String langCode = prefs.getString(ConstUtil.MY_LANGUAGE, ConstUtil.LANGUAGE_CODE_ENGLISH); // The default value is "en"

      // Call the setLocale method, set the language to the saved value
        setLocale(langCode);
    }

    /**
     * Initialize the auto-fill configuration.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * A compiled Pattern object representing the regular expression for a valid input.
     * This pattern accepts an optional sign (+ or -), followed by 1 to 13 digits,
     * and an optional decimal point followed by any number of decimal digits.
     */
    public static final Pattern inputPattern = Pattern.compile(ConstUtil.INPUT_PATTERN);

    /**
     * Validate the input parameters.
     *
     * @param input_parameters The EditText input field containing the parameter.
     * @return boolean Returns true if the input is valid, false otherwise.
     */
    public boolean validateInput(EditText input_parameters){
        String inputNumber = input_parameters.getText().toString();
        if(inputNumber.isEmpty()){
            input_parameters.setError(getString(R.string.error_empty_field));
            return false;
        }
        else if(inputNumber.length()>ConstUtil.INPUT_NUMBER_MAX_LENGTH){
            input_parameters.setError(getString(R.string.error_exceed_max_range));
            return false;
        }
        else if (!inputPattern.matcher(inputNumber).matches()){
            input_parameters.setError(getString(R.string.error_invalid_input));
            return false;
        }else{
            input_parameters.setError(null);
            return true;
        }
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
     * Inflates the options menu in the action bar.
     * This method is called when the activity needs to display the options menu.
     *
     * @param menu The options menu in which you place your items.
     * @return boolean Returns true for the menu to be displayed.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.design_method_menu,menu);
        return true;
    }

    /**
     * Handles item selections in the options menu.
     * This method is called when an item in the options menu is selected.
     * It displays an information dialog depending on the selected menu item.
     *
     * @param item The menu item that was selected.
     * @return boolean Returns true to consume the menu item selection,
     *                 or returns the result of the superclass method.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Create a new AlertDialog.Builder
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // Switch based on the ID of the selected menu item
        switch (item.getItemId()) {
            case R.id.IGSHPA:
            case R.id.ASHRAE:
            case R.id.Development_Information:
            case R.id.About_the_App:
                // Initialize the information string with the default value
                String info = ConstUtil.INFO;
                // Switch based on the ID of the selected menu item
                switch (item.getItemId()) {
                    case R.id.IGSHPA:
                        info = getString(R.string.IGSHPA_info);
                        break;
                    case R.id.ASHRAE:
                        info = getString(R.string.ASHRAE_info);
                        break;
                    case R.id.Development_Information:
                        info = getString(R.string.Development_info);
                        break;
                    case R.id.About_the_App:
                        info = getString(R.string.About_the_APP_info);
                        break;
                }
                // Create and show the dialog
                dialogBuilder.setTitle(getString(R.string.information))
                        .setMessage(info)
                        .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> dialogInterface.dismiss());
                dialog = dialogBuilder.create();
                dialog.show();
                break;

            case R.id.Language:
                // Create an array of language options
                String[] languages = {
                        getString(R.string.english),
                        getString(R.string.spanish),
                        getString(R.string.chinese)};
                // Create and show the language selection dialog
                dialogBuilder.setTitle(getString(R.string.choose_language))
                        .setItems(languages, (dialogInterface, which) -> {
                            String chosenLanguage = languages[which];
                            // Change the language based on the selected option
                            switch (chosenLanguage) {
                                case ConstUtil.ENGLISH:
                                case ConstUtil.ENGLISH_CN:
                                case ConstUtil.ENGLISH_ES:
                                    setLocale(ConstUtil.LANGUAGE_CODE_ENGLISH);
                                    recreate();
                                    ToastUtil.showToast(this, R.string.language_changed_to_english);
                                    break;
                                case ConstUtil.SPANISH:
                                case ConstUtil.SPANISH_CN:
                                case ConstUtil.SPANISH_ES:
                                    setLocale(ConstUtil.LANGUAGE_CODE_SPANISH);
                                    recreate();
                                    ToastUtil.showToast(this, R.string.language_changed_to_spanish);
                                    break;
                                case ConstUtil.CHINESE:
                                case ConstUtil.CHINESE_CN:
                                case ConstUtil.CHINESE_ES:
                                    setLocale(ConstUtil.LANGUAGE_CODE_CHINESE);
                                    recreate();
                                    ToastUtil.showToast(this, R.string.language_changed_to_chinese);
                                    break;
                            }
                        });
                dialog = dialogBuilder.create();
                dialog.show();
                break;
            case R.id.Style:
                // Create an array of style options
                String[] styles = {
                        getString(R.string.style_default),getString(R.string.style_blue),getString(R.string.style_green),
                        getString(R.string.style_lemon)};
                // Create and show the style selection dialog
                dialogBuilder.setTitle(getString(R.string.choose_style))
                        .setItems(styles, (dialogInterface, which) -> {
                            String chosenStyle = styles[which];
                            // Change the style based on the selected option
                            switch (chosenStyle) {
                                case ConstUtil.STYLE_DEFAULT:
                                case ConstUtil.STYLE_DEFAULT_CN:
                                case ConstUtil.STYLE_DEFAULT_ES:
                                    setTheme(R.style.AppBlueAndWhite);
                                    recreate();
                                    ToastUtil.showToast(this, R.string.style_changed_to_default);
                                    saveTheme(R.style.AppBlueAndWhite);
                                    break;
                                case ConstUtil.STYLE_BLUE :
                                case ConstUtil.STYLE_BLUE_CN:
                                case ConstUtil.STYLE_BLUE_ES:
                                    setTheme(R.style.AppBlue);
                                    recreate();
                                    ToastUtil.showToast(this, R.string.style_changed_to_blue);
                                    saveTheme(R.style.AppBlue);
                                    break;
                                case ConstUtil.STYLE_GREEN:
                                case ConstUtil.STYLE_GREEN_CN:
                                case ConstUtil.STYLE_GREEN_ES:
                                    setTheme(R.style.AppGreen);
                                    recreate();
                                    ToastUtil.showToast(this, R.string.style_changed_to_green);
                                    saveTheme(R.style.AppGreen);
                                    break;
                                case ConstUtil.STYLE_LEMON :
                                case ConstUtil.STYLE_LEMON_CN:
                                case ConstUtil.STYLE_LEMON_ES:
                                    setTheme(R.style.AppLemon);
                                    recreate();
                                    ToastUtil.showToast(this, R.string.style_changed_to_lemon);
                                    saveTheme(R.style.AppLemon);
                                    break;
                            }
                        });
                dialog = dialogBuilder.create();
                dialog.show();
                break;
        }
        // Call the superclass implementation of onOptionsItemSelected
        return super.onOptionsItemSelected(item);
    }

    /**
     * Saves the user-selected theme to SharedPreferences.
     *
     * @param themeId The resource ID of the user-selected theme.
     */

    private void saveTheme(int themeId) {
        SharedPreferences prefs = getSharedPreferences(ConstUtil.SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(ConstUtil.MY_THEME, themeId);
        editor.apply();
    }

    /**
     * Set the application language to the given language code.
     * This method also saves the language setting in SharedPreferences for future use.
     *
     * @param langCode The language code for the new language setting. For example, "en" for English, "zh" for Chinese.
     */
    public void setLocale(String langCode) {

        // Create a new Locale object that represents a specific geographic, political, or cultural region. The parameter is the language code we pass.
        Locale locale = new Locale(langCode);

        // Set this Locale as the default Locale for the application. This means that the application's user interface will display the language corresponding to this Locale.
        Locale.setDefault(locale);

        // Create a new Configuration object that represents the current configuration information of the device.
        Configuration config = new Configuration();

        // Update the locale field of the Configuration object to the locale object we set.
        config.setLocale(locale);

        // Update the resource configuration information of the application. This way, the application's user interface will display the new language.
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        // Get the editor of SharedPreferences to store data in SharedPreferences. "Settings" is the name of the SharedPreferences file, and MODE_PRIVATE is the file operation mode that only allows this app to read and write.
        SharedPreferences.Editor editor = getSharedPreferences(ConstUtil.SETTINGS, MODE_PRIVATE).edit();

        // Store the language setting in SharedPreferences. "My_Lang" is the key to save the language setting, and langCode is the corresponding value (which is the language code we set).
        editor.putString(ConstUtil.MY_LANGUAGE, langCode);

        // Commit the modifications to SharedPreferences. This way, our language settings are saved, and the next time the application is opened, this setting can be read to keep the language consistent.
        editor.apply();
    }

    /**
     * This method displays an informational dialog with the given title and message.
     *
     * @param title   The title of the dialog.
     * @param message The message to be displayed in the dialog.
     */
    public void showInformationDialog(String title, String message) {
        AlertDialog.Builder informationBuilder = new AlertDialog.Builder(BaseActivity.this);

        informationBuilder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> {
                });
        AlertDialog dialog = informationBuilder.create();
        dialog.show();
    }
    /**

     Handles image button click events.
     @param view The View object representing the clicked image button
     */

    public void onImageButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ashrae_rpin:
                showInformationDialog(ConstUtil.ASHRAE_RPIN, getString(R.string.rpin_info));
                break;
            case R.id.btn_ashrae_rpext:
                showInformationDialog(ConstUtil.ASHRAE_RPEXT, getString(R.string.rpext_info));
                break;
            case R.id.btn_ashrae_rbore:
                showInformationDialog(ConstUtil.ASHRAE_RBORE, getString(R.string.rBore_info));
                break;
            case R.id.btn_ashrae_kg:
                showInformationDialog(ConstUtil.ASHRAE_KG, getString(R.string.KG_info));
                break;
            case R.id.btn_ashrae_kgrout:
                showInformationDialog(ConstUtil.ASHRAE_KGROUT, getString(R.string.kGrout_info));
                break;
            case R.id.btn_ashrae_kpipe:
                showInformationDialog(ConstUtil.ASHRAE_KPIPE, getString(R.string.kp_info));
                break;
            case R.id.btn_ashrae_lu:
                showInformationDialog(ConstUtil.ASHRAE_LU, getString(R.string.Lu_info));
                break;
            case R.id.btn_ashrae_hconv:
                showInformationDialog(ConstUtil.ASHRAE_HCONV, getString(R.string.hconv_info));
                break;
            case R.id.btn_ashrae_alpha:
                showInformationDialog(ConstUtil.ASHRAE_ALPHA, getString(R.string.Alpha_info));
                break;
            case R.id.btn_ashrae_tin:
                showInformationDialog(ConstUtil.ASHRAE_TIN, getString(R.string.TinHP_info));
                break;
            case R.id.btn_ashrae_mfls:
                showInformationDialog(ConstUtil.ASHRAE_MFLS, getString(R.string.mfls_info));
                break;
            case R.id.btn_ashrae_cp:
                showInformationDialog(ConstUtil.ASHRAE_CP, getString(R.string.Cp_info));
                break;
            case R.id.btn_ashrae_b:
                showInformationDialog(ConstUtil.ASHRAE_B, getString(R.string.B_info));
                break;
            case R.id.btn_ashrae_h:
                showInformationDialog(ConstUtil.ASHRAE_H, getString(R.string.H_info));
                break;
            case R.id.btn_ashrae_numberOfBoreholes:
                showInformationDialog(ConstUtil.ASHRAE_NUMBER_OF_BOREHOLES, getString(R.string.NB_info));
                break;
            case R.id.btn_ashrae_a:
                showInformationDialog(ConstUtil.ASHRAE_A, getString(R.string.A_info));
                break;
            case R.id.btn_ashrae_tg:
                showInformationDialog(ConstUtil.ASHRAE_TG, getString(R.string.T_g_info));
                break;
            case R.id.btn_ashrae_qy:
                showInformationDialog(ConstUtil.ASHRAE_QY, getString(R.string.qy_info));
                break;
            case R.id.btn_ashrae_qh:
                showInformationDialog(ConstUtil.ASHRAE_QH, getString(R.string.qh_info));
                break;
            case R.id.btn_ashrae_qm:
                showInformationDialog(ConstUtil.ASHRAE_QM, getString(R.string.qm_info));
                break;
            case R.id.btn_copd:
                showInformationDialog(ConstUtil.IGSHPA_COPD, getString(R.string.COP_info));
                break;
            case R.id.btn_eerd:
                showInformationDialog(ConstUtil.IGSHPA_EERD, getString(R.string.EERD_info));
                break;
            case R.id.btn_hcd:
                showInformationDialog(ConstUtil.IGSHPA_HCD, getString(R.string.HCD_info));
                break;
            case R.id.btn_tcd:
                showInformationDialog(ConstUtil.IGSHPA_TCD, getString(R.string.TCD_info));
                break;
            case R.id.btn_ewtmin:
                showInformationDialog(ConstUtil.IGSHPA_EWTMIN, getString(R.string.EWTmin_info));
                break;
            case R.id.btn_lwtmin:
                showInformationDialog(ConstUtil.IGSHPA_LWTMIN, getString(R.string.LWTmin_info));
                break;
            case R.id.btn_ewtmax:
                showInformationDialog(ConstUtil.IGSHPA_EWTMAX, getString(R.string.EWTmax_info));
                break;
            case R.id.btn_lwtmax:
                showInformationDialog(ConstUtil.IGSHPA_LWTMAX, getString(R.string.LWTmax_info));
                break;
            case R.id.btn_rtclg:
                showInformationDialog(ConstUtil.IGSHPA_RTCLG, getString(R.string.RTCLG_info));
                break;
            case R.id.btn_rthtg:
                showInformationDialog(ConstUtil.IGSHPA_RTHTG, getString(R.string.RTHTG_info));
                break;
            case R.id.btn_tg:
                showInformationDialog(ConstUtil.IGSHPA_TG, getString(R.string.TG_info));
                break;
            case R.id.btn_as:
                showInformationDialog(ConstUtil.IGSHPA_AS, getString(R.string.AS_info));
                break;
            case R.id.btn_kg:
                showInformationDialog(ConstUtil.IGSHPA_KG, getString(R.string.KG_info));
                break;
            case R.id.btn_dgo:
                showInformationDialog(ConstUtil.IGSHPA_DGO, getString(R.string.DGO_info));
                break;
            case R.id.btn_db:
                showInformationDialog(ConstUtil.IGSHPA_DB, getString(R.string.DB_info));
                break;
            case R.id.btn_dpo:
                showInformationDialog(ConstUtil.IGSHPA_DPO, getString(R.string.DPO_info));
                break;
            case R.id.btn_dpi:
                showInformationDialog(ConstUtil.IGSHPA_DPI, getString(R.string.Dpi_info));
                break;
            case R.id.btn_kp:
                showInformationDialog(ConstUtil.IGSHPA_KP, getString(R.string.kp_info));
                break;
            case R.id.btn_kgrout:
                showInformationDialog(ConstUtil.IGSHPA_KGROUT, getString(R.string.kGrout_info));
                break;
            case R.id.btn_tm:
                showInformationDialog(ConstUtil.IGSHPA_TM, getString(R.string.TM_info));
                break;
            case R.id.btn_rs:
                showInformationDialog(ConstUtil.IGSHPA_RS, getString(R.string.RS_info));
                break;
            case R.id.btn_alpha:
                showInformationDialog(ConstUtil.IGSHPA_ALPHA, getString(R.string.Alpha_info));
                break;
            case R.id.btn_sm:
                showInformationDialog(ConstUtil.IGSHPA_SM, getString(R.string.SM_info));
                break;
            case R.id.btn_pm:
                showInformationDialog(ConstUtil.IGSHPA_PM, getString(R.string.PM_info));
                break;
            case R.id.btn_avgPipeDepth:
                showInformationDialog(ConstUtil.IGSHPA_AVGPIPEDEPTH, getString(R.string.AVGPipeDepth_info));
                break;
            default:
                break;

        }
    }

    /**
     * Go back to the MainActivity when the back button is clicked.
     *
     * @param view The current view.
     */
    public void clickBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Show an information dialog when the information button is clicked.
     *
     */
    public void initAutoFillConfig() {
        AUTO_FULL_CONFIG.put(null, "None");
    }
    /**
     * Add an EditText and its value to the auto fill config.
     *
     * @param editText The EditText to add.
     * @param value The value to add.
     */
    public void addAutoFillConfig(EditText editText, String value) {
        AUTO_FULL_CONFIG.put(editText, value);
    }
    /**
     * Set the auto fill config.
     */
    public void setAutoFillConfig(){
        return;
    }

    /**
     * Auto fill the EditTexts.
     */
    public static void autofillEditTexts(Map<EditText, String> editTextsToFill) {
        for (Map.Entry<EditText, String> entry : editTextsToFill.entrySet()) {
            EditText editText = entry.getKey();
            String text = entry.getValue();
            if (editText != null && text != null) {
                editText.setText(text);
            } else if (editText == null && text == "None") {
                continue;
            } else {
                Log.i("autofillEditTextsIsNull", "EditText or text is null");
            }
        }
    }

    /**
     * Show an information dialog when the information button is clicked.
     *
     * @param title The title of the dialog.
     * @param message The message of the dialog.
     */
    public static int consoleLogDebug(String title,String message) {
        Log.i(title, message);
        return 0;
    }

    /**
     * Show an information dialog when the information button is clicked.
     *
     * @param message The message of the dialog.
     */
    public static int consoleLogDebug(String message) {
        Log.i("CLD", message);
        return 0;
    }

}
