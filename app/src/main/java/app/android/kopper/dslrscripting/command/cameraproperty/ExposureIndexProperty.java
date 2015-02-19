package app.android.kopper.dslrscripting.command.cameraproperty;

import java.util.HashMap;
import java.util.Map;

import app.android.kopper.dslrscripting.command.converter.IConverter;
import app.android.kopper.dslrscripting.command.converter.StringConverter;
import app.android.kopper.dslrscripting.command.converter.UnsignedConverter;
import app.android.kopper.dslrscripting.constants.DeviceProp;

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
public class ExposureIndexProperty extends AbstractCameraProperty {

    private static Map<String,Integer> values=new HashMap<String,Integer>(){{
        put("100",0x0064);
        put("125",0x007D);
        put("160",0x00A0);
        put("200",0x00C8);
        put("250",0x00FA);
        put("320",0x0140);
        put("400",0x0190);
        put("500",0x01F4);
        put("640",0x0280);
        put("800",0x0320);
        put("1000",0x03E8);
        put("1250",0x04E2);
        put("1600",0x0640);
        put("2000",0x07D0);
        put("2500",0x09C4);
        put("3200",0x0C80);
        put("4000",0x0FA0);
        put("5000",0x1388);
        put("6400",0x1900);
        put("Hi 0.3",0x1F40);
        put("Hi 0.7",0x2710);
        put("Hi 1",0x3200);
        put("Hi 2",0x6400);
    }};

    public ExposureIndexProperty() {
        super("ExposureIndex",DeviceProp.EXPOSURE_INDEX,true,true);
    }

    @Override
    public IConverter getConverter() {
        return new StringConverter(new UnsignedConverter(2),values);
    }
}
