# Compiles all the java files into class files
.PHONY: compile
compile:
	mkdir -p class
	javac ./src/*.java -d ./class

# Cleans the current directory
.PHONY: clean
clean:
	rm -f ./class/*.*~ ./class/*.class ./class/*~
	rm -rf ./class

# 'all' target:
# First cleans the directory then compiles the java file
.PHONY: all
all: clean compile