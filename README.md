DSLR Scripting gives you a possibility to control your dslr camera via script running on your android device. You can think of this app as a programmable remote shutter.

Simple script can look like this:
```java
while 1
    message("Press 'OK' to take photo")
    capture()
endwhile
```

It waits for a user to click ‘OK’ button and then takes a photo. An infinite loop makes this process repetitive.

To make this application running you need:
 * dslr nikon or canon camera. This app was created for and tested on nikon D5100, but it should work with other dslr cameras as well. Unfortunately i have no confirmation if it is true
 * android device with OTG (host USB) support
 * USB cable to connect your android with camera
 * application itself
 * and set of scripts. Every script file has to have '.script' extension. You have to upload your scripts on your android device and, using properties button in app, you have to provide location of directory with scripts

Scripting language is based on FScript by murlen (http://fscript.sourceforge.net/fscript/index.html). More about structures and usage can be found here: http://fscript.sourceforge.net/fscript/page3.html 

DSLR Scripting introduces one important change - variables don’t have to be declared before use. If you use undeclared variable for the very first time it is integer with 0 value.

List of available functions can be found here: https://github.com/kopperek/dslr-scripting/blob/master/functionslist.md

Every script can be described with three tags:

```java
#name: <name of the script>
#desc: <short description of the script>
#author: <author of the script>
```

Every tag acts as a comment in code so it doesn’t influence script itself. Values from this tags are displayed on your android device on a scripts list.

Here https://code.google.com/p/dslr-scripting/wiki/ExampleScripts you can find some  examples.

Ready to use version can be found here: https://play.google.com/store/apps/details?id=app.android.kopper.dslrscripting
