package planet.it.limited.pepsigosmart.activities.bondhu_club;

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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.android.policy.TimeWindow;
import com.cloudinary.utils.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import planet.it.limited.pepsigosmart.R;
import planet.it.limited.pepsigosmart.activities.ReconfirmedPageAct;
import planet.it.limited.pepsigosmart.activities.VolumeAndImagesAct;
import planet.it.limited.pepsigosmart.database.LocalStoragePepsiDB;
import planet.it.limited.pepsigosmart.services.TrackGPS;
import planet.it.limited.pepsigosmart.utils.ClearAllSaveData;
import planet.it.limited.pepsigosmart.utils.ImageCompressor;

import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getBoleanValueSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getValueFromSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveBoleanValueSharedPreferences;
import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.saveToSharedPreferences;

public class VolumeAndImagesBAct extends AppCompatActivity {
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.edt_remarks)
    EditText edtRemarks;
    @BindView(R.id.btn_update)
    Button btnUpdate;

    @BindView(R.id.cb_csd)
    CheckBox cbCSD;
    @BindView(R.id.cb_water)
    CheckBox cbWater;
    @BindView(R.id.cb_lrb)
    CheckBox cbLRB;

    @BindView(R.id.edt_csd_v)
    EditText edtCSD;
    @BindView(R.id.edt_water_v)
    EditText edtWater;
    @BindView(R.id.edt_lrb_v)
    EditText edtLRB;

    boolean checkActiviyRunning ;
    boolean isBack ;


    @BindView(R.id.btn_take_out_pic_one)
    Button btnTakeOutPhotoOne;
    @BindView(R.id.txv_pic_one_name)
    TextView txvPicOneName;
    //location
    private TrackGPS gps;
    double longitude;
    double latitude;

    ClearAllSaveData clearAllSaveData;
    ImageCompressor imageCompressor;
    //Map config;
    public static boolean isInitCloudLib;
    LocalStoragePepsiDB localStoragePepsiDB;
    String outletCode = " ";

    private Timer timer = new Timer();
    private final long DELAY = 1000; // in ms
    private final int CAPTURE_PHOTO_ONE = 104;
    private final int CAPTURE_PHOTO_TWO = 105;
    public static final int REQUEST_PERM_WRITE_STORAGE = 102;

    public static final int REQUEST_PHOTO_ONE_IMAGE = 100;
    public static final int REQUEST_PHOTO_TWO_IMAGE = 101;

    String photoOneImageFilePath = "";
    Bitmap resizeImage ;
    String outPhotoOneFName=" ";
    String resizeOutPicOnePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_and_images_b);

        initViews();
    }

    public void initViews(){
        ButterKnife.bind(this);
        setClickListener();
        imageCompressor = new ImageCompressor();
        localStoragePepsiDB = new LocalStoragePepsiDB(VolumeAndImagesBAct.this);
        clearAllSaveData = new ClearAllSaveData(VolumeAndImagesBAct.this);
        isInitCloudLib = getBoleanValueSharedPreferences("init_cloud",VolumeAndImagesBAct.this);
        checkActiviyRunning = getBoleanValueSharedPreferences("is_active",VolumeAndImagesBAct.this);
        isBack = getBoleanValueSharedPreferences("is_back",VolumeAndImagesBAct.this);
        outletCode = getValueFromSharedPreferences("outlet_code_b",VolumeAndImagesBAct.this);

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


        gps = new TrackGPS(VolumeAndImagesBAct.this);
        if(gps.canGetLocation()){

            longitude = gps.getLongitude();
            latitude = gps .getLatitude();

            String convertlat = String.valueOf(latitude);
            String convertLon = String.valueOf(longitude);



            saveToSharedPreferences("latitude_b",convertlat,VolumeAndImagesBAct.this);
            saveToSharedPreferences("longitude_b",convertLon,VolumeAndImagesBAct.this);


        }
        else {
            gps.showSettingsAlert();
        }


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


                saveToSharedPreferences("remarks", remarks, VolumeAndImagesBAct.this);
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

                    saveToSharedPreferences("pepsico_csd", pepsiCSD, VolumeAndImagesBAct.this);
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

                    saveToSharedPreferences("pepsico_water", pepsiWater, VolumeAndImagesBAct.this);
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

                    saveToSharedPreferences("pepsico_lrb", pepsiLRB, VolumeAndImagesBAct.this);
                }

            }
        });



    }

    public void setClickListener(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(VolumeAndImagesBAct.this,CoolerAndSignageBAct.class);
                startActivity(signIn);
                //ActivityCompat.finishAffinity(CoolerAndSignageAct.this);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(VolumeAndImagesBAct.this,ReconfirmedBAct.class);
                startActivity(signIn);
                ActivityCompat.finishAffinity(VolumeAndImagesBAct.this);

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cbCSD.isChecked()){
                    //industryVolList.clear();
                    String industCSD = edtCSD.getText().toString();
                    saveToSharedPreferences("pepsico_csd",industCSD,VolumeAndImagesBAct.this);
                }

                if(cbWater.isChecked()){
                    // industryVolList.clear();
                    String industWater = edtWater.getText().toString();
                    saveToSharedPreferences("pepsico_water",industWater,VolumeAndImagesBAct.this);
                }
                if(cbLRB.isChecked()){
                    // industryVolList.clear();
                    String industLRB = edtLRB.getText().toString();
                    saveToSharedPreferences("pepsico_lrb",industLRB,VolumeAndImagesBAct.this);
                }

                if(txvPicOneName.getText().toString().length()>0 ){

                    if(photoOneImageFilePath.length()>0 ){

                            try {
                                    Intent signIn = new Intent(VolumeAndImagesBAct.this, ReconfirmedBAct.class);
                                    startActivity(signIn);

                            }catch (NumberFormatException e){
                                e.printStackTrace();
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


        btnTakeOutPhotoOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(VolumeAndImagesBAct.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }

                }
                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(VolumeAndImagesBAct.this,
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);

                } else{
                    takeOutletPhotoOneByCamera();
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

                    saveBoleanValueSharedPreferences("is_pepsico_csd",true,VolumeAndImagesBAct.this);

                }else {
                    edtCSD.setEnabled(false);
                    saveBoleanValueSharedPreferences("is_pepsico_csd",false,VolumeAndImagesBAct.this);
                }
            }
        });

        cbWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbWater.isChecked()){
                    edtWater.setEnabled(true);
                    edtWater.requestFocus();

                    saveBoleanValueSharedPreferences("is_pepsico_water",true,VolumeAndImagesBAct.this);
                }else {
                    edtWater.setEnabled(false);
                    saveBoleanValueSharedPreferences("is_pepsico_water",false,VolumeAndImagesBAct.this);
                }
            }
        });
        cbLRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbLRB.isChecked()){
                    edtLRB.setEnabled(true);
                    edtLRB.requestFocus();
                    saveBoleanValueSharedPreferences("is_pepsico_lrb",true,VolumeAndImagesBAct.this);
                }else {
                    edtLRB.setEnabled(false);
                    saveBoleanValueSharedPreferences("is_pepsico_lrb",false,VolumeAndImagesBAct.this);
                }
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent returnIntent) {
        super.onActivityResult(requestCode, resultCode, returnIntent);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case REQUEST_PHOTO_ONE_IMAGE:
                    //File to upload to cloudinary
                    imageCompressor.compressPicOneTempImage(photoOneImageFilePath,photoOneImageFilePath,VolumeAndImagesBAct.this);
                    String resizePicOnePath = imageCompressor.getPicOneFileName();
                    String outPhotoOneImageName = imageCompressor.getPicOneImageName();
                    saveToSharedPreferences("out_pic_one_local_path",resizePicOnePath,VolumeAndImagesBAct.this);
                    saveToSharedPreferences("out_photo_one_name",outPhotoOneImageName,VolumeAndImagesBAct.this);
                    txvPicOneName.setText(outPhotoOneImageName);
                    try{
                        clearAllSaveData.initCloudinaryLib();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    String requestId = MediaManager.get().upload(resizePicOnePath)
                            .constrain(TimeWindow.immediate()) .option("folder", "bondhu_club_enroll/")
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

                                                saveToSharedPreferences("out_photo_one_s_path",imageURL,VolumeAndImagesBAct.this);
                                                localStoragePepsiDB.open();
                                                localStoragePepsiDB.updatePicOneSPathB(outletCode,imageURL);

                                            }else {
                                                saveToSharedPreferences("out_photo_one_s_path","",VolumeAndImagesBAct.this);
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onError(String requestId, ErrorInfo error) {
                                    // your code here
                                    Log.d("onSuccess",requestId);
                                    saveToSharedPreferences("out_photo_one_s_path","",VolumeAndImagesBAct.this);
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
            saveToSharedPreferences("out_pic_one_local_path",resizeOutPicOnePath,VolumeAndImagesBAct.this);
            saveToSharedPreferences("out_photo_one_name",outPhotoOneFName,VolumeAndImagesBAct.this);

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
                    .constrain(TimeWindow.immediate()).option("folder", "bondhu_club_enroll/")
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
                                        saveToSharedPreferences("out_photo_one_s_path",imageURL,VolumeAndImagesBAct.this);
                                        localStoragePepsiDB.open();
                                        localStoragePepsiDB.updatePicOneSPathB(outletCode,imageURL);
                                    }else {
                                        saveToSharedPreferences("out_photo_one_s_path","",VolumeAndImagesBAct.this);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {
                            // your code here
                            Log.d("onSuccess",requestId);
                            saveToSharedPreferences("out_photo_one_s_path","",VolumeAndImagesBAct.this);
                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {
                            // your code here
                        }
                    }).dispatch();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        photoOneImageFilePath =  getValueFromSharedPreferences("out_pic_one_local_path",VolumeAndImagesBAct.this);

        String remarks = getValueFromSharedPreferences("remarks",VolumeAndImagesBAct.this);
        if(remarks!=null){
            edtRemarks.setText(remarks);
        }

        String photoNameOne = getValueFromSharedPreferences("out_photo_one_name",VolumeAndImagesBAct.this);
        if(photoNameOne!=null){
            txvPicOneName.setText(photoNameOne);
        }



        if(cbCSD!=null){
            boolean isChkCSD = getBoleanValueSharedPreferences("is_pepsico_csd",VolumeAndImagesBAct.this);

            cbCSD.setChecked(isChkCSD);
            if(isChkCSD){
                edtCSD.setEnabled(true);
                String csd = getValueFromSharedPreferences("pepsico_csd",VolumeAndImagesBAct.this);
                if(csd!=null){
                    edtCSD.setText(csd);
                }


            }

        }
        if(cbWater!=null){
            boolean isChkWater = getBoleanValueSharedPreferences("is_pepsico_water",VolumeAndImagesBAct.this);
            cbWater.setChecked(isChkWater);
            if(isChkWater){
                edtWater.setEnabled(true);
                String water = getValueFromSharedPreferences("pepsico_water",VolumeAndImagesBAct.this);
                if(water!=null){
                    edtWater.setText(water);
                }


            }

        }
        if(cbLRB!=null){
            boolean isChkLRB = getBoleanValueSharedPreferences("is_pepsico_lrb",VolumeAndImagesBAct.this);
            cbLRB.setChecked(isChkLRB);
            if(isChkLRB){
                edtLRB.setEnabled(true);

                String lrb = getValueFromSharedPreferences("pepsico_lrb",VolumeAndImagesBAct.this);
                if(lrb!=null){
                    edtLRB.setText(lrb);
                }


            }

        }


    }


}
