using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.Drawing;
using System.Diagnostics;

namespace TSP
{
    class Node
    {
        public Node(int name, ref double[,] m, double lb, ref int[] enter, ref int[] exit, int citiesVisit)
        {
            this.name = name;
            matrix = new double[m.GetLength(0), m.GetLength(1)];
            Array.Copy(m, matrix, m.Length);
            lowerBound = lb;
            entered = new int[enter.Length];
            Array.Copy(enter, entered, enter.Length);
            exited = new int[exit.Length];
            Array.Copy(exit, exited, exit.Length);
            citiesVisited = citiesVisit;
        }
        public int name;
        public double[,] matrix;
        public double lowerBound;
        public int[] entered;
        public int[] exited;
        public int citiesVisited;
    }

    class ProblemAndSolver
    {
        public class TSPSolution
        {
            /// <summary>
            /// we use the representation [cityB,cityA,cityC] 
            /// to mean that cityB is the first city in the solution, cityA is the second, cityC is the third 
            /// and the edge from cityC to cityB is the final edge in the path.  
            /// You are, of course, free to use a different representation if it would be more convenient or efficient 
            /// for your node data structure and search algorithm. 
            /// </summary>
            public ArrayList Route;
            public double cost;

            public TSPSolution(ArrayList iroute)
            {
                Route = new ArrayList(iroute);
                cost = costOfRoute();
            }


            /// <summary>
            /// Compute the cost of the current route.  
            /// Note: This does not check that the route is complete.
            /// It assumes that the route passes from the last city back to the first city. 
            /// </summary>
            /// <returns></returns>
            public double costOfRoute()
            {
                // go through each edge in the route and add up the cost. 
                int x;
                City here;
                double cost = 0D;

                for (x = 0; x < Route.Count - 1; x++)
                {
                    here = Route[x] as City;
                    cost += here.costToGetTo(Route[x + 1] as City);
                }

                // go from the last city to the first. 
                here = Route[Route.Count - 1] as City;
                cost += here.costToGetTo(Route[0] as City);
                return cost;
            }

            public override bool Equals(System.Object obj)
            {
                TSPSolution otherSolution = (TSPSolution)obj;
                for (int i = 0; i < Route.Count; i++)
                {
                    if(Route[i] != otherSolution.Route[i])
                    {
                        return false;
                    }
                }
                return true;
            }

            public override int GetHashCode()
            {
                return base.GetHashCode();
            }
        }

        #region Private members 

        /// <summary>
        /// Default number of cities (unused -- to set defaults, change the values in the GUI form)
        /// </summary>
        // (This is no longer used -- to set default values, edit the form directly.  Open Form1.cs,
        // click on the Problem Size text box, go to the Properties window (lower right corner), 
        // and change the "Text" value.)
        private const int DEFAULT_SIZE = 25;

        private const int CITY_ICON_SIZE = 5;

        // For normal and hard modes:
        // hard mode only
        private const double FRACTION_OF_PATHS_TO_REMOVE = 0.20;

        /// <summary>
        /// the cities in the current problem.
        /// </summary>
        private City[] Cities;
        /// <summary>
        /// a route through the current problem, useful as a temporary variable. 
        /// </summary>
        private ArrayList Route;
        /// <summary>
        /// best solution so far. 
        /// </summary>
        private TSPSolution bssf; 

        /// <summary>
        /// how to color various things. 
        /// </summary>
        private Brush cityBrushStartStyle;
        private Brush cityBrushStyle;
        private Pen routePenStyle;


        /// <summary>
        /// keep track of the seed value so that the same sequence of problems can be 
        /// regenerated next time the generator is run. 
        /// </summary>
        private int _seed;
        /// <summary>
        /// number of cities to include in a problem. 
        /// </summary>
        private int _size;

        /// <summary>
        /// Difficulty level
        /// </summary>
        private HardMode.Modes _mode;

        /// <summary>
        /// random number generator. 
        /// </summary>
        private Random rnd;
        #endregion

        #region Public members
        public int Size
        {
            get { return _size; }
        }

        public int Seed
        {
            get { return _seed; }
        }
        #endregion

        #region Constructors
        public ProblemAndSolver()
        {
            this._seed = 1; 
            rnd = new Random(1);
            this._size = DEFAULT_SIZE;

            this.resetData();
        }

