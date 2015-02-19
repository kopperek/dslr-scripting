package app.android.kopper.dslrscripting.event;

import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;

import java.util.AbstractCollection;
import java.util.LinkedHashSet;

import app.android.kopper.dslrscripting.ByteArray;
import app.android.kopper.dslrscripting.PureCommand;
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
public class EventDispatcher {

    private static EventDispatcher instance;

    private final UsbEndpoint inEndpoint;
    private final UsbEndpoint outEndpoint;
    private final UsbDeviceConnection connection;
    private final IEventExceptionListener excepitonListener;

    private boolean isRunning;

    private AbstractCollection<IEventListener> listeners=new LinkedHashSet<>();

    public static void init(UsbEndpoint inEndpoint,UsbEndpoint outEndpoint,UsbDeviceConnection connection,IEventExceptionListener exceptionListener) {
        EventDispatcher.instance=new EventDispatcher(inEndpoint,outEndpoint,connection,exceptionListener);
        EventDispatcher.instance.start();
    }

    public static void addListener(IEventListener listener) {
        EventDispatcher.instance._addListener(listener);
    }

    public static void removeListener(IEventListener listener) {
        EventDispatcher.instance._removeListener(listener);
    }


    private void _addListener(IEventListener listener) {
        synchronized(this) {
            this.listeners.add(listener);
        }
    }

    private void _removeListener(IEventListener listener) {
        synchronized(this) {
            this.listeners.remove(listener);
        }
    }

    private EventDispatcher(UsbEndpoint inEndpoint,UsbEndpoint outEndpoint,UsbDeviceConnection connection,IEventExceptionListener exceptionListener) {
        this.inEndpoint=inEndpoint;
        this.outEndpoint=outEndpoint;
        this.connection=connection;
        this.excepitonListener=exceptionListener;
    }


    private void start() {
        isRunning=true;
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try {
                    for(;isRunning;) {
                        PureCommand event=new PureCommand(PureCommand.GET_EVENT);
                        event.execute(inEndpoint,outEndpoint,connection);
                        ByteArray resultArray=event.getResultArray();
                        if(resultArray!=null) {
                            long events=resultArray.get(2);
                            for(int a=0;a<events;a++) {
                                int eventCode=(int)resultArray.get(2);
                                long eventParameter=resultArray.get(4);
                                //notify listeners
                                LinkedHashSet<IEventListener> listenersTmp=new LinkedHashSet<>();
                                synchronized(EventDispatcher.this) {
                                    listenersTmp.addAll(listeners);
                                }
                                for(IEventListener listener : listenersTmp) {
                                    listener.notify(eventCode,eventParameter);
                                }
                            }
                        }
                        Thread.sleep(200);
                    }
                } catch(Exception e) {
                    if(isRunning) {
                        LogUtil.e(e);
                        excepitonListener.exception(e);
                    }
                }
                LogUtil.i("EventDispatcher.done");
            }
        };
        new Thread(runnable).start();
    }

    public static void done() {
        EventDispatcher.instance._done();
    }

    private void _done() {
        LogUtil.i("EventDispatcher.almostDone");
        isRunning=false;
    }
}
