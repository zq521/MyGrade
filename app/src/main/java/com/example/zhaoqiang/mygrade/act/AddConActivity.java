package com.example.zhaoqiang.mygrade.act;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhaoqiang.mygrade.R;
import com.hyphenate.chat.EMClient;

/**
 * Created by 轩韩子 on 2017/3/24.
 * at 11:24
 */

public class AddConActivity extends AppCompatActivity {
    private EditText et_username;
    private Button btn_add;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_person);


        et_username = (EditText) this.findViewById(R.id.et_username);
        btn_add = (Button) this.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "请输入内容...", Toast.LENGTH_SHORT).show();
                    return;
                }
                addContact(username);
            }

        });

    }


    /**
     * 添加contact
     *
     * @param
     */


    public void addContact(final String usernamer) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在查找");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        new Thread(new Runnable() {
            public void run() {

                try {
                    EMClient.getInstance().contactManager().addContact(usernamer,"");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "未找到该联系人", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "输入有误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    public void back(View v) {
        finish();
    }
}
