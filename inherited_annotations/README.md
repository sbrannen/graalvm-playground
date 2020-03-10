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
javac InheritedAnnotations.java
java InheritedAnnotations
```

### Output ✅

```
SUCCESS: @InheritedAnnotation was found as an annotation on Superclass.
SUCCESS: @InheritedAnnotation was found as a declared annotation on Superclass.
SUCCESS: @InheritedAnnotation was found as an annotation on Subclass.
SUCCESS: @InheritedAnnotation was found as a declared annotation on Subclass.
```

## Build and Run Native Image

```shell
native-image --no-fallback -H:Name=native-image.bin InheritedAnnotations
./native-image.bin
```

### Output ❌

```
SUCCESS: @InheritedAnnotation was found as an annotation on Superclass.
SUCCESS: @InheritedAnnotation was found as a declared annotation on Superclass.
SUCCESS: @InheritedAnnotation was found as an annotation on Subclass.
FAILURE: @InheritedAnnotation was NOT found as a declared annotation on Subclass.
```

## Run with Native Image Agent

```shell
java -agentlib:native-image-agent=config-output-dir=build/META-INF/native-image InheritedAnnotations
```

### Result

Generated `reflection-config.json` is empty:

```json
```

## Build and Run Native Image with result of Agent

```shell
native-image --no-fallback -H:Name=native-image.bin -cp .:build InheritedAnnotations
./native-image.bin
```

### Output ❌

It still fails, and this is to be expected since the agent didn't generate anything
in `reflection-config.json`.

```
SUCCESS: @InheritedAnnotation was found as an annotation on Superclass.
SUCCESS: @InheritedAnnotation was found as a declared annotation on Superclass.
SUCCESS: @InheritedAnnotation was found as an annotation on Subclass.
FAILURE: @InheritedAnnotation was NOT found as a declared annotation on Subclass.
```

----

# Source Code

```java
import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class InheritedAnnotations {

	public static void main(String[] args) {
		findAnnotation(Superclass.class);
		findAnnotation(Subclass.class);
	}

	private static void findAnnotation(Class<?> clazz) {
		Annotation[] annotations = clazz.getAnnotations();
		if (annotations.length == 1 && annotations[0].annotationType().equals(InheritedAnnotation.class)) {
			System.out.format("SUCCESS: @InheritedAnnotation was found as an annotation on %s.%n",
				clazz.getSimpleName());
		}
		else {
			System.out.format("FAILURE: @InheritedAnnotation was NOT found as an annotation on %s.%n",
				clazz.getSimpleName());
		}
		Annotation[] declaredAnnotations = clazz.getDeclaredAnnotations();
		if (declaredAnnotations.length == 1
				&& declaredAnnotations[0].annotationType().equals(InheritedAnnotation.class)) {
			System.out.format("SUCCESS: @InheritedAnnotation was found as a declared annotation on %s.%n",
				clazz.getSimpleName());
		}
		else {
			System.out.format("FAILURE: @InheritedAnnotation was NOT found as a declared annotation on %s.%n",
				clazz.getSimpleName());
		}
	}


	@Retention(RetentionPolicy.RUNTIME)
	@Inherited
	@interface InheritedAnnotation {
	}

	@InheritedAnnotation
	static class Superclass {
	}

	@InheritedAnnotation
	static class Subclass extends Superclass {
	}

}
```
