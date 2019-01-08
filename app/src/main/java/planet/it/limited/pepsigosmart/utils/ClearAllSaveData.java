package planet.it.limited.pepsigosmart.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import planet.it.limited.pepsigosmart.R;
import planet.it.limited.pepsigosmart.activities.CoolerAndSignageAct;
import planet.it.limited.pepsigosmart.activities.DrawerCompatActivity;
import planet.it.limited.pepsigosmart.activities.SignInAct;
import planet.it.limited.pepsigosmart.activities.SummerHangamaEnrollAct;
import planet.it.limited.pepsigosmart.activities.VolumeAndImagesAct;
import planet.it.limited.pepsigosmart.activities.bondhu_club.CoolerAndSignageBAct;


import static android.content.Context.MODE_PRIVATE;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveBoleanValueSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveToSharedPreferences;

/**
 * Created by Tarikul on 02-Dec-18.
 */

public class ClearAllSaveData {
    Context mContext;
    public static String apkVersion = " 1.0 ";
    Map config;
    //CsvFileExport csvFileExport;
    SharedPreferences shared;
    ArrayList<String> valueList;

    public ClearAllSaveData(Context context){
        this.mContext = context;
        shared =mContext.getSharedPreferences("App_settings", MODE_PRIVATE);
         //add values for your ArrayList any where...
        valueList = new ArrayList<>();
     //   csvFileExport = new CsvFileExport(mContext);
    }

    public void clearAllWhenSearchOutlet(){
        //Summer hangama
        saveToSharedPreferences("outlet_code","",mContext);
        saveToSharedPreferences("unit_name","",mContext);
        saveToSharedPreferences("territory_name","",mContext);
        saveToSharedPreferences("ce_area_name","",mContext);
        saveToSharedPreferences("db_name","",mContext);
        saveToSharedPreferences("cluster_id","",mContext);
        saveToSharedPreferences("psr_name","",mContext);
        saveToSharedPreferences("outlet_name","",mContext);
        saveToSharedPreferences("outlet_address","",mContext);
        saveToSharedPreferences("retailer_name","",mContext);
        saveToSharedPreferences("retailer_mobile","",mContext);
        saveToSharedPreferences("ret_bikash_no", "", mContext);
        saveToSharedPreferences("ret_bikash_name", "", mContext);

        //cooler and signage

        saveBoleanValueSharedPreferences("cool_pepsico",false,mContext);
        saveBoleanValueSharedPreferences("cool_cocacola",false,mContext);
        saveBoleanValueSharedPreferences("cool_pran",false,mContext);
        saveBoleanValueSharedPreferences("cool_mojo",false,mContext);
        saveBoleanValueSharedPreferences("cool_others",false,mContext);
        saveBoleanValueSharedPreferences("sig_pepsico",false,mContext);
        saveBoleanValueSharedPreferences("sig_cocacola",false,mContext);
        saveBoleanValueSharedPreferences("sig_other_bever",false,mContext);
        saveBoleanValueSharedPreferences("sig_others",false,mContext);
        saveBoleanValueSharedPreferences("rb_cool_yes",false,mContext);
        saveBoleanValueSharedPreferences("rb_cool_no",false,mContext);
        saveBoleanValueSharedPreferences("rb_cool_alldone",false,mContext);
        saveBoleanValueSharedPreferences("rb_sig_yes",false,mContext);
        saveBoleanValueSharedPreferences("rb_sig_no",false,mContext);
        saveBoleanValueSharedPreferences("rb_sig_alldone",false,mContext);
        saveToSharedPreferences("cooler_brand","",mContext);
        saveToSharedPreferences("signage_brand","",mContext);
        CoolerAndSignageAct.coolerStatusList.clear();
        CoolerAndSignageAct.signageStatusList.clear();

        //Volume and Image

        saveToSharedPreferences("volume_target", "", mContext);
        saveToSharedPreferences("remarks", "", mContext);
        saveToSharedPreferences("out_pic_one_local_path", "", mContext);
        saveToSharedPreferences("out_photo_one_s_path", "", mContext);

        saveToSharedPreferences("out_photo_one_name", "", mContext);
        saveToSharedPreferences("out_photo_two_name", "", mContext);

        saveBoleanValueSharedPreferences("is_active",false,mContext);
        saveBoleanValueSharedPreferences("is_back",false,mContext);




        saveToSharedPreferences("latitude","",mContext);
        saveToSharedPreferences("longitude","",mContext);

        saveBoleanValueSharedPreferences("is_edit_ret_mob",false,mContext);
        saveBoleanValueSharedPreferences("is_edit_bkash",false,mContext);
        saveBoleanValueSharedPreferences("is_edit_bkash_name",false,mContext);
        saveBoleanValueSharedPreferences("is_edit_out_addr",false,mContext);
    }

