<h1>Course Registration System</h1>

## Overview
The Course Registration System is a Java-based application that facilitates course registration for students, lecturers, and administrators. It provides functionalities for viewing, registering, and managing courses, with authentication mechanisms for different user roles.

## Table of Contents
1. [Features](#features)
2. [Installation](#installation)
3. [Usage](#usage)
4. [Actors and Their Roles](#actors-and-their-roles)
5. [File Structure](#file-structure)
6. [Contributing](#contributing)
7. [License](#license)

## Features
- **Student Functions**: Search for subjects, register for subjects, drop subjects, and list registered subjects.
- **Lecturer Functions**: View subject details, choose subjects to teach, drop subjects, and view subject student lists.
- **Admin Functions**: List subjects, manage subject sections, set student capacity, drop subjects, confirm course registrations, and list registered students and lecturers.

## Installation
To run this project locally, follow these steps:

1. **Clone the repository**:
    ```bash
    git clone https://github.com/nenowoDev/OOPSem4GroupProject.git
    ```

2. **Navigate to the project directory**:
    ```bash
    cd OOPSem4GroupProject
    ```

3. **Compile the Java files**:
    ```bash
    javac Project.java
    ```

4. **Run the application**:
    ```bash
    java Project
    ```

## Usage
Upon running the application, you will be presented with a main menu to choose between Student, Lecturer, Admin, or exit the application.

### Main Menu
    COURSE REGISTRATION SYSTEM
        -----------------
            MAIN MENU
        -----------------

          SELECT OPTION
        (PRESS 4 TO EXIT)

     ENTER AN OPTION -> 

Depending on the selected option, you will be guided through a login process and then to the respective sub-menu for each role.

### Student Menu
- Search subject
- Register Subject
- Drop Subject
- List of Subjects
- List All registered Subjects
- Back to Main Menu

### Lecturer Menu
- View Subject Details
- Choose Subject to Teach
- Drop Subject
- Subject Student List
- Back to Main Menu

### Admin Menu
- List Subjects
- Manage Subject Sections
- Set Student Capacity
- Drop Subject/Course
- Confirm Course Registrations
- List Registered Students
- List Registered Lecturers
- Close Subjects
- Back to Main Menu

## Actors and Their Roles
1. **Student**: Can search, register, drop, and list subjects.
2. **Lecturer**: Can view details of subjects, choose subjects to teach, drop subjects, and view lists of students registered in subjects.
3. **Admin**: Manages subjects, sections, student capacities, and registration confirmations.

## File Structure
- `Project.java`: The main class file containing the menu and logic for the application.
- `Actor/`: Directory containing the Java classes for `Student`, `Lecturer`, and `Admin`.
- `src/`: Directory containing CSV files for student, lecturer, and admin lists.

## Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes. Ensure your code follows the established style and passes all tests.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Create a new Pull Request

## License
This project is licensed under the MIT License. See the LICENSE file for more details.
