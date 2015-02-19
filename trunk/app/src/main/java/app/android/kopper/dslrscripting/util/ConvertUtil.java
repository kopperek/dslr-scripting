package app.android.kopper.dslrscripting.util;

import java.math.BigDecimal;
import java.util.Arrays;

import app.android.kopper.dslrscripting.command.converter.ConvertException;

/**
 * Created by kopper on 2015-02-08.
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
public class ConvertUtil {

    public static Integer toInteger(Object o) throws ConvertException {
        if(o==null)
            return (0);
        else if(o instanceof Number)
            return (((Number)o).intValue());
        else if(o instanceof String)
            return (new BigDecimal((String)o).intValue());
        throw new ConvertException("Can't convert "+o+" to int");
    }

    public static Boolean toBoolean(Object o) throws ConvertException  {
        if(o instanceof String) {
            String val=((String)o).toLowerCase().trim();
            return new Boolean(Arrays.asList("on","yes","true","1").contains(val));
        } else if (o instanceof Number) {
            int i=((Number)o).intValue();
            return new Boolean(i!=0);
        }
        throw new ConvertException("Can't convert "+o+" to boolean");

    }

    public static String toString(Object o) {
        if(o==null)
            return ("?");
        return(String.valueOf(o));
    }
}
