#!/bin/bash

# Define the input and output paths
INPUT_FILE="src/main/resources/application.properties"  # Path to your application.properties file
OUTPUT_FILE="../application.properties.tmpl"  # Output file in the project root

# Check if application.properties exists
if [ ! -f "$INPUT_FILE" ]; then
  echo "Error: $INPUT_FILE does not exist."
  exit 1
fi

# Generate the application.properties.tmpl file with secret placeholders
echo "Generating $OUTPUT_FILE..."

# Replace datasource URL, username, and password with the placeholders
sed 's|spring.datasource.url=.*|spring.datasource.url={{ secret "datasource_url" }}|' $INPUT_FILE > $OUTPUT_FILE
sed -i '' 's|spring.datasource.username=.*|spring.datasource.username={{ secret "datasource_username" }}|' $OUTPUT_FILE
sed -i '' 's|spring.datasource.password=.*|spring.datasource.password={{ secret "datasource_password" }}|' $OUTPUT_FILE

# Verify the file was created
if [ -f "$OUTPUT_FILE" ]; then
  echo "File generated successfully: $OUTPUT_FILE"
else
  echo "Error: $OUTPUT_FILE was not generated."
  exit 1
fi
