package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myproject.ATask.JoinInsert;
import com.example.myproject.ATask.LoginSelect;

import java.util.concurrent.ExecutionException;

import static com.example.myproject.Common.CommonMethod.loginDTO;

public class MainActivity extends AppCompatActivity {

    EditText etID, etPASSWD;
    Button btnLogin, btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDangerousPermissions();

        etID = findViewById(R.id.etId);
        etPASSWD = findViewById(R.id.etPASSWD);

        btnLogin = findViewById(R.id.btnLogin);
        btnJoin = findViewById(R.id.btnJoin);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                                                JoinActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etID.getText().toString().length() != 0 && etPASSWD.getText().toString().length() != 0){
                    String id = etID.getText().toString();
                    String passwd = etPASSWD.getText().toString();

                    LoginSelect loginSelect = new LoginSelect(id, passwd);
                    try {
                        loginSelect.execute().get();
                    } catch (ExecutionException e) {
                        e.getMessage();
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }

                    if(loginDTO != null){
                        Toast.makeText(MainActivity.this, "????????? ??????????????? !!!", Toast.LENGTH_SHORT).show();
                        Log.d("main:login", loginDTO.getId() + "??? ????????? ??????????????? !!!");

                        // ????????? ????????? ?????? ????????? ???????????? ??????????????? ?????????????????? ??????
                        if(loginDTO != null){
                            Intent intent = new Intent(getApplicationContext(), Sub1.class);
                            startActivity(intent);
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "???????????? ??????????????? ???????????? !!!", Toast.LENGTH_SHORT).show();
                        Log.d("main:login", "???????????? ??????????????? ???????????? !!!");
                        etID.setText(""); etPASSWD.setText("");
                        etID.requestFocus();
                    }

                }else {
                    Toast.makeText(MainActivity.this, "???????????? ????????? ?????? ???????????????", Toast.LENGTH_SHORT).show();
                    Log.d("main:login", "???????????? ????????? ?????? ??????????????? !!!");
                    return;
                }


            }
        });

    }

    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "?????? ??????", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "?????? ??????", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "?????? ?????? ?????????.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " ????????? ?????????.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " ????????? ???????????? ??????.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}