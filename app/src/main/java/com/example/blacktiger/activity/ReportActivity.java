package com.example.blacktiger.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blacktiger.AccountApplication;
import com.example.blacktiger.R;
import com.example.blacktiger.db.AccountDao;
import com.example.blacktiger.entity.AccountItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    EditText editTextBeginDate;
    EditText editTextEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        ImageButton buttonQuery = (ImageButton)this.findViewById(R.id.imageButtonQuery);
        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query();
            }
        });

        editTextBeginDate =(EditText)this.findViewById(R.id.editTextBeginDate);
        editTextEndDate =(EditText)this.findViewById(R.id.editTextEndDate);

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        editTextBeginDate.setText(sdf1.format(new Date())+"-01-01");
        editTextEndDate.setText(sdf2.format(new Date()));
    }

    //查询数据
    private void query() {
        RadioGroup radioGroup = (RadioGroup)this.findViewById(R.id.radioGroupType);
        boolean isIncome = radioGroup.getCheckedRadioButtonId() == R.id.radioButtonIncome;

        String beginDate = editTextBeginDate.getText().toString();
        String endDate = editTextEndDate.getText().toString();

        AccountApplication app = (AccountApplication)(this.getApplication());
        AccountDao dbManager = app.getDatabaseManager();
        List<AccountItem> accountList = null;
        if (radioGroup.getCheckedRadioButtonId() == R.id.radioButtonIncome){
            accountList = dbManager.queryIncomeList(beginDate,endDate);
        }else{
            accountList = dbManager.queryOutlayList(beginDate,endDate);
        };

        ListView listView = (ListView)this.findViewById(R.id.listView1);
        ArrayAdapter<AccountItem> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,accountList);
        listView.setAdapter(arrayAdapter);
    }
}
