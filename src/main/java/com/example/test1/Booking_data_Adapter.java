package com.example.test1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Booking_data_Adapter extends RecyclerView.Adapter<Booking_data_Adapter.bookingViewHolder> {
    private Context context;
    private List<booking_data> booking_dataList;

    public Booking_data_Adapter(Context context,List<booking_data> booking_dataList){
        this.context=context;
        this.booking_dataList = booking_dataList;
    }

    @Override
    public bookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.booking_data,null);
        return new bookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(bookingViewHolder holder, int position) {
        booking_data data = booking_dataList.get(position);

        holder.text_date.setText(data.getBooking_date());
        holder.text_seat_id.setText(data.getBooking_seat_id());
        holder.text_user_name.setText(data.getUser_name());
        holder.text_time.setText(data.getBooking_time());
    }

    @Override
    public int getItemCount() {
        return booking_dataList.size();
    }

    class bookingViewHolder extends RecyclerView.ViewHolder{
        TextView text_date,text_seat_id,text_user_name,text_time;

        public bookingViewHolder(@NonNull View itemView) {
            super(itemView);

            text_date = itemView.findViewById(R.id.data_title);
            text_seat_id = itemView.findViewById(R.id.data_table_num);
            text_user_name = itemView.findViewById(R.id.data_who);
            text_time = itemView.findViewById(R.id.data_time);
        }
    }
}