package planet.it.limited.pepsigosmart.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;

import planet.it.limited.pepsigosmart.model.BondhuClubM;
import planet.it.limited.pepsigosmart.model.SurveyModel;


/**
 * Created by User on 2/3/2018.
 */

public class LocalStoragePepsiDB {
    //to local storage table
    public static final String KEY_ROWID = "id";
    public static final String KEY_OUTLET_CODE = "outlet_code";

    public static final String KEY_COOLER_STATUS = "cooler_status";
    public static final String KEY_COOLER_BRANDING = "cooler_branding";
    public static final String KEY_SIGNAGE_STATUS = "signage_status";
    public static final String KEY_SIGNAGE_BRANDING = "signage_branding";
    public static final String KEY_ENROLLED_ORDER = "enrolled_order";
    public static final String KEY_VOLUME_TARGET = "volume_target";
    public static final String KEY_REMARKS = "remarks";
    public static final String KEY_ENTRY_DATE = "entry_date";

    public static final String KEY_OUT_PIC_ONE_LOCAL_PATH = "out_pic_one_local_path";
    public static final String KEY_OUT_PIC_ONE_SERVER_PATH = "out_pic_one_server_path";


    public static final String KEY_START_TIME = "start_time";
    public static final String KEY_END_TIME = "end_time";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_SYNC_STATUS = "sync_status";

    public static final String KEY_PHOTO_URI = "photo_uri";

//    public static final String KEY_LATITUDE = "latitude";
//    public static final String KEY_LONGITUDE = "longitude";

    // db version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pepsi_smart";
    private static final String DATABASE_TABLE_SERVEY_STORAGE = "table_servey_storage";
    //bondhu club table
    private static final String DATABASE_TABLE_BONDHU_CLUB = "table_bondhu_club";
    public static final String KEY_ROWID_B = "id_b";
    public static final String KEY_OUTLET_CODE_B = "outlet_code_b";
    public static final String KEY_COOL_CATEGORY = "cool_category";
    public static final String KEY_INDUST_CSD = "indust_csd";
    public static final String KEY_INDUST_WATER = "indust_water";
    public static final String KEY_INDUST_LRB = "indust_lrb";

    public static final String KEY_PEPSI_CSD = "pepsi_csd";
    public static final String KEY_PEPSI_WATER = "pepsi_water";
    public static final String KEY_PEPSI_LRB = "pepsi_lrb";
    public static final String KEY_REMARKS_B = "remarks_b";

    public static final String KEY_OUT_PIC_ONE_LOCAL_PATH_B = "out_pic_one_local_path_b";
    public static final String KEY_OUT_PIC_ONE_SERVER_PATH_B = "out_pic_one_server_path_b";
    public static final String KEY_ENTRY_DATE_B = "entry_date_b";
    public static final String KEY_LATITUDE_B = "latitude_b";
    public static final String KEY_LONGITUDE_B = "longitude_b";

    public static final String KEY_START_TIME_B = "start_time_b";
    public static final String KEY_END_TIME_B = "end_time_b";
    public static final String KEY_SYNC_STATUS_B = "sync_status_b";

  //  private static final String DATABASE_TABLE_BASIC_INFORMATION = "table_basic_information";
    private static final String DATABASE_TABLE_PICURI = "table_pic_uri";

    private LocalStoragePepsiDB.DBHelper dbhelper;
    private final Context context;
    private SQLiteDatabase database;

    private TableLayout mtableLayout,mCloumnHeader;

    String RESPONSE_UPDATE = "update";

    private static class DBHelper extends SQLiteOpenHelper {

        @SuppressLint("NewApi")
        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            // create table to store msgs
            db.execSQL("CREATE TABLE " + DATABASE_TABLE_SERVEY_STORAGE + " ("
                    + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_OUTLET_CODE + " TEXT, "
                    + KEY_COOLER_STATUS + " TEXT, "
                    + KEY_COOLER_BRANDING + " TEXT, "
                    + KEY_SIGNAGE_STATUS + " TEXT, "
                    + KEY_SIGNAGE_BRANDING + " TEXT, "

                    + KEY_VOLUME_TARGET + " TEXT, "
                    + KEY_REMARKS + " TEXT, "
                    + KEY_ENTRY_DATE + " TEXT, "
                    + KEY_OUT_PIC_ONE_LOCAL_PATH + " TEXT, "
                    + KEY_OUT_PIC_ONE_SERVER_PATH + " TEXT, "
                    + KEY_START_TIME + " TEXT, "
                    + KEY_END_TIME + " TEXT, "
                    + KEY_LATITUDE + " TEXT, "
                    + KEY_LONGITUDE + " TEXT, "
                    + KEY_USER_NAME + " TEXT, "
                    + KEY_SYNC_STATUS+ " TEXT );");

            //bondhu club table
            db.execSQL("CREATE TABLE " + DATABASE_TABLE_BONDHU_CLUB + " ("
                    + KEY_ROWID_B + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_OUTLET_CODE_B + " TEXT, "
                    + KEY_COOL_CATEGORY + " TEXT, "
                    + KEY_INDUST_CSD + " TEXT, "
                    + KEY_INDUST_WATER + " TEXT, "
                    + KEY_INDUST_LRB + " TEXT, "

                    + KEY_PEPSI_CSD + " TEXT, "
                    + KEY_PEPSI_WATER + " TEXT, "
                    + KEY_PEPSI_LRB + " TEXT, "

                    + KEY_REMARKS_B + " TEXT, "
                    + KEY_ENTRY_DATE_B + " TEXT, "
                    + KEY_OUT_PIC_ONE_LOCAL_PATH_B + " TEXT, "
                    + KEY_OUT_PIC_ONE_SERVER_PATH_B + " TEXT, "
                    + KEY_START_TIME_B + " TEXT, "
                    + KEY_END_TIME_B + " TEXT, "
                    + KEY_LATITUDE_B + " TEXT, "
                    + KEY_LONGITUDE_B + " TEXT, "
                    + KEY_USER_NAME + " TEXT, "
                    + KEY_SYNC_STATUS_B + " TEXT );");


            //table photo uri

            db.execSQL("CREATE TABLE " + DATABASE_TABLE_PICURI + " ("
                    + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    //+ KEY_OUTLET_CODE + " TEXT UNIQUE, "
                    + KEY_START_TIME + " TEXT UNIQUE, "
                    + KEY_USER_NAME + " TEXT, "
                    + KEY_PHOTO_URI + " TEXT );");



        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_SERVEY_STORAGE);
            //db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_BASIC_INFORMATION);

