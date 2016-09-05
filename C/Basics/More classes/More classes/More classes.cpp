#include <iostream>
#include <fstream>
#include <vector>
#include <sstream>
#include <string>
#include "Animal.h"
#include "Dog.h" 
#include "Cat.h" 
#include "House.h"
 
using namespace std;
 
int main() {
 
	vector<House*> upscaleneighborhood;
 
	House * newhouse;	// Note that it is important that this be outside of
						// the loop so that it is retain as the loop executes
	while (true) {
		string line;
		string type;
		string name;
 
		cout << "Next entry?";
		getline(cin, line);
		if (line == "stop") {
			break;
		}
		istringstream is(line); // grab a line
		is >> type;
		if ("House" == type) {//does it say house?
			is >> name;
			newhouse = new House(name);//new finds space
			upscaleneighborhood.push_back(newhouse);
		}
		else if ("Cat" == type) {//etc....
			is >> name;
			newhouse->add_pet(new Cat(name)); //arrow means find the pointer and use what it is pointing to.
		}
		else if ("Dog" == type) {
			is >> name;
			newhouse->add_pet(new Dog(name));
		}
		else {
			cout << "I don't know what that is, sorry I will ignore it.\n";
		}
 
	}
 
	// First print out the names using an iterator	
    cout << "\nfirst print out the names\n";
	for (unsigned int i = 0; i < upscaleneighborhood.size(); i++) {
		cout << upscaleneighborhood[i]->toString() << endl;
	}
	cout << endl;
 
    cout << "\nNow do the action!\n";
	for (unsigned int i = 0; i < upscaleneighborhood.size(); i++) {
		cout << "Actions at the " << upscaleneighborhood[i]->get_familyname() << " residence:\n";
		upscaleneighborhood[i]->do_act();
		cout << endl;
	}
	cout << endl;
 
	system("pause");
}