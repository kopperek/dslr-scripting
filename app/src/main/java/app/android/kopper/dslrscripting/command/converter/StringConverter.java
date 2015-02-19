package app.android.kopper.dslrscripting.command.converter;

import java.util.Map;

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
public class StringConverter implements IConverter<String> {

    private final IArrayConverter converter;
    private final Map<String,Integer> values;

    public StringConverter(IArrayConverter converter, Map<String,Integer> values) {
        this.converter=converter;
        this.values=values;
    }

    @Override
    public byte[] convertToArray(Object value) throws ConvertException {
        if(value instanceof String) {
            Integer val=values.get(value);
            if(val==null)
                throw new ConvertException("Unknown value for argument ("+value+")");
            return(converter.convertFromIntToArray(val));

        } else
            throw new ConvertException("Argument has to be string ("+value+")");
    }

    @Override
    public String convertFromArray(byte[] array) throws ConvertException {
        Integer value=converter.convertFromArrayToInt(array);
        for(String res:values.keySet())
            if(values.get(res).equals(value))
                return(res);
        throw new ConvertException("Unknown value ("+value+")");
    }
}
