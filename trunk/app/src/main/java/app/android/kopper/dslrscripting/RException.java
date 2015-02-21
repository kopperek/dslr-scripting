/*
 * Created by kopper on 21.02.15 11:50
 * (C) Copyright 2015 kopperek@gmail.com
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */

package app.android.kopper.dslrscripting;

public class RException extends Exception {

    private final int key;
    private final Object[] params;

    public RException(int key, Object ... params) {
        this.key=key;
        this.params=params;
    }

    public int getKey() {
        return key;
    }

    public Object[] getParams() {
        return params;
    }
}
