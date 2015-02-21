/*
 * Created by kopper on 21.02.15 11:46
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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import app.android.kopper.dslrscripting.util.LogUtil;

public class DirListAdapter extends BaseAdapter {

    private final Context context;

    private File dir;
    private File[] files=new File[]{};

    public DirListAdapter(Context context) {
        this.context=context;
    }

    public void setCurrentDir(File dir) {
        try {
            this.dir=dir;
            files=dir.listFiles();
            if(files==null)
                files=new File[]{};
            Arrays.sort(files,new Comparator<File>() {
                @Override
                public int compare(File lhs,File rhs) {
                    if(lhs.isDirectory()&&!rhs.isDirectory())
                        return(-1);
                    if(!lhs.isDirectory()&&rhs.isDirectory())
                        return(1);
                    return(lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase()));
                }
            });
            notifyDataSetChanged();
        } catch(Exception e) {
            LogUtil.e(e);
            files=new File[]{};
        }
    }

    @Override
    public int getCount() {
        return files.length+(dir!=null&&dir.getParentFile()!=null?1:0);
    }

    @Override
    public Object getItem(int position) {
        if(dir!=null&&dir.getParentFile()!=null)
            return(position==0?dir.getParentFile():files[position-1]);
        return files[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public File getCurrentDir() {
        return dir;
    }

    private static class DirListItem {

        private final String label;
        private final boolean isDirectory;

        public DirListItem(String label,boolean isDirectory) {
            this.label=label;
            this.isDirectory=isDirectory;
        }

        public String getLabel() {
            return label;
        }

        public boolean isDirectory() {
            return isDirectory;
        }
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.select_dir_list_row,parent,false);
        DirListItem label;
        if(dir!=null&&dir.getParentFile()!=null)
            label=new DirListItem(position==0?"..":files[position-1].getName(),position==0?true:files[position-1].isDirectory());
        else
            label=new DirListItem(files[position].getName(),files[position].isDirectory());
        ((TextView)rowView.findViewById(R.id.name)).setText(label.getLabel());
        ((ImageView)rowView.findViewById(R.id.icon)).setImageResource(label.isDirectory?R.drawable.ic_folder:R.drawable.ic_file);
        return rowView;
    }

    public Context getContext() {
        return context;
    }
}
