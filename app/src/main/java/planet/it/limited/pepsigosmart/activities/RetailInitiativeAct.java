package planet.it.limited.pepsigosmart.activities;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import planet.it.limited.pepsigosmart.R;
import planet.it.limited.pepsigosmart.activities.bondhu_club.BondhuClubAct;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;

import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getValueFromSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveToSharedPreferences;

public class RetailInitiativeAct extends AppCompatActivity {

    @BindView(R.id.exp_btn_summer_hangama)
    LinearLayout expBtnSummerHangama;

    @BindView(R.id.exp_btn_bondhu_club)
    LinearLayout expBtnBondhuClub;


    @BindView(R.id.btn_back)
    ImageButton btnBack;

    ExpandableButtonListener expandableButtonListener = null;
    @BindView(R.id.txv_user_name)
    TextView txvUserName;
    @BindView(R.id.txv_user_mobile)
    TextView txvUserMobile;
    @BindView(R.id.imgv_logout)
    ImageView btnLogOut;

    String userName = " ";
    String userMobile = " ";
    ClearAllSaveData clearAllSaveData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retail_initiative);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        initViews();
    }

    public void initViews(){
        ButterKnife.bind(this);
        setClickListener();
        clearAllSaveData = new ClearAllSaveData(RetailInitiativeAct.this);
        userName = getValueFromSharedPreferences("name",RetailInitiativeAct.this);
        userMobile = getValueFromSharedPreferences("user_mobile",RetailInitiativeAct.this);

        if(userName!=null){
            txvUserName.setText(userName);
        }
        if(userMobile!=null){
            txvUserMobile.setText(userMobile);
        }

    }

    public interface ExpandableButtonListener {
        void onViewExpanded();
        void onViewCollapsed();
    }


    private void setClickListener() {
        expBtnSummerHangama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(RetailInitiativeAct.this,SummerHangamaAct.class);
                startActivity(signIn);
               // ActivityCompat.finishAffinity(RetailInitiativeAct.this);
                saveToSharedPreferences("act_tag","summer_hangama_enroll",RetailInitiativeAct.this);

            }
        });

        expBtnBondhuClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(RetailInitiativeAct.this,BondhuClubAct.class);
                startActivity(signIn);
               // ActivityCompat.finishAffinity(RetailInitiativeAct.this);
                saveToSharedPreferences("act_tag","bondhu_club_enroll",RetailInitiativeAct.this);

            }
        });





        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(RetailInitiativeAct.this,SelectPuposeAct.class);
                startActivity(signIn);
                ActivityCompat.finishAffinity(RetailInitiativeAct.this);

            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllSaveData.logoutNavigation();
            }
        });
    }





    @Override
    protected void onResume() {
        super.onResume();

    }


}
