# Setup

```shell
export GRAALVM_HOME=/opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home
export PATH=$GRAALVM_HOME/bin:$PATH
export JAVA_HOME=$GRAALVM_HOME
mkdir -p build/META-INF/native-image
```

----

# Inherited Instance Method

## Compile and Run with standard JRE

```shell
javac InheritedInstanceMethod.java
java InheritedInstanceMethod
```

### Output ✅

```
SUCCESS: found instance method getId() in java.time.ZoneRegion.
```

## Build and Run Native Image

```shell
native-image --no-fallback -H:Name=native-image.bin InheritedInstanceMethod
./native-image.bin
```

### Output ❌

```
Exception in thread "main" java.lang.NoSuchMethodException: java.time.ZoneRegion.getId()
	at java.lang.Class.getMethod(DynamicHub.java:1053)
	at Tester.instanceMethod(InheritedInstanceMethod.java:38)
	at Tester.performTest(InheritedInstanceMethod.java:34)
	at InheritedInstanceMethod.main(InheritedInstanceMethod.java:24)
```

## Run with Native Image Agent

```shell
java -agentlib:native-image-agent=config-output-dir=build/META-INF/native-image InheritedInstanceMethod
```

### Result

Generated `reflection-config.json` contains:

```json
[
	{
	  "name":"java.time.ZoneRegion",
	  "methods":[{"name":"getId","parameterTypes":[] }]
	}
]
```

## Build and Run Native Image with result of Agent

```shell
native-image --no-fallback -H:Name=native-image.bin -cp .:build InheritedInstanceMethod
./native-image.bin
```

### Output ✅

```
SUCCESS: found instance method getId() in java.time.ZoneRegion.
```
----

# Inherited Static Method

## Compile and Run with standard JRE

```shell
javac InheritedStaticMethod.java
java InheritedStaticMethod
```

### Output ✅

```
SUCCESS: found static method of(String) in java.time.ZoneRegion.
```

## Build and Run Native Image

```shell
native-image --no-fallback -H:Name=native-image.bin InheritedStaticMethod
./native-image.bin
```

### Output ❌

```
Exception in thread "main" java.lang.NoSuchMethodException: java.time.ZoneRegion.of(java.lang.String)
	at java.lang.Class.getMethod(DynamicHub.java:1053)
	at Tester.staticMethod(InheritedStaticMethod.java:38)
	at Tester.performTest(InheritedStaticMethod.java:34)
	at InheritedStaticMethod.main(InheritedStaticMethod.java:24)
```

## Run with Native Image Agent

```shell
java -agentlib:native-image-agent=config-output-dir=build/META-INF/native-image InheritedStaticMethod
```

### Result

Generated `reflection-config.json` contains:

```json
[
	{
	  "name":"java.time.ZoneId",
	  "methods":[{"name":"of","parameterTypes":["java.lang.String"] }]
	}
]
```

## Build and Run Native Image with result of Agent

```shell
native-image --no-fallback -H:Name=native-image.bin -cp .:build InheritedStaticMethod
./native-image.bin
```

### Output ❌

```
Exception in thread "main" java.lang.NoSuchMethodException: java.time.ZoneRegion.of(java.lang.String)
	at java.lang.Class.getMethod(DynamicHub.java:1053)
	at Tester.staticMethod(InheritedStaticMethod.java:38)
	at Tester.performTest(InheritedStaticMethod.java:34)
	at InheritedStaticMethod.main(InheritedStaticMethod.java:24)
```

## Introduce manual reflection configuration ⚠️

Contents of `./manual-reflect-config.json`:

```json
[
	{
		"name": "java.time.ZoneRegion"
	}
]
```

# Build and Run Native Image with result of Agent plus manual config

```shell
native-image --no-fallback -H:ReflectionConfigurationFiles=manual-reflect-config.json -H:Name=native-image.bin -cp .:build InheritedStaticMethod
./native-image.bin
```

### Output ✅

```
SUCCESS: found static method of(String) in java.time.ZoneRegion.
```

----

# Inherited Static and Instance Methods

## Compile and Run

```shell
javac InheritedStaticAndInstanceMethods.java
java InheritedStaticAndInstanceMethods
```

### Output ✅

```
SUCCESS: found instance method getId() in java.time.ZoneRegion.
SUCCESS: found static method of(String) in java.time.ZoneRegion.
```

## Build and Run Native Image

```shell
native-image --no-fallback -H:Name=native-image.bin InheritedStaticAndInstanceMethods
./native-image.bin
```

