# 💡 Logic Circuit Simulator

[![Java](https://img.shields.io/badge/Language-Java-007396?logo=openjdk&style=flat-square)](https://www.java.com/)

## Project Overview

Digital logic circuits are the **building blocks** of all computers. They use simple gates **(AND, OR, NOT)** to make decisions. Understanding how logic expressions turn into working circuits is important for computer science students. However, seeing how this works can be hard without the right tools.

This project creates a simple logic circuit simulator. It involves expressions like (A && B) || !C and constructs a system that can test them with various inputs. 
The application allows users to input any valid logical expression, which is then automatically parsed, converted, and displayed as an equivalent circuit diagram using fundamental **AND, OR, and NOT** gates.


---

## ✨ Key Features

The simulator delivers the following core functionalities:

* **Propositional Logic Parsing:** Accepts expressions in a simple, standardized format (e.g., `(A || B) && (!C)`).
* **Automatic Circuit Synthesis:** Generates a corresponding circuit diagram using only **AND, OR, and NOT** gates.
* **Visual Circuit Display:** Renders a clean, navigable graphical representation of the circuit structure.
* **Interactive Testing:** Users can easily toggle the boolean values (true/false) of input variables (A, B, C, etc.).
* **Real-time Output Visualization:** Highlights the circuit pathways and the output state of every gate in real-time, allowing users to trace the signal flow visually.

---

## 🚀 Getting Started

These instructions will guide you through setting up and running the Java application on your local machine.

### Prerequisites

You must have the following installed:

* **Java Development Kit (JDK) 11 or higher**
* **A build tool (Maven is recommended)**

### Installation & Execution

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/Glover69/Logic-Circuit-Simulator.git](https://github.com/Glover69/Logic-Circuit-Simulator.git)
    cd Logic-Circuit-Simulator
    ```

2.  **Build the Project (using Maven):**
    Use Maven to sync and reload, as well as download all the packages and dependencies the application needs.

3.  **Run the Simulator:**
    Execute the HelloApplication.java (main class) file located in the ui folder, and you're up and running!

---

## ✍️ Usage

The simulator is designed for immediate use upon execution:

1.  **Input:** Enter your logical expression (e.g., `A && (B || C)`).
2.  **Generate:** Click the Parse button to render the circuit.
3.  **Test:** Click on the inputs in the circuit render to change their values (Red for FALSE, Green for TRUE).
4.  **Observe:** The visual representation of the circuit will immediately update, showing the flow of the signal and the final output state will be shown below.

### Supported Operators

|   Operator   | Notation |
|:------------:|:--------:|
|   **AND**    |   `&&`   |
|    **OR**    |  `\|\|`  |
|   **NOT**    |   `!`    |
| **Grouping** |  `( )`   ||

---

## 🛠️ Technologies Used

* **Core Language:** Java
* **GUI Framework:** JavaFX
* **Build System:** Maven

---

