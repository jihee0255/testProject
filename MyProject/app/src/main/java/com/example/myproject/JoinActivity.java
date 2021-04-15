package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myproject.ATask.JoinInsert;

import java.util.concurrent.ExecutionException;

public class JoinActivity extends AppCompatActivity {
    private static final String TAG = "main:JoinActivity";

    String state;

    EditText etId, etPasswd, etName, etPhoneNum, etAddress;
    Button btnJoin, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        etId = findViewById(R.id.etId);
        etPasswd = findViewById(R.id.etPasswd);
        etName = findViewById(R.id.etName);
        etPhoneNum = findViewById(R.id.etPhoneNum);
        etAddress = findViewById(R.id.etAddress);
        btnJoin = findViewById(R.id.btnJoin);
        btnCancel = findViewById(R.id.btnCancel);

        // 서버로 edittext에 써진 내용을 담아서 서버통신을 한다
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etId.getText().toString();
                String passwd = etPasswd.getText().toString();
                String name = etName.getText().toString();
                String phonenumber =  etPhoneNum.getText().toString();
                String address = etAddress.getText().toString();

                // 서버통신을 위한 AsyncTask를 생성해서 데이터 보낸다
                JoinInsert joinInsert = new
                        JoinInsert(id, passwd, name, phonenumber, address);
                try {
                    // joinInsert가 끝나고 넘긴 String(state)을 받는다.
                    state = joinInsert.execute().get().trim();
                    Log.d(TAG, "onClick: state => " + state );

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(state.equals("1")){
                    Toast.makeText(JoinActivity.this, "회원가입을 축하합니다 !!!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "회원가입을 축하합니다 !!!");
                    finish();
                }else{
                    Toast.makeText(JoinActivity.this, "다시 회원가입을 해주세요 !!!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "다시 회원가입을 해주세요");
                    finish();
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}