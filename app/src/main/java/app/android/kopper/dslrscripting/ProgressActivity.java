package app.android.kopper.dslrscripting;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;

import app.android.kopper.dslrscripting.inputer.DoneInputer;
import app.android.kopper.dslrscripting.util.LogUtil;

/**
 * Created by kopper on 2015-02-08.
 * (C) Copyright 2015 kopperek@gmail.com
 * <p/>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 * <p/>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
public class ProgressActivity extends Activity {

    public static final String SCRIPT_PATH="script.path";

    private Worker worker;

    private VisualState visualState;

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    private PendingIntent mPermissionIntent;

    private BroadcastReceiver mUsbReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_progress);
            ProgressSavedState savedState=(ProgressSavedState)getLastNonConfigurationInstance();

            mPermissionIntent=PendingIntent.getBroadcast(this,0,new Intent(ACTION_USB_PERMISSION),0);
            IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
            mUsbReceiver=createBrodcastReceiver();
            registerReceiver(mUsbReceiver, filter);

            if(savedState!=null) {
                show(savedState.getVisualState());
                worker=savedState.getWorker();
                if(worker!=null)
                    worker.setActivity(this);
            } else {
                //go
                UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
                try {


                    UsbDevice validDevice = null;

                    HashMap<String,UsbDevice> deviceList = manager.getDeviceList();
                    Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
                    while(deviceIterator.hasNext()){
                        UsbDevice device = deviceIterator.next();
                        LogUtil.i(device.toString());
                        if(validDevice(device)) {
                            validDevice=device;
                            break;
                        }
                    }
                    if(validDevice!=null) {
                        manager.requestPermission(validDevice, mPermissionIntent);
                    } else {
                        show(new VisualState(new DoneInputer("No valid devices"),new String[]{}));
                    }
                } catch(Exception e) {
                    LogUtil.e(e);
                }
                LogUtil.i("done...");
            }
        } catch(Exception e) {
            LogUtil.e(e);
        }
        LogUtil.i("created");
    }

    private BroadcastReceiver createBrodcastReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context,Intent intent) {
                LogUtil.i("$$$ onReceive");
                String action = intent.getAction();
                //todo: deatach
                if (ACTION_USB_PERMISSION.equals(action)) {
                    synchronized (this) {
                        UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                            if(device != null){
                                //call method to set up device communication
                                LogUtil.d("permission gained for device "+device);
                                for(int a=0;a<device.getInterfaceCount();a++) {
                                    LogUtil.d("interface #"+a+": "+device.getInterface(a).toString());
                                    for(int b=0;b<device.getInterface(a).getEndpointCount();b++) {
                                        LogUtil.d("=>endpoint #"+b+": "+device.getInterface(a).getEndpoint(b).toString());
                                        LogUtil.d("=>dir: "+((device.getInterface(a).getEndpoint(b).getDirection()==UsbConstants.USB_DIR_OUT)?"OUT":"IN"));
                                    }
                                }
                                startScript(device);
                            }
                        }
                        else {
                            String errorMessage="Permission denied for device "+device;
                            LogUtil.d(errorMessage);
                            show(new VisualState(new DoneInputer(errorMessage),new String[]{}));
                        }
                    }
                }
            }
        };
    }

    private void startScript(UsbDevice device) {
        try {
            worker=new Worker(this);
            LogUtil.i("intent: "+getIntent());
            LogUtil.i("extras: "+getIntent().getExtras());
            LogUtil.i("path: "+getIntent().getExtras().getString(SCRIPT_PATH));
            worker.execute(new WorkerParams((UsbManager)getSystemService(Context.USB_SERVICE),device,getResources(),getIntent().getExtras().getString(SCRIPT_PATH)));
        } catch(Exception e) {
            LogUtil.e(e);
        }
    }

    public void show(final VisualState state) {
        this.visualState=state;
        LinearLayout buttonsLayout = (LinearLayout)findViewById(R.id.buttons_layout);
        buttonsLayout.removeAllViews();
        state.getInputer().updateLayout(this,buttonsLayout);
        LinearLayout logView=(LinearLayout)findViewById(R.id.log);
        logView.removeAllViews();
        for(String logItem:state.getLog()) {
            TextView logItemView=new TextView(getApplicationContext());
            logItemView.setText(logItem);
            logView.addView(logItemView);
        }
        ((ScrollView)findViewById(R.id.log_scroll)).fullScroll(View.FOCUS_DOWN);
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        return new ProgressSavedState(visualState,worker);
    }

    private boolean validDevice(UsbDevice device) {
        LogUtil.d("isValidDevice: "+device);
        return(device.getProductId()==1065&&device.getVendorId()==1200);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mUsbReceiver);
        if(worker!=null)
            worker.setActivity(null);
    }

    @Override
    public void onBackPressed() {
        if(worker!=null)
            worker.interrupt("Back pressed");
        super.onBackPressed();
    }

    public void interrupt() {
        if(worker!=null)
            worker.interrupt("Cancel pressed");
    }

    public void inputResult(Object inputtedResult) {
        if(worker!=null)
            worker.inputResult(inputtedResult);
    }
}
