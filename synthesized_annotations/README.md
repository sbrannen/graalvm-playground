# GraalVM Setup

```shell
export GRAALVM_HOME=/opt/graalvm/graalvm-ce-java17-22.2.0/Contents/Home/
export PATH=$GRAALVM_HOME/bin:$PATH
export JAVA_HOME=$GRAALVM_HOME
```

# Spring Framework 6.0 Snapshots

The local `lib` folder already contains the required pre-built snapshot jars, but you can build new ones as follows.

Check out the `SynthesizedAnnotation-removal` branch from `git@github.com:sbrannen/spring-framework.git`.

Run `./gradlew jar` and copy `spring-core/build/libs/spring-core-6.0.0-SNAPSHOT.jar` and `spring-jcl/build/libs/spring-jcl-6.0.0-SNAPSHOT.jar` to the local `lib` folder.

----

# Compile and Run with standard JRE

```shell
javac -cp lib/spring-core-6.0.0-SNAPSHOT.jar:lib/spring-jcl-6.0.0-SNAPSHOT.jar SynthesizedAnnotations.java
java -cp .:lib/spring-core-6.0.0-SNAPSHOT.jar:lib/spring-jcl-6.0.0-SNAPSHOT.jar SynthesizedAnnotations
```

## Output ✅

```diff
JDK: @RequestMapping(value="/test", path="")
JDK: class $Proxy1
----------------------------------------------------------------
SPR: @RequestMapping(path="/test", value="/test")
SPR: class $Proxy1
----------------------------------------------------------------
Proxy classes are the same: true
```

# Build and Run Native Image

```shell
native-image --no-fallback -H:Name=native-image.bin -cp .:lib/spring-core-6.0.0-SNAPSHOT.jar:lib/spring-jcl-6.0.0-SNAPSHOT.jar SynthesizedAnnotations
./native-image.bin
```

## Output ✅

```diff
JDK: @RequestMapping(value="/test", path="")
JDK: class $Proxy96
----------------------------------------------------------------
SPR: @RequestMapping(path="/test", value="/test")
SPR: class $Proxy96
----------------------------------------------------------------
Proxy classes are the same: true
```
