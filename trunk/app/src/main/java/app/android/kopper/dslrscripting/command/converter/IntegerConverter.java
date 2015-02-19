package app.android.kopper.dslrscripting.command.converter;

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
public class IntegerConverter implements IConverter<Integer> {

    private final IArrayConverter converter;

    public IntegerConverter(IArrayConverter converter) {
        this.converter=converter;
    }

    @Override
    public byte[] convertToArray(Object value) throws ConvertException {
        if (value instanceof Integer)
            return(converter.convertFromIntToArray((Integer)value));
        else
            throw new ConvertException("Unknown value for argument ("+value+")");
    }

    @Override
    public Integer convertFromArray(byte[] array) throws ConvertException {
        return converter.convertFromArrayToInt(array);
    }
}
