# !/bin/bash

javac --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls -d ./bin ./src/*.java
java -cp bin:img --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls Pendu