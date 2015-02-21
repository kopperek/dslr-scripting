package app.android.kopper.dslrscripting.command;

import java.util.ArrayList;

import app.android.kopper.dslrscripting.IWorkerUtil;
import app.android.kopper.dslrscripting.R;
import app.android.kopper.dslrscripting.RException;
import app.android.kopper.dslrscripting.util.ConvertUtil;

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
public class LogCommand extends AbstractCommand {

    public LogCommand() {
        super("log");
    }

    @Override
    public Object execute(ArrayList params,IWorkerUtil util) throws RException {
        if(params.size()!=1)
            throw new RException(R.string.error_method_requires_one,getName());
        String message=ConvertUtil.toString(params.get(0));
        util.log(message);
        return null;
    }
}