        public ProblemAndSolver(int seed)
        {
            this._seed = seed;
            rnd = new Random(seed);
            this._size = DEFAULT_SIZE;

            this.resetData();
        }

        public ProblemAndSolver(int seed, int size)
        {
            this._seed = seed;
            this._size = size;
            rnd = new Random(seed); 
            this.resetData();
        }
        #endregion

        #region Private Methods

        /// <summary>
        /// Reset the problem instance.
        /// </summary>
        private void resetData()
        {

            Cities = new City[_size];
            Route = new ArrayList(_size);
            bssf = null;

            if (_mode == HardMode.Modes.Easy)
            {
                for (int i = 0; i < _size; i++)
                    Cities[i] = new City(rnd.NextDouble(), rnd.NextDouble());
            }
            else // Medium and hard
            {
                for (int i = 0; i < _size; i++)
                    Cities[i] = new City(rnd.NextDouble(), rnd.NextDouble(), rnd.NextDouble() * City.MAX_ELEVATION);
            }

            HardMode mm = new HardMode(this._mode, this.rnd, Cities);
            if (_mode == HardMode.Modes.Hard)
            {
                int edgesToRemove = (int)(_size * FRACTION_OF_PATHS_TO_REMOVE);
                mm.removePaths(edgesToRemove);
            }
            City.setModeManager(mm);

            cityBrushStyle = new SolidBrush(Color.Black);
            cityBrushStartStyle = new SolidBrush(Color.Red);
            routePenStyle = new Pen(Color.Blue,1);
            routePenStyle.DashStyle = System.Drawing.Drawing2D.DashStyle.Solid;
        }

        #endregion

        #region Public Methods

        /// <summary>
        /// make a new problem with the given size.
        /// </summary>
        /// <param name="size">number of cities</param>
        //public void GenerateProblem(int size) // unused
        //{
        //   this.GenerateProblem(size, Modes.Normal);
        //}

        /// <summary>
        /// make a new problem with the given size.
        /// </summary>
        /// <param name="size">number of cities</param>
        public void GenerateProblem(int size, HardMode.Modes mode)
        {
            this._size = size;
            this._mode = mode;
            resetData();
        }

        /// <summary>
        /// return a copy of the cities in this problem. 
        /// </summary>
        /// <returns>array of cities</returns>
        public City[] GetCities()
        {
            City[] retCities = new City[Cities.Length];
            Array.Copy(Cities, retCities, Cities.Length);
            return retCities;
        }

        /// <summary>
        /// draw the cities in the problem.  if the bssf member is defined, then
        /// draw that too. 
        /// </summary>
        /// <param name="g">where to draw the stuff</param>
        public void Draw(Graphics g)
        {
            float width  = g.VisibleClipBounds.Width-45F;
            float height = g.VisibleClipBounds.Height-45F;
            Font labelFont = new Font("Arial", 10);

            // Draw lines
            if (bssf != null)
            {
                // make a list of points. 
                Point[] ps = new Point[bssf.Route.Count];
                int index = 0;
                foreach (City c in bssf.Route)
                {
                    if (index < bssf.Route.Count -1)
                        g.DrawString(" " + index +"("+c.costToGetTo(bssf.Route[index+1]as City)+")", labelFont, cityBrushStartStyle, new PointF((float)c.X * width + 3F, (float)c.Y * height));
                    else 
                        g.DrawString(" " + index +"("+c.costToGetTo(bssf.Route[0]as City)+")", labelFont, cityBrushStartStyle, new PointF((float)c.X * width + 3F, (float)c.Y * height));
                    ps[index++] = new Point((int)(c.X * width) + CITY_ICON_SIZE / 2, (int)(c.Y * height) + CITY_ICON_SIZE / 2);
                }

                if (ps.Length > 0)
                {
                    g.DrawLines(routePenStyle, ps);
                    g.FillEllipse(cityBrushStartStyle, (float)Cities[0].X * width - 1, (float)Cities[0].Y * height - 1, CITY_ICON_SIZE + 2, CITY_ICON_SIZE + 2);
                }

                // draw the last line. 
                g.DrawLine(routePenStyle, ps[0], ps[ps.Length - 1]);
            }

            // Draw city dots
            foreach (City c in Cities)
            {
                g.FillEllipse(cityBrushStyle, (float)c.X * width, (float)c.Y * height, CITY_ICON_SIZE, CITY_ICON_SIZE);
            }

        }

