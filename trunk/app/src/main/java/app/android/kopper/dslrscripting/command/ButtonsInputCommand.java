package app.android.kopper.dslrscripting.command;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.android.kopper.dslrscripting.IWorkerUtil;
import app.android.kopper.dslrscripting.ProgressActivity;
import app.android.kopper.dslrscripting.R;
import app.android.kopper.dslrscripting.inputer.IInputer;
import app.android.kopper.dslrscripting.util.ConvertUtil;

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
public class ButtonsInputCommand extends AbstractCommand {

    public ButtonsInputCommand() {
        super("buttonsInput");
    }

    @Override
    public Object execute(final ArrayList params,IWorkerUtil util) throws Exception {
        if(params.size()<2)
            throw new Exception(getName()+" requires at least two parameters");
        final String message=ConvertUtil.toString(params.get(0));
        return util.input(new IInputer(){
            @Override
            public void updateLayout(final ProgressActivity progressActivity,LinearLayout buttonsLayout) {
                View view=progressActivity.getLayoutInflater().inflate(R.layout.buttons_empty,buttonsLayout,false);
                buttonsLayout.addView(view);

                LinearLayout buttonsContentLayout=(LinearLayout)progressActivity.findViewById(R.id.buttons_content);

                for(int a=0;a<params.size()-1;a++) {
                    Button b=new Button(progressActivity.getApplicationContext());
                    b.setText((String)params.get(a+1));
                    b.setTag(Integer.valueOf(a));
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressActivity.inputResult(v.getTag());
                        }
                    });
                    buttonsContentLayout.addView(b);
                }
                Button cancelButton=new Button(progressActivity.getApplicationContext());
                cancelButton.setText(progressActivity.getText(R.string.cancel_button));
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressActivity.interrupt();
                    }
                });
                buttonsContentLayout.addView(cancelButton);
                ((TextView)progressActivity.findViewById(R.id.message)).setText(message);
            }
        });
    }
}
