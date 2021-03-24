#!/bin/bash

IMAGE="system-images;android-29;default;x86"
NAME="viewtest"
ABI="x86"

SDK_MANAGER="$ANDROID_SDK_ROOT/cmdline-tools/latest/bin/sdkmanager"
AVD_MANAGER="$ANDROID_SDK_ROOT/cmdline-tools/latest/bin/avdmanager"
EMULATOR="$ANDROID_SDK_ROOT/emulator/emulator"

function is_image_installed {
  $SDK_MANAGER --list | sed -e '/Available Packages/q' | grep -q "system-images;android-29;default;x86"
}

function is_emulator_created {
  $AVD_MANAGER list avd | grep -q $NAME
}

if ! is_image_installed
then
  echo "Installing image $IMAGE"
  $SDK_MANAGER --install $IMAGE || exit 1
fi

if ! is_emulator_created
then
  echo "Creating emulator"
  echo "no" | $AVD_MANAGER create avd --force --name $NAME --package $IMAGE --tag default --abi $ABI || exit 1
  echo "hw.lcd.density=320" >> ~/.android/avd/$NAME.avd/config.ini || exit 1
fi

$EMULATOR @$NAME -skin 1080x1920 &
