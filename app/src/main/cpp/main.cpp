#include <iostream>
#include <algorithm>
#include <iterator>
#include <jni.h>

extern "C" JNIEXPORT jstring JNICALL

Java_com_eevee_SignatureTest_g(
        JNIEnv* env,
        jobject /* this */) {
        std::string hello = "https://whoeevee.herokuapp.com/test";
        return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL

Java_com_eevee_SignatureTest_g1(
        JNIEnv* env,
jobject /* this */) {
std::string hello = "s";
return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL

Java_com_eevee_SignatureTest_g2(
        JNIEnv* env,
        jobject /* this */) {
        std::string hello = "debug_code";
        return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL

Java_com_eevee_SignatureTest_g3(
        JNIEnv* env,
        jobject /* this */) {
        std::string hello = "К сожалению, Ваша мама умерла. Что будете делать?";
        return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL

Java_com_eevee_SignatureTest_g4(
        JNIEnv* env,
        jobject /* this */) {
        std::string hello = "Воскресить";
        return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL

Java_com_eevee_SignatureTest_g5(
        JNIEnv* env,
        jobject /* this */) {
        std::string hello = "Произошла ошибка";
        return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL

Java_com_eevee_SignatureTest_g6(
        JNIEnv* env,
        jobject /* this */) {
        std::string hello = "Не удалось воскресить маму.";
        return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL

Java_com_eevee_SignatureTest_g8(
        JNIEnv* env,
        jobject /* this */) {
        std::string hello = "Мне все равно";
        return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL

Java_com_eevee_SignatureTest_g9(
        JNIEnv* env,
        jobject /* this */) {
        std::string hello = "message";
        return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL

Java_com_eevee_SignatureTest_g10(
        JNIEnv* env,
        jobject /* this */) {
        std::string hello = "** You've entered unsupported characters **";
        return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_eevee_SignatureTest_g11(
        JNIEnv* env,
jobject /* this */) {
std::string hello = "994";
return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_eevee_SignatureTest_g12(
        JNIEnv* env,
        jobject /* this */) {
        std::string hello = "Важное сообщение";
        return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_eevee_SignatureTest_g13(
        JNIEnv* env,
        jobject /* this */) {
        std::string hello = "l";
        return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_eevee_SignatureTest_g14(
        JNIEnv* env,
        jobject /* this */) {
        std::string hello = "2000";
        return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_eevee_SignatureTest_g16(
        JNIEnv* env,
        jobject /* this */) {
        std::string hello = "code";
        return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_eevee_SignatureTest_g18(
        JNIEnv* env,
        jobject /* this */) {
        std::string hello = "https://whoeevee.herokuapp.com/bg";
        return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_eevee_SignatureTest_g19(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "color";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_eevee_SignatureTest_g20(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "color2";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_eevee_SignatureTest_g23(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Готово";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_eevee_SignatureTest_g24(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Воскрешение мамы прошло успешно. Но впредь не вводите в калькулятор буквы: воскресить маму еще раз, возможно, не получится.";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_eevee_ChatActivity_g(JNIEnv *env, jobject thiz) {
    std::string hello = "https://whoeevee.herokuapp.com/message";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_eevee_ChatActivity_g4(JNIEnv *env, jobject thiz) {
    std::string hello = "list";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_eevee_ChatActivity_g5(JNIEnv *env, jobject thiz) {
    std::string hello = "author";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_eevee_ChatActivity_g7(JNIEnv *env, jobject thiz) {
    std::string hello = "content";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_eevee_Util_00024Companion_g(JNIEnv *env, jobject thiz) {
    std::string hello = "https://whoeevee.herokuapp.com/verify/";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_eevee_Util_00024Companion_g1(JNIEnv *env, jobject thiz) {
    std::string hello = "l";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_eevee_Util_00024Companion_g2(JNIEnv *env, jobject thiz) {
    std::string hello = "w";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_eevee_Util_00024Companion_g3(JNIEnv *env, jobject thiz) {
    std::string hello = "t";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_eevee_Util_00024Companion_g4(JNIEnv *env, jobject thiz) {
    std::string hello = "u";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_eevee_Util_00024Companion_g5(JNIEnv *env, jobject thiz) {
    std::string hello = "v";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_eevee_Util_00024Companion_g6(JNIEnv *env, jobject thiz) {
    std::string hello = "nickname";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_eevee_Util_00024Companion_g7(JNIEnv *env, jobject thiz) {
    std::string hello = "message";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_eevee_Util_00024Companion_g8(JNIEnv *env, jobject thiz) {
    std::string hello = "OK";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_eevee_Util_00024Companion_g9(JNIEnv *env, jobject thiz) {
    std::string hello = "uid";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_eevee_Util_00024Companion_g10(JNIEnv *env, jobject thiz) {
    std::string hello = "timestamp";
    return env->NewStringUTF(hello.c_str());
}