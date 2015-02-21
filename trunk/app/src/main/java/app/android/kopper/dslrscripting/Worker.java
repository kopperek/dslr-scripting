package app.android.kopper.dslrscripting;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.LockSupport;

import app.android.kopper.dslrscripting.command.ButtonsInputCommand;
import app.android.kopper.dslrscripting.command.CaptureCommand;
import app.android.kopper.dslrscripting.command.ICommand;
import app.android.kopper.dslrscripting.command.InputNumberCommand;
import app.android.kopper.dslrscripting.command.LogCommand;
import app.android.kopper.dslrscripting.command.MessageCommand;
import app.android.kopper.dslrscripting.command.PerformAFCommand;
import app.android.kopper.dslrscripting.command.QuestionCommand;
import app.android.kopper.dslrscripting.command.WaitCommand;
import app.android.kopper.dslrscripting.command.array.ArrayCreateCommand;
import app.android.kopper.dslrscripting.command.array.ArrayGetCommand;
import app.android.kopper.dslrscripting.command.array.ArraySizeCommand;
import app.android.kopper.dslrscripting.command.cameraproperty.AEBracketingCountProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.AEBracketingStepProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.AFModeSelectProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.AbstractCameraProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.ActiveDLightingProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.AutoDistortionProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.BracketingTypeProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.BurstNumberProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.ColorSpaceProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.CompressionSettingProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.ContinuousShootingCountProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.EffectModeProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.EnableBracketingProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.ExposureBiasCompensationProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.ExposureEVStepProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.ExposureIndexProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.ExposureMeteringModeProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.ExposureProgramModeProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.ExposureTimeProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.ExposuresRemainingProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.FlashModeProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.FnumberProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.FocusMeteringModeProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.FocusModeProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.HDREvProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.HDRModeProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.HDRSmoothingProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.LensApatureMaxProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.LensApatureMinProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.NoiseReductionHiIsoProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.NoiseReductionProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.RemainingExposureProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.SceneModeProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.StillCaptureModeProperty;
import app.android.kopper.dslrscripting.command.cameraproperty.WhiteBalanceProperty;
import app.android.kopper.dslrscripting.event.EventDispatcher;
import app.android.kopper.dslrscripting.event.IEventExceptionListener;
import app.android.kopper.dslrscripting.inputer.DoneInputer;
import app.android.kopper.dslrscripting.inputer.IInputer;
import app.android.kopper.dslrscripting.inputer.ProgressInputer;
import app.android.kopper.dslrscripting.util.LogUtil;
import murlen.util.fscript.FSException;
import murlen.util.fscript.FScript;

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
public class Worker extends AsyncTask<WorkerParams,VisualState,Object> {

    private static final int MAX_LOG_EVENTS=50;
    private List<String> logEvents=new LinkedList<>();
    private ProgressActivity activity;

    private static List<ICommand> commands=new LinkedList<>();
    private static AbstractCameraProperty[] cameraProperties;

    static {

        commands.add(new PerformAFCommand());

        commands.add(new ArrayCreateCommand());
        commands.add(new ArraySizeCommand());
        commands.add(new ArrayGetCommand());

        commands.add(new WaitCommand());
        commands.add(new MessageCommand());
        commands.add(new LogCommand());
        commands.add(new InputNumberCommand());
        commands.add(new ButtonsInputCommand());
        commands.add(new QuestionCommand());

        commands.add(new CaptureCommand());

        cameraProperties=new AbstractCameraProperty[]{
                new ActiveDLightingProperty(),
                new AEBracketingCountProperty(),
                new AEBracketingStepProperty(),
                new AFModeSelectProperty(),
                new AutoDistortionProperty(),
                new BracketingTypeProperty(),
                new ColorSpaceProperty(),
                new CompressionSettingProperty(),
                new ContinuousShootingCountProperty(),
                new EffectModeProperty(),
                new EnableBracketingProperty(),
                new ExposureBiasCompensationProperty(),
                new ExposureEVStepProperty(),
                new ExposureIndexProperty(),
                new ExposureMeteringModeProperty(),
                new ExposureProgramModeProperty(),
                new ExposuresRemainingProperty(),
                new ExposureTimeProperty(),
                new FlashModeProperty(),
                new FnumberProperty(),
                new FocusMeteringModeProperty(),
                new FocusModeProperty(),
                new HDREvProperty(),
                new HDRModeProperty(),
                new HDRSmoothingProperty(),
                new LensApatureMaxProperty(),
                new LensApatureMinProperty(),
                new NoiseReductionHiIsoProperty(),
                new NoiseReductionProperty(),
                new RemainingExposureProperty(),
                new SceneModeProperty(),
                new WhiteBalanceProperty(),
                new StillCaptureModeProperty(),
                new BurstNumberProperty()
        };
        for(AbstractCameraProperty cameraProperty: cameraProperties)
            commands.addAll(cameraProperty.getCommands());

    }

