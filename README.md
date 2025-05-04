# Matmini - KeyStore

**Matmini - KeyStore** is a simple password management application focused on basic functionalities like managing, encrypting, decrypting, and facilitating the portability of passwords.

The application has two main screens: one for login and another for viewing and managing passwords.

## Build

To build and package the project, you need to have [Java 8](https://www.oracle.com/java/technologies/javase/javase8u211-later-archive-downloads.html) and [Maven](https://maven.apache.org/download.cgi) installed.

Run the command below in the root folder of the project to clean artifacts, compile the source code, and package it:

```sh
mvn clean package
```

## Features

The application consists of two main screens: a login screen and a password management screen. The access flow can be seen in the image below:

![Access Flow](https://github.com/user-attachments/assets/f26ee00d-7647-4ab5-a029-70e5c8d674d8)

### Login

To access the data, you need to provide a secret key and select a file that will be saved or opened (if previously used). If the file has already been registered and data has been entered with a certain secret key, the same key must be used to log in again, as decryption is tested to validate the login.

![Login Screen](https://github.com/user-attachments/assets/fec14e3c-9459-4f4b-bba8-98f2a0a7a299)

### Viewing

After successfully logging in, all records are displayed, showing the user and chosen site. There are no specific validations for saving data, only a double password confirmation, so make sure it is correct.

Options to edit, copy (where decryption occurs), or delete a password record are available.

![Viewing Screen](https://github.com/user-attachments/assets/d161bc98-acb1-4dce-b326-d7abc7dee842)

The option to add a new record can be accessed under **_Tools > Add Registry_**:

![Add Registry](https://github.com/user-attachments/assets/924e8a91-3e1b-4955-ac31-56b0fd43310f)

The new record will be displayed in the list:

![Record Listing](https://github.com/user-attachments/assets/d23699c7-8964-4bbc-a41f-d47604a64a93)

📄 See [CHANGELOG](./CHANGELOG.md) for version history and upcoming features.
