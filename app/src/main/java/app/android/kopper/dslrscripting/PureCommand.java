package app.android.kopper.dslrscripting;

import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kopper on 2015-02-07.
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
public class PureCommand {

    public static final long GET_DEVICE_INFO=0x1001;
    public static final long OPEN_SESSION=0x1002;
    public static final long CLOSE_SESSION=0x1003;
    public static final long GET_STORAGE_IDS=0x1004;
    public static final long GET_STORAGE_INFO=0x1005;
    public static final long GET_NUM_OBJECTS=0x1006;
    public static final long GET_OBJECT_HANDLES=0x1007;
    public static final long GET_OBJECT_INFO=0x1008;
    public static final long GET_OBJECT=0x1009;
    public static final long GET_THUMB=0x100A;
    public static final long DELETE_OBJECT=0x100B;
    public static final long SEND_OBJECT_INFO=0x100C;
    public static final long SEND_OBJECT=0x100D;
    public static final long INITIATE_CAPTURE=0x100E;
    public static final long FORMAT_STORE=0x100F;
    public static final long GET_DEVICE_PROP_DESC=0x1014;
    public static final long GET_DEVICE_PROP_VALUE=0x1015;
    public static final long SET_DEVICE_PROP_VALUE=0x1016;
    public static final long GET_PARTIAL_OBJECT=0x101B;
    public static final long INITIATE_CAPTURE_REC_IN_SDRAM=0x90C0;
    public static final long AF_DRIVE=0x90C1;
    public static final long CHANGE_CAMERA_MODE=0x90C2;
    public static final long DELETE_IMAGES_IN_SDRAM=0x90C3;
    public static final long GET_LARGE_THUMB=0x90C4;
    public static final long GET_EVENT=0x90C7;
    public static final long DEVICE_READY=0x90C8;
    public static final long SET_PRE_WB_DATA=0x90C9;
    public static final long GET_VENDOR_PROP_CODES=0x90CA;
    public static final long AF_AND_CAPTURE_REC_IN_SDRAM=0x90CB;
    public static final long GET_PIC_CTRL_DATA=0x90CC;
    public static final long SET_PIC_CTRL_DATA=0x90CD;
    public static final long DELETE_CUSTOM_PIC_CTRL=0x90CE;
    public static final long GET_PIC_CTRL_CAPABILITY=0x90CF;
    public static final long START_LIVE_VIEW=0x9201;
    public static final long END_LIVE_VIEW=0x9202;
    public static final long GET_LIVE_VIEW_IMAGE=0x9203;
    public static final long MF_DRIVE=0x9204;
    public static final long CHANGE_AF_AREA=0x9205;
    public static final long AF_DRIVE_CANCEL=0x9206;
    public static final long INITIATE_CAPTURE_REC_IN_MEDIA=0x9207;
    public static final long GET_VENDOR_STORAGE_IDS=0x9209;
    public static final long START_MOVIE_REC_IN_CARD=0x920A;
    public static final long END_MOVIE_REC=0x920B;
    public static final long GET_OBJECT_PROPS_SUPPORTED=0x9801;
    public static final long GET_OBJECT_PROP_DESC=0x9802;
    public static final long GET_OBJECT_PROP_VALUE=0x9803;
    public static final long GET_OBJECT_PROP_LIST=0x9805;

    private final long code;

    private List<Long> params=new LinkedList<>();
    private static Long sessionId=Long.valueOf(0x00);
    private ByteArray resultArray;
    private byte[] data;

    private static Object SEMAPHORE="asdasdfsadf";

    public PureCommand(long code) {
        this.code=code;
    }

    public void addParam(Long param) {
        params.add(param);
    }

    private ByteArray createCommandArray() {
        int arraySize=12+params.size()*4;
        ByteArray commandArray=new ByteArray(arraySize);
        commandArray.put(arraySize,4); //size
        commandArray.put(1,2); //command type
        commandArray.put(code,2); //code
        commandArray.put(sessionId,4);
        for(int a=0;a<params.size();a++)
            commandArray.put(params.get(a),4);
        return(commandArray);
    }

    private ByteArray createDataArray() {
        if(data==null)
            return(null);
        int arraySize=12+data.length;
        ByteArray dataArray=new ByteArray(arraySize);
        dataArray.put(arraySize,4); //size
        dataArray.put(2,2); //data type
        dataArray.put(code,2); //code
        dataArray.put(sessionId,4);
        for(int a=0;a<data.length;a++)
            dataArray.put(data[a],1);
        return(dataArray);
    }

    public void execute(UsbEndpoint inEndpoint,UsbEndpoint outEndpoint,UsbDeviceConnection connection) throws Exception {
        synchronized(SEMAPHORE) {
            again:
            for(;;) {
                sessionId++;
                sendArray(outEndpoint,connection,createCommandArray());
                ByteArray dataArray=createDataArray();
                if(dataArray!=null)
                    sendArray(outEndpoint,connection,dataArray);

                byte[] data=new byte[inEndpoint.getMaxPacketSize()];
                loop:
                for(;;) {
                    ByteArrayOutputStream baos=new ByteArrayOutputStream();
                    long messageTotalSize=0;
                    long messageReceivedSize=0;
                    for(;;) {
                        int received=connection.bulkTransfer(inEndpoint,data,data.length,5000); //auto focus can take a while
                        if(received==0)
                            continue;
                        if(received>0) {
                            ByteArray resultArray=new ByteArray(Arrays.copyOf(data,received));
                            if(messageTotalSize==0)
                                messageTotalSize=resultArray.get(4);
                            baos.write(resultArray.getArray());
                            messageReceivedSize+=received;
                            if(messageReceivedSize==messageTotalSize)
                                break;
                        } else {
                            throw new IOException("No response");
                        }
                    }
                    ByteArray resultArray=new ByteArray(baos.toByteArray());
                    baos.close();
                    long sessionId=resultArray.get(8,4);
                    if(sessionId!=PureCommand.sessionId)
                        continue;
                    long blockType=resultArray.get(4,2);
                    if(messageTotalSize==12&&blockType==3) {
                        int responseCode=(int)resultArray.get(6,2);
                        if(responseCode!=0x2001)
                            throw new NoOKResponseCodeException(responseCode,resultArray);
                        break loop;
                    }
                    else
                        this.resultArray=new ByteArray(Arrays.copyOfRange(resultArray.getArray(),12,(int)messageTotalSize));
                }
                break;
            }
        }
    }

    private void sendArray(UsbEndpoint outEndpoint,UsbDeviceConnection connection,ByteArray byteArray) throws IOException {
        int a;
        for(a=0;a<3;a++) {
            int transfered=connection.bulkTransfer(outEndpoint,byteArray.getArray(),byteArray.getSize(),2000);
            if(transfered==byteArray.getSize())
                break;
        }
        if(a==3)
            throw new IOException("Can't send command");
    }

    public ByteArray getResultArray() {
        return resultArray;
    }

    public void setData(byte[] data) {
        this.data=data;
    }
}
