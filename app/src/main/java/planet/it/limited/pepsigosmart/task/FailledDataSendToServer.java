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
import planet.it.limited.pepsigosmart.activities.ShowAllIputDataAct;
import planet.it.limited.pepsigosmart.activities.SummerHangamaEnrollAct;
import planet.it.limited.pepsigosmart.database.LocalStoragePepsiDB;
import planet.it.limited.pepsigosmart.utils.APIList;
import planet.it.limited.pepsigosmart.utils.BusyDialog;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;

import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getValueFromSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveBoleanValueSharedPreferences;

/**
 * Created by Tarikul on 04-Dec-18.
 */

public class FailledDataSendToServer {
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

    public FailledDataSendToServer(Context context, AppCompatActivity appCompatActivity){
        this.mContext = context;
        this.appCompatActivity = appCompatActivity;

        clearAllSaveData = new ClearAllSaveData(mContext);
        localStoragePepsiDB = new LocalStoragePepsiDB(mContext);
        userName = getValueFromSharedPreferences("user_name",mContext);

    }


    public void sendDataToServer(String outletCode, String coolerStatus, String coolerBrand, String signageStatus
                                , String signageBrand,  int volumeTarget,
                                 String remarks, String outPicOneServerP, String latitude,String longitude,
                                 String startTime,  String endTime,String device,String apkVersion){
                    sendDataToServerAPI = APIList.sendDataToServerAPI + userName;
                    SurveyDataSendTask surveyDataSendTask = new SurveyDataSendTask(outletCode,coolerStatus,coolerBrand,signageStatus,signageBrand,
                              volumeTarget,remarks,outPicOneServerP,latitude,longitude,startTime,endTime,device,apkVersion);
                    surveyDataSendTask.execute();

    }

    public class SurveyDataSendTask extends AsyncTask<String, Integer, String> {

        String mOutletCode;
        String mCoolerStatus;
        String mCoolerBrand;
        String mSignageStatus;
        String mSignageBrand;

        int mVolumeTarget;
        String mRemarks;
        String mPicOneServerPath;
       // String mPicTwoServerPath;
        String mLatitude;
        String mLongitude;
        String mStartTime;
        String mEndTime;
       // String mEntryBy;
        String mDevice;
        String mApkVersion;




        // private Dialog loadingDialog;
        public SurveyDataSendTask( String mOutletCode, String mCoolerStatus, String mCoolerBrand, String mSignageStatus,
                String mSignageBrand,   int mVolumeTarget, String mRemarks, String mPicOneServerPath,
                                   String mLatitude, String mLongitude, String mStartTime,String mEndTime,
                String mDevice, String mApkVersion) {
            this.mOutletCode = mOutletCode;
            this.mCoolerStatus = mCoolerStatus;
            this.mCoolerBrand = mCoolerBrand;
            this.mSignageStatus = mSignageStatus;
            this.mSignageBrand = mSignageBrand;

            this.mVolumeTarget = mVolumeTarget;
            this.mRemarks = mRemarks;
            this.mPicOneServerPath = mPicOneServerPath;
           // this.mPicTwoServerPath = mPicTwoServerPath;
            this.mLatitude = mLatitude;
            this.mLongitude = mLongitude;
            this.mStartTime = mStartTime;
            this.mEndTime = mEndTime;
          //  this.mEntryBy = mEntryBy;
            this.mDevice = mDevice;
            this.mApkVersion = mApkVersion;
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
                        json.put("outletCode", mOutletCode);
                        json.put("coolerStatus", mCoolerStatus);
                        json.put("coolerBranding", mCoolerBrand);
                        json.put("signageStatus", mSignageStatus);
                        json.put("signageBranding", mSignageBrand);
                        //json.put("enrolledOrder", String.valueOf(mEnrolledOrder));
                        json.put("volTarget", String.valueOf(mVolumeTarget));
                        json.put("remark", mRemarks);
                        json.put("photoOne", mPicOneServerPath);
                       // json.put("photoTwo", mPicTwoServerPath);
                        json.put("latitude", mLatitude);
                        json.put("longitude", mLongitude);
                        json.put("startTime", mStartTime);
                         json.put("endTime", mEndTime);
                        json.put("devise", mDevice);
                        json.put("apk", mApkVersion);

                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));



                Request request = new Request.Builder()
                        .url(sendDataToServerAPI)
                        .post(requestBody)
                        .build();


                //client.setRetryOnConnectionFailure(true);
                response = client.newCall(request).execute();
                //Log.d(RESPONSE_LOG,response.toString());
                if (response.isSuccessful()) {
                    mBusyDialog.dismis();
                    final String result = response.body().string();
                    Log.d(RESPONSE_LOG,response.toString());

                    try {
                      //  final JSONObject jsonObject = new JSONObject(result);


                        localStoragePepsiDB.open();

                        try {
                            // String outletId = SharedPreferenceLocalMemory.getValueFromSharedPreferences("outlet_id",mContext);
                            if(mOutletCode!=null && !mOutletCode.isEmpty()){
                                // long ROWID = Long.parseLong(rowId);
                                localStoragePepsiDB.updateSyncStatus(mOutletCode,"success");
                                saveBoleanValueSharedPreferences("is_active",false,mContext);
                                ((Activity) mContext).runOnUiThread(new Thread(new Runnable() {
                                    public void run() {
                                        // openDialog("Your Device Is Offline !");
                                       // appCompatActivity.finish();
                                        Intent intent = new Intent(mContext, ShowAllIputDataAct.class);
                                         mContext.startActivity(intent);
                                        appCompatActivity.finish();

                                    }
                                }));
                            }else{

                                ((Activity) mContext).runOnUiThread(new Thread(new Runnable() {
                                    public void run() {
                                        // openDialog("Your Device Is Offline !");
                                        mBusyDialog.dismis();
                                        clearAllSaveData.openDialog("Failled");
                                    }
                                }));
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
//            ((Activity)mContext).runOnUiThread (new Thread(new Runnable() {
//                public void run() {
//                    mBusyDialog.dismis();
//                    Intent backIntent = new Intent(mContext, SummerHangamaEnrollAct.class);
//                    mContext.startActivity(backIntent);
//                   // ActivityCompat.finishAffinity((Activity)mContext);
//                    appCompatActivity.finish();
//                  //  saveBoleanValueSharedPreferences("is_back",false,mContext);
//                  //  saveBoleanValueSharedPreferences("is_active",false,mContext);
//                    clearAllSaveData.clearAllWhenSearchOutlet();
//                }
//            }));

            //  loadingDialog.cancel();
        }
    }




}
