package planet.it.limited.pepsigosmart.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import planet.it.limited.pepsigosmart.R;
import planet.it.limited.pepsigosmart.database.LocalStoragePepsiDB;
import planet.it.limited.pepsigosmart.model.ExistsModel;
import planet.it.limited.pepsigosmart.utils.APIList;
import planet.it.limited.pepsigosmart.utils.BusyDialog;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;

import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getValueFromSharedPreferences;

public class AllExistingDataShowAct extends AppCompatActivity {

    String allExistingDataAPI = " ";
    String totalDataAPI = " ";
    TableLayout tblLayout;
    //TableRow tbrHeader,tbrCell;
    TextView txvTotalResult,txvTotalDurationFirst,txvTotalDurationLast;
    ImageButton btnNavigationRight,btnNavigationBack;
    ImageView btnDateFirst,btnDateLast;

    ImageView imgvClosePWindow,imgvOutPhoto;
    public static int page = 0;
    Button btnSearchDate;
    EditText edtFirstDate,edtLastDate;
    String userName = " ";
    ClearAllSaveData clearAllSaveData;
    BusyDialog mBusyDialog;
    Toolbar toolbar;
    ArrayList<ExistsModel> existDataList = new ArrayList<>();
    boolean isAvailableInternet;
    public static boolean checkLast;
    static String firstDate = "";
    static String lastDate = "";
    public static int totalData = 0;
    private int month;
    private int year;
    private Calendar calendar;
    public static String PATTERN = "dd-MM-yyyy";
    String systemDate = "";
    String firstDayOfMonth = "";
   // String outletCode = " ";
    //String outPhotoURI = " ";
    LocalStoragePepsiDB localStoragePepsiDB;
    int allVisited = 0;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_existing_data_show);
        toolbar = (Toolbar)findViewById(R.id.toolbar_existing_data) ;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        initViews();


    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void initViews(){
        ButterKnife.bind(this);

        localStoragePepsiDB = new LocalStoragePepsiDB(AllExistingDataShowAct.this);
        tblLayout = (TableLayout)findViewById(R.id.tbl_layout_all_existing_data);
        txvTotalResult = (TextView)findViewById(R.id.txv_no_total);
        txvTotalDurationFirst = (TextView)findViewById(R.id.txv_total_duration_first);
        txvTotalDurationLast = (TextView)findViewById(R.id.txv_total_duration_last);
        btnNavigationBack =(ImageButton) findViewById(R.id.btn_navigation_back);
        btnNavigationRight = (ImageButton) findViewById(R.id.btn_navigation_right);
        btnDateFirst = (ImageView) findViewById(R.id.btn_date_first);
        btnDateLast = (ImageView) findViewById(R.id.btn_date_last);
        edtFirstDate = (EditText)findViewById(R.id.edt_date_first);
        edtLastDate = (EditText)findViewById(R.id.edt_date_last);
        btnSearchDate = (Button) findViewById(R.id.btn_search);
        btnSearchDate.setTransformationMethod(null);

        localStoragePepsiDB.open();
        localStoragePepsiDB.clearPhotoTable();

        clearAllSaveData = new ClearAllSaveData(AllExistingDataShowAct.this);
        userName = getValueFromSharedPreferences("user_name",AllExistingDataShowAct.this);
        systemDate = clearAllSaveData.getCurrentDate();
        firstDayOfMonth =  getDefaultMonthDate();
        edtFirstDate.setText(firstDayOfMonth);
        edtLastDate.setText(systemDate);
        allExistingDataAPI =  APIList.searchExistingDataAPI + userName + "/" + firstDayOfMonth + "/" + systemDate + "?" + "page=" + page ;

        isAvailableInternet = clearAllSaveData.isConnectingToInternet(AllExistingDataShowAct.this);
        if(isAvailableInternet){
            GetAllExistingDataTask getAllExistingDataTask = new GetAllExistingDataTask();
            getAllExistingDataTask.execute();
        }else {

            clearAllSaveData.openDialog("Sorry,Your Device is Offline");
        }

        addHeaders();
        firstDate = edtFirstDate.getText().toString();
        lastDate = edtLastDate.getText().toString();

        btnNavigationRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(page>=0){
                    page++;
                }
                allExistingDataAPI =  APIList.searchExistingDataAPI + userName + "/" + firstDate + "/" + lastDate + "?" + "page=" + page ;
                if(isAvailableInternet){
                    if(!checkLast){
                        existDataList.clear();
                        redrawEverything();
                        addHeaders();
                        GetAllExistingDataTask getAllExistingDataTask = new GetAllExistingDataTask();
                        getAllExistingDataTask.execute();
                    }

                }else {
                    clearAllSaveData.openDialog("Sorry,Your Device is Offline");
                }

            }
        });


        btnNavigationBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(page>-1){
                    page--;
                    if(page==0){
                        txvTotalDurationFirst.setText( String.valueOf("1"));
                        btnNavigationBack.setVisibility(View.GONE);
                        btnNavigationRight.setVisibility(View.VISIBLE);
                        redrawEverything();
                        addHeaders();
                        checkLast = false;

                    }
                }


                if(checkLast){

                    redrawEverything();
                    addHeaders();

                }
                allExistingDataAPI =  APIList.searchExistingDataAPI + userName + "/" + firstDate + "/" + lastDate + "?" + "page=" + page ;
                if(isAvailableInternet){
                    existDataList.clear();
                    redrawEverything();
                    addHeaders();
                    GetAllExistingDataTask getAllExistingDataTask = new GetAllExistingDataTask();
                    getAllExistingDataTask.execute();

                }else {
                    clearAllSaveData.openDialog("Sorry,Your Device is Offline");
                }

            }
        });

        btnDateFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDatePickerFirst();

            }
        });

        btnDateLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePickerLast();

            }
        });


        btnSearchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstDate = edtFirstDate.getText().toString();
                lastDate = edtLastDate.getText().toString();
                page = 0;
                allExistingDataAPI =  APIList.searchExistingDataAPI + userName + "/" + firstDate + "/" + lastDate + "?" + "page=" + page ;

                btnNavigationRight.setVisibility(View.VISIBLE);
                btnNavigationBack.setVisibility(View.GONE);
                existDataList.clear();
                redrawEverything();
                addHeaders();
                GetAllExistingDataTask getAllExistingDataTask = new GetAllExistingDataTask();
                getAllExistingDataTask.execute();



            }
        });


        TotalDataTask totalDataTask = new TotalDataTask();
        totalDataTask.execute();

    }



    private void redrawEverything()
    {
        tblLayout.removeAllViews();

    }
    public void openDatePickerFirst() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear  = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay   = c.get(Calendar.DAY_OF_MONTH);

        //launch datepicker modal
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                        edtFirstDate.setText(view.getDayOfMonth() +
                                " - " + (view.getMonth()+1) +
                                " - " + view.getYear());


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void openDatePickerLast() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear  = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay   = c.get(Calendar.DAY_OF_MONTH);
        //launch datepicker modal
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        edtLastDate.setText(view.getDayOfMonth() +
                                " - " + (view.getMonth()+1) +
                                " - " + view.getYear());

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void addHeaders() {

        //TableLayout tl = findViewById(R.id.headergridview);
        TableRow tbrHeader = new TableRow(this);
        tbrHeader.setLayoutParams(getLayoutParams());

        //  tr.addView(getTextView(0, "Auditor id", Color.WHITE, Typeface.BOLD, R.color.colorAccent));
        tbrHeader.addView(getTextView(0, "OUTLET CODE", Color.BLACK, Typeface.NORMAL,ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_header_border)));
        tbrHeader.addView(getTextView(0, "OUTLET", Color.BLACK, Typeface.NORMAL,ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_header_border)));
        tbrHeader.addView(getTextView(0, "Bkash No", Color.BLACK, Typeface.NORMAL,ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_header_border)));
        tbrHeader.addView(getTextView(0, "ADDRESS", Color.BLACK, Typeface.NORMAL,ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_header_border)));
        tbrHeader.addView(getTextView(0, "Cooler Status", Color.BLACK, Typeface.NORMAL, ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_header_border)));
        tbrHeader.addView(getTextView(0, "Cooler Branding", Color.BLACK, Typeface.NORMAL,ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_header_border)));
        tbrHeader.addView(getTextView(0, "Signage Status", Color.BLACK, Typeface.NORMAL, ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_header_border)));
        tbrHeader.addView(getTextView(0, "Signage Branding", Color.BLACK, Typeface.NORMAL,ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_header_border)));
       // tbrHeader.addView(getTextView(0, "Enrolled Order", Color.BLACK, Typeface.NORMAL,ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_header_border)));
        tbrHeader.addView(getTextView(0, "Volume Target", Color.BLACK, Typeface.NORMAL, ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_header_border)));
        tbrHeader.addView(getTextView(0, "VOL 2018(Feb to Apr 2018)", Color.BLACK, Typeface.NORMAL,ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_header_border)));
        tbrHeader.addView(getTextView(0, "OutletPhoto", Color.BLACK, Typeface.NORMAL,ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_header_border)));
        tbrHeader.addView(getTextView(0, "Remarks", Color.BLACK, Typeface.NORMAL, ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_header_border)));
        tbrHeader.addView(getTextView(0, "Time", Color.BLACK, Typeface.NORMAL, ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_header_border)));
        //tbrHeader.addView(getTextView(0, "Status", Color.BLACK, Typeface.NORMAL, ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_header_border)));
       // tr.addView(getTextView(0, "Status", Color.WHITE, Typeface.BOLD, R.color.colorAccent));


        tblLayout.addView(tbrHeader, getTblLayoutParams());
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
        //tv.setLayoutParams(getLayoutParams());
        tv.setHeight(100);
        tv.setGravity(Gravity.CENTER );
        if(id==10){

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TableRow tablerow = (TableRow)v.getParent();
                    TextView iTime = (TextView) tablerow.getChildAt(12);
                    String time = iTime.getText().toString();

                    localStoragePepsiDB.open();

                    String photoUri = localStoragePepsiDB.getPhotoUri(time,userName);

                    showPopup(photoUri);



                }
            });

        }


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

    private void showPopup(String photoUri) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.pic_layout, (ViewGroup) findViewById(R.id.rl_custom_layout), false);

        final PopupWindow pwindo = new PopupWindow(layout, 600, ViewGroup.LayoutParams.WRAP_CONTENT, false);

        imgvClosePWindow = (ImageView)layout.findViewById(R.id.imgv_close_window);
        imgvOutPhoto = (ImageView)layout.findViewById(R.id.imgv_photo);

        if(photoUri!=null && photoUri.length()>0){
           // File outPhotoOneFile = new File(photoUri);
            Picasso.with(this).load(photoUri).into(imgvOutPhoto);

        }
        if(Build.VERSION.SDK_INT>=21){
            pwindo.setElevation(5.0f);
        }
        imgvClosePWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwindo.dismiss();
            }
        });


        pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
    }

    public class GetAllExistingDataTask extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mBusyDialog = new BusyDialog(AllExistingDataShowAct.this, false, "");
            mBusyDialog.show();

        }


        @Override
        protected String doInBackground(String... params) {
            String responseBodyText = null;

            OkHttpClient client = new OkHttpClient();

            try {

                Request request = new Request.Builder()
                        .url(allExistingDataAPI)

                        .build();


                Response response = null;

                response = client.newCall(request).execute();
                if (!response.isSuccessful()) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        clearAllSaveData.openDialog("Data Not Found");
                        }
                    });
                    throw new IOException("Okhttp Error: " + response);

                } else {

//                    if(checkLast){
//                        allExistingDataList.clear();
//
//                    }
                    responseBodyText = response.body().string();
                    JSONObject resultData = new JSONObject(responseBodyText);

                    JSONArray itemsArray = resultData.getJSONArray("items");

                    if(!itemsArray.isNull(0)){
                        for(int i=0;i<itemsArray.length();i++){

                            JSONObject jobject = itemsArray.getJSONObject(i);
                            String outletCode = jobject.getString("outlet_code");
                            String outletName = jobject.getString("outlet_name");
                            String bkashNo = jobject.getString("owner_bkash");
                            String outletAddr = jobject.getString("outlet_address");
                            String coolerStatus = jobject.getString("cooler_status");
                            String coolerBranding = jobject.getString("cooler_branding");
                            String signageStatus = jobject.getString("signage_status");
                            String signageBranding = jobject.getString("signage_branding");
                           // String enrolledOrder = jobject.getString("enrolled_order");
                            String vol = jobject.getString("vol");
                             final String outPhotoURI = jobject.getString("outlet_in");

                            String volTarget = jobject.getString("vol_target");
                            String remark = jobject.getString("remark");
                            if(remark.equals("null")){
                                remark = "-";
                            }
                            if(bkashNo.equals("null")){
                                bkashNo = "-";
                            }
                            final String time = jobject.getString("start_time");
                            //String endTime = jobject.getString("end_time");
//                            if(endTime.equals("null")){
//                                endTime = "-";
//                            }

                            existDataList.add(new ExistsModel(outletCode,outletName,bkashNo,outletAddr,coolerStatus,
                                    coolerBranding,signageStatus,
                                    signageBranding,volTarget,vol,outPhotoURI,remark,time));


                            totalData = existDataList.size();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    localStoragePepsiDB.open();
                                    localStoragePepsiDB.savePhotoUri(time,userName,outPhotoURI);


                                }


                            });

                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                               mBusyDialog.dismis();
                                txvTotalDurationLast.setText("- " + String.valueOf(totalData));

                            }


                        });



                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                clearAllSaveData.openDialog("Data Not Found");
                                btnNavigationRight.setVisibility(View.GONE);
                                btnNavigationBack.setVisibility(View.VISIBLE);
                                //btnNavigationRight.setVisibility(View.VISIBLE);
                                checkLast = true;

                                txvTotalDurationLast.setText("- " + String.valueOf(0));
                            }


                        });


                    }



                }



            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return responseBodyText;


        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mBusyDialog.dismis();


            tblLayout = findViewById(R.id.tbl_layout_all_existing_data);

            for (int i = 0; i < existDataList.size(); i++) {
                TableRow tbrCell = new TableRow(AllExistingDataShowAct.this);
                tbrCell.setLayoutParams(getLayoutParams());

                tbrCell.addView(getRowsTextView(  0,existDataList.get(i).getOutletCode(), Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_row_text_color)));
                tbrCell.addView(getRowsTextView(  1,existDataList.get(i).getOutletName(), Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_row_text_color)));
                tbrCell.addView(getRowsTextView(  2,existDataList.get(i).getOwnerBkashNo(), Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_row_text_color)));
                tbrCell.addView(getRowsTextView(  3,existDataList.get(i).getOutletAddress(), Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_row_text_color)));
                tbrCell.addView(getRowsTextView(  4,existDataList.get(i).getCoolerStatus(), Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_row_text_color)));
                tbrCell.addView(getRowsTextView(  5,existDataList.get(i).getCoolerBrand(), Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_row_text_color)));
                tbrCell.addView(getRowsTextView(  6,existDataList.get(i).getSignageStatus(), Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_row_text_color)));
                tbrCell.addView(getRowsTextView(  7,existDataList.get(i).getSignageBrand(), Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_row_text_color)));
               // tbrCell.addView(getRowsTextView(  8,existDataList.get(i).getEnrolledOrder(), Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_row_text_color)));
                tbrCell.addView(getRowsTextView(  8,existDataList.get(i).getVolumeTarget(), Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_row_text_color)));
                tbrCell.addView(getRowsTextView(  9,existDataList.get(i).getVolAmount(), Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_row_text_color)));


                tbrCell.addView(getRowsTextView(  10,"Photo View", Color.BLACK, Typeface.NORMAL, ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_row_text_color)));
                tbrCell.addView(getRowsTextView(  11,existDataList.get(i).getRemarks(), Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_row_text_color)));

                tbrCell.addView(getRowsTextView(  12,existDataList.get(i).getStartTime(), Color.WHITE, Typeface.NORMAL, ContextCompat.getDrawable(AllExistingDataShowAct.this,R.drawable.table_row_text_color)));

                tblLayout.addView(tbrCell, getTblLayoutParams());

            }
        }





    }

    private String  getDefaultMonthDate() {
        // TODO Auto-generated method stub
        calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        calendar.set(year, month, 1);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(calendar.getTime());


    }
    public class TotalDataTask extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  loadingDialog = ProgressDialog.show(AllExistingDataViewActivity.this, "Please wait", "Loading...");
        }


        @Override
        protected String doInBackground(String... params) {
            String responseBodyText = null;

            OkHttpClient client = new OkHttpClient();

            try {

                Request request = new Request.Builder()
                        .url(APIList.totalDataAPI + userName)

                        .build();


                Response response = null;

                response = client.newCall(request).execute();
                if (!response.isSuccessful()) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //  Toast.makeText(AllExistingDataViewActivity.this,"Data Not Found",Toast.LENGTH_SHORT).show();


                        }
                    });
                    throw new IOException("Okhttp Error: " + response);

                } else {

                    responseBodyText = response.body().string();
                    JSONObject resultData = new JSONObject(responseBodyText);

                    final String visitedTotal = resultData.getString("total");
                   // final String thisMonthTotal = resultData.getString("this_month");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txvTotalResult.setText("Of "+ visitedTotal);
                            if(visitedTotal!=null && !visitedTotal.isEmpty()){
                                allVisited = Integer.parseInt(visitedTotal);
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
            //loadingDialog.dismiss();

        }
    }

}
