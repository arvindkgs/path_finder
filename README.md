# Multi-threaded PathFinder

This is a java based project that can be used to find paths of multiple robots in given Grid using different. It is built to plugin desired algorithms for path finding. Supports extending existing algorithms by implementing Algorithm service interface.

Grid is two-dimensional array. Grid can be initialized with multiple customizations using a builder. Custom constraints can be added to the grid like,
* Lower Limit - Validates and denies movement of robot to cells below given (x,y) value
* Upper Limit - Validates and denies movement of robot to cells above given (x,y) value
* Single Robot in Cell Limit - Denies more than one robot to occupy a given cell at any moment.
* Custom constraints can be defined and added

This is multithreading safe as movement to each cell is synchronized. Advantage with this is if robot 'Tron' is moving to cell x, robot 'Mesa' is not blocked on moving to cell y. This is achived by utilizing individual locks for each cells. If movement is synchronized on entire grid then at any moment only one robot is allowed to move.  