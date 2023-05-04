//
//  SerialQueueTest.cpp
//  ThreadSafeQueue
//
//  Created by Dande on 4/7/23.
//

#include "SerialQueueTest.hpp"

#include <stdio.h>
#include <iostream>


//test for static allocation in serial Queue
 void staticSerialQueueTestInts(){
     std::cout<<"\nSTATIC TEST\n\n";
    
    SerialQueue<int> queue;
    std::cout<<"Queue decleared empty: \n";
    queue.printQueue();
    std::cout<<"\n";
    assert(queue.size()==0);
    queue.enqueue(2);
    queue.enqueue(3);
    queue.enqueue(4);
    queue.enqueue(5);
    assert(queue.size()==4);
    std::cout<<"Queue after enqueuing 4 items: \n";
    queue.printQueue();
    std::cout<<"\n";


     int ptr1;
     queue.dequeue(&ptr1);
     std::cout<<"Queue after dequeuing 1 items: \n";
     queue.printQueue();
     std::cout<<"\n";
     assert(ptr1==2);
     assert(queue.size()==3);
     queue.dequeue(&ptr1);
     assert(ptr1==3);
     queue.dequeue(&ptr1);
     assert(ptr1==4);
     queue.dequeue(&ptr1);
     assert(ptr1==5);
  
     std::cout<<"Queue after dequeuing all items: \n";
     queue.printQueue();
     
     std::cout<<"\n++++++++++++++++++++++++++++++++++++++++++++\n";
     std::cout<<"++++++++++++++++++++++++++++++++++++++++++++\n\n";
     
    
}


//testing dynamic allocation in serial Queue
 void dynamicSerialQueueTestInts(){
     std::cout<<"\nDYNAMIC TEST\n\n";
    
    SerialQueue<int>* queue2 = new SerialQueue<int>();
    queue2->printQueue();
    std::cout<<"\n";
    assert(queue2->size()==0);
    int item1;

    queue2->enqueue(5);
    queue2->enqueue(4);
     std::cout<<"Queue after enqueuing 2 items: \n";
    queue2->printQueue();
    std::cout<<"\n";
    assert(queue2->size()==2);
    queue2->dequeue(&item1);
    assert(item1==5);
    assert(queue2->size()==1);
     std::cout<<"Queue after enqueuing 1 items: \n";
    queue2->printQueue();
    std::cout<<"\n";
     std::cout<<"Queue after enqueuing all items: \n";
     queue2->dequeue(&item1);
     queue2->printQueue();
    
    
}


