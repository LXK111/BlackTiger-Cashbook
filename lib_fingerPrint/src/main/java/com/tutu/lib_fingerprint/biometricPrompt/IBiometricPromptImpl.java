package com.tutu.lib_fingerprint.biometricPrompt;

import android.os.CancellationSignal;

import androidx.annotation.NonNull;

import com.tutu.lib_fingerprint.BiometricPromptManager;

/**
 * Created by gaoyang on 2018/06/19.
 */
public interface IBiometricPromptImpl {

    void authenticate(@NonNull CancellationSignal cancel,
                      @NonNull BiometricPromptManager.OnBiometricIdentifyCallback callback);

}
