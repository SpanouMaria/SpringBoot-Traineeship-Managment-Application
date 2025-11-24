# SPRINGBOOT TRAINEESHIP MANAGEMENT APPLICATION

This project implements a **web-based traineeship management application** using **Java Spring Boot**, supporting **user accounts, traineeship creation, applications, evaluations, and automated allocation strategies**. It was developed as part of the **MYY803 – Software Engineering** course at the **University of Ioannina**.

---

## TABLE OF CONTENTS
1. [Overview](#overview)
2. [Features](#features)
3. [Input Data](#input-data)
4. [Algorithms Implemented](#algorithms-implemented)
5. [Installation](#installation)
6. [Usage](#usage)
7. [License](#license)
8. [Contact](#contact)

---

## OVERVIEW

The **Traineeship Management Application** is a platform designed to help the **traineeship committee**, **students**, **companies**, and **professors** manage all stages of the traineeship lifecycle.  
Users can:

- **Register and log in**  
- **Create profiles** (students, companies, professors)  
- **Publish, browse, and apply for traineeship positions**  
- **Manage applications and assignments**  
- **Submit and review evaluations**  

The application supports multiple roles and enforces different functionalities depending on the user type.

The system further includes advanced features such as:

- **Matching traineeship positions with students based on Jaccard similarity**  
- **Supervisor allocation strategies** (by interests or by professor load)  
- **Role-based access control**  
- **Layered architecture (controllers, services, repositories, models)**  

These features provide a **clean, maintainable, and extensible Spring Boot structure**, reflecting the requirements of the course project.

---

## FEATURES

- **User Authentication**
  - Registration, login, logout  
  - Role-based permissions (Student, Company, Professor, Committee)

- **Student Features**
  - Create a full profile (interests, skills, preferred location)  
  - Apply for traineeship positions  
  - Submit logbook entries

- **Company Features**
  - Create company profile  
  - Announce new traineeship positions  
  - View advertised and assigned positions  
  - Submit evaluations for students

- **Professor Features**
  - Create profile with interests  
  - View supervised traineeships  
  - Submit evaluations for both students and companies

- **Committee Features**
  - View all applications  
  - Match students to positions based on:
    - **Interests (Jaccard similarity)**
    - **Preferred location**
    - **Combined strategy**
  - Assign traineeship positions  
  - Assign supervising professors  
  - Monitor in-progress traineeships  
  - Finalize pass/fail results

---

## INPUT DATA

- **User Accounts** – Registered through the platform (multiple roles)  
- **Student Profiles** – Name, university ID, interests, skills, preferred location  
- **Company Profiles** – Company name and location  
- **Professor Profiles** – Full name, interests  
- **Traineeship Positions** – Description, duration, required skills, topics of interest  
- **Applications** – Student applications for available positions  
- **Evaluations** – Submitted by professors and companies  
- **Committee Assignments** – Matching, supervision, final decisions  

---

## ALGORITHMS IMPLEMENTED

1. **Interest-Based Matching (Jaccard Similarity)**
   - Computes similarity between **student interests** and **position topics**
   - Formula: `J(I, T) = |intersection(I, T)| / |union(I, T)|`
   - Accepts matches above a given threshold

2. **Location-Based Matching**
   - Filters positions based on a student's **preferred location**  
   - Matches companies located in the specified region

3. **Combined Matching Strategy**
   - Uses **both interest similarity and location**

4. **Supervisor Allocation**
   - Strategy 1: Match professor interests with topics (Jaccard similarity)  
   - Strategy 2: Assign professor with **minimum load**  

5. **Role-Based Access Control & Workflow Logic**
   - Ensures each role performs only permitted operations  
   - Ensures correct transitions (e.g., from application → assignment → supervision → evaluation)

---

## INSTALLATION

1. **Clone the repository:**
```bash
git clone https://github.com/ChristosGkovaris/Traineeship-Management-Application.git
cd Traineeship-Management-Application
```
2. Install Java (>= 17) and Maven
3. Install dependencies:
```bash
mvn clean install
```
4. Set up the database (MySQL recommended):
```bash
mvn spring-boot:run
```
5. Ensure database credentials are configured in:
```bash
src/main/resources/application.properties
```

---

## USAGE

1. Open your browser and navigate to:
```
http://localhost:3000
```
2. Register an account (Student, Company, Professor, Committee)
3. Log in to access your role-specific dashboard
4. Students can:
   - Create profiles  
   - Browse and apply for positions  
   - Submit logbooks  
5. Companies can:
   - Publish positions  
   - View assignments  
   - Submit evaluations  
6. Professors can:
   - Review supervised traineeships  
   - Submit evaluations for students and companies  
7. Committee members can:
   - Match positions  
   - Assign supervisors  
   - Finalize traineeships  

---

## LICENSE

This project was developed as part of the **MYY803 – Software Engineering** course at the  
University of Ioannina.

Original specification & academic materials by: **A. Zarras**  
Final implementation by the project team.

---

## CONTACT

**Christos-Grigorios Gkovaris**  
University of Ioannina – Computer Science and Engineering  
GitHub: https://github.com/ChristosGkovaris

**Maria Spanou**  
University of Ioannina – Computer Science and Engineering  
GitHub: https://github.com/SpanouMaria

**Vasilis Kotopoulos**
University of Ioannina – Computer Science and Engineering  
GitHub: https://github.com/VasilisK1
