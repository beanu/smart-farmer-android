package com.an.biometric;

import javax.crypto.Cipher;

public interface BiometricCallback {

    void onSdkVersionNotSupported();

    void onBiometricAuthenticationNotSupported();

    void onBiometricAuthenticationNotAvailable();

    void onBiometricAuthenticationPermissionNotGranted();

    void onBiometricAuthenticationInternalError(String error);


    void onAuthenticationFailed();

    void onAuthenticationCancelled();

    void onAuthenticationSuccessful(Cipher cipher);

    void onAuthenticationHelp(int helpCode, CharSequence helpString);

    void onAuthenticationError(int errorCode, CharSequence errString);
}
