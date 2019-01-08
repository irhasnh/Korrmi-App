package planet.it.limited.pepsigosmart.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import planet.it.limited.pepsigosmart.task.FailledDataSendToServer;
import planet.it.limited.pepsigosmart.task.SurveyDataSendToServer;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;
import planet.it.limited.pepsigosmart.utils.TableHelper;

import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getValueFromSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveToSharedPreferences;

public class ShowAllIputDataAct extends DrawerCompatActivity implements View.OnClickListener{
    private DrawerLayout drawerLayout;

    private TableLayout tableLayout,cloumnHeader;
    LocalStoragePepsiDB localStorageDB;
    TableHelper tableHelper;
    String []headers ;
    String [][]allRows;
    TextView txvNoOfRows,todaysAllRows ;
    public static int todaysRowCount ;
    Toolbar toolbar;
    ArrayList dataList ;
    FailledDataSendToServer failledDataSendToServer;
    String userName = " ";
    ClearAllSaveData clearAllSaveData;

    @BindView(R.id.btn_back)
    Button btnPageBack;
    @BindView(R.id.txv_user_name)
    TextView txvUserName;
    @BindView(R.id.txv_mobile)
    TextView txvMobile;


    String outletCode = " ";
    String coolerStatus =" ";
    String coolerBrand = " ";
    String signageStatus =  " ";
    String signageBrand =  " ";
   // String enrollOrder =  " ";
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

