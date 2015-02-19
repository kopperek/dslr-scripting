package app.android.kopper.dslrscripting.command.converter;

import app.android.kopper.dslrscripting.ByteArray;

/**
 * Created by kopper on 2015-02-14.
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
public class UnsignedConverter implements IArrayConverter {

    private final int bytes;

    public UnsignedConverter(int bytes) {
        this.bytes=bytes;
    }

    @Override
    public byte[] convertFromIntToArray(Integer value) {
        ByteArray array=new ByteArray(bytes);
        array.put(value.longValue(),bytes);
        return array.getArray();
    }

    @Override
    public Integer convertFromArrayToInt(byte[] value) throws ConvertException {
        if(value.length!=bytes)
            throw new ConvertException("Array size != "+bytes+" ("+value.length+") ");
        return Integer.valueOf((int)new ByteArray(value).get(bytes));
    }
}