### Output ❌

```
Exception in thread "main" java.lang.NoSuchMethodException: java.time.ZoneRegion.getId()
	at java.lang.Class.getMethod(DynamicHub.java:1053)
	at Tester.instanceMethod(InheritedStaticAndInstanceMethods.java:39)
	at Tester.performTest(InheritedStaticAndInstanceMethods.java:34)
	at InheritedStaticAndInstanceMethods.main(InheritedStaticAndInstanceMethods.java:24)
```

## Run with Native Image Agent

```shell
java -agentlib:native-image-agent=config-output-dir=build/META-INF/native-image InheritedStaticAndInstanceMethods
```

### Result

Generated `reflection-config.json` contains only:

```json
[
	{
	  "name":"java.time.ZoneId",
	  "methods":[{"name":"of","parameterTypes":["java.lang.String"] }]
	},
	{
	  "name":"java.time.ZoneRegion",
	  "methods":[{"name":"getId","parameterTypes":[] }]
	}
]
```

## Build and Run Native Image with result of Agent

```shell
native-image --no-fallback -H:Name=native-image.bin -cp .:build InheritedStaticAndInstanceMethods
./native-image.bin
```

### Output ✅

```
SUCCESS: found instance method getId() in java.time.ZoneRegion.
SUCCESS: found static method of(String) in java.time.ZoneRegion.
```

----

# Source Code

```java
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.ZoneId;

public class InheritedInstanceMethod {

	public static void main(String[] args) throws Exception {
		new Tester().performTest();
	}
}

class Tester {

	void performTest() throws Exception {
		// Note: ZoneId.of() returns an instance of java.time.ZoneRegion.
		ZoneId zoneId = ZoneId.of("GMT+1");
		Class<?> clazz = zoneId.getClass();
		instanceMethod(clazz);
	}

	private void instanceMethod(Class<?> clazz) throws NoSuchMethodException {
		Method method = clazz.getMethod("getId");
		if (method != null && !Modifier.isStatic(method.getModifiers())) {
			System.out.format("SUCCESS: found instance method getId() in %s.%n", clazz.getName());
		}
		else {
			System.err.format("FAILURE: did not find instance method getId() in %s.%n", clazz.getName());
		}
	}

}
```

```java
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.ZoneId;

public class InheritedStaticMethod {

	public static void main(String[] args) throws Exception {
		new Tester().performTest();
	}
}

class Tester {

	void performTest() throws Exception {
		// Note: ZoneId.of() returns an instance of java.time.ZoneRegion.
		ZoneId zoneId = ZoneId.of("GMT+1");
		Class<?> clazz = zoneId.getClass();
		staticMethod(clazz);
	}

	private void staticMethod(Class<?> clazz) throws NoSuchMethodException {
		Method method = clazz.getMethod("of", String.class);
		if (method != null && Modifier.isStatic(method.getModifiers())) {
			System.out.format("SUCCESS: found static method of(String) in %s.%n", clazz.getName());
		}
		else {
			System.err.format("FAILURE: did not find static method of(String) in %s.%n", clazz.getName());
		}
	}

}
```

```java
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.ZoneId;

public class InheritedStaticAndInstanceMethods {

	public static void main(String[] args) throws Exception {
		new Tester().performTest();
	}
}

class Tester {

	void performTest() throws Exception {
		// Note: ZoneId.of() returns an instance of java.time.ZoneRegion.
		ZoneId zoneId = ZoneId.of("GMT+1");
		Class<?> clazz = zoneId.getClass();
		instanceMethod(clazz);
		staticMethod(clazz);
	}

	private void instanceMethod(Class<?> clazz) throws NoSuchMethodException {
		Method method = clazz.getMethod("getId");
		if (method != null && !Modifier.isStatic(method.getModifiers())) {
			System.out.format("SUCCESS: found instance method getId() in %s.%n", clazz.getName());
		}
		else {
			System.err.format("FAILURE: did not find instance method getId() in %s.%n", clazz.getName());
		}
	}

	private void staticMethod(Class<?> clazz) throws NoSuchMethodException {
		Method method = clazz.getMethod("of", String.class);
		if (method != null && Modifier.isStatic(method.getModifiers())) {
			System.out.format("SUCCESS: found static method of(String) in %s.%n", clazz.getName());
		}
		else {
			System.err.format("FAILURE: did not find static method of(String) in %s.%n", clazz.getName());
		}
	}

}
```
