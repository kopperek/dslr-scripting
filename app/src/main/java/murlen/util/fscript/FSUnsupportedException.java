package murlen.util.fscript;

import java.util.HashMap;


/**
 * <p>FSUnsupportedException - Exception rasied to indicate that a particular
 * method/variable is not implemented by extensions</p>
 * <p>
 * <I>Copyright (C) 2000 murlen.</I></p>
 * <p>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.</p>
 * <p>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.</p>
 *
 * <p>You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the Free
 * Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA </p>
 *
 * @author murlen
 */

public class FSUnsupportedException extends FSException{

	public FSUnsupportedException(String message, int lineNo, String line, LexAnn tok, HashMap vars, HashMap gVars) {
		super(message, lineNo, line, tok, vars, gVars);
	}

    @Override
    public String getMessage() {
        return "Unknown variable/function: " + message  +"\nline:" + lineNo+ "\n" + line ;
    }

    @Override
	public String toString() {
		return "Unknown variable/function: " + message  + " (line:" + lineNo+ " [" + line + "])";
	}
}
