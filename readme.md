# World Of Zuul

A classic style game that utilises command driven input from a command line interface. 

## Implementation

This assessment aimed to evaluate our skills when making a fully modular and open implementation of the Zuul game. For example, it should provide multiple languages, an 'API' for implementing new commands, etc. 

### API

An API was provided for the addition of new commands. Utilising reflection the game will attempt to load any .jar file within a certain
local directory to its running. Those .jar files will be scanned for any class that extends functionality given by the API. If found, it will
execute the functionality provided by the class.

### Saving

All data is saved using XML. XML is utilised as it provides an easy readable mechanism for those who wish to add new rooms, items, characters etc. 

### Localisation

The game in its current state provides two language options. English and French. English is the select fallback.

