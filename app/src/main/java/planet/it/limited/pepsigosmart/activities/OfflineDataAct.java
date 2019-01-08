package planet.it.limited.pepsigosmart.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.android.policy.TimeWindow;
import com.cloudinary.utils.StringUtils;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import planet.it.limited.pepsigosmart.R;
import planet.it.limited.pepsigosmart.database.LocalStoragePepsiDB;
import planet.it.limited.pepsigosmart.model.SurveyModel;
import planet.it.limited.pepsigosmart.task.OfflineDataSendToServer;
import planet.it.limited.pepsigosmart.utils.BusyDialog;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;
import planet.it.limited.pepsigosmart.utils.TableHelper;

import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getValueFromSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveToSharedPreferences;

public class OfflineDataAct extends AppCompatActivity implements View.OnClickListener{
    public static TableLayout tableLayout,cloumnHeader;
    LocalStoragePepsiDB localStoragePepsiDB;
    TableHelper tableHelper;
    String []headers ;
    String [][]allRows;
    String [][]specificOfflineData;

    ArrayList dataList ;
    @BindView(R.id.btn_back)
    Button btnPageBack;
    Toolbar toolbar;
    ClearAllSaveData clearAllSaveData;
    String outletCode = " ";
    String coolerStatus =" ";
    String coolerBrand = " ";
    String signageStatus =  " ";
    String signageBrand =  " ";
  //  String enrollOrder =  " ";
    String volumeTar =  " ";
    String remarks = " ";
    String picOneSPath =  " ";
    String picTwoSPath = " ";
    String startTime =  " ";
    String endTime =  " ";
    String latitude = " ";
    String longitude = " ";

