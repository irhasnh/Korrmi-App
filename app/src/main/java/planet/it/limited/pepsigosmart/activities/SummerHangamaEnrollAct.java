package planet.it.limited.pepsigosmart.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import planet.it.limited.pepsigosmart.R;
import planet.it.limited.pepsigosmart.utils.APIList;
import planet.it.limited.pepsigosmart.utils.BusyDialog;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;

import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getBoleanValueSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getValueFromSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveBoleanValueSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveToSharedPreferences;

public class SummerHangamaEnrollAct extends DrawerCompatActivity {
    private DrawerLayout drawerLayout;
    @BindView(R.id.edt_search_outlet_code)
    EditText edtSearchOutlet;

    @BindView(R.id.btn_enter_code)
    Button btnSearchOut;
    @BindView(R.id.btn_back)
    Button btnPageBack;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.btn_next)
    Button btnPageNext;

    @BindView(R.id.txv_unit_value)
    TextView txvUnitValue;
    @BindView(R.id.txv_teritory_value)
    TextView txvTerritoryValue;
    @BindView(R.id.txv_se_area_value)
    TextView txvSEAreaValue;
    @BindView(R.id.txv_distributor_value)
    TextView txvDistribValue;

    @BindView(R.id.txv_cluster_value)
    TextView txvClusterValue;

    @BindView(R.id.txv_psr_value)
    TextView txvPSRValue;
    @BindView(R.id.txv_outlet_name_value)
    TextView txvOutValue;

    @BindView(R.id.txv_retailer_name_value)
    TextView txvRetNameValue;
    @BindView(R.id.edt_retailer_mobile_value)
    EditText edtRetMobValue;

    @BindView(R.id.btn_ret_mob_edt)
    Button btnMobEdt;
    @BindView(R.id.btn_out_address_edt)
    Button btnEdtOutAddress;
    @BindView(R.id.edt_bikash_name)
    EditText edtBkashName;
    @BindView(R.id.edt_retailer_bikash_no)
    EditText edtBkashNo;

    @BindView(R.id.txv_outlet_address_value)
    EditText txvOutAddress;
    BusyDialog mBusyDialog;
    @BindView(R.id.txv_user_name)
    TextView txvUserName;
    @BindView(R.id.txv_mobile)
    TextView txvMobile;
//    @BindView(R.id.imgv_logout)
//    ImageView btnLogOut;

    String userName = " ";

    String unit=" ";
    String territory =" ";
    String ceArea =" ";
    String distributor = " ";
    String cluster = " ";
    String PSR = " ";
    String outletName = " ";
    String retailerName = " ";
    String retailerMobile = " ";
    String retailerBikashNo = " ";
    //String retBkashNo = " ";
    String bkashName = " ";
    String outletAddress = " ";
    String searchAPI = " ";
    private Timer timer = new Timer();
    private final long DELAY = 5000; // in ms
    ClearAllSaveData clearAllSaveData;
    String outletCode = " ";
    boolean checkActiviyRunning ;
    boolean isBack ;


   public static boolean isBkashNo;
   public static boolean isBkashName;
   public static boolean isRetMob;
   public static boolean isOutAddr;

    String userMobile = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summer_hangama_enroll);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        initViews();
        removeToolbar();
        setupDrawer();
    }

    public void initViews(){
        clearAllSaveData = new ClearAllSaveData(SummerHangamaEnrollAct.this);
        drawerLayout = (DrawerLayout) findViewById(R.id.app_drawer);
        ImageView menuIcon = (ImageView) findViewById(R.id.drawer_menuIcon);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!drawerLayout.isDrawerOpen(Gravity.LEFT))
                    drawerLayout.openDrawer(Gravity.LEFT);
            }
        });


        ButterKnife.bind(this);
        setClickListener();
       // searchAPI = APIList.outletInfoAPI;
        //clearAllSaveData.clearAllWhenSearchOutlet();

        String startTime = clearAllSaveData.getStartTime();