    public void clearWhenSearchBondhuClubOutlet(){
        //bondhu club enroll
        saveToSharedPreferences("outlet_code_b","",mContext);
        saveToSharedPreferences("unit_name_b","",mContext);
        saveToSharedPreferences("territory_name_b","",mContext);
        saveToSharedPreferences("ce_area_name_b","",mContext);
        saveToSharedPreferences("db_name_b","",mContext);
        saveToSharedPreferences("cluster_id_b","",mContext);
        saveToSharedPreferences("psr_name_b","",mContext);
        saveToSharedPreferences("outlet_name_b","",mContext);
        saveToSharedPreferences("outlet_address_b","",mContext);
        saveToSharedPreferences("retailer_name_b","",mContext);
        saveToSharedPreferences("retailer_mobile_b","",mContext);
        saveToSharedPreferences("ret_bikash_no_b", "", mContext);
        saveToSharedPreferences("ret_bikash_name_b", "", mContext);

        //cooler and signage
        saveToSharedPreferences("cool_category_b","",mContext);
        saveToSharedPreferences("indust_csd","",mContext);
        saveToSharedPreferences("indust_water","",mContext);
        saveToSharedPreferences("indust_lrb","",mContext);

        saveBoleanValueSharedPreferences("rb_eat_drink",false,mContext);
        saveBoleanValueSharedPreferences("rb_grocery",false,mContext);
        saveBoleanValueSharedPreferences("rb_whole_sale",false,mContext);
        saveBoleanValueSharedPreferences("rb_aquafina_hvpo",false,mContext);
        saveBoleanValueSharedPreferences("rb_hvpo_mixed",false,mContext);
        saveBoleanValueSharedPreferences("rb_pepsi_exclusive",false,mContext);

        saveBoleanValueSharedPreferences("is_indust_csd",false,mContext);
        saveBoleanValueSharedPreferences("is_indust_water",false,mContext);
        saveBoleanValueSharedPreferences("is_indust_lrb",false,mContext);


        //volume and signage

        saveToSharedPreferences("pepsico_csd","",mContext);
        saveToSharedPreferences("pepsico_water","",mContext);
        saveToSharedPreferences("pepsico_lrb","",mContext);



        saveBoleanValueSharedPreferences("is_pepsico_csd",false,mContext);
        saveBoleanValueSharedPreferences("is_pepsico_water",false,mContext);
        saveBoleanValueSharedPreferences("is_pepsico_lrb",false,mContext);

        saveToSharedPreferences("remarks", "", mContext);
        saveToSharedPreferences("out_pic_one_local_path", "", mContext);
        saveToSharedPreferences("out_photo_one_s_path", "", mContext);
        saveToSharedPreferences("out_photo_one_name", "", mContext);

        saveBoleanValueSharedPreferences("is_active",false,mContext);
        saveBoleanValueSharedPreferences("is_back",false,mContext);

        saveToSharedPreferences("latitude_b","",mContext);
        saveToSharedPreferences("longitude_b","",mContext);

        //bondhu club enroll

        saveBoleanValueSharedPreferences("is_edit_ret_mob",false,mContext);
        saveBoleanValueSharedPreferences("is_edit_bkash",false,mContext);
        saveBoleanValueSharedPreferences("is_edit_bkash_name",false,mContext);
        saveBoleanValueSharedPreferences("is_edit_out_addr",false,mContext);
    }



    public boolean checkInternet() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public void openDialog( String msg) {
        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        View toastLayout = inflater.inflate(R.layout.custom_dialog, null);
        TextView txvMsg = (TextView)toastLayout.findViewById(R.id.dialog_info);

        txvMsg.setText(msg);
        Toast toast = new Toast(mContext);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0,0);

        toast.show();
    }

    public static String getTodayDate() {
        //DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.US);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.US);
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static String getStartTime() {
        //DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.US);
//        DateFormat dateFormat = new SimpleDateFormat("hh:mm a",Locale.US);
//        Date date = new Date();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa",Locale.US);
      //  Datetime = sdf.format(c.getTime());


        return sdf.format(c.getTime());
    }

    public static String getCurrentEntryDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static boolean isConnectingToInternet(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public  void logoutNavigation() {
        saveBoleanValueSharedPreferences("check_first_time",false,mContext);
        Intent intent = new Intent(mContext, SignInAct.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
        ((Activity)mContext).finish();


       clearAllWhenSearchOutlet();

        saveToSharedPreferences("user_name"," ",mContext);
        saveToSharedPreferences("user_mobile"," ",mContext);
        saveToSharedPreferences("name"," ",mContext);
    }

    public String getDeviceName() {
        String manufacturer = android.os.Build.MANUFACTURER;

        return manufacturer;
    }
    public String getAndroidDeviceModel() {
        return Build.MODEL;
    }
    public void initCloudinaryLib(){
        config = new HashMap();
        config.put("cloud_name", "planet");
        config.put("api_key", "366796436724612");
        config.put("api_secret", "8o_N_zKKHrHMLwfjDg1Q28bv-Rg");

        MediaManager.init(mContext, config);
        saveBoleanValueSharedPreferences("init_cloud",true,mContext);
    }

    public void saveCoolerStatusList( ArrayList<String> valueList) {
        SharedPreferences.Editor editor = shared.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(valueList);
        editor.putStringSet("cooler_status", set);
        editor.apply();
        Log.d("savecolerstatus",""+set);
    }
    public ArrayList getCoolerStatusList() {
        Set<String> set = shared.getStringSet("cooler_status", null);
        valueList.addAll(set);
        Log.d("retcolerstatus",""+set);
        return valueList;
    }

}
