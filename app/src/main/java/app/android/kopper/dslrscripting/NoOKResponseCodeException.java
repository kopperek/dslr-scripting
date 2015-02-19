package app.android.kopper.dslrscripting;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kopper on 2015-02-15.
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
public class NoOKResponseCodeException extends Exception {
    private final int responseCode;
    private final ByteArray resultArray;

    private static final Map<Integer,String> codes=new HashMap<>();
    static {
        codes.put(0x2001,"OK"); // :)
        codes.put(0x2002,"General_Error");
        codes.put(0x2003,"Session_Not_Open");
        codes.put(0x2004,"Invalid_TransactionID");
        codes.put(0x2005,"Operation_Not_Supported");
        codes.put(0x2006,"Parameter_Not_Supported");
        codes.put(0x2007,"Incomplete_Transfer");
        codes.put(0x2008,"Invalid_StorageID");
        codes.put(0x2009,"Invalid_Object_Handle");
        codes.put(0x200A,"DeviceProp_Not_Supported");
        codes.put(0x200B,"Invalid_ObjectFormatCode");
        codes.put(0x200C,"Store_Full");
        codes.put(0x200D,"Object_Write_Protect");
        codes.put(0x200E,"Store_Read_Only");
        codes.put(0x200F,"Access_Denied");
        codes.put(0x2010,"No_Thumbnail_Present");
        codes.put(0x2012,"Partial_Deletion");
        codes.put(0x2013,"Store_Not_Available");
        codes.put(0x2014,"Specification_By_Format_Unsupported");
        codes.put(0x2015,"No_Valid_ObjectInfo");
        codes.put(0x2019,"Device_Busy");
        codes.put(0x201A,"Invalid_Parent_Object");
        codes.put(0x201B,"Invalid_DeviceProp_Format");
        codes.put(0x201C,"Invalid_DeviceProp_Value");
        codes.put(0x201D,"Invalid_Parameter");
        codes.put(0x201E,"Session_Already_Open");
        codes.put(0x2020,"Specification_of_Destination_Unsupported");
        codes.put(0xA001,"Hardware_Error");
        codes.put(0xA002,"Out_of_Focus");
        codes.put(0xA003,"Change_CameraMode_Failed");
        codes.put(0xA004,"Invalid_Status");
        codes.put(0xA005,"Set_Property_Not_Support");
        codes.put(0xA006,"Wb_Preset_Error");
        codes.put(0xA007,"Dust_Reference_Error");
        codes.put(0xA008,"Shutter_Speed_Bulb");
        codes.put(0xA009,"MirrorUp_Sequence");
        codes.put(0xA00A,"CameraMode_Not_Adjust_Fnumber");
        codes.put(0xA00B,"Not_LiveView");
        codes.put(0xA00C,"MfDrive_Step_End");
        codes.put(0xA00E,"MfDrive_Step_ Insufficiency");
        codes.put(0xA021,"Store_Error");
        codes.put(0xA022,"Store_Unformatted");
        codes.put(0xA801,"Invalid_ObjectPropCode");
        codes.put(0xA802,"Invalid_ObjectProp_Format");
    }

    public NoOKResponseCodeException(int responseCode,ByteArray resultArray) {
        super(responseCode+": "+resultArray.toString());
        this.responseCode=responseCode;
        this.resultArray=resultArray;
    }

    public long getResponseCode() {
        return responseCode;
    }

    public ByteArray getResultArray() {
        return resultArray;
    }

    @Override
    public String getMessage() {
        String errorMessage=codes.get(responseCode);
        return errorMessage==null?super.getMessage():errorMessage;
    }
}
