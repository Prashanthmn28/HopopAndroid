package com.hopop.hopop.sidenavigation.suggestedroute.data;

public class ForSuggestedRoute {
    public String mobile_number;
    public String from_route;
    public String to_route;
    public String requeste_on;


    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }


    public String getRequeste_on(String systime) {
        return requeste_on;
    }

    public void setRequeste_on(String requeste_on) {
        this.requeste_on = requeste_on;
    }

    public String getTo_route() {
        return to_route;
    }

    public void setTo_route(String to_route) {
        this.to_route = to_route;
    }

    public String getFrom_route() {
        return from_route;
    }

    public void setFrom_route(String from_route) {
        this.from_route = from_route;
    }


}
