# Example of GAs
This Maven project consists in three modules:

- `ga-core`, containing the common logic for designing a very simple GA;
- `binary-square`, containing the GA for optimizing the square function on a population of 5-bit long strings. It depends on `ga-core`;
- `eight-queens`, containing the GA for solving the problem o 8 Queens. It depends on `ga-core`.

# Build the Project from IntelliJ

1. Open Intellij Idea
2. Make sure you have `Git`, `GitHub` and `Maven Helper` IntelliJ plugins installed
3. "File>New>Project from Version Control..."
4. Paste `https://github.com/SeSa-Lab/ga-examples`
5. Click "Clone"
6. Wait for Maven plugin build
7. Enjoy!

# Build the Project from Command Line

1. Make sure you have `git` installed on your local machine
2. Open a terminal instance
3. `git clone https://github.com/SeSa-Lab/ga-examples`
4. Open the IDE you like
5. Open `ga-examples` project
6. Let the IDE do the build (assuming the IDE handles the `git`-managed projects)
7. Enjoy!
