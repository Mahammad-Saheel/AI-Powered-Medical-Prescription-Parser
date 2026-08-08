# AI-Powered-Medical-Prescription-Parser

I developed a full-stack AI-powered medical prescription parser designed to simplify the process of managing and organizing prescription information from uploaded prescription images. The project was built using **Java, Spring Boot, React.js, MySQL, and Tesseract OCR**, giving me hands-on experience in both frontend and backend development.

The core functionality of the application is based on **Optical Character Recognition (OCR)**. I integrated **Tesseract OCR** to process uploaded prescription images and extract important information such as patient name, doctor name, medicines, dosage, and frequency. The extracted information is then organized and stored in the database, making prescription details easier to search, access, and manage.

For the backend, I developed the application using **Spring Boot** and designed **REST APIs** to enable communication between the React.js frontend and backend services. I implemented **JWT-based authentication** to provide secure user access and protect prescription-related information. The backend follows a **layered architecture**, separating different responsibilities such as controllers, services, repositories, and data access logic. I used **Spring Data JPA with Hibernate** for database operations and designed the MySQL database to efficiently store user and prescription information.

On the frontend, I used **React.js** to create an interactive interface where users can upload prescription images, view extracted information, and access their prescription history. I also implemented a searchable prescription history feature to make previously processed prescriptions easier to retrieve. The application includes **image upload functionality and PDF report generation**, allowing users to generate reports from stored prescription information.

During development, I worked on integrating multiple technologies and solving issues related to OCR extraction, API communication, authentication, database management, and frontend-backend integration. This project helped me strengthen my understanding of **full-stack development, REST API design, database management, authentication, and modular software architecture**.

Overall, the project gave me practical experience in building a complete application from frontend to backend and integrating an external OCR technology into a real-world software solution. It also improved my ability to independently learn technologies, troubleshoot implementation issues, and develop maintainable software.
