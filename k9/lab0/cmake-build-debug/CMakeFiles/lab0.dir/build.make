# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.17

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Disable VCS-based implicit rules.
% : %,v


# Disable VCS-based implicit rules.
% : RCS/%


# Disable VCS-based implicit rules.
% : RCS/%,v


# Disable VCS-based implicit rules.
% : SCCS/s.%


# Disable VCS-based implicit rules.
% : s.%


.SUFFIXES: .hpux_make_needs_suffix_list


# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /home/serafim/.local/share/JetBrains/Toolbox/apps/CLion/ch-0/202.6397.106/bin/cmake/linux/bin/cmake

# The command to remove a file.
RM = /home/serafim/.local/share/JetBrains/Toolbox/apps/CLion/ch-0/202.6397.106/bin/cmake/linux/bin/cmake -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/serafim/Documents/KPI/labs/k9/lab0

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/serafim/Documents/KPI/labs/k9/lab0/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/lab0.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/lab0.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/lab0.dir/flags.make

CMakeFiles/lab0.dir/lab0.c.o: CMakeFiles/lab0.dir/flags.make
CMakeFiles/lab0.dir/lab0.c.o: ../lab0.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/serafim/Documents/KPI/labs/k9/lab0/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building C object CMakeFiles/lab0.dir/lab0.c.o"
	/home/serafim/.linuxbrew/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/lab0.dir/lab0.c.o   -c /home/serafim/Documents/KPI/labs/k9/lab0/lab0.c

CMakeFiles/lab0.dir/lab0.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/lab0.dir/lab0.c.i"
	/home/serafim/.linuxbrew/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/serafim/Documents/KPI/labs/k9/lab0/lab0.c > CMakeFiles/lab0.dir/lab0.c.i

CMakeFiles/lab0.dir/lab0.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/lab0.dir/lab0.c.s"
	/home/serafim/.linuxbrew/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/serafim/Documents/KPI/labs/k9/lab0/lab0.c -o CMakeFiles/lab0.dir/lab0.c.s

# Object files for target lab0
lab0_OBJECTS = \
"CMakeFiles/lab0.dir/lab0.c.o"

# External object files for target lab0
lab0_EXTERNAL_OBJECTS =

lab0: CMakeFiles/lab0.dir/lab0.c.o
lab0: CMakeFiles/lab0.dir/build.make
lab0: CMakeFiles/lab0.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/serafim/Documents/KPI/labs/k9/lab0/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking C executable lab0"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/lab0.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/lab0.dir/build: lab0

.PHONY : CMakeFiles/lab0.dir/build

CMakeFiles/lab0.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/lab0.dir/cmake_clean.cmake
.PHONY : CMakeFiles/lab0.dir/clean

CMakeFiles/lab0.dir/depend:
	cd /home/serafim/Documents/KPI/labs/k9/lab0/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/serafim/Documents/KPI/labs/k9/lab0 /home/serafim/Documents/KPI/labs/k9/lab0 /home/serafim/Documents/KPI/labs/k9/lab0/cmake-build-debug /home/serafim/Documents/KPI/labs/k9/lab0/cmake-build-debug /home/serafim/Documents/KPI/labs/k9/lab0/cmake-build-debug/CMakeFiles/lab0.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/lab0.dir/depend

