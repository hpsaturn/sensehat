# sensehat
SenseHat MatrixLED tests

## Compiling
```javascript
./gradlew assembleDebug
```

## Install
```javascript
./gradlew installDebug
```

## Start Activity
```javascript
adb shell am start hpsaturn.sensehat.matrixled/.mainactivity
```

### Troubleshooting

if you get IO permission error, please install with grant permission option and reboot:

```javascript
adb install -g -r app/build/outputs/apk/debug/app-debug.apk
adb reboot
adb install -g -r app/build/outputs/apk/debug/app-debug.apk
```


