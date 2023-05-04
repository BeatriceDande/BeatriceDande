CFLAGS = -std=c++11 #-fsanitize=undefined
CXX = clang++

exc:main.o SerialQueueTest.o  ConcurrentQueueTests.o
	${CXX} ${CFLAGS} main.o  SerialQueueTest.o  ConcurrentQueueTests.o -o exc
	
main.o:main.cpp
	${CXX} ${CFLAGS} -c main.cpp
	
SerialQueueTest.o:SerialQueue.hpp SerialQueueTest.cpp
	${CXX} ${CFLAGS} -c SerialQueueTest.cpp
	
ConcurrentQueueTests.o:ConcurrentQueueTests.hpp ConcurrentQueue.hpp
	${CXX} ${CFLAGS} -c ConcurrentQueueTests.cpp

Value.o:Value.cpp Value.hpp
	${CXX} ${CFLAGS} -c Value.cpp
	
	

.PHONY: clean test doc

clean:
	rm -f *.o *.out exc


