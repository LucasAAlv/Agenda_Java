package com.pucpr.agenda.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.pucpr.agenda.R;
import com.pucpr.agenda.model.Contact;
import com.pucpr.agenda.model.DataModel;

public class DetailActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText phoneEditText;

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        nameEditText = findViewById(R.id.nameEditTextText);
        phoneEditText = findViewById(R.id.phoneEditText);

        Bundle extra = getIntent().getExtras();
        index = extra.getInt("index");

        if (index != -1){
            Contact c = DataModel.getInstance().getContact(index);
            nameEditText.setText(c.getName());
            phoneEditText.setText(c.getPhone());
        }
    }

    public void onBackPressed() {

        String name = nameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        if (name.length() > 1 && phone.length() > 1) {
            if (index == -1){
                DataModel.getInstance().addContact(
                        new Contact(name, phone)
                );
            }else {
                Contact c = DataModel.getInstance().getContact(index);
                c.setName(name);
                c.setPhone(phone);
                DataModel.getInstance().updateContact(c,index);
            }

            finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
            builder.setTitle(R.string.attention);
            builder.setMessage(R.string.empty_contact_alert_msg);
            finish();
        }
        super.onBackPressed();
    }
}