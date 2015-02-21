package app.android.kopper.dslrscripting.command.converter;

import app.android.kopper.dslrscripting.ByteArray;
import app.android.kopper.dslrscripting.R;

/**
 * Created by kopper on 2015-02-19.
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
public class FnumberConverter implements IConverter {
    @Override
    public byte[] convertToArray(Object value) throws ConvertException {
        if(value instanceof Number) {
            ByteArray result=new ByteArray(2);
            result.put((long)(((Number)value).doubleValue()*100),2);
            return result.getArray();
        }
        throw new ConvertException(R.string.error_parameter_is_not_valid,value);
    }

    @Override
    public Object convertFromArray(byte[] array) throws ConvertException {
        if(array.length!=2)
            throw new ConvertException(R.string.error_array_size_2,array.length);
        ByteArray result=new ByteArray(array);
        return result.get(2)/100.0;
    }
}
