//
//  ConcurrentQueueTests.cpp
//  ThreadSafeQueue
//
//  Created by Dande on 4/8/23.
//

#include "ConcurrentQueueTests.hpp"




bool testQueue(int numProducers, int numConsumers, int numOfInts)
{

    ConcurrentQueue<int> concurrentQ;
    std::vector<std::thread> myThreads;
    //let producers add to the queue
    for(int j= 0; j<numProducers; j++){
    
        myThreads.push_back(std::thread([ &concurrentQ, &numOfInts]()
                {
            for(int i=0; i<numOfInts; i++){
                concurrentQ.enqueue(i);

            }
        
                }));
        
    }

    //join producers after adding to queue
    for(auto &t:myThreads){
        t.join();
    }
    
    //let consummers start dequeuing
    for(int i= 0; i<numConsumers; i++){
        int counter=0;
       
        myThreads.push_back(std::thread([&counter, &concurrentQ, &numOfInts]()
                {
            for(int i=0; i<numOfInts; i++){
                concurrentQ.dequeue(&i);
                    continue;
               
            }
           
        
                }));
    }
        
    //join all the threads not joined already
    for(auto &t:myThreads){
        if(t.joinable()){
            t.join();
        }
       
    }
    
    //once both consummer and producer threads are done,
    //size of the queue should equal 0
    return concurrentQ.size()==0;

}
    
