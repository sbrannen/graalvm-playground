# GraalVM Issue

https://github.com/oracle/graal/issues/2063

# Setup

```			
export GRAALVM_HOME=/opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home
export PATH=$GRAALVM_HOME/bin:$PATH
export JAVA_HOME=$GRAALVM_HOME
```

# Compile

`javac JavaPreferences.java`

# Run

`java JavaPreferences`

## Output

```
fruit: apple
color: blue
```

# Build Image

`native-image JavaPreferences`

## Failure

```
Build on Server(pid: 35208, port: 51433)
[javapreferences:35208]    classlist:     138.76 ms,  1.23 GB
[javapreferences:35208]        (cap):     949.25 ms,  1.23 GB
[javapreferences:35208]        setup:   2,078.19 ms,  1.23 GB
[javapreferences:35208]   (typeflow):   2,722.66 ms,  1.28 GB
[javapreferences:35208]    (objects):   2,673.22 ms,  1.28 GB
[javapreferences:35208]   (features):     124.05 ms,  1.28 GB
[javapreferences:35208]     analysis:   5,614.20 ms,  1.28 GB
[javapreferences:35208]     (clinit):     103.03 ms,  1.28 GB
[javapreferences:35208]     universe:     308.96 ms,  1.28 GB
[javapreferences:35208]      (parse):     322.78 ms,  1.28 GB
[javapreferences:35208]     (inline):     736.05 ms,  1.28 GB
[javapreferences:35208]    (compile):   2,654.90 ms,  1.44 GB
[javapreferences:35208]      compile:   3,924.85 ms,  1.44 GB
[javapreferences:35208]        image:     327.68 ms,  1.44 GB
[javapreferences:35208]        write:     144.29 ms,  1.44 GB
Fatal error: java.lang.RuntimeException: java.lang.RuntimeException: There was an error linking the native image: Linker command exited with 1

Linker command executed:
cc -v -o /Users/developer/source/graalvm-playground/preferences/javapreferences -Wl,-no_compact_unwind -Wl,-exported_symbols_list -Wl,/var/folders/4m/0md8vq2n2dz_24nypqtqhmwm0000gn/T/SVM-1224911916344818511/exported_symbols.list -Wl,-x -arch x86_64 -L/var/folders/4m/0md8vq2n2dz_24nypqtqhmwm0000gn/T/SVM-1224911916344818511 -L/opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib -L/opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/svm/clibraries/darwin-amd64 /var/folders/4m/0md8vq2n2dz_24nypqtqhmwm0000gn/T/SVM-1224911916344818511/javapreferences.o /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/libnet.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/libzip.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/libjava.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/libnio.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/svm/clibraries/darwin-amd64/libffi.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/svm/clibraries/darwin-amd64/liblibchelper.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/svm/clibraries/darwin-amd64/libjvm.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/svm/clibraries/darwin-amd64/libstrictmath.a -Wl,-framework,CoreFoundation -lpthread -ldl -lz

Linker command ouput:
Apple clang version 11.0.0 (clang-1100.0.33.17)
Target: x86_64-apple-darwin18.7.0
Thread model: posix
InstalledDir: /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin
 "/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/ld" -demangle -lto_library /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/lib/libLTO.dylib -dynamic -arch x86_64 -macosx_version_min 10.14.0 -syslibroot /Library/Developer/CommandLineTools/SDKs/MacOSX.sdk -o /Users/developer/source/graalvm-playground/preferences/javapreferences -L/var/folders/4m/0md8vq2n2dz_24nypqtqhmwm0000gn/T/SVM-1224911916344818511 -L/opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib -L/opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/svm/clibraries/darwin-amd64 -no_compact_unwind -exported_symbols_list /var/folders/4m/0md8vq2n2dz_24nypqtqhmwm0000gn/T/SVM-1224911916344818511/exported_symbols.list -x /var/folders/4m/0md8vq2n2dz_24nypqtqhmwm0000gn/T/SVM-1224911916344818511/javapreferences.o /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/libnet.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/libzip.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/libjava.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/libnio.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/svm/clibraries/darwin-amd64/libffi.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/svm/clibraries/darwin-amd64/liblibchelper.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/svm/clibraries/darwin-amd64/libjvm.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/svm/clibraries/darwin-amd64/libstrictmath.a -framework CoreFoundation -lpthread -ldl -lz -L/usr/local/lib -lSystem /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/lib/clang/11.0.0/lib/darwin/libclang_rt.osx.a
Undefined symbols for architecture x86_64:
  "_Java_java_util_prefs_MacOSXPreferencesFile_addKeyToNode", referenced from:
      ___svm_version_info in javapreferences.o
  "_Java_java_util_prefs_MacOSXPreferencesFile_addNode", referenced from:
      ___svm_version_info in javapreferences.o
  "_Java_java_util_prefs_MacOSXPreferencesFile_getKeyFromNode", referenced from:
      ___svm_cglobaldata_base in javapreferences.o
  "_Java_java_util_prefs_MacOSXPreferencesFile_synchronize", referenced from:
      ___svm_cglobaldata_base in javapreferences.o
ld: symbol(s) not found for architecture x86_64
clang: error: linker command failed with exit code 1 (use -v to see invocation)

	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.lang.reflect.Constructor.newInstance(Constructor.java:423)
	at java.util.concurrent.ForkJoinTask.getThrowableException(ForkJoinTask.java:593)
	at java.util.concurrent.ForkJoinTask.get(ForkJoinTask.java:1005)
	at com.oracle.svm.hosted.NativeImageGenerator.run(NativeImageGenerator.java:462)
	at com.oracle.svm.hosted.NativeImageGeneratorRunner.buildImage(NativeImageGeneratorRunner.java:357)
	at com.oracle.svm.hosted.NativeImageGeneratorRunner.build(NativeImageGeneratorRunner.java:501)
	at com.oracle.svm.hosted.server.NativeImageBuildServer.executeCompilation(NativeImageBuildServer.java:406)
	at com.oracle.svm.hosted.server.NativeImageBuildServer.lambda$processCommand$8(NativeImageBuildServer.java:343)
	at com.oracle.svm.hosted.server.NativeImageBuildServer.withJVMContext(NativeImageBuildServer.java:424)
	at com.oracle.svm.hosted.server.NativeImageBuildServer.processCommand(NativeImageBuildServer.java:340)
	at com.oracle.svm.hosted.server.NativeImageBuildServer.processRequest(NativeImageBuildServer.java:284)
	at com.oracle.svm.hosted.server.NativeImageBuildServer.lambda$serve$7(NativeImageBuildServer.java:244)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at java.lang.Thread.run(Thread.java:748)
Caused by: java.lang.RuntimeException: There was an error linking the native image: Linker command exited with 1

Linker command executed:
cc -v -o /Users/developer/source/graalvm-playground/preferences/javapreferences -Wl,-no_compact_unwind -Wl,-exported_symbols_list -Wl,/var/folders/4m/0md8vq2n2dz_24nypqtqhmwm0000gn/T/SVM-1224911916344818511/exported_symbols.list -Wl,-x -arch x86_64 -L/var/folders/4m/0md8vq2n2dz_24nypqtqhmwm0000gn/T/SVM-1224911916344818511 -L/opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib -L/opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/svm/clibraries/darwin-amd64 /var/folders/4m/0md8vq2n2dz_24nypqtqhmwm0000gn/T/SVM-1224911916344818511/javapreferences.o /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/libnet.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/libzip.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/libjava.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/libnio.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/svm/clibraries/darwin-amd64/libffi.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/svm/clibraries/darwin-amd64/liblibchelper.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/svm/clibraries/darwin-amd64/libjvm.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/svm/clibraries/darwin-amd64/libstrictmath.a -Wl,-framework,CoreFoundation -lpthread -ldl -lz

Linker command ouput:
Apple clang version 11.0.0 (clang-1100.0.33.17)
Target: x86_64-apple-darwin18.7.0
Thread model: posix
InstalledDir: /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin
 "/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/ld" -demangle -lto_library /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/lib/libLTO.dylib -dynamic -arch x86_64 -macosx_version_min 10.14.0 -syslibroot /Library/Developer/CommandLineTools/SDKs/MacOSX.sdk -o /Users/developer/source/graalvm-playground/preferences/javapreferences -L/var/folders/4m/0md8vq2n2dz_24nypqtqhmwm0000gn/T/SVM-1224911916344818511 -L/opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib -L/opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/svm/clibraries/darwin-amd64 -no_compact_unwind -exported_symbols_list /var/folders/4m/0md8vq2n2dz_24nypqtqhmwm0000gn/T/SVM-1224911916344818511/exported_symbols.list -x /var/folders/4m/0md8vq2n2dz_24nypqtqhmwm0000gn/T/SVM-1224911916344818511/javapreferences.o /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/libnet.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/libzip.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/libjava.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/libnio.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/svm/clibraries/darwin-amd64/libffi.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/svm/clibraries/darwin-amd64/liblibchelper.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/svm/clibraries/darwin-amd64/libjvm.a /opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home/jre/lib/svm/clibraries/darwin-amd64/libstrictmath.a -framework CoreFoundation -lpthread -ldl -lz -L/usr/local/lib -lSystem /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/lib/clang/11.0.0/lib/darwin/libclang_rt.osx.a
Undefined symbols for architecture x86_64:
  "_Java_java_util_prefs_MacOSXPreferencesFile_addKeyToNode", referenced from:
      ___svm_version_info in javapreferences.o
  "_Java_java_util_prefs_MacOSXPreferencesFile_addNode", referenced from:
      ___svm_version_info in javapreferences.o
  "_Java_java_util_prefs_MacOSXPreferencesFile_getKeyFromNode", referenced from:
      ___svm_cglobaldata_base in javapreferences.o
  "_Java_java_util_prefs_MacOSXPreferencesFile_synchronize", referenced from:
      ___svm_cglobaldata_base in javapreferences.o
ld: symbol(s) not found for architecture x86_64
clang: error: linker command failed with exit code 1 (use -v to see invocation)

	at com.oracle.svm.hosted.image.NativeBootImageViaCC.handleLinkerFailure(NativeBootImageViaCC.java:424)
	at com.oracle.svm.hosted.image.NativeBootImageViaCC.write(NativeBootImageViaCC.java:399)
	at com.oracle.svm.hosted.NativeImageGenerator.doRun(NativeImageGenerator.java:657)
	at com.oracle.svm.hosted.NativeImageGenerator.lambda$run$0(NativeImageGenerator.java:445)
	at java.util.concurrent.ForkJoinTask$AdaptedRunnableAction.exec(ForkJoinTask.java:1386)
	at java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:289)
	at java.util.concurrent.ForkJoinPool$WorkQueue.runTask(ForkJoinPool.java:1056)
	at java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1692)
	at java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:157)
Error: Image build request failed with exit status 1
```
