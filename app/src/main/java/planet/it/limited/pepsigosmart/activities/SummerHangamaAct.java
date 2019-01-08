package planet.it.limited.pepsigosmart.activities;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import planet.it.limited.pepsigosmart.R;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;

import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getValueFromSharedPreferences;

public class SummerHangamaAct extends AppCompatActivity {

    @BindView(R.id.txv_user_name)
    TextView txvUserName;
    @BindView(R.id.txv_user_mobile)
    TextView txvUserMobile;
    @BindView(R.id.imgv_logout)
    ImageView btnLogOut;
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.imgbtn_summer_enrollment)
    ImageButton imgBtnEnrollment;

    String userName = " ";
    String userMobile = " ";
    ClearAllSaveData clearAllSaveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven_up_summer_hangama);
        initViews();
    }

    public void initViews(){
        ButterKnife.bind(this);
        setClickListener();
        clearAllSaveData = new ClearAllSaveData(SummerHangamaAct.this);
        userName = getValueFromSharedPreferences("name",SummerHangamaAct.this);
        userMobile = getValueFromSharedPreferences("user_mobile",SummerHangamaAct.this);

        if(userName!=null){
            txvUserName.setText(userName);
        }
        if(userMobile!=null){
            txvUserMobile.setText(userMobile);
        }

    }

    private void setClickListener() {
        imgBtnEnrollment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(SummerHangamaAct.this,SummerHangamaEnrollAct.class);
                startActivity(signIn);
             //   ActivityCompat.finishAffinity(RetailInitiativeAct.this);
            }
        });




        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(SummerHangamaAct.this,SelectPuposeAct.class);
                startActivity(signIn);
                ActivityCompat.finishAffinity(SummerHangamaAct.this);

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
