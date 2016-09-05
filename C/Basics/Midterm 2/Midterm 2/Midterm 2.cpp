//Stephen Snyder    268261194        CS 142 Fall 2013 Midterm 2

/* Test Cases

1. 
I selected the first option.
I inputted "What's up? My name is Steve Snyder.
It gave me the integers that coorespond to that message.
I inputted 8 as the key.
It gave me the converted integers.
It encrypted the text as: _pi!/ ("xG(U&(vium(q ([!m#m([v&lmz6

2.
I selected the second option.
I inputted _pi!/ ("xG(U&(vium(q ([!m#m([v&lmz6.
It converted the message to integers.
I inputted 8 as the key.
It gave me the converted integers.
It printed the message: What's up? My name is Steve Snyder.
Success!

3. 
I selected option 3.
I inputted _pi!/ ("xG(U&(vium(q ([!m#m([v&lmz6.
It gave me a chart with all the numbers listed.
I saw that ( was the most repeated character.
It coded for the key being 8 and this key made the message: What's up? My name is Steve Snyder.
The other key was 77 and it brought up unintelligable symbols.

*/


#include <iostream>
#include <vector>
#include <string>
#include <cstring>
#include <cstdlib>


using namespace std;

//Caesar Cipher function

void inputtextandconvert (vector <char> &texts, string &text)
{
	cout << "Please enter the text to encrypt:\n";
		cin.ignore(100,'\n');
		getline(cin,text);

	for (unsigned int i=0;i < text.size();i++)
	{
		texts.push_back(text[i]);
	}
	
}

void displayvector (vector <char> vec)
{
	for (unsigned int i=0; i < vec.size();i++)
	{
		cout<<vec[i];
	}
	cout<<"\n\n";
}
void displayintvector(vector <int> intvec)
{
	for (unsigned int i=0; i < intvec.size();i++)
	{
		cout<<intvec[i]<< " ";
	}
	cout<<"\n\n";
}

void makeintvector (vector<char> &vec, vector<int> &intvec)
{
	for (unsigned int i=0; i < vec.size();i++)
	{
		intvec.push_back(vec[i]);
	}
}

void makecharvector (vector<int> &intvec, vector <char> &vec)
{
	for (unsigned int i=0; i < intvec.size();i++)
	{	
		vec.push_back(intvec[i]);
	}
}

void encrypt(vector <int> &intvec,vector <int> &output, int k)
{
	for (unsigned int i=0;i<intvec.size();i++)
	{
		
		if (intvec[i]+k>122)
		{
			output.push_back(intvec[i]+k-91);
		}
		else
		{
			output.push_back(intvec[i]+k);
		}
	}
}

void clearvector (vector <char> &vec)
{
	int j = vec.size();
	for (unsigned int i=0; i < j;i++)
	{
		vec.pop_back();
	}
	
}

void clearintvector (vector <int> &intvec)
{
	int j = intvec.size();
	for (unsigned int i=0; i < j;i++)
	{
		intvec.pop_back();
	}
}

void decrypt(vector <int> &intvec,vector <int> &output, int k)
{
	for (unsigned int i=0;i<intvec.size();i++)
	{
		if (intvec[i]-k<32)
		{
			output.push_back((intvec[i]-k)+91);
		}
		else
		{
			output.push_back(intvec[i]-k);
		}
	}
}

void inputtextandunconvert (vector <char> &encrypt, string &text)
{
	cout << "Please enter the text to decrypt:\n";
		cin.ignore(100,'\n');
		getline(cin,text);

	for (unsigned int i=0;i < text.size();i++)
	{
		encrypt.push_back(text[i]);
	}
	
}

void findfrequencies (vector <int> encrypted, vector <int> &frequencies, vector <char> &letters)
{	
	int counter=0;
	letters.push_back(encrypted[0]);
	int check;
	for(unsigned int k=0; k<encrypted.size();k++)
				{
					if (encrypted[k]==encrypted[0])
					{
						counter = counter +1;
					}
				}
				
				frequencies.push_back(counter);
				counter=0;

	for (unsigned int i=1; i<encrypted.size();i++)
	{
		for (unsigned int j=0;j<letters.size();j++)
		{
			if (encrypted[i] == letters[j])
			{
				check =1;
				break;
			}
			else
			{
				check = 0;
			}
		}

		if (check == 0)
		{
			for(unsigned int k=i; k<encrypted.size();k++)
				{
					if (encrypted[k]==encrypted[i])
					{
						counter = counter +1;
					}
				}
				letters.push_back(encrypted[i]);
				frequencies.push_back(counter);
				counter=0;
			}
		}	
	}

void copyvector (vector <char> vec, vector <char> &vec2)
{
	for (unsigned int i=0;i<vec.size();i++)
	{
		vec2.push_back(vec[i]);
	}
}


