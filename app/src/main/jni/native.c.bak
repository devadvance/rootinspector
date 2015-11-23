#include <jni.h>
#include <string.h>
#include <android/log.h>
#include <stdio.h>
#include <errno.h>
#include <unistd.h>
#include <stdlib.h>

#include <sys/types.h>
#include <sys/stat.h>
#include <string.h>

#define DEBUG_TAG "RootInspector"
void Java_com_devadvance_rootinspector_Root_helloLog(JNIEnv * env, jobject this, jstring logThis)
{
    jboolean isCopy;
    const char * szLogThis = (*env)->GetStringUTFChars(env, logThis, &isCopy);
    __android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NDK:LC: [%s]", szLogThis);
    (*env)->ReleaseStringUTFChars(env, logThis, szLogThis);
}

jboolean Java_com_devadvance_rootinspector_Root_checkfopen(JNIEnv * env, jobject this, jstring filepath)
{
	jboolean fileExists = 0;
	jboolean isCopy;
	const char * path = (*env)->GetStringUTFChars(env, filepath, &isCopy);
	FILE *fp;
	__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Construct file with path: [%s]", path);
	fp=fopen(path, "r");
	if (fp != NULL) {
		fileExists = 1;
		__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: File exists. fopen is not NULL.");
		fclose(fp);
	}


	return fileExists;
}

jboolean Java_com_devadvance_rootinspector_Root_statfile(JNIEnv * env, jobject this, jstring filepath) {
	jboolean fileExists = 0;
	jboolean isCopy;
	const char * path = (*env)->GetStringUTFChars(env, filepath, &isCopy);
	struct stat fileattrib;
	if (stat(path, &fileattrib) < 0) {
		__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: stat error: [%s]", strerror(errno));
	} else
	{
		__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: stat success, access perms: [%d]", fileattrib.st_mode);
		return 1;
	}

	return 0;
}

jboolean Java_com_devadvance_rootinspector_Root_runsu(JNIEnv * env, jobject this, jstring command)
{
	jboolean run_su_success = 1;
	jboolean isCopy;
	const char * path = (*env)->GetStringUTFChars(env, command, &isCopy);
	int status;
	if(fork() == 0){
		// Child process will return 0 from fork()
		__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Child process here.");
		int result = execl("/system/xbin/su", "su", NULL);
		int errsv = errno;
		if (result != 0) {
			char buffer[256];
			char * errorMessage = strerror_r( errsv, buffer, 256 ); // get string message from errno
			__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: execl error: [%s]", errorMessage);
			exit(1);
		} else {
			__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: execl no error");
			exit(0);
		}
	}else{
		// Parent process will return a non-zero value from fork()
		__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Parent process here.");
		wait(&status);
		__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Parent done waiting.");
	}

	__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: status: [%d]", status);
	if (status) {
		run_su_success = 0;
		__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Error in running command.");
	} else {
		__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Success in running command.");
	}

	return run_su_success;
}

jboolean Java_com_devadvance_rootinspector_Root_runls(JNIEnv * env, jobject this, jstring filepath, jstring filename, jboolean exactMatch) {
	jboolean isCopy;
	const char * path = (*env)->GetStringUTFChars(env, filepath, &isCopy);
	jboolean isCopyB;
	const char * name = (*env)->GetStringUTFChars(env, filename, &isCopyB);
	char searchString[100];
	if (exactMatch) {
		sprintf(searchString, "%s%s%s", "\n", name, "\n");
	} else {
		sprintf(searchString, "%s", name);
	}


	int link[2];
	pid_t pid;
	char buf[4096];
	ssize_t bytesRead;

	if (pipe(link)==-1) {
		__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Error with pipe");
		exit(-1);
	}

	if ((pid = fork()) == -1) {
		__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Error with fork");
		exit(-1);
	}

	if(pid == 0) {
		//__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Child reporting in!");
		dup2 (link[1], STDOUT_FILENO);
		close(link[0]);
		execlp("ls", "ls", path, (char *)0);
		__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: After execl :( error: [%s]", strerror(errno));
		exit(-1);

	} else {
		close(link[1]);
		//__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Parent ready.");
		wait(NULL);
		//__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Parent done waiting.");
		while(1) {
		    bytesRead = read(link[0], buf, sizeof(buf)-1);
		    if (bytesRead <= 0) {
		    	__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: bytesread: [%zd]", bytesRead);
		    	break;
		    }
		    buf[bytesRead] = 0; // append null terminator
		    //__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Output: [%s]", buf);
		    if (strstr(buf, searchString) != NULL) {
		    	 __android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Found [%s]!", name);
		    	close(link[0]);
		    	return 1;
		    }
		  }
		//read(link[0], buf, sizeof(buf));


		close(link[0]);
		//__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Parent done.");
	}
	return 0;
}

jboolean Java_com_devadvance_rootinspector_Root_runpmlist(JNIEnv * env, jobject this, jstring packageName, jboolean exactMatch) {
	jboolean isCopy;
	const char * package = (*env)->GetStringUTFChars(env, packageName, &isCopy);
	char searchString[100];
	if (exactMatch) {
		sprintf(searchString, "%s%s%s", "\npackage:", package, "\n");
	} else {
		sprintf(searchString, "%s", package);
	}


	int link[2];
	pid_t pid;
	char buf[4096];
	ssize_t bytesRead;

	if (pipe(link)==-1) {
		__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Error with pipe");
		exit(-1);
	}

	if ((pid = fork()) == -1) {
		__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Error with fork");
		exit(-1);
	}

	if(pid == 0) {
		//__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Child reporting in!");
		dup2 (link[1], STDOUT_FILENO);
		close(link[0]);
		execlp("pm", "pm", "list", "packages", (char *)0);
		__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: After execl :( error: [%s]", strerror(errno));
		exit(-1);

	} else {
		close(link[1]);
		//__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Parent ready.");
		wait(NULL);
		//__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Parent done waiting.");
		while(1) {
		    bytesRead = read(link[0], buf, sizeof(buf)-1);
		    if (bytesRead <= 0) {
		    	__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: bytesread: [%zd]", bytesRead);
		    	break;
		    }
		    buf[bytesRead] = 0; // append null terminator
		    //__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Output: [%s]", buf);
		    if (strstr(buf, searchString) != NULL) {
		    	 __android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Found [%s]!", package);
		    	close(link[0]);
		    	return 1;
		    }
		  }
		//read(link[0], buf, sizeof(buf));


		close(link[0]);
		//__android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NATIVE: Parent done.");
	}
	return 0;
}
