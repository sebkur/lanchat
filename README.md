# Lanchat - an insecure (W)LAN messenger

It's like analogue radio, there's a bunch of channels and anybody can tune in.
Just choose your channel (a port in this case) and start sending and receiving
messages. Please keep in mind that this is totally insecure and everybody on
the same local network can read anything you send across the wire.

## Compose UI

There's a UI build with Compose for Desktop. To run it from the command line,
make sure to have a JDK 17 installed on your machine and on your path and
execute this:

    ./gradlew lanchat-desktop:pinpitRun

![](screenshot.png)

## Command line tools

There are also a few command line utilities that can be used to participate
in message and file exchange. To build those tools type this:

    ./gradlew installDist

Afterwards, the following tools are available at `cli/build/install/lanchat/bin`:

    lanchat-send <message>        # send message to other clients in the same network
    lanchat-receive               # receive messages from other clients
    lanchat-send-file <ip> <file> # send file to client at ip
    lanchat-receive-file <file>   # receive file sent by other client

## Android

To build an APK for Android, run this:

    ./gradlew assembleDebug

Afterwards, install it using adb:

    adb install -r android/build/outputs/apk/debug/lanchat-android-debug.apk

## Building releases

For Google Play, build an `aab` package using:

    ./gradlew bundleRelease
