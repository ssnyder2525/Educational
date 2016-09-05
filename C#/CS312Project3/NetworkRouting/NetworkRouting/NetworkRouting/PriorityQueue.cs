using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace NetworkRouting
{
    class PriorityQueue
    {
        int[] nodeIndexes;
        Node[] heap;
        int lastNode;

        public PriorityQueue(int nodeCount)
        {
            this.nodeIndexes = new int[nodeCount + 1];
            this.heap = new Node[nodeCount + 1];
            this.lastNode = 1;
        }
        ~PriorityQueue()
        {

        }

        public void makeQueue()
        {

        }

        public bool isEmpty()
        {
            if (lastNode == 1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        public void insert(Node node)
        {
            heap[lastNode] = node;
            nodeIndexes[node.getName()] = lastNode;
            lastNode++;
        }

        public Node removeMin()
        {
            Node minNode = heap[1];
            heap[1].setPopped();
            nodeIndexes[heap[1].getName()] = 0;
            heap[1] = heap[lastNode-1];
            heap[lastNode - 1] = null;
            lastNode--;
            if (lastNode != 1)
            {
                percolateDown(1);
            }
            return minNode;
        }

        public void decreaseKey(int nodeIndex, double newDistance)
        {
            int heapIndex = nodeIndexes[nodeIndex];
            heap[heapIndex].setDistance(newDistance);
            percolateUp(heapIndex);
        }

        //move a node up the tree until it is no longer smaller than it's parent node. Big-O of (logn)
        void percolateUp(int heapIndex)
        {
            int nodeIndex = heap[heapIndex].getName();
            while (heapIndex > 1)
            {
                int parentIndex = getParentIndex(heapIndex);
                if (hasLessDistance(heap[heapIndex].getDistance(), heap[parentIndex].getDistance()))
                {
                    updateArrays(heapIndex, parentIndex);
                    heapIndex = parentIndex;
                }
                else
                {
                    break;
                }
            }
        }

        //Big-O of (logn)
        void percolateDown(int heapIndex)
        {
            int nodeIndex = heap[heapIndex].getName();
            while (heapIndex < lastNode)
            {
                int leftChildIndex = getLeftChildIndex(heapIndex);
                int rightChildIndex = getRightChildIndex(heapIndex);
                if (rightChildIndex < lastNode)
                {
                    if (hasLessDistance(heap[leftChildIndex].getDistance(), heap[rightChildIndex].getDistance()))
                    {
                        if (hasLessDistance(heap[leftChildIndex].getDistance(), heap[heapIndex].getDistance()))
                        {
                            updateArrays(heapIndex, leftChildIndex);
                            heapIndex = leftChildIndex;
                        }
                        else
                        {
                            break;
                        }
                    }
                    else
                    {
                        if (hasLessDistance(heap[rightChildIndex].getDistance(), heap[heapIndex].getDistance()))
                        {
                            updateArrays(heapIndex, rightChildIndex);
                            heapIndex = rightChildIndex;
                        }
                        else
                        {
                            break;
                        }
                    }
                }
                else if (leftChildIndex < lastNode)
                {
                    if (hasLessDistance(heap[leftChildIndex].getDistance(), heap[heapIndex].getDistance()))
                    {
                        updateArrays(heapIndex, leftChildIndex);
                        heapIndex = leftChildIndex;
                    }
                    else
                    {
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
        }

        bool hasLessDistance(double distance1, double distance2)
        {
            if(distance1 == -1 && distance2 != -1)
            {
                return false;
            }
            if(distance1 != -1 && distance2 == -1)
            {
                return true;
            }
            if(distance1 < distance2)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        void updateArrays(int startIndex, int destinationIndex)
        {
            Node temp = heap[destinationIndex];
            heap[destinationIndex] = heap[startIndex];
            heap[startIndex] = temp;
            nodeIndexes[heap[destinationIndex].getName()] = destinationIndex;
            nodeIndexes[heap[startIndex].getName()] = startIndex;
        }
        int getParentIndex(int index)
        {
            return index/2;
        }
        int getLeftChildIndex(int index)
        {
            return index*2;
        }
        int getRightChildIndex(int index)
        {
            return ((index*2)+1);
        }
    }
}
