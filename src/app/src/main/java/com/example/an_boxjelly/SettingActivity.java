//package com.example.an_boxjelly;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.annotation.Nullable;
//
//import com.example.an_boxjelly.utils.Config;
//import com.example.an_boxjelly.utils.LanguageUtils;
//import com.example.an_boxjelly.utils.Store;
//
//
//public class SettingActivity extends BaseActivity {
//    private static final String TAG = "SettingAc";
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_set);
//        setTitle("设置Activity");
//        final String[] cities = {getString(R.string.lan_chinese), getString(R.string.lan_en),getString(R.string.lan_zh_rTYW),getString(R.string.Follow_the_system)};
//        final String[] locals = {"zh_CN", "en","zh_TW","111"};
//        Button button = (Button)findViewById(R.id.btn_setting);
//        button.setText("Language");
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
//                builder.setIcon(R.mipmap.ic_launcher);
//                builder.setTitle(R.string.select_language);
//                builder.setItems(cities, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String  language=null;
//                        if(which==3){
//                            language= LanguageUtils.getCurrentLanguage();
//                            Log.e(TAG, "onClick: language   --- >"+language  );
//                        }else{
//                            language=locals[which];
//                        }
//                        Store.setLanguageLocal(SettingActivity.this, language);
//                        Intent intent = new Intent(Config.ACTION);
//                        intent.putExtra("msg", "EVENT_REFRESH_LANGUAGE");
//                        sendBroadcast(intent);
//
//                    }
//                });
//                builder.show();
//
//            }
//        });
//    }
//
//
//}
