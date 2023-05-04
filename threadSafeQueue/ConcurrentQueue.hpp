//
//  ConcurrentQueue.hpp
//  ThreadSafeQueue
//
//  Created by Dande on 4/7/23.
//

//#ifndef ConcurrentQueue_hpp
//#define ConcurrentQueue_hpp

#pragma once

#include <stdio.h>
#include <mutex>
#include <iostream>



template <typename T>
class ConcurrentQueue{
private:
  struct Node{
    T data;
    Node* next;
  };

  Node* head;
  Node* tail;
  int size_;
    std::mutex mtx;

public:
    ConcurrentQueue()
    :head(new Node{T{}, nullptr}), size_(0)
  {
    tail = head;
  }


  void enqueue(const T& x)
    {
     
          Node* temp = new Node();
          temp->data = x;
          std::unique_lock<std::mutex> lck (mtx);
          tail->next = temp;
          tail = temp;
          size_++;
          
  }

  bool dequeue(T* ret){
      
      mtx.lock();
      if (head->next==nullptr ) {
          mtx.unlock();
          return false;
      }
     
      else {
          Node* temp = head->next;
          *ret = (temp->data);
          head->next = head->next->next;
          size_ --;
          mtx.unlock();
          delete temp;
          return true;
          
      }
  }
    
    void printQueue(){
        Node* temp=head->next;
        std::cout<<"QueueSize: "<<size_<<"\n";
        std::cout<<"Queue: head->";
        while (temp) {
            std::cout<<temp->data<<"->";
            temp = temp->next;
        }
        std::cout<<"\n";
    }

  ~ConcurrentQueue(){

    while(head){
      Node* temp = head->next;
      delete head;
      head = temp;
    }
  }

  int size() const{ return size_;}
};



