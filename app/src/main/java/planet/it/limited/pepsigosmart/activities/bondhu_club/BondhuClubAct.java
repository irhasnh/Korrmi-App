package planet.it.limited.pepsigosmart.activities.bondhu_club;

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
import planet.it.limited.pepsigosmart.activities.SelectPuposeAct;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;

import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getValueFromSharedPreferences;

public class BondhuClubAct extends AppCompatActivity {
    @BindView(R.id.txv_user_name)
    TextView txvUserName;
    @BindView(R.id.txv_user_mobile)
    TextView txvUserMobile;

    @BindView(R.id.imgbtn_bondhu_enrollment)
    ImageButton btnBondhuEnroll;

    @BindView(R.id.imgbtn_bondhu_audit)
    ImageButton btnBondhuAudit;

    @BindView(R.id.imgv_logout)
    ImageView btnLogOut;
    @BindView(R.id.btn_back)
    ImageButton btnBack;

    String userName = " ";
    String userMobile = " ";
    ClearAllSaveData clearAllSaveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bondhu_club);

        initViews();
    }


    public void initViews(){
        ButterKnife.bind(this);
        setClickListener();
        clearAllSaveData = new ClearAllSaveData(BondhuClubAct.this);
        userName = getValueFromSharedPreferences("name",BondhuClubAct.this);
        userMobile = getValueFromSharedPreferences("user_mobile",BondhuClubAct.this);

        if(userName!=null){
            txvUserName.setText(userName);
        }
        if(userMobile!=null){
            txvUserMobile.setText(userMobile);
        }
    }

    private void setClickListener() {


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(BondhuClubAct.this,SelectPuposeAct.class);
                startActivity(signIn);
                ActivityCompat.finishAffinity(BondhuClubAct.this);

            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllSaveData.logoutNavigation();
            }
        });

        btnBondhuEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(BondhuClubAct.this,BondhuClubEnrollAct.class);
                startActivity(signIn);
             //   ActivityCompat.finishAffinity(BondhuClubAct.this);
            }
        });

    }
}
