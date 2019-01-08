package planet.it.limited.pepsigosmart.utils;

import android.content.Context;

import java.util.ArrayList;


import planet.it.limited.pepsigosmart.database.LocalStoragePepsiDB;
import planet.it.limited.pepsigosmart.model.BondhuClubM;
import planet.it.limited.pepsigosmart.model.SurveyModel;

import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getValueFromSharedPreferences;

/**
 * Created by Master on 2/10/2018.
 */

public class TableHelper {
    Context mContext;
     String userName = "";

    public String[] tableHeaders={"outlet_code","cooler_status","cooler_branding","signage_status",
            "signage_branding",
            "volume_target","remarks","entry_date","pic_one_local_path","pic_one_server_path","entry_time","latitude","longitude",
          "enty_by","sync status"};
    public String[][] allStorageData;
    public String[][] specificOfflineData;
    SurveyModel serveyModel;
    LocalStoragePepsiDB localStorageDB;
    public String[][] allTodaysData;
    ClearAllSaveData clearAllSaveData;
    BondhuClubM bondhuClubM;
    public String[][] allStorageDataB;

    public TableHelper(Context c) {
        this.mContext = c;
        serveyModel= new SurveyModel();
        localStorageDB = new LocalStoragePepsiDB(c);
        userName = getValueFromSharedPreferences("user_name",mContext);
        clearAllSaveData = new ClearAllSaveData(mContext);

    }

    public String[] getTableHeaders()
    {
        return tableHeaders;
    }


    public  String[][] getAllInputData()
    {
        String loginDate = clearAllSaveData.getCurrentEntryDate();

        localStorageDB.open();
        if(userName!=null && !userName.isEmpty()){
            ArrayList<SurveyModel> surveylist=localStorageDB.getALLInputData(userName,loginDate);
            allStorageData= new String[surveylist.size()][15];

            for (int i=0;i<surveylist.size();i++) {

                serveyModel=surveylist.get(i);

                allStorageData[i][0]=serveyModel.getOutletCode();
                allStorageData[i][1]=serveyModel.getCoolerStatus();
                allStorageData[i][2]=serveyModel.getCoolerBrand();
                allStorageData[i][3]=serveyModel.getSignageStatus();
                allStorageData[i][4]=serveyModel.getSignageBrand();
              //  allStorageData[i][5]=serveyModel.getEnrolledOrder();
                allStorageData[i][5]=serveyModel.getVolumeTarget();
                allStorageData[i][6]=serveyModel.getRemarks();
                allStorageData[i][7]=serveyModel.getEntryDate();
                allStorageData[i][8]=serveyModel.getOutPicOneLPath();
                allStorageData[i][9]=serveyModel.getOutPicOneSPath();
//                allStorageData[i][11]=serveyModel.getOutPicTwoLPath();
//                allStorageData[i][12]=serveyModel.getOutPicTwoSPath();
                allStorageData[i][10]=serveyModel.getStartTime();
                allStorageData[i][11]=serveyModel.getLatitude();
                allStorageData[i][12]=serveyModel.getLongitude();
                allStorageData[i][13]=serveyModel.getUserName();
                allStorageData[i][14]=serveyModel.getStatus();

            }
        }



        return allStorageData;
    }
    public  String[][] getAllInputBData()
    {
        String loginDate = clearAllSaveData.getCurrentEntryDate();

        localStorageDB.open();
        if(userName!=null && !userName.isEmpty()){
            ArrayList<BondhuClubM> surveylist=localStorageDB.getALLInputBData(userName,loginDate);
            allStorageDataB= new String[surveylist.size()][17];

            for (int i=0;i<surveylist.size();i++) {

                bondhuClubM=surveylist.get(i);

                allStorageDataB[i][0]=bondhuClubM.getOutletCode();
                allStorageDataB[i][1]=bondhuClubM.getCoolCategory();
                allStorageDataB[i][2]=bondhuClubM.getIndusCSD();
                allStorageDataB[i][3]=bondhuClubM.getIndusWater();
                allStorageDataB[i][4]=bondhuClubM.getIndusLRB();
                //  allStorageData[i][5]=serveyModel.getEnrolledOrder();
                allStorageDataB[i][5]=bondhuClubM.getPepsiCSD();
                allStorageDataB[i][6]=bondhuClubM.getIndusWater();
                allStorageDataB[i][7]=bondhuClubM.getPepsiLRB();

                allStorageDataB[i][8]=bondhuClubM.getRemarks();
                allStorageDataB[i][9]=bondhuClubM.getEntryDate();
                allStorageDataB[i][10]=bondhuClubM.getOutPicOneLPath();
                allStorageDataB[i][11]=bondhuClubM.getOutPicOneSPath();
//                allStorageData[i][11]=serveyModel.getOutPicTwoLPath();
//                allStorageData[i][12]=serveyModel.getOutPicTwoSPath();
                allStorageDataB[i][12]=bondhuClubM.getStartTime();
                allStorageDataB[i][13]=bondhuClubM.getLatitude();
                allStorageDataB[i][14]=bondhuClubM.getLongitude();
                allStorageDataB[i][15]=bondhuClubM.getUserName();
                allStorageDataB[i][16]=bondhuClubM.getStatus();

            }
        }



        return allStorageDataB;
    }


