package ModelClass;

import Domain.UrlConfig;

/**
 * Created by wolfsoft5 on 18/9/18.
 */

public class WalletModelClass {

    String title;
    Integer icon;
    String icon_type;
    String percentage;
    Integer arrow;
    String price;
    int value;



    UrlConfig urlConfig;


    public WalletModelClass(String title, Integer icon, String icon_type, String percentage, Integer arrow, String price, int value,UrlConfig urlConfig) {
        this.title = title;
        this.icon = icon;
        this.icon_type = icon_type;
        this.percentage = percentage;
        this.arrow = arrow;
        this.price = price;
        this.value = value;
        this.urlConfig = urlConfig;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getIcon_type() {
        return icon_type;
    }

    public void setIcon_type(String icon_type) {
        this.icon_type = icon_type;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public Integer getArrow() {
        return arrow;
    }

    public void setArrow(Integer arrow) {
        this.arrow = arrow;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public UrlConfig getUrlConfig() {
        return urlConfig;
    }

    public void setUrlConfig(UrlConfig urlConfig) {
        this.urlConfig = urlConfig;
    }
}
