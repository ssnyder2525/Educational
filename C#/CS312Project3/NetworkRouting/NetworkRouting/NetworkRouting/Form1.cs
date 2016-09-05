using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Diagnostics;

namespace NetworkRouting
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void generateButton_Click(object sender, EventArgs e)
        {
            int randomSeed = int.Parse(randomSeedBox.Text);
            int size = int.Parse(sizeBox.Text);

            Random rand = new Random(randomSeed);
            seedUsedLabel.Text = "Random Seed Used: " + randomSeed.ToString();

            this.adjacencyList = generateAdjacencyList(size, rand);
            List<PointF> points = generatePoints(size, rand);
            resetImageToPoints(points);
            this.points = points;
            startNodeIndex = -1;
            stopNodeIndex = -1;
            sourceNodeBox.Text = "";
            targetNodeBox.Text = "";
        }

        // Generates the distance matrix.  Values of -1 indicate a missing edge.  Loopbacks are at a cost of 0.
        private const int MIN_WEIGHT = 1;
        private const int MAX_WEIGHT = 100;
        private const double PROBABILITY_OF_DELETION = 0.35;

        private const int NUMBER_OF_ADJACENT_POINTS = 3;

        private List<HashSet<int>> generateAdjacencyList(int size, Random rand)
        {
            List<HashSet<int>> adjacencyList = new List<HashSet<int>>();

            for (int i = 0; i < size; i++)
            {
                HashSet<int> adjacentPoints = new HashSet<int>();
                while (adjacentPoints.Count < 3)
                {
                    int point = rand.Next(size);
                    if (point != i) adjacentPoints.Add(point);
                }
                adjacencyList.Add(adjacentPoints);
            }
            return adjacencyList;
        }

        private List<PointF> generatePoints(int size, Random rand)
        {
            List<PointF> points = new List<PointF>();
            for (int i = 0; i < size; i++)
            {
                points.Add(new PointF((float) (rand.NextDouble() * pictureBox.Width), (float) (rand.NextDouble() * pictureBox.Height)));
            }
            return points;
        }

        private void resetImageToPoints(List<PointF> points)
        {
            pictureBox.Image = new Bitmap(pictureBox.Width, pictureBox.Height);
            Graphics graphics = Graphics.FromImage(pictureBox.Image);
            foreach (PointF point in points)
            {
                graphics.DrawEllipse(new Pen(Color.Blue), point.X, point.Y, 2, 2);
            }

            this.graphics = graphics;
            pictureBox.Invalidate();
        }

        // These variables are instantiated after the "Generate" button is clicked
        private List<PointF> points = new List<PointF>();
        private Graphics graphics;
        private List<HashSet<int>> adjacencyList;
        private List<Node> nodes = new List<Node>();

        // Use this to generate routing tables for every node
        private void solveButton_Click(object sender, EventArgs e)
        {
            nodes.Clear();
            if (startNodeIndex == -1)
                return;
            
            //create all the nodes in the graph
            //BigO of (n)
            for(int i = 0; i < points.Count; i++)
            {
                    Node newNode = new Node(i, points[i]);
                    nodes.Add(newNode);
            }
            
            //create the priority queue
            PriorityQueue pq = new PriorityQueue(nodes.Count);

            Stopwatch watch = new Stopwatch();
            watch.Start();
            
            //add nodes. BigO of (n)
            for (int i = 0; i < nodes.Count; i++)
            {
                pq.insert(nodes[i]);
                nodes[i].setVisited();
            }
            //run Dijkstra
            pq.decreaseKey(startNodeIndex, 0);
            //do this until the priority queue is empty. BigO (nlogn)
            while (!pq.isEmpty())
            {
                //pop of closest node to current node.BigO (logn)
                Node currentNode = pq.removeMin();
                //for each edge leading from current node BigO (n).
                for (int i = 0; i < 3; i++)
                {
                    int destinationIndex = adjacencyList[currentNode.getName()].ElementAt(i);
                    //do nothing if the edge leads to a popped node.
                    if (nodes[destinationIndex].isPopped())
                    {
                        continue;
                    }
                    //get the length of the edge
                    double distance = currentNode.findDistance(nodes[destinationIndex].getPoint());
                    //if the destination node's distance is farther than the path through the current node, replace it's distance and change it's parent. Update the heap accordingly. BigO (logn)
                    if (nodes[destinationIndex].getDistance() > currentNode.getDistance() + distance || nodes[destinationIndex].getDistance() == -1)
                    {
                        nodes[destinationIndex].setDistance(currentNode.getDistance() + distance);
                        nodes[destinationIndex].setPrev(currentNode.getName());
                        pq.decreaseKey(destinationIndex, currentNode.getDistance() + distance);
                    }
                }
            }
            watch.Stop();
            double allTime = watch.Elapsed.TotalSeconds;


            nodes.Clear();
            for (int i = 0; i < points.Count; i++)
            {
                Node newNode = new Node(i, points[i]);
                nodes.Add(newNode);
            }

            //run Path Dijkstra
            PriorityQueue pq2 = new PriorityQueue(nodes.Count);
            Stopwatch watch2 = new Stopwatch();
            watch2.Start();
            //add start node
            nodes[startNodeIndex].setDistance(0);
            pq2.insert(nodes[startNodeIndex]);
            nodes[startNodeIndex].setVisited();
            while(!pq2.isEmpty())
            {
                //pop of closest node to current node.
                Node currentNode = pq2.removeMin();
                if (currentNode.getName() == stopNodeIndex)
                {
                    watch2.Stop();
                    double pathTime = watch2.Elapsed.TotalSeconds;
                    printPath(ref pq2, pathTime, allTime);
                    return;
                }
                //for each edge leading from current node
                for(int i = 0; i < 3; i++)
                {
                    int destinationIndex = adjacencyList[currentNode.getName()].ElementAt(i);
                    //add the node the edge leads to if it has never been on the queue
                    if (!nodes[destinationIndex].hasBeenVisited())
                    {
                        pq2.insert(nodes[destinationIndex]);
                        nodes[destinationIndex].setVisited();
                    }
                    //do nothing if the edge leads to a popped node.
                    if(nodes[destinationIndex].isPopped())
                    {
                        continue;
                    }
                    //get the length of the edge
                    double distance = currentNode.findDistance(nodes[destinationIndex].getPoint());
                    //if the destination node's distance is farther than the path through the current node, replace it's distance and change it's parent. Update the heap accordingly.
                    if (nodes[destinationIndex].getDistance() > currentNode.getDistance() + distance || nodes[destinationIndex].getDistance() == -1)
                    {
                        nodes[destinationIndex].setDistance(currentNode.getDistance() + distance);
                        nodes[destinationIndex].setPrev(currentNode.getName());
                        pq2.decreaseKey(destinationIndex, currentNode.getDistance() + distance);
                    }
                }
            }
            watch2.Stop();
            double pathTime2 = watch2.Elapsed.TotalSeconds;
            pathCostBox.Text = "unreachable";
            oneTimeBox.Text = pathTime2.ToString();
            allTimeBox.Text = allTime.ToString();
            double percentage = ((allTime - pathTime2) / allTime);
            differenceBox.Text = percentage.ToString();
            return;
        }

        void printPath(ref PriorityQueue pq, double pathTime, double allTime)
        {
            int prev = -1;
            int i = stopNodeIndex;
            while(prev != startNodeIndex)
            {
                prev = nodes[i].getPrev();
                double distance = nodes[i].getDistance() - nodes[prev].getDistance();
                string distancestr = distance.ToString();
                float x = nodes[i].getPoint().X - ((nodes[i].getPoint().X - nodes[prev].getPoint().X) / 2);
                float y = nodes[i].getPoint().Y - ((nodes[i].getPoint().Y - nodes[prev].getPoint().Y) / 2);
                drawLine(nodes[i].getPoint(), nodes[prev].getPoint(), distancestr, x, y);
                i = prev;
            }
            pathCostBox.Text = nodes[stopNodeIndex].getDistance().ToString();
            oneTimeBox.Text = pathTime.ToString();
            allTimeBox.Text = allTime.ToString();
            double percentage = ((allTime-pathTime)/allTime);
            differenceBox.Text = percentage.ToString();
        }

        private Boolean startStopToggle = true;
        private int startNodeIndex = -1;
        private int stopNodeIndex = -1;
        private void pictureBox_MouseDown(object sender, MouseEventArgs e)
        {
            if (points.Count > 0)
            {
                Point mouseDownLocation = new Point(e.X, e.Y);
                int index = ClosestPoint(points, mouseDownLocation);
                if (startStopToggle)
                {
                    startNodeIndex = index;
                    sourceNodeBox.Text = "" + index;
                }
                else
                {
                    stopNodeIndex = index;
                    targetNodeBox.Text = "" + index;
                }
                startStopToggle = !startStopToggle;

                resetImageToPoints(points);
                paintStartStopPoints();
            }
        }

        private void paintStartStopPoints()
        {
            if (startNodeIndex > -1)
            {
                Graphics graphics = Graphics.FromImage(pictureBox.Image);
                graphics.DrawEllipse(new Pen(Color.Green, 6), points[startNodeIndex].X, points[startNodeIndex].Y, 1, 1);
                this.graphics = graphics;
                pictureBox.Invalidate();
            }

            if (stopNodeIndex > -1)
            {
                Graphics graphics = Graphics.FromImage(pictureBox.Image);
                graphics.DrawEllipse(new Pen(Color.Red, 2), points[stopNodeIndex].X - 3, points[stopNodeIndex].Y - 3, 8, 8);
                this.graphics = graphics;
                pictureBox.Invalidate();
            }
        }

        private int ClosestPoint(List<PointF> points, Point mouseDownLocation)
        {
            double minDist = double.MaxValue;
            int minIndex = 0;

            for (int i = 0; i < points.Count; i++)
            {
                double dist = Math.Sqrt(Math.Pow(points[i].X-mouseDownLocation.X,2) + Math.Pow(points[i].Y - mouseDownLocation.Y,2));
                if (dist < minDist)
                {
                    minIndex = i;
                    minDist = dist;
                }
            }

            return minIndex;
        }

        public void drawLine(PointF point1, PointF point2, string distance, float x, float y)
        {
            Graphics graphics = Graphics.FromImage(pictureBox.Image);
            Pen pen = new Pen(Color.Black);
            graphics.DrawLine(pen, point1, point2);

            System.Drawing.Font drawFont = new System.Drawing.Font("Arial", 10);
            System.Drawing.SolidBrush drawBrush = new System.Drawing.SolidBrush(System.Drawing.Color.Black);
            graphics.DrawString(distance, drawFont, drawBrush, x, y);
            drawFont.Dispose();
            drawBrush.Dispose();

            this.graphics = graphics;
            pictureBox.Invalidate();
        }
    }
}
