package app.android.kopper.dslrscripting.command.converter;

import java.util.Arrays;

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
public class BooleanConverter implements IConverter<Integer> {

    private final IArrayConverter converter;

    public BooleanConverter(IArrayConverter converter) {
        this.converter=converter;
    }

    @Override
    public byte[] convertToArray(Object value) throws ConvertException {
        Integer val;
        if(value instanceof String)
            val=Arrays.asList("true","on","yes","ok","1").contains(((String)value).toLowerCase().trim())?1:0;
        else if (value instanceof Integer)
            val=((Integer)value).intValue()!=0?1:0;
        else
            throw new ConvertException("Unknown value for argument ("+value+")");
        return(converter.convertFromIntToArray(val));
    }

    @Override
    public Integer convertFromArray(byte[] array) throws ConvertException {
        return converter.convertFromArrayToInt(array);
    }
}
