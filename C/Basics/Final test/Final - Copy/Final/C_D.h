#pragma once
#include "Account.h"

class C_D : public Account

{
public:
C_D(double Balance, string Name);

~C_D();

void C_D::advanceMonth();

bool withdrawFromSavings(double amount);

};