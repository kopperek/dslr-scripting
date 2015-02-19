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
public class FlashModeProperty extends AbstractCameraProperty {

    private static Map<String,Integer> values=new HashMap<String,Integer>(){{
        put("Flash prohibited",0x0002);
        put("Red-eye reduction",0x0004 );
        put("Front curtain sync",0x8010 );
        put("Slow sync",0x8011 );
        put("Rear curtain sync",0x8012 );
        put("Red-eye reduction slow sync",0x8013 );
    }};

    public FlashModeProperty() {
        super("FlashMode",DeviceProp.FLASH_MODE,true,true);
    }

    @Override
    public IConverter getConverter() {
        return new StringConverter(new UnsignedConverter(2),values);
    }
}