        /// <summary>
        ///  return the cost of the best solution so far. 
        /// </summary>
        /// <returns></returns>
        public double costOfBssf ()
        {
            if (bssf != null)
                return (bssf.costOfRoute());
            else
                return -1D; 
        }

        /// <summary>
        ///  solve the problem.  This is the entry point for the solver when the run button is clicked
        /// right now it just picks a simple solution. 
        /// </summary>
        public void solveProblem()
        {
            int x;
            Route = new ArrayList(); 
            // this is the trivial solution. 
            for (x = 0; x < Cities.Length; x++)
            {
                Route.Add( Cities[Cities.Length - x -1]);
            }
            // call this the best solution so far.  bssf is the route that will be drawn by the Draw method. 
            bssf = new TSPSolution(Route);
            // update the cost of the tour. 
            Program.MainForm.tbCostOfTour.Text = " " + bssf.costOfRoute();
            // do a refresh. 
            Program.MainForm.Invalidate();

        }
        #endregion

        //greedy algorithm. O(n^3)
        internal double greedyAlg(bool draw)
        {
            int startCity = 0;
            double result = -1;
            ArrayList bestRoute = new ArrayList();
            while (startCity != Cities.Length)
            {
                bestRoute.Clear();
                //get random start city
                //Random rand = new Random(Guid.NewGuid().GetHashCode());
                //int startCity = rand.Next(0, GetCities().Length);
                bestRoute.Add(Cities[startCity]);
                int currentCity = startCity;
                List<int> unvisitedCities = new List<int>();
                for (int i = 0; i < GetCities().Length; i++)
                {
                    if (i != startCity) { unvisitedCities.Add(i); }
                }
                double totalDistance = 0;
                while (unvisitedCities.Count != 0)
                {
                    double shortestDistance = -1;
                    int nextCity = -1;
                    for (int i = 0; i < unvisitedCities.Count; i++)
                    {
                        double possibleDistance = GetCities()[currentCity].costToGetTo(GetCities()[unvisitedCities[i]]);
                        if (!double.IsInfinity(possibleDistance))
                        {
                            if (shortestDistance == -1)
                            {
                                shortestDistance = possibleDistance;
                                nextCity = unvisitedCities[i];
                            }
                            else
                            {
                                if (possibleDistance < shortestDistance)
                                {
                                    shortestDistance = possibleDistance;
                                    nextCity = unvisitedCities[i];
                                }
                            }
                        }
                    }
                    if (nextCity == -1) { break; }
                    currentCity = nextCity;
                    bestRoute.Add(Cities[nextCity]);
                    unvisitedCities.Remove(nextCity);
                    totalDistance += shortestDistance;
                }
                double lastDistance = GetCities()[currentCity].costToGetTo(GetCities()[startCity]);
                if (!double.IsInfinity(lastDistance))
                {
                    if (result == -1)
                    {
                        result = totalDistance += lastDistance;
                        Route = bestRoute;
                    }
                    else
                    {
                        double newResult = totalDistance += lastDistance;
                        if (newResult < result)
                        {
                            result = newResult;
                            Route = bestRoute;
                        }
                    }
                }
                startCity++;
            }
            if (draw == false)
                return result;
            Program.MainForm.tbCostOfTour.Text = result.ToString();
            bssf = new TSPSolution(Route);
            Program.MainForm.Invalidate();
            return 0;
        }

