# Simple Post-Comments Service

## Objective
This project is a basic Post-Comments Service that allows users to create text-based posts and add comments to these posts. The goal is to demonstrate core functionality, proper API design, data storage strategies, and clean coding practices.

---

## Features

### 1. Core Functionality
- Users can create posts.
- Users can comment on posts.
- Each post supports multiple comments.

### 2. Comment Features
- Comments are text-based.
- (Optional) Rich text support for formatting such as bold, italics, and hyperlinks.

### 3. RESTful APIs
- Endpoints for creating and retrieving posts.
- Endpoints for adding and retrieving comments for specific posts.

### 4. Data Storage
- Posts and comments are stored in a **MongoDB database** for scalability and flexibility.
- MongoDB provides a natural document-based schema to represent posts and their associated comments.

---

## Technologies Used
- **Language:** Java
- **Framework:** Spring Boot for backend development
- **Database:** MongoDB
- **Tools:** Postman for API testing, Maven for dependency management

---

## API Endpoints

### Posts
- **Create Post**  
  `POST /api/posts`  
  **Request Body:**  
  ```json
  {
    "title": "Post Title",
    "content": "Post Content"
  }
