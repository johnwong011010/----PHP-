package com.example.test1;

public class booking_data {
    private String booking_date;
    private String booking_seat_id;
    private String user_name;
    private String booking_time;
    public booking_data(String booking_date,String booking_seat_id,String user_name,String booking_time){
        this.booking_date=booking_date;
        this.booking_seat_id=booking_seat_id;
        this.user_name=user_name;
        this.booking_time=booking_time;
    }
    public String getBooking_date(){
        return booking_date;
    }
    public String getBooking_seat_id(){
        return booking_seat_id;
    }
    public String getUser_name(){
        return user_name;
    }
    public String getBooking_time(){
        return booking_time;
    }
}
