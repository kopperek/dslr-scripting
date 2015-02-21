package app.android.kopper.dslrscripting;

import android.content.res.Resources;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

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
public class WorkerParams {

    private final UsbDevice device;
    private final UsbManager usbManager;
    private final Resources resources;
    private final String selectedScript;


    public WorkerParams(UsbManager usbManager, UsbDevice device,Resources resources, String selectedScript) {
        this.usbManager=usbManager;
        this.device=device;
        this.resources=resources;
        this.selectedScript=selectedScript;
    }

    public UsbDevice getDevice() {
        return device;
    }

    public UsbManager getUsbManager() {
        return usbManager;
    }

    public Resources getResources() {
        return resources;
    }

    public String getSelectedScript() {
        return selectedScript;
    }
}
