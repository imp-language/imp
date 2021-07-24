# Imp

Imp is a general-purpose programming language originally implemented as a compiler for the Java Virtual Machine.

Imp is under development and functionality can be expected to change rapidly.

## About

I'm working on Imp to learn more about programming languages while creating a language myself and others could use for
personal projects. I'm blogging my progress [here](https://matthall.codes/tags/imp/).

## Usage

Currently, to test Imp projects I'd recommend you use Intellij. Then you can run the `org.imp.jvm.tool.Compiler` class
with an input filename to compile and execute Imp code.

### Notes

How to compile Java files using the Imp runtime:

```bash
javac --enable-preview -source 14 -cp .compile;target/classes .compile/main/Example.java
```