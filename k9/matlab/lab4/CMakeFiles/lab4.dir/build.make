# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.19

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

#Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /usr/bin/cmake

# The command to remove a file.
RM = /usr/bin/cmake -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/koz9va/Documents/KPILABS/k9/matlab/lab4

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/koz9va/Documents/KPILABS/k9/matlab/lab4

# Include any dependencies generated for this target.
include CMakeFiles/lab4.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/lab4.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/lab4.dir/flags.make

CMakeFiles/lab4.dir/main.cpp.o: CMakeFiles/lab4.dir/flags.make
CMakeFiles/lab4.dir/main.cpp.o: main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/koz9va/Documents/KPILABS/k9/matlab/lab4/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/lab4.dir/main.cpp.o"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/lab4.dir/main.cpp.o -c /home/koz9va/Documents/KPILABS/k9/matlab/lab4/main.cpp

CMakeFiles/lab4.dir/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/lab4.dir/main.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/koz9va/Documents/KPILABS/k9/matlab/lab4/main.cpp > CMakeFiles/lab4.dir/main.cpp.i

CMakeFiles/lab4.dir/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/lab4.dir/main.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/koz9va/Documents/KPILABS/k9/matlab/lab4/main.cpp -o CMakeFiles/lab4.dir/main.cpp.s

CMakeFiles/lab4.dir/Newton.cpp.o: CMakeFiles/lab4.dir/flags.make
CMakeFiles/lab4.dir/Newton.cpp.o: Newton.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/koz9va/Documents/KPILABS/k9/matlab/lab4/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building CXX object CMakeFiles/lab4.dir/Newton.cpp.o"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/lab4.dir/Newton.cpp.o -c /home/koz9va/Documents/KPILABS/k9/matlab/lab4/Newton.cpp

CMakeFiles/lab4.dir/Newton.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/lab4.dir/Newton.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/koz9va/Documents/KPILABS/k9/matlab/lab4/Newton.cpp > CMakeFiles/lab4.dir/Newton.cpp.i

CMakeFiles/lab4.dir/Newton.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/lab4.dir/Newton.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/koz9va/Documents/KPILABS/k9/matlab/lab4/Newton.cpp -o CMakeFiles/lab4.dir/Newton.cpp.s

# Object files for target lab4
lab4_OBJECTS = \
"CMakeFiles/lab4.dir/main.cpp.o" \
"CMakeFiles/lab4.dir/Newton.cpp.o"

# External object files for target lab4
lab4_EXTERNAL_OBJECTS =

lab4: CMakeFiles/lab4.dir/main.cpp.o
lab4: CMakeFiles/lab4.dir/Newton.cpp.o
lab4: CMakeFiles/lab4.dir/build.make
lab4: CMakeFiles/lab4.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/koz9va/Documents/KPILABS/k9/matlab/lab4/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Linking CXX executable lab4"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/lab4.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/lab4.dir/build: lab4

.PHONY : CMakeFiles/lab4.dir/build

CMakeFiles/lab4.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/lab4.dir/cmake_clean.cmake
.PHONY : CMakeFiles/lab4.dir/clean

CMakeFiles/lab4.dir/depend:
	cd /home/koz9va/Documents/KPILABS/k9/matlab/lab4 && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/koz9va/Documents/KPILABS/k9/matlab/lab4 /home/koz9va/Documents/KPILABS/k9/matlab/lab4 /home/koz9va/Documents/KPILABS/k9/matlab/lab4 /home/koz9va/Documents/KPILABS/k9/matlab/lab4 /home/koz9va/Documents/KPILABS/k9/matlab/lab4/CMakeFiles/lab4.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/lab4.dir/depend

