package planet.it.limited.pepsigosmart.activities.bondhu_club;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import planet.it.limited.pepsigosmart.R;
import planet.it.limited.pepsigosmart.activities.CoolerAndSignageAct;
import planet.it.limited.pepsigosmart.activities.DrawerBondhuClubAct;
import planet.it.limited.pepsigosmart.activities.ReconfirmedPageAct;
import planet.it.limited.pepsigosmart.activities.SummerHangamaEnrollAct;
import planet.it.limited.pepsigosmart.activities.VolumeAndImagesAct;
import planet.it.limited.pepsigosmart.utils.BusyDialog;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;

import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getBoleanValueSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getValueFromSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveBoleanValueSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveToSharedPreferences;

public class CoolerAndSignageBAct extends DrawerBondhuClubAct {
    private DrawerLayout drawerLayout;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.txv_user_name)
    TextView txvUserName;
    @BindView(R.id.txv_mobile)
    TextView txvMobile;
    @BindView(R.id.btn_update)
    Button btnUpdate;

    @BindView(R.id.rg_category)
    RadioGroup rgCategory;
    @BindView(R.id.rg_eating_drinking)
    RadioGroup rgEatAndDrink;

    @BindView(R.id.rbtn_eating_drinking)
    RadioButton rbEatandDrink;

    @BindView(R.id.rbtn_hvpo_mixed)
    RadioButton rbHVPOMixed;
    @BindView(R.id.rbtn_pepsi_exclusive)
    RadioButton rbPepsiExclu;

    @BindView(R.id.rbtn_grocery)
    RadioButton rbGrocery;
    @BindView(R.id.rbtn_wholesale)
    RadioButton rbWholeSale;
    @BindView(R.id.rbtn_aquafina_hvpo)
    RadioButton rbAquafinaHVPO;


    @BindView(R.id.cb_csd)
    CheckBox cbCSD;
    @BindView(R.id.cb_water)
    CheckBox cbWater;
    @BindView(R.id.cb_lrb)
    CheckBox cbLRB;

    @BindView(R.id.edt_csd)
    EditText edtCSD;
    @BindView(R.id.edt_water)
    EditText edtWater;
    @BindView(R.id.edt_lrb)
    EditText edtLRB;




    @BindView(R.id.lin_show_eating_drinking)
    LinearLayout linChildEatAndDrnk;


    BusyDialog mBusyDialog;
    ClearAllSaveData clearAllSaveData;
    boolean checkActiviyRunning ;
    String userName = " ";
    String userMobile = " ";
    String coolCategory = " ";
    String industCSD = " ";
    String industWater = " ";
    String industLRB = " ";

    ArrayList<String> industryVolList;
    RadioButton selectedCategoryRB;
    RadioButton selectedEatAndDrinkRB ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooler_and_signage_b);
        initViews();
        removeToolbar();
        setupDrawer();
    }

    public void initViews(){
        clearAllSaveData = new ClearAllSaveData(CoolerAndSignageBAct.this);
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
        industryVolList = new ArrayList<>();
        checkActiviyRunning = getBoleanValueSharedPreferences("is_active",CoolerAndSignageBAct.this);


        if(checkActiviyRunning){
            btnUpdate.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.GONE);
            btnBack.setVisibility(View.GONE);
        }else {
            btnNext.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
        }

        userName = getValueFromSharedPreferences("name",CoolerAndSignageBAct.this);
        userMobile = getValueFromSharedPreferences("user_mobile",CoolerAndSignageBAct.this);
        if(userName!=null){
            txvUserName.setText(userName);
        }
        if(userMobile!=null){
            txvMobile.setText(userMobile);
        }


    }

    public void setClickListener(){

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(CoolerAndSignageBAct.this,BondhuClubEnrollAct.class);
                startActivity(signIn);
                //ActivityCompat.finishAffinity(CoolerAndSignageAct.this);
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(CoolerAndSignageBAct.this,ReconfirmedBAct.class);
                startActivity(signIn);
                ActivityCompat.finishAffinity(CoolerAndSignageBAct.this);

            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedCategoryRB  = (RadioButton)findViewById(rgCategory.getCheckedRadioButtonId());
                selectedEatAndDrinkRB = (RadioButton)findViewById(rgEatAndDrink.getCheckedRadioButtonId());

                if( selectedCategoryRB!=null){
                    coolCategory = selectedCategoryRB.getText().toString();
                }
                if(rbEatandDrink.isChecked()){
                    if( selectedEatAndDrinkRB!=null){
                        coolCategory = selectedEatAndDrinkRB.getText().toString();
                    }
                }
                saveToSharedPreferences("cool_category_b",coolCategory,CoolerAndSignageBAct.this);

                if(cbCSD.isChecked()){
                    //industryVolList.clear();
                    String industCSD = edtCSD.getText().toString();
                    saveToSharedPreferences("indust_csd",industCSD,CoolerAndSignageBAct.this);
                }

                if(cbWater.isChecked()){
                   // industryVolList.clear();
                    String industWater = edtWater.getText().toString();
                    saveToSharedPreferences("indust_water",industWater,CoolerAndSignageBAct.this);
                }
                if(cbLRB.isChecked()){
                    // industryVolList.clear();
                    String industLRB = edtLRB.getText().toString();
                    saveToSharedPreferences("indust_lrb",industLRB,CoolerAndSignageBAct.this);
                }


                if(rgCategory.getCheckedRadioButtonId()==-1)
                {
                    clearAllSaveData.openDialog("Please select a category");
                    return;
                }



                Intent signIn = new Intent(CoolerAndSignageBAct.this,VolumeAndImagesBAct.class);
                startActivity(signIn);
            }
        });

        rbEatandDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linChildEatAndDrnk.setVisibility(View.VISIBLE);
                if(rbEatandDrink.isChecked()){
                    saveBoleanValueSharedPreferences("rb_eat_drink",true,CoolerAndSignageBAct.this);

                    saveBoleanValueSharedPreferences("rb_grocery",false,CoolerAndSignageBAct.this);
                    saveBoleanValueSharedPreferences("rb_whole_sale",false,CoolerAndSignageBAct.this);
                    saveBoleanValueSharedPreferences("rb_aquafina_hvpo",false,CoolerAndSignageBAct.this);
                }else {
                    saveBoleanValueSharedPreferences("rb_eat_drink",false,CoolerAndSignageBAct.this);
                }

            }
        });

        rbGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linChildEatAndDrnk.setVisibility(View.GONE);


                if(rbGrocery.isChecked()){
                    saveBoleanValueSharedPreferences("rb_grocery",true,CoolerAndSignageBAct.this);

                    saveBoleanValueSharedPreferences("rb_eat_drink",false,CoolerAndSignageBAct.this);
                    saveBoleanValueSharedPreferences("rb_whole_sale",false,CoolerAndSignageBAct.this);
                    saveBoleanValueSharedPreferences("rb_aquafina_hvpo",false,CoolerAndSignageBAct.this);
                    rbEatandDrink.setChecked(false);
//                    if(rbHVPOMixed.isChecked()){
                        rbHVPOMixed.setChecked(false);
                    rbPepsiExclu.setChecked(false);
                    saveBoleanValueSharedPreferences("rb_hvpo_mixed",false,CoolerAndSignageBAct.this);
                    saveBoleanValueSharedPreferences("rb_aquafina_hvpo",false,CoolerAndSignageBAct.this);
//                    }
//                    if(rbPepsiExclu.isChecked()){

//                    }

                }else {
                    saveBoleanValueSharedPreferences("rb_grocery",false,CoolerAndSignageBAct.this);
                }
            }
        });

        rbWholeSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linChildEatAndDrnk.setVisibility(View.GONE);
                if(rbWholeSale.isChecked()){
                    saveBoleanValueSharedPreferences("rb_whole_sale",true,CoolerAndSignageBAct.this);
                    saveBoleanValueSharedPreferences("rb_eat_drink",false,CoolerAndSignageBAct.this);
                    saveBoleanValueSharedPreferences("rb_grocery",false,CoolerAndSignageBAct.this);
                    saveBoleanValueSharedPreferences("rb_aquafina_hvpo",false,CoolerAndSignageBAct.this);

                    rbEatandDrink.setChecked(false);
                    rbHVPOMixed.setChecked(false);
                    rbPepsiExclu.setChecked(false);
                    saveBoleanValueSharedPreferences("rb_hvpo_mixed",false,CoolerAndSignageBAct.this);
                    saveBoleanValueSharedPreferences("rb_aquafina_hvpo",false,CoolerAndSignageBAct.this);
                }else {
                    saveBoleanValueSharedPreferences("rb_whole_sale",false,CoolerAndSignageBAct.this);
                }
            }
        });
        rbAquafinaHVPO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linChildEatAndDrnk.setVisibility(View.GONE);

                if(rbAquafinaHVPO.isChecked()){
                    saveBoleanValueSharedPreferences("rb_aquafina_hvpo",true,CoolerAndSignageBAct.this);
                    saveBoleanValueSharedPreferences("rb_whole_sale",false,CoolerAndSignageBAct.this);
                    saveBoleanValueSharedPreferences("rb_eat_drink",false,CoolerAndSignageBAct.this);
                    saveBoleanValueSharedPreferences("rb_grocery",false,CoolerAndSignageBAct.this);

                    rbEatandDrink.setChecked(false);
                    rbHVPOMixed.setChecked(false);
                    rbPepsiExclu.setChecked(false);
                    saveBoleanValueSharedPreferences("rb_hvpo_mixed",false,CoolerAndSignageBAct.this);
                    saveBoleanValueSharedPreferences("rb_aquafina_hvpo",false,CoolerAndSignageBAct.this);
                }else {
                    saveBoleanValueSharedPreferences("rb_aquafina_hvpo",false,CoolerAndSignageBAct.this);
                }
            }
        });

        rbHVPOMixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbHVPOMixed.isChecked()){
                    saveBoleanValueSharedPreferences("rb_hvpo_mixed",true,CoolerAndSignageBAct.this);
                }else {
                    saveBoleanValueSharedPreferences("rb_hvpo_mixed",false,CoolerAndSignageBAct.this);
                }

            }
        });

        rbPepsiExclu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbPepsiExclu.isChecked()){
                    saveBoleanValueSharedPreferences("rb_pepsi_exclusive",true,CoolerAndSignageBAct.this);
                }else {
                    saveBoleanValueSharedPreferences("rb_pepsi_exclusive",false,CoolerAndSignageBAct.this);
                }

            }
        });


        cbCSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbCSD.isChecked()){
                    edtCSD.setEnabled(true);
                    edtCSD.requestFocus();
                   // industCSD = cbCSD.getText().toString();

                    saveBoleanValueSharedPreferences("is_indust_csd",true,CoolerAndSignageBAct.this);

                }else {
                    edtCSD.setEnabled(false);
                    saveBoleanValueSharedPreferences("is_indust_csd",false,CoolerAndSignageBAct.this);
                }
            }
        });

        cbWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbWater.isChecked()){
                    edtWater.setEnabled(true);
                    edtWater.requestFocus();

                    saveBoleanValueSharedPreferences("is_indust_water",true,CoolerAndSignageBAct.this);
                }else {
                    edtWater.setEnabled(false);
                    saveBoleanValueSharedPreferences("is_indust_water",false,CoolerAndSignageBAct.this);
                }
            }
        });
        cbLRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbLRB.isChecked()){
                    edtLRB.setEnabled(true);
                    edtLRB.requestFocus();
                    saveBoleanValueSharedPreferences("is_indust_lrb",true,CoolerAndSignageBAct.this);
                }else {
                    edtLRB.setEnabled(false);
                    saveBoleanValueSharedPreferences("is_indust_lrb",false,CoolerAndSignageBAct.this);
                }
            }
        });


        edtCSD.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {

                if(editable.toString().length()>0){
                    String pepsiCSD = editable.toString();

                    saveToSharedPreferences("indust_csd", pepsiCSD, CoolerAndSignageBAct.this);
                }

            }
        });
        edtWater.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {

                if(editable.toString().length()>0){
                    String pepsiWater = editable.toString();

                    saveToSharedPreferences("indust_water", pepsiWater, CoolerAndSignageBAct.this);
                }

            }
        });

        edtLRB.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {

                if(editable.toString().length()>0){
                    String pepsiLRB = editable.toString();

                    saveToSharedPreferences("indust_lrb", pepsiLRB, CoolerAndSignageBAct.this);
                }

            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();

        //cooler branding

        if(rbEatandDrink!=null){
            boolean isCheckedEAndD = getBoleanValueSharedPreferences("rb_eat_drink",CoolerAndSignageBAct.this);
            rbEatandDrink.setChecked(isCheckedEAndD);

            if(rbEatandDrink.isChecked()){
                linChildEatAndDrnk.setVisibility(View.VISIBLE);
                rbHVPOMixed.setChecked(getBoleanValueSharedPreferences("rb_hvpo_mixed",CoolerAndSignageBAct.this));
                rbPepsiExclu.setChecked(getBoleanValueSharedPreferences("rb_pepsi_exclusive",CoolerAndSignageBAct.this));

            }
        }
        if(rbGrocery!=null){
            rbGrocery.setChecked(getBoleanValueSharedPreferences("rb_grocery",CoolerAndSignageBAct.this));
        }

        if(rbWholeSale!=null){
            rbWholeSale.setChecked(getBoleanValueSharedPreferences("rb_whole_sale",CoolerAndSignageBAct.this));
        }



        if(rbAquafinaHVPO!=null){
            rbAquafinaHVPO.setChecked(getBoleanValueSharedPreferences("rb_aquafina_hvpo",CoolerAndSignageBAct.this));
        }


        if(cbCSD!=null){
            boolean isChkCSD = getBoleanValueSharedPreferences("is_indust_csd",CoolerAndSignageBAct.this);

            cbCSD.setChecked(isChkCSD);
            if(isChkCSD){
                edtCSD.setEnabled(true);
                 String csd = getValueFromSharedPreferences("indust_csd",CoolerAndSignageBAct.this);
                 if(csd!=null){
                     edtCSD.setText(csd);
                 }


            }

        }
        if(cbWater!=null){
            boolean isChkWater = getBoleanValueSharedPreferences("is_indust_water",CoolerAndSignageBAct.this);
            cbWater.setChecked(isChkWater);
            if(isChkWater){
                edtWater.setEnabled(true);
                String water = getValueFromSharedPreferences("indust_water",CoolerAndSignageBAct.this);
                if(water!=null){
                    edtWater.setText(water);
                }


            }

        }
        if(cbLRB!=null){
            boolean isChkLRB = getBoleanValueSharedPreferences("is_indust_lrb",CoolerAndSignageBAct.this);
            cbLRB.setChecked(isChkLRB);
            if(isChkLRB){
                edtLRB.setEnabled(true);

                String lrb = getValueFromSharedPreferences("indust_lrb",CoolerAndSignageBAct.this);
                if(lrb!=null){
                    edtLRB.setText(lrb);
                }


            }

        }
        //  clearAllSaveData.saveCoolerStatusList(coolerStatusList);

    }
}