    private Thread threadForUnpark=null;
    private Object inputtedResult=null;

    private Integer interruptedMessageKey=null;

    private Map<String,Object> vars=new HashMap<>();

    public Worker(ProgressActivity activity) {
        this.activity=activity;
        this.vars.clear();
    }

    @Override
    protected Object doInBackground(WorkerParams... params) {

        interruptedMessageKey=null;

        UsbDevice device=params[0].getDevice();
        UsbManager manager=params[0].getUsbManager();

        UsbInterface usbInterface=device.getInterface(0);
        final UsbEndpoint inEndpoint=usbInterface.getEndpoint(0);
        final UsbEndpoint outEndpoint=usbInterface.getEndpoint(1);

        final UsbDeviceConnection connection = manager.openDevice(device);
        boolean claimInterface=connection.claimInterface(usbInterface,true);

        publishProgress(new VisualState(new ProgressInputer(),getLogEvents(null)));

        int[] finalMessageKeys=new int[]{};
        Object[] finalMessageParams=new Object[]{};
        try {
            FScript script=new FScript() {

                @Override
                public void setVar(String name,Object index,Object value) throws FSException {
                    vars.put(name,value);
                }

                @Override
                public Object getVar(String name,Object index) throws FSException {
                    if(!vars.containsKey(name)) {
                        vars.put(name,Integer.valueOf(0));
                    }
                    return vars.get(name);
                }

                @Override
                public Object callFunction(String name,ArrayList params) throws FSException {
                    ICommand command=findCommand(name);
                    if(command!=null) {
                        try {
                            return command.execute(params,new IWorkerUtil() {

                                @Override
                                public void waitWhile(int time) throws RException {
                                    try {
                                        Thread.sleep(time);
                                    } catch(java.lang.InterruptedException e) {
                                        throw new RException(R.string.error_interrupted);
                                    }
                                }

                                @Override
                                public boolean isInterrupted() throws InterruptedException {
                                    checkInterruption();
                                    return false;
                                }

                                @Override
                                public void log(String s) throws InterruptedException {
                                    checkInterruption();
                                    publishProgress(new VisualState(new ProgressInputer(),getLogEvents(s)));
                                }

                                @Override
                                public void cameraCommand(PureCommand command) throws RException {
                                    checkInterruption();
                                    command.execute(inEndpoint,outEndpoint,connection);
                                }

                                @Override
                                public void waitForNoBusy() throws RException {
                                    for(;;) {
                                        try {
                                            checkInterruption();
                                            PureCommand checkCommand=new PureCommand(PureCommand.DEVICE_READY);
                                            checkCommand.execute(inEndpoint,outEndpoint,connection);
                                            break;
                                        } catch(NoOKResponseCodeException e) {
                                            if(e.getResponseCode()!=0x2019)
                                                throw e;
                                            waitWhile(100);
                                        }
                                    }
                                }


                                @Override
                                public Object input(IInputer inputer) throws InterruptedException {
                                    checkInterruption();
                                    publishProgress(new VisualState(inputer,getLogEvents(null)));
                                    //wait for input
                                    Worker.this.threadForUnpark=Thread.currentThread();
                                    LockSupport.park();
                                    checkInterruption();
                                    publishProgress(new VisualState(new ProgressInputer(),getLogEvents(null)));
                                    return (inputtedResult);
                                }
                            });
                        } catch(RException e) {
                            LogUtil.e(e);
                            throw new FSException(e.getKey(),getCode().getCurLine(),getCode().getLineAsString(),null,null,null,e.getParams());
                        }
                    } else
                        return (super.callFunction(name,params));
                }

                ;
            };

            String scriptPath=params[0].getSelectedScript();
            if(scriptPath==null)
                throw new NullPointerException("Script path is null");
            File scriptFile=new File(scriptPath);
            if(!scriptFile.exists())
                throw new RException(R.string.error_selected_script_doesnt_exist);
            InputStreamReader reader=new InputStreamReader(new FileInputStream(scriptFile));
            script.load(reader);
            reader.close();

            PureCommand sessionCommand=new PureCommand(PureCommand.OPEN_SESSION);
            sessionCommand.addParam((long)0x01);
            sessionCommand.execute(inEndpoint,outEndpoint,connection);

            EventDispatcher.init(inEndpoint,outEndpoint,connection,new IEventExceptionListener() {
                @Override
                public void exception(Exception e) {
                    LogUtil.e(e);
                    interrupt(R.string.error_io_exception);
                }
            });

            script.run();
            finalMessageKeys=new int[]{R.string.message_done};
        } catch (FSException e) {
            LogUtil.e(e);
            finalMessageKeys=new int[]{e.getKey(),R.string.error_localization};
            Object[] orygParams=e.getParams();
            finalMessageParams=new Object[orygParams.length+2];
            System.arraycopy(orygParams,0,finalMessageParams,0,orygParams.length);
            finalMessageParams[orygParams.length]=e.getLineNo();
            finalMessageParams[orygParams.length+1]=e.getLine();
        } catch(RException e) {
            LogUtil.e(e);
            finalMessageKeys=new int[]{e.getKey()};
            finalMessageParams=e.getParams();
        } catch(Exception e) {
            LogUtil.e(e);
            finalMessageKeys=new int[]{R.string.error};
            finalMessageParams=new Object[]{e.getLocalizedMessage()};
        }
        finally {
            EventDispatcher.done();
            PureCommand sessionCommand=new PureCommand(PureCommand.CLOSE_SESSION);
            try {
                sessionCommand.execute(inEndpoint,outEndpoint,connection);
            } catch(Exception e) {
                LogUtil.e(e);
            }


            connection.releaseInterface(usbInterface);
            connection.close();
        }
        publishProgress(new VisualState(new DoneInputer(finalMessageKeys,finalMessageParams),getLogEvents(null)));
        return(null);
    }

    private void checkInterruption() throws InterruptedException {
        if(interruptedMessageKey!=null)
            throw new InterruptedException(interruptedMessageKey);
    }

    private ICommand findCommand(String name) {
        for(ICommand command: commands)
            if(name.equals(command.getName()))
                return(command);
        return(null);
    }

    private String[] getLogEvents(String newEvent) {
        if(newEvent!=null) {
            logEvents.add(newEvent);
            if(logEvents.size()>MAX_LOG_EVENTS)
                logEvents.remove(0);
        }
        return(logEvents.toArray(new String[logEvents.size()]));
    }

    @Override
    protected void onProgressUpdate(VisualState... values) {
        if(activity!=null) {
            VisualState visualState=values[0];
            activity.show(visualState);
        }
    }

    public void setActivity(ProgressActivity activity) {
        this.activity=activity;
    }

    public void inputResult(Object inputtedResult) {
        this.inputtedResult=inputtedResult;
        unpark();
    }

    public void interrupt(int key) {
        //todo: send interrupted message to dslr
        this.interruptedMessageKey=key;
        unpark();
    }

    private void unpark() {
        if(threadForUnpark!=null) {
            LockSupport.unpark(threadForUnpark);
            threadForUnpark=null;
        }
    }
}