void displayfrequencies (vector <char> letters, vector <int> frequencies,vector<int> intvec)
{
	for (unsigned int i=0; i<letters.size();i++)
	{
		cout << "\""<<letters[i]<<"\""<<" which is ASCII "<<intvec[i]<< " occurs " << frequencies[i]<< " time(s).\n";
	}
	cout <<"\n\n";
}

void checkforsolutions(vector <char> letters, vector <int> frequencies, vector <char> encrypted, int &key, int &keytwo)
{
	int largest=frequencies[0];
	int secondlargest=0;
	char charoflargest=letters[0];
	char charofsecondlargest='\0';
	int largestnum=0;
	int secondlargestnum=0;
	int key1;
	int key2;

	for (unsigned int i=0; i<frequencies.size();i++)
	{
		for(unsigned int j=i; j<frequencies.size();j++)
		{
			if (largest < frequencies[j])
			{
				largest = frequencies[j];
				charoflargest = letters[j];
				largestnum=charoflargest;
				
			}
			else
			{
			}
		}
	}
	

	for (unsigned int k=0; k<frequencies.size();k++)
	{
		for(unsigned int l=0; l<frequencies.size();l++)
		{
			if ((frequencies[k] == largest) || (frequencies[l] == largest))
			{
				
			}
			else if (secondlargest < frequencies[l])
			{
				secondlargest = frequencies[l];
				charofsecondlargest = letters[l];
				secondlargestnum=charofsecondlargest;
			
			}
		}
	}
	

	
	char space=' ';
	int numspace=space;
	key1 = largestnum-numspace;
	

	key2= secondlargestnum-numspace;
	

	key=key1;
	keytwo=key2;


}


void encryptwithfourkeys (vector <int> vec1, vector <int> &vec2, int keys1, int keys2, int keys3, int keys4)
{

	for (unsigned int i=0;i<vec1.size();i++)
	{
		
		if (vec1[i]+keys1>122)
		{
			vec2.push_back(vec1[i]+keys1-91);
			i++;
		}
		else
		{
			vec2.push_back(vec1[i]+keys1);
			i++;
		}
	
	if (vec1.size()-i>0)
	{
		if (vec1[i]+keys2>122)
		{
			vec2.push_back(vec1[i]+keys2-91);
			i++;
		}
		else
		{
			vec2.push_back(vec1[i]+keys2);
			i++;
		}
	}
	if (vec1.size()-i>0)
	{
		if (vec1[i]+keys3>122)
		{
			vec2.push_back(vec1[i]+keys3-91);
			i++;
		}
		else
		{
			vec2.push_back(vec1[i]+keys3);
			i++;
		}
	}
	if (vec1.size()-i>0)
	{
		if (vec1[i]+keys4>122)
		{
			vec2.push_back(vec1[i]+keys4-91);
			i++;
		}
		else
		{
			vec2.push_back(vec1[i]+keys4);
			
		}
	}
	}
}


void decryptwithfourkeys (vector <int> vec1, vector <int> &vec2, int keys1, int keys2, int keys3, int keys4)
{
	for (unsigned int i=0;i<vec1.size();i++)
	{
	if (vec1.size() >0)
	{
		if (vec1[i]-keys1<32)
		{
			vec2.push_back((vec1[i]-keys1)+91);
		}
		else
		{
			vec2.push_back(vec1[i]-keys1);
		}
		i++;
	}

	if (vec1.size() >0)
	{
		if (vec1[i]-keys2<32)
		{
			vec2.push_back((vec1[i]-keys2)+91);
		}
		else
		{
			vec2.push_back(vec1[i]-keys2);
		}
		i++;
	}

	if (vec1.size() >0)
	{
		if (vec1[i]-keys3<32)
		{
			vec2.push_back((vec1[i]-keys3)+91);
		}
		else
		{
			vec2.push_back(vec1[i]-keys3);
		}
		i++;
	}

	if (vec1.size() >0)
	{
		if (vec1[i]-keys4<32)
		{
			vec2.push_back((vec1[i]-keys4)+91);
		}
		else
		{
			vec2.push_back(vec1[i]-keys4);
		}
	}

	}
}

void makefourvectors (vector <int> intvec, vector <int> &vector1, vector <int> &vector2, vector <int> &vector3, vector <int> &vector4)
{
	for (unsigned int i=0; i<intvec.size(); i=i+4)
	{
		vector1.push_back(intvec[i]);
	}

	for (unsigned int i=1; i<intvec.size(); i=i+4)
	{
		vector2.push_back(intvec[i]);
	}

	for (unsigned int i=2; i<intvec.size(); i=i+4)
	{
		vector3.push_back(intvec[i]);
	}

	for (unsigned int i=3; i<intvec.size(); i=i+4)
	{
		vector4.push_back(intvec[i]);
	}
}

