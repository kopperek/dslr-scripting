package app.android.kopper.dslrscripting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by kopper on 2015-02-14.
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
public class ScriptFile {
    private final String path;
    private String desc;
    private String name;
    private String author;

    public ScriptFile(File file) throws IOException {
        this.path=file.getAbsolutePath();
        this.name=file.getName();
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        for(;;) {
            String line=br.readLine();
            if(line==null)
                break;
            if(line.startsWith("#name: "))
                name=line.substring("#name: ".length());
            if(line.startsWith("#desc: "))
                desc=line.substring("#desc: ".length());
            if(line.startsWith("#author: "))
                author=line.substring("#author: ".length());
        }
        br.close();
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getPath() {
        return path;
    }
}
