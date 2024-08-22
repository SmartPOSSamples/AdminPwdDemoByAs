package com.cloudpos.test.activity;

import com.cloudpos.util.TextViewUtil;
import com.cloudpos.test.aidl.control.IAIDLListener;
import com.cloudpos.test.aidl.control.ModifyAdminPwdController;
import com.wizarpos.wizarviewagentassistant.aidl.IModifyAdminPwdService;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ConstantActivity implements OnClickListener, IAIDLListener {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_activity_main);

        Button btn_run3 = (Button) this.findViewById(R.id.btn_run3);
        Button btn_run4 = (Button) this.findViewById(R.id.btn_run4);
        Button btn_run5 = (Button) this.findViewById(R.id.btn_run5);
        log_text = (TextView) this.findViewById(R.id.text_result);
        log_text.setMovementMethod(ScrollingMovementMethod.getInstance());

        findViewById(R.id.settings).setOnClickListener(this);
        btn_run3.setOnClickListener(this);
        btn_run4.setOnClickListener(this);
        btn_run5.setOnClickListener(this);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == DEFAULT_LOG) {
                    log_text.append("\t" + msg.obj + "\n");
                } else if (msg.what == SUCCESS_LOG) {
                    String str = "\t" + msg.obj + "\n";
                    TextViewUtil.infoBlueTextView(log_text, str);
                } else if (msg.what == FAILED_LOG) {
                    String str = "\t" + msg.obj + "\n";
                    TextViewUtil.infoRedTextView(log_text, str);
                } else if (msg.what == CLEAR_LOG) {
                    log_text.setText("");
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View arg0) {
        int index = arg0.getId();
        if (index == R.id.btn_run3) {
            ModifyAdminPwdController.getInstance().startConnectService(this, this, 1);
        } else if (index == R.id.btn_run4) {
            ModifyAdminPwdController.getInstance().startConnectService(this, this, 2);
        } else if (index == R.id.btn_run5) {
            ModifyAdminPwdController.getInstance().startConnectService(this, this, 3);
        } else if (index == R.id.settings) {
            log_text.setText("");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onDestory() {
        super.onDestroy();
    }

    private static final String DEFAULT_PWD = "99999999";

    @Override
    public synchronized void serviceConnected(Object objService, ServiceConnection connection, int cmd) {
        try {
            if (objService instanceof IModifyAdminPwdService) {
                IModifyAdminPwdService modifyService = (IModifyAdminPwdService) objService;
                if (cmd == 1) {
                    boolean isAdminPwd = modifyService.isAdminPwd(DEFAULT_PWD);
                    writerInSuccessLog("veryfy admin pwd " + (isAdminPwd ? "Success" : "failed"));
                } else if (cmd == 2) {
                    boolean isModifySuccess = modifyService.modifyAdminPwd(DEFAULT_PWD, "123");
                    writerInSuccessLog("modify admin pwd " + (isModifySuccess ? "Success" : "failed"));
                } else if (cmd == 3) {
                    boolean isModifySuccess = modifyService.forceModifyAdminPwd(DEFAULT_PWD);
                    writerInSuccessLog("force modify admin pwd " + (isModifySuccess ? "Success" : "failed"));
                }
//				boolean isResetSuccess = modifyService.reset(DEFAULT_PWD);
//				writerInSuccessLog("Reset admin " + (isResetSuccess?"Success": "failed"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                this.unbindService(connection);
            }
        }

    }

}
