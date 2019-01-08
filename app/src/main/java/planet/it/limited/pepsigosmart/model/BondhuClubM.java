package planet.it.limited.pepsigosmart.model;

/**
 * Created by Tarikul on 08-Jan-19.
 */

public class BondhuClubM {
    public String getOutletCode() {
        return outletCode;
    }

    public void setOutletCode(String outletCode) {
        this.outletCode = outletCode;
    }

    public String getCoolCategory() {
        return coolCategory;
    }

    public void setCoolCategory(String coolCategory) {
        this.coolCategory = coolCategory;
    }

    public String getIndusCSD() {
        return indusCSD;
    }

    public void setIndusCSD(String indusCSD) {
        this.indusCSD = indusCSD;
    }

    public String getIndusWater() {
        return indusWater;
    }

    public void setIndusWater(String indusWater) {
        this.indusWater = indusWater;
    }

    public String getIndusLRB() {
        return indusLRB;
    }

    public void setIndusLRB(String indusLRB) {
        this.indusLRB = indusLRB;
    }

    public String getPepsiCSD() {
        return pepsiCSD;
    }

    public void setPepsiCSD(String pepsiCSD) {
        this.pepsiCSD = pepsiCSD;
    }

    public String getPepsiWater() {
        return pepsiWater;
    }

    public void setPepsiWater(String pepsiWater) {
        this.pepsiWater = pepsiWater;
    }

    public String getPepsiLRB() {
        return pepsiLRB;
    }

    public void setPepsiLRB(String pepsiLRB) {
        this.pepsiLRB = pepsiLRB;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getOutPicOneLPath() {
        return outPicOneLPath;
    }

    public void setOutPicOneLPath(String outPicOneLPath) {
        this.outPicOneLPath = outPicOneLPath;
    }

    public String getOutPicOneSPath() {
        return outPicOneSPath;
    }

    public void setOutPicOneSPath(String outPicOneSPath) {
        this.outPicOneSPath = outPicOneSPath;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String outletCode;

    public String coolCategory;


    public BondhuClubM(){

    }

    public BondhuClubM(String outletCode, String coolCategory, String indusCSD, String indusWater, String indusLRB, String pepsiCSD, String pepsiWater, String pepsiLRB, String remarks, String entryDate, String outPicOneLPath,
                       String outPicOneSPath, String startTime, String endTime, String latitude, String longitude, String userName, String status) {
        this.outletCode = outletCode;
        this.coolCategory = coolCategory;
        this.indusCSD = indusCSD;
        this.indusWater = indusWater;
        this.indusLRB = indusLRB;
        this.pepsiCSD = pepsiCSD;
        this.pepsiWater = pepsiWater;
        this.pepsiLRB = pepsiLRB;
        this.remarks = remarks;
        this.entryDate = entryDate;
        this.outPicOneLPath = outPicOneLPath;
        this.outPicOneSPath = outPicOneSPath;
        this.startTime = startTime;
        this.endTime = endTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userName = userName;
        this.status = status;
    }

    public String indusCSD;
    public String indusWater;
    public String indusLRB;
    public String pepsiCSD;
    public String pepsiWater;
    public String pepsiLRB;


    public String remarks;
    public String entryDate;
    public String outPicOneLPath;
    public String outPicOneSPath;

    public String startTime;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String endTime;
    public String latitude ;
    public String longitude;
    public String userName;
    public String status;





}