//        String todayDate = clearAllSaveData.getTodayDate();
//        if(!startTime.contains("AM")){
//
//        }

    //    String dateTime = todayDate + " " + startTime;

        saveToSharedPreferences("start_time", startTime , SummerHangamaEnrollAct.this);
        checkActiviyRunning = getBoleanValueSharedPreferences("is_active",SummerHangamaEnrollAct.this);
       // isBack = getBoleanValueSharedPreferences("is_back",SummerHangamaEnrollAct.this);
        if(checkActiviyRunning){
            btnUpdate.setVisibility(View.VISIBLE);
            btnPageNext.setVisibility(View.GONE);
            btnPageBack.setVisibility(View.GONE);
        }else {
            btnPageNext.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
        }

        userName = getValueFromSharedPreferences("name",SummerHangamaEnrollAct.this);
        userMobile = getValueFromSharedPreferences("user_mobile",SummerHangamaEnrollAct.this);
        if(userName!=null){
            txvUserName.setText(userName);
        }
        if(userMobile!=null){
            txvMobile.setText(userMobile);
        }

    }

    private void setClickListener(){
//        btnLogOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clearAllSaveData.logoutNavigation();
//            }
//        });
            btnPageBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent signIn = new Intent(SummerHangamaEnrollAct.this,RetailInitiativeAct.class);
                    startActivity(signIn);
                  //  ActivityCompat.finishAffinity(SummerHangamaEnrollAct.this);
                }
            });


            btnPageNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtSearchOutlet.getText().toString().length()>0) {

                        unit = txvUnitValue.getText().toString();
                        if (outletCode.toString().length() > 0) {
                            if(edtBkashNo.getText().toString().length()==11 ){
                                if(edtRetMobValue.getText().toString().length()==11){
                                    Intent signIn = new Intent(SummerHangamaEnrollAct.this,CoolerAndSignageAct.class);
                                    startActivity(signIn);
                                }else {
                                     String retMob = edtRetMobValue.getText().toString();
                                     boolean isValidNo = isValid(retMob);

                                    openDialog("need valid Retailer Mobile no");
                                }

                            }else {
                                openDialog("need valid Bkash no");
                            }

                            // ActivityCompat.finishAffinity(BasicInformationActivity.this);
                        }else {
                            openDialog("must input outlet  code");
                        }

                    } else {
                        openDialog("must input outlet code");


                    }



                }
            });

            isBkashNo =false;
            isBkashName = false;
            isRetMob = false;
            isOutAddr = false;

        btnSearchOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllSaveData.clearAllWhenSearchOutlet();
                isBkashNo =false;
                isBkashName = false;
                isRetMob = false;
                isOutAddr = false;

                outletCode = edtSearchOutlet.getText().toString();
                searchAPI = APIList.outletInfoAPI + outletCode ;
                if (!outletCode.equals("")) {
                    if (clearAllSaveData.checkInternet()) {
                        GetSearchDataTask getSearchDataTask = new GetSearchDataTask();
                        getSearchDataTask.execute();
                    } else {
                       openDialog("Your Device is Offline !");
                    }

                }else {
                    openDialog("must input outlet code");
                }



            }
        });
        edtSearchOutlet.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if(actionId== EditorInfo.IME_ACTION_DONE||actionId==EditorInfo.IME_ACTION_NEXT)
                {
                    clearAllSaveData.clearAllWhenSearchOutlet();

                    outletCode = edtSearchOutlet.getText().toString();
                    searchAPI = APIList.outletInfoAPI + outletCode ;

                    if (!outletCode.equals("")) {
                        if (clearAllSaveData.checkInternet()) {
                            GetSearchDataTask getSearchDataTask = new GetSearchDataTask();
                            getSearchDataTask.execute();
                        } else {
                            openDialog("Your Device is Offline !");
                        }

                    }else {
                        openDialog("must input outlet code");
                    }

                }
                return false;
            }
        });



        btnMobEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearAllSaveData.openDialog("Edit Enable");
                edtRetMobValue.setFocusableInTouchMode(true);
            }
        });

        btnEdtOutAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllSaveData.openDialog("Edit Enable");

                txvOutAddress.setFocusableInTouchMode(true);
            }
        });

        txvOutAddress.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isOutAddr) {
                    saveBoleanValueSharedPreferences("is_edit_out_addr",true,SummerHangamaEnrollAct.this);
                }else {
                    isOutAddr = true;
                }

                    outletAddress = editable.toString();
                    saveToSharedPreferences("outlet_address", outletAddress, SummerHangamaEnrollAct.this);



            }
        });


        edtRetMobValue.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                if (isRetMob) {
                    saveBoleanValueSharedPreferences("is_edit_ret_mob",true,SummerHangamaEnrollAct.this);
                }else {
                    isRetMob = true;
                }

                if(editable.toString().length()>0){
                    final boolean isvalidNo = isValid(editable.toString());

                    retailerMobile = editable.toString();
                    saveToSharedPreferences("retailer_mobile", retailerMobile, SummerHangamaEnrollAct.this);

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (editable.toString().length()==11) {

                                if (!isvalidNo) {

                                    runOnUiThread(new Thread(new Runnable() {
                                        public void run() {
                                            openDialog("Invalid Mobile number format");
                                        }
                                    }));
                                }

                            }else {
                                if (!isvalidNo) {

                                    runOnUiThread(new Thread(new Runnable() {
                                        public void run() {
                                            openDialog("Invalid Mobile number format");
                                        }
                                    }));
                                }

                            }

                        }

                    }, DELAY);

                }




            }
        });

        edtBkashNo.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(final Editable editable) {

                if (isBkashNo) {
                    saveBoleanValueSharedPreferences("is_edit_bkash",true,SummerHangamaEnrollAct.this);
                }else {
                    isBkashNo=true;
                }
                if(editable.toString().length()>0){
                final boolean isvalidNo = isValid(editable.toString());

            //    if (editable.toString().length()==11) {
                    retailerBikashNo = editable.toString();
                    saveToSharedPreferences("ret_bikash_no", retailerBikashNo, SummerHangamaEnrollAct.this);

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            if (editable.toString().length()==11) {

                                if (!isvalidNo) {

                                    runOnUiThread(new Thread(new Runnable() {
                                        public void run() {
                                            openDialog("Invalid Mobile number format");
                                        }
                                    }));
                                }

                            }else {
                                if (!isvalidNo) {

                                    runOnUiThread(new Thread(new Runnable() {
                                        public void run() {
                                            openDialog("Invalid Mobile number format");
                                        }
                                    }));
                                }

                            }

                        }

                    }, DELAY);


                }



            }
        });




        edtBkashName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isBkashName) {
                    saveBoleanValueSharedPreferences("is_edit_bkash_name",true,SummerHangamaEnrollAct.this);
                }else {
                    isBkashName = true;
                }

                    bkashName = editable.toString();
                    saveToSharedPreferences("ret_bikash_name", bkashName, SummerHangamaEnrollAct.this);



            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(SummerHangamaEnrollAct.this,ReconfirmedPageAct.class);
                startActivity(signIn);
                ActivityCompat.finishAffinity(SummerHangamaEnrollAct.this);
            }
        });

    }

    public class GetSearchDataTask extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mBusyDialog = new BusyDialog(SummerHangamaEnrollAct.this, true, "");
            mBusyDialog.show();
        }


        @Override
        protected String doInBackground(String... params) {
            String responseBodyText = null;

            OkHttpClient client = new OkHttpClient();

            try {

                Request request = new Request.Builder()
                        .url(searchAPI)

                        .build();


                Response response = null;

                response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    mBusyDialog.dismis();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txvUnitValue.setText("");
                            txvTerritoryValue.setText("");
                            txvSEAreaValue.setText("");
                            txvDistribValue.setText("");
                            txvClusterValue.setText("");
                            txvPSRValue.setText("");
                            txvOutValue.setText("");
                            txvRetNameValue.setText("");
                            edtRetMobValue.setText("");
                            txvOutAddress.setText("");
                            edtBkashNo.setText("");
                            Toast.makeText(SummerHangamaEnrollAct.this,"Data Not Found",Toast.LENGTH_SHORT).show();

                        }
                    });
                    throw new IOException("Okhttp Error: " + response);

                } else {
                    mBusyDialog.dismis();
                    responseBodyText = response.body().string();


                    JSONObject jobject = new JSONObject(responseBodyText);
                    outletCode = jobject.getString("outlet_code");
                    saveToSharedPreferences("outlet_code",outletCode,SummerHangamaEnrollAct.this);

                    unit = jobject.getString("unit_name");
                    saveToSharedPreferences("unit_name",unit,SummerHangamaEnrollAct.this);

                    territory = jobject.getString("territory_name");
                    saveToSharedPreferences("territory_name",territory,SummerHangamaEnrollAct.this);
                    ceArea = jobject.getString("ce_area_name");
                    saveToSharedPreferences("ce_area_name",ceArea,SummerHangamaEnrollAct.this);
                    distributor = jobject.getString("db_name");
                    saveToSharedPreferences("db_name",distributor,SummerHangamaEnrollAct.this);

                    cluster = jobject.getString("cluster_id");
                    saveToSharedPreferences("cluster_id",cluster,SummerHangamaEnrollAct.this);
                    PSR = jobject.getString("psr_name");
                    saveToSharedPreferences("psr_name",PSR,SummerHangamaEnrollAct.this);
                    outletName = jobject.getString("outlet_name");
                    saveToSharedPreferences("outlet_name",outletName,SummerHangamaEnrollAct.this);
                    outletAddress = jobject.getString("outlet_address");
                    saveToSharedPreferences("outlet_address",outletAddress,SummerHangamaEnrollAct.this);
                    retailerName = jobject.getString("owner_name");
                    saveToSharedPreferences("retailer_name",retailerName,SummerHangamaEnrollAct.this);

                    retailerMobile = jobject.getString("owner_mobile");
                    saveToSharedPreferences("retailer_mobile",retailerMobile,SummerHangamaEnrollAct.this);

                    bkashName = jobject.getString("bkash_name");
                    saveToSharedPreferences("ret_bikash_name",bkashName,SummerHangamaEnrollAct.this);

                    retailerBikashNo = jobject.getString("bkash_no");
                    saveToSharedPreferences("ret_bikash_no", retailerBikashNo, SummerHangamaEnrollAct.this);


                    String achievedVol =  jobject.getString("vol");
                    saveToSharedPreferences("achieved_vol",achievedVol,SummerHangamaEnrollAct.this);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(unit.equals("null")){
                                txvUnitValue.setText("");
                            }else {
                                txvUnitValue.setText(unit);
                            }
                            if(territory.equals("null")){
                                txvTerritoryValue.setText("");
                            }else {
                                txvTerritoryValue.setText(territory);
                            }
                            if(ceArea.equals("null")){
                                txvSEAreaValue.setText("");
                            }else {
                                txvSEAreaValue.setText(ceArea);
                            }

                            if(distributor.equals("null")){
                                txvDistribValue.setText("");
                            }else {
                                txvDistribValue.setText(distributor);
                            }
                        if(cluster.equals("null")){
                            txvClusterValue.setText("");
                        }else {
                            txvClusterValue.setText(cluster);
                        }



                        if(PSR.equals("null")){
                            txvPSRValue.setText("");
                        }else {
                            txvPSRValue.setText(PSR);
                        }
                        if(outletName.equals("null")){
                            txvOutValue.setText("");
                        }else {
                            txvOutValue.setText(outletName);
                        }
                        if(retailerName.equals("null")){
                            txvRetNameValue.setText("");
                        }else {
                            txvRetNameValue.setText(retailerName);
                        }
                            if(retailerMobile.equals("null")){
                                edtRetMobValue.setText("");
                            }else {
                                edtRetMobValue.setText(retailerMobile);
                            }

                            if(outletAddress.equals("null")){
                                txvOutAddress.setText("");
                            }else {
                                txvOutAddress.setText(outletAddress);
                            }
                            if(bkashName.equals("null")){
                                edtBkashName.setText("");
                            }else {
                                edtBkashName.setText(bkashName);
                            }
                            if(retailerBikashNo.equals("null")){
                                edtBkashNo.setText("");
                            }else {
                                edtBkashNo.setText(retailerBikashNo);
                            }



                        }
                    });

                }



            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return responseBodyText;


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mBusyDialog.dismis();


            // Toast.makeText(LoginActivity.this,"Login Success",Toast.LENGTH_SHORT).show();

        }
    }

    public static boolean isValid(String s)
    {

        Pattern p = Pattern.compile("(^[+]{1}[8]{2}[01]{1}[0-9]{9}|^[8]{2}[01]{1}[0-9]{9}|^[01]{2}[0-9]{9})$");

        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

    public void openDialog( String msg) {
        LayoutInflater inflater = SummerHangamaEnrollAct.this.getLayoutInflater();
        View toastLayout = inflater.inflate(R.layout.custom_dialog, null);
        TextView txvMsg = (TextView)toastLayout.findViewById(R.id.dialog_info);

        txvMsg.setText(msg);
        Toast toast = new Toast(SummerHangamaEnrollAct.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0,0);

        toast.show();
    }

    @Override
    public void onResume() {
        super.onResume();

        String outletCode = getValueFromSharedPreferences("outlet_code",SummerHangamaEnrollAct.this);
        if(outletCode!=null){
            edtSearchOutlet.setText(outletCode);
        }

        String unit = getValueFromSharedPreferences("unit_name",SummerHangamaEnrollAct.this);
        if(unit!=null){
            if(unit.equals("null")){
                txvUnitValue.setText(" ");
            }else {
                txvUnitValue.setText(unit);
            }

        }


        String territory = getValueFromSharedPreferences("territory_name",SummerHangamaEnrollAct.this);
        if(territory!=null){
            if(territory.equals("null")){
                txvTerritoryValue.setText(" ");
            }else {
                txvTerritoryValue.setText(territory);
            }

        }
        String ceArea = getValueFromSharedPreferences("ce_area_name",SummerHangamaEnrollAct.this);
        if(ceArea!=null){
            if(ceArea.equals("null")){
                txvSEAreaValue.setText(" ");
            }else {
                txvSEAreaValue.setText(ceArea);
            }

        }
        String distributor = getValueFromSharedPreferences("db_name",SummerHangamaEnrollAct.this);
        if(distributor!=null){
            if(distributor.equals("null")){
                txvDistribValue.setText("");
            }else {
                txvDistribValue.setText(distributor);
            }

        }
        String cluster = getValueFromSharedPreferences("cluster_id",SummerHangamaEnrollAct.this);
        if(cluster!=null){
             if(cluster.equals("null")){

                 txvClusterValue.setText(" ");
             }else {

                 txvClusterValue.setText(cluster);
             }

        }
        String PSR = getValueFromSharedPreferences("psr_name",SummerHangamaEnrollAct.this);
        if(PSR!=null){
            if(PSR.equals("null")){
                txvPSRValue.setText(" ");
            }else {
                txvPSRValue.setText(PSR);
            }

        }
        String outletName = getValueFromSharedPreferences("outlet_name",SummerHangamaEnrollAct.this);
        if(outletName!=null){
            if(outletName.equals("null")){
                txvOutValue.setText(" ");
            }else {
                txvOutValue.setText(outletName);
            }

        }

        String retName = getValueFromSharedPreferences("retailer_name",SummerHangamaEnrollAct.this);
        if(retName!=null){
            if(retName.equals("null")){
                txvRetNameValue.setText(" ");
            }else {
                txvRetNameValue.setText(retName);
            }

        }
        String retMobile = getValueFromSharedPreferences("retailer_mobile",SummerHangamaEnrollAct.this);
        if(retMobile!=null){
            if(retMobile.equals("null")){
                edtRetMobValue.setText(" ");
            }else {
                edtRetMobValue.setText(retMobile);
            }

        }
        String retBkashName = getValueFromSharedPreferences("ret_bikash_name",SummerHangamaEnrollAct.this);
        if(retBkashName!=null){
            if(retBkashName.equals("null")){
                edtBkashName.setText(" ");
            }else {
                edtBkashName.setText(retBkashName);
            }

        }
        String retBkashNo = getValueFromSharedPreferences("ret_bikash_no",SummerHangamaEnrollAct.this);
        if(retBkashNo!=null){
            if(retBkashNo.equals("null")){
                edtBkashNo.setText(" ");
            }else {
                edtBkashNo.setText(retBkashNo);
            }

        }
        String outletAddress = getValueFromSharedPreferences("outlet_address",SummerHangamaEnrollAct.this);
        if(outletAddress!=null){
            if(outletAddress.equals("null")){
                txvOutAddress.setText(" ");
            }else {
                txvOutAddress.setText(outletAddress);
            }

        }

        isBkashNo =false;
        isBkashName = false;
        isRetMob = false;
        isOutAddr = false;

    }
//    @Override
//    public void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        if(isComingOnResume){
//            canListenInput = false;
//        }else {
//            canListenInput = true;
//        }
//
//    }



}
