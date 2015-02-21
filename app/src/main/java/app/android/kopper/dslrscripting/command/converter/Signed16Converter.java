package app.android.kopper.dslrscripting.command.converter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import app.android.kopper.dslrscripting.R;

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
public class  Signed16Converter implements IArrayConverter {

    @Override
    public byte[] convertFromIntToArray(Integer value) {
        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(value.shortValue()).array();
    }

    @Override
    public Integer convertFromArrayToInt(byte[] value) throws ConvertException {
        if(value.length!=2)
            throw new ConvertException(R.string.error_array_size_2,value.length);
        return Integer.valueOf(ByteBuffer.wrap(value).order(ByteOrder.LITTLE_ENDIAN).getShort());
    }
}
