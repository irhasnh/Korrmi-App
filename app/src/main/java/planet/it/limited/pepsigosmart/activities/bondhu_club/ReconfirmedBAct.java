package planet.it.limited.pepsigosmart.activities.bondhu_club;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import planet.it.limited.pepsigosmart.BuildConfig;
import planet.it.limited.pepsigosmart.R;
import planet.it.limited.pepsigosmart.activities.ReconfirmedPageAct;
import planet.it.limited.pepsigosmart.activities.SummerHangamaEnrollAct;
import planet.it.limited.pepsigosmart.database.LocalStoragePepsiDB;
import planet.it.limited.pepsigosmart.task.BondhuClubDataSendToServer;
import planet.it.limited.pepsigosmart.task.SurveyDataSendToServer;
import planet.it.limited.pepsigosmart.task.UpdateBkashAndRetMob;
import planet.it.limited.pepsigosmart.task.UpdateBkashAndRetMobB;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;

import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getBoleanValueSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getValueFromSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveBoleanValueSharedPreferences;

public class ReconfirmedBAct extends AppCompatActivity {
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
    @BindView(R.id.txv_bikash_name)
    TextView txvBkashName;

    @BindView(R.id.txv_cooler_cate_value)
    TextView txvCoolCategory;
    @BindView(R.id.txv_indus_csd_v)
    TextView txvIndusCSDV;
    @BindView(R.id.txv_industry_water_b)
    TextView txvIndusWaterV;
    @BindView(R.id.txv_industry_lrb_b)
    TextView txvIndusLRBV;

    @BindView(R.id.txv_pepsico_csd_v)
    TextView txvPepsicoCSDV;
    @BindView(R.id.txv_pepsico_water_b)
    TextView txvPepsicoWaterV;
    @BindView(R.id.txv_pepsico_lrb_b)
    TextView txvPepsicoLRBV;


    @BindView(R.id.txv_remarks_value)
    TextView txvRemarks;

    @BindView(R.id.btn_bikash_no)
    Button edtBtnBkashNo;

    @BindView(R.id.btn_bikash_name)
    Button edtBtnBkashName;
    @BindView(R.id.btn_cooler_cate)
    Button edtBtnCoolCate;

    @BindView(R.id.btn_indust_csd)
    Button edtBtnIndusCSD;
    @BindView(R.id.btn_indust_water)
    Button edtBtnIndusWater;
    @BindView(R.id.btn_indust_lrb)
    Button edtBtnIndusLRB;

    @BindView(R.id.btn_pepsico_csd)
    Button edtBtnPepsicoCSD;
    @BindView(R.id.btn_pepsico_water)
    Button edtBtnPepsicoWater;
    @BindView(R.id.btn_pepsico_lrb)
    Button edtBtnPepsicoLRB;


    @BindView(R.id.btn_remark)
    Button edtBtnRemarks;
    @BindView(R.id.btn_change_pic_one)
    Button btnChangePicOne;

    @BindView(R.id.img_outlet_pic_one)
    ImageView imgvOutPicOne;

    //b enroll
    String outletCode = " ";
    String territory=" ";
    String ceArea =" ";
    String distributor = " ";
    String outletName = " ";
    String bikashNo = " ";
    String bkashName = " ";
     //b cooler
    String coolCategory = " ";
    String indusCSD = " ";
    String indusWater = " ";
    String indusLRB = " ";
    //b volume
    String pepsiCSD = " ";
    String pepsiWater = " ";
    String pepsiLRB = " ";
    String remarks = " ";
    String outPhotoOneLocalPath = " ";
    String outPhotoOneServerPath = " ";

    String latitude = " ";
    String longitude = " ";


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

