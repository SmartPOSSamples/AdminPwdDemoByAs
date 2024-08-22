package com.cloudpos.test.aidl.control;

import com.wizarpos.wizarviewagentassistant.aidl.IModifyAdminPwdService;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class ModifyAdminPwdController {


    private static final String TAG = "modifyService";

    private static int cmd = 1;

    private IModifyAdminPwdService modifyService;
    private ModifyAdminPwdServiceConnection connection = null;
    private static ModifyAdminPwdController instance;

    public static ModifyAdminPwdController getInstance() {
        if (instance == null) {
            instance = new ModifyAdminPwdController();
        }
        return instance;
    }

    private ModifyAdminPwdController() {
        Log.d(TAG, "create ModifyAdminPwdController");
    }


    private IAIDLListener aidlListener;

    private class ModifyAdminPwdServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected begin ");
            modifyService = IModifyAdminPwdService.Stub.asInterface(service);
            aidlListener.serviceConnected(modifyService, connection, cmd);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            modifyService = null;
        }
    }

    public boolean startConnectService(Context mContext, IAIDLListener aidlListener, int cmd) {
        this.aidlListener = aidlListener;
        this.cmd = cmd;
        Intent intent = new Intent();
        ComponentName comp = new ComponentName(
                "com.wizarpos.wizarviewagentassistant",
                "com.wizarpos.wizarviewagentassistant.AdminPwdMainService");
        intent.setComponent(comp);
        connection = new ModifyAdminPwdServiceConnection();
        mContext.startService(intent);
        boolean isSuccess = mContext.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "invoke ModifyAdminPwdController start service method! isSuccess = " + isSuccess);
        return isSuccess;
    }
}
