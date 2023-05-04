//
//  SerialQueue.hpp
//  ThreadSafeQueue
//
//  Created by Dande on 4/3/23.
//

#pragma once
#include <iostream>
#include <stdio.h>

template <typename T>
class SerialQueue{
private:
  struct Node{
    T data;
    Node* next;
  };

  Node* head;
  Node* tail;
  int size_;

public:
  SerialQueue()
    :head(new Node{T{}, nullptr}), size_(0)
  {
    tail = head;
  }


  void enqueue(const T& x)
    {

          Node* temp = new Node();
          temp->data = x;
          tail->next = temp;
          tail = temp;
          size_++;
          
  }

  bool dequeue(T* ret){
      if (head->next==nullptr ) {
          return false;
      }
      else {
          Node* temp = head->next;
          *ret = (temp->data);
          head->next = head->next->next;
          delete temp;
          size_ --;
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

  ~SerialQueue(){

    while(head){
      Node* temp = head->next;
      delete head;
      head = temp;
    }
  }

  int size() const{ return size_;}
};


