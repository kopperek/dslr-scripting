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

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import app.android.kopper.dslrscripting.util.LogUtil;


public class MainActivity extends ListActivity {

    private static final int SCRIPTS_DIRECTORY_REQ_CODE=0x01;

    private static final String SCRIPTS_DIRECTORY_KEY="scripts.directory.key";

    private ScriptFileListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<ScriptFile> scripts=new ArrayList<ScriptFile>();
        String scriptsDirectory=getPreferences(MODE_PRIVATE).getString(SCRIPTS_DIRECTORY_KEY,null);
        if(scriptsDirectory!=null)
            scripts=loadScripts(new File(scriptsDirectory));

        myAdapter =new ScriptFileListAdapter(getApplicationContext());
        myAdapter.setValues(scripts);
        Integer lastSelection=(Integer)getLastNonConfigurationInstance();
        if(lastSelection!=null)
            myAdapter.setSelection(lastSelection);
        setListAdapter(myAdapter);

        findViewById(R.id.layout_main_start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selection=myAdapter.getSelection();
                if(selection==-1) {
                    Toast.makeText(getApplicationContext(),getString(R.string.error_select_script),Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Intent intent = new Intent(MainActivity.this, ProgressActivity.class);
                    Bundle b = new Bundle();
                    b.putString(ProgressActivity.SCRIPT_PATH,myAdapter.getItem(selection).getPath());
                    intent.putExtras(b);
                    startActivity(intent);
                } catch(Exception e) {
                    LogUtil.e(e);
                }
            }
        });
        updateStartButtonState();
    }

    private List<ScriptFile> loadScripts(File scriptingDirectory) {
        List<ScriptFile> scripts=new ArrayList<>();
        if(scriptingDirectory.exists()&&scriptingDirectory.isDirectory()) {
            for(File file:scriptingDirectory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir,String filename) {
                    return filename.toLowerCase().endsWith(".script");
                }
            }))
                try {
                    scripts.add(new ScriptFile(file));
                } catch(IOException e) {
                    LogUtil.e(e);
                }
        }

        Collections.sort(scripts,new Comparator<ScriptFile>() {
            @Override
            public int compare(ScriptFile lhs,ScriptFile rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        return(scripts);
    }

    @Override
    protected void onListItemClick(ListView l,View v,int position,long id) {
        myAdapter.setSelection(position);
        updateStartButtonState();
    }

    private void updateStartButtonState() {
        findViewById(R.id.layout_main_start_button).setEnabled(myAdapter.getSelection()!=-1);
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        Integer selection=myAdapter.getSelection();
        return selection;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id=item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==R.id.action_select_dir) {
            Intent intent = new Intent(MainActivity.this,SelectDirectoryActivity.class);
            startActivityForResult(intent,SCRIPTS_DIRECTORY_REQ_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        if(resultCode==RESULT_OK) {
            if(requestCode==SCRIPTS_DIRECTORY_REQ_CODE) {
                File selectedFile=(File)data.getExtras().get(SelectDirectoryActivity.SELECTED_DIRECTORY);
                SharedPreferences.Editor editor=getPreferences(MODE_PRIVATE).edit();
                editor.putString(SCRIPTS_DIRECTORY_KEY,selectedFile.getAbsolutePath());
                editor.commit();
                myAdapter.setValues(loadScripts(selectedFile));
                updateStartButtonState();
            }
        }
    }
}