void decryptwithfourkeys2 (vector <int> vector1, vector <int> vector2, vector <int> vector3, vector <int> vector4, vector <int> intvec2,int keys1,int keys2,int keys3,int keys4)
{
	for (unsigned int i=0;i<vector1.size();i=i++)
	{
	if (vector1.size() >i)
	{
		if (vector1[i]-keys1<32)
		{
			intvec2.push_back((vector1[i]-keys1)+91);
		}
		else
		{
			intvec2.push_back(vector1[i]-keys1);
		}
		
	}

	if (vector2.size() >=i)
	{
		if (vector2[i]-keys2<32)
		{
			intvec2.push_back((vector2[i]-keys2)+91);
		}
		else
		{
			intvec2.push_back(vector2[i]-keys2);
		}
	}

	if (vector3.size() >i)
	{
		if (vector3[i]-keys3<32)
		{
			intvec2.push_back((vector3[i]-keys3)+91);
		}
		else
		{
			intvec2.push_back(vector3[i]-keys3);
		}
		
	}

	if (vector4.size() >i)
	{
		if (vector4[i]-keys4<32)
		{
			intvec2.push_back((vector4[i]-keys4)+91);
		}
		else
		{
			intvec2.push_back(vector4[i]-keys4);
		}
	}

	}
}


