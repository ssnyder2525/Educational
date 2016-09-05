using System;
using System.Collections.Generic;
using System.Text;
using System.Drawing;

public class Hull {
    public List<PointF> points = new List<PointF>();
    public int rightmost = 0;
    public int topTangent = 0;
    public int bottomTangent = 0;

    public void addPoint(PointF point) {
        points.Add(point);
    }
    public PointF getPoint(int index) {
        return points[index];
    }

    //executes in O(n) time.
    public void findRightMostPoint() {
        for(int i = 0; i < points.Count; i++) {
            if(points[i].X > points[rightmost].X) {
                rightmost = i;
            }
        }
    }

    //runs in O(1) time
    public void orderClockwise() {
        double? slope1;
        double? slope2;

        if(points.Count == 2) {
            PointF point1 = points[0];
            PointF point2 = points[1];
            if(point1.X > point2.X) {
                points[0] = point2;
                points[1] = point1;
            }
        }
        else if (points.Count == 3) {
            PointF first = points[0];
            PointF second = points[1];
            PointF third = points[2];
            PointF leftMost = first;
            PointF point2 = second;
            PointF point3 = third;
            if(second.X < leftMost.X) {
                leftMost = second;
                point2 = first;
                point3 = third;
            }
            if(third.X < leftMost.X) {
                leftMost = third;
                point2 = first;
                point3 = second;
            }
            slope1 = getSlope(ref leftMost, ref point2);
            slope2 = getSlope(ref leftMost, ref point3);
            if (slope1 > slope2)
            {
                points[0] = leftMost;
                points[1] = point2;
                points[2] = point3;
            }
            else
            {
                points[0] = leftMost;
                points[1] = point3;
                points[2] = point2;
            }
        }
    }

    //runs in O(n) time
    public void addPointsFromOrigin(ref Hull hull) {
        for(int i = 0; i <= topTangent; i++) {
            hull.addPoint(points[i]);
        }
    }

    //runs in O(n) time
    public void addPointsFromTangentToTangent(ref Hull hull) {
        if (topTangent == bottomTangent)
        {
            hull.addPoint(points[topTangent]);
        }
        else
        {
            int i = topTangent;
            hull.addPoint(points[topTangent]);
            while (++i != bottomTangent)
            {
                if (i == points.Count)
                {
                    i = 0;
                    if (bottomTangent == 0)
                    {
                        break;
                    }
                }
                hull.addPoint(points[i]);
            }
            hull.addPoint(points[bottomTangent]);
        }
    }

    //runs in O(n) time
    public void addPointsFromTangentToOrigin(ref Hull hull)
    {
        if (bottomTangent != 0)
        {
            for (int i = bottomTangent; i < points.Count; i++)
            {
                hull.addPoint(points[i]);
            }
        }
    }

    //runs in O(1) time;
    public double? getSlope(ref PointF point1, ref PointF point2)
    {
        double? d = null;
        if ((point2.Y - point1.Y) == 0)
        {
            return 0;
        }
        if ((point2.X - point1.X) == 0)
        {
            return d;
        }
        return (-(point2.Y - point1.Y) / (point2.X - point1.X));
    }
}

namespace _2_convex_hull
{

    class ConvexHullSolver
    {
        System.Drawing.Graphics g;
        System.Windows.Forms.PictureBox pictureBoxView;

        public ConvexHullSolver(System.Drawing.Graphics g, System.Windows.Forms.PictureBox pictureBoxView)
        {
            this.g = g;
            this.pictureBoxView = pictureBoxView;
        }

        public void Refresh()
        {
            // Use this especially for debugging and whenever you want to see what you have drawn so far
            pictureBoxView.Refresh();
        }

        public void Pause(int milliseconds)
        {
            // Use this especially for debugging and to animate your algorithm slowly
            pictureBoxView.Refresh();
            System.Threading.Thread.Sleep(milliseconds);
        }

