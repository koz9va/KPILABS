//
// Created by koz9va on 26.10.20.
//

#ifndef LAB3_CALLANDWRITE_H
#define LAB3_CALLANDWRITE_H

#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/xml_parser.hpp>
#include <set>
#include <exception>
#include <string>
#include <cstdio>
#include "NU.h"
namespace pt = boost::property_tree;

int Calc(char *fileName, std::string &configName);
#endif //LAB3_CALLANDWRITE_H
