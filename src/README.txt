SMART GREENHOUSE SENSOR SIMULATION
----------------------------------

1. DEPENDENCIES
This program is written in standard Java and requires no external libraries. It is designed to compile and run in a standard Linux command-line environment (such as the Curtin Linux VDI). 

2. HOW TO COMPILE AND RUN
Navigate to the directory containing the source files and compile the program using the terminal:
> javac businesslogic/*.java csv/*.java datatypes/*.java logger/*.java Main.java

Run the compiled program using:
> java Main

3. HOW TO USE THE PROGRAM
Upon starting the program, you will be prompted to enter the path to the CSV file. If you do not have one, enter any invalid path (e.g., "none") and the system will automatically generate a blank "data.csv" file in your current directory.

You will then be presented with the Main Menu. Enter the number corresponding to your desired action:
- Options 1, 2, and 3 will filter the greenhouse data (by all, zone, or sensor type, respectively). 
- Once a filter is selected, a secondary menu will appear allowing you to calculate specific statistics (average, min, max, unsafe readings, etc.).
- Option 4 allows you to manually add new sensor data. Follow the prompts to enter the value and time. The system will auto-generate a valid ID.
- Option 5 allows you to delete an existing record by providing its 6-character Sensor ID.
- Option 6 will exit the program safely.

All outputs, interactions, and errors are automatically recorded in a "logs.txt" file generated in the project directory.
***************************************************************************************************************************************


DOCUMENTAITON:
--------------------------------------------------------------------------------------

SensorData:
    This is the aggregate class of SensorReading instances

    SensorData(String filePath)
        filePath = Path to csv file containing data
        If filePath does not exist, an IOException thrown.
    
    SensorData()
        Default constructor. Sets filePath to an empty string and creates an empty SensorReading array on length 0
    
    SensorData(SensorData obj)
        obj = another instance of SensorData
        It creates a deep copy of all the SensorReading objects held by obj.
    
    public static SensorData readCsv(filePath):
        filePath = Path to csv file containing data.
        This is a static method that reads the csv file filePath and instantiates each records in the csv. NOTE: unlike the Constructor, readCsv does NOT set filePath to csv.

    public void append(SensorData obj):
        obj = another instance of SensorData
        