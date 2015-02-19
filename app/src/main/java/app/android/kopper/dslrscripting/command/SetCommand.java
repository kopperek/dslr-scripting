package app.android.kopper.dslrscripting.command;

import java.util.ArrayList;

import app.android.kopper.dslrscripting.IWorkerUtil;
import app.android.kopper.dslrscripting.PureCommand;
import app.android.kopper.dslrscripting.command.converter.IConverter;
import app.android.kopper.dslrscripting.constants.DeviceProp;

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
public class SetCommand extends AbstractCommand {

    private final DeviceProp property;
    private final IConverter converter;

    public SetCommand(String name,DeviceProp property,IConverter converter) {
        super(name);
        this.property=property;
        this.converter=converter;
    }

    @Override
    public Object execute(ArrayList params,IWorkerUtil util) throws Exception {
        if(params.size()!=1)
            throw new Exception(getName()+" requires one parameter");
        PureCommand propValueCommand=new PureCommand(PureCommand.SET_DEVICE_PROP_VALUE);
        propValueCommand.addParam(Long.valueOf(property.getCode()));
        Object value=params.get(0);
        propValueCommand.setData(converter.convertToArray(value));
        util.log(getName()+"("+value+")");
        util.cameraCommand(propValueCommand);
        return (null);
    }
}
