# Polls App

Polls App is a web-based application with two roles: **Admin** and **User**.

- **Admins** can create new polls, view existing polls, and vote.
- **Users** can view polls and vote.

## 🔧 Running Locally

To run the app locally using Docker:

```bash
docker compose build
docker compose up
```

The application will be available at: [http://localhost:3000](http://localhost:3000)

## 🌐 Running Remotely

You can access the live version of the app at:

🔗 [https://cogify.jakovkusic.dev](https://cogify.jakovkusic.dev)

### 👤 User Accounts

#### Admin
- **Username:** `poll_admin`
- **Password:** `poll_admin`

#### Users
- **Username:** `poll_one`
- **Password:** `poll_one`

- **Username:** `poll_two`
- **Password:** `poll_two`

## 🚀 CI/CD

Complete **CI/CD pipeline** is set up using **GitHub Actions**.  
All changes pushed to the `master` branch are automatically built and deployed.

## 📝 Notes

- This project uses Docker and Docker Compose for easy setup.
- Admins have full access to manage polls.
- Users can only view and vote in polls.