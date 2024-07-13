# Command-Line-Interpreter

## Description
This Java application simulates a basic command-line interpreter (CLI) with various commands like echo, pwd, cd, ls, mkdir, rmdir, touch, cp, rm, cat, wc, history, and exit. It allows users to navigate directories, manipulate files, display file contents, and manage command history.

## Functionality
The application supports the following commands:
- **echo**: Print arguments to the console.
- **pwd**: Print the current working directory.
- **cd**: Change directory.
- **ls**: List files and directories.
- **ls -r**: List files and directories in reverse order.
- **mkdir**: Create directories.
- **rmdir**: Remove directories.
- **touch**: Create files.
- **cp**: Copy files.
- **rm**: Remove files.
- **cat**: Concatenate and display files.
- **wc**: Count lines, words, and characters in a file.
- **history**: Display command history.
- **exit**: Terminate the application.

## Usage
1. **Commands**: Enter commands followed by arguments separated by spaces.
2. **Navigation**: Use `cd` to navigate directories (`cd ..` for parent directory).
3. **File Management**: Create, copy, delete files and directories using respective commands.
4. **View Content**: Use `cat` to display file contents and `ls` to list directory contents.
5. **History**: View past commands using `history`.

## Contributors
- [AliveTube (Ahmed Abd El-Wahab)](https://github.com/AliveTube)
- [Mohamed Khaled (Amin)](https://github.com/emailam)
- [Ali Tarek](https://github.com/Alitarek517)
- [Beshoy Hany](https://github.com/beshoy-hany74)

## Dependencies
- Java SE Development Kit (JDK)
- IDE (IntelliJ IDEA, Eclipse, etc.)

## Running the Application
1. **Compile**: Compile the `Terminal.java` file using `javac Terminal.java`.
2. **Run**: Execute the compiled class using `java Terminal`.

## Example
```
Enter a command: ls
file1.txt
file2.txt
Enter a command: mkdir newDir
Created directory: /path/to/current/newDir
```
