#pragma once
#include "ArenaInterface.h"
#include "Fighter.h"
#include <vector>
#include "FighterInterface.h"


class Arena : public ArenaInterface
{
public:
	Arena(void);
	~Arena(void);

	
	virtual bool addFighter(string info);
	virtual bool removeFighter(string name);
	virtual FighterInterface* getFighter(string name);
	virtual int getSize();
	
private:
	vector<FighterInterface*> allfighters;
	int position;
	
	
};