        internal void branchAndBoundAlg()
        {
            
            //create the queue and build the matrix O(n^2)
            int nodeNumber = 0;
            PriorityQueue pq = new PriorityQueue();
            double[,] matrix = new double[Cities.Length, Cities.Length];
            for (int i = 0; i < Cities.Length; i++)
            {
                for (int j = 0; j < Cities.Length; j++)
                {
                    if (i == j)
                    {
                        matrix[i, j] = double.PositiveInfinity;
                    }
                    else
                    {
                        matrix[i, j] = Cities[i].costToGetTo(Cities[j]);
                    }
                }
            }
            //create first node and push it to the queue O(n)
            int[] entered = new int[Cities.Length];
            for (int i = 0; i < Cities.Length; i++)
            {
                entered[i] = -1;
            }
            int[] exited = new int[Cities.Length];
            for (int i = 0; i < Cities.Length; i++)
            {
                exited[i] = -1;
            }
            Node firstNode = new Node(nodeNumber, ref matrix, 0, ref entered, ref exited, 1);
            getLowerBound(ref firstNode);
            if (double.IsInfinity(firstNode.lowerBound)) { return; }
            double upperBound = greedyAlg(false);
            nodeNumber++;
            pq.insert(firstNode);

            Stopwatch sw = new Stopwatch();
            sw.Start();

            //now the branch and bound process starts!
            bool gotAnswer = false;
            int[] bestEntered = new int[Cities.Length];
            int[] bestExited = new int[Cities.Length];
            double bestCost = 0;
            int bssfUpdates = 0;
            int statesCreated = 0;
            int statesPruned = 0;
            int start = -1;
            int destination = -1;
            
            //The algorithm runs until there are no nodes under the upper bound left or time runs out. O(n^3)
            while(!pq.isEmpty() && sw.Elapsed.Seconds < 30)
            {
                Node node = pq.removeMin();
                if(node.lowerBound > upperBound)
                {
                    statesPruned++;
                    continue;
                }
                //for every 0 cost in the matrix
                Node bestInclude = null;
                Node bestExclude = null;
                double bestDifference = -1;
                start = -1;
                destination = -1;

                //for each 0 cost in the table
                for(int i = 0; i < Cities.Length; i++)
                {
                    for (int j = 0; j < Cities.Length; j++)
                    {
                        if (node.matrix[i, j] != 0) { continue; }
                        Node includeNode = new Node(nodeNumber, ref node.matrix, node.lowerBound, ref node.entered, ref node.exited, node.citiesVisited);
                        nodeNumber++;
                        Node excludeNode = new Node(nodeNumber, ref node.matrix, node.lowerBound, ref node.entered, ref node.exited, node.citiesVisited);
                        nodeNumber++;
                        
                        //for the include node, make the row and column of the current edge infinity;
                        for (int k = 0; k < Cities.Length; k++)
                        {
                            includeNode.matrix[i, k] = double.PositiveInfinity;
                            includeNode.matrix[k, j] = double.PositiveInfinity;
                        }
                        //make the reciprical node infinity
                        includeNode.matrix[j, i] = double.PositiveInfinity;
                        //make any cycles infinity
                        deleteEdges(includeNode, i, j);
                        //reduce the matrix
                        getLowerBound(ref includeNode);
                        
                        //for the exclude node, get rid of the edge you used for the include node.
                        excludeNode.matrix[i, j] = double.PositiveInfinity;
                        //reduce the matrix
                        getLowerBound(ref excludeNode);
                        //keep track of the best difference pair.
                        if (double.IsInfinity(includeNode.lowerBound) || double.IsInfinity(excludeNode.lowerBound)) { continue; }
                        if(bestDifference == -1)
                        {
                            bestInclude = includeNode;
                            bestExclude = excludeNode;
                            bestDifference = excludeNode.lowerBound - includeNode.lowerBound;
                            start = i;
                            destination = j;
                        }
                        double difference = excludeNode.lowerBound - includeNode.lowerBound;
                        if(difference > bestDifference)
                        {
                            bestInclude = includeNode;
                            bestExclude = excludeNode;
                            bestDifference = difference;
                            start = i;
                            destination = j;
                        }
                    }
                }
                //if you got an answer
                if(bestInclude != null && bestExclude != null)
                {
                    //if this answer completes a cycle add it as the best so far.
                    if(bestInclude.citiesVisited == Cities.Length)
                    {
                        gotAnswer = true;
                        bssfUpdates++;
                        bestEntered = bestInclude.entered;
                        bestExited = bestInclude.exited;
                        upperBound = bestInclude.lowerBound;
                        bestCost = bestInclude.lowerBound;
                    }
                    //if either or both of these partial answers is still within the upper bound, keep it.
                    if (bestInclude.lowerBound < upperBound)
                    {
                        statesCreated++;
                        pq.insert(bestInclude);
                    }
                    else { statesPruned++; }
                    if(bestExclude.lowerBound < upperBound)
                    {
                        statesCreated++;
                        pq.insert(bestExclude);
                    }
                    else { statesPruned++; }
                }
            }
            if(gotAnswer == false)
            {
                System.Windows.Forms.MessageBox.Show("failed to get answer in 30 seconds.");
                return;
            }
            //display the best answer.
            Program.MainForm.tbElapsedTime.Text = sw.Elapsed.TotalSeconds.ToString();
            Route.Clear();
            while (bestExited[destination] != -1) { destination = bestExited[destination]; }
            while (bestEntered[start] != -1) { start = bestEntered[start]; }
            while (start != destination) 
            {
                Route.Add(Cities[start]);
                start = bestExited[start];
            }
            Route.Add(Cities[destination]);
            Program.MainForm.tbCostOfTour.Text = bestCost.ToString();
            bssf = new TSPSolution(Route);
            Program.MainForm.Invalidate();
            System.Windows.Forms.MessageBox.Show("Max States Stored at a Time: " + pq.maxStored + "\nBSSF Updates: " + bssfUpdates.ToString() + "\nStates Created: " + statesCreated.ToString() + "\nStates Pruned: " + statesPruned.ToString());
            return;
        }

