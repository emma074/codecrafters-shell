# CodeCrafters Shell – Java

This is a shell implementation built in Java as part of the [CodeCrafters](https://codecrafters.io) challenge.

It replicates the basic functionality of common Unix shells like `bash` or `sh`, including support for running commands, handling built-ins, and more.

## 🚀 Features

- Execute basic shell commands (e.g., `ls`, `echo`, `pwd`, etc.)
- Built-in command handling (e.g., `cd`, `exit`)
- Process spawning using Java's `ProcessBuilder`
- REPL (Read-Eval-Print Loop) interface
- Basic error handling

## 🛠️ Getting Started

### Prerequisites

- Java 11 or higher
- Maven (or your preferred Java build tool)

### Running the Shell

```bash
# Compile and run
javac src/Main.java
java src.Main
