package planet.it.limited.pepsigosmart.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import planet.it.limited.pepsigosmart.BuildConfig;
import planet.it.limited.pepsigosmart.R;
import planet.it.limited.pepsigosmart.database.LocalStoragePepsiDB;
import planet.it.limited.pepsigosmart.task.SurveyDataSendToServer;
import planet.it.limited.pepsigosmart.task.UpdateBkashAndRetMob;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;

import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getBoleanValueSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getValueFromSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveBoleanValueSharedPreferences;

public class ReconfirmedPageAct extends AppCompatActivity {

//    @BindView(R.id.btn_back)
//    Button btnBack;

    @BindView(R.id.btn_confirmed)
    Button btnConfirmed;

    @BindView(R.id.txv_outlet_code_value)
    TextView txvOutCodeValue;
    @BindView(R.id.txv_teritory_value)
    TextView txvTeriValue;
    @BindView(R.id.txv_se_area_value)
    TextView txvSeAreaValue;
    @BindView(R.id.txv_distributor_value)
    TextView txvDistrValue;
    @BindView(R.id.txv_outlet_name_value)
    TextView txvOutNameValue;
    @BindView(R.id.txv_bikash_no)
    TextView txvBikashNo;
    @BindView(R.id.txv_cooler_s_value)
    TextView txvCoolSValue;
    @BindView(R.id.txv_cooler_b_value)
    TextView txvCoolBValue;
    @BindView(R.id.txv_signage_s_value)
    TextView txvSigSValue;
    @BindView(R.id.txv_signage_b_value)
    TextView txvSigBValue;

    @BindView(R.id.txv_vol_targ_value)
    TextView txvVolTarValue;
    @BindView(R.id.txv_remarks_value)
    TextView txvRemarks;

    @BindView(R.id.btn_bikash_no)
    Button edtBtnBikashNo;
    @BindView(R.id.btn_cooler_s)
    Button edtBtnCoolS;
    @BindView(R.id.btn_cooler_b)
    Button edtBtnCoolB;
    @BindView(R.id.btn_signage_s)
    Button edtBtnSigS;
    @BindView(R.id.btn_signage_b)
    Button edtBtnSigB;

    @BindView(R.id.btn_vol_target)
    Button edtBtnVolTar;
    @BindView(R.id.btn_remark)
    Button edtBtnRemarks;
    @BindView(R.id.btn_change_pic_one)
    Button btnChangePicOne;

    @BindView(R.id.img_outlet_pic_one)
    ImageView imgvOutPicOne;


    String outletCode = " ";
    String territory=" ";
    String ceArea =" ";
    String distributor = " ";
    String outletName = " ";
    String bikashNo = " ";
    String bkashName = " ";
    String coolerStatus = " ";
    String coolerBranding = " ";
    String signageStatus = " ";
    String signageBranding = " ";
    //String enrolledOrder = " ";
    String volumeTarget = " ";
    String remarks = " ";
    String outPhotoOneLocalPath = " ";
    String outPhotoOneServerPath = " ";
    //String outPhotoTwoLocalPath = " ";
    //String outPhotoTwoServerPath = " ";
    String latitude = " ";
    String longitude = " ";

    private static String LIST_SEPARATOR = ":";

    ArrayList<String> coolerStatusList = new ArrayList<>();
    ArrayList<String> signageStatusList = new ArrayList<>();
    LocalStoragePepsiDB localStoragePepsiDB;

    public static boolean isActive = false;
    Date date1 ;
    Date date2;
    String entryDate = " ";
    ClearAllSaveData clearAllSaveData;
    String startTime = " ";
    String endTime = " ";
    boolean checkOutletId ;
    String syncStatus = "failled";
    String userName = " ";
    String device = " ";
    String apkVersion = "";
    String model = " ";

