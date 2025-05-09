# Simplified Payment Gateway - Spring Boot Project
---
## Features
- User registration and login (JWT-based authentication)
- Initiate a payment
- Confirm payment via simulated webhook
- Get payment status
- HMAC verification for webhook security
- Pagination support for payment list
---
## Tech Stack
- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA
- MySQL
- Maven
---
## How to Run Locally

1. Download the zip file from github.
2. Configure application.properties
- Update src/main/resources/application.properties with your DB credentials
3. Build and Run the App
- mvn clean install
- mvn spring-boot:run
  or
  Use run button or shortcut on any IDE.

End points are authorized with Authorization: Bearer <your-token>

 ## Payment APIs
Endpoint	                Description
/api/payments/initiate	  Initiate a new payment (JWT req)
/api/payments/confirm	    Simulate payment webhook
/api/payments/{id}	      Get payment status (JWT req)
/api/payments	Paginated   list of payments (JWT)

# HMAC-SHA256 Signature (for /confirm)
- Header: X-Signature: <base64-encoded HMAC>
- Payload for HMAC: paymentId + status
- Uses shared secret: webhook.secret

## Also I have attached Postman collection too.
