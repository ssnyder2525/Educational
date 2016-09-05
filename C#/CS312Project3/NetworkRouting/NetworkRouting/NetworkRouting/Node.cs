using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Drawing;
using System.Drawing.Drawing2D;

namespace NetworkRouting
{
    class Node
    {
        int name;
        double distance = -1;
        int prev = -1;
        PointF point;
        bool visited = false;
        bool popped = false;

        public bool hasBeenVisited()
        {
            return this.visited;
        }

        public void setVisited()
        {
            this.visited = true;
        }

        public bool isPopped()
        {
            return this.popped;
        }

        public void setPopped()
        {
            this.popped = true;
        }

        public Node(int name, PointF point)
        {
            this.point = point;
            this.name = name;
        }
        ~Node() { }

        public int getName()
        {
            return this.name;
        }

        public PointF getPoint()
        {
            return this.point;
        }

        public double getDistance()
        {
            return this.distance;
        }

        public int getPrev()
        {
            return this.prev;
        }

        public double findDistance(PointF otherPoint)
        {
            double xDelta = this.point.X - otherPoint.X;
            double yDelta = this.point.Y - otherPoint.Y;
            return Math.Sqrt(Math.Pow(xDelta, 2) + Math.Pow(yDelta, 2));
        }

        public void setDistance(double distance)
        {
            this.distance = distance;
        }

        public void setPrev(int i)
        {
            this.prev = i;
        }
    }
}
