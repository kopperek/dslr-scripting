#### List of available functions in DSLR scripting


#### Array functions ####

```
array arrayCreate(firstElement, secondElement, ...)
```
creates an array containing provided elements

```
element arrayGet(array,index)
```
returns array element from selected index

```
int arraySize(array)
```
returns the number of items in array


example:
```
array=arrayCreate(”one”,4.5,10,”last”)
while a<arraySize(array)
    log(arrayGet(array,a))
    a=a+1
endwhile
```

#### User input/output functions ####

```
int buttonsInput(String message, String button1Text, String button2Text,...)
```

presents message and set of buttons. it returns number of button that was selected by user

```
int inputNumber(String message, int minValue, int maxValue)
```
shows a message and wait for user to put a number. number has to be in between minValue and maxValue. it returns entered number

```
message(String message)
```
shows a message to user and waits for confirmation (ok button)

```
int question(String message)
```
asks a question (message) and wait for an answer. user can select yes or no answer. it returns 1 when user takes yes and 0 otherwise

```
log(String message)
```
adds a message to logs stack

```
wait(int time)
```
waits time*10 seconds

#### Camera functions ####

```
capture()
```
takes photo. when autofocus is set performs auto focus before photo is taken

```
performAF()
```
tries to set focus on frame. command is ignored when manual focus is set

#### Camera properties functions ####

List of properties is valid for nikon D5100. For other cameras some of the functions can be unavailable.

<table>
		<tr>
			<td>functions</td>
			<td>available values</td>
			<td>remarks</td>
		</tr>
		<tr>
			<td>
<pre>
value getActiveDLighting()
setActiveDLighting(value)
</pre>
</td>
			<td>"Extra high", "High", "Normal", "Low", "No", "Auto"</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getAEBracketingCount()
</pre>
</td>
			<td>number</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getAEBracketingStep()
setAEBracketingStep(value)
</pre>
</td>
			<td>"1/3", "1/2", "2/3", "1", "1 1/3", "1 1/2", "1 2/3", "2"</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getAFModeSelect()
setAFModeSelect(value)
</pre>
</td>
			<td>"AF-S", "AF-C", "AF-A", "MF<tt>[</tt>f<tt>]</tt>", "MF"</td>
			<td>
value 'MF<tt>[</tt>f<tt>]</tt>' means that property is set by switch on camera, so it can't be changed via script</td>
		</tr>
		<tr>
			<td>
<pre>
value getAutoDistortion()
setAutoDistortion(value)
</pre>
</td>
			<td>boolean (0/1)</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getBracketingType()
setBracketingType(value)
</pre>
</td>
			<td>"AE", "WB", "ADL"</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getBurstNumber()
setBurstNumber(value)
</pre>
</td>
			<td>number</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getColorSpace()
setColorSpace(value)
</pre>
</td>
			<td>"sRGB", "Adobe RGB"</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getCompressionSetting()
setCompressionSetting(value)
</pre>
</td>
			<td>"B", "N", "F", "R", "R+B", "R+N", "R+F"</td>
			<td>B - JPG Basic<br/>N - JPG Normal<br/>F - JPG File<br/>R - RAW</td>
		</tr>
		<tr>
			<td>
<pre>
value getContinuousShootingCount()
</pre>
</td>
			<td>number</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getEffectMode()
setEffectMode(value)
</pre>
</td>
			<td>"Night vision", "Color sketch", "Miniature effects", "Select color", "Silhouette", "High key", "Low key"</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getEnableBracketing()
setEnableBracketing(value)
</pre>
</td>
			<td>boolean (0/1)</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getExposureBiasCompensation()
setExposureBiasCompensation(value)
</pre>
</td>
			<td>"-5", "-4 2/3", "-4 1/2", "-4 1/3", "-4", "-3 2/3", "-3 1/2", "-3 1/3", "-3", "-2 2/3", "-2 1/2", "-2 1/3", "-2", "-1 2/3", "-1 1/2", "-1 1/3", "-1", "-2/3", "-1/2", "-1/3", "0", "+1/3", "+1/2", "+2/3", "+1", "+1 1/3", "+1 1/2", "+1 2/3", "+2", "+2 1/3", "+2 1/2", "+2 2/3", "+3", "+3 1/3", "+3 1/2", "+3 2/3", "+4", "+4 1/3", "+4 1/2", "+4 2/3", "+5"</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getExposureEVStep()