int main()
{
//menu
	int menuloop=0; // keeps the menu looping.
	int choice;
	string text; //what you want encrypted
	vector <char> texts;
	vector <char> encrypted;
	vector <char> copyofencrypted;
	vector <int> intvec;
	vector <int> intvec2;
	vector <int> frequencies;
	vector <char> letters;
	int k; //the key
	int key1;
	int key2;

while (menuloop==0)
{
	cout << "What would you like to do?\n\n"
		"0) Quit\n"
		"1) Encrypt text using the Caesar Cipher and a key\n"
		"2) Decrypt text using the Caesar Cipher and a key\n"
		"3) Decrypt text using the Caesar Cipher without the key\n"
		"4) Encrypt text using the \"Super Cipher\" and a key\n"
		"5) Decrypt text using the \"Super Cipher\" and a key\n"
		"6) Decrypt text using the \"Super Cipher\" without the key\n\n";
	
	cin>>choice;
	cout << "\n\n";

//Encrypt Caesar Cipher and key
	if (choice == 1)
	{
		inputtextandconvert(texts, text);//makes a vector of the text
		cout<<"Converted to a vector<int>: ";
		makeintvector(texts,intvec);//converts to ints
		displayintvector(intvec);// prints the vector
		cout<<"Please input the key\n";
		cin >> k;//key
		encrypt(intvec, intvec2, k);//encrypts using the key and makes another int vector
		cout<< "Converted ints: ";
		displayintvector(intvec2);
		clearintvector(intvec);
		makecharvector(intvec2,encrypted);//returns int vector to a character vector
		cout<<"Encrypted text: ";
		displayvector(encrypted);//Answer
		clearintvector(intvec2);
		clearvector(texts);
		clearvector(encrypted);
	}


//Decrypt Caesar Cipher and key
	else if (choice == 2)
	{
		inputtextandunconvert(encrypted,text);
		makeintvector(encrypted,intvec);
		cout<<"Converted to an int vector: ";
		displayintvector(intvec);
		clearvector(encrypted);
		cout<<"Please input the key\n";
		cin>>k;
		decrypt(intvec,intvec2,k);
		cout<<"Converted ints: ";
		displayintvector(intvec2);
		makecharvector(intvec2,encrypted);
		cout<< "Decrypted text: ";
		displayvector(encrypted);
		clearvector(encrypted);
		clearintvector(intvec);
		clearintvector(intvec2);
	}


//Decrypt Caesar Cipher without key
	else if (choice == 3)
	{
		inputtextandunconvert(encrypted,text);//gives the vector
		copyvector(encrypted,copyofencrypted);//makes a copy for future use
		makeintvector(encrypted, intvec);//changes to ints
		findfrequencies(intvec, frequencies,letters);//finds how many of each character there are
		makeintvector(letters,intvec2);//changes the letters to ints
		displayfrequencies(letters,frequencies, intvec2);
		checkforsolutions(letters, frequencies, encrypted, key1, key2);//finds the most common and second most common characters and finds the approprate keys
		clearvector(letters);
		clearintvector(intvec);
		clearintvector(intvec2);
		clearintvector (frequencies);
		clearvector(encrypted);

		copyvector(copyofencrypted,encrypted);
		makeintvector(encrypted,intvec);

	//This is decrypting code for the first key
		clearvector(encrypted);
		decrypt(intvec,intvec2,key1);
		makecharvector(intvec2,encrypted);
		cout<< "If "<<key1<< " is the key, the message is: ";
		displayvector(encrypted);
		clearvector(encrypted);
		clearintvector(intvec);
		clearintvector(intvec2);

	//This is decrypting code for the second key
		makeintvector(copyofencrypted,intvec);
		clearvector(copyofencrypted);
		decrypt(intvec,intvec2,key2);
		makecharvector(intvec2,encrypted);
		cout<< "If "<<key2<< " is the key, the message is: ";
		displayvector(encrypted);
		clearvector(encrypted);
		clearintvector(intvec);
		clearintvector(intvec2);

	}


//Encrypt Super Cipher and key
	else if (choice == 4)
	{
		int keys1,keys2,keys3,keys4;
		inputtextandconvert(texts, text);//makes a vector of the text
		cout<<"Converted to a vector<int>: ";
		makeintvector(texts,intvec);//converts to ints
		displayintvector(intvec);// prints the vector
		cout<<"Please input four keys\n";
		cin >> keys1>>keys2>>keys3>>keys4;
		encryptwithfourkeys(intvec, intvec2, keys1,keys2,keys3,keys4);//encrypts using the keys and makes another int vector
		cout<< "Converted ints: ";
		displayintvector(intvec2);
		clearintvector(intvec);
		makecharvector(intvec2,encrypted);//returns int vector to a character vector
		cout<<"Encrypted text: ";
		displayvector(encrypted);//Answer
		clearintvector(intvec2);
		clearvector(texts);
		clearvector(encrypted);
	}
	


//Decrypt Super Cipher and key
	else if (choice == 5)
	{
		int keys1,keys2,keys3,keys4;
		inputtextandunconvert(encrypted,text);
		makeintvector(encrypted,intvec);
		cout<<"Converted to an int vector: ";
		displayintvector(intvec);
		clearvector(encrypted);
		cout<<"Please input four keys\n";
		cin>>keys1>>keys2>>keys3>>keys4;
		decryptwithfourkeys(intvec,intvec2,keys1,keys2,keys3,keys4);
		cout<<"Converted ints: ";
		displayintvector(intvec2);
		makecharvector(intvec2,encrypted);
		cout<< "Decrypted text: ";
		displayvector(encrypted);
		clearvector(encrypted);
		clearintvector(intvec);
		clearintvector(intvec2);
	}



//Decrypt Super Cipher without key
	else if (choice == 6)
	{
		vector <int> vector1;
		vector <int> vector2;
		vector <int> vector3;
		vector <int> vector4;

		vector <int> frequency1;
		vector <int> frequency2;
		vector <int> frequency3;
		vector <int> frequency4;

		vector <char> letters1;
		vector <char> letters2;
		vector <char> letters3;
		vector <char> letters4;

		inputtextandunconvert(encrypted,text);//gives the vector
		makeintvector(encrypted, intvec);//changes to ints
		makefourvectors(intvec,vector1,vector2,vector3,vector4);
		findfrequencies(vector1, frequencies,letters);//finds how many of each character there are
		frequency1=frequencies;
		letters1=letters;
		clearintvector(frequencies);
		clearvector(letters);

		findfrequencies(vector2, frequencies,letters);//finds how many of each character there are
		frequency2=frequencies;
		letters2=letters;
		clearintvector(frequencies);
		clearvector(letters);

		findfrequencies(vector3, frequencies,letters);//finds how many of each character there are
		frequency3=frequencies;
		letters3=letters;
		clearintvector(frequencies);
		clearvector(letters);

		findfrequencies(vector4, frequencies,letters);//finds how many of each character there are
		frequency4=frequencies;
		letters4=letters;
		clearintvector(frequencies);
		clearvector(letters);

		//makeintvector(letters,intvec2);//changes the letters to ints
		
		checkforsolutions(letters1, frequency1, encrypted, key1, key2);
		int keys1=key1;
		checkforsolutions(letters2, frequency2, encrypted, key1, key2);
		int keys2=key1;
		checkforsolutions(letters3, frequency3, encrypted, key1, key2);
		int keys3=key1;
		checkforsolutions(letters4, frequency4, encrypted, key1, key2);
		int keys4=key1;
		clearvector(letters);
		clearintvector(intvec);
		clearintvector(intvec2);
		clearintvector (frequencies);
		clearvector(encrypted);


		clearvector(encrypted);
		decryptwithfourkeys2(vector1, vector2, vector3, vector4, intvec2,keys1,keys2,keys3,keys4);

		makecharvector(intvec2,encrypted);
		cout<< "The message is: ";
		displayvector(encrypted);
		clearvector(encrypted);
		clearintvector(intvec);
		clearintvector(intvec2);
	}


//Quit
	else if (choice == 0)
	{
		break;
	}

	else
	{
		cout << "\nERROR: Invalide input.\n\n";
	}

}

	system ("pause");
	return 0;
}