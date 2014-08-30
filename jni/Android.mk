LOCAL_PATH := $(call my-dir)  
include $(CLEAR_VARS)  
LOCAL_LDLIBS := -llog  
LOCAL_MODULE    := ndk1  
LOCAL_SRC_FILES := native.c
include $(BUILD_SHARED_LIBRARY) 

include $(CLEAR_VARS)  
LOCAL_LDLIBS := -llog  
LOCAL_MODULE    := native2  
LOCAL_SRC_FILES := native2.cpp
LOCAL_CPPFLAGS := -fpermissive
include $(BUILD_SHARED_LIBRARY) 