package app.android.kopper.dslrscripting.inputer;

import android.view.View;
import android.widget.LinearLayout;

import app.android.kopper.dslrscripting.ProgressActivity;
import app.android.kopper.dslrscripting.R;

/**
 * Created by kopper on 2015-02-18.
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
public class ProgressInputer implements IInputer {

    public ProgressInputer() {
    }

    @Override
    public void updateLayout(final ProgressActivity progressActivity,LinearLayout buttonsLayout) {
        View view=progressActivity.getLayoutInflater().inflate(R.layout.buttons_progress,buttonsLayout,false);
        buttonsLayout.addView(view);
        progressActivity.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressActivity.interrupt();
            }
        });
    }
}
