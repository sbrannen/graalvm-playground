# GraalVM Issue

https://github.com/oracle/graal/issues/2234

----

# Setup

```shell
export GRAALVM_HOME=/opt/graalvm/graalvm-ce-java8-20.0.0/Contents/Home
export PATH=$GRAALVM_HOME/bin:$PATH
export JAVA_HOME=$GRAALVM_HOME
```

# Compile

`javac TimeZoneIdParsing.java`

# Run

`java TimeZoneIdParsing`

## Output

```
SUCCESS: 'GMT+2' was correctly parsed into 'GMT+02:00'.
```

# Build Native Image

`native-image -H:Name=native-image.bin TimeZoneIdParsing`

## Execute Native Image

`./native-image.bin`

## Output

```
FAILURE: expected 'GMT+2' to be parsed into 'GMT+02:00' but got 'GMT'.
```

# Build Native Image with `-H:+IncludeAllTimeZones`

`native-image -H:Name=native-image.bin -H:+IncludeAllTimeZones TimeZoneIdParsing`

## Execute Native Image

`./native-image.bin`

## Output

```
FAILURE: expected 'GMT+2' to be parsed into 'GMT+02:00' but got 'GMT'.
```
