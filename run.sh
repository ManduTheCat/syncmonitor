#!/bin/bash

# Function to find Java 1.8
find_java() {
    potential_java_paths=(
        "/usr/java"
        "/usr/lib/jvm"
        "/opt/java"
        "/opt/jdk"
        "/usr/local/java"
    )
    for base_path in "${potential_java_paths[@]}"; do
        if [ -d "$base_path" ]; then
            for java_bin in $(find "$base_path" -type f -name "java"); do
                if [ -x "$java_bin" ]; then
                    java_version=$("$java_bin" -version 2>&1 | grep "version" | awk -F '"' '{print $2}')
                    if [[ $java_version == 1.8* ]]; then
                        echo "$java_bin"
                        return 0
                    fi
                fi
            done
        fi
    done
    echo ""
    return 1
}

# Find Java 1.8
JAVA_BIN=$(find_java)

if [ -z "$JAVA_BIN" ]; then
    echo "Java 1.8 not found! Please install Java 1.8 and try again."
    exit 1
fi

echo "Using Java: $JAVA_BIN"

# Run the Java application
"$JAVA_BIN" -Dconfig.file=./config.yml -Dfile.encoding=UTF-8 -jar ProSyncMonitor-1.0.jar

