package planet.it.limited.pepsigosmart.activities;

/**
 * Created by Tarikul on 28-Oct-18.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import planet.it.limited.pepsigosmart.R;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;
import planet.it.limited.pepsigosmart.utils.CsvFileExport;


public class DrawerCompatActivity extends AppCompatActivity {
    private static final String TAG = "DrawerCompatActivity";

    private DrawerLayout drawerLayout;
    String userName = " ";

    TextView txvUserName;

    ClearAllSaveData clearAllSaveData;
    CsvFileExport csvFileExport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.drawer_default);
        drawerLayout = (DrawerLayout) findViewById(R.id.app_drawer);
       // txvUserName = (TextView)findViewById(R.id.drawer_header_username);
        setupDrawer();
       // userName = getValueFromSharedPreferences("name",DrawerCompatActivity.this);

        clearAllSaveData = new ClearAllSaveData(DrawerCompatActivity.this);
        csvFileExport = new CsvFileExport(DrawerCompatActivity.this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // We inflate the sent layout
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.app_container);
        frameLayout.addView(inflater.inflate(R.layout.layout_container, null, false));

        FrameLayout appFrame = (FrameLayout) findViewById(R.id.app_content);
        appFrame.addView(inflater.inflate(layoutResID, null, false));

        toolbarSetted();
    }

    @Override
    protected void onResume() {
        // Close drawer on activity result
        if (drawerLayout.isDrawerOpen(Gravity.LEFT))
            drawerLayout.closeDrawer(Gravity.LEFT, false);

        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // We close the drawer if the user press back
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                drawerLayout.closeDrawer(Gravity.LEFT);
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }


    public void setupDrawer() {

//        if(userName!=null && userName.length()>0){
//            txvUserName.setText(userName);
//        }
        LinearLayout allInputData = (LinearLayout) findViewById(R.id.drawer_all_input_data);
        allInputData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ShowAllIputDataAct.class);
                startActivity(i);
            }
        });
        LinearLayout offlineData = (LinearLayout) findViewById(R.id.drawer_offline_data);
        offlineData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), OfflineDataAct.class);
                startActivity(i);
            }
        });

        LinearLayout allExistData = (LinearLayout) findViewById(R.id.drawer_exist_data);
        allExistData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AllExistingDataShowAct.class);
                startActivity(i);
            }
        });
        LinearLayout exportData = (LinearLayout) findViewById(R.id.drawer_export_data);
        exportData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openExportDataDialog();

            }
        });


        LinearLayout logout = (LinearLayout) findViewById(R.id.lin_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), SignInAct.class);
                startActivity(i);
                ActivityCompat.finishAffinity(DrawerCompatActivity.this);
            }
        });
    }

    private void toolbarSetted() {
        // We bind the click on the toolbar title
//        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
//        toolbarTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!drawerLayout.isDrawerOpen(Gravity.LEFT))
//                    drawerLayout.openDrawer(Gravity.LEFT);
//            }
//        });

//        // We bind the click on the menu icon
//        ImageView menuIcon = (ImageView) findViewById(R.id.toolbar_menuIcon);
//        menuIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!drawerLayout.isDrawerOpen(Gravity.LEFT))
//                    drawerLayout.openDrawer(Gravity.LEFT);
//            }
//        });
    }

    /**
     * Allow you to remove the toolbar
     */
    public void removeToolbar() {
        RelativeLayout bar = (RelativeLayout) findViewById(R.id.toolbar_container);
        bar.setVisibility(View.GONE);
    }

    /**
     * Change the toolbar title
     * @param title
     */
    public void setToolbarTitle(String title) {
//        TextView barTitle = (TextView) findViewById(R.id.toolbar_title);
//        barTitle.setText(title);
    }
    /**
     * Change the toolbar title
     * @param title
     */
    public void setToolbarTitle(@StringRes int title) {
//        TextView barTitle = (TextView) findViewById(R.id.toolbar_title);
//        barTitle.setText(title);
    }

    /**
     * Give you the possibility to disable the shadow under the toolbar
     * @param state
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setToolbarShadowVisibility(boolean state) {
        RelativeLayout bar = (RelativeLayout) findViewById(R.id.toolbar_container);
        if (state)
            bar.setElevation(4);
        else
            bar.setElevation(0);
    }

    /**
     * Display or not the back button in the toolbar
     * @param state
     */
    public void setToolbarBackButtonVisibility(boolean state) {
//        ImageView backButton = (ImageView) findViewById(R.id.toolbar_back);
//        ImageView menuIcon = (ImageView) findViewById(R.id.toolbar_menuIcon);
//        if (state) {
//            backButton.setVisibility(View.VISIBLE);
//            backButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    finish();
//                }
//            });
//            menuIcon.setVisibility(View.GONE);
//        }
//        else {
//            backButton.setVisibility(View.GONE);
//            menuIcon.setVisibility(View.VISIBLE);
//        }
    }

    /**
     * Display the back button in the toolbar
     * @param listener Callback when the button is pressed
     */
    public void setToolbarBackButtonVisibility(View.OnClickListener listener) {
//        ImageView backButton = (ImageView) findViewById(R.id.toolbar_back);
//        backButton.setVisibility(View.VISIBLE);
//        backButton.setOnClickListener(listener);
//        hideToolbarMenuIcon();
    }

    public void hideToolbarMenuIcon() {
//        ImageView menuIcon = (ImageView) findViewById(R.id.toolbar_menuIcon);
//        menuIcon.setVisibility(View.GONE);
    }


    public void openExportDataDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(DrawerCompatActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.export_data_dialog,null);

        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(false);

        // Set the custom layout as alert dialog view
        builder.setView(dialogView);

        // Get the custom alert dialog view widgets reference
        Button btn_positive = (Button) dialogView.findViewById(R.id.dialog_positive_btn);
        Button btn_negative = (Button) dialogView.findViewById(R.id.dialog_negative_btn);

        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        // Set positive/yes button click listener
        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the alert dialog
                dialog.dismiss();
                csvFileExport.csvFileExport();

            }
        });

        // Set negative/no button click listener
        btn_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss/cancel the alert dialog
                //dialog.cancel();
                dialog.dismiss();

            }
        });

        // Display the custom alert dialog on interface
        dialog.show();
        //   recreate();
    }

}
