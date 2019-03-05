CSV to SQL
By Andrew Millian
All rights reserved

Running the code: Central.java will run as is, and will report how many incorrect and incomplete data sets there are, as well as generating a bad data csv file. Note that the file selected is currently hard coded for testing purposes, but it is relatively simple to replace that with a scanner to grab a different file.

Approach:
I separated the problem into several discrete steps: Parsing the CVS file, checking and modifying the cvs file, inserting into the SQL database, and logging the results.

Parsing: I used Open CVS to parse the file.

Checking and modifying the file: First, I check to see if the line has the correct number of entries, then if any of the entries are empty. If they are, they are written to a CSV file. If they aren’t they are fed through a switch statement to ensure that they are the proper value types. The conversion of G to a double is currently nonfunctional.

Inserting: This is currently nonfunctional due to time constraints. The planned implementation separates the SQL insertion into a separate method which is currently nonfunctional due to time constraints. In a larger program this would be separated into a separate file. The 10 inputs are named A-J respectively because of explicit instructions, more meaningful names would have been chosen otherwise. 

Logging: Uses a standard logging system, with three integer variables incrementing at appropriate portions during the program. This is fully functional.
