package planet.it.limited.pepsigosmart.task;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import planet.it.limited.pepsigosmart.utils.APIList;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;


/**
 * Created by Tarikul on 30-Oct-18.
 */

public class UpdateBkashAndRetMobB {
    Context mContext;
   // BusyDialog mBusyDialog;
    String updateBkashAndRetMobAPI = " ";
    String statusCode = " ";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ClearAllSaveData clearAllSaveData;
    String RESPONSE_UPDATE = "Response";
    String RESPONSE_CODE = "StatusCode";
    String RESPONSE_JSON = "Json";

   public UpdateBkashAndRetMobB(Context context){
       this.mContext = context;
       //this.mAppCompatActivity = appCompatActivity;
        clearAllSaveData = new ClearAllSaveData(mContext);
   }


   public void doUpdate( String bkash,String retMob,String bKashName,String outAddr,String outletCode){
       updateBkashAndRetMobAPI = APIList.updateBkashBondhuAPI + outletCode;

       UpdateBkashRetMob updateBkashRetMob = new UpdateBkashRetMob(bkash,retMob,bKashName,outAddr);
       updateBkashRetMob.execute();

   }


    public class UpdateBkashRetMob extends AsyncTask<String, Integer, String> {

        String mBkash;
        String mRetMob;
        String mBkashName ;
        String mOutAddr;

        public UpdateBkashRetMob(String bkash,String retMob,String bkashName,String outAddr){
            this.mBkash = bkash;
            this.mRetMob = retMob;
            this.mBkashName = bkashName;
            this.mOutAddr = outAddr;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mBusyDialog = new BusyDialog(mContext, false, "");
//            mBusyDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();

            try {
                JSONObject json = new JSONObject();
                json.put("mobile", mRetMob);
                json.put("bkash", mBkash);
                json.put("name", mBkashName);
                json.put("address", mOutAddr);

                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
                Log.d(RESPONSE_JSON,String.valueOf(json));

                Request request = new Request.Builder()
                        .url(updateBkashAndRetMobAPI)
                        .put(requestBody)
                        .build();

                Response response = null;
                //client.setRetryOnConnectionFailure(true);
                response = client.newCall(request).execute();
                Log.d(RESPONSE_UPDATE,response.toString());
                if (response.isSuccessful()){
                  //  mBusyDialog.dismis();
                    final String result = response.body().string();
                    // Log.d(RESPONSE_LOG,result);

                    try {
                        JSONObject jsonObject = new JSONObject(result);
                         statusCode = jsonObject.getString("statusCode");
                        Log.d(RESPONSE_CODE,statusCode);




                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                    ((Activity)mContext).runOnUiThread (new Thread(new Runnable() {
                        public void run() {
                         //   mBusyDialog.dismis();

                            clearAllSaveData.openDialog("Your Device Is Offline");
                        }
                    }));
                }
            } catch (IOException e) {
                e.printStackTrace();
//                ((Activity)mContext).runOnUiThread (new Thread(new Runnable() {
//                    public void run() {
//                        loadingDialog.cancel();
//                        Toast.makeText(mContext,"Message Sending Failled",Toast.LENGTH_SHORT).show();
//                    }
//                }));
            }  catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

          //  loadingDialog.cancel();
        }


    }

}