        //runs in O(nlogn) time because of the sort and the recursive algorithm.
        public void Solve(List<System.Drawing.PointF> pointList)
        {
            if (pointList.Count == 0 || pointList.Count == 1) { return; }
            else if (pointList.Count > 3)
            {
                //sort the points. Quicksort (nlogn);
                sortPointsFromLeftToRight(ref pointList, 0, pointList.Count - 1);
                Hull firstHull = new Hull();
                firstHull.points = pointList;
                Hull answer = recursivelyFindHulls(ref firstHull);
                drawHull(answer);
            }
        }

        //runs in O(n)
        public Hull recursivelyFindHulls(ref Hull hull)
        {
            Hull leftHull = new Hull();
            Hull rightHull = new Hull();

            //if there are more than three points in the hull, divide it in half and recurse.
            if (hull.points.Count > 3)
            {
                //divide the points. O(n) complexity
                int halfway = (hull.points.Count / 2);
                foreach (PointF point in hull.points)
                {
                    if (halfway > 0)
                    {
                        leftHull.addPoint(point);
                        halfway--;
                    }
                    else
                    {
                        rightHull.addPoint(point);
                    }
                }
                //if the hull is still too large, split it again recursively.
                if (leftHull.points.Count > 3 || rightHull.points.Count > 3)
                {
                    leftHull = recursivelyFindHulls(ref leftHull);
                    rightHull = recursivelyFindHulls(ref rightHull);
                }
                else
                {
                    leftHull.orderClockwise();
                    rightHull.orderClockwise();
                }
            }
            else
            {
                hull.orderClockwise();
                return hull;
            }

            leftHull.findRightMostPoint();
            rightHull.findRightMostPoint();
            Hull newHull = mergeTwoHulls(leftHull, rightHull);
            newHull.findRightMostPoint();
            return newHull;
        }

        //runs in O(n) time
        public Hull mergeTwoHulls(Hull leftHull, Hull rightHull)
        {
            Hull newHull = buildNewHull(ref leftHull, ref rightHull);
            return newHull;
        }    
        
        //runs in O(n) time
        public Hull buildNewHull(ref Hull leftHull, ref Hull rightHull )
        {
            Hull newHull = new Hull();
            findTopBridge(ref leftHull, ref rightHull);
            findBottomBridge(ref leftHull, ref rightHull);

            //add points from both hulls. All run in O(n) time.
            leftHull.addPointsFromOrigin(ref newHull);
            rightHull.addPointsFromTangentToTangent(ref newHull);
            leftHull.addPointsFromTangentToOrigin(ref newHull);
           
            newHull.findRightMostPoint();
            return newHull;
        }

        //This runs in O(n) time.
        public void findTopBridge(ref Hull leftHull, ref Hull rightHull) {
            leftHull.topTangent = leftHull.rightmost;
            PointF leftPoint = leftHull.points[leftHull.topTangent];
            PointF rightPoint = rightHull.points[rightHull.topTangent];
            double? slope = getSlope(ref leftPoint, ref rightPoint);
            double? tempSlope;
            bool pointChanged = true;

            while(pointChanged == true) {
                pointChanged = false;
                while(true) {
                    rightHull.topTangent++;
                    if(rightHull.topTangent == rightHull.points.Count) {
                        rightHull.topTangent = 0;
                    }
                    rightPoint = rightHull.points[rightHull.topTangent];
                    tempSlope = getSlope(ref leftPoint, ref rightPoint);
                    if (tempSlope > slope) {
                        slope = tempSlope;
                        pointChanged = true;
                    }
                    else {
                        rightHull.topTangent--;
                        if(rightHull.topTangent < 0) {
                            rightHull.topTangent = rightHull.points.Count-1;
                        }
                        rightPoint = rightHull.points[rightHull.topTangent];
                        break; 
                    }
                }

                while(true) {
                    leftHull.topTangent--;
                    if (leftHull.topTangent < 0)
                    {
                        leftHull.topTangent = leftHull.points.Count - 1;
                    }
                    leftPoint = leftHull.points[leftHull.topTangent];
                    tempSlope = getSlope(ref leftPoint, ref rightPoint);
                    if (tempSlope < slope) {
                        slope = tempSlope;
                        pointChanged = true;
                    }
                    else
                    {
                        leftHull.topTangent++;
                        if (leftHull.topTangent == leftHull.points.Count)
                        {
                            leftHull.topTangent = 0;
                        }
                        leftPoint = leftHull.points[leftHull.topTangent];
                        break;
                    }
                }
            }
        }

