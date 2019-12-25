package com.an.biometric;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.util.Base64;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.os.CancellationSignal;


@TargetApi(Build.VERSION_CODES.M)
public class BiometricManagerV23 {

    private static final String KEY_NAME = "firefly_finger_key33344";

    Cipher cipher;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private FingerprintManagerCompat.CryptoObject cryptoObject;

    protected Context context;

    protected String title;
    protected String subtitle;
    protected String description;
    protected String negativeButtonText;
    //当前秘钥的IV参数。解密的时候使用
    protected String decryptIV;

    private BiometricDialogV23 biometricDialogV23;
    protected CancellationSignal mCancellationSignalV23 = new CancellationSignal();


    public void displayBiometricPromptV23(final BiometricCallback biometricCallback) {

        if (initCipher()) {

            cryptoObject = new FingerprintManagerCompat.CryptoObject(cipher);
            FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(context);

            fingerprintManagerCompat.authenticate(cryptoObject, 0, mCancellationSignalV23,
                    new FingerprintManagerCompat.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationError(int errMsgId, CharSequence errString) {
                            super.onAuthenticationError(errMsgId, errString);
                            updateStatus(String.valueOf(errString));
                            biometricCallback.onAuthenticationError(errMsgId, errString);
                        }

                        @Override
                        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                            super.onAuthenticationHelp(helpMsgId, helpString);
                            updateStatus(String.valueOf(helpString));
                            biometricCallback.onAuthenticationHelp(helpMsgId, helpString);
                        }

                        @Override
                        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                            super.onAuthenticationSucceeded(result);
                            dismissDialog();
                            biometricCallback.onAuthenticationSuccessful(result.getCryptoObject().getCipher());
                        }


                        @Override
                        public void onAuthenticationFailed() {
                            super.onAuthenticationFailed();
                            updateStatus(context.getString(R.string.biometric_failed));
                            biometricCallback.onAuthenticationFailed();
                        }
                    }, null);

            displayBiometricDialog(biometricCallback);
        }
    }


    private void displayBiometricDialog(final BiometricCallback biometricCallback) {
        biometricDialogV23 = new BiometricDialogV23(context, biometricCallback);
        biometricDialogV23.setTitle(title);
        biometricDialogV23.setSubtitle(subtitle);
        biometricDialogV23.setDescription(description);
        biometricDialogV23.setButtonText(negativeButtonText);
        biometricDialogV23.show();
    }


    private void dismissDialog() {
        if (biometricDialogV23 != null) {
            biometricDialogV23.dismiss();
        }
    }

    private void updateStatus(String status) {
        if (biometricDialogV23 != null) {
            biometricDialogV23.updateStatus(status);
        }
    }

    void generateKey() {
        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);

            //第二次启动的时候，如果存在key就不用再次创建了
            if (!keyStore.containsAlias(KEY_NAME)) {
                keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
                keyGenerator.init(new
                        KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .build());

                keyGenerator.generateKey();
            }

        } catch (KeyStoreException
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException exc) {
            exc.printStackTrace();
        }
    }


    boolean initCipher() {
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);

        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);

            //如果有iv参数，则进行解密操作
            if (!TextUtils.isEmpty(decryptIV)) {
                byte[] iv = Base64.decode(decryptIV, Base64.URL_SAFE);
                cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            } else {
                //加密操作
                cipher.init(Cipher.ENCRYPT_MODE, key);
            }

            return true;

        } catch (KeyPermanentlyInvalidatedException e) {
            return false;

        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException e) {

            throw new RuntimeException("Failed to init Cipher", e);
        }
    }
}
