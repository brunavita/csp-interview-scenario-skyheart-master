# csp-interview-scenario-skyheart
Content Scheduling and Planning Interview Test

## Scenario 
Sky wants to be able to start optimising and improving the selection of programmes that they schedule for one of their new channels called Sky Heart. Sky Heart will be a channel that plays content that is light-hearted and whose main demographic will be women within their twenties and thirties.

## Test Description 
We have provided a solution which contains the structure for implementing the optimisation algorithm for scheduling content for Sky Heart. 
Within this you will find a set of titles and a schedule that contains a list of days and times for which content needs to be scheduled. The test data for this is not complete and will need to be extended to cover the above scenario. 

**We advise that you take the time to review the test data and the structure of the solution before starting.** 

### First Stage – Filtering Appropriate Programmes
----
You have been provided with a JSON file called programme-data.json located within the resources folder of the solution. For each programme has its own set of metadata.   
 
Where:
* programmeName represents the title of the programme
* provider represents the studio from which the programme was sourced
* viewershipRating represents how many viewers (in thousands) have watched that programme
* genre  represents the category of programme
* certificate represent the certification of that programme

Sky Heart has access to the full list of programmes available within Sky’s programme inventory. However Sky Heart can only take content if it matches the following rules:
* We should only take in programmes that match the genre of comedy
* We should only take in programmes that match the genre of romance
* We should only take in programmes whose provider matches that of Sky

We want you to be able to filter the programme list to be able to only allow appropriate content to be assigned to a slot. 

**We expect to see the use of TDD.**

### Second Stage – Programme Assignation to a Slot
----

A Schedule is formatted as follows:
* A scheduleStartDate
* A scheduleEndDate
* A list of Slots that exists within the schedule. 

A Slot points to a specific date and time within the schedule. A Slot can only have one programme assigned to it. Each slot has a duration of one hour. 

Each Slot has the following data:
* A date
* A startTime
* An endTime
* A Programme (this is null by default)

Sky must comply to rules around the placement of programmes to ensure that they are not breaching Ofcom rules or violating any deals that they have negotiated with studios.

These rules are defined as follows: 
* We cannot play programmes that have a certificate of 18 between 06:00 in the morning to 21:00 in the evening.
* We cannot play the same programme:
* Immediately after its initial play
* A programme at most can be played twice in the same day but must have at least two programmes scheduled in between. 

Within the solution you have been provided with a ScheduleSolutionScorer class. 

We want the ability to score the assignation of a programme for a given slot. If the programme assignment violates any of the rules above we want to decrement the score by 5 points. 
We would like you to implement the CalculateScore method within the Scorer class. This method should return the score and should take in the Schedule as a parameter. 

**Please ensure you use TDD.** 
