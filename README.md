# 💡 Logic Circuit Simulator

[![Java](https://img.shields.io/badge/Language-Java-007396?logo=openjdk&style=flat-square)](https://www.java.com/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![GitHub Issues](https://img.shields.io/github/issues/Glover69/Logic-Circuit-Simulator?style=flat-square)](https://github.com/Glover69/Logic-Circuit-Simulator/issues)

## Project Overview

The **Logic Circuit Simulator** is an interactive, Java-based educational tool designed to visually bridge the gap between abstract **propositional logic** and concrete **digital logic circuits**.

The application allows users to input any valid logical expression, which is then automatically parsed, converted, and displayed as an equivalent circuit diagram using fundamental **AND, OR, and NOT** gates. It is an ideal resource for students and enthusiasts looking to learn or visualize Boolean algebra and digital design principles.


---

## ✨ Key Features

The simulator delivers the following core functionalities:

* **Propositional Logic Parsing:** Accepts expressions in a simple, standardized format (e.g., `(A || B) && (!C)`).
* **Automatic Circuit Synthesis:** Generates a corresponding circuit diagram using only **AND, OR, and NOT** gates.
* **Visual Circuit Display:** Renders a clean, navigable graphical representation of the circuit structure.
* **Interactive Testing:** Users can easily toggle the binary values (1/0) of input variables (A, B, C, etc.).
* **Real-time Output Visualization:** Highlights the circuit pathways and the output state of every gate in real-time, allowing users to trace the signal flow visually.

---

## 🚀 Getting Started

These instructions will guide you through setting up and running the Java application on your local machine.

### Prerequisites

You must have the following installed:

* **Java Development Kit (JDK) 11 or higher**
* **A build tool (Recommended: Apache Maven)**

### Installation & Execution

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/Glover69/Logic-Circuit-Simulator.git](https://github.com/Glover69/Logic-Circuit-Simulator.git)
    cd Logic-Circuit-Simulator
    ```

2.  **Build the Project (using Maven):**
    Use Maven to compile the source code and package the application into an executable JAR file.
    ```bash
    mvn clean install
    ```

3.  **Run the Simulator:**
    Execute the generated JAR file from the `target` directory. (Note: Adjust the JAR name if your `pom.xml` specifies a different output.)
    ```bash
    java -jar target/logic-circuit-simulator-[VERSION].jar
    ```

> 💡 **IDE Quick Start:** Alternatively, import the project into an IDE like IntelliJ or Eclipse, and run the main class directly.

---

## ✍️ Usage

The simulator is designed for immediate use upon execution:

1.  **Input:** Enter your logical expression (e.g., `A && (B || C)`).
2.  **Generate:** Click the corresponding button to render the circuit.
3.  **Test:** Click on the inputs in the circuit render to change their values (Red for FALSE, Green for TRUE).
4.  **Observe:** The visual representation of the circuit will immediately update, showing the flow of the signal and the final output state.

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

