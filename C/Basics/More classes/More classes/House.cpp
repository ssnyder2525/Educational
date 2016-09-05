#include "House.h"
#include <sstream>
 
House::House(string new_name) {
	familyname = new_name;
}
 
void House::add_pet(Animal *new_pet) {
	pets.push_back(new_pet);
}
 
string House::toString()
{
	ostringstream strm;
	strm << endl<<"Household: "<<familyname<<endl;
	for (unsigned int i = 0; i < pets.size(); i++) {
		strm << pets[i]->toString() << " ";
	}
	/* Looping through the elements of the pets can also be done like this:
		Take a look if you have some time
	for (vector<Animal *>::iterator i = pets.begin(); i != pets.end(); i++) {
		strm << (*i)->toString() << " ";
	}
	*/
 
	strm <<endl;
	return(strm.str());
}
void House::do_act()
{
	for (unsigned int i = 0; i < pets.size(); i++) {
		pets[i]->do_act();
	}
 
}
string House::get_familyname()
{
	return (familyname);
}