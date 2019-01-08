package planet.it.limited.pepsigosmart.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.interconn.salmaanahmed.saexpandablebutton.ExpandableButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import planet.it.limited.pepsigosmart.R;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;

import static android.view.View.GONE;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getBoleanValueSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getValueFromSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveBoleanValueSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveToSharedPreferences;

public class CoolerAndSignageAct extends DrawerCompatActivity {
    ExpandableButtonListener expandableButtonListener = null;
    @BindView(R.id.exp_btn_cooler_branding)
    RelativeLayout expBtnCoolerBranding;
    @BindView(R.id.exp_btn_signage_branding)
    RelativeLayout expBtnSignagB;
    @BindView(R.id.childview_cooler_brand)
    LinearLayout childviewCoolB;
    @BindView(R.id.childview_signage)
    LinearLayout childviewSignageB;

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.btn_next)
    Button btnNext;

    @BindView(R.id.chkb_cool_pepsico)
    CheckBox  chkbCoolPepsico;
    @BindView(R.id.chkb_cool_cocacola)
    CheckBox  chkbCoolCoca;
    @BindView(R.id.chkb_cool_pran)
    CheckBox  chkbCoolPran;
    @BindView(R.id.chkb_cool_mojo)
    CheckBox  chkbCoolMojo;
    @BindView(R.id.chkb_cool_others)
    CheckBox  chkbCoolOthers;
    @BindView(R.id.ckhb_sig_pepsico)
    CheckBox  chkbSigPepsico;
    @BindView(R.id.ckhb_sig_cocacola)
    CheckBox  chkbSigCoca;
    @BindView(R.id.ckhb_sig_other_bevarage)
    CheckBox  chkbSigOthBever;
    @BindView(R.id.ckhb_sig_others_signage)
    CheckBox  chkbSigOthSignage;
    @BindView(R.id.rg_cool_brand)
    RadioGroup  rgCoolBrand;
    @BindView(R.id.rg_signage_brand)
    RadioGroup  rgSigBrand;
    @BindView(R.id.rbtn_coolb_yes)
    RadioButton rbtnCoolBYes;
    @BindView(R.id.rbtn_coolb_no)
    RadioButton rbtnCoolBNo;
    @BindView(R.id.rbtn_coolb_already_done)
    RadioButton rbtnCoolBAllDone;
    @BindView(R.id.rbtn_sig_brand_yes)
    RadioButton rbtnSigBYes;
    @BindView(R.id.rbtn_sig_brand_no)
    RadioButton rbtnSigBNo;
    @BindView(R.id.rbtn_sig_brand_already_done)
    RadioButton rbtnSigBAllDone;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.txv_user_name)
    TextView txvUserName;
    @BindView(R.id.txv_mobile)
    TextView txvMobile;

    public static ArrayList<String> coolerStatusList = new ArrayList<String>();
    public static ArrayList<String> signageStatusList = new ArrayList<String>();
    String coolerBranding =" ";
    String signageBranding = " ";

    String selectedCoolPepsico = " ";
    String selectedCoolCocacola = " ";
    String selectedCoolPran = " ";
    String selectedCoolMojo = " ";
    String selectedCoolOthers = " ";

    String selectedSigPepsico = " ";
    String selectedsigCocacola = " ";
    String selectedSigOtherBeve = " ";
    String selectedSigOthers = " ";

    RadioButton selectedCoolerB;
    RadioButton selectedSigB ;

    String coolerBrand = " ";
    String signageBrand = " ";

    ClearAllSaveData clearAllSaveData;
    ArrayList<CheckBox> coolChkbList = new ArrayList<>();
    ArrayList<CheckBox> sigangeChkbList = new ArrayList<>();
    boolean checkActiviyRunning ;
    boolean isBack ;
    private DrawerLayout drawerLayout;
    String userMobile = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooler_and_signage);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        initViews();
        removeToolbar();
        setupDrawer();
    }

   public void initViews(){
       ButterKnife.bind(this);
       setClickListener();

       drawerLayout = (DrawerLayout) findViewById(R.id.app_drawer);
       ImageView menuIcon = (ImageView) findViewById(R.id.drawer_menuIcon);
       menuIcon.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (!drawerLayout.isDrawerOpen(Gravity.LEFT))
                   drawerLayout.openDrawer(Gravity.LEFT);
           }
       });
       userName = getValueFromSharedPreferences("name",CoolerAndSignageAct.this);
       userMobile = getValueFromSharedPreferences("user_mobile",CoolerAndSignageAct.this);
       if(userName!=null){
           txvUserName.setText(userName);
       }
       if(userMobile!=null){
           txvMobile.setText(userMobile);
       }

       coolChkbList.add(chkbCoolPepsico);
       coolChkbList.add(chkbCoolCoca);
       coolChkbList.add(chkbCoolPran);
       coolChkbList.add(chkbCoolMojo);
       coolChkbList.add(chkbCoolOthers);

       sigangeChkbList.add(chkbSigPepsico);
       sigangeChkbList.add(chkbSigCoca);
       sigangeChkbList.add(chkbSigOthBever);
       sigangeChkbList.add(chkbSigOthSignage);

       clearAllSaveData = new ClearAllSaveData(CoolerAndSignageAct.this);
        //cooler status listen
       chkbCoolPepsico.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(chkbCoolPepsico.isChecked()){
                   selectedCoolPepsico = chkbCoolPepsico.getText().toString();
                   if(!coolerStatusList.contains("PepsiCo")){
                       coolerStatusList.add(selectedCoolPepsico);
                   }

                   saveBoleanValueSharedPreferences("cool_pepsico",true,CoolerAndSignageAct.this);

               }else {
                   saveBoleanValueSharedPreferences("cool_pepsico",false,CoolerAndSignageAct.this);
                   coolerStatusList.clear();
               }
           }
       });


       chkbCoolCoca.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(chkbCoolCoca.isChecked()){
                   selectedCoolCocacola = chkbCoolCoca.getText().toString();
                   if(!coolerStatusList.contains("CocaCola")){
                       coolerStatusList.add(selectedCoolCocacola);
                   }

                   saveBoleanValueSharedPreferences("cool_cocacola",true,CoolerAndSignageAct.this);

               }else {
                   saveBoleanValueSharedPreferences("cool_cocacola",false,CoolerAndSignageAct.this);
                   coolerStatusList.clear();
               }
           }
       });


       chkbCoolPran.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(chkbCoolPran.isChecked()){
                   selectedCoolPran = chkbCoolPran.getText().toString();
                   if(!coolerStatusList.contains("Pran")){
                       coolerStatusList.add(selectedCoolPran);
                   }

                   saveBoleanValueSharedPreferences("cool_pran",true,CoolerAndSignageAct.this);

               }else {
                   saveBoleanValueSharedPreferences("cool_pran",false,CoolerAndSignageAct.this);
                   coolerStatusList.clear();
               }
           }
       });

       chkbCoolMojo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(chkbCoolMojo.isChecked()){
                   selectedCoolMojo = chkbCoolMojo.getText().toString();
                   if(!coolerStatusList.contains("Mojo")){
                       coolerStatusList.add(selectedCoolMojo);
                   }

                   saveBoleanValueSharedPreferences("cool_mojo",true,CoolerAndSignageAct.this);

               }else {
                   saveBoleanValueSharedPreferences("cool_mojo",false,CoolerAndSignageAct.this);
                   coolerStatusList.clear();
               }
           }
       });

       chkbCoolOthers.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(chkbCoolOthers.isChecked()){
                   selectedCoolOthers = chkbCoolOthers.getText().toString();
                   if(!coolerStatusList.contains("Others")){
                       coolerStatusList.add(selectedCoolOthers);
                   }

                   saveBoleanValueSharedPreferences("cool_others",true,CoolerAndSignageAct.this);

               }else {
                   saveBoleanValueSharedPreferences("cool_others",false,CoolerAndSignageAct.this);
                   coolerStatusList.clear();
               }
           }
       });

       //signage status listen
       chkbSigPepsico.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(chkbSigPepsico.isChecked()){
                   selectedSigPepsico = chkbSigPepsico.getText().toString();
                   if(!signageStatusList.contains("PepsiCo")){
                       signageStatusList.add(selectedSigPepsico);
                   }

                   saveBoleanValueSharedPreferences("sig_pepsico",true,CoolerAndSignageAct.this);

               }else {
                   saveBoleanValueSharedPreferences("sig_pepsico",false,CoolerAndSignageAct.this);
                   signageStatusList.clear();
               }
           }
       });

       chkbSigCoca.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(chkbSigCoca.isChecked()){
                   selectedsigCocacola = chkbSigCoca.getText().toString();
                   if(!signageStatusList.contains("CocaCola")){
                       signageStatusList.add(selectedsigCocacola);
                   }

                   saveBoleanValueSharedPreferences("sig_cocacola",true,CoolerAndSignageAct.this);

               }else {
                   saveBoleanValueSharedPreferences("sig_cocacola",false,CoolerAndSignageAct.this);
                   signageStatusList.clear();
               }
           }
       });

       chkbSigOthBever.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(chkbSigOthBever.isChecked()){
                   selectedSigOtherBeve = chkbSigOthBever.getText().toString();
                   if(!signageStatusList.contains("Other Beverage")){
                       signageStatusList.add(selectedSigOtherBeve);
                   }

                   saveBoleanValueSharedPreferences("sig_other_bever",true,CoolerAndSignageAct.this);

               }else {
                   saveBoleanValueSharedPreferences("sig_other_bever",false,CoolerAndSignageAct.this);
                   signageStatusList.clear();
               }
           }
       });


       chkbSigOthSignage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(chkbSigOthSignage.isChecked()){
                   selectedSigOthers = chkbSigOthSignage.getText().toString();
                   if(!signageStatusList.contains("Others")){
                       signageStatusList.add(selectedSigOthers);
                   }

                   saveBoleanValueSharedPreferences("sig_others",true,CoolerAndSignageAct.this);

               }else {
                   saveBoleanValueSharedPreferences("sig_others",false,CoolerAndSignageAct.this);
                   signageStatusList.clear();
               }
           }
       });

        //cooler branding
       rbtnCoolBYes.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(rbtnCoolBYes.isChecked()){
                   saveBoleanValueSharedPreferences("rb_cool_yes",true,CoolerAndSignageAct.this);
               }else {
                   saveBoleanValueSharedPreferences("rb_cool_yes",false,CoolerAndSignageAct.this);
               }
           }
       });

       rbtnCoolBNo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(rbtnCoolBNo.isChecked()){
                   saveBoleanValueSharedPreferences("rb_cool_no",true,CoolerAndSignageAct.this);
               }else {
                   saveBoleanValueSharedPreferences("rb_cool_no",false,CoolerAndSignageAct.this);
               }
           }
       });
       rbtnCoolBAllDone.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(rbtnCoolBAllDone.isChecked()){
                   saveBoleanValueSharedPreferences("rb_cool_alldone",true,CoolerAndSignageAct.this);
               }else {
                   saveBoleanValueSharedPreferences("rb_cool_alldone",false,CoolerAndSignageAct.this);
               }
           }
       });

       //signage branding
       rbtnSigBYes.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(rbtnSigBYes.isChecked()){
                   saveBoleanValueSharedPreferences("rb_sig_yes",true,CoolerAndSignageAct.this);
               }else {
                   saveBoleanValueSharedPreferences("rb_sig_yes",false,CoolerAndSignageAct.this);
               }
           }
       });

       rbtnSigBNo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(rbtnSigBNo.isChecked()){
                   saveBoleanValueSharedPreferences("rb_sig_no",true,CoolerAndSignageAct.this);
               }else {
                   saveBoleanValueSharedPreferences("rb_sig_no",false,CoolerAndSignageAct.this);
               }
           }
       });
       rbtnSigBAllDone.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(rbtnSigBAllDone.isChecked()){
                   saveBoleanValueSharedPreferences("rb_sig_alldone",true,CoolerAndSignageAct.this);
               }else {
                   saveBoleanValueSharedPreferences("rb_sig_alldone",false,CoolerAndSignageAct.this);
               }
           }
       });








   }
    public interface ExpandableButtonListener {
        void onViewExpanded();
        void onViewCollapsed();
    }
    private void setClickListener() {
        isBack = getBoleanValueSharedPreferences("is_back",CoolerAndSignageAct.this);
        checkActiviyRunning = getBoleanValueSharedPreferences("is_active",CoolerAndSignageAct.this);
        if(checkActiviyRunning){
            btnUpdate.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.GONE);
            btnBack.setVisibility(GONE);
        }else {
            btnNext.setVisibility(View.VISIBLE);
        }
        if(isBack){
            btnBack.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
        }


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedCoolerB  = (RadioButton)findViewById(rgCoolBrand.getCheckedRadioButtonId());
                selectedSigB = (RadioButton)findViewById(rgSigBrand.getCheckedRadioButtonId());

                if( selectedCoolerB!=null){
                    coolerBrand = selectedCoolerB.getText().toString();
                }
                saveToSharedPreferences("cooler_brand",coolerBrand,CoolerAndSignageAct.this);

                if( selectedSigB!=null){
                    signageBrand = selectedSigB.getText().toString();
                }
                saveToSharedPreferences("signage_brand",signageBrand,CoolerAndSignageAct.this);

                boolean atLeastOneChecked = false;
                for (int i = 0; i < coolChkbList.size(); i++){
                    CheckBox checkBox = coolChkbList.get(i);
                    if (checkBox.isChecked()) {
                        atLeastOneChecked = true;
                        break;
                    }
                }
                if (!atLeastOneChecked){
                    //make Toast "Hey, you didn't check a box"
                    clearAllSaveData.openDialog("Must Check an Cooler Status Item");
                    return;
                }
                boolean atLeastOneSigChecked = false;
                for (int i = 0; i < sigangeChkbList.size(); i++){
                    CheckBox checkBox = sigangeChkbList.get(i);
                    if (checkBox.isChecked()) {
                        atLeastOneSigChecked = true;
                        break;
                    }
                }
                if(rgCoolBrand.getCheckedRadioButtonId()==-1)
                {
                    clearAllSaveData.openDialog("Please select cooler branding");
                    return;
                }
                if (!atLeastOneSigChecked){
                    //make Toast "Hey, you didn't check a box"
                    clearAllSaveData.openDialog("Must Check an Signage Status Item");
                    return;
                }

                if(rgSigBrand.getCheckedRadioButtonId()==-1){
                    clearAllSaveData.openDialog("Please select signage branding");
                    return;
                }

                if(chkbCoolPepsico.isChecked()){
                    selectedCoolPepsico = chkbCoolPepsico.getText().toString();
                    if(!coolerStatusList.contains("PepsiCo")){
                        coolerStatusList.add(selectedCoolPepsico);
                    }

                    saveBoleanValueSharedPreferences("cool_pepsico",true,CoolerAndSignageAct.this);

                }

                if(chkbCoolCoca.isChecked()){
                    selectedCoolCocacola = chkbCoolCoca.getText().toString();
                    if(!coolerStatusList.contains("CocaCola")){
                        coolerStatusList.add(selectedCoolCocacola);
                    }

                    saveBoleanValueSharedPreferences("cool_cocacola",true,CoolerAndSignageAct.this);

                }
                if(chkbCoolPran.isChecked()){
                    selectedCoolPran = chkbCoolPran.getText().toString();
                    if(!coolerStatusList.contains("Pran")){
                        coolerStatusList.add(selectedCoolPran);
                    }

                    saveBoleanValueSharedPreferences("cool_pran",true,CoolerAndSignageAct.this);

                }
                if(chkbCoolMojo.isChecked()){
                    selectedCoolMojo = chkbCoolMojo.getText().toString();
                    if(!coolerStatusList.contains("Mojo")){
                        coolerStatusList.add(selectedCoolMojo);
                    }

                    saveBoleanValueSharedPreferences("cool_mojo",true,CoolerAndSignageAct.this);

                }
                if(chkbCoolOthers.isChecked()){
                    selectedCoolOthers = chkbCoolOthers.getText().toString();
                    if(!coolerStatusList.contains("Others")){
                        coolerStatusList.add(selectedCoolOthers);
                    }

                    saveBoleanValueSharedPreferences("cool_others",true,CoolerAndSignageAct.this);

                }

                if(chkbSigPepsico.isChecked()){
                    selectedSigPepsico = chkbSigPepsico.getText().toString();
                    if(!signageStatusList.contains("PepsiCo")){
                        signageStatusList.add(selectedSigPepsico);
                    }

                    saveBoleanValueSharedPreferences("sig_pepsico",true,CoolerAndSignageAct.this);

                }

                if(chkbSigCoca.isChecked()){
                    selectedsigCocacola = chkbSigCoca.getText().toString();
                    if(!signageStatusList.contains("CocaCola")){
                        signageStatusList.add(selectedsigCocacola);
                    }

                    saveBoleanValueSharedPreferences("sig_cocacola",true,CoolerAndSignageAct.this);

                }

                if(chkbSigOthBever.isChecked()){
                    selectedSigOtherBeve = chkbSigOthBever.getText().toString();
                    if(!signageStatusList.contains("Other Beverage")){
                        signageStatusList.add(selectedSigOtherBeve);
                    }

                    saveBoleanValueSharedPreferences("sig_other_bever",true,CoolerAndSignageAct.this);

                }
                if(chkbSigOthSignage.isChecked()){
                    selectedSigOthers = chkbSigOthSignage.getText().toString();
                    if(!signageStatusList.contains("Others")){
                        signageStatusList.add(selectedSigOthers);
                    }

                    saveBoleanValueSharedPreferences("sig_others",true,CoolerAndSignageAct.this);

                }

                Intent signIn = new Intent(CoolerAndSignageAct.this,ReconfirmedPageAct.class);
                startActivity(signIn);
                ActivityCompat.finishAffinity(CoolerAndSignageAct.this);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(CoolerAndSignageAct.this,SummerHangamaEnrollAct.class);
                startActivity(signIn);
                //ActivityCompat.finishAffinity(CoolerAndSignageAct.this);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCoolerB  = (RadioButton)findViewById(rgCoolBrand.getCheckedRadioButtonId());
                selectedSigB = (RadioButton)findViewById(rgSigBrand.getCheckedRadioButtonId());

                if( selectedCoolerB!=null){
                    coolerBrand = selectedCoolerB.getText().toString();
                }
                saveToSharedPreferences("cooler_brand",coolerBrand,CoolerAndSignageAct.this);

                if( selectedSigB!=null){
                    signageBrand = selectedSigB.getText().toString();
                }
                saveToSharedPreferences("signage_brand",signageBrand,CoolerAndSignageAct.this);

                boolean atLeastOneChecked = false;
                for (int i = 0; i < coolChkbList.size(); i++){
                    CheckBox checkBox = coolChkbList.get(i);
                    if (checkBox.isChecked()) {
                        atLeastOneChecked = true;
                        break;
                    }
                }
                if (!atLeastOneChecked){
                    //make Toast "Hey, you didn't check a box"
                    clearAllSaveData.openDialog("Must Check an Cooler Status Item");
                    return;
                }
                boolean atLeastOneSigChecked = false;
                for (int i = 0; i < sigangeChkbList.size(); i++){
                    CheckBox checkBox = sigangeChkbList.get(i);
                    if (checkBox.isChecked()) {
                        atLeastOneSigChecked = true;
                        break;
                    }
                }
                if(rgCoolBrand.getCheckedRadioButtonId()==-1)
                {
                    clearAllSaveData.openDialog("Please select cooler branding");
                    return;
                }
                if (!atLeastOneSigChecked){
                    //make Toast "Hey, you didn't check a box"
                    clearAllSaveData.openDialog("Must Check an Signage Status Item");
                    return;
                }

                if(rgSigBrand.getCheckedRadioButtonId()==-1){
                    clearAllSaveData.openDialog("Please select signage branding");
                    return;
                }

                Intent signIn = new Intent(CoolerAndSignageAct.this,VolumeAndImagesAct.class);
                startActivity(signIn);
              //  ActivityCompat.finishAffinity(CoolerAndSignageAct.this);
                //clearAllSaveData.saveCoolerStatusList(coolerStatusList);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        //cooler status listen

        if(chkbCoolPepsico!=null){
            boolean isPepsico= getBoleanValueSharedPreferences("cool_pepsico",CoolerAndSignageAct.this);
            chkbCoolPepsico.setChecked(isPepsico);

            if(isPepsico){
                if(!coolerStatusList.contains("PepsiCo")){
                    coolerStatusList.add("PepsiCo");
                }

            }
        }
        if(chkbCoolCoca!=null){
            boolean isCocacola = getBoleanValueSharedPreferences("cool_cocacola",CoolerAndSignageAct.this);
            chkbCoolCoca.setChecked(isCocacola);
            if(isCocacola){
                if(!coolerStatusList.contains("CocaCola")){
                    coolerStatusList.add("CocaCola");
                }

            }
        }
        if(chkbCoolPran!=null){
            boolean isPran= getBoleanValueSharedPreferences("cool_pran",CoolerAndSignageAct.this);
            chkbCoolPran.setChecked(isPran);
            if(isPran){
                if(!coolerStatusList.contains("Pran")){
                    coolerStatusList.add("Pran");
                }

            }
        }
        if(chkbCoolMojo!=null){
            boolean isMojo= getBoleanValueSharedPreferences("cool_mojo",CoolerAndSignageAct.this);
            chkbCoolMojo.setChecked(isMojo);
            if(isMojo){
                if(!coolerStatusList.contains("Mojo")){
                    coolerStatusList.add("Mojo");
                }


            }
        }

        if(chkbCoolOthers!=null){
            boolean isOthers= getBoleanValueSharedPreferences("cool_others",CoolerAndSignageAct.this);

            chkbCoolOthers.setChecked(isOthers);
            if(isOthers){
                if(!coolerStatusList.contains("Others")){
                    coolerStatusList.add("Others");
                }

            }
        }

        //signage status
        if(chkbSigPepsico!=null){
            boolean isSPepsico= getBoleanValueSharedPreferences("sig_pepsico",CoolerAndSignageAct.this);
            chkbSigPepsico.setChecked(isSPepsico);

            if(isSPepsico){
                if(!signageStatusList.contains("PepsiCo")){
                    signageStatusList.add("PepsiCo");
                }

            }
        }
        if(chkbSigCoca!=null){
            boolean isSCocacola = getBoleanValueSharedPreferences("sig_cocacola",CoolerAndSignageAct.this);
            chkbSigCoca.setChecked(isSCocacola);
            if(isSCocacola){
                if(!signageStatusList.contains("CocaCola")){
                    signageStatusList.add("CocaCola");
                }

            }

        }

        if(chkbSigOthBever!=null){
            boolean isSOtherBev = getBoleanValueSharedPreferences("sig_other_bever",CoolerAndSignageAct.this);
            chkbSigOthBever.setChecked(isSOtherBev);
            if(isSOtherBev){
                if(!signageStatusList.contains("Other Beverage")){
                    signageStatusList.add("Other Beverage");
                }

            }
        }

        if(chkbSigOthSignage!=null){
            boolean isSOthers = getBoleanValueSharedPreferences("sig_others",CoolerAndSignageAct.this);
            chkbSigOthSignage.setChecked(isSOthers);
            if(isSOthers){
                if(!signageStatusList.contains("Others")){
                    signageStatusList.add("Others");
                }

            }
        }

        //cooler branding

        if(rbtnCoolBYes!=null){
            rbtnCoolBYes.setChecked(getBoleanValueSharedPreferences("rb_cool_yes",CoolerAndSignageAct.this));
        }
        if(rbtnCoolBNo!=null){
            rbtnCoolBNo.setChecked(getBoleanValueSharedPreferences("rb_cool_no",CoolerAndSignageAct.this));
        }

        if(rbtnCoolBAllDone!=null){
            rbtnCoolBAllDone.setChecked(getBoleanValueSharedPreferences("rb_cool_alldone",CoolerAndSignageAct.this));
        }

        //signage branding

        if(rbtnSigBYes!=null){
            rbtnSigBYes.setChecked(getBoleanValueSharedPreferences("rb_sig_yes",CoolerAndSignageAct.this));
        }
        if(rbtnSigBNo!=null){
            rbtnSigBNo.setChecked(getBoleanValueSharedPreferences("rb_sig_no",CoolerAndSignageAct.this));
        }

        if(rbtnSigBAllDone!=null){
            rbtnSigBAllDone.setChecked(getBoleanValueSharedPreferences("rb_sig_alldone",CoolerAndSignageAct.this));
        }



      //  clearAllSaveData.saveCoolerStatusList(coolerStatusList);

    }


}
