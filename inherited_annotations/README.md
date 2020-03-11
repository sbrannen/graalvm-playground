# GraalVM Issue

https://github.com/oracle/graal/issues/2243

----

# Setup

```shell
export GRAALVM_HOME=/opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home
export PATH=$GRAALVM_HOME/bin:$PATH
export JAVA_HOME=$GRAALVM_HOME
mkdir -p build/META-INF/native-image
```

----

# Compile and Run with standard JRE

```shell
javac InheritedAnnotations.java
java InheritedAnnotations
```

## Output ✅

```
SUCCESS: @InheritedAnnotation was found as an annotation on Superclass.
SUCCESS: @InheritedAnnotation was found as a declared annotation on Superclass.
SUCCESS: @InheritedAnnotation was found as an annotation on Subclass.
SUCCESS: @InheritedAnnotation was found as a declared annotation on Subclass.
```

# Build and Run Native Image

```shell
native-image --no-fallback -H:Name=native-image.bin InheritedAnnotations
./native-image.bin
```

## Output ❌

```
SUCCESS: @InheritedAnnotation was found as an annotation on Superclass.
SUCCESS: @InheritedAnnotation was found as a declared annotation on Superclass.
SUCCESS: @InheritedAnnotation was found as an annotation on Subclass.
FAILURE: @InheritedAnnotation was NOT found as a declared annotation on Subclass.
```

# Run with Native Image Agent

```shell
java -agentlib:native-image-agent=config-output-dir=build/META-INF/native-image InheritedAnnotations
```

## Result

Generated `reflection-config.json` is empty:

```json
```

# Build and Run Native Image with result of Agent

```shell
native-image --no-fallback -H:Name=native-image.bin -cp .:build InheritedAnnotations
./native-image.bin
```

## Output ❌

It still fails, and this is to be expected since the agent didn't generate anything
in `reflection-config.json`.

```
SUCCESS: @InheritedAnnotation was found as an annotation on Superclass.
SUCCESS: @InheritedAnnotation was found as a declared annotation on Superclass.
SUCCESS: @InheritedAnnotation was found as an annotation on Subclass.
FAILURE: @InheritedAnnotation was NOT found as a declared annotation on Subclass.
```
