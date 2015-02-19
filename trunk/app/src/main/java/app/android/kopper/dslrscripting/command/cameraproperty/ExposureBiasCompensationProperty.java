package app.android.kopper.dslrscripting.command.cameraproperty;

import java.util.HashMap;
import java.util.Map;

import app.android.kopper.dslrscripting.command.converter.IConverter;
import app.android.kopper.dslrscripting.command.converter.Signed16Converter;
import app.android.kopper.dslrscripting.command.converter.StringConverter;
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
public class ExposureBiasCompensationProperty extends AbstractCameraProperty {

    private static Map<String,Integer> values=new HashMap<String,Integer>(){{
        put("+5",5000);
        put("+4",4000);
        put("+3",3000);
        put("+2",2000);
        put("+1",1000);
        put("0",0);
        put("-1",-1000);
        put("-2",-2000);
        put("-3",-3000);
        put("-4",-4000);
        put("-5",-5000);

        put("+4 2/3",4666);
        put("+4 1/3",4333);
        put("+3 2/3",3666);
        put("+3 1/3",3333);
        put("+2 2/3",2666);
        put("+2 1/3",2333);
        put("+1 2/3",1666);
        put("+1 1/3",1333);
        put("+2/3",666);
        put("+1/3",333);
        put("-1/3",-333);
        put("-2/3",-666);
        put("-1 1/3",-1333);
        put("-1 2/3",-1666);
        put("-2 1/3",-2333);
        put("-2 2/3",-2666);
        put("-3 1/3",-3333);
        put("-3 2/3",-3666);
        put("-4 1/3",-4333);
        put("-4 2/3",-4666);

        put("+4 1/2",4500);
        put("+3 1/2",3500);
        put("+2 1/2",2500);
        put("+1 1/2",1500);
        put("+1/2",500);
        put("-1/2",-500);
        put("-1 1/2",-1500);
        put("-2 1/2",-2500);
        put("-3 1/2",-3500);
        put("-4 1/2",-4500);
    }};

    public ExposureBiasCompensationProperty() {
        super("ExposureBiasCompensation",DeviceProp.EXPOSURE_BIAS_COMPENSATION,true,true);
    }

    @Override
    public IConverter getConverter() {
        return new StringConverter(new Signed16Converter(),values);
    }
}
