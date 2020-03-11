# GraalVM Issue

https://github.com/oracle/graal/issues/2250

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
javac RelativeClasspathResource.java
java RelativeClasspathResource
```

## Output ✅

```
SUCCESS: found and read classpath resource: subdir/example1.txt
SUCCESS: found and read classpath resource: example2.txt
SUCCESS: found and read classpath resource: subdir/../example2.txt
```

# Build and Run Native Image

```shell
native-image --no-fallback -H:Name=native-image.bin RelativeClasspathResource
./native-image.bin
```

## Output ❌

```
FAILURE: could not find classpath resource: subdir/example1.txt
FAILURE: could not find classpath resource: example2.txt
FAILURE: could not find classpath resource: subdir/../example2.txt
```

# Run with Native Image Agent

```shell
java -agentlib:native-image-agent=config-output-dir=build/META-INF/native-image RelativeClasspathResource
```

## Result

Generated `resource-config.json` contains:

```json
{
  "resources":[
    {"pattern":"\\Qexample2.txt\\E"}, 
    {"pattern":"\\Qsubdir/../example2.txt\\E"}, 
    {"pattern":"\\Qsubdir/example1.txt\\E"}
  ],
  "bundles":[]
}
```

# Build and Run Native Image with result of Agent

```shell
native-image --no-fallback -H:Name=native-image.bin -cp .:build RelativeClasspathResource
./native-image.bin
```

## Output ❌

The first two resources are now found; however, the third (relative path) resource
is still not found. Note that the agent generates an entry in `resource-config.json`
for `{"pattern":"\\Qsubdir/../example2.txt\\E"}`. Thus, it appears that the relative
path (i.e., `subdir/../`) is not properly supported within a native image.

```
SUCCESS: found and read classpath resource: subdir/example1.txt
SUCCESS: found and read classpath resource: example2.txt
FAILURE: could not find classpath resource: subdir/../example2.txt
```
