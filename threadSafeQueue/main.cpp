//
//  main.cpp
//  ThreadSafeQueue
//
//  Created by Dande on 4/3/23.
//

//#include <iostream>

#include "SerialQueueTest.hpp"
//#include "ConcurrentQueue.hpp"

#include "ConcurrentQueueTests.hpp"


//to validate input
bool validInput(int argc, const char * argv[]){
    
    int i;
    for (i=2; i<argc; i++) {
        const char* p =argv[i];
        while (*p!='\0') {
            if (*p<'0'||*p>'9') {
                return false;
            }
            else{
                p++;
            }
        }
    }
    return true;
   
}

int main(int argc, const char * argv[]) {
  
    int numberOfProducers = 0;
    int numberOfConsumers =0;
    int numberOfInts=0;
    
    if(argc<1||argc>4){
        std::perror("invalid Input");
        exit(-9);
    }
    if(argc>1&&argc<4){
        std::perror("invalid Input");
        exit(-9);
    }
    
    if (argc == 1) {//run serial queue as defualt
        staticSerialQueueTestInts();
        dynamicSerialQueueTestInts();
    }
    
    else if(argc==4){ //for testing Concurrent queue
        if(!validInput(argc, argv)){
            std::perror("invalid Input");
            exit(-9);
        }
        else{
            numberOfProducers = atoi(argv[1]);
            numberOfConsumers = atoi(argv[2]);
            numberOfInts = atoi(argv[3]);
        }
        
        std::cout<<testQueue(numberOfProducers, numberOfConsumers, numberOfInts)<<"\n";
        
    }
    

    return 0;
    
    

    
    
    
}
