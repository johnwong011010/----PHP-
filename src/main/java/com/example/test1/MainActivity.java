package com.example.test1;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView tvDate;
    EditText etDate;
    Button search;
    TextView test_time;
    DatePickerDialog.OnDateSetListener setListener;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDate = findViewById(R.id.textView);
        etDate = findViewById(R.id.editTextDate);
        spinner = (Spinner) findViewById(R.id.spinner2);
        search = findViewById(R.id.button);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.people_number, android.R.layout.simple_spinner_item); //set the adapter of a spinner menu
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //get the appearance of spinner menu
        spinner.setAdapter(adapter); // set adapter to my spinner menu
        spinner.setSelection(0, false); //set the default selected item to spinner menu
        setToolBar();//set the toolbar
        spinner.setOnItemSelectedListener(spinnerItemis_Selected); // the event of spinner menu item is selected


        etDate.setOnClickListener(new View.OnClickListener() { // while a user try to select a date
            @Override
            public void onClick(View view) { // use datePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) { //當在選擇好日期後
                        datePicker.setMinDate(System.currentTimeMillis());
                        month +=1; // default range is 0-11, so we have to +1 to ensure is 1-12
                        String date = year+"/"+month+"/"+day; // make a date string
                        etDate.setText(date); // let a editText become what user select
                    }
                },year,month,day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());//設定查詢今天以後的時間
                datePickerDialog.show();
            }
        });

        search.setOnClickListener(new View.OnClickListener() { //transfer date and people number to secondActivity
            @Override
            public void onClick(View view) {
                String search_date = etDate.getText().toString().trim();
                int search_people_num = spinner.getSelectedItemPosition();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,SecondActivity.class);
                Bundle bundle = new Bundle(); //用bundle來傳遞資料
                bundle.putString("search_date",search_date);
                bundle.putInt("search_people_num",search_people_num);
                intent.putExtras(bundle);
                startActivity(intent);//切換頁面
            } //
        });
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

    private AdapterView.OnItemSelectedListener spinnerItemis_Selected = new AdapterView.OnItemSelectedListener() { //spinner item selected
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long time) {
            String spinner_pos = String.valueOf(pos); //get item pos
            String people_num = adapterView.getItemAtPosition(pos).toString(); //get the people which user selected
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };
}
