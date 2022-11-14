package com.example.test1;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    private static final String get_booking_news = "";//fill url which get the booking data
    List<booking_data> data;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras(); //接收傳遞過來的資料
        String search_date = bundle.getString("search_date");
        int search_people_num = bundle.getInt("search_people_num");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_booking_data);
        setToolBar();

        recyclerView = findViewById(R.id.show_data);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        data = new ArrayList<>();
        loadBooking_news();
    }

    private void setToolBar(){ //the setting of ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("main title");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitle("sub title");
        toolbar.setSubtitleTextColor(Color.WHITE);
    }
    @Override
    /*新增菜單 */
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(0,0,0,"最新消息").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0,1,1,"關於我們").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0,2,2,"線上訂位").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0,3,3,"登入").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menu.add(0,4,4,"登出").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onCreateOptionsMenu(menu);
    }
    /*菜單點擊事件 */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(this,"hello", LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "關於我們", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(this, "線上訂位", LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "會員登入", LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this, "會員登出", LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadBooking_news(){ //向DB發送請求
        StringRequest stringRequest = new StringRequest(Request.Method.GET, get_booking_news, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    if (response=="[]"){ //當找不到符合的結果時提示使用者沒有對應的結果
                        showErrorDialog();
                    }
                    JSONArray array = new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject object =array.getJSONObject(i);
                        data.add(new booking_data(object.getString("booking_date"),
                                object.getString("booking_seat_id"),
                                object.getString("user_name"),
                                object.getString("booking_time")));
                    }
                    Booking_data_Adapter adapter = new Booking_data_Adapter(SecondActivity.this,data);
                    recyclerView.setAdapter(adapter); //顯示結果
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(stringRequest); //發送請求
    }

    private void showErrorDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("找不到結果");
        builder.setMessage("目前並沒有符合條件的結果!");
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent = new Intent();
                intent.setClass(SecondActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        builder.create();
    }
}