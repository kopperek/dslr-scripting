package app.android.kopper.dslrscripting.command;

import android.text.InputType;
import android.widget.EditText;

import java.util.ArrayList;

import app.android.kopper.dslrscripting.IInputVerifier;
import app.android.kopper.dslrscripting.IWorkerUtil;
import app.android.kopper.dslrscripting.ProgressActivity;
import app.android.kopper.dslrscripting.R;
import app.android.kopper.dslrscripting.RException;
import app.android.kopper.dslrscripting.inputer.TextInputInputter;
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
public class InputNumberCommand extends AbstractCommand {

    public InputNumberCommand() {
        super("inputNumber");
    }

    @Override
    public Object execute(ArrayList params,IWorkerUtil util) throws RException {
        if(params.size()!=3)
            throw new RException(R.string.error_method_requires_three,getName());
        String message=ConvertUtil.toString(params.get(0));
        final Integer minValue=ConvertUtil.toInteger(params.get(1));
        final Integer maxValue=ConvertUtil.toInteger(params.get(2));
        String result=(String)util.input(new TextInputInputter(message,new IInputVerifier() {
            @Override
            public boolean check(String inputtedText) {
                return getErrorMessage(inputtedText)==0;
            }

            @Override
            public int getErrorMessage(String inputtedText) {
                if(!inputtedText.matches("^[0-9]+$"))
                    return (R.string.error_not_a_number);
                Integer enteredNumber=Integer.valueOf(inputtedText);
                if(enteredNumber.compareTo(minValue)<0)
                    return (R.string.error_number_to_low);
                if(enteredNumber.compareTo(maxValue)>0)
                    return (R.string.error_number_to_high);
                return 0;
            }

            @Override
            public void updateLayout(ProgressActivity progressActivity) {
                ((EditText)progressActivity.findViewById(R.id.input_value)).setInputType(InputType.TYPE_CLASS_NUMBER);
            }
        }));
        return Integer.valueOf(result);
    }
}
