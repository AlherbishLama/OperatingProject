# CSC227

In this project, we are required to program a simulation of a simple multiprogramming batch operating system. 

## The system hardware specifications:
* A single-core CPU.
* A hard disk with 2 GB available for user programs.
* A RAM with 192 MB available for user programs.
* A single IO device.

## The simulation covers two features of the operating system:
* Job scheduling: The operating system maintains a single job queue. Job Scheduler follows the Smallest Storage Requirement (SSR) policy.
* Process scheduling. The operating system maintains a single ready queue and a single I/O queue. CPU Scheduler follows the First-Come, First-Served (FCFS) scheduling algorithm policy.


# Work team

|  |   ## Name                  |    
| ------------- | ------------- | 
|1 |      Reema Alshowmer       |
|2 |      Haneen Alkhalaf       |          
|3 |      Rahaf Bin Mohanna     |        
|4 |      Lama Alherbish        |        


# Task distribution 

| Task                                       |       Name         |
| ------------- | ------------- | 
| General                                  | All members    |
| Setup                                     |All members     |
| Writing the README file        | All members    |
| Comprehensive review          | All members    |
The main For Project class    | Lama   |
| Job class                               | Rahaf, Lama   |
| CPU class                             |Haneen,Reema|
| PCB class                             | Lama,Rahaf  |
| IO Device class                     | Lama ,Haneen    |
| The System Hardware class | Reema, Rahaf |
| operating Project  class        | Reema     |



# Instructions on how to execute the program. 

### First step
Clone the project repository via GitHub or download it from the LMS.

### Second step
Import the project into your favorite IDE (eg Eclipse)

### Third step
run the ThemainForProject.java The bath of this class is
OperatingProject-->src--> ThemainForProject.java

### Fourth step
after excision the main you should notice the output of the program in the IDE console +Job.txt file +Result.txt file the output represented the simulation process.
the flow of the output in the console should be generate and read Job.txt file print the info of all the process (100) generating the result file.
Job.txt file should contain this info for each job from the100 generated jobs (JID, ECU, EMR)

### The Result.txt file contains the summery of the process it should has this info: -
* The Total number of jobs processed.
* AVR, MAX, MIN Job Size.
* #of jobs completed normally/abnormally 
* CPU/IO Utilization %
* AVR waiting time.
* #of jobs served by IO devices
Both Job.txt file and Result .txt will. located in OperatingProject directory.

# Goals

After simulating the operating system, our reflections became on two sides, the first one was the development of team members skills in writing and practicing code in Java language, and other side is the logical understanding of the scheduling process flow in the operating system, as we become with a more clearer look and understandable view of the scheduling mechanisms and the difference between its methods and the distinction in their uses, so that it was an opportunity for us to know the aspects of improving the system and its weaknesses and evaluate its performance by analyzing the results and linking them with the criteria upon which the performance of different types of scheduling depends on. such as analyzing the result of the total number of jobs processed which it might help us know throughput, CPU utilization and the rest of the other criteria (response time, waiting time…etc) which we can take advantage of evaluated for maximizing and minimizing based on which can be work for rising performance.
finally, after simulating we believe that acquisition a proper foundation of scheduling method will help us as developers in facing future problems with operating systems and will also learn how ensure fairness in CPU scheduling 

## Suggestions for improving performance

* Create an interactive simulation user interface so that we can study the results more clearly.
* Create a specialized program to analyze the results immediately after observation, print them from the simulation outputs and maintain its history in a special database, so that it will be a reference to study the best methods and measure the results in the best form of performance that occurred during the simulation.

