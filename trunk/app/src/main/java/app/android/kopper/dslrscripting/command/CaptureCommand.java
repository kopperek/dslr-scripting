package app.android.kopper.dslrscripting.command;

import java.util.ArrayList;
import java.util.concurrent.locks.LockSupport;

import app.android.kopper.dslrscripting.IWorkerUtil;
import app.android.kopper.dslrscripting.PureCommand;
import app.android.kopper.dslrscripting.R;
import app.android.kopper.dslrscripting.RException;
import app.android.kopper.dslrscripting.event.EventDispatcher;
import app.android.kopper.dslrscripting.event.IEventListener;

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
public class CaptureCommand extends AbstractCommand {

    boolean canGo=false;
    private Thread thread;

    public CaptureCommand() {
        super("capture");
    }

    @Override
    public Object execute(ArrayList params,IWorkerUtil util) throws RException {
        if(params.size()!=0)
            throw new RException(R.string.error_method_requires_no,getName());
        thread=Thread.currentThread();
        canGo=false;

        IEventListener listener=new IEventListener() {
            @Override
            public void notify(int eventCode,long eventParameter) {
                if(eventCode==0x400d) {
                    canGo=true;
                    LockSupport.unpark(thread);
                }
            }
        };
        EventDispatcher.addListener(listener);
        PureCommand command=new PureCommand(PureCommand.INITIATE_CAPTURE_REC_IN_MEDIA);
        command.addParam((long)0xFFFFFFFF);
        command.addParam((long)0x0000);
        util.log("capture()");
        util.cameraCommand(command);
        util.waitForNoBusy();
        while(!canGo) {
            util.isInterrupted();
            LockSupport.park(100);
        }
        EventDispatcher.removeListener(listener);
        return(null);
    }
}
