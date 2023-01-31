------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all he/she needs to know. The comments should usually include at least:
------------------------------------------------------------------------

PROJECT TITLE:AT28C256 EEPROM programmer pc client
PURPOSE OF PROJECT:Following The Ben Eater 8Bit Breadboard computer youtube series needed a eeprom programmer
VERSION or DATE:early alpha v0.2
HOW TO START THIS PROJECT:There is a java jar file download it and allow as an executable, it was tested with java 11;

AUTHORS:Ian Worsley
DESCRIPTION:An eeprom programmer client written in java that works with a homemade at28c256 eeprom programmer built in arduino check this out https://www.youtube.com/watch?v=K88pgWhEb1M.I have tested this on both my ubuntu and windows 10 machines it works but is rather slow at writing but gets the job done and is cheap to make.
USER INSTRUCTIONS:
The java client requires the arduino sketch from bread80 https://github.com/aussieworz1983/BenPrommer-SDP which has extra functionallity like remove write protection on 28c256 its forked and adapted to work with the client on my git hub https://github.com/aussieworz1983/BenPrommer-SDP.

Writing:
Check the com port for your device is selected in the port picker dropdown, if writing open file from file tab once filr opened press write when its finished light on nano will no longer flash.
Reading:
Press read once chip is read the contents will be displayed in textbox in hexidecimal you can also save the read dat to bin or out file for backing up.