    BondhuClubDataSendToServer  bondhuClubDataSendToServer;
    boolean isEditBkash ;
    boolean isEditRetMobile;
    UpdateBkashAndRetMobB updateBkashAndRetMobB;
    boolean isEditBkashName;
    boolean isEditOutAddr;
    String retMob = " ";
    String outletAddr = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reconfirmed_b);
        initViews();
    }

    public void initViews(){
        ButterKnife.bind(this);
        setClickListener();

        clearAllSaveData = new ClearAllSaveData(ReconfirmedBAct.this);
        localStoragePepsiDB = new LocalStoragePepsiDB(ReconfirmedBAct.this);

        updateBkashAndRetMobB = new UpdateBkashAndRetMobB(ReconfirmedBAct.this);
        bondhuClubDataSendToServer = new BondhuClubDataSendToServer(ReconfirmedBAct.this,ReconfirmedBAct.this);
        localStoragePepsiDB.open();
        //get all save value
        //b enroll

        userName = getValueFromSharedPreferences("user_name",ReconfirmedBAct.this);
        outletCode = getValueFromSharedPreferences("outlet_code_b",ReconfirmedBAct.this);
        territory =  getValueFromSharedPreferences("territory_name_b",ReconfirmedBAct.this);
        ceArea =  getValueFromSharedPreferences("ce_area_name_b",ReconfirmedBAct.this);
        distributor =  getValueFromSharedPreferences("db_name_b",ReconfirmedBAct.this);
        outletName =  getValueFromSharedPreferences("outlet_name_b",ReconfirmedBAct.this);
        bikashNo =  getValueFromSharedPreferences("ret_bikash_no_b",ReconfirmedBAct.this);
        bkashName =  getValueFromSharedPreferences("ret_bikash_name_b",ReconfirmedBAct.this);
        retMob =  getValueFromSharedPreferences("retailer_mobile_b",ReconfirmedBAct.this);
        outletAddr =  getValueFromSharedPreferences("outlet_address_b",ReconfirmedBAct.this);

        //b cooler
        coolCategory = getValueFromSharedPreferences("cool_category_b",ReconfirmedBAct.this);
        indusCSD = getValueFromSharedPreferences("indust_csd",ReconfirmedBAct.this);
        indusWater =getValueFromSharedPreferences("indust_water",ReconfirmedBAct.this);
        indusLRB = getValueFromSharedPreferences("indust_lrb",ReconfirmedBAct.this);

        //b volume
        pepsiCSD = getValueFromSharedPreferences("pepsico_csd",ReconfirmedBAct.this);
        pepsiWater = getValueFromSharedPreferences("pepsico_water",ReconfirmedBAct.this);
        pepsiLRB = getValueFromSharedPreferences("pepsico_lrb",ReconfirmedBAct.this);
        remarks =  getValueFromSharedPreferences("remarks",ReconfirmedBAct.this);
        outPhotoOneLocalPath =getValueFromSharedPreferences("out_pic_one_local_path",ReconfirmedBAct.this);
        outPhotoOneServerPath = getValueFromSharedPreferences("out_photo_one_s_path",ReconfirmedBAct.this);

         latitude = getValueFromSharedPreferences("latitude_b",ReconfirmedBAct.this);
         longitude = getValueFromSharedPreferences("longitude_b",ReconfirmedBAct.this);

        isEditBkash = getBoleanValueSharedPreferences("is_edit_bkash_b",ReconfirmedBAct.this);
        isEditRetMobile = getBoleanValueSharedPreferences("is_edit_ret_mob_b",ReconfirmedBAct.this);
        isEditBkashName = getBoleanValueSharedPreferences("is_edit_bkash_name_b",ReconfirmedBAct.this);
        isEditOutAddr = getBoleanValueSharedPreferences("is_edit_out_addr_b",ReconfirmedBAct.this);



        entryDate = clearAllSaveData.getCurrentEntryDate();
        startTime = getValueFromSharedPreferences("start_time_b",ReconfirmedBAct.this);
        endTime = clearAllSaveData.getStartTime();
        apkVersion = BuildConfig.VERSION_NAME;
        setTextAllSaveValue();
        if(outPhotoOneServerPath!=null){
            if( outPhotoOneServerPath.equals("")){
                syncStatus = "failled";
            }
        }

        device = getDeviceName();
        model = getAndroidDeviceModel();


    }

    public void setClickListener(){

        btnConfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outPhotoOneServerPath =  getValueFromSharedPreferences("out_photo_one_s_path",ReconfirmedBAct.this);
                //outPhotoTwoServerPath =  getValueFromSharedPreferences("out_photo_two_s_path",ReconfirmedPageAct.this);
                if(outletCode!=null && !outletCode.isEmpty()){
                    checkOutletId = localStoragePepsiDB.checkOutletIdB(outletCode);
                }



                String currentEntryDate = entryDate;
                String checkLastEntryDate =localStoragePepsiDB.selectLastEntryDateB(outletCode);

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
                        localStoragePepsiDB.updateServeyDataForSameUserB(outletCode,coolCategory,
                                indusCSD,indusWater,indusLRB,
                                pepsiCSD,pepsiWater,pepsiLRB,remarks,entryDate,
                                outPhotoOneLocalPath,outPhotoOneServerPath,startTime,endTime,latitude,longitude,userName,syncStatus);
                    }

                }else if( checkCurrentEM>checkLastEntM){
                    if(outPhotoOneServerPath!=null){
                        localStoragePepsiDB.saveBondhuClubEntry(outletCode,coolCategory,
                                indusCSD,indusWater,indusLRB,
                                pepsiCSD,pepsiWater,pepsiLRB,remarks,entryDate,
                                outPhotoOneLocalPath,outPhotoOneServerPath,startTime,endTime,latitude,longitude,userName,syncStatus);
                    }


                }else {
                    if(outPhotoOneServerPath!=null ){
                        localStoragePepsiDB.saveBondhuClubEntry(outletCode,coolCategory,
                                indusCSD,indusWater,indusLRB,
                                pepsiCSD,pepsiWater,pepsiLRB,remarks,entryDate,
                                outPhotoOneLocalPath,outPhotoOneServerPath,startTime,endTime,latitude,longitude,userName,syncStatus);
                    }

                }


                //data send to server
                boolean isAvailableInternet = clearAllSaveData.isConnectingToInternet(ReconfirmedBAct.this);

                if(outletCode!=null && outletCode.length()>0){
                    if(isAvailableInternet) {
                        try{
                            if(outPhotoOneServerPath!=null ){
                                if((!outPhotoOneServerPath.equals("") && outPhotoOneServerPath.length()>0)){
                                    bondhuClubDataSendToServer.sendDataToServer(outletCode,coolCategory,indusCSD,indusWater,
                                            indusLRB,
                                            pepsiCSD,pepsiWater,pepsiLRB,remarks,outPhotoOneServerPath,
                                            latitude,longitude,startTime,endTime,device + model ,apkVersion);
                                }else {

                                    clearAllSaveData.openDialog("Your picture one  missing");
                                    Intent backIntent = new Intent(ReconfirmedBAct.this, BondhuClubEnrollAct.class);
                                    startActivity(backIntent);
                                    clearAllSaveData.clearWhenSearchBondhuClubOutlet();
                                    ActivityCompat.finishAffinity(ReconfirmedBAct.this);
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }else {

                        clearAllSaveData.openDialog("Your Device is Offline");
                        Intent backIntent = new Intent(ReconfirmedBAct.this, BondhuClubEnrollAct.class);
                        startActivity(backIntent);
                        clearAllSaveData.clearWhenSearchBondhuClubOutlet();
                        ActivityCompat.finishAffinity(ReconfirmedBAct.this);

                    }


                }



                if(isEditBkash && isEditRetMobile && isEditBkashName && isEditOutAddr){
                    updateBkashAndRetMobB.doUpdate(bikashNo,retMob,bkashName,outletAddr,outletCode);
                }
                else {
                    if(isEditBkash){
                        updateBkashAndRetMobB.doUpdate(bikashNo,retMob,bkashName,outletAddr,outletCode);
                    }
                    if(isEditRetMobile){
                        updateBkashAndRetMobB.doUpdate(bikashNo,retMob,bkashName,outletAddr,outletCode);
                    }
                    if(isEditBkashName){
                        updateBkashAndRetMobB.doUpdate(bikashNo,retMob,bkashName,outletAddr,outletCode);
                    }
                    if(isEditOutAddr){
                        updateBkashAndRetMobB.doUpdate(bikashNo,retMob,bkashName,outletAddr,outletCode);
                    }
                }

                clearAllSaveData.clearWhenSearchBondhuClubOutlet();





            }








        });



        edtBtnBkashNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedBAct.this,BondhuClubEnrollAct.class);
                startActivity(coolerstatus);
                saveBoleanValueSharedPreferences("is_back",false,ReconfirmedBAct.this);
                saveBoleanValueSharedPreferences("is_active",true,ReconfirmedBAct.this);
            }
        });

        edtBtnBkashName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedBAct.this,BondhuClubEnrollAct.class);
                startActivity(coolerstatus);
                saveBoleanValueSharedPreferences("is_back",false,ReconfirmedBAct.this);
                saveBoleanValueSharedPreferences("is_active",true,ReconfirmedBAct.this);
            }
        });

        edtBtnCoolCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedBAct.this,CoolerAndSignageBAct.class);
                startActivity(coolerstatus);
                saveBoleanValueSharedPreferences("is_back",false,ReconfirmedBAct.this);
                saveBoleanValueSharedPreferences("is_active",true,ReconfirmedBAct.this);
            }
        });
        edtBtnIndusCSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedBAct.this,CoolerAndSignageBAct.class);
                startActivity(coolerstatus);
                saveBoleanValueSharedPreferences("is_back",false,ReconfirmedBAct.this);
                saveBoleanValueSharedPreferences("is_active",true,ReconfirmedBAct.this);
            }
        });

        edtBtnIndusWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedBAct.this,CoolerAndSignageBAct.class);
                startActivity(coolerstatus);
                saveBoleanValueSharedPreferences("is_back",false,ReconfirmedBAct.this);
                saveBoleanValueSharedPreferences("is_active",true,ReconfirmedBAct.this);
            }
        });


        edtBtnIndusLRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedBAct.this,CoolerAndSignageBAct.class);
                startActivity(coolerstatus);
                saveBoleanValueSharedPreferences("is_back",false,ReconfirmedBAct.this);
                saveBoleanValueSharedPreferences("is_active",true,ReconfirmedBAct.this);
            }
        });

        edtBtnPepsicoCSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedBAct.this,VolumeAndImagesBAct.class);
                startActivity(coolerstatus);
                saveBoleanValueSharedPreferences("is_back",false,ReconfirmedBAct.this);
                saveBoleanValueSharedPreferences("is_active",true,ReconfirmedBAct.this);
            }
        });

        edtBtnPepsicoWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedBAct.this,VolumeAndImagesBAct.class);
                startActivity(coolerstatus);
                saveBoleanValueSharedPreferences("is_back",false,ReconfirmedBAct.this);
                saveBoleanValueSharedPreferences("is_active",true,ReconfirmedBAct.this);
            }
        });


        edtBtnPepsicoLRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedBAct.this,VolumeAndImagesBAct.class);
                startActivity(coolerstatus);
                saveBoleanValueSharedPreferences("is_back",false,ReconfirmedBAct.this);
                saveBoleanValueSharedPreferences("is_active",true,ReconfirmedBAct.this);
            }
        });






        edtBtnRemarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedBAct.this,VolumeAndImagesBAct.class);
                startActivity(coolerstatus);
                saveBoleanValueSharedPreferences("is_back",false,ReconfirmedBAct.this);
                saveBoleanValueSharedPreferences("is_active",true,ReconfirmedBAct.this);
            }
        });


        btnChangePicOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coolerstatus = new Intent(ReconfirmedBAct.this,VolumeAndImagesBAct.class);
                startActivity(coolerstatus);
                saveBoleanValueSharedPreferences("is_back",false,ReconfirmedBAct.this);
                saveBoleanValueSharedPreferences("is_active",true,ReconfirmedBAct.this);
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

        if(bkashName!=null){
            txvBkashName.setText(bkashName);
        }


        if(coolCategory!=null){
            txvCoolCategory.setText(coolCategory);
        }
        if(indusCSD!=null){
            txvIndusCSDV.setText(indusCSD);
        }

        if(indusWater!=null){
            txvIndusWaterV.setText(indusWater);
        }
        if(indusLRB!=null){
            txvIndusLRBV.setText(indusLRB);
        }

        if(pepsiCSD!=null){
            txvPepsicoCSDV.setText(pepsiCSD);
        }

        if(pepsiWater!=null){
            txvPepsicoWaterV.setText(pepsiWater);
        }
        if(pepsiLRB!=null){
            txvPepsicoLRBV.setText(pepsiLRB);
        }



        if(remarks!=null){
            txvRemarks.setText(remarks);
        }

        if(outPhotoOneLocalPath!=null && outPhotoOneLocalPath.length()>0){
            File outPhotoOneFile = new File(outPhotoOneLocalPath);
            Picasso.with(this).load(outPhotoOneFile).into(imgvOutPicOne);

        }

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
        saveBoleanValueSharedPreferences("is_active",false,ReconfirmedBAct.this);
    }

    public String getDeviceName() {
        String manufacturer = android.os.Build.MANUFACTURER;

        return manufacturer;
    }
    public String getAndroidDeviceModel() {
        return Build.MODEL;
    }
}
