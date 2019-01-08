package planet.it.limited.pepsigosmart.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import planet.it.limited.pepsigosmart.activities.SelectPuposeAct;
import planet.it.limited.pepsigosmart.activities.SignInAct;
import planet.it.limited.pepsigosmart.utils.APIList;
import planet.it.limited.pepsigosmart.utils.BusyDialog;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;

import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveBoleanValueSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveToSharedPreferences;
public class LoginTask {
    Context mContext;
    String  chkLoginAPI = " ";
    // String mobileNo = " ";
    String androidDeviceId = " ";
    BusyDialog mBusyDialog;
    AppCompatActivity mAppCompatActivity;
    ClearAllSaveData clearAllSaveData;

    public LoginTask(Context context, AppCompatActivity appCompatActivity){
        this.mContext = context;
        this.mAppCompatActivity =appCompatActivity;
        clearAllSaveData = new ClearAllSaveData(mContext);
    }


    public void doLogin(String userName,String password){
        chkLoginAPI = APIList.loginAPI + password + "/"+ userName  ;
        new CHKLoginTask().execute();
        saveToSharedPreferences("user_name",userName,mContext);
    }

    public class CHKLoginTask extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mBusyDialog = new BusyDialog(mContext, true, "");
            mBusyDialog.show();
//            mBusyDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {

                OkHttpClient client = new OkHttpClient();


                Request request = new Request.Builder()
                        .url(chkLoginAPI)
                        .build();


                Response response = null;
                //client.setRetryOnConnectionFailure(true);
                response = client.newCall(request).execute();
                if (response.isSuccessful()){
                    mBusyDialog.dismis();

                    final String result = response.body().string();
                    // Log.d(RESPONSE_LOG,result);

                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String  status = jsonObject.getString("status");
                        String  userName = jsonObject.getString("name");
                        String  userMobile = jsonObject.getString("mobile");

                        saveToSharedPreferences("name",userName,mContext);
                        saveToSharedPreferences("user_mobile",userMobile,mContext);

                        if(status.equals("enable")){
                            SignInAct.isValidUser = true;
                            Intent intent = new Intent(mContext,SelectPuposeAct.class);
                            mContext.startActivity(intent);
                            //ActivityCompat.finishAffinity(mContext);
                            mAppCompatActivity.finish();

                            saveBoleanValueSharedPreferences("check_first_time",true,mContext);
                        }else {
                            ((Activity)mContext).runOnUiThread (new Thread(new Runnable() {
                                public void run() {
                                    mBusyDialog.dismis();
                                   // mTxvResErr.setText("Mobile No or PIN not Valid,Need to Sign Up");
                                    clearAllSaveData.openDialog("Invalid User");
                                }
                            }));
                        }






                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    //saveToSharedPreferences("balance","",mContext);
                    ((Activity)mContext).runOnUiThread (new Thread(new Runnable() {
                        public void run() {
                            mBusyDialog.dismis();
                            // saveToSharedPreferences("amount","",mContext);
                           // mTxvResErr.setText("Mobile No or PIN not Valid,Need to Sign Up");

                            clearAllSaveData.openDialog("Invalid User");
                        }
                    }));
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mBusyDialog.dismis();
        }


    }

}
