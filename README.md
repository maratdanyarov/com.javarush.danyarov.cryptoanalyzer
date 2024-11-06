Caesar Cipher Analyzer

This is the final project for Module 1 of JavaRush University. The application provides a graphical user interface (GUI) to encrypt and decrypt text files using the Caesar Cipher algorithm. Additionally, it includes features for brute-force decryption without knowing the key.

Features

	•	Encrypt Files: Encrypt any text file using a specified key.
	•	Decrypt Files: Decrypt files encrypted with the Caesar Cipher using the known key.
	•	Brute Force Decryption: Decrypt files without knowing the key by trying all possible keys and selecting the most likely decryption based on language analysis.
	•	Supports English and Russian Alphabets: The application works with both English and Russian texts.
	•	User-Friendly GUI: Built with JavaFX, providing an intuitive interface for users.

Technologies Used

	•	Java 11
	•	JavaFX
	•	Maven

Getting Started

Prerequisites

	•	Java Development Kit (JDK) 11 or higher
	•	Maven

Installation

	1.	Clone the Repository

git clone https://github.com/yourusername/caesar-cipher-analyzer.git
cd caesar-cipher-analyzer


	2.	Build the Project
Use Maven to build the project:

mvn clean install



Running the Application

	1.	Navigate to the Target Directory

cd target


	2.	Run the Application

java -jar caesar-cipher-analyzer.jar



Usage

	1.	Launch the Application
After running the application, a GUI window will appear.
2.	Encrypting a File
•	Navigate to the Encrypt tab.
•	Click on Select a File to choose the text file you wish to encrypt.
•	Enter the encryption Key (an integer).
•	Click Encrypt.
•	Choose a location to save the encrypted file.
3.	Decrypting a File
•	Navigate to the Decrypt tab.
•	Click on Select a File to choose the encrypted text file.
•	Enter the decryption Key (the same key used for encryption).
•	Click Decrypt.
•	Choose a location to save the decrypted file.
4.	Brute Force Decryption
•	Navigate to the Brute Force tab.
•	Click on Select a File to choose the encrypted text file.
•	Select the expected Language (English or Russian).
•	Click Start Brute Force.
•	The application will attempt to decrypt the file without knowing the key.
•	A snippet of the decrypted text will be displayed.
•	Optionally, save the full decrypted text to a file.

Project Structure

	•	application: Contains the main application class that launches the GUI.
	•	controller: Handles user interactions with the GUI.
	•	model: Contains the core logic for encryption, decryption, brute-force analysis, file management, and validation.

Acknowledgments

	•	This project was developed as part of the final project for Module 1 at JavaRush University.
	•	Special thanks to ChatGPT, which was instrumental in generating Javadoc comments, inline code comments, and assisting with debugging.

Contact

For any inquiries or feedback, please contact [maratdanyarov87@gmail.com].
