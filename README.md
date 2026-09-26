# Medical Prescription Parser

A full-stack web application that extracts medical information from prescription images using OCR and stores the extracted data digitally.

## Features

* Upload prescription images
* Extract patient, doctor, medicine, dosage, and frequency details
* JWT-based user authentication
* Prescription history
* REST APIs
* PDF report generation

## Tech Stack

* **Backend:** Java, Spring Boot, Spring Data JPA
* **Frontend:** React.js, JavaScript, HTML, CSS
* **Database:** MySQL
* **OCR:** Tesseract
* **Tools:** Git, GitHub, Postman

## Workflow

```text
Upload Prescription
        ↓
Tesseract OCR
        ↓
Extract Details
        ↓
Spring Boot REST API
        ↓
MySQL Database
        ↓
View Prescription History
```

## Setup

1. Install Java 17+, Node.js, MySQL, and Tesseract OCR.
2. Clone the repository.
3. Configure MySQL and Tesseract in the backend.
4. Run the Spring Boot backend.
5. Run the React frontend using:

```bash
npm install
npm start
```

## Author

**Mahammad Saheel**
B.E. Computer Science & Engineering

