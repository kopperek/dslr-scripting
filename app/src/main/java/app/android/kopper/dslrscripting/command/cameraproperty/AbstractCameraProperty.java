package app.android.kopper.dslrscripting.command.cameraproperty;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import app.android.kopper.dslrscripting.command.GetCommand;
import app.android.kopper.dslrscripting.command.ICommand;
import app.android.kopper.dslrscripting.command.SetCommand;
import app.android.kopper.dslrscripting.command.converter.IConverter;
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
public abstract class AbstractCameraProperty {

    private final String propertyName;
    private final DeviceProp propertyField;
    private final boolean canRead;
    private final boolean canWrite;

    public AbstractCameraProperty(String propertyName,DeviceProp propertyField,boolean canRead,boolean canWrite) {
        this.propertyName=propertyName;
        this.propertyField=propertyField;
        this.canRead=canRead;
        this.canWrite=canWrite;
    }

    public abstract IConverter getConverter();

    public Collection<ICommand> getCommands() {
        List<ICommand> commands=new LinkedList<>();
        if(canRead)
            commands.add(new GetCommand("get"+propertyName,propertyField,getConverter()));
        if(canWrite)
            commands.add(new SetCommand("set"+propertyName,propertyField,getConverter()));
        return commands;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
