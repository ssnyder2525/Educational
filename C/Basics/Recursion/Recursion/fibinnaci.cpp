#include <iostream>



using namespace std;

 

/* 
How to time:
clock_t start;
double elapsed;




5.Fibonacci numbers are defined as follows:

6.fib(1) = 1 (by definition)

7.fib(2) = 1 (by definition)

8.fib(n) = fib(n-1) + fib(n-2) (for n > 2)

Thus the first 10 fibonacci numbers are:

1, 1, 2, 3, 5, 8, 13, 21, 34, 55

*/

 

// Recursive solution; this solution is nice because it is

// compact and it matches the mathematical definition; either

// n is a base case and so the answer is 1 by definition, or it

// is larger than 2, so the answer is the sum of the prior two

// fibonacci numbers (n-1 and n-2); the drawback is that this

// solution is much less efficient than the iterative solution

// below.

int fib_r(int n) {

	if(n <= 2) { return 1; }

	return fib_r(n-1) + fib_r(n-2);

}

 

// Iterative solution; this solution is nice because it is

// much faster to run; however, the code is messier and more

// difficult to verify; it basically does what you would do

// on paper if you were to figure out a particular fibonacci

// number by hand.

int fib_i(int n) {

	if(n <= 2) { return 1; }

	int n1 = 1;

	int n2 = 1;

	int new_n;

	for(int i=3; i<=n; i++) {

		new_n = n1 + n2;

		n2 = n1;

		n1 = new_n;

	}

	return new_n;

}

 

int main() {

 

	// Notice that the two solutions run in about the same 

	// amount of time for small values of n (up to approx. 30);

	// for values greater than 30, though, the recursive solution

	// is significantly slower; and for values of about 50 and

	// above the recursive solution takes hours or days to run;

	// if you diagram what the recursive solution is doing in

	// terms of function calls, you quickly realize that it is

	// recalculating the same intermediate fibonacci numbers

	// over and over again, which is why it runs so slowly; it

	// would seem from this and the triangle numbers example that

	// recursion is not all that helpful; however, we'll soon

	// see other examples, including lab 11, in which the

	// recursion makes good sense.

	while(true) {

		cout << "Enter n: ";

		int n;

		cin >> n;

		int f_i = fib_i(n);

		cout << "fib_i(" << n << ") = " << f_i << endl;

		int f_r = fib_r(n);

		cout << "fib_r(" << n << ") = " << f_r << endl;

	}

 

}

