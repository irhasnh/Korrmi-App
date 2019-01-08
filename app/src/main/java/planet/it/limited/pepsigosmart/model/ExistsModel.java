package planet.it.limited.pepsigosmart.model;

/**
 * Created by Tarikul on 03-Dec-18.
 */

public class ExistsModel {



    public String getOutletCode() {
        return outletCode;
    }

    public void setOutletCode(String outletCode) {
        this.outletCode = outletCode;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getOwnerBkashNo() {
        return ownerBkashNo;
    }

    public void setOwnerBkashNo(String ownerBkashNo) {
        this.ownerBkashNo = ownerBkashNo;
    }

    public String getOutletAddress() {
        return outletAddress;
    }

    public void setOutletAddress(String outletAddress) {
        this.outletAddress = outletAddress;
    }

    public String getCoolerStatus() {
        return coolerStatus;
    }

    public void setCoolerStatus(String coolerStatus) {
        this.coolerStatus = coolerStatus;
    }

    public String getCoolerBrand() {
        return coolerBrand;
    }

    public void setCoolerBrand(String coolerBrand) {
        this.coolerBrand = coolerBrand;
    }

    public String getSignageStatus() {
        return signageStatus;
    }

    public void setSignageStatus(String signageStatus) {
        this.signageStatus = signageStatus;
    }

    public String getSignageBrand() {
        return signageBrand;
    }

    public void setSignageBrand(String signageBrand) {
        this.signageBrand = signageBrand;
    }



    public String getVolumeTarget() {
        return volumeTarget;
    }

    public void setVolumeTarget(String volumeTarget) {
        this.volumeTarget = volumeTarget;
    }

    public String getVolAmount() {
        return volAmount;
    }

    public void setVolAmount(String volAmount) {
        this.volAmount = volAmount;
    }

    public String getOutPhotoUri() {
        return outPhotoUri;
    }

    public void setOutPhotoUri(String outPhotoUri) {
        this.outPhotoUri = outPhotoUri;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String outletCode;

    public ExistsModel(String outletCode, String outletName, String ownerBkashNo, String outletAddress, String coolerStatus, String coolerBrand, String signageStatus,
                       String signageBrand,  String volumeTarget, String volAmount, String outPhotoUri, String remarks, String startTime) {
        this.outletCode = outletCode;
        this.outletName = outletName;
        this.ownerBkashNo = ownerBkashNo;
        this.outletAddress = outletAddress;
        this.coolerStatus = coolerStatus;
        this.coolerBrand = coolerBrand;
        this.signageStatus = signageStatus;
        this.signageBrand = signageBrand;

        this.volumeTarget = volumeTarget;
        this.volAmount = volAmount;
        this.outPhotoUri = outPhotoUri;
        this.remarks = remarks;
        this.startTime = startTime;
    }

    public String outletName;
    public String ownerBkashNo;
    public String outletAddress;
    public String coolerStatus;
    public String coolerBrand;
    public String signageStatus;
    public String signageBrand;

    public String volumeTarget;
    public String volAmount;
    public String outPhotoUri;
    public String remarks;
    public String startTime;



    public ExistsModel(){

    }





}
