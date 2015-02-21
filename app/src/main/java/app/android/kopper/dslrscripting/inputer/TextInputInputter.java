package app.android.kopper.dslrscripting.inputer;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import app.android.kopper.dslrscripting.IInputVerifier;
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
public class TextInputInputter extends AbstractInputInputer {

    private final IInputVerifier verifier;

    public TextInputInputter(String message, IInputVerifier verifier) {
        super(message);
        this.verifier=verifier;

    }

    @Override
    protected int getButtonsLayoutId() {
        return R.layout.buttons_input;
    }

    @Override
    protected Object getInputtedResult(ProgressActivity progressActivity) {
        String inputtedResult="";
        if(verifier!=null) {
            inputtedResult=((EditText)progressActivity.findViewById(R.id.input_value)).getText().toString();
            if(!verifier.check(inputtedResult)) {
                Toast.makeText(progressActivity.getApplicationContext(),verifier.getErrorMessage(inputtedResult),Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        return(inputtedResult);
    }

    @Override
    public void updateLayout(ProgressActivity progressActivity,LinearLayout buttonsLayout) {
        super.updateLayout(progressActivity,buttonsLayout);
        if(verifier!=null)
            verifier.updateLayout(progressActivity);
        progressActivity.findViewById(R.id.input_value).requestFocus();
    }
}
