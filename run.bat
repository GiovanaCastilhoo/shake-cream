@echo off

echo Subindo backend...
start cmd /k "mvn -pl backend exec:java"

timeout /t 3

echo Subindo frontend...
start cmd /k "mvn -pl frontend javafx:run"