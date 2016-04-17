# CD2T-100
[![Build Status](https://travis-ci.org/battila7/cd2t-100.svg?branch=master)](https://travis-ci.org/battila7/cd2t-100)
[![Codacy Badge](https://api.codacy.com/project/badge/grade/d8c7bd2165ca47169cc29c84533635b6)](https://www.codacy.com/app/bagossyattila_2/cd2t-100)
[![Codacy Badge](https://api.codacy.com/project/badge/coverage/d8c7bd2165ca47169cc29c84533635b6)](https://www.codacy.com/app/bagossyattila_2/cd2t-100)

**C**ompletely **D**ifferent **T**han **T**IS**-100**
 
## Brief
CD2T-100 is a Java implementation of a TIS-100-like CPU released under the MIT license. 

If you don't know TIS-100 yet, check it out at here:
[TIS-100 by Zachtronics](http://www.zachtronics.com/tis-100/)

Don't forget to take a look at the <a href="https://github.com/battila7/cd2t-100/wiki">Wiki</a>.

## Functionality
### What'll be left out
* Stack memory nodes
* Image drawing capabilities

### What's planned
* Preprocessor rules
* Custom instructions can be developed in Groovy
* Registers over `ACC` and `BAK`

For more information, please check the Wiki and the Javadoc.

## Current state
Just another milestone! CD2T-100 is now able to run using one node! Thanks to the hard work on the `asm` and `formal` packages I could finally focus on the `computation` package and push tons of lines of code. I've also implemented 5 basic instructions just to illustrate the capabilities of the architecture. 

## What's next then?
One node is running, many to come! The next step is implementing the pseudo-concurrent execution model of the nodes.
