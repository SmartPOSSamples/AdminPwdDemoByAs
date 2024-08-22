package com.cloudpos.test.aidl.control;

import android.content.ServiceConnection;

public interface IAIDLListener {

    public static final int STATE_UNKNOW = -1;

    //	onServiceConnected
    void serviceConnected(Object objService, ServiceConnection connection, int cmd);

}