setExposureEVStep(value)
</pre>
</td>
			<td>"1/3", "1/2"</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getExposureIndex()
setExposureIndex(value)
</pre>
</td>
			<td>"100", "125", "160", "200", "250", "320", "400", "500", "640", "800", "1000", "1250", "1600", "2000", "2500", "3200", "4000", "5000", "6400", "Hi 0.3", "Hi 0.7", "Hi 1", "Hi 2"</td>
			<td>list of values is valid for nikon D5100. for other cameras some of values can be unavailable</td>
		</tr>
		<tr>
			<td>
<pre>
value getExposureMeteringMode()
setExposureMeteringMode(value)
</pre>
</td>
			<td>"Center", "Multi", "Spot"</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getExposureProgramMode()
</pre>
</td>
			<td>"M", "P", "A", "S", "AUTO", "Portrait", "Landscape", "Close up", "Sports", "Flash prohibition", "Child", "SCENE", "EFFECTS"</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getExposuresRemaining()
</pre>
</td>
			<td>number</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getExposureTime()
setExposureTime(value)
</pre>
</td>
			<td>number</td>
			<td>
value 0.0 indicates bulb time (valid only in 'M' exposure program)</td>
		</tr>
		<tr>
			<td>
<pre>
value getFlashMode()
setFlashMode(value)
</pre>
</td>
			<td>"Flash prohibited", "Red-eye reduction", "Front curtain sync", "Slow sync", "Rear curtain sync", "Red-eye reduction slow sync"</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getFnumber()
setFnumber(value)
</pre>
</td>
			<td>number</td>
			<td>list of valid values depends on current camera settings<br/>examples:<br/>1.8, 5.6, 22</td>
		</tr>
		<tr>
			<td>
<pre>
value getFocusMeteringMode()
setFocusMeteringMode(value)
</pre>
</td>
			<td>"Dynamic", "Single", "Auto", "3D"</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getFocusMode()
</pre>
</td>
			<td>"M", "S", "C", "A", "F"</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getHDREv()
setHDREv(value)
</pre>
</td>
			<td>"Auto", "1", "2", "3"</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getHDRMode()
setHDRMode(value)
</pre>
</td>
			<td>boolean (0/1)</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getHDRSmoothing()
setHDRSmoothing(value)
</pre>
</td>
			<td>"High", "Normal", "Low"</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getLensApatureMax()
</pre>
</td>
			<td>number</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getLensApatureMin()
</pre>
</td>
			<td>number</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getNoiseReduction()
setNoiseReduction(value)
</pre>
</td>
			<td>boolean (0/1)</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getNoiseReductionHiIso()
setNoiseReductionHiIso(value)
</pre>
</td>
			<td>"Not performed", "Low", "Normal", "High"</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getRemainingExposure()
</pre>
</td>
			<td>number</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getSceneMode()
setSceneMode(value)
</pre>
</td>
			<td>"Night landscape", "Party/indoor", "Beach/snow", "Sunset", "Dusk/dawn", "Pet portrait", "Candlelight", "Blossom", "Autumn colors", "Food", "Night portrait"</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
<pre>
value getStillCaptureMode()
setStillCaptureMode(value)
</pre>
</td>
			<td>"Single", "Continuous", "Self-timer", "Quick remote", "2s remote", "Quiet"</td>
			<td><br/></td>
		</tr>
		<tr>
			<td>
			
<pre>
value getWhiteBalance()
setWhiteBalance(value)
</pre>
</td>
			<td>"Auto", "Sunny", "Fluorescent", "Incandescent", "Flash", "Cloudy", "Shade", "Preset manual"</td>
			<td><br/></td>
		</tr>
</table>
