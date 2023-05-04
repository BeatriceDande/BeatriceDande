//
//  ConcurrentQueueTests.hpp
//  ThreadSafeQueue
//
//  Created by Dande on 4/8/23.
//

#pragma once
#include "ConcurrentQueue.hpp"
#include <thread>
#include <vector>


bool testQueue(int numProducers, int numConsumers, int numOfInts);


