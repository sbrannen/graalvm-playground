# GraalVM Issue

Reported issue: https://github.com/oracle/graal/issues/2253

Related issue: https://github.com/oracle/graal/issues/1683

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
javac LastModifiedClasspathResource.java
java LastModifiedClasspathResource
```

## Output ✅

```
SUCCESS: found classpath resource LastModifiedClasspathResource.class
SUCCESS: content-length 469
SUCCESS: last-modified timestamp 1584104196000 / date 2020-03-13T13:56:36
```

# Build and Run Native Image

```shell
native-image --no-fallback -H:Name=native-image.bin LastModifiedClasspathResource
./native-image.bin
```

## Output ❌

```
FAILURE: could not find classpath resource LastModifiedClasspathResource.class
```

# Run with Native Image Agent

```shell
java -agentlib:native-image-agent=config-output-dir=build/META-INF/native-image LastModifiedClasspathResource
```

## Result

Generated `resource-config.json` contains:

```json
{
  "resources":[
    {"pattern":"\\QLastModifiedClasspathResource.class\\E"}, 
    {"pattern":"\\QMETA-INF/services/jdk.vm.ci.hotspot.HotSpotJVMCIBackendFactory\\E"}, 
    {"pattern":"\\QMETA-INF/services/jdk.vm.ci.services.JVMCIServiceLocator\\E"}
  ],
  "bundles":[]
}
```

# Build and Run Native Image with result of Agent

```shell
native-image --no-fallback -H:Name=native-image.bin -cp .:build LastModifiedClasspathResource
./native-image.bin
```

## Output ❌

`LastModifiedClasspathResource.class` is now available as a classpath resource, and the
content length is properly supported. However, the last-modified timestamp is not
available.

```
SUCCESS: found classpath resource LastModifiedClasspathResource.class
SUCCESS: content-length 469
FAILURE: non-positive last-modified 0
```