    String userMobile = " ";



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_iput_data);
        initViews();
        removeToolbar();
        setupDrawer();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void initViews(){
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
        clearAllSaveData = new ClearAllSaveData(ShowAllIputDataAct.this);

        tableLayout = (TableLayout) findViewById(R.id.gridview);
        txvNoOfRows = (TextView)findViewById(R.id.txv_no_total) ;
        todaysAllRows = (TextView)findViewById(R.id.txv_today_no_rows) ;

        userName = getValueFromSharedPreferences("name",ShowAllIputDataAct.this);
        userMobile = getValueFromSharedPreferences("user_name",ShowAllIputDataAct.this);
        if(userName!=null){
            txvUserName.setText(userName);
        }
        if(userMobile!=null){
            txvMobile.setText(userMobile);
        }


        String entryDate = clearAllSaveData.getCurrentEntryDate();

        //cloumnHeader =(TableLayout) findViewById(R.id.headergridview);

        localStorageDB = new LocalStoragePepsiDB(ShowAllIputDataAct.this,tableLayout,cloumnHeader);
        localStorageDB.open();
        tableHelper = new TableHelper(ShowAllIputDataAct.this);
        allRows = tableHelper.getAllInputData();
        todaysRowCount = localStorageDB.getTodaysNoOfRows(entryDate,userMobile);

        if(todaysRowCount>0){
            todaysAllRows.setText(String.valueOf(todaysRowCount));
        }

        addHeaders();

        addData();
        failledDataSendToServer = new FailledDataSendToServer(ShowAllIputDataAct.this,ShowAllIputDataAct.this);

        btnPageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(ShowAllIputDataAct.this,SummerHangamaEnrollAct.class);
                startActivity(signIn);
                ActivityCompat.finishAffinity(ShowAllIputDataAct.this);
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
        tr.addView(getTextView(0, "Outlet Code", Color.BLACK, Typeface.BOLD,ContextCompat.getDrawable(ShowAllIputDataAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Cooler Status", Color.BLACK, Typeface.BOLD, ContextCompat.getDrawable(ShowAllIputDataAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Cooler Brand", Color.BLACK, Typeface.BOLD,ContextCompat.getDrawable(ShowAllIputDataAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Signage Status", Color.BLACK, Typeface.BOLD,ContextCompat.getDrawable(ShowAllIputDataAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Signage Brand", Color.BLACK, Typeface.BOLD,ContextCompat.getDrawable(ShowAllIputDataAct.this,R.drawable.table_header_border)));
        //tr.addView(getTextView(0, "Enrolled Order", Color.BLACK, Typeface.BOLD,ContextCompat.getDrawable(ShowAllIputDataAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Volume Target", Color.BLACK, Typeface.BOLD,ContextCompat.getDrawable(ShowAllIputDataAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Remarks", Color.BLACK, Typeface.BOLD, ContextCompat.getDrawable(ShowAllIputDataAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Entry Date", Color.BLACK, Typeface.BOLD, ContextCompat.getDrawable(ShowAllIputDataAct.this,R.drawable.table_header_border)));
        tr.addView(getTextView(0, "Status", Color.BLACK, Typeface.BOLD, ContextCompat.getDrawable(ShowAllIputDataAct.this,R.drawable.table_header_border)));


        tl.addView(tr, getTblLayoutParams());
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void addData() {
        dataList = new ArrayList();
        headers = tableHelper.tableHeaders;
        int allInputData = 0 ;
        if(allRows!=null){
            allInputData = allRows.length;
            txvNoOfRows.setText(String.valueOf(allInputData));
        }



        //   int pos = 0;

        //   int numCompanies = companies.length;
        TableLayout tl = findViewById(R.id.gridview);
        for (int i = 0; i < allInputData; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
//            tr.addView(getRowsTextView(i + 1, allRows[i][13], Color.BLACK, Typeface.NORMAL, ContextCompat.getColor(this, R.color.cell_background_color)));
            tr.addView(getRowsTextView(  0, allRows[i][0], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            tr.addView(getRowsTextView(  1, allRows[i][1], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            tr.addView(getRowsTextView(  2, allRows[i][2], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));

            tr.addView(getRowsTextView(  3, allRows[i][3], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            tr.addView(getRowsTextView(  4, allRows[i][4], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
           // tr.addView(getRowsTextView( 5, allRows[i][5], Color.WHITE, Typeface.NORMAL,ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            tr.addView(getRowsTextView( 5, allRows[i][5], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            tr.addView(getRowsTextView(  6, allRows[i][6], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));
            tr.addView(getRowsTextView(  7, allRows[i][7], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color)));


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


            TextView picOneL = getRowsTextView(  8, allRows[i][8], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color));
            picOneL.setVisibility(View.GONE);
            TextView picOneS =getRowsTextView(  9, allRows[i][9], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color));
            picOneS.setVisibility(View.GONE);

            TextView entryTime =getRowsTextView(  10, allRows[i][10], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color));
            entryTime.setVisibility(View.GONE);

            TextView latitude = getRowsTextView(  11, allRows[i][11], Color.WHITE, Typeface.NORMAL,ContextCompat.getDrawable(this,R.drawable.table_row_text_color));
            latitude.setVisibility(View.GONE);
            TextView longitude =getRowsTextView(  12, allRows[i][12], Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(this,R.drawable.table_row_text_color));
            longitude.setVisibility(View.GONE);
            TextView entryBy =  getRowsTextView(  13, allRows[i][13], Color.WHITE, Typeface.NORMAL,ContextCompat.getDrawable(this,R.drawable.table_row_text_color));
            entryBy.setVisibility(View.GONE);




            tr.addView(picOneL);
            tr.addView(picOneS);
//            tr.addView(picTwoL);
//            tr.addView(picTwoS);
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
//    private TextView getRowsTextView(int id, String title, int color, int typeface, int bgColor) {
//        TextView tv = new TextView(this);
//        tv.setId(id);
//        tv.setText(title);
//        tv.setTextColor(color);
//        tv.setPadding(40, 40, 40, 40);
//        tv.setTypeface(Typeface.DEFAULT, typeface);
//        tv.setBackgroundColor(bgColor);
//        tv.setLayoutParams(getLayoutParams());
//        tv.setOnClickListener(this);
//        return tv;
//    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private TextView getRowsTextView(int id, String title, int color, int typeface, Drawable bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title);
        tv.setTextColor(color);
       // tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackground(bgColor);
        //tv.setLayoutParams(getLayoutParams());
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

    @Override
    public void onClick(View v) {
        TableRow tablerow = (TableRow)v.getParent();
        TextView iOutletCode = (TextView) tablerow.getChildAt(0);
        TextView iCoolerStatus = (TextView) tablerow.getChildAt(1);
        TextView iCoolerBrand = (TextView) tablerow.getChildAt(2);
        TextView iSignageStatus = (TextView) tablerow.getChildAt(3);
        TextView iSignageBrand = (TextView) tablerow.getChildAt(4);
       // TextView iEnrolledOrder = (TextView) tablerow.getChildAt(5);
        TextView iVolTarget = (TextView) tablerow.getChildAt(6);
        TextView iRemarks = (TextView) tablerow.getChildAt(7);
        TextView iEntryDate= (TextView) tablerow.getChildAt(8);
        TextView iPicOneLPath= (TextView) tablerow.getChildAt(10);
        //TextView iPicTwoLPath= (TextView) tablerow.getChildAt(12);
        TextView iPicOneSPath= (TextView) tablerow.getChildAt(11);
        //TextView iPicTwoSPath= (TextView) tablerow.getChildAt(13);
        TextView iEntryTime= (TextView) tablerow.getChildAt(14);
        TextView iLatitude= (TextView) tablerow.getChildAt(15);
        TextView iLongitude= (TextView) tablerow.getChildAt(16);
        TextView iStatus = (TextView) tablerow.getChildAt(8);


        String syncStatus = iStatus.getText().toString();
        if(syncStatus.equals("failled")){
            outletCode = iOutletCode.getText().toString();
            localStorageDB.open();
            ArrayList<SurveyModel> failedSurList = localStorageDB.getFailledData(userMobile,outletCode);


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

            picOneLPath = iPicOneLPath.getText().toString();
           // picTwoLPath = iPicTwoLPath.getText().toString();

            // for(int i=0;failedSurList.size()>0;i++){
            coolerStatus = failedSurList.get(0).getCoolerStatus();
            coolerBrand = failedSurList.get(0).getCoolerBrand();
            signageStatus = failedSurList.get(0).getSignageStatus();
            signageBrand = failedSurList.get(0).getSignageBrand();
            //enrollOrder = failedSurList.get(0).getEnrolledOrder();
            volumeTar = failedSurList.get(0).getVolumeTarget();
            remarks = failedSurList.get(0).getRemarks();
            picOneSPath = failedSurList.get(0).getOutPicOneSPath();
           // picTwoSPath = failedSurList.get(0).getOutPicTwoSPath();
            startTime = failedSurList.get(0).getStartTime();
            endTime = failedSurList.get(0).getEndTime();
            latitude = failedSurList.get(0).getLatitude();
            longitude = failedSurList.get(0).getLongitude();

            //  }



            //data send to server
            boolean isAvailableInternet = clearAllSaveData.isConnectingToInternet(ShowAllIputDataAct.this);

            if(isAvailableInternet){
                if(picOneSPath!=null && picTwoSPath!=null){
                    if((!picOneSPath.equals("") && picOneSPath.length()>0 ) ){

                        failledDataSendToServer.sendDataToServer(outletCode,coolerStatus,coolerBrand,signageStatus,
                                signageBrand,Integer.parseInt(volumeTar.trim()),remarks,
                                picOneSPath,latitude,longitude,startTime,endTime,clearAllSaveData.getDeviceName() + clearAllSaveData.getAndroidDeviceModel() ,ClearAllSaveData.apkVersion);
                    }else {
                        // Toast.makeText(ViewInputDataActivity.this,"Your cooler pic and outlet pic code missing,please update offline data",Toast.LENGTH_SHORT).show();
                        clearAllSaveData.openDialog("Your picture one  missing,please update offline data");

                    }
                }


            }else {
                clearAllSaveData.openDialog("Your Device is Offline");
                // Toast.makeText(ViewInputDataActivity.this,"Your Device is Offline",Toast.LENGTH_SHORT).show();
            }

        }


    }
}
