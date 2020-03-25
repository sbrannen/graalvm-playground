# GraalVM Issue

???

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
javac NonexistentClasspathResource.java
java NonexistentClasspathResource
```

## Output ✅

```diff
+ SUCCESS: found classpath resource: NonexistentClasspathResource.class
+ getContentLengthLong() for file:/nonexistent_classpath_resource/NonexistentClasspathResource.class: 466
+ SUCCESS: classpath resource exists: file:/nonexistent_classpath_resource/NonexistentClasspathResource.class
+ SUCCESS: bogus classpath resource does not exist: file:/nonexistent_classpath_resource/Bogus.class
```

# Build and Run Native Image

```shell
native-image --no-fallback -H:Name=native-image.bin NonexistentClasspathResource
./native-image.bin
```

## Output ❌

```diff
- FAILURE: could not find classpath resource: NonexistentClasspathResource.class
```

# Run with Native Image Agent

```shell
java -agentlib:native-image-agent=config-output-dir=build/META-INF/native-image NonexistentClasspathResource
```

## Result

Generated `resource-config.json` contains:

```json
{
  "resources":[
    {"pattern":"\\QMETA-INF/services/jdk.vm.ci.hotspot.HotSpotJVMCIBackendFactory\\E"}, 
    {"pattern":"\\QMETA-INF/services/jdk.vm.ci.services.JVMCIServiceLocator\\E"}, 
    {"pattern":"\\QNonexistentClasspathResource.class\\E"}
  ],
  "bundles":[]
}
```

# Build and Run Native Image with result of Agent

```shell
native-image --no-fallback -H:Name=native-image.bin -cp .:build NonexistentClasspathResource
./native-image.bin
```

## Output ❌

The first resource is now found; however, the second "bogus" resource also appears
to exist within the native image. Note that `java.net.URLConnection.getContentLengthLong()`
returns a positive value for the nonexistent resource that is identical to the value
returned for the existing resource (`466`).

```diff
+ SUCCESS: found classpath resource: NonexistentClasspathResource.class
+ getContentLengthLong() for resource:NonexistentClasspathResource.class: 466
+ SUCCESS: classpath resource exists: resource:NonexistentClasspathResource.class
- getContentLengthLong() for resource:Bogus.class: 466
- FAILURE: bogus classpath resource appears to exist: resource:Bogus.class
```
