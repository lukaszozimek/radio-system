package io.protone.custom.metadata.cartchunk;

/**
 * Created by lukaszozimek on 28.01.2017.
 */
public class CartChunk {
    private String version;  ///4 char
    private String title;  //64char
    private String artist;  //64 char
    private String cutID; //64 char
    private String clientID; //64 char
    private String categoryID;  ///64 char
    private String classification;  ///64char
    private String outCue;  ///64 char
    private String startDate;  //10 char
    private String startTime; //8char
    private String endDate;  //10 char
    private String endTime;  //8 char
    private String producerAppID; //64char
    private String producerAppVersion;  //64char
    private String userDef;   ///64 char
    private long dwLevelReference;  ///uint
    private CartTimer[] postTimer;
    private String reserved;  //276
    private String URL;  //1024 char

    public String getuRL() {
        return URL;
    }

    public void setuRL(String uRL) {
        this.URL = uRL;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public CartTimer[] getPostTimer() {
        return postTimer;
    }

    public void setPostTimer(CartTimer[] postTimer) {
        this.postTimer = postTimer;
    }

    public long getDwLevelReference() {
        return dwLevelReference;
    }

    public void setDwLevelReference(long dwLevelReference) {
        this.dwLevelReference = dwLevelReference;
    }

    public String getUserDef() {
        return userDef;
    }

    public void setUserDef(String userDef) {
        this.userDef = userDef;
    }

    public String getProducerAppVersion() {
        return producerAppVersion;
    }

    public void setProducerAppVersion(String producerAppVersion) {
        this.producerAppVersion = producerAppVersion;
    }

    public String getProducerAppID() {
        return producerAppID;
    }

    public void setProducerAppID(String producerAppID) {
        this.producerAppID = producerAppID;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getOutCue() {
        return outCue;
    }

    public void setOutCue(String qutCue) {
        this.outCue = qutCue;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getCutID() {
        return cutID;
    }

    public void setCutID(String cutID) {
        this.cutID = cutID;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
