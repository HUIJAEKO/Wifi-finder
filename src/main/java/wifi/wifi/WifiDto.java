package wifi.wifi;

public class WifiDto {
    private String mgrNo;
    private String wrdofc;
    private String mainNm;
    private String adres1;
    private String adres2;
    private String instlFloor;
    private String instlTy;
    private String instlMby;
    private String svcSe;
    private String cmcwr;
    private String cnstcYear;
    private String inoutDoor;
    private String remars3;
    private double lat;
    private double lnt;
    private String workDttm;

    private double distance;

    public WifiDto(String mgrNo, String wrdofc, String mainNm, String adres1, String adres2, String instlFloor, String instlTy,
                    String instlMby, String svcSe, String cmcwr, String cnstcYear, String inoutDoor, String remars3, double lat, double lnt, String workDttm, double distance) {
        this.mgrNo = mgrNo;
        this.wrdofc = wrdofc;
        this.mainNm = mainNm;
        this.adres1 = adres1;
        this.adres2 = adres2;
        this.instlFloor = instlFloor;
        this.instlTy = instlTy;
        this.instlMby = instlMby;
        this.svcSe = svcSe;
        this.cmcwr = cmcwr;
        this.cnstcYear = cnstcYear;
        this.inoutDoor = inoutDoor;
        this.remars3 = remars3;
        this.lat = lat;
        this.lnt = lnt;
        this.workDttm = workDttm;
        this.distance = distance;
    }

    public String getMgrNo() { return mgrNo; }
    public String getWrdofc() { return wrdofc; }
    public String getMainNm() { return mainNm; }
    public String getAdres1() { return adres1; }
    public String getAdres2() { return adres2; }
    public String getInstlFloor() { return instlFloor; }
    public String getInstlTy() { return instlTy; }
    public String getInstlMby() { return instlMby; }
    public String getSvcSe() { return svcSe; }
    public String getCmcwr() { return cmcwr; }
    public String getCnstcYear() { return cnstcYear; }
    public String getInoutDoor() { return inoutDoor; }
    public String getRemars3() { return remars3; }
    public double getLat() { return lat; }
    public double getLnt() { return lnt; }
    public String getWorkDttm() { return workDttm; }
    public double getDistance() { return distance; }
}
