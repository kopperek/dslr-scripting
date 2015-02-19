package app.android.kopper.dslrscripting;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;

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
public class SelectDirectoryActivity extends ListActivity {

    public static final String SELECTED_DIRECTORY="selected.directory";

    private DirListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_folder);
        listAdapter=new DirListAdapter(getApplicationContext());
        setListAdapter(listAdapter);
        File currentDir=(File)getLastNonConfigurationInstance();
        if(currentDir==null||!currentDir.exists()||!currentDir.isDirectory())
            currentDir=new File("/");
        setCurrentDir(currentDir);
        findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data=new Intent();
                data.putExtra(SELECTED_DIRECTORY,listAdapter.getCurrentDir());
                setResult(RESULT_OK,data);
                finish();
            }
        });
    }

    private void setCurrentDir(File dir) {
        listAdapter.setCurrentDir(dir);
        ((TextView)findViewById(R.id.current_dir)).setText(dir.getAbsolutePath());
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        return listAdapter.getCurrentDir();
    }

    @Override
    protected void onListItemClick(ListView l,View v,int position,long id) {
        File selectedFile=(File)listAdapter.getItem(position);
        if(selectedFile.isDirectory())
            setCurrentDir(selectedFile);
    }
}
