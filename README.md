# CD2T-100
[![Build Status](https://travis-ci.org/battila7/cd2t-100.svg?branch=master)](https://travis-ci.org/battila7/cd2t-100)
[![Codacy Badge](https://api.codacy.com/project/badge/grade/d8c7bd2165ca47169cc29c84533635b6)](https://www.codacy.com/app/bagossyattila_2/cd2t-100)
[![Codacy Badge](https://api.codacy.com/project/badge/coverage/d8c7bd2165ca47169cc29c84533635b6)](https://www.codacy.com/app/bagossyattila_2/cd2t-100)

**C**ompletely **D**ifferent **T**han **T**IS**-100**
 
## Brief
CD2T-100 is a Java implementation of a TIS-100-like CPU released under the MIT license. 

If you don't know TIS-100 yet, check it out at here:
[TIS-100 by Zachtronics](http://www.zachtronics.com/tis-100/)

## Functionality
### What'll be left out
* Stack memory nodes
* Image drawing capabilities

### What's planned
* Preprocessor rules
* Custom instructions can be developed in ~~Java~~ **Groovy**
* Registers over `ACC` and `BAK`

For more information, please check the Wiki and the Javadoc.

## Current state
The project has just reached the first milestone! Now it's able to analyze the source code and create elements for further processing. Also, CD2T-100 is capable of perceiving syntactical and some semantical errors. I say "some", because detecting all error without knowing the instruction set is impossible.

## What's next then?
Integrating Groovy into the project and implementing processor nodes. Surely, these tasks sound pretty hard but arguably these are the most exciting challenges!
