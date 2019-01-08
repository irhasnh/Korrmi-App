package planet.it.limited.pepsigosmart.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import planet.it.limited.pepsigosmart.activities.SummerHangamaEnrollAct;
import planet.it.limited.pepsigosmart.activities.bondhu_club.BondhuClubEnrollAct;
import planet.it.limited.pepsigosmart.database.LocalStoragePepsiDB;
import planet.it.limited.pepsigosmart.utils.APIList;
import planet.it.limited.pepsigosmart.utils.BusyDialog;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;

import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getValueFromSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveBoleanValueSharedPreferences;

/**
 * Created by Tarikul on 04-Dec-18.
 */

public class BondhuClubDataSendToServer {
    Context mContext;
    AppCompatActivity appCompatActivity;
    BusyDialog mBusyDialog;
    String sendDataToServerAPI = " ";
    ClearAllSaveData clearAllSaveData;
     Response response =null;
     String RESPONSE_LOG = "Result";
     LocalStoragePepsiDB localStoragePepsiDB;

     String userName = " ";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public BondhuClubDataSendToServer(Context context, AppCompatActivity appCompatActivity){
        this.mContext = context;
        this.appCompatActivity = appCompatActivity;

        clearAllSaveData = new ClearAllSaveData(mContext);
        localStoragePepsiDB = new LocalStoragePepsiDB(mContext);

        userName = getValueFromSharedPreferences("user_name",mContext);

    }


    public void sendDataToServer(String outletCode, String coolCategory, String indusCSD, String indusWater
                                , String indusLRB, String pepsiCSD, String pepsiWater
            , String pepsiLRB,String remarks, String outPicOneServerP, String latitude,String longitude,
                                 String startTime,String endTime,String device,String apkVersion){
                    sendDataToServerAPI = APIList.postBondhuDataAPI + userName;
                    SurveyDataSendTask surveyDataSendTask = new SurveyDataSendTask( outletCode, coolCategory,  indusCSD, indusWater
                            ,  indusLRB,  pepsiCSD,  pepsiWater
                            , pepsiLRB,remarks, outPicOneServerP,  latitude, longitude,
                             startTime, endTime, device,apkVersion);
                    surveyDataSendTask.execute();

    }

    public class SurveyDataSendTask extends AsyncTask<String, Integer, String> {

        String outletCode ;
        String coolCategory;
        String indusCSD ;
        String indusWater;
        String indusLRB;
        String pepsiCSD;
        String pepsiWater;
        String pepsiLRB;
        String remarks;
        String outPicOneServerP;
        String latitude;
        String longitude;
        String startTime;
        String endTime;
        String device;
        String apkVersion;


        // private Dialog loadingDialog;
        public SurveyDataSendTask( String outletCode, String coolCategory, String indusCSD, String indusWater
                , String indusLRB, String pepsiCSD, String pepsiWater
                , String pepsiLRB,String remarks, String outPicOneServerP, String latitude,String longitude,
                                   String startTime,String endTime,String device,String apkVersion) {
            this.outletCode = outletCode;
            this.coolCategory = coolCategory;
            this.indusCSD = indusCSD;
            this.indusWater = indusWater;
            this.indusLRB = indusLRB;

            this.pepsiCSD = pepsiCSD;
            this.pepsiWater = pepsiWater;
            this.pepsiLRB = pepsiLRB;

            this.remarks = remarks;
            this.outPicOneServerP = outPicOneServerP;
            this.latitude = latitude;
            this.longitude = longitude;
          //  this.mEntryBy = mEntryBy;
            this.startTime = startTime;
            this.endTime = endTime;
            this.device = device;
            this.apkVersion = apkVersion;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mBusyDialog = new BusyDialog(mContext, false, "");
            mBusyDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();

            try {
                JSONObject json = new JSONObject();
                        json.put("outletCode", outletCode);
                        json.put("category", coolCategory);
                        json.put("indVolCsd", indusCSD);
                        json.put("indVolWater", indusWater);
                        json.put("indVolLrb", indusLRB);
                       // json.put("enrolledOrder", String.valueOf(mEnrolledOrder));
                        json.put("pepVolCsd", pepsiCSD);
                        json.put("pepVolWater", pepsiWater);
                        json.put("pepVolLrb", pepsiLRB);


                        json.put("remark", remarks);
                        json.put("photo", outPicOneServerP);
                        //json.put("photoTwo", mPicTwoServerPath);
                        json.put("latitude", latitude);
                        json.put("longitude", longitude);
                        json.put("startTime", startTime);
                        json.put("endTime", endTime);
                        json.put("device", device);
                        json.put("apk", apkVersion);

                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));



                Request request = new Request.Builder()
                        .url(sendDataToServerAPI)
                        .post(requestBody)
                        .build();


                //client.setRetryOnConnectionFailure(true);
                response = client.newCall(request).execute();
                //Log.d(RESPONSE_LOG,response.toString());
                if (response.isSuccessful()) {
                  //  mBusyDialog.dismis();
                    final String result = response.body().string();
                    Log.d(RESPONSE_LOG,response.toString());

                    try {
                      //  final JSONObject jsonObject = new JSONObject(result);


                        localStoragePepsiDB.open();

                        try {
                            // String outletId = SharedPreferenceLocalMemory.getValueFromSharedPreferences("outlet_id",mContext);
                            if(outletCode!=null && !outletCode.isEmpty()){
                                // long ROWID = Long.parseLong(rowId);
                                localStoragePepsiDB.updateSyncStatusB(outletCode,"success");
                                saveBoleanValueSharedPreferences("is_active",false,mContext);

                            }


                        }catch (NumberFormatException e){
                            e.getMessage();
                        }

//


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                    ((Activity) mContext).runOnUiThread(new Thread(new Runnable() {
                        public void run() {
                           // openDialog("Your Device Is Offline !");
                            mBusyDialog.dismis();
                            clearAllSaveData.openDialog("Network Problem");
                        }
                    }));
                }
            } catch (IOException e) {
                e.printStackTrace();
                ((Activity)mContext).runOnUiThread (new Thread(new Runnable() {
                    public void run() {
                        mBusyDialog.dismis();
                        clearAllSaveData.openDialog("Network Problem");
                    }
                }));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ((Activity)mContext).runOnUiThread (new Thread(new Runnable() {
                public void run() {
                    mBusyDialog.dismis();
                    Intent backIntent = new Intent(mContext, BondhuClubEnrollAct.class);
                    mContext.startActivity(backIntent);
                   // ActivityCompat.finishAffinity((Activity)mContext);
                    appCompatActivity.finish();
                  //  saveBoleanValueSharedPreferences("is_back",false,mContext);
                  //  saveBoleanValueSharedPreferences("is_active",false,mContext);
                    clearAllSaveData.clearWhenSearchBondhuClubOutlet();
                }
            }));

            //  loadingDialog.cancel();
        }
    }




}
