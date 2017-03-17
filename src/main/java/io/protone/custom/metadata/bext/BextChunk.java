package io.protone.custom.metadata.bext;

import java.util.Map;

/**
 * Created by lukaszozimek on 28.01.2017.
 */
public class BextChunk
{
    private String description;  //256
    private String originator;  //32
    private String originatorReferance;  //32
    private String originatorDate;  //10
    private String originatorTime;  //8
    private long timeRefenceLow; //uint
    private long timeReferenceHigh; //uint
    private int version; //ushort
    private byte UMID_0;
    private byte UMID_63;
    private int LoundessValue; //ushort
    private int loudnessRange; //ushort
    private int maxTruePeakLevel; //ushort
    private int maxMomentaryLoudness; //ushort
    private int maxShortTemLoudnes; //ushort
    private byte[] reversed;
    private String codingHistory;
    ///Ensure that this is in standard bext
    private Map<String, String> customFields;/// Keys: 1,2,C,CI,CL,CO,D,G,IN,LC,LD,N,Q,SR

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public String getOriginatorReferance() {
        return originatorReferance;
    }

    public void setOriginatorReferance(String originatorReferance) {
        this.originatorReferance = originatorReferance;
    }

    public String getOriginatorDate() {
        return originatorDate;
    }

    public void setOriginatorDate(String originatorDate) {
        this.originatorDate = originatorDate;
    }

    public String getOriginatorTime() {
        return originatorTime;
    }

    public void setOriginatorTime(String originatorTime) {
        this.originatorTime = originatorTime;
    }

    public long getTimeRefenceLow() {
        return timeRefenceLow;
    }

    public void setTimeRefenceLow(long timeRefenceLow) {
        this.timeRefenceLow = timeRefenceLow;
    }

    public long getTimeReferenceHigh() {
        return timeReferenceHigh;
    }

    public void setTimeReferenceHigh(long timeReferenceHigh) {
        this.timeReferenceHigh = timeReferenceHigh;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public byte getUMID_0() {
        return UMID_0;
    }

    public void setUMID_0(byte UMID_0) {
        this.UMID_0 = UMID_0;
    }

    public byte getUMID_63() {
        return UMID_63;
    }

    public void setUMID_63(byte UMID_63) {
        this.UMID_63 = UMID_63;
    }

    public int getLoundessValue() {
        return LoundessValue;
    }

    public void setLoundessValue(int loundessValue) {
        LoundessValue = loundessValue;
    }

    public int getLoudnessRange() {
        return loudnessRange;
    }

    public void setLoudnessRange(int loudnessRange) {
        this.loudnessRange = loudnessRange;
    }

    public int getMaxTruePeakLevel() {
        return maxTruePeakLevel;
    }

    public void setMaxTruePeakLevel(int maxTruePeakLevel) {
        this.maxTruePeakLevel = maxTruePeakLevel;
    }

    public int getMaxMomentaryLoudness() {
        return maxMomentaryLoudness;
    }

    public void setMaxMomentaryLoudness(int maxMomentaryLoudness) {
        this.maxMomentaryLoudness = maxMomentaryLoudness;
    }

    public int getMaxShortTemLoudnes() {
        return maxShortTemLoudnes;
    }

    public void setMaxShortTemLoudnes(int maxShortTemLoudnes) {
        this.maxShortTemLoudnes = maxShortTemLoudnes;
    }

    public byte[] getReversed() {
        return reversed;
    }

    public void setReversed(byte[] reversed) {
        this.reversed = reversed;
    }

    public String getCodingHistory() {
        return codingHistory;
    }

    public void setCodingHistory(String codingHistory) {
        this.codingHistory = codingHistory;
    }

    public Map<String, String> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(Map<String, String> customFields) {
        this.customFields = customFields;
    }
}