    String picOneLPath = " ";
    String picTwoLPath = " ";
    String userName = " ";
    BusyDialog mBusyDialog;
    OfflineDataSendToServer offlineDataSendToServer;
    boolean isAvailableInternet;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_data);
        toolbar = (Toolbar)findViewById(R.id.toolbar_offline_data) ;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        ButterKnife.bind(this);
        tableLayout = (TableLayout) findViewById(R.id.gridview);
        localStoragePepsiDB = new LocalStoragePepsiDB(OfflineDataAct.this,tableLayout,cloumnHeader);
        offlineDataSendToServer = new OfflineDataSendToServer(OfflineDataAct.this,OfflineDataAct.this);

        localStoragePepsiDB.open();
        clearAllSaveData = new ClearAllSaveData(OfflineDataAct.this);
        tableHelper = new TableHelper(OfflineDataAct.this);
        userName = getValueFromSharedPreferences("user_name",OfflineDataAct.this);

        allRows = tableHelper.getAllOfflineData();

        addHeaders();

        addData();

        btnPageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(OfflineDataAct.this,SummerHangamaEnrollAct.class);
                startActivity(signIn);
                ActivityCompat.finishAffinity(OfflineDataAct.this);
            }
        });
    }
    /**
     * This function add the headers to the table
     **/
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void addHeaders() {

        TableLayout tl = findViewById(R.id.gridview);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());

        //  tr.addView(getTextView(0, "Auditor id", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tr.addView(getTextView(0, "Outlet Code", Color.BLACK, Typeface.BOLD, ContextCompat.getDrawable(OfflineDataAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Cooler Status", Color.BLACK, Typeface.BOLD, ContextCompat.getDrawable(OfflineDataAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Cooler Brand", Color.BLACK, Typeface.BOLD,  ContextCompat.getDrawable(OfflineDataAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Signage Status", Color.BLACK, Typeface.BOLD, ContextCompat.getDrawable(OfflineDataAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Signage Brand", Color.BLACK, Typeface.BOLD,  ContextCompat.getDrawable(OfflineDataAct.this,R.drawable.table_header_border)));
       // tr.addView(getTextView(0, "Enrolled Order", Color.BLACK, Typeface.BOLD, ContextCompat.getDrawable(OfflineDataAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Volume Target", Color.BLACK, Typeface.BOLD,  ContextCompat.getDrawable(OfflineDataAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Remarks", Color.BLACK, Typeface.BOLD,  ContextCompat.getDrawable(OfflineDataAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Entry Date", Color.BLACK, Typeface.BOLD,  ContextCompat.getDrawable(OfflineDataAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Status", Color.BLACK, Typeface.BOLD, ContextCompat.getDrawable(OfflineDataAct.this,R.drawable.table_header_border)));


        tl.addView(tr, getTblLayoutParams());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private TextView getTextView(int id, String title, int color, int typeface, Drawable bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        // tv.setBackgroundColor(bgColor);
        tv.setBackground(bgColor);
        //tv.setLayoutParams(getLayoutParams());
        tv.setHeight(80);
        // tv.setWidth(330);
        tv.setGravity(Gravity.CENTER );



        return tv;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private TextView getRowsTextView(int id, String title, int color, int typeface, Drawable bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title);
        tv.setTextColor(color);
      //  tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackground(bgColor);
       // tv.setLayoutParams(getLayoutParams());
        tv.setHeight(100);
        tv.setGravity(Gravity.CENTER );
        tv.setOnClickListener(this);
        return tv;
    }

    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void addData() {
        dataList = new ArrayList();
        headers = tableHelper.tableHeaders;
        int allInputData = 0 ;
        if(allRows!=null){
            allInputData = allRows.length;
        }

        //   int pos = 0;
        //   int numCompanies = companies.length;
        TableLayout tl = findViewById(R.id.gridview);
        for (int i = 0; i < allInputData; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
//            tr.addView(getRowsTextView(i + 1, allRows[i][13], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            tr.addView(getRowsTextView(  0, allRows[i][0], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            tr.addView(getRowsTextView(  1, allRows[i][1], Color.WHITE, Typeface.NORMAL,ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            tr.addView(getRowsTextView(  2, allRows[i][2], Color.WHITE, Typeface.NORMAL,ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));

            tr.addView(getRowsTextView(  3, allRows[i][3], Color.WHITE, Typeface.NORMAL,ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            tr.addView(getRowsTextView(  4, allRows[i][4], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            //tr.addView(getRowsTextView( 5, allRows[i][5], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            tr.addView(getRowsTextView( 5, allRows[i][5], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            tr.addView(getRowsTextView(  6, allRows[i][6], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            tr.addView(getRowsTextView(  7, allRows[i][7], Color.WHITE, Typeface.NORMAL,ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));


//            tr.addView(getRowsTextView(  9, allRows[i][9], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
//            tr.addView(getRowsTextView(  10, allRows[i][10], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
//            tr.addView(getRowsTextView(  11, allRows[i][11], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
//
//            tr.addView(getRowsTextView(  12, allRows[i][12], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
//            tr.addView(getRowsTextView(  13, allRows[i][13], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
//
//            tr.addView(getRowsTextView( 14, allRows[i][14], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
//            tr.addView(getRowsTextView(  15, allRows[i][15], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            // tr.addView(getRowsTextView(  16, allRows[i][16], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            // tr.addView(getRowsTextView(  17, allRows[i][17], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));

            String syncStatus = allRows[i][14];
            if(syncStatus.equals("failled")){
                String uderLineText = allRows[i][14] ;

                tr.addView(getRowsTextView(  14,uderLineText , Color.RED, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            }else {
                tr.addView(getRowsTextView(  14, allRows[i][14], Color.GREEN, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            }


            TextView picOneL = getRowsTextView(  8, allRows[i][8], Color.WHITE, Typeface.NORMAL,ContextCompat.getDrawable(this,R.drawable.table_row_text_color));
            picOneL.setVisibility(View.GONE);
            TextView picOneS =getRowsTextView(  9, allRows[i][9], Color.WHITE, Typeface.NORMAL,ContextCompat.getDrawable(this,R.drawable.table_row_text_color));
            picOneS.setVisibility(View.GONE);

            TextView entryTime =getRowsTextView(  10, allRows[i][10], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color));
            entryTime.setVisibility(View.GONE);

            TextView latitude = getRowsTextView(  11, allRows[i][11], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color));
            latitude.setVisibility(View.GONE);
            TextView longitude =getRowsTextView(  12, allRows[i][12], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color));
            longitude.setVisibility(View.GONE);
            TextView entryBy =  getRowsTextView(  13, allRows[i][13], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color));
            entryBy.setVisibility(View.GONE);




            tr.addView(picOneL);
            tr.addView(picOneS);

            tr.addView(entryTime);
            tr.addView(latitude);
            tr.addView(longitude);
            tr.addView(entryBy);

//            picOneL.setVisibility(View.GONE);
//            picOneS.setVisibility(View.GONE);
//            picTwoL.setVisibility(View.GONE);
//            picTwoS.setVisibility(View.GONE);
//            entryTime.setVisibility(View.GONE);
//            latitude.setVisibility(View.GONE);
//            longitude.setVisibility(View.GONE);
//            entryBy.setVisibility(View.GONE);
            tl.addView(tr, getTblLayoutParams());





        }
    }
    @Override
    public void onClick(View v) {
        isAvailableInternet = clearAllSaveData.isConnectingToInternet(OfflineDataAct.this);

        TableRow tablerow = (TableRow)v.getParent();
        TextView iOutletCode = (TextView) tablerow.getChildAt(0);
        TextView iCoolerStatus = (TextView) tablerow.getChildAt(1);
        TextView iCoolerBrand = (TextView) tablerow.getChildAt(2);
        TextView iSignageStatus = (TextView) tablerow.getChildAt(3);
        TextView iSignageBrand = (TextView) tablerow.getChildAt(4);
        TextView iEnrolledOrder = (TextView) tablerow.getChildAt(5);
        TextView iVolTarget = (TextView) tablerow.getChildAt(6);
        TextView iRemarks = (TextView) tablerow.getChildAt(7);
        TextView iEntryDate= (TextView) tablerow.getChildAt(8);
        TextView iPicOneLPath= (TextView) tablerow.getChildAt(10);

        TextView iPicOneSPath= (TextView) tablerow.getChildAt(11);

        TextView iEntryTime= (TextView) tablerow.getChildAt(14);
        TextView iLatitude= (TextView) tablerow.getChildAt(15);
        TextView iLongitude= (TextView) tablerow.getChildAt(16);
        TextView iStatus = (TextView) tablerow.getChildAt(8);


        String syncStatus = iStatus.getText().toString();
        if(syncStatus.equals("failled")){
            outletCode = iOutletCode.getText().toString();
            localStoragePepsiDB.open();
            ArrayList<SurveyModel> failedSurList = localStoragePepsiDB.getFailledData(userName,outletCode);


//             coolerStatus =  iCoolerStatus.getText().toString();
//             coolerBrand =  iCoolerBrand.getText().toString();
//             signageStatus =  iSignageStatus.getText().toString();
//              signageBrand =  iSignageBrand.getText().toString();
//              enrollOrder =  iEnrolledOrder.getText().toString();
//             volumeTar =  iVolTarget.getText().toString();
//             remarks =  iRemarks.getText().toString();
//             picOneSPath =  iPicOneSPath.getText().toString();
//             picTwoSPath =  iPicTwoSPath.getText().toString();
//             entryTime =  iEntryTime.getText().toString();
//             latitude =iLatitude.getText().toString();
//             longitude = iLongitude.getText().toString();

            //picOneLPath = iPicOneLPath.getText().toString();
         //   picTwoLPath = iPicTwoLPath.getText().toString();

            // for(int i=0;failedSurList.size()>0;i++){
            coolerStatus = failedSurList.get(0).getCoolerStatus();
            coolerBrand = failedSurList.get(0).getCoolerBrand();
            signageStatus = failedSurList.get(0).getSignageStatus();
            signageBrand = failedSurList.get(0).getSignageBrand();
            //enrollOrder = failedSurList.get(0).getEnrolledOrder();
            volumeTar = failedSurList.get(0).getVolumeTarget();
            remarks = failedSurList.get(0).getRemarks();
            picOneSPath = failedSurList.get(0).getOutPicOneSPath();
            picOneLPath = failedSurList.get(0).getOutPicOneLPath();
            startTime = failedSurList.get(0).getStartTime();
            endTime = failedSurList.get(0).getEndTime();
            latitude = failedSurList.get(0).getLatitude();
            longitude = failedSurList.get(0).getLongitude();

            //  }

            if(picOneSPath.equals("") || picOneSPath==null) {
                if (isAvailableInternet) {

                    try {
                        mBusyDialog = new BusyDialog(OfflineDataAct.this, false, "");
                        mBusyDialog.show();
                        clearAllSaveData.initCloudinaryLib();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String requestId = MediaManager.get().upload(picOneLPath)
                            .constrain(TimeWindow.immediate()).option("folder", "summer_hangama_enroll/")
                            .callback(new UploadCallback() {
                                @Override
                                public void onStart(String requestId) {
                                    Log.d("onStart", requestId);
                                    // your code here
                                }

                                @Override
                                public void onProgress(String requestId, long bytes, long totalBytes) {
                                    // example code starts here

                                }

                                @Override
                                public void onSuccess(String requestId, Map resultData) {
                                    // your code here
                                    Log.d("onSuccess", requestId);

                                    if (resultData != null && resultData.size() > 0) {
                                        if (resultData.containsKey("url")) {
                                            String imageURL = (String) resultData.get("url");
                                            String cloudinaryID = (String) resultData.get("public_id");
                                            int width = (Integer) resultData.get("width");
                                            int height = (Integer) resultData.get("height");
                                            if (imageURL != null && !StringUtils.isEmpty(imageURL)) {
                                                //logic here

                                                saveToSharedPreferences("out_photo_one_s_path", imageURL, OfflineDataAct.this);
                                                localStoragePepsiDB.open();
                                                localStoragePepsiDB.updatePicOneSPath(outletCode, imageURL);
                                                mBusyDialog.dismis();

                                            } else {
                                                saveToSharedPreferences("out_photo_one_s_path", "", OfflineDataAct.this);
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onError(String requestId, ErrorInfo error) {
                                    // your code here
                                    Log.d("onSuccess", requestId);
                                    saveToSharedPreferences("out_photo_one_s_path", "", OfflineDataAct.this);
                                }

                                @Override
                                public void onReschedule(String requestId, ErrorInfo error) {
                                    // your code here
                                }
                            }).dispatch();

                }

            }else {
                clearAllSaveData.openDialog("Your Device is Offline");
            }

            }

            //data send to server


            if(isAvailableInternet){
                if(picOneSPath!=null && picTwoSPath!=null){
                    if((!picOneSPath.equals("") && picOneSPath.length()>0 ) ){

                        try{
                            offlineDataSendToServer.sendDataToServer(outletCode,coolerStatus,coolerBrand,signageStatus,
                                    signageBrand,Integer.parseInt(volumeTar.trim()),remarks,
                                    picOneSPath,latitude,longitude,startTime,endTime,clearAllSaveData.getDeviceName() + clearAllSaveData.getAndroidDeviceModel() , ClearAllSaveData.apkVersion);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }else {
                        // Toast.makeText(ViewInputDataActivity.this,"Your cooler pic and outlet pic code missing,please update offline data",Toast.LENGTH_SHORT).show();
                       // clearAllSaveData.openDialog("Your picture one and picture two path missing,please update offline data");
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                localStoragePepsiDB.open();
                                ArrayList<SurveyModel> failedSurList = localStoragePepsiDB.getFailledData(userName,outletCode);
                                coolerStatus = failedSurList.get(0).getCoolerStatus();
                                coolerBrand = failedSurList.get(0).getCoolerBrand();
                                signageStatus = failedSurList.get(0).getSignageStatus();
                                signageBrand = failedSurList.get(0).getSignageBrand();

                                volumeTar = failedSurList.get(0).getVolumeTarget();
                                remarks = failedSurList.get(0).getRemarks();
                                picOneSPath = failedSurList.get(0).getOutPicOneSPath();

                                startTime = failedSurList.get(0).getEntryDate();
                                endTime = failedSurList.get(0).getEndTime();
                                latitude = failedSurList.get(0).getLatitude();
                                longitude = failedSurList.get(0).getLongitude();

                                if ((!picOneSPath.equals("") && picOneSPath.length()>0 ) ) {
                                    offlineDataSendToServer.sendDataToServer(outletCode,coolerStatus,coolerBrand,signageStatus,
                                            signageBrand,Integer.parseInt(volumeTar.trim()),remarks,
                                            picOneSPath,latitude,longitude,startTime,endTime,clearAllSaveData.getDeviceName() + clearAllSaveData.getAndroidDeviceModel() ,
                                            ClearAllSaveData.apkVersion);


                                }else {
                                    clearAllSaveData.openDialog("Please Click Again this Failled Option");
                                    //Toast.makeText(OfflineDataActivity.this, "Please Click Again this Failled Option", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },10000);// set time as per your requirement
                    }
                }


            }else {
                clearAllSaveData.openDialog("Your Device is Offline");
                // Toast.makeText(ViewInputDataActivity.this,"Your Device is Offline",Toast.LENGTH_SHORT).show();
            }

        }
    }

