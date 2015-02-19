package app.android.kopper.dslrscripting.command;

import java.util.ArrayList;

import app.android.kopper.dslrscripting.IWorkerUtil;
import app.android.kopper.dslrscripting.PureCommand;

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
public class PerformAFCommand extends AbstractCommand {

    public PerformAFCommand() {
        super("performAF");
    }

    @Override
    public Object execute(ArrayList params,IWorkerUtil util) throws Exception {
        PureCommand command=new PureCommand(PureCommand.AF_DRIVE);
        util.log("performAF()");
        util.cameraCommand(command);
        util.waitForNoBusy();
        return(null);
    }
}