        //This algorithm is also O(n)
        public void findBottomBridge(ref Hull leftHull, ref Hull rightHull)
        {
            leftHull.bottomTangent = leftHull.rightmost;
            PointF leftPoint = leftHull.points[leftHull.bottomTangent];
            PointF rightPoint = rightHull.points[rightHull.bottomTangent];
            double? slope = getSlope(ref leftPoint, ref rightPoint);
            double? tempSlope;
            bool pointChanged = true;

            while (pointChanged == true)
            {
                pointChanged = false;
                while (true)
                {
                    rightHull.bottomTangent--;
                    if (rightHull.bottomTangent < 0)
                    {
                        rightHull.bottomTangent = rightHull.points.Count - 1;
                    }
                    rightPoint = rightHull.points[rightHull.bottomTangent];
                    tempSlope = getSlope(ref leftPoint, ref rightPoint);
                    if (tempSlope < slope)
                    {
                        slope = tempSlope;
                        pointChanged = true;
                    }
                    else
                    {
                        rightHull.bottomTangent++;
                        if (rightHull.bottomTangent == rightHull.points.Count)
                        {
                            rightHull.bottomTangent = 0;
                        }
                        rightPoint = rightHull.points[rightHull.bottomTangent];
                        break;
                    }
                }

                while (true)
                {
                    leftHull.bottomTangent++;
                    if (leftHull.bottomTangent == leftHull.points.Count)
                    {
                        leftHull.bottomTangent = 0;
                    }
                    leftPoint = leftHull.points[leftHull.bottomTangent];
                    tempSlope = getSlope(ref leftPoint, ref rightPoint);
                    if (tempSlope > slope)
                    {
                        slope = tempSlope;
                        pointChanged = true;
                    }
                    else
                    {
                        leftHull.bottomTangent--;
                        if (leftHull.bottomTangent < 0)
                        {
                            leftHull.bottomTangent = leftHull.points.Count - 1;
                        }
                        leftPoint = leftHull.points[leftHull.bottomTangent];
                        break;
                    }
                }
            }
        }

        //This algorithm runs at O(nlogn) time.
        public void sortPointsFromLeftToRight(ref List<System.Drawing.PointF> pointList, int left, int right)
        {
            int i = left, j = right;
            IComparable pivot = pointList[(left + right) / 2].X;
 
            while (i <= j)
            {
                while (pointList[i].X.CompareTo(pivot) < 0)
                {
                    i++;
                }
 
                while (pointList[j].X.CompareTo(pivot) > 0)
                {
                    j--;
                }
 
                if (i <= j)
                {
                    // Swap
                    PointF tmp = pointList[i];
                    pointList[i] = pointList[j];
                    pointList[j] = tmp;
 
                    i++;
                    j--;
                }
            }
 
            // Recursive calls
            if (left < j)
            {
                sortPointsFromLeftToRight(ref pointList, left, j);
            }
 
            if (i < right)
            {
                sortPointsFromLeftToRight(ref pointList, i, right);
            }
        }
        
        //runs at O(1) time
        public double? getSlope(ref PointF point1, ref PointF point2)
        {
            double? d = null;
            if((point2.Y - point1.Y) == 0)
            {
                return 0;
            }
            if ((point2.X - point1.X) == 0)
            {
                return d;
            }
            return (-(point2.Y - point1.Y) / (point2.X - point1.X));
        }

        //This runs at the end and runs at O(n)
        public void drawHull(Hull hull)
        {
            if (hull.points.Count > 1)
            {
                // This just draws the lines. No solution yet.
                Pen pen = new Pen(Color.Black);
                PointF[] points = new PointF[hull.points.Count];
                for (int i = 0; i < hull.points.Count; i++)
                {
                    points[i] = hull.points[i];
                }
                g.DrawPolygon(pen, points);
            }
        }
    }
}
