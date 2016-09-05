using System;
using System.Collections.Generic;
using System.Text;

namespace GeneticsLab
{
    public struct Node
    {
        int value;
        int prevRow;
        int prevColumn;

        public Node(int value, int row, int col)
        {
            this.value = value;
            this.prevRow = row;
            this.prevColumn = col;
        }
        public int getValue()
        {
            return this.value;
        }
        public int getPrevColumn()
        {
            return this.prevColumn;
        }
        public int getPrevRow()
        {
            return this.prevRow;
        }
    }

    class PairWiseAlign
    {
        
        /// <summary>
        /// Align only 5000 characters in each sequence.
        /// </summary>
        private int MaxCharactersToAlign = 5000;
        private Node[,] table;
        /// <summary>
        /// this is the function you implement.
        /// </summary>
        /// <param name="sequenceA">the first sequence</param>
        /// <param name="sequenceB">the second sequence, may have length not equal to the length of the first seq.</param>
        /// <param name="resultTableSoFar">the table of alignment results that has been generated so far using pair-wise alignment</param>
        /// <param name="rowInTable">this particular alignment problem will occupy a cell in this row the result table.</param>
        /// <param name="columnInTable">this particular alignment will occupy a cell in this column of the result table.</param>
        /// <returns>the alignment score for sequenceA and sequenceB.  The calling function places the result in entry rowInTable,columnInTable
        /// of the ResultTable</returns>
        public int Align(GeneSequence sequenceA, GeneSequence sequenceB, ResultTable resultTableSoFar, int rowInTable, int columnInTable)
        {
            //scoring algorithm
            int rowNum = sequenceA.Sequence.Length;
            int colNum = sequenceB.Sequence.Length;
            if(sequenceA.Sequence.Length > MaxCharactersToAlign)
            {
                rowNum = MaxCharactersToAlign;
            }
            if(sequenceB.Sequence.Length > MaxCharactersToAlign)
            {
                colNum = MaxCharactersToAlign;
            }
            int[] oldRow = new int[colNum + 1];
            int[] newRow = new int[colNum + 1];
            // a place holder computation.  You'll want to implement your code here. 
            for (int i = 0; i <= rowNum; i++)
            {
                for (int j = 0; j <= colNum; j++)
                {
                    //set all the first cells in the first row and column to multiples of five.
                    if (i == 0)
                    {
                        if(j == 0)
                        {
                            newRow[j] = 0;
                        }
                        else
                        {
                            newRow[j] = 5 * j;
                        }
                    }
                    else if (j == 0)
                    {
                        newRow[j] = 5 * i;
                    }
                    else
                    {
                        //calculate possible values
                        int value = diff(sequenceA.Sequence[i - 1], sequenceB.Sequence[j - 1]) + oldRow[j - 1];
                        int left = 5 + newRow[j - 1];
                        int up = 5 + oldRow[j];

                        int tempval = Math.Min(value, left);
                        int chosenValue = Math.Min(tempval, up);
                        newRow[j] = chosenValue;
                    }
                }
                oldRow = newRow;
                newRow = new int[colNum + 1];
            }
            return oldRow[colNum];             
        }

        public int diff(char leftChar, char topChar)
        {
            if (leftChar == topChar)
            {
                return -3;
            }
            else
            {
                return 1;
            }
        }

        public string extractAlignment(GeneSequence sequenceA, GeneSequence sequenceB, ResultTable resultTableSoFar, int rowInTable, int columnInTable)
        {
            //extraction algorithm
            int rowNum = sequenceA.Sequence.Length;
            int colNum = sequenceB.Sequence.Length;
            if(sequenceA.Sequence.Length > MaxCharactersToAlign)
            {
                rowNum = MaxCharactersToAlign;
            }
            if(sequenceB.Sequence.Length > MaxCharactersToAlign)
            {
                colNum = MaxCharactersToAlign;
            }
            table = new Node[rowNum + 1, colNum + 1];
            // a place holder computation.  You'll want to implement your code here. 
            for (int i = 0; i <= rowNum; i++)
            {
                for (int j = 0; j <= colNum; j++)
                {
                    //set all the first cells in the first row and column to multiples of five.
                    if (i == 0)
                    {
                        if(j == 0)
                        {
                            table[i,j] = new Node(0, -1, -1);
                        }
                        else
                        {
                            table[i, j] = new Node(5 * j, 0, j - 1);
                        }
                    }
                    else if (j == 0)
                    {
                        table[i, j] = new Node(5 * i, i - 1, 0);
                    }
                    else
                    {
                        //calculate possible values
                        int value = diff(sequenceA.Sequence[i - 1], sequenceB.Sequence[j - 1]) + table[i - 1, j - 1].getValue();
                        int left = 5 + table[i, j - 1].getValue();
                        int up = 5 + table[i - 1, j].getValue();

                        int tempval = Math.Min(value, left);
                        int chosenValue = Math.Min(tempval, up);

                        int prevRow = 0;
                        int prevCol = 0;
                        if (chosenValue == value)
                        {
                            prevRow = i - 1;
                            prevCol = j - 1;
                        }
                        else if (chosenValue == left)
                        {
                            prevRow = i;
                            prevCol = j - 1;
                        }
                        else
                        {
                            prevRow = i - 1;
                            prevCol = j;
                        }
                        table[i, j] = new Node(chosenValue, prevRow, prevCol);
                    }
                }
            }
            return extraction(rowNum, colNum, sequenceA.Sequence, sequenceB.Sequence); 
        }

        public string extraction(int rowNum, int colNum, string sequenceA, string sequenceB)
        {
            Stack<char> sequence1 = new Stack<char>();
            Stack<char> sequence2 = new Stack<char>();
            int i = rowNum;
            int j = colNum;
            int count = 100;
            while(i != 0 && j != 0 && count > 0)
            {
                int previ = table[i, j].getPrevRow();
                int prevj = table[i, j].getPrevColumn();
                if (previ == i - 1 && prevj == j - 1)
                {
                    sequence1.Push(sequenceA[i-1]);
                    sequence2.Push(sequenceB[j-1]);
                    i--;
                    j--;
                }
                else if (previ == i-1)
                {
                    sequence1.Push(sequenceA[i - 1]);
                    sequence2.Push('_');
                    i--;
                }
                else
                {
                    sequence1.Push('_');
                    sequence2.Push(sequenceB[j - 1]);
                    j--;
                }
                count--;
            }
            
            string returnString = "";
            while(sequence1.Count != 0)
            {
                returnString += sequence1.Pop();
            }
            returnString += "\r\n";
            while(sequence2.Count != 0)
            {
                returnString += sequence2.Pop();
            }
            return returnString;
        }
    }
}