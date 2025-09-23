#!/bin/bash

set -e

VERSION=$(grep '^pVersion=' gradle.properties | cut -d'=' -f2)
echo "Building release $VERSION"

./gradlew clean

# Android
./gradlew assembleRelease bundleRelease

# macOS
./gradlew \
    pinpitPackageDefaultDistributableZipMacosX64 \
    pinpitPackageDefaultDistributableZipMacosArm64 \

# Windows
./gradlew \
    pinpitPackageDefaultMsiX64

# Linux
./gradlew \
    pinpitPackageDefaultDistributableTarGzLinuxX64 \
    pinpitPackageDefaultDistributableTarGzLinuxArm64 \
    pinpitPackageDefaultDebUniversalX64 \
    pinpitPackageDefaultAppImageLinuxX64 \
    pinpitPackageDefaultAppImageLinuxArm64

DIR=dist

rm -rf $DIR
mkdir $DIR

FILES_DESKTOP=$(find desktop/build -type f -and \( \
    -name "*AppImage" -or -name "*.zip" -or -name "*msi" -or \
    -name "*.deb" -or -name "*.tar.gz" \))

for f in $FILES_DESKTOP; do
    echo $f;
    cp $f $DIR;
done;

FILES_ANDROID=$(find android/build/outputs -type f -and \( \
    -name "*apk" -or -name "*.aab" \))

for f in $FILES_ANDROID; do
    echo $f;
    cp $f $DIR;
done;

mv "$DIR/Lanchat-x64-$VERSION.zip"           "$DIR/Lanchat-macos-x64-$VERSION.zip"
mv "$DIR/Lanchat-arm64-$VERSION.zip"         "$DIR/Lanchat-macos-arm64-$VERSION.zip"

mv "$DIR/Lanchat-x64-$VERSION.msi"           "$DIR/Lanchat-windows-x64-$VERSION.zip"

mv "$DIR/lanchat-universal-x64-$VERSION.deb" "$DIR/lanchat-linux-x64-$VERSION.deb"
mv "$DIR/lanchat-x64-$VERSION.tar.gz"        "$DIR/lanchat-linux-x64-$VERSION.tar.gz"
mv "$DIR/lanchat-arm64-$VERSION.tar.gz"      "$DIR/lanchat-linux-arm64-$VERSION.tar.gz"
mv "$DIR/lanchat-x64-$VERSION.AppImage"      "$DIR/lanchat-linux-x64-$VERSION.AppImage"
mv "$DIR/lanchat-arm64-$VERSION.AppImage"    "$DIR/lanchat-linux-arm64-$VERSION.AppImage"

mv "$DIR/lanchat-android-release.aab"        "$DIR/lanchat-android-$VERSION.aab"
mv "$DIR/lanchat-android-release.apk"        "$DIR/lanchat-android-$VERSION.apk"
