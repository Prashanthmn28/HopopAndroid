package com.hopop.hopop.payment.data;

public class ForBookId {
    public String user_id;
    public String user_mobile;
    public String from_route;
    public String to_route;
    public String route_id;
    public String trip_id;
    public String vechicle_num;
    public String total_seats;
    public String sys_time;

    public String amount_paid;
    public String getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(String amount_paid) {
        this.amount_paid = amount_paid;
    }


    public String getSys_time() {
        return sys_time;
    }

    public void setSys_time(String sys_time) {
        this.sys_time = sys_time;
    }

    public String getFrom_route() {
        return from_route;
    }

    public void setFrom_route(String from_route) {
        this.from_route = from_route;
    }

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getTo_route() {
        return to_route;
    }

    public void setTo_route(String to_route) {
        this.to_route = to_route;
    }

    public String getTotal_seats() {
        return total_seats;
    }

    public void setTotal_seats(String total_seats) {
        this.total_seats = total_seats;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getVechicle_num() {
        return vechicle_num;
    }

    public void setVechicle_num(String vechicle_num) {
        this.vechicle_num = vechicle_num;
    }
}
