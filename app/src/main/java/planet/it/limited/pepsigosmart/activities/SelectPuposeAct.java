package planet.it.limited.pepsigosmart.activities;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import planet.it.limited.pepsigosmart.R;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;
import planet.it.limited.pepsigosmart.utils.FontCustomization;

import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getValueFromSharedPreferences;

public class SelectPuposeAct extends AppCompatActivity {
    @BindView(R.id.lin_retail_initiative)
    RelativeLayout btnRetailIni;
    @BindView(R.id.txv_user_name)
    TextView txvUserName;
    @BindView(R.id.txv_market_visit)
    TextView txvMarketVisit;

    @BindView(R.id.txv_retail_init)
    TextView txvRetInit;

    @BindView(R.id.txv_user_mobile)
    TextView txvUserMobile;
    @BindView(R.id.imgv_logout)
    ImageView btnLogOut;
//    @BindView(R.id.btn_sign_out)
//    Button btnLogOut;
    String userName = " ";
    String userMobile = " ";
    ClearAllSaveData clearAllSaveData;
    FontCustomization fontCustomization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pupose);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        initViews();
    }

    public void initViews(){
        ButterKnife.bind(this);
        fontCustomization = new FontCustomization(SelectPuposeAct.this);
        clearAllSaveData = new ClearAllSaveData(SelectPuposeAct.this);
        userName = getValueFromSharedPreferences("name",SelectPuposeAct.this);
        userMobile = getValueFromSharedPreferences("user_mobile",SelectPuposeAct.this);
        if(userName!=null){
            txvUserName.setText(userName);
        }

        if(userMobile!=null){
            txvUserMobile.setText(userMobile);
        }

        txvMarketVisit.setTypeface(fontCustomization.getLucidaCalligraphy());
        txvRetInit.setTypeface(fontCustomization.getLucidaCalligraphy());

        btnRetailIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(SelectPuposeAct.this,RetailInitiativeAct.class);
                startActivity(signIn);
              //  ActivityCompat.finishAffinity(SelectPuposeAct.this);
            }
        });


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllSaveData.logoutNavigation();
            }
        });
    }
}
