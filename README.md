# Extrusion Operator Calculator
A tool for common Extrusion Operator calculations.


## Tools
### Saw Setting
- Input the `Current Length`, the `Desired Length`, and the `Current Setting` of the saw.
- Outputs the `New Setting` fr the saw. (rounded to the nearest 1/8th of an inch)

### Rack Time
- Input the `Puller Speed`, the `Current Length` of the profile, and the number of  `Pieces per Rack`.
- Outputs the `Rack Time` in hours and minutes. (does not account for samples or NCM)

### Speed Change
- Input the `Current Puller Speed`, the `Current Feeder Rate`, the `Current Extruder Rate`, and the `New Puller Speed`.
- Outputs the `New Feeder Rate` , and the `New Extruder Rate`.

### Weatherstrip Usage (TODO)
- Input the `Puller Speed`, and the `Spool Length`.
- Outputs the `Time` in hours and minutes.

### Weight Calculation and Adjustment (TODO)
#### Weight Calculation
- Input the `Current Weight`, and the `Standard Weight`.
- Outputs the `Percent Weight`, the `Minimum Weight`, and the `Maximum Weight`.

#### Weight Adjustment for ECS (TODO)
- Input the `Set Point`, the `Current Weight`, and the `Current Weight Percentage`.
- Outputs TBD. (Likely `Set Point for 100%`, `Set Point for +1%`, `Percentage Increase for each +5 Set Point`, ...)

#### Weight Adjustment for non-ECS (TODO)
TBD


## Developer Information
Alan F. Larimer, Jr. alarimer@gmail.com
