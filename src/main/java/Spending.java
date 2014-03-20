import java.io.Serializable;

/**
 * Created by Nidhin on 3/19/2014.
 */
public class Spending implements Serializable {


    private String code;
    private String item;
    private String group;
    private String region_number;
    private String region_name;
    private String state_name;
    private double y2000;
    private double y2009;
    private double average_annual_percent_growth;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getRegion_number() {
        return region_number;
    }

    public void setRegion_number(String region_number) {
        this.region_number = region_number;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public double getY2000() {
        return y2000;
    }

    public void setY2000(double y2000) {
        this.y2000 = y2000;
    }

    public double getY2009() {
        return y2009;
    }

    public void setY2009(double y2009) {
        this.y2009 = y2009;
    }

    public double getAverage_annual_percent_growth() {
        return average_annual_percent_growth;
    }

    public void setAverage_annual_percent_growth(double average_annual_percent_growth) {
        this.average_annual_percent_growth = average_annual_percent_growth;
    }
}
