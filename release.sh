#!/bin/bash

set -e

VERSION=$(grep '^pVersion=' gradle.properties | cut -d'=' -f2)
echo "Building release $VERSION"

./gradlew clean

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
    pinpitPackageDefaultDebUniversalX64 \
    pinpitPackageDefaultAppImageLinuxX64

DIR=dist

rm -rf $DIR
mkdir $DIR

FILES=$(find desktop/build -type f -and \( \
    -name "*AppImage" -or -name "*.zip" -or -name "*msi" -or \
    -name "*.deb" -or -name "*.tar.gz" \))

for f in $FILES; do
    echo $f;
    cp $f $DIR;
done;

mv "$DIR/Lanchat-x64-$VERSION.zip"           "$DIR/Lanchat-macos-x64-$VERSION.zip"
mv "$DIR/Lanchat-arm64-$VERSION.zip"         "$DIR/Lanchat-macos-arm64-$VERSION.zip"

mv "$DIR/Lanchat-x64-$VERSION.msi"           "$DIR/Lanchat-windows-x64-$VERSION.zip"

mv "$DIR/lanchat-universal-x64-$VERSION.deb" "$DIR/Lanchat-x64-$VERSION.deb"
mv "$DIR/lanchat-x64-$VERSION.tar.gz"        "$DIR/Lanchat-linux-x64-$VERSION.tar.gz"
mv "$DIR/lanchat-x64-$VERSION.AppImage"      "$DIR/Lanchat-x64-$VERSION.AppImage"
