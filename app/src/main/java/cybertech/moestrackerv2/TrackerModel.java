package cybertech.moestrackerv2;

/**
 * Created by CyberTech on 1/23/2018.
 * stagen24@gmail.com
 */

public class TrackerModel {
    String trackerid;
    String crnno;
    String trackerno;

    public String getTrackerno() {
        return trackerno;
    }

    public void setTrackerno(String trackerno) {
        this.trackerno = trackerno;
    }

    public TrackerModel(String trackerid, String crnno, String trackerno) {
        this.trackerid = trackerid;
        this.crnno = crnno;
        this.trackerno = trackerno;
    }

    public TrackerModel() {
    }

    public String getTrackerid() {
        return trackerid;
    }

    public void setTrackerid(String trackerid) {
        this.trackerid = trackerid;
    }

    public String getCrnno() {
        return crnno;
    }

    public void setCrnno(String crnno) {
        this.crnno = crnno;
    }
}
