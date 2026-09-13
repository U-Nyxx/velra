#include <jni.h>
#include <string>
#include <android/log.h>
#include <sys/system_properties.h>
#include <unistd.h>

#define LOG_TAG "Velra"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

// Low-level hardware↔software bridge for Liquid Glass
// Research: reference platform renders glass in Render Server (out-of-process),
// we replicate via AGSL + RenderEffect + native thermal/SOC probe.
// This NDK layer reads hardware caps directly, bypassing Java Build.* cache,
// and exposes a fast path for the Kotlin SocDetector.
//
// No "apple" word — platform-agnostic, license-safe.

extern "C" {

JNIEXPORT jstring JNICALL
Java_io_github_u_1nyxx_velra_SocDetector_nativeHardware(JNIEnv* env, jclass) {
    char value[PROP_VALUE_MAX] = {0};
    __system_property_get("ro.hardware", value);
    if (value[0] == '\0') {
        __system_property_get("ro.boot.hardware", value);
    }
    return env->NewStringUTF(value);
}

JNIEXPORT jboolean JNICALL
Java_io_github_u_1nyxx_velra_VelraGlassView_nativeIsLowRam(JNIEnv* env, jobject thiz) {
    // placeholder low-RAM heuristic via sysconf — real check stays in Kotlin ActivityManager,
    // native shows hardware↔software direct page.
    long pages = sysconf(_SC_PHYS_PAGES);
    long pageSize = sysconf(_SC_PAGE_SIZE);
    long memMb = (pages * pageSize) / (1024 * 1024);
    return memMb < 4096 ? JNI_TRUE : JNI_FALSE; // <4GB → low-RAM tier
}

JNIEXPORT jstring JNICALL
Java_io_github_u_1nyxx_velra_VelraBridge_stringFromJNI(JNIEnv* env, jclass) {
    return env->NewStringUTF("Velra — C++ NDK bridge — hardware↔software, AGSL + RenderEffect, not full Kotlin");
}

} // extern "C"
