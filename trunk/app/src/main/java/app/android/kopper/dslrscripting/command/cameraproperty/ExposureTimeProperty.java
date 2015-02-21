package app.android.kopper.dslrscripting.command.cameraproperty;

import java.util.Arrays;

import app.android.kopper.dslrscripting.ByteArray;
import app.android.kopper.dslrscripting.R;
import app.android.kopper.dslrscripting.command.converter.ConvertException;
import app.android.kopper.dslrscripting.command.converter.IConverter;
import app.android.kopper.dslrscripting.constants.DeviceProp;
import app.android.kopper.dslrscripting.util.LogUtil;

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
public class ExposureTimeProperty extends AbstractCameraProperty {

    public ExposureTimeProperty() {
        super("ExposureTime",DeviceProp.EXPOSURE_TIME,true,true);
    }

    @Override
    public IConverter getConverter() {
        return new IConverter() {
            @Override
            public byte[] convertToArray(Object value) throws ConvertException {
                if(value instanceof Double) {
                    if(value.equals(Double.valueOf(0.0)))
                        return (getBulbArray());
                    ByteArray result=new ByteArray(4);
                    result.put((long)((Double)value*10000),4);
                    return result.getArray();
                }
                throw new ConvertException(R.string.error_parameter_is_not_valid,value);
            }

            @Override
            public Object convertFromArray(byte[] array) throws ConvertException {
                if(array.length!=4)
                    throw new ConvertException(R.string.error_array_size_4,array.length);
                if(Arrays.equals(array,getBulbArray()))
                    return(0.0);
                ByteArray result=new ByteArray(array);
                return result.get(4)/10000.0;
            }
        };
    }

    private static byte[] getBulbArray() {
        return new byte[] {(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
    }
}
