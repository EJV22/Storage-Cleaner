# Storage-Cleaner
**🧹 Storage Cleaner (Java CLI)**

A lightweight command-line application that helps users evaluate which files to delete by assigning each file a deletion priority score.

**🚀 Overview**

Storage Cleaner analyzes files based on usage, size, and type, then ranks them to highlight which ones are most suitable for deletion.
Instead of guessing what to remove, users get a clear, data-driven recommendation.

**⚙️ Features**
- Add and categorize files (App or Media)
- Automatic deletion scoring system
- Highlight recommended deletions
- Instant access to top deletion candidate (O(1))
- Prevent duplicate files using HashSet
- Save and load data from file
- Clean, formatted CLI interface
  
**🧠 How It Works:**
Each file implements a deletionScore() method (via the Score interface), which evaluates:

File size, Usage frequency, File type, Backup status

Higher score = higher priority for deletion.

**🏗️ Concepts**
- OOP: Inheritance, Interfaces, Polymorphism

- Data Structures:
HashSet → uniqueness
PriorityQueue → highest deletion score (O(1) peek)
ArrayList → sorted display

- File I/O: BufferedReader, FileWriter
Error Handling & Input Validation

📊 Example
No. | Name           | Type   | Score | Backup
----------------------------------------------
1   | GameApp        | App    | 90    | No   <-- Delete
2   | Notes.png      | Media  | 20    | Yes

Top Deletion: GameApp (Score: 90)

**▶️ Run the Program**
javac *.java
java ProjectOneTester

📌 Notes
Saved data is stored in: **App_Scores.txt**
PriorityQueue ensures the top deletion candidate is always available in O(1) time
HashSet prevents duplicate file entries

🎯 Purpose
Designed as an educational project to demonstrate how data structures and OOP can be applied to a practical problem like storage management.

⭐ Key Takeaway
Efficient design using:
HashSet + PriorityQueue + OOP
creates a simple but powerful system for decision-making.
