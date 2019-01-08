package planet.it.limited.pepsigosmart.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.android.policy.TimeWindow;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.utils.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import planet.it.limited.pepsigosmart.R;
import planet.it.limited.pepsigosmart.database.LocalStoragePepsiDB;
import planet.it.limited.pepsigosmart.services.TrackGPS;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;
import planet.it.limited.pepsigosmart.utils.ImageCompressor;

import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getBoleanValueSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getValueFromSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveBoleanValueSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveToSharedPreferences;

public class VolumeAndImagesAct extends DrawerCompatActivity {

    private final int CAPTURE_PHOTO_ONE = 104;
    private final int CAPTURE_PHOTO_TWO = 105;
    public static final int REQUEST_PERM_WRITE_STORAGE = 102;

    public static final int REQUEST_PHOTO_ONE_IMAGE = 100;
    public static final int REQUEST_PHOTO_TWO_IMAGE = 101;
    @BindView(R.id.btn_back)
    Button btnBack;

    @BindView(R.id.btn_next)
    Button btnNext;

    @BindView(R.id.btn_take_out_pic_one)
    Button btnTakeOutPhotoOne;
    @BindView(R.id.txv_pic_one_name)
    TextView txvPicOneName;
//    @BindView(R.id.txv_pic_two_name)
//    TextView txvPicTwoName;
//    @BindView(R.id.edt_enrolled_order)
//    EditText edtEnrolledOrder;
    @BindView(R.id.txv_achieved_vol_vlue)
    TextView txvAchievedVol;
    @BindView(R.id.edt_volume_target_value)
    EditText edtVolumeTar;
    @BindView(R.id.txv_remarks_value)
    EditText edtRemarks;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.txv_user_name)
    TextView txvUserName;
    @BindView(R.id.txv_mobile)
    TextView txvMobile;

    String photoOneImageFilePath = "";
    String photoTwoImageFilePath = "";
    ImageCompressor imageCompressor;
    //Map config;
    public static boolean isInitCloudLib;
    String enrolledOrder = " ";
    String achievedVol = " ";
    String volumeTarget = " ";
    String remarks = " ";
    Bitmap resizeImage ;
    String outPhotoOneFName=" ";
    String outPhotoTwoFName=" ";
    String resizeOutPicOnePath = "";
    String resizeOutPicTwoPath = "";
    ClearAllSaveData clearAllSaveData;
    String userName = " ";
    //location
    private TrackGPS gps;
    double longitude;
    double latitude;

    boolean checkActiviyRunning ;
    boolean isBack ;

    LocalStoragePepsiDB localStoragePepsiDB;
    String outletCode = " ";

    private Timer timer = new Timer();
    private final long DELAY = 1000; // in ms

    private DrawerLayout drawerLayout;

    String userMobile = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_and_images);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        initViews();
        removeToolbar();
        setupDrawer();
    }

    public void initViews(){
        ButterKnife.bind(this);
        setClickListener();
        localStoragePepsiDB = new LocalStoragePepsiDB(VolumeAndImagesAct.this);

        gps = new TrackGPS(VolumeAndImagesAct.this);
        imageCompressor = new ImageCompressor();
        clearAllSaveData = new ClearAllSaveData(VolumeAndImagesAct.this);
        isInitCloudLib = getBoleanValueSharedPreferences("init_cloud",VolumeAndImagesAct.this);
        checkActiviyRunning = getBoleanValueSharedPreferences("is_active",VolumeAndImagesAct.this);
        isBack = getBoleanValueSharedPreferences("is_back",VolumeAndImagesAct.this);
        outletCode = getValueFromSharedPreferences("outlet_code",VolumeAndImagesAct.this);

        drawerLayout = (DrawerLayout) findViewById(R.id.app_drawer);
        ImageView menuIcon = (ImageView) findViewById(R.id.drawer_menuIcon);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!drawerLayout.isDrawerOpen(Gravity.LEFT))
                    drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        userName = getValueFromSharedPreferences("name",VolumeAndImagesAct.this);
        userMobile = getValueFromSharedPreferences("user_mobile",VolumeAndImagesAct.this);
        if(userName!=null){
            txvUserName.setText(userName);
        }
        if(userMobile!=null){
            txvMobile.setText(userMobile);
        }


        if(checkActiviyRunning){
            btnUpdate.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.GONE);
            btnBack.setVisibility(View.GONE);
        }else {
            btnNext.setVisibility(View.VISIBLE);
        }

        if(isBack){
            btnBack.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
        }

        if(gps.canGetLocation()){

            longitude = gps.getLongitude();
            latitude = gps .getLatitude();

            String convertlat = String.valueOf(latitude);
            String convertLon = String.valueOf(longitude);



            saveToSharedPreferences("latitude",convertlat,VolumeAndImagesAct.this);
            saveToSharedPreferences("longitude",convertLon,VolumeAndImagesAct.this);


        }
        else {
            gps.showSettingsAlert();
        }



        edtVolumeTar.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable editable) {

                   volumeTarget = editable.toString();
                 try{
                     if(volumeTarget.length()>0){
                      //   if(Integer.parseInt(volumeTarget.trim())>=120){
                             saveToSharedPreferences("volume_target", volumeTarget, VolumeAndImagesAct.this);
                    //     }

                         timer = new Timer();
                         timer.schedule(new TimerTask() {
                             @Override
                             public void run() {

                                 if (Integer.parseInt(volumeTarget.trim())<120) {

                                     runOnUiThread (new Thread(new Runnable() {
                                         public void run() {
                                             clearAllSaveData.openDialog("Need Minimum 120 target value");
                                         }
                                     }));


                                 }

                             }

                         }, DELAY);

                     }

                 }catch (NumberFormatException e){
                     e.printStackTrace();
                 }


            }

        });
        edtRemarks.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String remarks = editable.toString();


                saveToSharedPreferences("remarks", remarks, VolumeAndImagesAct.this);
            }

        });
    }



    private void setClickListener(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(VolumeAndImagesAct.this,CoolerAndSignageAct.class);
                startActivity(signIn);
              //  ActivityCompat.finishAffinity(VolumeAndImagesAct.this);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(txvPicOneName.getText().toString().length()>0 ){

                    if(photoOneImageFilePath.length()>0 ){
                        if(edtVolumeTar.getText().toString().length()>0){
                            try {
                                if (Integer.parseInt(volumeTarget.trim()) >= 120) {
                                    Intent signIn = new Intent(VolumeAndImagesAct.this, ReconfirmedPageAct.class);
                                    startActivity(signIn);
                                }else {
                                    clearAllSaveData.openDialog("Need Minimum 120 target value");
                                }
                            }catch (NumberFormatException e){
                                e.printStackTrace();
                            }
                        }else {
                            clearAllSaveData.openDialog("Enrolled Order Or Volume Target Missing");
                        }

                    }else {
                        clearAllSaveData.openDialog("Photo take Mandatory");
                    }

                    // ActivityCompat.finishAffinity(ImagesAndLast.this);
                }else {

                    clearAllSaveData.openDialog("Photo take Mandatory");
                }



            }
        });