    SurveyDataSendToServer surveyDataSendToServer;
    boolean isEditBkash ;
    boolean isEditRetMobile;
    UpdateBkashAndRetMob updateBkashAndRetMob;
    boolean isEditBkashName;
    boolean isEditOutAddr;
    String retMob = " ";
    String outletAddr = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reconfirmed_page);
        initViews();
    }

    public void initViews(){
        ButterKnife.bind(this);
        setClickListener();
        clearAllSaveData = new ClearAllSaveData(ReconfirmedPageAct.this);
        updateBkashAndRetMob = new UpdateBkashAndRetMob(ReconfirmedPageAct.this);
        localStoragePepsiDB = new LocalStoragePepsiDB(ReconfirmedPageAct.this);
        surveyDataSendToServer = new SurveyDataSendToServer(ReconfirmedPageAct.this,ReconfirmedPageAct.this);
        localStoragePepsiDB.open();
        //get all save value
        outletCode = getValueFromSharedPreferences("outlet_code",ReconfirmedPageAct.this);
        territory =  getValueFromSharedPreferences("territory_name",ReconfirmedPageAct.this);
        ceArea =  getValueFromSharedPreferences("ce_area_name",ReconfirmedPageAct.this);
        distributor =  getValueFromSharedPreferences("db_name",ReconfirmedPageAct.this);
        outletName =  getValueFromSharedPreferences("outlet_name",ReconfirmedPageAct.this);
        bikashNo =  getValueFromSharedPreferences("ret_bikash_no",ReconfirmedPageAct.this);
        bkashName =  getValueFromSharedPreferences("ret_bikash_name",ReconfirmedPageAct.this);
        retMob =  getValueFromSharedPreferences("retailer_mobile",ReconfirmedPageAct.this);
        outletAddr =  getValueFromSharedPreferences("outlet_address",ReconfirmedPageAct.this);

        coolerStatusList = CoolerAndSignageAct.coolerStatusList;
      //  coolerStatusList = clearAllSaveData.getCoolerStatusList();

        coolerBranding =  getValueFromSharedPreferences("cooler_brand",ReconfirmedPageAct.this);
        signageStatusList = CoolerAndSignageAct.signageStatusList;
        signageBranding =  getValueFromSharedPreferences("signage_brand",ReconfirmedPageAct.this);


        volumeTarget =  getValueFromSharedPreferences("volume_target",ReconfirmedPageAct.this);
        remarks =  getValueFromSharedPreferences("remarks",ReconfirmedPageAct.this);
        outPhotoOneLocalPath =  getValueFromSharedPreferences("out_pic_one_local_path",ReconfirmedPageAct.this);
        outPhotoOneServerPath =  getValueFromSharedPreferences("out_photo_one_s_path",ReconfirmedPageAct.this);

        latitude =  getValueFromSharedPreferences("latitude",ReconfirmedPageAct.this);
        longitude =  getValueFromSharedPreferences("longitude",ReconfirmedPageAct.this);
        userName =  getValueFromSharedPreferences("user_name",ReconfirmedPageAct.this);

        isEditBkash = getBoleanValueSharedPreferences("is_edit_bkash",ReconfirmedPageAct.this);
        isEditRetMobile = getBoleanValueSharedPreferences("is_edit_ret_mob",ReconfirmedPageAct.this);
        isEditBkashName = getBoleanValueSharedPreferences("is_edit_bkash_name",ReconfirmedPageAct.this);
        isEditOutAddr = getBoleanValueSharedPreferences("is_edit_out_addr",ReconfirmedPageAct.this);

        entryDate = clearAllSaveData.getCurrentEntryDate();
        startTime = getValueFromSharedPreferences("start_time",ReconfirmedPageAct.this);

//        String startTime = clearAllSaveData.getStartTime();
//        String todayDate = clearAllSaveData.getTodayDate();
//        String dateTime = todayDate + " " + startTime;

        endTime = clearAllSaveData.getStartTime();


        apkVersion = BuildConfig.VERSION_NAME;

        if(coolerStatusList.size()>0){
            coolerStatus = convertListToString(coolerStatusList);
        }
        if(signageStatusList.size()>0){
            signageStatus = convertListToString(signageStatusList);
        }

        setTextAllSaveValue();
        if(outPhotoOneServerPath!=null){
            if( outPhotoOneServerPath.equals("")){
                syncStatus = "failled";
            }
        }
//        if(outPhotoTwoServerPath!=null){
//            if(outPhotoTwoServerPath.equals("")){
//                syncStatus = "failled";
//            }
//        }

        device = getDeviceName();
        model = getAndroidDeviceModel();

    }

    private void setClickListener(){
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent signIn = new Intent(ReconfirmedPageAct.this,VolumeAndImagesAct.class);
//                startActivity(signIn);
//               // ActivityCompat.finishAffinity(ReconfirmedPageAct.this);
//                saveBoleanValueSharedPreferences("is_back",true,ReconfirmedPageAct.this);
//            }
//        });

        btnConfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outPhotoOneServerPath =  getValueFromSharedPreferences("out_photo_one_s_path",ReconfirmedPageAct.this);
                //outPhotoTwoServerPath =  getValueFromSharedPreferences("out_photo_two_s_path",ReconfirmedPageAct.this);
                if(outletCode!=null && !outletCode.isEmpty()){
                    checkOutletId = localStoragePepsiDB.checkOutletId(outletCode);
                }



                String currentEntryDate = entryDate;
                String checkLastEntryDate =localStoragePepsiDB.selectLastEntryDate(outletCode);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                int checkCurrentEM=0;
                int checkLastEntM=0;
                try {
                    if(checkLastEntryDate!=null && !checkLastEntryDate.isEmpty()){
                        date1 = sdf.parse(currentEntryDate);
                        date2 = sdf.parse(checkLastEntryDate);
                        checkCurrentEM= date1.getMonth();
                        checkLastEntM = date2.getMonth();
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(checkOutletId && checkCurrentEM==checkLastEntM){
                    if(outPhotoOneServerPath!=null){
                        localStoragePepsiDB.updateServeyDataForSameUser(outletCode,coolerStatus,
                                coolerBranding,signageStatus,signageBranding,
                                volumeTarget,remarks,entryDate,
                                outPhotoOneLocalPath,outPhotoOneServerPath,startTime,endTime,latitude,longitude,userName,syncStatus);
                    }

                }else if( checkCurrentEM>checkLastEntM){
                    if(outPhotoOneServerPath!=null){
                        localStoragePepsiDB.saveServeyStorageEntry(outletCode,coolerStatus,
                                coolerBranding,signageStatus,signageBranding,
                                volumeTarget,remarks,entryDate,
                                outPhotoOneLocalPath,outPhotoOneServerPath,startTime,endTime,latitude,longitude,userName,syncStatus);
                    }


                }else {
                    if(outPhotoOneServerPath!=null ){
                        localStoragePepsiDB.saveServeyStorageEntry(outletCode,coolerStatus,
                                coolerBranding,signageStatus,signageBranding,
                                volumeTarget,remarks,entryDate,
                                outPhotoOneLocalPath,outPhotoOneServerPath,startTime,endTime,latitude,longitude,userName,syncStatus);
                    }

                }


                //data send to server
                boolean isAvailableInternet = clearAllSaveData.isConnectingToInternet(ReconfirmedPageAct.this);

                if(outletCode!=null && outletCode.length()>0){
                    if(isAvailableInternet) {
                        try{
                            if(outPhotoOneServerPath!=null ){
                                if((!outPhotoOneServerPath.equals("") && outPhotoOneServerPath.length()>0)){
                                    surveyDataSendToServer.sendDataToServer(outletCode,coolerStatus,coolerBranding,signageStatus,
                                            signageBranding,
                                            Integer.parseInt(volumeTarget.trim()),remarks,outPhotoOneServerPath,
                                            latitude,longitude,startTime,endTime,device + model ,apkVersion);
                                }else {

                                    clearAllSaveData.openDialog("Your picture one  missing");
                                    Intent backIntent = new Intent(ReconfirmedPageAct.this, SummerHangamaEnrollAct.class);
                                    startActivity(backIntent);
                                    clearAllSaveData.clearAllWhenSearchOutlet();
                                    ActivityCompat.finishAffinity(ReconfirmedPageAct.this);
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }else {

                        clearAllSaveData.openDialog("Your Device is Offline");
                        Intent backIntent = new Intent(ReconfirmedPageAct.this, SummerHangamaEnrollAct.class);
                        startActivity(backIntent);
                        clearAllSaveData.clearAllWhenSearchOutlet();
                        ActivityCompat.finishAffinity(ReconfirmedPageAct.this);


                    }


                }

                    if(isEditBkash && isEditRetMobile && isEditBkashName && isEditOutAddr){
                      updateBkashAndRetMob.doUpdate(bikashNo,retMob,bkashName,outletAddr,outletCode);
                    }
                    else {
                            if(isEditBkash){
                                updateBkashAndRetMob.doUpdate(bikashNo,retMob,bkashName,outletAddr,outletCode);
                            }
                            if(isEditRetMobile){
                                updateBkashAndRetMob.doUpdate(bikashNo,retMob,bkashName,outletAddr,outletCode);
                            }
                            if(isEditBkashName){
                                updateBkashAndRetMob.doUpdate(bikashNo,retMob,bkashName,outletAddr,outletCode);
                            }
                            if(isEditOutAddr){
                                updateBkashAndRetMob.doUpdate(bikashNo,retMob,bkashName,outletAddr,outletCode);
                            }
                    }

                    clearAllSaveData.clearAllWhenSearchOutlet();


            }
        });

        edtBtnBikashNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPageAct.this,SummerHangamaEnrollAct.class);
                startActivity(coolerstatus);
                saveBoleanValueSharedPreferences("is_back",false,ReconfirmedPageAct.this);
                saveBoleanValueSharedPreferences("is_active",true,ReconfirmedPageAct.this);
            }
        });

        edtBtnCoolS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPageAct.this,CoolerAndSignageAct.class);
                startActivity(coolerstatus);
                saveBoleanValueSharedPreferences("is_back",false,ReconfirmedPageAct.this);
                saveBoleanValueSharedPreferences("is_active",true,ReconfirmedPageAct.this);
            }
        });

        edtBtnCoolB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPageAct.this,CoolerAndSignageAct.class);
                startActivity(coolerstatus);
                saveBoleanValueSharedPreferences("is_back",false,ReconfirmedPageAct.this);
                saveBoleanValueSharedPreferences("is_active",true,ReconfirmedPageAct.this);
            }
        });
        edtBtnSigS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPageAct.this,CoolerAndSignageAct.class);
                startActivity(coolerstatus);
                saveBoleanValueSharedPreferences("is_back",false,ReconfirmedPageAct.this);
                saveBoleanValueSharedPreferences("is_active",true,ReconfirmedPageAct.this);
            }
        });

        edtBtnSigB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPageAct.this,CoolerAndSignageAct.class);
                startActivity(coolerstatus);
                saveBoleanValueSharedPreferences("is_back",false,ReconfirmedPageAct.this);
                saveBoleanValueSharedPreferences("is_active",true,ReconfirmedPageAct.this);
            }
        });


        edtBtnVolTar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPageAct.this,VolumeAndImagesAct.class);
                startActivity(coolerstatus);
                saveBoleanValueSharedPreferences("is_back",false,ReconfirmedPageAct.this);
                saveBoleanValueSharedPreferences("is_active",true,ReconfirmedPageAct.this);
            }
        });

        edtBtnRemarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPageAct.this,VolumeAndImagesAct.class);
                startActivity(coolerstatus);
                saveBoleanValueSharedPreferences("is_back",false,ReconfirmedPageAct.this);
                saveBoleanValueSharedPreferences("is_active",true,ReconfirmedPageAct.this);
            }
        });


        btnChangePicOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedPageAct.this,VolumeAndImagesAct.class);
                startActivity(coolerstatus);
                saveBoleanValueSharedPreferences("is_back",false,ReconfirmedPageAct.this);
                saveBoleanValueSharedPreferences("is_active",true,ReconfirmedPageAct.this);
            }
        });


    }

    public void setTextAllSaveValue(){
        //set all save value
        if(outletCode!=null){
            txvOutCodeValue.setText(outletCode);
        }
        if(territory!=null){
            txvTeriValue.setText(territory);
        }
        if(ceArea!=null){
            txvSeAreaValue.setText(ceArea);
        }

        if(distributor!=null){
            txvDistrValue.setText(distributor);
        }

        if(outletName!=null){
            txvOutNameValue.setText(outletName);
        }
        if(bikashNo!=null){
            txvBikashNo.setText(bikashNo);
        }

        if(coolerStatus!=null){
            txvCoolSValue.setText(coolerStatus);
        }
        if(coolerBranding!=null){
            txvCoolBValue.setText(coolerBranding);
        }

        if(signageStatus!=null){
            txvSigSValue.setText(signageStatus);
        }
        if(signageBranding!=null){
            txvSigBValue.setText(signageBranding);
        }

        if(volumeTarget!=null){
            txvVolTarValue.setText(volumeTarget);
        }
        if(remarks!=null){
            txvRemarks.setText(remarks);
        }

        if(outPhotoOneLocalPath!=null && outPhotoOneLocalPath.length()>0){
            File outPhotoOneFile = new File(outPhotoOneLocalPath);
            Picasso.with(this).load(outPhotoOneFile).into(imgvOutPicOne);

        }





    }

    public static String convertListToString(List<String> stringList) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : stringList) {
            stringBuffer.append(str).append(LIST_SEPARATOR);
        }
        try {
            // Remove last separator
            int lastIndex = stringBuffer.lastIndexOf(LIST_SEPARATOR);
            stringBuffer.delete(lastIndex, lastIndex + LIST_SEPARATOR.length() + 1);
        }catch (Exception e){
            e.getStackTrace();

        }


        return stringBuffer.toString();
    }

    @Override
    public void onPause() {
        super.onPause();
        isActive = true;

    }
    @Override
    public void onStop() {
        super.onStop();
        isActive = false;
        saveBoleanValueSharedPreferences("is_active",false,ReconfirmedPageAct.this);
    }
    public String getDeviceName() {
        String manufacturer = android.os.Build.MANUFACTURER;

        return manufacturer;
    }
    public String getAndroidDeviceModel() {
        return Build.MODEL;
    }
}