            onCreate(db);
        }

    }
    // constructor
    public LocalStoragePepsiDB(Context c) {
        context = c;


    }
    public LocalStoragePepsiDB(Context c, TableLayout tableLayout, TableLayout cloumnHeader) {
        context = c;
         this.mtableLayout=tableLayout;
         this.mCloumnHeader = cloumnHeader;

    }

    // open db
    public LocalStoragePepsiDB open() {
        dbhelper = new  DBHelper(context);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    // close db
    public void close() {
        dbhelper.close();
    }

    // insert login information
    public long saveServeyStorageEntry(String outletCode, String coolerStatus, String coolerBrand, String signageStatus
                                        , String signageBrand,  String volumeTarget,
                                       String remarks, String entryDate, String outPicOneLocalP,
                                       String outPicOneServerP,
                                       String startTime, String endTime,String latitude,String longitude,String userName, String syncStatus){
        ContentValues cv = new ContentValues();
        cv.put(KEY_OUTLET_CODE, outletCode);

        cv.put(KEY_COOLER_STATUS,  coolerStatus);
        cv.put(KEY_COOLER_BRANDING,  coolerBrand);
        cv.put(KEY_SIGNAGE_STATUS,  signageStatus);
        cv.put(KEY_SIGNAGE_BRANDING,  signageBrand);

        cv.put(KEY_VOLUME_TARGET,  volumeTarget);
        cv.put(KEY_REMARKS,  remarks);
        cv.put(KEY_ENTRY_DATE,  entryDate);
        cv.put(KEY_OUT_PIC_ONE_LOCAL_PATH,  outPicOneLocalP);
        cv.put(KEY_OUT_PIC_ONE_SERVER_PATH,  outPicOneServerP);
//        cv.put(KEY_OUT_PIC_TWO_LOCAL_PATH,  outPicTwoLocalP);
//        cv.put(KEY_OUT_PIC_TWO_SERVER_PATH,  outPicTwoServerP);
        cv.put(KEY_START_TIME,  startTime);
        cv.put(KEY_END_TIME,  endTime);
        cv.put(KEY_LATITUDE,  latitude);
        cv.put(KEY_LONGITUDE,  longitude);
        cv.put(KEY_USER_NAME,  userName);
        cv.put(KEY_SYNC_STATUS,  syncStatus);

        long dbInsert = database.insert(DATABASE_TABLE_SERVEY_STORAGE, null, cv);
        //saveToSharedPreferences("rowid", String.valueOf(dbInsert),context);

        if(dbInsert != -1) {
            Toast.makeText(context, "New row added in local storage, row id: " + dbInsert, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
        }

        return dbInsert;

    }

    public long saveBondhuClubEntry(String outletCode, String coolCategory, String indusCSD, String indusWater
            , String indusLRB,  String pepsiCSD,String pepsiWater
            , String pepsiLRB, String remarks, String entryDate, String outPicOneLocalP,
                                       String outPicOneServerP,
                                       String startTime, String endTime,String latitude,String longitude,String userName, String syncStatus){
        ContentValues cv = new ContentValues();
        cv.put(KEY_OUTLET_CODE_B, outletCode);

        cv.put(KEY_COOL_CATEGORY,  coolCategory);
        cv.put(KEY_INDUST_CSD,  indusCSD);
        cv.put(KEY_INDUST_WATER,  indusWater);
        cv.put(KEY_INDUST_LRB,  indusLRB);

        cv.put(KEY_PEPSI_CSD,  pepsiCSD);
        cv.put(KEY_PEPSI_WATER,  pepsiWater);
        cv.put(KEY_PEPSI_LRB,  pepsiLRB);

        cv.put(KEY_REMARKS_B,  remarks);
        cv.put(KEY_ENTRY_DATE_B,  entryDate);
        cv.put(KEY_OUT_PIC_ONE_LOCAL_PATH_B,  outPicOneLocalP);
        cv.put(KEY_OUT_PIC_ONE_SERVER_PATH_B,  outPicOneServerP);
//        cv.put(KEY_OUT_PIC_TWO_LOCAL_PATH,  outPicTwoLocalP);
//        cv.put(KEY_OUT_PIC_TWO_SERVER_PATH,  outPicTwoServerP);
        cv.put(KEY_START_TIME_B,  startTime);
        cv.put(KEY_END_TIME_B,  endTime);
        cv.put(KEY_LATITUDE_B,  latitude);
        cv.put(KEY_LONGITUDE_B,  longitude);
        cv.put(KEY_USER_NAME,  userName);
        cv.put(KEY_SYNC_STATUS_B,  syncStatus);

        long dbInsert = database.insert(DATABASE_TABLE_BONDHU_CLUB, null, cv);
        //saveToSharedPreferences("rowid", String.valueOf(dbInsert),context);

        if(dbInsert != -1) {
            Toast.makeText(context, "New row added in local storage, row id: " + dbInsert, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
        }

        return dbInsert;

    }
    public long savePhotoUri(String startTime,String userName, String photoUri){
        ContentValues cv = new ContentValues();
        cv.put(KEY_START_TIME, startTime);
        cv.put(KEY_USER_NAME, userName);
        cv.put(KEY_PHOTO_URI,  photoUri);

        long dbInsert = database.insert(DATABASE_TABLE_PICURI, null, cv);
        //saveToSharedPreferences("rowid", String.valueOf(dbInsert),context);

//        if(dbInsert != -1) {
//            Toast.makeText(context, "New row added in local storage, row id: " + dbInsert, Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
//        }

        return dbInsert;

    }

    public void clearPhotoTable(){
        database.execSQL("delete from "+ DATABASE_TABLE_PICURI );
    }


    public String getPhotoUri(String time,String userName){
        String photoUri = "";
        String select_query = "SELECT  photo_uri  FROM " + DATABASE_TABLE_PICURI +
                " WHERE " + KEY_START_TIME + " = '" + time + "'" + " And " + KEY_USER_NAME + " = '" + userName + "'" ;
        Cursor cursor = database.rawQuery(select_query,null);
        int iPhotoUri = cursor.getColumnIndex(KEY_PHOTO_URI);

        for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            photoUri = cursor.getString(iPhotoUri);

        }

        return  photoUri;
    }

//    public void updateSyncStatus(long ROWID,String syncResult){
//        ContentValues cv = new ContentValues();
//        cv.put(KEY_SYNC_STATUS, syncResult);
//        database.update(DATABASE_TABLE_SERVEY_STORAGE, cv, "id=" + ROWID, null);
//        //saveToSharedPreferences("rowid","",context);
//
//    }

public void updateSyncStatus(String outletCode, String syncResult){
    ContentValues cv = new ContentValues();
    cv.put(KEY_SYNC_STATUS, syncResult);
   long updateDb = database.update(DATABASE_TABLE_SERVEY_STORAGE, cv, " outlet_code = " + outletCode , null);

    Log.d(RESPONSE_UPDATE, String.valueOf(updateDb));

}
    public void updateSyncStatusB(String outletCode, String syncResult){
        ContentValues cv = new ContentValues();
        cv.put(KEY_SYNC_STATUS_B, syncResult);
        long updateDb = database.update(DATABASE_TABLE_BONDHU_CLUB, cv, " outlet_code_b = " + outletCode , null);

        Log.d(RESPONSE_UPDATE, String.valueOf(updateDb));

    }

    public void updatePicOneSPath(String outletCode, String picOneSPath){
        ContentValues cv = new ContentValues();
        cv.put(KEY_OUT_PIC_ONE_SERVER_PATH, picOneSPath);
        long updateDb = database.update(DATABASE_TABLE_SERVEY_STORAGE, cv, " outlet_code = " + outletCode , null);

        Log.d(RESPONSE_UPDATE, String.valueOf(updateDb));

    }
    public void updatePicOneSPathB(String outletCode, String picOneSPath){
        ContentValues cv = new ContentValues();
        cv.put(KEY_OUT_PIC_ONE_SERVER_PATH_B, picOneSPath);
        long updateDb = database.update(DATABASE_TABLE_BONDHU_CLUB, cv, " outlet_code_b = " + outletCode , null);

        Log.d(RESPONSE_UPDATE, String.valueOf(updateDb));

    }

//    public void updatePicTwoSPath(String outletCode, String picTwoSPath){
//        ContentValues cv = new ContentValues();
//        cv.put(KEY_OUT_PIC_TWO_SERVER_PATH, picTwoSPath);
//        long updateDb = database.update(DATABASE_TABLE_SERVEY_STORAGE, cv, " outlet_code = " + outletCode , null);
//
//        Log.d(RESPONSE_UPDATE, String.valueOf(updateDb));
//
//    }

//    public void updateFailledCoolerPicCode(String outletId, String coolerPicCode){
//        ContentValues cv = new ContentValues();
//        cv.put(KEY_COOLER_PIC_CODE, coolerPicCode);
//        database.update(DATABASE_TABLE_SERVEY_STORAGE, cv, "outlet_id=" + outletId, null);
//
//    }

//    public void updateFailledOutletPicCode(String outletId, String outletPicCode){
//        ContentValues cv = new ContentValues();
//        cv.put(KEY_OUTLET_PIC_CODE, outletPicCode);
//        database.update(DATABASE_TABLE_SERVEY_STORAGE, cv, "outlet_id=" + outletId, null);
//
//    }
//    public String selectOutletID (String userId){
//        String outletId = "";
//        String select_query = "SELECT  outlet_id FROM " + DATABASE_TABLE_SERVEY_STORAGE +
//                " WHERE " + KEY_USER_ID + " = '" + userId + "'" ;
//        Cursor cursor = database.rawQuery(select_query,null);
//        int iOutletId = cursor.getColumnIndex(KEY_OUTLET_ID);
//
//        for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
//            outletId = cursor.getString(iOutletId);
//
//        }
//
//        return  outletId;
//    }

    public boolean checkOutletId(String outletId) throws SQLException
    {
        Cursor cursor = database.query(DATABASE_TABLE_SERVEY_STORAGE, null, " outlet_code=? ",
                new String[] { outletId }, null, null, null);

        if (cursor != null) {
            if(cursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }

    public boolean checkOutletIdB(String outletId) throws SQLException
    {
        Cursor cursor = database.query(DATABASE_TABLE_BONDHU_CLUB, null, " outlet_code_b=? ",
                new String[] { outletId }, null, null, null);

        if (cursor != null) {
            if(cursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }


    public  int getTodaysNoOfRows(String entryDate, String userName){
        ArrayList allEntryDateList = new ArrayList();
        int listSize = 0;

        String select_query = "SELECT  entry_date FROM " + DATABASE_TABLE_SERVEY_STORAGE +
        " WHERE " + KEY_ENTRY_DATE + " = '" + entryDate + "'" + " And " + KEY_USER_NAME + " = '" + userName + "'" ;

        Cursor cursor = database.rawQuery(select_query, null);
        int iEntryDate = cursor.getColumnIndex(KEY_ENTRY_DATE);

        for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
          String lastEntryDate = cursor.getString(iEntryDate);
          allEntryDateList.add(lastEntryDate);
            listSize = allEntryDateList.size();

        }


        return listSize;
    }
//+ KEY_OUTLET_ID + " = '" + outletId + "'" + " AND " +
    public String selectLastEntryDate(String outletId){
        String lastEntryDate = "";
        String select_query = "SELECT  entry_date FROM " + DATABASE_TABLE_SERVEY_STORAGE +
                " WHERE " + KEY_OUTLET_CODE + " = '" + outletId + "'" ;
        Cursor cursor = database.rawQuery(select_query,null);
        int iEntryDate = cursor.getColumnIndex(KEY_ENTRY_DATE);

        for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            lastEntryDate = cursor.getString(iEntryDate);

        }

        return  lastEntryDate;
    }
    public String selectLastEntryDateB(String outletId){
        String lastEntryDate = "";
        String select_query = "SELECT  entry_date_b FROM " + DATABASE_TABLE_BONDHU_CLUB +
                " WHERE " + KEY_OUTLET_CODE_B + " = '" + outletId + "'" ;
        Cursor cursor = database.rawQuery(select_query,null);
        int iEntryDate = cursor.getColumnIndex(KEY_ENTRY_DATE_B);

        for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            lastEntryDate = cursor.getString(iEntryDate);

        }

        return  lastEntryDate;
    }

    public void updateServeyDataForSameUser(String outletCode, String coolerStatus, String coolerBrand, String signageStatus
                                            , String signageBrand, String volumeTarget,
                                            String remarks, String entryDate, String outPicOneLocalP,
                                            String outPicOneServerP,
                                            String startTime,String endTime, String latitude,String longitude,String userName, String syncStatus){
        ContentValues cv = new ContentValues();
        cv.put(KEY_OUTLET_CODE, outletCode);

        cv.put(KEY_COOLER_STATUS,  coolerStatus);
        cv.put(KEY_COOLER_BRANDING,  coolerBrand);
        cv.put(KEY_SIGNAGE_STATUS,  signageStatus);
        cv.put(KEY_SIGNAGE_BRANDING,  signageBrand);

        cv.put(KEY_VOLUME_TARGET,  volumeTarget);
        cv.put(KEY_REMARKS,  remarks);
        cv.put(KEY_ENTRY_DATE,  entryDate);
        cv.put(KEY_OUT_PIC_ONE_LOCAL_PATH,  outPicOneLocalP);
        cv.put(KEY_OUT_PIC_ONE_SERVER_PATH,  outPicOneServerP);
//        cv.put(KEY_OUT_PIC_TWO_LOCAL_PATH,  outPicTwoLocalP);
//        cv.put(KEY_OUT_PIC_TWO_SERVER_PATH,  outPicTwoServerP);
        cv.put(KEY_START_TIME,  startTime);
        cv.put(KEY_END_TIME,  endTime);
        cv.put(KEY_LATITUDE,  latitude);
        cv.put(KEY_LONGITUDE,  longitude);
        cv.put(KEY_USER_NAME,  userName);
        cv.put(KEY_SYNC_STATUS,  syncStatus);

       long dbUpdate = database.update(DATABASE_TABLE_SERVEY_STORAGE, cv, "outlet_code=" + outletCode, null);

        if(dbUpdate != -1) {
         //   saveToSharedPreferences("rowid", String.valueOf(dbUpdate),context);

            Toast.makeText(context, "New row upadated in   Information, row id: " + dbUpdate, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
        }


    }

    public void updateServeyDataForSameUserB(String outletCode, String coolCategory, String indusCSD, String indusWater
            , String indusLRB,  String pepsiCSD,String pepsiWater
            , String pepsiLRB, String remarks, String entryDate, String outPicOneLocalP,
                                             String outPicOneServerP,
                                             String startTime, String endTime,String latitude,String longitude,String userName, String syncStatus){
        ContentValues cv = new ContentValues();
        cv.put(KEY_OUTLET_CODE_B, outletCode);

        cv.put(KEY_COOL_CATEGORY,  coolCategory);
        cv.put(KEY_INDUST_CSD,  indusCSD);
        cv.put(KEY_INDUST_WATER,  indusWater);
        cv.put(KEY_INDUST_LRB,  indusLRB);

        cv.put(KEY_PEPSI_CSD,  pepsiCSD);
        cv.put(KEY_PEPSI_WATER,  pepsiWater);
        cv.put(KEY_PEPSI_LRB,  pepsiLRB);

        cv.put(KEY_REMARKS_B,  remarks);
        cv.put(KEY_ENTRY_DATE_B,  entryDate);
        cv.put(KEY_OUT_PIC_ONE_LOCAL_PATH_B,  outPicOneLocalP);
        cv.put(KEY_OUT_PIC_ONE_SERVER_PATH_B,  outPicOneServerP);
//        cv.put(KEY_OUT_PIC_TWO_LOCAL_PATH,  outPicTwoLocalP);
//        cv.put(KEY_OUT_PIC_TWO_SERVER_PATH,  outPicTwoServerP);
        cv.put(KEY_START_TIME_B,  startTime);
        cv.put(KEY_END_TIME_B,  endTime);
        cv.put(KEY_LATITUDE_B,  latitude);
        cv.put(KEY_LONGITUDE_B,  longitude);
        cv.put(KEY_USER_NAME,  userName);
        cv.put(KEY_SYNC_STATUS_B,  syncStatus);


        long dbUpdate = database.update(DATABASE_TABLE_BONDHU_CLUB, cv, "outlet_code_b=" + outletCode, null);

        if(dbUpdate != -1) {
            //   saveToSharedPreferences("rowid", String.valueOf(dbUpdate),context);

            Toast.makeText(context, "New row upadated in   Information, row id: " + dbUpdate, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
        }


    }



    public ArrayList getALLInputData(String userName, String loginDate){

        ArrayList<SurveyModel> serveyList = new ArrayList<>();
//        String select_query = "SELECT user_id,outlet_id,channel,cooler_status,cooler_purity,cooler_charging,sku_grb,sku_can,sku_go_pack,sku_400_ml,sku_500_ml,sku_1_liter,sku_2_liter,sku_aquafina,no_of_active_cooler,light_working,no_of_shelves,cleanliness,prime_position,availability,remarks,latitude,longitude,entry_date,cooler_pic_code,outlet_pic_code,start_time,end_time,sync_status FROM " + DATABASE_TABLE_SERVEY_STORAGE +
//                " WHERE " + KEY_USER_ID + " = '" + userId + "'" ;
        //Tr: Modified
        String select_query = "SELECT outlet_code,cooler_status,cooler_branding,signage_status,signage_branding,volume_target,remarks,entry_date,out_pic_one_local_path,out_pic_one_server_path,start_time,end_time,latitude,longitude,user_name,sync_status FROM " + DATABASE_TABLE_SERVEY_STORAGE +
                " WHERE " + KEY_USER_NAME + " = '" + userName + "'" + " AND " + KEY_ENTRY_DATE + " = '" + loginDate + "'" ;

        Cursor cursor = database.rawQuery(select_query,null);

       // if(cursor != null && cursor.moveToFirst()){
            //int iDbId = cursor.getColumnIndex(KEY_ROWID);
            int iOutletCode = cursor.getColumnIndex(KEY_OUTLET_CODE);

            int iCoolerStatus = cursor.getColumnIndex(KEY_COOLER_STATUS);
            int iCoolerBranding = cursor.getColumnIndex(KEY_COOLER_BRANDING);
            int iSignageStatus = cursor.getColumnIndex(KEY_SIGNAGE_STATUS);
            int iSignageBranding = cursor.getColumnIndex(KEY_SIGNAGE_BRANDING);
            int iEnrolledOrder = cursor.getColumnIndex(KEY_ENROLLED_ORDER);
            int iVolumeTarget = cursor.getColumnIndex(KEY_VOLUME_TARGET);
            int iRemarks = cursor.getColumnIndex(KEY_REMARKS);
            int iEntryDate = cursor.getColumnIndex(KEY_ENTRY_DATE);
            int iPhotoOneLocalPath = cursor.getColumnIndex(KEY_OUT_PIC_ONE_LOCAL_PATH);
            int iPhotoOneServerPath = cursor.getColumnIndex(KEY_OUT_PIC_ONE_SERVER_PATH);
//            int iPhotoTwoLocalPath = cursor.getColumnIndex(KEY_OUT_PIC_TWO_LOCAL_PATH);
//            int iPhotoTwoServerPath = cursor.getColumnIndex(KEY_OUT_PIC_TWO_SERVER_PATH);

            int iStartTime = cursor.getColumnIndex(KEY_START_TIME);
            int iEndTime = cursor.getColumnIndex(KEY_END_TIME);
            int iLatitude = cursor.getColumnIndex(KEY_LATITUDE);
            int iLongitude = cursor.getColumnIndex(KEY_LONGITUDE);
            int iUserName = cursor.getColumnIndex(KEY_USER_NAME);
            int iSyncStatus = cursor.getColumnIndex(KEY_SYNC_STATUS);

            for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
                //    for (cursor.moveToFirst(); ! cursor.isAfterLast(); cursor.moveToNext()) {

                SurveyModel serveyModel = new SurveyModel();
                serveyModel.setOutletCode(cursor.getString(iOutletCode));
                serveyModel.setCoolerStatus(cursor.getString(iCoolerStatus));
                serveyModel.setCoolerBrand(cursor.getString(iCoolerBranding));

                serveyModel.setSignageStatus(cursor.getString(iSignageStatus));
                serveyModel.setSignageBrand(cursor.getString(iSignageBranding));

                serveyModel.setVolumeTarget(cursor.getString(iVolumeTarget));

                serveyModel.setRemarks(cursor.getString(iRemarks));
                serveyModel.setEntryDate(cursor.getString(iEntryDate));
                serveyModel.setOutPicOneLPath(cursor.getString(iPhotoOneLocalPath));
                serveyModel.setOutPicOneSPath(cursor.getString(iPhotoOneServerPath));
//                serveyModel.setOutPicTwoLPath(cursor.getString(iPhotoTwoLocalPath));
//                serveyModel.setOutPicTwoSPath(cursor.getString(iPhotoTwoServerPath));

                serveyModel.setStartTime(cursor.getString(iStartTime));
                serveyModel.setEndTime(cursor.getString(iEndTime));
                serveyModel.setLatitude(cursor.getString(iLatitude));
                serveyModel.setLongitude(cursor.getString(iLongitude));
                serveyModel.setUserName(cursor.getString(iUserName));
                serveyModel.setStatus(cursor.getString(iSyncStatus));

                serveyList.add(serveyModel);

           // }
            //
        }
        cursor.close();
        return serveyList;
    }
    public ArrayList getALLInputBData(String userName, String loginDate){

        ArrayList<BondhuClubM> serveyList = new ArrayList<>();
//        String select_query = "SELECT user_id,outlet_id,channel,cooler_status,cooler_purity,cooler_charging,sku_grb,sku_can,sku_go_pack,sku_400_ml,sku_500_ml,sku_1_liter,sku_2_liter,sku_aquafina,no_of_active_cooler,light_working,no_of_shelves,cleanliness,prime_position,availability,remarks,latitude,longitude,entry_date,cooler_pic_code,outlet_pic_code,start_time,end_time,sync_status FROM " + DATABASE_TABLE_SERVEY_STORAGE +
//                " WHERE " + KEY_USER_ID + " = '" + userId + "'" ;
        //Tr: Modified
        String select_query = "SELECT outlet_code_b,cool_category,indust_csd,indust_water,indust_lrb,pepsi_csd,pepsi_water,pepsi_lrb" +
                ",remarks_b,entry_date_b,out_pic_one_local_path_b," +
                "out_pic_one_server_path_b,start_time_b,end_time_b,latitude_b," +
                "longitude_b,user_name,sync_status_b FROM " + DATABASE_TABLE_BONDHU_CLUB
                + " WHERE " + KEY_USER_NAME + " = '" + userName + "'" + " AND " + KEY_ENTRY_DATE_B + " = '" + loginDate + "'" ;

        Cursor cursor = database.rawQuery(select_query,null);

        // if(cursor != null && cursor.moveToFirst()){
        //int iDbId = cursor.getColumnIndex(KEY_ROWID);
        int iOutletCode = cursor.getColumnIndex(KEY_OUTLET_CODE_B);

        int iCoolCategory = cursor.getColumnIndex(KEY_COOL_CATEGORY);
        int iIndusCSD = cursor.getColumnIndex(KEY_INDUST_CSD);
        int iIndusWater = cursor.getColumnIndex(KEY_INDUST_WATER);
        int iIndusLRB = cursor.getColumnIndex(KEY_INDUST_LRB);
        int iPepsiCSD = cursor.getColumnIndex(KEY_PEPSI_CSD);
        int iPepsiWater = cursor.getColumnIndex(KEY_PEPSI_WATER);
        int iPepsiLRB = cursor.getColumnIndex(KEY_PEPSI_LRB);

        int iRemarks = cursor.getColumnIndex(KEY_REMARKS_B);
        int iEntryDate = cursor.getColumnIndex(KEY_ENTRY_DATE_B);
        int iPhotoOneLocalPath = cursor.getColumnIndex(KEY_OUT_PIC_ONE_LOCAL_PATH_B);
        int iPhotoOneServerPath = cursor.getColumnIndex(KEY_OUT_PIC_ONE_SERVER_PATH_B);
//            int iPhotoTwoLocalPath = cursor.getColumnIndex(KEY_OUT_PIC_TWO_LOCAL_PATH);
//            int iPhotoTwoServerPath = cursor.getColumnIndex(KEY_OUT_PIC_TWO_SERVER_PATH);

        int iStartTime = cursor.getColumnIndex(KEY_START_TIME_B);
        int iEndTime = cursor.getColumnIndex(KEY_END_TIME_B);
        int iLatitude = cursor.getColumnIndex(KEY_LATITUDE_B);
        int iLongitude = cursor.getColumnIndex(KEY_LONGITUDE_B);
        int iUserName = cursor.getColumnIndex(KEY_USER_NAME);
        int iSyncStatus = cursor.getColumnIndex(KEY_SYNC_STATUS_B);

        for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            //    for (cursor.moveToFirst(); ! cursor.isAfterLast(); cursor.moveToNext()) {

            BondhuClubM serveyModel = new BondhuClubM();
            serveyModel.setOutletCode(cursor.getString(iOutletCode));
            serveyModel.setCoolCategory(cursor.getString(iCoolCategory));
            serveyModel.setIndusCSD(cursor.getString(iIndusCSD));
            serveyModel.setIndusWater(cursor.getString(iIndusWater));
            serveyModel.setIndusLRB(cursor.getString(iIndusLRB));


            serveyModel.setPepsiCSD(cursor.getString(iPepsiCSD));
            serveyModel.setPepsiWater(cursor.getString(iPepsiWater));
            serveyModel.setPepsiLRB(cursor.getString(iPepsiLRB));

            serveyModel.setRemarks(cursor.getString(iRemarks));
            serveyModel.setEntryDate(cursor.getString(iEntryDate));
            serveyModel.setOutPicOneLPath(cursor.getString(iPhotoOneLocalPath));
            serveyModel.setOutPicOneSPath(cursor.getString(iPhotoOneServerPath));
//                serveyModel.setOutPicTwoLPath(cursor.getString(iPhotoTwoLocalPath));
//                serveyModel.setOutPicTwoSPath(cursor.getString(iPhotoTwoServerPath));

            serveyModel.setStartTime(cursor.getString(iStartTime));
            serveyModel.setEndTime(cursor.getString(iEndTime));
            serveyModel.setLatitude(cursor.getString(iLatitude));
            serveyModel.setLongitude(cursor.getString(iLongitude));
            serveyModel.setUserName(cursor.getString(iUserName));
            serveyModel.setStatus(cursor.getString(iSyncStatus));

            serveyList.add(serveyModel);

            // }
            //
        }
        cursor.close();
        return serveyList;
    }


    public ArrayList getFailledData(String userName, String outletCode){

        ArrayList<SurveyModel> serveyList = new ArrayList<>();
//        String select_query = "SELECT user_id,outlet_id,channel,cooler_status,cooler_purity,cooler_charging,sku_grb,sku_can,sku_go_pack,sku_400_ml,sku_500_ml,sku_1_liter,sku_2_liter,sku_aquafina,no_of_active_cooler,light_working,no_of_shelves,cleanliness,prime_position,availability,remarks,latitude,longitude,entry_date,cooler_pic_code,outlet_pic_code,start_time,end_time,sync_status FROM " + DATABASE_TABLE_SERVEY_STORAGE +
//                " WHERE " + KEY_USER_ID + " = '" + userId + "'" ;
        //Tr: Modified
        String select_query = "SELECT outlet_code,cooler_status,cooler_branding,signage_status,signage_branding,volume_target,remarks,entry_date,out_pic_one_local_path,out_pic_one_server_path,start_time,end_time,latitude,longitude,user_name,sync_status FROM " + DATABASE_TABLE_SERVEY_STORAGE +
                " WHERE " + KEY_USER_NAME + " = '" + userName + "'" + " AND " + KEY_OUTLET_CODE + " = '" + outletCode + "'" ;

        Cursor cursor = database.rawQuery(select_query,null);

        // if(cursor != null && cursor.moveToFirst()){
        //int iDbId = cursor.getColumnIndex(KEY_ROWID);
        int iOutletCode = cursor.getColumnIndex(KEY_OUTLET_CODE);

        int iCoolerStatus = cursor.getColumnIndex(KEY_COOLER_STATUS);
        int iCoolerBranding = cursor.getColumnIndex(KEY_COOLER_BRANDING);
        int iSignageStatus = cursor.getColumnIndex(KEY_SIGNAGE_STATUS);
        int iSignageBranding = cursor.getColumnIndex(KEY_SIGNAGE_BRANDING);
        int iEnrolledOrder = cursor.getColumnIndex(KEY_ENROLLED_ORDER);
        int iVolumeTarget = cursor.getColumnIndex(KEY_VOLUME_TARGET);
        int iRemarks = cursor.getColumnIndex(KEY_REMARKS);
        int iEntryDate = cursor.getColumnIndex(KEY_ENTRY_DATE);
        int iPhotoOneLocalPath = cursor.getColumnIndex(KEY_OUT_PIC_ONE_LOCAL_PATH);
        int iPhotoOneServerPath = cursor.getColumnIndex(KEY_OUT_PIC_ONE_SERVER_PATH);
//        int iPhotoTwoLocalPath = cursor.getColumnIndex(KEY_OUT_PIC_TWO_LOCAL_PATH);
//        int iPhotoTwoServerPath = cursor.getColumnIndex(KEY_OUT_PIC_TWO_SERVER_PATH);

        int iStartTime = cursor.getColumnIndex(KEY_START_TIME);
        int iEndTime = cursor.getColumnIndex(KEY_END_TIME);
        int iLatitude = cursor.getColumnIndex(KEY_LATITUDE);
        int iLongitude = cursor.getColumnIndex(KEY_LONGITUDE);
        int iUserName = cursor.getColumnIndex(KEY_USER_NAME);
        int iSyncStatus = cursor.getColumnIndex(KEY_SYNC_STATUS);

        for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            //    for (cursor.moveToFirst(); ! cursor.isAfterLast(); cursor.moveToNext()) {

            SurveyModel serveyModel = new SurveyModel();
            serveyModel.setOutletCode(cursor.getString(iOutletCode));
            serveyModel.setCoolerStatus(cursor.getString(iCoolerStatus));
            serveyModel.setCoolerBrand(cursor.getString(iCoolerBranding));

            serveyModel.setSignageStatus(cursor.getString(iSignageStatus));
            serveyModel.setSignageBrand(cursor.getString(iSignageBranding));

            serveyModel.setVolumeTarget(cursor.getString(iVolumeTarget));

            serveyModel.setRemarks(cursor.getString(iRemarks));
            serveyModel.setEntryDate(cursor.getString(iEntryDate));
            serveyModel.setOutPicOneLPath(cursor.getString(iPhotoOneLocalPath));
            serveyModel.setOutPicOneSPath(cursor.getString(iPhotoOneServerPath));
//            serveyModel.setOutPicTwoLPath(cursor.getString(iPhotoTwoLocalPath));
//            serveyModel.setOutPicTwoSPath(cursor.getString(iPhotoTwoServerPath));

            serveyModel.setStartTime(cursor.getString(iStartTime));
            serveyModel.setEndTime(cursor.getString(iEndTime));
            serveyModel.setLatitude(cursor.getString(iLatitude));
            serveyModel.setLongitude(cursor.getString(iLongitude));
            serveyModel.setUserName(cursor.getString(iUserName));
            serveyModel.setStatus(cursor.getString(iSyncStatus));

            serveyList.add(serveyModel);

            // }
            //
        }
        cursor.close();
        return serveyList;
    }

    public ArrayList getAllOfflineData(String userName, String checkSyncStatus){

        ArrayList<SurveyModel> serveyList = new ArrayList<>();
//        String select_query = "SELECT user_id,outlet_id,channel,cooler_status,cooler_purity,cooler_charging,sku_grb,sku_can,sku_go_pack,sku_400_ml,sku_500_ml,sku_1_liter,sku_2_liter,sku_aquafina,no_of_active_cooler,light_working,no_of_shelves,cleanliness,prime_position,availability,remarks,latitude,longitude,entry_date,cooler_pic_code,outlet_pic_code,start_time,end_time,sync_status FROM " + DATABASE_TABLE_SERVEY_STORAGE +
//                " WHERE " + KEY_USER_ID + " = '" + userId + "'" ;

        //Tr: Modified
        String select_query = "SELECT outlet_code,cooler_status,cooler_branding,signage_status,signage_branding,volume_target,remarks,entry_date,out_pic_one_local_path,out_pic_one_server_path,start_time,end_time,latitude,longitude,user_name,sync_status FROM  " + DATABASE_TABLE_SERVEY_STORAGE +
                " WHERE " + KEY_USER_NAME + " = '" + userName + "'" + " AND " + KEY_SYNC_STATUS + " = '" + checkSyncStatus + "'" ;


        Cursor cursor = database.rawQuery(select_query,null);

        // if(cursor != null && cursor.moveToFirst()){
        //int iDbId = cursor.getColumnIndex(KEY_ROWID);
        int iOutletCode = cursor.getColumnIndex(KEY_OUTLET_CODE);

        int iCoolerStatus = cursor.getColumnIndex(KEY_COOLER_STATUS);
        int iCoolerBranding = cursor.getColumnIndex(KEY_COOLER_BRANDING);
        int iSignageStatus = cursor.getColumnIndex(KEY_SIGNAGE_STATUS);
        int iSignageBranding = cursor.getColumnIndex(KEY_SIGNAGE_BRANDING);
        int iEnrolledOrder = cursor.getColumnIndex(KEY_ENROLLED_ORDER);
        int iVolumeTarget = cursor.getColumnIndex(KEY_VOLUME_TARGET);
        int iRemarks = cursor.getColumnIndex(KEY_REMARKS);
        int iEntryDate = cursor.getColumnIndex(KEY_ENTRY_DATE);
        int iPhotoOneLocalPath = cursor.getColumnIndex(KEY_OUT_PIC_ONE_LOCAL_PATH);
        int iPhotoOneServerPath = cursor.getColumnIndex(KEY_OUT_PIC_ONE_SERVER_PATH);
//        int iPhotoTwoLocalPath = cursor.getColumnIndex(KEY_OUT_PIC_TWO_LOCAL_PATH);
//        int iPhotoTwoServerPath = cursor.getColumnIndex(KEY_OUT_PIC_TWO_SERVER_PATH);

        int iStartTime = cursor.getColumnIndex(KEY_START_TIME);
        int iEndTime = cursor.getColumnIndex(KEY_END_TIME);
        int iLatitude = cursor.getColumnIndex(KEY_LATITUDE);
        int iLongitude = cursor.getColumnIndex(KEY_LONGITUDE);
        int iUserName = cursor.getColumnIndex(KEY_USER_NAME);
        int iSyncStatus = cursor.getColumnIndex(KEY_SYNC_STATUS);

        for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            //    for (cursor.moveToFirst(); ! cursor.isAfterLast(); cursor.moveToNext()) {

            SurveyModel serveyModel = new SurveyModel();
            serveyModel.setOutletCode(cursor.getString(iOutletCode));
            serveyModel.setCoolerStatus(cursor.getString(iCoolerStatus));
            serveyModel.setCoolerBrand(cursor.getString(iCoolerBranding));

            serveyModel.setSignageStatus(cursor.getString(iSignageStatus));
            serveyModel.setSignageBrand(cursor.getString(iSignageBranding));

            serveyModel.setVolumeTarget(cursor.getString(iVolumeTarget));

            serveyModel.setRemarks(cursor.getString(iRemarks));
            serveyModel.setEntryDate(cursor.getString(iEntryDate));
            serveyModel.setOutPicOneLPath(cursor.getString(iPhotoOneLocalPath));
            serveyModel.setOutPicOneSPath(cursor.getString(iPhotoOneServerPath));
//            serveyModel.setOutPicTwoLPath(cursor.getString(iPhotoTwoLocalPath));
//            serveyModel.setOutPicTwoSPath(cursor.getString(iPhotoTwoServerPath));

            serveyModel.setStartTime(cursor.getString(iStartTime));
            serveyModel.setEndTime(cursor.getString(iEndTime));
            serveyModel.setLatitude(cursor.getString(iLatitude));
            serveyModel.setLongitude(cursor.getString(iLongitude));
            serveyModel.setUserName(cursor.getString(iUserName));
            serveyModel.setStatus(cursor.getString(iSyncStatus));

            serveyList.add(serveyModel);
            // }
            //
        }
        cursor.close();
        return serveyList;
    }

//    public String getCoolerPicPath(String outletId){
//
//        String coolerPicPath = "";
//        String select_query = "SELECT  cooler_pic_path FROM " + DATABASE_TABLE_SERVEY_STORAGE +
//                " WHERE " + KEY_OUTLET_ID + " = '" + outletId + "'" ;
//        Cursor cursor = database.rawQuery(select_query,null);
//        int iCoolerPicPath = cursor.getColumnIndex(KEY_COOLER_PIC_PATH);
//
//        for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
//            coolerPicPath = cursor.getString(iCoolerPicPath);
//
//        }
//
//        return  coolerPicPath;
//    }

//    public String getOutletPicPath(String outletId){
//
//        String outletPicPath = "";
//        String select_query = "SELECT  outlet_pic_path FROM " + DATABASE_TABLE_SERVEY_STORAGE +
//                " WHERE " + KEY_OUTLET_ID + " = '" + outletId + "'" ;
//        Cursor cursor = database.rawQuery(select_query,null);
//        int iOutletPicPath = cursor.getColumnIndex(KEY_OUTLET_PIC_PATH);
//
//        for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
//            outletPicPath = cursor.getString(iOutletPicPath);
//
//        }
//
//        return  outletPicPath;
//    }
    public Cursor selectALlRecords() {

        Cursor c = null;
        try {
            c = database.rawQuery("Select * from "
                    + DATABASE_TABLE_SERVEY_STORAGE, null);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return c;
    }

    public Cursor getAllInputRecords() {

        Cursor c = null;
        try {
            c = database.rawQuery("Select * from "
                    + DATABASE_TABLE_SERVEY_STORAGE, null);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return c;
    }

//    public ArrayList getSpecificOfflineData(String outletId, String userId){
//
//        ArrayList<ServeyModel> serveyList = new ArrayList<>();
////        String select_query = "SELECT user_id,outlet_id,channel,cooler_status,cooler_purity,cooler_charging,sku_grb,sku_can,sku_go_pack,sku_400_ml,sku_500_ml,sku_1_liter,sku_2_liter,sku_aquafina,no_of_active_cooler,light_working,no_of_shelves,cleanliness,prime_position,availability,remarks,latitude,longitude,entry_date,cooler_pic_code,outlet_pic_code,start_time,end_time,sync_status FROM " + DATABASE_TABLE_SERVEY_STORAGE +
////                " WHERE " + KEY_USER_ID + " = '" + userId + "'" ;
//        //Tr: Modified
//        String select_query = "SELECT user_id,outlet_id,channel,cooler_status,cooler_purity,cooler_charging,sku_grb,sku_can,sku_go_pack,sku_400_ml,sku_500_ml,sku_1_liter,sku_2_liter,sku_aquafina,no_of_active_cooler,light_working,no_of_shelves,cleanliness,prime_position,availability,remarks,latitude,longitude,entry_date,cooler_pic_code,outlet_pic_code,start_time,end_time,sync_status FROM " + DATABASE_TABLE_SERVEY_STORAGE +
//                " WHERE " + KEY_USER_ID + " = '" + userId + "'" + " AND " + KEY_OUTLET_ID + " = '" + outletId + "'" ;
//
//        Cursor cursor = database.rawQuery(select_query,null);
//
//        // if(cursor != null && cursor.moveToFirst()){
//        //int iDbId = cursor.getColumnIndex(KEY_ROWID);
//        int iOutletId = cursor.getColumnIndex(KEY_OUTLET_ID);
//        int iChannel = cursor.getColumnIndex(KEY_CHANNEL);
//        int iCoolerStatus = cursor.getColumnIndex(KEY_COOLER_STATUS);
//        int iCoolerCharging = cursor.getColumnIndex(KEY_COOLER_CHARGING);
//        int iGRB = cursor.getColumnIndex(KEY_SKU_GRB);
//        int iCan500 = cursor.getColumnIndex(KEY_CAN_500_ML);
//        int iShelves = cursor.getColumnIndex(KEY_NO_OF_SHELVES);
//        int iCleanlines = cursor.getColumnIndex(KEY_CLEANLINESS);
//        int iPrimePosition = cursor.getColumnIndex(KEY_PRIME_POSITION);
//        int iAvailabilty = cursor.getColumnIndex(KEY_AVAILABILTY);
//        int iRemarks = cursor.getColumnIndex(KEY_REMARKS);
//        int iLatitude = cursor.getColumnIndex(KEY_LATITUDE);
//        int iLongitude = cursor.getColumnIndex(KEY_LONGITUDE);
//        int iUserId = cursor.getColumnIndex(KEY_USER_ID);
//        int iEntryDate = cursor.getColumnIndex(KEY_ENTRY_DATE);
//
//        int iCoolerPID = cursor.getColumnIndex(KEY_COOLER_PIC_CODE);
//        int iOutletPID = cursor.getColumnIndex(KEY_OUTLET_PIC_CODE);
//        int iCulerPurity = cursor.getColumnIndex(KEY_COOLER_PURITY);
//        int iCan = cursor.getColumnIndex(KEY_SKU_CAN);
//        int iGoPack = cursor.getColumnIndex(KEY_SKU_GO_PACK);
//        int iCan400 = cursor.getColumnIndex(KEY_CAN_400_ML);
//        int iLtr1 = cursor.getColumnIndex(KEY_SKU_1_LITER);
//        int iLtr2 = cursor.getColumnIndex(KEY_SKU_2_LITER);
//        int iAquafina = cursor.getColumnIndex(KEY_SKU_AQUAFINA);
//        int iCoolerActive = cursor.getColumnIndex(KEY_NO_OF_ACTIVE_COOLER);
//        int iLightWorking = cursor.getColumnIndex(KEY_LIGHT_WORKING);
//        int iStartTime = cursor.getColumnIndex(KEY_START_TIME);
//        int iEndTime = cursor.getColumnIndex(KEY_END_TIME);
//        int iSyncStatus = cursor.getColumnIndex(KEY_SYNC_STATUS);
//
//        for (cursor.moveToLast(); ! cursor.isBeforeFirst(); cursor.moveToPrevious()) {
//            //    for (cursor.moveToFirst(); ! cursor.isAfterLast(); cursor.moveToNext()) {
//
//            ServeyModel serveyModel = new ServeyModel();
//            serveyModel.setOutletId(cursor.getString(iOutletId));
//            serveyModel.setChannel(cursor.getString(iChannel));
//            serveyModel.setCoolerStatus(cursor.getString(iCoolerStatus));
//            serveyModel.setCoolerCharging(cursor.getString(iCoolerCharging));
//
//            serveyModel.setGrb(cursor.getString(iGRB));
//            serveyModel.setCan500(cursor.getString(iCan500));
//            serveyModel.setShelves(cursor.getString(iShelves));
//            serveyModel.setCleanliness(cursor.getString(iCleanlines));
//
//            serveyModel.setPrimePosition(cursor.getString(iPrimePosition));
//            serveyModel.setAvailabilty(cursor.getString(iAvailabilty));
//
//            serveyModel.setRemarks(cursor.getString(iRemarks));
//            serveyModel.setLatitude(cursor.getString(iLatitude));
//            serveyModel.setLongitude(cursor.getString(iLongitude));
//
//            serveyModel.setUserId(cursor.getString(iUserId));
//
//            serveyModel.setEntryDate(cursor.getString(iEntryDate));
//            serveyModel.setCoolerPID(cursor.getString(iCoolerPID));
//            serveyModel.setOutletPID(cursor.getString(iOutletPID));
//            serveyModel.setCoolerPurity(cursor.getString(iCulerPurity));
//            serveyModel.setCan(cursor.getString(iCan));
//            serveyModel.setGoPack(cursor.getString(iGoPack));
//            serveyModel.setCan400(cursor.getString(iCan400));
//
//            serveyModel.setLtr1(cursor.getString(iLtr1));
//            serveyModel.setLtr2(cursor.getString(iLtr2));
//            serveyModel.setAquafina(cursor.getString(iAquafina));
//            serveyModel.setCoolerActive(cursor.getString(iCoolerActive));
//
//            serveyModel.setLightWorking(cursor.getString(iLightWorking));
//            serveyModel.setStartTime(cursor.getString(iStartTime));
//            serveyModel.setEndTime(cursor.getString(iEndTime));
//            serveyModel.setSyncStatus(cursor.getString(iSyncStatus));
//
//            serveyList.add(serveyModel);
//
//            // }
//            //
//        }
//        cursor.close();
//        return serveyList;
//    }
}
