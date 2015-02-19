package app.android.kopper.dslrscripting.util;

import android.util.Log;

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
public class LogUtil {
    private static final String LOG_TAG="DslrScripting.log";

    public static void i(String s) {
        Log.i(LOG_TAG,s);
    }

    public static void d(String s) {
        Log.d(LOG_TAG,s);
    }

    public static void e(Throwable e) {
        e("",e);
    }

    public static void e(String s, Throwable e) {
        Log.e(LOG_TAG,s,e);
    }
}
