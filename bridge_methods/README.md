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
javac BridgeMethods.java
java BridgeMethods
```

## Output ✅

```
Bridged method is a bridge method: false
Bridge method is a bridge method:  true
```

# Build and Run Native Image

```shell
native-image --no-fallback -H:Name=native-image.bin BridgeMethods
./native-image.bin
```

## Output ❌

```
Exception in thread "main" java.lang.NullPointerException
	at BridgeMethods.main(BridgeMethods.java:34)
```

# Run with Native Image Agent

```shell
java -agentlib:native-image-agent=config-output-dir=build/META-INF/native-image BridgeMethods
```

## Result

Generated `reflect-config.json` contains:

```json
[
	{
	  "name":"BridgeMethods$StringGenericParameter",
	  "allDeclaredMethods":true
	}
]
```

# Build and Run Native Image with result of Agent

```shell
native-image --no-fallback -H:Name=native-image.bin -cp .:build BridgeMethods
./native-image.bin
```

## Output ✅


```
Bridged method is a bridge method: false
Bridge method is a bridge method:  true
```