        internal void printMatrix(ref double[,] matrix)
        {
            System.Console.Write("\n\n");
            for (int i = 0; i < Cities.Length; i++)
            {
                for (int j = 0; j < Cities.Length; j++)
                {
                    System.Console.Write(matrix[i, j].ToString() + " ");
                }
                System.Console.Write("\n");
            }
        }
        
        //reduces the passed in matrix. O(n^2)
        internal void getLowerBound(ref Node node)
        {
            //reduce rows i = row
            for (int i = 0; i < Cities.Length; i++)
            {
                double rowMin = double.PositiveInfinity; 
                for(int j = 0; j < Cities.Length; j++)
                {
                    if(node.matrix[i, j] < rowMin)
                    {
                        rowMin = node.matrix[i, j];
                    }
                }
                if(!double.IsInfinity(rowMin))
                {
                    node.lowerBound += rowMin;
                }
                else
                {
                    if(node.exited[i] == -1 && node.citiesVisited != Cities.Length-1)
                    {
                        node.lowerBound = double.PositiveInfinity;
                        return;
                    }
                }
                for (int k = 0; k < Cities.Length; k++)
                {
                    if (!double.IsInfinity(node.matrix[i, k]))
                    {
                        node.matrix[i, k] -= rowMin;
                    }
                }
            }
            //reduce columns i = column
            for (int i = 0; i < Cities.Length; i++)
            {
                double colMin = double.PositiveInfinity;
                for (int j = 0; j < Cities.Length; j++)
                {
                    if (node.matrix[j, i] < colMin)
                    {
                        colMin = node.matrix[j, i];
                    }
                }
                if (!double.IsInfinity(colMin))
                {
                    node.lowerBound += colMin;
                }
                else
                {
                    if (node.entered[i] == -1 && node.citiesVisited != Cities.Length - 1)
                    {
                        node.lowerBound = double.PositiveInfinity;
                        return;
                    }
                }
                for (int k = 0; k < Cities.Length; k++)
                {
                    if(!double.IsInfinity(node.matrix[k,i]))
                    {
                        node.matrix[k, i] -= colMin;
                    }
                }
            }
        }

        //gets rid of early cycles. O(n)
        internal void deleteEdges (Node node, int i, int j)
        {
            node.citiesVisited++;
            node.entered[j] = i;
            node.exited[i] = j;
            int start = i;
            int end = j;
            // The new edge may be part of a partial solution. Go to the end of that solution.
            while (node.exited[end] != -1) { end = node.exited[end]; }
            // Similarly, go to the start of the new partial solution.
            while (node.entered[start] != -1) { start = node.entered[start]; }
            // Delete the edges that would make partial cycles, unless we’re ready to finish the tour
            if (node.citiesVisited != Cities.Length)
            {
                while (start != j){
                    node.matrix[end, start] = double.PositiveInfinity;
                    node.matrix[j, start] = double.PositiveInfinity;
                    start = node.exited[start];
                }
            }
        }

        internal void tabuAlg()
        {
            ArrayList route = new ArrayList();
            foreach(City city in Cities)
            {
                route.Add(city);
            }
            TSPSolution solution = new TSPSolution(route);
            route.Insert(3, route[route.Count - 1]);
            route.RemoveAt(route.Count - 1);
            TSPSolution solution2 = new TSPSolution(route);
            if (solution.Equals(solution2))
            {
                System.Console.Write("it worked.");
            }
        }
    }
}
