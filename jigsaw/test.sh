# compile
mkdir -p build
javac -encoding UTF-8 -d build -cp src src/Runners/*.java

# run
java -cp build Runners.RunnerDemo
java -cp build Runners.RunnerPart1
java -cp build Runners.RunnerPart2

echo