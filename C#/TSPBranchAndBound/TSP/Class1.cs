using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TSP
{
    class PriorityQueue
    {
        List<Node> heap;
        int lastNode;
        public int maxStored = 0;

        public PriorityQueue()
        {
            this.heap = new List<Node>();
            this.lastNode = 1;
            Node blank = null;
            heap.Add(blank);
        }
        ~PriorityQueue()
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
            heap.Add(node);
            lastNode++;
            if(heap.Count > maxStored)
            {
                maxStored = heap.Count;
            }
        }

        public Node removeMin()
        {
            Node minNode = heap[1];
            heap[1] = heap[lastNode - 1];
            heap.RemoveAt(lastNode - 1);
            lastNode--;
            if (lastNode != 1)
            {
                percolateDown(1);
            }
            return minNode;
        }

        //move a node up the tree until it is no longer smaller than it's parent node. Big-O of (logn)
        void percolateUp(int heapIndex)
        {
            int nodeIndex = heap[heapIndex].name;
            while (heapIndex > 1)
            {
                int parentIndex = getParentIndex(heapIndex);
                if (hasLessDistance(heap[heapIndex].lowerBound, heap[parentIndex].lowerBound))
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
            int nodeIndex = heap[heapIndex].name;
            while (heapIndex < lastNode)
            {
                int leftChildIndex = getLeftChildIndex(heapIndex);
                int rightChildIndex = getRightChildIndex(heapIndex);
                if (rightChildIndex < lastNode)
                {
                    if (hasLessDistance(heap[leftChildIndex].lowerBound, heap[rightChildIndex].lowerBound))
                    {
                        if (hasLessDistance(heap[leftChildIndex].lowerBound, heap[heapIndex].lowerBound))
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
                        if (hasLessDistance(heap[rightChildIndex].lowerBound, heap[heapIndex].lowerBound))
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
                    if (hasLessDistance(heap[leftChildIndex].lowerBound, heap[heapIndex].lowerBound))
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
            if (distance1 == -1 && distance2 != -1)
            {
                return false;
            }
            if (distance1 != -1 && distance2 == -1)
            {
                return true;
            }
            if (distance1 < distance2)
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
        }
        int getParentIndex(int index)
        {
            return index / 2;
        }
        int getLeftChildIndex(int index)
        {
            return index * 2;
        }
        int getRightChildIndex(int index)
        {
            return ((index * 2) + 1);
        }
    }
}