//        btnTakePhotoTwo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(VolumeAndImagesAct.this, new String[]{Manifest.permission.CAMERA}, 1);
//                    }
//
//                }
//                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//
//                    ActivityCompat.requestPermissions(VolumeAndImagesAct.this,
//                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);
//
//                } else{
//                    takeOutletPhotoTwoByCamera();
//                }
//
//            }
//        });


        btnTakeOutPhotoOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(VolumeAndImagesAct.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }

                }
                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(VolumeAndImagesAct.this,
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);

                } else{
                    takeOutletPhotoOneByCamera();
                }

            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(VolumeAndImagesAct.this,ReconfirmedPageAct.class);
                startActivity(signIn);
            //    ActivityCompat.finishAffinity(VolumeAndImagesAct.this);
            }
        });


    }


    public void takeOutletPhotoOneByCamera (){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (pictureIntent.resolveActivity(getPackageManager()) != null) {

                File photoFile = null;
                try {
                    photoFile = createPhotoOneImageFile();
                }
                catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                Uri photoUri = FileProvider.getUriForFile(this, getPackageName() +".provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(pictureIntent, REQUEST_PHOTO_ONE_IMAGE);
            }
        }else {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAPTURE_PHOTO_ONE);
        }

    }

    private File createPhotoOneImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        photoOneImageFilePath = image.getAbsolutePath();
        return image;
    }

    public void takeOutletPhotoTwoByCamera (){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (pictureIntent.resolveActivity(getPackageManager()) != null) {

                File photoFile = null;
                try {
                    photoFile = createPhotoTwoImageFile();
                }
                catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                Uri photoUri = FileProvider.getUriForFile(this, getPackageName() +".provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(pictureIntent, REQUEST_PHOTO_TWO_IMAGE);
            }
        }else {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAPTURE_PHOTO_TWO);
        }

    }


    private File createPhotoTwoImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        photoTwoImageFilePath = image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent returnIntent) {
        super.onActivityResult(requestCode, resultCode, returnIntent);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case REQUEST_PHOTO_ONE_IMAGE:
                    //File to upload to cloudinary
                    imageCompressor.compressPicOneTempImage(photoOneImageFilePath,photoOneImageFilePath,VolumeAndImagesAct.this);
                    String resizePicOnePath = imageCompressor.getPicOneFileName();
                    String outPhotoOneImageName = imageCompressor.getPicOneImageName();
                    saveToSharedPreferences("out_pic_one_local_path",resizePicOnePath,VolumeAndImagesAct.this);
                    saveToSharedPreferences("out_photo_one_name",outPhotoOneImageName,VolumeAndImagesAct.this);
                    txvPicOneName.setText(outPhotoOneImageName);
                    try{
                        clearAllSaveData.initCloudinaryLib();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    String requestId = MediaManager.get().upload(resizePicOnePath)
                            .constrain(TimeWindow.immediate()) .option("folder", "summer_hangama_enroll/")
                            .callback(new UploadCallback() {
                                @Override
                                public void onStart(String requestId) {
                                    Log.d("onStart",requestId);
                                    // your code here
                                }

                                @Override
                                public void onProgress(String requestId, long bytes, long totalBytes) {
                                    // example code starts here

                                }

                                @Override
                                public void onSuccess(String requestId, Map resultData) {
                                    // your code here
                                    Log.d("onSuccess",requestId);

                                    if (resultData != null && resultData.size() > 0) {
                                        if (resultData.containsKey("url")) {
                                            String imageURL = (String) resultData.get("url");
                                            String cloudinaryID = (String) resultData.get("public_id");
                                            int width = (Integer) resultData.get("width");
                                            int height = (Integer) resultData.get("height");
                                            if (imageURL != null && !StringUtils.isEmpty(imageURL)) {
                                                //logic here

                                                saveToSharedPreferences("out_photo_one_s_path",imageURL,VolumeAndImagesAct.this);
                                                localStoragePepsiDB.open();
                                                localStoragePepsiDB.updatePicOneSPath(outletCode,imageURL);

                                            }else {
                                                saveToSharedPreferences("out_photo_one_s_path","",VolumeAndImagesAct.this);
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onError(String requestId, ErrorInfo error) {
                                    // your code here
                                    Log.d("onSuccess",requestId);
                                    saveToSharedPreferences("out_photo_one_s_path","",VolumeAndImagesAct.this);
                                }

                                @Override
                                public void onReschedule(String requestId, ErrorInfo error) {
                                    // your code here
                                }
                            }).dispatch();


                    break;


                case CAPTURE_PHOTO_ONE:
                    Bitmap capturedCoolerBitmap = (Bitmap) returnIntent.getExtras().get("data");

                    int capturedCImageWidth = 1200;
                    int capturedCImageHeight = 800;
                    resizeImage =   resizeBitmap(capturedCoolerBitmap,capturedCImageWidth,capturedCImageHeight);
                    saveOutPhotoOne(resizeImage);

                    break;



                default:
                    break;

            }

        }

    }

    private void saveOutPhotoOne(Bitmap finalBitmap) {
        Bitmap  bmp = Bitmap.createScaledBitmap(finalBitmap, 800, 800 * finalBitmap.getHeight() / finalBitmap.getWidth(), false);
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/out_pic_one_new");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        outPhotoOneFName = "Image-"+ n +".jpg";
        File file = new File (myDir, outPhotoOneFName);
        if (file.exists ()) file.delete ();
        //To use Image Compress
        resizeOutPicOnePath = file.getAbsolutePath();

        try {
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);

            out.flush();
            out.close();
            saveToSharedPreferences("out_pic_one_local_path",resizeOutPicOnePath,VolumeAndImagesAct.this);
            saveToSharedPreferences("out_photo_one_name",outPhotoOneFName,VolumeAndImagesAct.this);

            txvPicOneName.setText(outPhotoOneFName);
            //Toast.makeText(ImagesAndLast.this,"Your Photo save and Resize Success",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(ImagesAndLast.this,"Exaception Throw",Toast.LENGTH_SHORT).show();
        }

        String imageUriAfterResize = resizeOutPicOnePath;
        if(imageUriAfterResize!=null && !imageUriAfterResize.isEmpty()){

            try{
                clearAllSaveData.initCloudinaryLib();
            }catch (Exception e){
                e.printStackTrace();
            }

            String requestIdTwo = MediaManager.get().upload(imageUriAfterResize)
                    .constrain(TimeWindow.immediate()).option("folder", "summer_hangama_enroll/")
                    .callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {
                            Log.d("onStart",requestId);
                            // your code here
                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {
                            // example code starts here

                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            // your code here
                            Log.d("onSuccess",requestId);

                            if (resultData != null && resultData.size() > 0) {
                                if (resultData.containsKey("url")) {
                                    String imageURL = (String) resultData.get("url");
                                    String cloudinaryID = (String) resultData.get("public_id");
                                    int width = (Integer) resultData.get("width");
                                    int height = (Integer) resultData.get("height");
                                    if (imageURL != null && !StringUtils.isEmpty(imageURL)) {
                                        //logic here
                                        saveToSharedPreferences("out_photo_one_s_path",imageURL,VolumeAndImagesAct.this);
                                        localStoragePepsiDB.open();
                                        localStoragePepsiDB.updatePicOneSPath(outletCode,imageURL);
                                    }else {
                                        saveToSharedPreferences("out_photo_one_s_path","",VolumeAndImagesAct.this);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {
                            // your code here
                            Log.d("onSuccess",requestId);
                            saveToSharedPreferences("out_photo_one_s_path","",VolumeAndImagesAct.this);
                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {
                            // your code here
                        }
                    }).dispatch();
        }
    }

    private static Bitmap resizeBitmap(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        photoOneImageFilePath =  getValueFromSharedPreferences("out_pic_one_local_path",VolumeAndImagesAct.this);
        photoTwoImageFilePath = getValueFromSharedPreferences("out_pic_two_local_path",VolumeAndImagesAct.this);


        String achievedVol = getValueFromSharedPreferences("achieved_vol",VolumeAndImagesAct.this);
        if(achievedVol!=null){
            txvAchievedVol.setText(achievedVol);
        }
        String volumeTarget = getValueFromSharedPreferences("volume_target",VolumeAndImagesAct.this);
        if(volumeTarget!=null){
            edtVolumeTar.setText(volumeTarget);
        }

        String remarks = getValueFromSharedPreferences("remarks",VolumeAndImagesAct.this);
        if(remarks!=null){
            edtRemarks.setText(remarks);
        }

        String photoNameOne = getValueFromSharedPreferences("out_photo_one_name",VolumeAndImagesAct.this);
        if(photoNameOne!=null){
            txvPicOneName.setText(photoNameOne);
        }

//        String photoNameTwo = getValueFromSharedPreferences("out_photo_two_name",VolumeAndImagesAct.this);
//        if(photoNameTwo!=null){
//            txvPicTwoName.setText(photoNameTwo);
//        }
    }
}
