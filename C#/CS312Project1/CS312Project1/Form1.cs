using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CS312Project1
{
    public partial class theOneAndOnlyForm : Form
    {
        private int inputNumber = -1;
        private int k = -1;

        public theOneAndOnlyForm()
        {
            InitializeComponent();
        }

        private void input_TextChanged(object sender, EventArgs e)
        {
            TextBox input = (TextBox)sender;
            if (int.TryParse(input.Text, out inputNumber))
            {
                inputNumber = int.Parse(input.Text);
            }
            else { }
        }

        private void k_TextChanged(object sender, EventArgs e)
        {
            TextBox kInput = (TextBox)sender;
            if (int.TryParse(kInput.Text, out k))
            {
                k = int.Parse(kInput.Text);
            }
            else { }
        }

        private void theOneAndOnlyForm_Load(object sender, EventArgs e)
        {

        }

        private void solve_Click(object sender, EventArgs e)
        {
            HashSet<int> used = new HashSet<int>();
            //make sure the user inputted values for each field.
            if(inputNumber != -1 && k != -1)
            {
                //calc the probability
                double probability = 1 - (1 / Math.Pow(2, k));
                Random rnd = new Random();
                //modular exponentiate a random number between two and the input k times.
                for(int i = 0; i < k; i++)
                {
                    int randomNumber = rnd.Next(2, inputNumber);
                    while(used.Contains(randomNumber))
                    {
                        Console.WriteLine(randomNumber);
                        randomNumber = rnd.Next(2, inputNumber);
                    }
                    used.Add(randomNumber);
                    double y = modExp(randomNumber, inputNumber - 1, inputNumber);
                    if (y == 1)
                    {
                        output.Text = "Prime" + " with probability: " + probability.ToString("#0.#########%");
                        return;  
                    }
                }
                output.Text = "Not Prime";
                return;
            }
            //This algorithm works at n^4 since modExp(n^3) is done n times.
        }
        private double modExp(int x, int y, int N)
        {
            if(y == 0)
            {
                return 1;
            }
            double z = modExp(x, (y / 2), N);
            if((y % 2) == 0)
            {
                return (Math.Pow(z, 2) % N);
            }
            else 
            {
                return x * (Math.Pow(z, 2) % N);
            }
            //this algorithm works with big o of n^3. There is a division for n^2 as a parameter and a recursion process.
        }
    }
}
