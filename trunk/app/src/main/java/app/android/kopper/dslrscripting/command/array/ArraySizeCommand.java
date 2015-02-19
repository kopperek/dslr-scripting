package app.android.kopper.dslrscripting.command.array;

import java.util.ArrayList;
import java.util.List;

import app.android.kopper.dslrscripting.IWorkerUtil;
import app.android.kopper.dslrscripting.command.AbstractCommand;

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
public class ArraySizeCommand extends AbstractCommand {
    public ArraySizeCommand() {
        super("arraySize");
    }

    @Override
    public Object execute(ArrayList params,IWorkerUtil util) throws Exception {
        if(params.size()!=1)
            throw new Exception(getName()+" requires one parameter");
        Object obj=params.get(0);
        if(obj instanceof List)
            return(((List)obj).size());
        throw new Exception("Parameter has to be an array");
    }
}
