## StarCraft: Brood War in Multi-Agent Systems

Travis CI: [![Build Status](https://travis-ci.org/eishub/Starcraft.svg?branch=master)](https://travis-ci.org/eishub/Starcraft)

This project creates a bridge between [BWAPI](https://code.google.com/p/bwapi/ "BWAPI") for [StarCraft: Brood War](http://us.blizzard.com/en-us/games/sc/ "StarCraft: Brood War") and EIS-enabled Multi-Agent Systems like the [GOAL Agent Programming Language](https://goalapl.atlassian.net/wiki/ "GOAL Agent Programming Language").

The environment interface standard ([EIS](https://github.com/eishub/eis/wiki "EIS")) has been developed to facilitate connecting software agents to environments.

For tech trees see [TechTrees](http://www.teamliquid.net/forum/brood-war/226892-techtree-pictures "TechTrees").

### Current status
- All units can be controlled by the MAS.
- The MAS is capable of playing all three races.

### Project overview
The project contains the following folders:

* **EISBW:** The EIS implementation of BWAPI. 
* **libs:** Files required for building the interface and running the various examples.