    public  String[][] getAllOfflineData()
    {
        String checkSyncStatus = "failled";

        localStorageDB.open();
        if(userName!=null && !userName.isEmpty()){
            ArrayList<SurveyModel> surveylist=localStorageDB.getAllOfflineData(userName,checkSyncStatus);
            allStorageData= new String[surveylist.size()][15];

            for (int i=0;i<surveylist.size();i++) {

                serveyModel=surveylist.get(i);

                allStorageData[i][0]=serveyModel.getOutletCode();
                allStorageData[i][1]=serveyModel.getCoolerStatus();
                allStorageData[i][2]=serveyModel.getCoolerBrand();
                allStorageData[i][3]=serveyModel.getSignageStatus();
                allStorageData[i][4]=serveyModel.getSignageBrand();
               // allStorageData[i][5]=serveyModel.getEnrolledOrder();
                allStorageData[i][5]=serveyModel.getVolumeTarget();
                allStorageData[i][6]=serveyModel.getRemarks();
                allStorageData[i][7]=serveyModel.getEntryDate();
                allStorageData[i][8]=serveyModel.getOutPicOneLPath();
                allStorageData[i][9]=serveyModel.getOutPicOneSPath();
//                allStorageData[i][11]=serveyModel.getOutPicTwoLPath();
//                allStorageData[i][12]=serveyModel.getOutPicTwoSPath();
                allStorageData[i][10]=serveyModel.getStartTime();
                allStorageData[i][11]=serveyModel.getLatitude();
                allStorageData[i][12]=serveyModel.getLongitude();
                allStorageData[i][13]=serveyModel.getUserName();
                allStorageData[i][14]=serveyModel.getStatus();

            }
        }



        return allStorageData;
    }
//    public   String[][] getSpecificOutletData(String outletCode)
//    {
//        //String loginDate = Constants.getCurrentEntryDate();
//
//        localStorageDB.open();
//        if(userId!=null && !userId.isEmpty()){
//            ArrayList<ServeyModel> surveylist=localStorageDB.getSpecificOfflineData(outletCode,userId);
//            specificOfflineData= new String[surveylist.size()][31];
//
//            for (int i=0;i<surveylist.size();i++) {
//
//                serveyModel=surveylist.get(i);
//
//                specificOfflineData[i][0]=serveyModel.getOutletId();
//                specificOfflineData[i][1]=serveyModel.getChannel();
//                specificOfflineData[i][2]=serveyModel.getCoolerStatus();
//                specificOfflineData[i][3]=serveyModel.getCoolerCharging();
//                specificOfflineData[i][4]=serveyModel.getGrb();
//                specificOfflineData[i][5]=serveyModel.getCan500();
//                specificOfflineData[i][6]=serveyModel.getShelves();
//                specificOfflineData[i][7]=serveyModel.getCleanliness();
//                specificOfflineData[i][8]=serveyModel.getPrimePosition();
//                specificOfflineData[i][9]=serveyModel.getAvailabilty();
//                specificOfflineData[i][10]=serveyModel.getRemarks();
//                specificOfflineData[i][11]=serveyModel.getLatitude();
//                specificOfflineData[i][12]=serveyModel.getLongitude();
//                specificOfflineData[i][13]=serveyModel.getUserId();
//                specificOfflineData[i][14]=serveyModel.getEntryDate();
//                specificOfflineData[i][15]=serveyModel.getCoolerPID();
//                specificOfflineData[i][16]=serveyModel.getOutletPID();
//                specificOfflineData[i][17]=serveyModel.getCoolerPurity();
//                specificOfflineData[i][18]=serveyModel.getCan();
//                specificOfflineData[i][19]=serveyModel.getGoPack();
//                specificOfflineData[i][20]=serveyModel.getCan400();
//                specificOfflineData[i][21]=serveyModel.getLtr1();
//                specificOfflineData[i][22]=serveyModel.getLtr2();
//                specificOfflineData[i][23]=serveyModel.getAquafina();
//                specificOfflineData[i][24]=serveyModel.getCoolerActive();
//                specificOfflineData[i][25]=serveyModel.getLightWorking();
//                specificOfflineData[i][26]=serveyModel.getStartTime();
//                specificOfflineData[i][27]=serveyModel.getEndTime();
//                specificOfflineData[i][28]=serveyModel.getSyncStatus();
//
//
//            }
//        }
//
//
//
//        return specificOfflineData;
//    }
}
