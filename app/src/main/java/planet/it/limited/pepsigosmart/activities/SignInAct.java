package planet.it.limited.pepsigosmart.activities;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import planet.it.limited.pepsigosmart.R;
import planet.it.limited.pepsigosmart.task.LoginTask;
import planet.it.limited.pepsigosmart.utils.BusyDialog;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;

public class SignInAct extends AppCompatActivity {
    @BindView(R.id.btn_sign_in)
    Button btnSignIn;
    @BindView(R.id.edt_user_name)
    EditText edtUserName;
    @BindView(R.id.edt_password)
    EditText edtPassword;
//    @BindView(R.id.txv_forgot_pass)
//    TextView txvForgotPass;
    BusyDialog mBusyDialog;

    String userName = " ";
    String password = " ";
    ClearAllSaveData clearAllSaveData;
    public static boolean isValidUser ;
    LoginTask loginTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        initViews();

    }
    public void initViews(){
        ButterKnife.bind(this);
//        edtUserName.setText("01985311474");
//        edtPassword.setText("66885926");
       setOnClickListener();
       clearAllSaveData = new ClearAllSaveData(SignInAct.this);
        loginTask =new LoginTask(SignInAct.this,SignInAct.this);

    }

    public void setOnClickListener(){
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearAllSaveData.clearAllWhenSearchOutlet();
                userName = edtUserName.getText().toString();
                password = edtPassword.getText().toString();
                if (clearAllSaveData.checkInternet()) {
                    if (userName.length() > 0 && password.length() > 0) {
                        loginTask.doLogin(userName, password);
                    } else {
                        clearAllSaveData.openDialog("Give User Mobile And Password");
                    }
                }else {
                    clearAllSaveData.openDialog("Your Device is Offline !");
                }


//                mBusyDialog = new BusyDialog(SignInAct.this, true, "");
//                mBusyDialog.show();
//                Intent signIn = new Intent(SignInAct.this,SelectPuposeAct.class);
//                startActivity(signIn);
//                ActivityCompat.finishAffinity(SignInAct.this);
            }
        });

//        txvForgotPass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });



    }



}
